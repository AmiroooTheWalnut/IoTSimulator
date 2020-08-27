/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import iotsimulator.Structure.DataArff;
import iotsimulator.Structure.DataExchange;
import iotsimulator.Structure.Device;
import iotsimulator.Structure.Metric;
import iotsimulator.Structure.TriggerCombination;
import java.io.Serializable;
import java.util.List;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.timeseries.WekaForecaster;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.filters.supervised.attribute.TSLagMaker;

/**
 *
 * @author user
 */
public class TriggerPredictor implements Serializable {

    static final long serialVersionUID = 1L;

    public IOTSimulator parent;

    String type = "DM";
    Classifier classifier;
    String name;
    public double frequency = 1000;//MILLISECONDS AFTER GETTING IDLE

    static Double results[];

    public TriggerPredictor(IOTSimulator iOTSimulator) {
        parent = iOTSimulator;
    }

    private void setClassifier() {
//        classifier = new LinearRegression();
        classifier = new RandomForest();
    }

    public DataExchange[][] generateNextSensorValuesByFullPredict(DataArff realSensorValues, int numberOfPredictionSteps, int timeIndex) {
        if (type.equals("DM")) {
            setClassifier();
            DataExchange output[][];
            WekaForecaster forecaster = new WekaForecaster();
            TSLagMaker lagMaker = forecaster.getTSLagMaker();

            try {
                lagMaker.setTimeStampField(realSensorValues.instances.attribute(timeIndex).name());
//                lagMaker.setMinLag(1);
//                lagMaker.setMaxLag(12);

                String targetField = "";
                for (int i = 0; i < realSensorValues.instances.numAttributes(); i++) {
                    if (i != timeIndex) {
                        if (i == 0 || targetField.length() == 0) {
                            targetField = realSensorValues.instances.attribute(i).name();
                        } else {
                            targetField = targetField + "," + realSensorValues.instances.attribute(i).name();
                        }
                    }
                }

                forecaster.setBaseForecaster(classifier);
                forecaster.setFieldsToForecast(targetField);

                forecaster.buildForecaster(realSensorValues.instances);
                forecaster.primeForecaster(realSensorValues.instances);

                int numStepsToForecast = numberOfPredictionSteps;
                List<List<Prediction>> forecast = forecaster.forecast(numStepsToForecast);

                double timeDiffStep = lagMaker.getDeltaTime();
                
                double endTime=realSensorValues.instances.lastInstance().value(0);
                
                output = getPredictions(forecast,endTime,timeDiffStep,realSensorValues.instances,timeIndex);
                return output;
            } catch (Exception ex) {
                ex.printStackTrace();
                String msg = ex.getMessage().toLowerCase();
                if (msg.indexOf("not in classpath") > -1) {
                    return null;
                }
            }
        } else if (type.equals("ARMA")) {
//            try {
//                double[] dataArray = new double[currentMonthIndex];
//                for (int d = 0; d < dataArray.length; d++) {
//                    dataArray[d] = RealDemands.instance(d).value(2);
//                }
//
//                //System.out.println(arraylist.size());
//                ARIMA arima = new ARIMA(dataArray);
//
//                double range = 0;
//                double counter = 0;
//                for (int d = 0; d < 4; d++) {
//                    range = Math.abs(dataArray[dataArray.length - 1 - d] - dataArray[dataArray.length - 1 - d - 1]);
//                    counter = counter + 1;
//                }
//                range = range / counter;
//
//                int[] model = arima.getARIMAmodel();
//                return arima.aftDeal(arima.predictValue(model[0], model[1])) + range / 1.0;
//            } catch (Exception ex) {
//                return -1;
//            }
        }

        return null;
    }

//    public double[][] generateNextSensorValuesByOverlay(DataArff realSensorValues, int numberOfPredictionSteps, int timeIndex, int classIndex) {
//        if (type.equals("DM")) {
//            setClassifier();
//            double output[][];
//            WekaForecaster forecaster = new WekaForecaster();
//            TSLagMaker lagMaker = forecaster.getTSLagMaker();
//
//            try {
//                lagMaker.setTimeStampField(realSensorValues.instances.attribute(timeIndex).name());
////                lagMaker.setMinLag(1);
////                lagMaker.setMaxLag(12);
//
//                String overlayFields = "";
//                String targetField = "";
//                targetField = realSensorValues.instances.attribute(classIndex).name();
//                for (int i = 0; i < realSensorValues.instances.numAttributes(); i++) {
//                    if (i != classIndex) {
//                        if (i == 0) {
//                            overlayFields = realSensorValues.instances.attribute(i).name();
//                        } else {
//                            overlayFields = overlayFields + "," + realSensorValues.instances.attribute(i).name();
//                        }
//                    }
//                }
//
//                forecaster.setBaseForecaster(classifier);
//                forecaster.setOverlayFields(overlayFields);
//                forecaster.setFieldsToForecast(targetField);
//
//                forecaster.buildForecaster(realSensorValues.instances);
//
//                int numStepsToForecast = numberOfPredictionSteps;
//                List<List<Prediction>> forecast = forecaster.forecast(numStepsToForecast);
//
//                output=getPredictions(forecast,classIndex);
//                return output;
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                String msg = ex.getMessage().toLowerCase();
//                if (msg.indexOf("not in classpath") > -1) {
//                    return null;
//                }
//            }
//        } else if (type.equals("ARMA")) {
////            try {
////                double[] dataArray = new double[currentMonthIndex];
////                for (int d = 0; d < dataArray.length; d++) {
////                    dataArray[d] = RealDemands.instance(d).value(2);
////                }
////
////                //System.out.println(arraylist.size());
////                ARIMA arima = new ARIMA(dataArray);
////
////                double range = 0;
////                double counter = 0;
////                for (int d = 0; d < 4; d++) {
////                    range = Math.abs(dataArray[dataArray.length - 1 - d] - dataArray[dataArray.length - 1 - d - 1]);
////                    counter = counter + 1;
////                }
////                range = range / counter;
////
////                int[] model = arima.getARIMAmodel();
////                return arima.aftDeal(arima.predictValue(model[0], model[1])) + range / 1.0;
////            } catch (Exception ex) {
////                return -1;
////            }
//        }
//
//        return null;
//    }
    
