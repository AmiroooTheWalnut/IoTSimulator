/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import de.siegmar.fastcsv.reader.CsvContainer;
import iotsimulator.Structure.Metric;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class MetricManager implements Serializable {

    static final long serialVersionUID = 1L;

    public String cSVFilePath;
//    public CsvReader cSVReader = new CsvReader();
    public CsvContainer data;

    public ArrayList<Long> timeStamps = new ArrayList();//IN NANO SECONDS***

    public int timeIndex = -1;
    public int metricIndices[];
    public ArrayList<Metric> availableMetrics = new ArrayList();
    public ArrayList<Metric> selectedMetrics = new ArrayList();

    public String smallestTimeScale;
    public String timeType;
    public String customTimeFormat;

    public long startingTime;
    
    public long endingTime;

    public void setupTimeStamps() {
        for (int i = 0; i < data.getRowCount(); i++) {
            try {
                timeStamps.add(convertToUnixEpochTime(data.getRow(i).getField(timeIndex), timeType, smallestTimeScale));
            } catch (ParseException ex) {
                Logger.getLogger(MetricManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        startingTime=timeStamps.get(0);
        endingTime=timeStamps.get(timeStamps.size()-1);
    }

    public long convertToUnixEpochTime(String inputTime, String type, String smallestStep) throws ParseException {
        long output=-1;
        switch (type) {
            case "Raw number":
                switch (smallestStep) {
                    case "Miliseconds":
                        output=Long.parseLong(inputTime);
                        break;
                    case "Seconds":
                        output=Long.parseLong(inputTime)*1000;
                        break;
                    case "Minutes":
                        output=Long.parseLong(inputTime)*1000*60;
                        break;
                    case "Hours":
                        output=Long.parseLong(inputTime)*1000*60*60;
                        break;
                    case "Days":
                        output=Long.parseLong(inputTime)*1000*60*60*24;
                        break;
                    default:
                        break;
                }

                break;
            case "Custom date format":
                SimpleDateFormat sdf = new SimpleDateFormat(customTimeFormat);
                Date date = sdf.parse(inputTime);
                output=date.getTime();
                break;
            default:
                break;
        }
        return output;
    }

    public double getMetricValue(Metric metric, long requestedTime) {
        for(int i=metric.lastRecordIndex;i<timeStamps.size();i++)
        {
            if(timeStamps.get(i)>requestedTime)
            {
                metric.lastRecordIndex=i;
                return Double.parseDouble(data.getRow(i).getField(metric.name));
                /**
                 * UNDER CONSTRUCTION
                 * IT NEEDS TO INTERPOLATE THE DATA.
                 * 
                 */
            }
        }
        return Double.NEGATIVE_INFINITY;
    }
}
