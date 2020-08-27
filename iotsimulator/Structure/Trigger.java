/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.CSVLoader;

/**
 *
 * @author user
 */
public class Trigger implements Serializable {

    static final long serialVersionUID = 1L;

    public String type;//HIGHER_THAN,LOWER_THAN,BETWEEN,MODEL
    public String name;
    public double threshold;
    public double thresholdHigh;
    public double thresholdLow;
    //public AbstractClassifier model;
    public static String HIGHER_THAN = "HIGHER_THAN";//ALL METRICS BE HIGHER THAN (&&) IF USER WANTS (||), SEPARATE TRIGGERS MUST BE USED
    public static String LESS_THAN = "LOWER_THAN";//ALL METRICS BE LESS THAN (&&) IF USER WANTS (||), SEPARATE TRIGGERS MUST BE USED
    public static String BETWEEN = "BETWEEN";//ALL METRICS BE BETWEEN (&&) IF USER WANTS (||), SEPARATE TRIGGERS MUST BE USED
    public static String MODEL = "MODEL";

    public ArrayList<Metric> metrics = new ArrayList();

    public int metricIndices[];

    public TriggerModel triggerModel;

    public boolean isTriggerActive = false;

    public TriggerState isSensingTriggered() {
        if (isTriggerActive == false && getSensingTriggerBoolean() == true) {
            isTriggerActive = true;
            return new TriggerState(this, true, false, false);
        } else if (isTriggerActive == true && getSensingTriggerBoolean() == false) {
            isTriggerActive = false;
            return new TriggerState(this, false, true, false);
        } else if (isTriggerActive == true && getSensingTriggerBoolean() == true) {
            isTriggerActive = true;
            return new TriggerState(this, false, false, true);
        } else if (isTriggerActive == false && getSensingTriggerBoolean() == false) {
            isTriggerActive = false;
            return new TriggerState(this, false, false, true);
        }
        return null;
    }

    public TriggerState[] isPredictionTriggered(int numSteps) {
        TriggerState triggerStates[] = new TriggerState[numSteps];
        for (int i = 0; i < numSteps; i++) {
            if (isTriggerActive == false && getPredictionTriggerBoolean(i) == true) {
                triggerStates[i] = new TriggerState(this, true, false, false);
            } else if (isTriggerActive == true && getPredictionTriggerBoolean(i) == false) {
                triggerStates[i] = new TriggerState(this, false, true, false);
            } else if (isTriggerActive == true && getPredictionTriggerBoolean(i) == true) {
                triggerStates[i] = new TriggerState(this, false, false, true);
            } else if (isTriggerActive == false && getPredictionTriggerBoolean(i) == false) {
                triggerStates[i] = new TriggerState(this, false, false, true);
            }
        }
        return triggerStates;
    }