    private DataExchange[][] getPredictions(List<List<Prediction>> preds, double lastTime, double timeDiffStep,Instances header,int timeIndex) {
        DataExchange output[][] = new DataExchange[preds.size()][preds.get(0).size()];

        for (int i = 0; i < preds.size(); i++) {
            int counter=0;
            for (int j = 0; j < preds.get(0).size(); j++) {
                String value=String.valueOf(preds.get(i).get(j).predicted());
                if(counter==timeIndex)
                {
                    counter=counter+1;
                }
                output[i][j]=new DataExchange(null,null,(long)(lastTime+(i+1)*timeDiffStep),value,findMetric(header.attribute(counter).name()));
                counter=counter+1;
            }
        }
        return output;
    }
    
    public Metric findMetric(String name)
    {
        for(int i=0;i<parent.metricManager.selectedMetrics.size();i++)
        {
            if(parent.metricManager.selectedMetrics.get(i).name.equals(name))
            {
                return parent.metricManager.selectedMetrics.get(i);
            }
        }
        return null;
    }
    
    public void predictTriggers(Device triggerMonitoringDevice) {
//        DataArff data = generateInstancesForDevice(triggerMonitoringDevice);
//        double predictedValues[][] = generateNextSensorValues(data, 10);
//        for (int i = 0; i < predictedValues.length; i++) {
//            parent.triggerMonitor.checkTriggers(new DataExchange());
//        }
    }

    public TriggerCombination getFutureTriggerCombination() {
        return null;
    }

}
