/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import de.siegmar.fastcsv.reader.CsvContainer;
import iotsimulator.Structure.DataArff;
import iotsimulator.Structure.DataExchange;
import iotsimulator.Structure.DataExchangePair;
import iotsimulator.Structure.Device;
import iotsimulator.Structure.Metric;
import iotsimulator.Structure.TriggerCombination;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import scala.actors.threadpool.Arrays;
import weka.core.Instances;
import weka.core.Utils;
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

    public IOTSimulator parent;

    public static String NUMERIC = "numeric";
    public static String NOMINAL = "nominal";

    public String cSVFilePath;
//    public CsvReader cSVReader = new CsvReader();
    public ArrayList<String> types = new ArrayList();//THIS SHOULD ONLY BE USED FOR READING RAW CSV DATA! NOT FOR PROCESSING ALREADY READ DATA! METRIC HAS ITS TYPE, USE THAT ONE.
    public CsvContainer data;
    public DataArff arffData;
    public double correlations[][];

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
    
    public double minimalMetricGain=0.1;

    public MetricManager(IOTSimulator iOTSimulator) {
        parent = iOTSimulator;
    }

    public void setupTimeStamps() {
        for (int i = 0; i < data.getRowCount(); i++) {
            try {
                long newTime = convertToUnixEpochTime(data.getRow(i).getField(timeIndex), timeType, smallestTimeScale);
                timeStamps.add(newTime);
                data.rows.get(i).fields.set(timeIndex, String.valueOf(newTime));
            } catch (ParseException ex) {
                Logger.getLogger(MetricManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        startingTime = timeStamps.get(0);
        endingTime = timeStamps.get(timeStamps.size() - 1);
    }

    public long convertToUnixEpochTime(String inputTime, String type, String smallestStep) throws ParseException {
        long output = -1;
        switch (type) {
            case "Raw number":
                switch (smallestStep) {
                    case "Miliseconds":
                        output = Long.parseLong(inputTime);
                        break;
                    case "Seconds":
                        output = Long.parseLong(inputTime) * 1000;
                        break;
                    case "Minutes":
                        output = Long.parseLong(inputTime) * 1000 * 60;
                        break;
                    case "Hours":
                        output = Long.parseLong(inputTime) * 1000 * 60 * 60;
                        break;
                    case "Days":
                        output = Long.parseLong(inputTime) * 1000 * 60 * 60 * 24;
                        break;
                    default:
                        break;
                }

                break;
            case "Custom date format":
                SimpleDateFormat sdf = new SimpleDateFormat(customTimeFormat);
                Date date = sdf.parse(inputTime);
                output = date.getTime();
                break;
            default:
                break;
        }
        return output;
    }

    public double getMetricValue(Metric metric, long requestedTime) {
        for (int i = metric.lastRecordIndex; i < timeStamps.size(); i++) {
            if (timeStamps.get(i) >= requestedTime && !data.getRow(i).getField(metric.name).equals("NULL")) {
                metric.lastRecordIndex = i;
                return Double.parseDouble(data.getRow(i).getField(metric.name));
                /**
                 * UNDER CONSTRUCTION IT NEEDS TO INTERPOLATE THE DATA. IT NEEDS
                 * TO CHECK ***INTERPOLATION BUFFER***.
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
            int finalIndices[] = new int[metricNames.length];
            for (int i = 0; i < metricNames.length; i++) {
                finalIndices[i] = localData.attribute(metricNames[i]).index();
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

    /**
     * metricIndices could be null to indicate that all metrics are required.
     *
     * @param cSVDataInString
     * @param types
     * @param metricIndices
     * @param classIndex
     * @return
     */
    public DataArff generateInstancesFromString(String cSVDataInString, ArrayList<String> types, int metricIndices[], int classIndex) {

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
            if (metricIndices != null) {
                int finalIndices[];
                if (classIndex > -1) {
                    finalIndices = new int[metricIndices.length + 1];
                    for (int i = 0; i < metricIndices.length; i++) {
                        finalIndices[i] = metricIndices[i];
                    }
                    finalIndices[metricIndices.length] = classIndex;
                } else {
                    finalIndices = metricIndices;
                }
                Remove removeFilter = new Remove();
                removeFilter.setAttributeIndicesArray(finalIndices);
                removeFilter.setInvertSelection(true);
                removeFilter.setInputFormat(localData);
                localData = Filter.useFilter(localData, removeFilter);
            }
            if (classIndex > -1) {
                localData.setClassIndex(classIndex);
            }
            return new DataArff(localData);
        } catch (IOException ex) {
            Logger.getLogger(TriggerPredictor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TriggerPredictor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public DataArff generateInstancesFromCSVContainder(CsvContainer cSVDataInCSVContainer, ArrayList<String> types, int metricIndices[], int classIndex) {
        StringBuilder cSVInString = new StringBuilder();
        List<String> header = cSVDataInCSVContainer.getHeader();
        for (int i = 0; i < header.size(); i++) {
            cSVInString.append(header.get(i));
            if (i != header.size() - 1) {
                cSVInString.append(",");
            }
        }
        cSVInString.append(System.lineSeparator());
        for (int i = 0; i < cSVDataInCSVContainer.rows.size(); i++) {
            for (int j = 0; j < cSVDataInCSVContainer.rows.get(i).fields.size(); j++) {
                cSVInString.append(cSVDataInCSVContainer.rows.get(i).fields.get(j));
                if (j != cSVDataInCSVContainer.rows.get(i).fields.size() - 1) {
                    cSVInString.append(",");
                }
            }
            cSVInString.append(System.lineSeparator());
        }
        return generateInstancesFromString(cSVInString.toString(), types, metricIndices, classIndex);
    }

    /**
     * @param device
     * @param classMetricIndex
     * @return
     */
    public DataArff generateInstancesFromPredictionBufferForDevice(Device device) {
        StringBuilder cSVInString = new StringBuilder();
        Map<String, Integer> metricIndex = new HashMap();
        ArrayList<String> localTypes = new ArrayList();
        cSVInString.append("Time");
        localTypes.add(NUMERIC);
//        cSVInString.append(",");
        int effectiveMetrics = 0;
        for (int i = 0; i < device.metrics.size(); i++) {
            if (device.metrics.get(i).predictionBuffer.size() > 0) {
                cSVInString.append(",");
                cSVInString.append(device.metrics.get(i).name);
                localTypes.add(device.metrics.get(i).type);
                metricIndex.put(device.metrics.get(i).name, effectiveMetrics);
                effectiveMetrics = effectiveMetrics + 1;
            } else {

            }
        }
        cSVInString.append(System.lineSeparator());
        ArrayList<DataExchange> allDataArrayList = new ArrayList();
        for (int i = 0; i < device.metrics.size(); i++) {
            for (int j = 0; j < device.metrics.get(i).predictionBuffer.size(); j++) {
                allDataArrayList.add(device.metrics.get(i).predictionBuffer.get(j));
            }
        }
        DataExchange allDataArray[] = allDataArrayList.toArray(new DataExchange[allDataArrayList.size()]);
        Arrays.sort(allDataArray);

        for (int i = 0; i < allDataArray.length; i++) {
            String row[] = new String[effectiveMetrics];
            Integer metricIndexRetreived = metricIndex.get(allDataArray[i].metric.name);
            row[metricIndexRetreived] = allDataArray[i].message;
            for (int j = i + 1; j < allDataArray.length; j++) {
                if (j < allDataArray.length - 1) {
                    if (allDataArray[j].time == allDataArray[i].time) {
                        metricIndexRetreived = metricIndex.get(allDataArray[j].metric.name);
                        row[metricIndexRetreived] = allDataArray[j].message;
                        i = j;
                    } else {
                        break;
                    }
                }
            }
            cSVInString.append(allDataArray[i].time);
//            cSVInString.append(",");
            for (int k = 0; k < row.length; k++) {
                if (row[k] == null) {
                    cSVInString.append(",");
                    cSVInString.append("?");
                } else {
                    cSVInString.append(",");
                    cSVInString.append(row[k]);
                }
                if (k != row.length - 1) {
//                    cSVInString.append(",");
                }
            }
            cSVInString.append(System.lineSeparator());
        }
        return generateInstancesFromString(cSVInString.toString(), localTypes);
    }

    public void generateCorrelationMatrix() {
        if (arffData != null) {
            correlations = getGlobalCorrelationMatrix(arffData.instances);
        }
    }

    public double[][] getGlobalCorrelationMatrix(Instances data) {

        double output[][] = new double[data.numAttributes()][data.numAttributes()];
        for (int i = 0; i < data.numAttributes(); i++) {
            for (int j = 0; j < data.numAttributes(); j++) {
                double attOne[] = convertAttributeToArray(data, i);
                double attTwo[] = convertAttributeToArray(data, j);
                output[i][j] = Utils.correlation(attOne, attTwo, attTwo.length);
            }
        }
        return output;
    }

    public DataArff interpolateInstances(DataArff input, int timeIndex) {
        for (int i = 0; i < input.instances.numAttributes(); i++) {
            if (input.instances.attribute(i).isNumeric()) {
                input.instances = linearNumericInterpolator(input.instances, timeIndex, i);
            }
            if (input.instances.attribute(i).isNominal()) {
                input.instances = nearestNominalInterpolator(input.instances, timeIndex, i);
            }
        }
        return input;
    }

    public double[] convertAttributeToArray(Instances data, int attributeIndex) {
        double output[] = new double[data.numInstances()];
        for (int i = 0; i < output.length; i++) {
            output[i] = data.get(i).value(attributeIndex);
        }
        return output;
    }

    public Instances linearNumericInterpolator(Instances input, int timeIndex, int attr) {
        if (attr != timeIndex) {
            long firstValidTime = -1;
            long secondValidTime = -1;
            double fistValidValue = -1;
            double secondValue = -1;
            boolean hasMissing = false;
            ArrayList<Integer> missingArrayIndices = new ArrayList();
            ArrayList<Long> missingArrayTimes = new ArrayList();
            for (int record = 0; record < input.numInstances(); record++) {
                if (input.get(record).isMissing(attr) == false && hasMissing == false) {
                    firstValidTime = (long) input.get(record).value(timeIndex);
                    fistValidValue = input.get(record).value(attr);
                }
                if (input.get(record).isMissing(attr) == true) {
                    hasMissing = true;
                    missingArrayIndices.add(record);
                    missingArrayTimes.add((long) input.get(record).value(timeIndex));
                }
                if (input.get(record).isMissing(attr) == false && hasMissing == true) {
                    secondValidTime = (long) input.get(record).value(timeIndex);
                    secondValue = input.get(record).value(attr);
                    if (firstValidTime > -1) {
                        long timeDifference = secondValidTime - firstValidTime;
                        double valueDifference = secondValue - fistValidValue;
                        for (int i = 0; i < missingArrayTimes.size(); i++) {
                            long recordTimeDifference = missingArrayTimes.get(i) - firstValidTime;
                            double newValue = (double) fistValidValue + (((double) recordTimeDifference / (double) timeDifference) * (double) valueDifference);
                            input.get(missingArrayIndices.get(i)).setValue(attr, newValue);
                        }
                    } else {
                        for (int i = 0; i < missingArrayTimes.size(); i++) {
                            input.get(missingArrayIndices.get(i)).setValue(attr, secondValue);
                        }
                    }
                    hasMissing = false;
                    firstValidTime = (long) input.get(record).value(timeIndex);
                    fistValidValue = input.get(record).value(attr);
                    missingArrayIndices.clear();
                    missingArrayTimes.clear();
                }
            }
            for (int i = 0; i < missingArrayTimes.size(); i++) {
                input.get(missingArrayIndices.get(i)).setValue(attr, fistValidValue);
            }
        }
        return input;
    }

    /**
     *
     * @param inputX time values for high frequency metric to build model
     * @param inputY message values for high frequency metric to build model
     * @param unknownTimeIndices low frequency time values to predict for high
     * frequency signal model
     * @return
     * @throws RejectedExecutionException
     * @throws InterruptedException
     * @throws EngineException
     * @throws ExecutionException
     */
    public double[] matlabKriggingInterpolate(double inputX[], double inputY[], double unknownTimeIndices[]) throws RejectedExecutionException, InterruptedException, EngineException, ExecutionException {
        MatlabEngine eng = MatlabEngine.startMatlab();
        String folder = "MatlabKrigingCode";
        String oldFolder = eng.feval("cd", folder);
        double interpolates[] = eng.feval("CallMatlabKrigging", inputX, inputY, unknownTimeIndices);
        for (int i = 0; i < interpolates.length; i++) {
            System.out.println(interpolates[i]);
        }
        eng.feval("cd", oldFolder);
        eng.close();

        return interpolates;
    }

    public Instances nearestNominalInterpolator(Instances input, int timeIndex, int attr) {
        if (attr != timeIndex) {
            long firstValidTime = -1;
            long secondValidTime = -1;
            double fistValidValue = -1;
            double secondValue = -1;
            boolean hasMissing = false;
            ArrayList<Integer> missingArrayIndices = new ArrayList();
            ArrayList<Long> missingArrayTimes = new ArrayList();
            for (int record = 0; record < input.numInstances(); record++) {
                if (input.get(record).isMissing(attr) == false && hasMissing == false) {
                    firstValidTime = (long) input.get(record).value(timeIndex);
                    fistValidValue = input.get(record).value(attr);
                }
                if (input.get(record).isMissing(attr)) {
                    hasMissing = true;
                    missingArrayIndices.add(record);
                    missingArrayTimes.add((long) input.get(record).value(timeIndex));
                }
                if (input.get(record).isMissing(attr) == false && hasMissing == true) {
                    secondValidTime = (long) input.get(record).value(timeIndex);
                    secondValue = input.get(record).value(attr);
                    if (firstValidTime > -1) {
                        if (fistValidValue == secondValue) {
                            for (int i = 0; i < missingArrayTimes.size(); i++) {
                                input.get(missingArrayIndices.get(i)).setValue(attr, fistValidValue);
                            }
                        } else {
                            double dummyFirstValue = 0;
                            double dummySecondValue = 1;
                            long timeDifference = secondValidTime - firstValidTime;
                            double valueDifference = dummySecondValue - dummyFirstValue;
                            for (int i = 0; i < missingArrayTimes.size(); i++) {
                                long recordTimeDifference = missingArrayTimes.get(i) - firstValidTime;
                                double newValue = dummyFirstValue + (((double) recordTimeDifference / (double) timeDifference) * (double) valueDifference);
                                double roundedNewValue = Math.round(newValue);
                                if (roundedNewValue == 0) {
                                    input.get(missingArrayIndices.get(i)).setValue(attr, fistValidValue);
                                } else {
                                    input.get(missingArrayIndices.get(i)).setValue(attr, secondValue);
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < missingArrayTimes.size(); i++) {
                            input.get(missingArrayIndices.get(i)).setValue(attr, secondValue);
                        }
                    }

                    hasMissing = false;
                    firstValidTime = (long) input.get(record).value(timeIndex);
                    fistValidValue = input.get(record).value(attr);
                    missingArrayIndices.clear();
                    missingArrayTimes.clear();
                }
            }
            for (int i = 0; i < missingArrayTimes.size(); i++) {
                input.get(missingArrayIndices.get(i)).setValue(attr, fistValidValue);
            }
        }
        return input;
    }

    public double[] setMetricWeights(TriggerCombination input) {
        HashMap<Metric, Double> weights = new HashMap();
        for (int i = 0; i < selectedMetrics.size(); i++) {
            weights.put(selectedMetrics.get(i), 0.0);
        }
        double correlation[][] = getCorrelationMatrix();
        for (int i = 0; i < input.isTriggered.length; i++) {
            if (input.isTriggered[i].isActivated) {
                for (int j = 0; j < input.isTriggered[i].trigger.metrics.size(); j++) {
                    weights.replace(input.isTriggered[i].trigger.metrics.get(j), weights.get(input.isTriggered[i].trigger.metrics.get(j)) + 1);
                    int triggeringMetricIndex;
                    for (int k = 0; k < selectedMetrics.size(); k++) {
                        if (input.isTriggered[i].trigger.metrics.get(j).name.equals(selectedMetrics.get(k).name)) {
                            triggeringMetricIndex = k;
                            for (int m = 0; m < selectedMetrics.size(); m++) {
                                if (triggeringMetricIndex != m) {
                                    weights.replace(selectedMetrics.get(m), weights.get(selectedMetrics.get(m)) + correlation[triggeringMetricIndex][m]);
                                }
                            }
                            break;
                        }
                    }
                }
            } else if (input.isTriggered[i].isDeactivated) {
                for (int j = 0; j < input.isTriggered[i].trigger.metrics.size(); j++) {
                    weights.replace(input.isTriggered[i].trigger.metrics.get(j), weights.get(input.isTriggered[i].trigger.metrics.get(j)) - 1);
                    int detriggeringMetricIndex;
                    for (int k = 0; k < selectedMetrics.size(); k++) {
                        if (input.isTriggered[i].trigger.metrics.get(j).name.equals(selectedMetrics.get(k).name)) {
                            detriggeringMetricIndex = k;
                            for (int m = 0; m < selectedMetrics.size(); m++) {
                                if (detriggeringMetricIndex != m) {
                                    weights.replace(selectedMetrics.get(m), weights.get(selectedMetrics.get(m)) - correlation[detriggeringMetricIndex][m]);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

        double output[] = new double[selectedMetrics.size()];
        for (int i = 0; i < selectedMetrics.size(); i++) {
            output[i]=weights.get(selectedMetrics.get(i));
        }
        output=normalize(output,minimalMetricGain,1);
        return output;
    }
    
    private double[] normalize(double input[],double min,double max)
    {
        double minimumValue=input[0];
        double maximumValue=input[0];
        for(int i=1;i<input.length;i++)
        {
            if(input[i]<minimumValue)
            {
                minimumValue=input[i];
            }
            if(input[i]>maximumValue)
            {
                maximumValue=input[i];
            }
        }
        for(int i=0;i<input.length;i++)
        {
            input[i]=input[i]-minimumValue;
            input[i]=input[i]/(maximumValue-minimumValue);
            input[i]=input[i]*(max-min);
            input[i]=input[i]+min;
        }
        return input;
    }

    public double[][] getCorrelationMatrix() {
        double output[][] = new double[selectedMetrics.size()][selectedMetrics.size()];
        for (int i = 0; i < selectedMetrics.size(); i++) {
            output[i][i] = 1;
            for (int j = i + 1; j < selectedMetrics.size(); j++) {
                if (selectedMetrics.get(i).interpolationBuffer.size() > 1 && selectedMetrics.get(j).interpolationBuffer.size() > 1) {
                    ArrayList<DataExchangePair> pairedPoints = getDataPair(selectedMetrics.get(i), selectedMetrics.get(j));
                    if (pairedPoints == null) {
                        output[i][j] = 0;
                    } else {
                        output[i][j] = getPearsonCorrelationValue(pairedPoints);
                        output[j][i] = output[i][j];
                    }
                } else {
                    output[i][j] = 0;
                }
            }
        }
        return output;
    }

    public ArrayList<DataExchangePair> getDataPair(Metric metricOne, Metric metricTwo) {
        ArrayList<DataExchangePair> output = new ArrayList();
        Metric highFreq;
        Metric lowFreq;
        if (metricOne.frequency < metricTwo.frequency) {
            highFreq = metricTwo;
            lowFreq = metricOne;
        } else {
            highFreq = metricOne;
            lowFreq = metricTwo;
        }

        long highFreqMaxTime = highFreq.interpolationBuffer.get(highFreq.interpolationBuffer.size() - 1).time;
        long highFreqMinTime = highFreq.interpolationBuffer.get(0).time;
        ArrayList<Double> unknownLowFreqTimeIndicesArrayList = new ArrayList();
        ArrayList<Double> highFrqXArrayList = new ArrayList();
        ArrayList<Double> highFrqYArrayList = new ArrayList();
        for (int i = 0; i < lowFreq.interpolationBuffer.size(); i++) {
            if (lowFreq.interpolationBuffer.get(i).time >= highFreqMinTime && lowFreq.interpolationBuffer.get(i).time <= highFreqMaxTime) {
                unknownLowFreqTimeIndicesArrayList.add((double) lowFreq.interpolationBuffer.get(i).time);
                output.add(new DataExchangePair(null, lowFreq.interpolationBuffer.get(i)));
            }
        }
        if (unknownLowFreqTimeIndicesArrayList.size() < 2) {
            return null;
        }
        for (int i = 0; i < highFreq.interpolationBuffer.size(); i++) {
            highFrqXArrayList.add((double) highFreq.interpolationBuffer.get(i).time);
            highFrqYArrayList.add(Double.valueOf(highFreq.interpolationBuffer.get(i).message));
        }
        double unknownLowFreqTimeIndices[] = unknownLowFreqTimeIndicesArrayList.stream().mapToDouble(Double::doubleValue).toArray();
        double highFrqX[] = highFrqXArrayList.stream().mapToDouble(Double::doubleValue).toArray();
        double highFrqY[] = highFrqYArrayList.stream().mapToDouble(Double::doubleValue).toArray();
        try {
            double[] predictions = matlabKriggingInterpolate(highFrqX, highFrqY, unknownLowFreqTimeIndices);
            for (int i = 0; i < predictions.length; i++) {
                output.get(i).highFrequency = new DataExchange(highFreq.interpolationBuffer.get(0).fromDevice, highFreq.interpolationBuffer.get(0).toDevice, (long) unknownLowFreqTimeIndices[i], String.valueOf(predictions[i]), highFreq);
            }
        } catch (RejectedExecutionException ex) {
            Logger.getLogger(MetricManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MetricManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MetricManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    private double getPearsonCorrelationValue(ArrayList<DataExchangePair> pairedPoints) {
        int N = pairedPoints.size();
        double sigmaX = 0;
        double sigmaY = 0;
        double sigmaXY = 0;
        double sigmaX2 = 0;
        double sigmaY2 = 0;
        for (int i = 0; i < pairedPoints.size(); i++) {
            sigmaX = sigmaX + Double.valueOf(pairedPoints.get(i).highFrequency.message);
            sigmaY = sigmaY + Double.valueOf(pairedPoints.get(i).lowFrequency.message);
            sigmaX2 = sigmaX2 + Math.pow(Double.valueOf(pairedPoints.get(i).highFrequency.message), 2);
            sigmaY2 = sigmaY2 + Math.pow(Double.valueOf(pairedPoints.get(i).lowFrequency.message), 2);
            sigmaXY = sigmaXY + Double.valueOf(pairedPoints.get(i).highFrequency.message) * Double.valueOf(pairedPoints.get(i).lowFrequency.message);
        }
        double r = ((N * sigmaXY) - (sigmaX * sigmaY)) / (Math.sqrt(((N * sigmaX2) - Math.pow(sigmaX, 2)) * ((N * sigmaY2) - Math.pow(sigmaY, 2))));
        return r;
    }

}