    public boolean getSensingTriggerBoolean() {
        switch (type) {
            case "HIGHER_THAN":
                boolean isTriggered = false;
                for (int i = 0; i < metrics.size(); i++) {
                    if (metrics.get(i).interpolationBuffer.size() > 0) {
                        try {
                            double value = Double.valueOf(metrics.get(i).interpolationBuffer.get(metrics.get(i).interpolationBuffer.size() - 1).message);
                            if ((value > threshold)) {
                                isTriggered = true;
                            }
                        } catch (Exception e) {
                            System.out.println("A trigger has a value that can't be converted to double.");
                        }

                    }
                }
                return isTriggered;
            case "LOWER_THAN":
                isTriggered = false;
                for (int i = 0; i < metrics.size(); i++) {
                    if (metrics.get(i).interpolationBuffer.size() > 0) {
                        try {
                            double value = Double.valueOf(metrics.get(i).interpolationBuffer.get(metrics.get(i).interpolationBuffer.size() - 1).message);
                            if ((value < threshold)) {
                                isTriggered = true;
                            }
                        } catch (Exception e) {
                            System.out.println("A trigger has a value that can't be converted to double.");
                        }

                    }

                }
                return isTriggered;
            case "BETWEEN":
                isTriggered = false;
                for (int i = 0; i < metrics.size(); i++) {
                    if (metrics.get(i).interpolationBuffer.size() > 0) {
                        try {
                            double value = Double.valueOf(metrics.get(i).interpolationBuffer.get(metrics.get(i).interpolationBuffer.size() - 1).message);
                            if ((value < thresholdHigh && value > thresholdLow)) {
                                isTriggered = true;
                            }
                        } catch (Exception e) {
                            System.out.println("A trigger has a value that can't be converted to double.");
                        }
                    }
                }
                return isTriggered;
//NOT IMPLEMENTED YET
            case "MODEL":
                StringBuilder sb = new StringBuilder();
                sb.append(triggerModel.readyHeader + System.lineSeparator());
                sb.append("@data" + System.lineSeparator());

                String values[] = new String[triggerModel.headerNames.length];
                for (int j = 0; j < triggerModel.headerNames.length; j++) {
                    values[j] = new String();
                }

                for (int i = 0; i < metrics.size(); i++) {
                    if (metrics.get(i).interpolationBuffer.size() > 0) {
                        double value = Double.valueOf(metrics.get(i).interpolationBuffer.get(metrics.get(i).interpolationBuffer.size() - 1).message);
                        for (int j = 0; j < triggerModel.headerNames.length; j++) {
                            if (triggerModel.headerNames[j].equals(metrics.get(i).name)) {
                                values[j] = String.valueOf(value);
//                                newInstance.setValue(j, value);
                            }
                        }
                    }
                }
                for (int j = 0; j < triggerModel.headerNames.length; j++) {
                    if (values[j].length() > 0) {
                        sb.append(values[j]);
                    } else {
                        sb.append("?");
                    }
                    if (j != triggerModel.headerNames.length - 1) {
                        sb.append(",");
                    }
                }

                ArffLoader arffLoader = new ArffLoader();
                InputStream stream = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
                 {
                    try {
                        arffLoader.setSource(stream);
                        Instances localData = arffLoader.getDataSet();
                        localData.setClassIndex(triggerModel.classIndex);
                        double value;
                        value = triggerModel.model.classifyInstance(localData.firstInstance());
                        if (value == 0) {
                            return false;
                        } else if (value == 1) {
                            return true;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Trigger.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(Trigger.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

//                Instances instances = new Instances(triggerModel.header,0);
//                Instance newInstance=new DenseInstance(triggerModel.header.numAttributes());
//                for (int i = 0; i < metrics.size(); i++) {
//                    if (metrics.get(i).interpolationBuffer.size() > 0) {
//                        double value = Double.valueOf(metrics.get(i).interpolationBuffer.get(metrics.get(i).interpolationBuffer.size() - 1).message);
//                        for (int j = 0; j < triggerModel.header.numAttributes(); j++) {
//                            if (triggerModel.header.attribute(j).name().equals(metrics.get(i).name)) {
//                                newInstance.setValue(j, value);
//                            }
//                        }
//                    }
//                }
//                double value;
////                instances.add(newInstance);
//                try {
//                    value = triggerModel.model.classifyInstance(instances.firstInstance());
//                    if (value == 0) {
//                        return false;
//                    } else if (value == 1) {
//                        return true;
//                    }
//                } catch (Exception ex) {
//                    Logger.getLogger(Trigger.class.getName()).log(Level.SEVERE, null, ex);
//                }
                return false;
            default:
                return false;
        }
    }

    public boolean getPredictionTriggerBoolean(int index) {
        switch (type) {
            case "HIGHER_THAN":
                boolean isTriggered = false;
                for (int i = 0; i < metrics.size(); i++) {
                    if (metrics.get(i).predictedBuffer.size() > 0) {
                        try {
                            double value = Double.valueOf(metrics.get(i).predictedBuffer.get(index).message);
                            if ((value > threshold)) {
                                isTriggered = true;
                            }
                        } catch (Exception e) {
                            System.out.println("A trigger has a value that can't be converted to double.");
                        }

                    }
                }
                return isTriggered;
            case "LOWER_THAN":
                isTriggered = false;
                for (int i = 0; i < metrics.size(); i++) {
                    if (metrics.get(i).predictedBuffer.size() > 0) {
                        try {
                            double value = Double.valueOf(metrics.get(i).predictedBuffer.get(index).message);
                            if ((value < threshold)) {
                                isTriggered = true;
                            }
                        } catch (Exception e) {
                            System.out.println("A trigger has a value that can't be converted to double.");
                        }

                    }

                }
                return isTriggered;
            case "BETWEEN":
                isTriggered = false;
                for (int i = 0; i < metrics.size(); i++) {
                    if (metrics.get(i).predictedBuffer.size() > 0) {
                        try {
                            double value = Double.valueOf(metrics.get(i).predictedBuffer.get(index).message);
                            if ((value < thresholdHigh && value > thresholdLow)) {
                                isTriggered = true;
                            }
                        } catch (Exception e) {
                            System.out.println("A trigger has a value that can't be converted to double.");
                        }
                    }
                }
                return isTriggered;
            case "MODEL":
                for (int i = 0; i < metrics.size(); i++) {
                    if (metrics.get(i).predictedBuffer.size() > 0) {
                        double value = Double.valueOf(metrics.get(i).predictedBuffer.get(index).message);
//                        triggerModel.classifyInstance(instnc);
                    }
                }
//                triggerModel.classifyInstance(instnc);
                return false;//NOT IMPLEMENTED YET
            default:
                return false;
        }
    }

}
