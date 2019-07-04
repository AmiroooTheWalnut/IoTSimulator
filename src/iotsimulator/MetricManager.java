/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import de.siegmar.fastcsv.reader.CsvContainer;
import iotsimulator.Structure.Metric;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class MetricManager implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public String cSVFilePath;
//    public CsvReader cSVReader = new CsvReader();
    public CsvContainer data;
    public int timeIndex=-1;
    public int metricIndices[];
    public ArrayList<Metric> availableMetrics=new ArrayList();
    public ArrayList<Metric> selectedMetrics=new ArrayList();
    
    public String smallestTimeScale;
    public String timeType;
    
    public void getMetricValue(Metric metric,int currentTime)
    {
        
    }
}
