/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import de.siegmar.fastcsv.reader.CsvContainer;
import iotsimulator.Structure.DataArff;
import iotsimulator.Structure.Metric;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;

/**
 *
 * @author user
 */
public class MetricManager implements Serializable {

    static final long serialVersionUID = 1L;

    public static String NUMERIC="numeric";
    public static String NOMINAL="nominal";
    
    public String cSVFilePath;
//    public CsvReader cSVReader = new CsvReader();
    public ArrayList<String> types;
    public CsvContainer data;
    public DataArff arffData;

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
    
    public String generators;
    public String exported;
    public String dataGenerationConfig;

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
            if(timeStamps.get(i)>requestedTime && !data.getRow(i).getField(metric.name).equals("NULL"))
            {
                metric.lastRecordIndex=i;
                return Double.parseDouble(data.getRow(i).getField(metric.name));
                /**
                 * UNDER CONSTRUCTION
                 * IT NEEDS TO INTERPOLATE THE DATA.
                 * IT NEEDS TO CHECK ***INTERPOLATION BUFFER***.
                 */
            }
        }
        return Double.NEGATIVE_INFINITY;
    }
    
    public DataArff generateInstancesFromCSV(String cSVPath, ArrayList<String> types) {

        try {
            CSVLoader csvLoader = new CSVLoader();
            csvLoader.setSource(new File(cSVPath));
            Instances localData = csvLoader.getDataSet();
            NumericToNominal filter = new NumericToNominal();
            ArrayList<Integer> indicesList = new ArrayList();
            for (int i = 0; i < types.size(); i++) {
                if (types.get(i).equals(MetricManager.NOMINAL)) {
                    indicesList.add(i);
                }
            }
            if (indicesList.size() > 0) {
                int[] indices = new int[indicesList.size()];

                for (int i = 0; i < indicesList.size(); i++) {
                    if (indicesList.get(i) != null) {
                        indices[i] = indicesList.get(i);
                    }
                }
                filter.setAttributeIndicesArray(indices);
                filter.setInputFormat(localData);
                localData = Filter.useFilter(localData, filter);
            }
            return new DataArff(localData);
        } catch (IOException ex) {
            Logger.getLogger(TriggerPredictor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TriggerPredictor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public DataArff generateInstancesFromCSV(String cSVPath, ArrayList<String> types, String metricNames[]) {

        try {
            CSVLoader csvLoader = new CSVLoader();
            csvLoader.setSource(new File(cSVPath));
            Instances localData = csvLoader.getDataSet();
            NumericToNominal filter = new NumericToNominal();
            ArrayList<Integer> indicesList = new ArrayList();
            for (int i = 0; i < types.size(); i++) {
                if (types.get(i).equals(MetricManager.NOMINAL)) {
                    indicesList.add(i);
                }
            }
            if (indicesList.size() > 0) {
                int[] indices = new int[indicesList.size()];

                for (int i = 0; i < indicesList.size(); i++) {
                    if (indicesList.get(i) != null) {
                        indices[i] = indicesList.get(i);
                    }
                }
                filter.setAttributeIndicesArray(indices);
                filter.setInputFormat(localData);
                localData = Filter.useFilter(localData, filter);
            }
            int finalIndices[]=new int[metricNames.length];
            for(int i=0;i<metricNames.length;i++)
            {
                finalIndices[i]=localData.attribute(metricNames[i]).index();
            }
            for(int i=0;i<localData.numAttributes();i++)
            {
                System.out.println(localData.attribute(i));
            }
            System.out.println("!!!");
            Remove removeFilter = new Remove();
            removeFilter.setAttributeIndicesArray(finalIndices);
            removeFilter.setInvertSelection(true);
            removeFilter.setInputFormat(localData);
            localData = Filter.useFilter(localData, removeFilter);
            for(int i=0;i<localData.numAttributes();i++)
            {
                System.out.println(localData.attribute(i));
            }
            return new DataArff(localData);
        } catch (IOException ex) {
            Logger.getLogger(TriggerPredictor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TriggerPredictor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public DataArff generateInstancesFromString(String cSVDataInString, ArrayList<String> types) {

        try {
            CSVLoader csvLoader = new CSVLoader();
            InputStream stream = new ByteArrayInputStream(cSVDataInString.getBytes(StandardCharsets.UTF_8));
            csvLoader.setSource(stream);
            Instances localData = csvLoader.getDataSet();
            NumericToNominal filter = new NumericToNominal();
            ArrayList<Integer> indicesList = new ArrayList();
            for (int i = 0; i < types.size(); i++) {
                if (types.get(i).equals(MetricManager.NOMINAL)) {
                    indicesList.add(i);
                }
            }
            if (indicesList.size() > 0) {
                int[] indices = new int[indicesList.size()];

                for (int i = 0; i < indicesList.size(); i++) {
                    if (indicesList.get(i) != null) {
                        indices[i] = indicesList.get(i);
                    }
                }
                filter.setAttributeIndicesArray(indices);
                filter.setInputFormat(localData);
                localData = Filter.useFilter(localData, filter);
            }
            return new DataArff(localData);
        } catch (IOException ex) {
            Logger.getLogger(TriggerPredictor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TriggerPredictor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public DataArff generateInstancesFromString(String cSVDataInString, ArrayList<String> types, int metricIndices[],int classIndex) {

        try {
            CSVLoader csvLoader = new CSVLoader();
            InputStream stream = new ByteArrayInputStream(cSVDataInString.getBytes(StandardCharsets.UTF_8));
            csvLoader.setSource(stream);
            Instances localData = csvLoader.getDataSet();
            NumericToNominal filter = new NumericToNominal();
            ArrayList<Integer> indicesList = new ArrayList();
            for (int i = 0; i < types.size(); i++) {
                if (types.get(i).equals(MetricManager.NOMINAL)) {
                    indicesList.add(i);
                }
            }
            if (indicesList.size() > 0) {
                int[] indices = new int[indicesList.size()];

                for (int i = 0; i < indicesList.size(); i++) {
                    if (indicesList.get(i) != null) {
                        indices[i] = indicesList.get(i);
                    }
                }
                filter.setAttributeIndicesArray(indices);
                filter.setInputFormat(localData);
                localData = Filter.useFilter(localData, filter);
            }
            int finalIndices[];
            if(classIndex>-1)
            {
                finalIndices=new int[metricIndices.length+1];
                for(int i=0;i<metricIndices.length;i++)
                {
                    finalIndices[i]=metricIndices[i];
                }
                finalIndices[metricIndices.length]=classIndex;
            }else{
                finalIndices=metricIndices;
            }
            Remove removeFilter = new Remove();
            removeFilter.setAttributeIndicesArray(finalIndices);
            removeFilter.setInvertSelection(true);
            removeFilter.setInputFormat(localData);
            localData = Filter.useFilter(localData, removeFilter);
            return new DataArff(localData);
        } catch (IOException ex) {
            Logger.getLogger(TriggerPredictor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TriggerPredictor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
