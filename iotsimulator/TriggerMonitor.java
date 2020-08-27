/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import iotsimulator.Structure.DataArff;
import iotsimulator.Structure.DataExchange;
import iotsimulator.Structure.Device;
import iotsimulator.Structure.FrequencySolution;
import iotsimulator.Structure.Metric;
import iotsimulator.Structure.Trigger;
import iotsimulator.Structure.TriggerCombination;
import iotsimulator.Structure.TriggerModel;
import iotsimulator.Structure.TriggerState;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.ParallelIteratedSingleClassifierEnhancer;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 *
 * @author user
 */
public class TriggerMonitor implements Serializable {

    static final long serialVersionUID = 1L;

    public IOTSimulator parent;

    public double memoryUsageForActivity = 1;
    public double cPUUsageForActivity = 1;
    public double storageUsageForActivity = 1;

    public ArrayList<Trigger> triggers = new ArrayList();

    public TriggerMonitor(IOTSimulator iOTSimulator) {
        parent = iOTSimulator;
    }

    public TriggerModel makeModel(DataArff data, Classifier model, int numCPUs) {
        try {
            if (model instanceof ParallelIteratedSingleClassifierEnhancer) {
                ((ParallelIteratedSingleClassifierEnhancer) model).setNumExecutionSlots(numCPUs);
            }
            model.buildClassifier(data.instances);
            TriggerModel output = new TriggerModel();
            output.model = model;
            output.headerNames = new String[data.instances.numAttributes()];
            for (int i = 0; i < data.instances.numAttributes(); i++) {
                output.headerNames[i] = data.instances.attribute(i).name();
            }
            String tempString = data.instances.toString();
            output.readyHeader = tempString.substring(0, tempString.indexOf("@data"));
            output.classIndex=data.instances.classIndex();
            return output;
        } catch (Exception ex) {
            Logger.getLogger(TriggerMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public TriggerModel[] makeModelForEachAttribute(DataArff data, ArrayList<Metric> selectedMetrics, String className, String timeName, int numCPUs) {
        try {
            TriggerModel[] output = new TriggerModel[selectedMetrics.size()];
            for (int i = 0; i < selectedMetrics.size(); i++) {
                RandomForest model = new RandomForest();
                if (model instanceof ParallelIteratedSingleClassifierEnhancer) {
                    ((ParallelIteratedSingleClassifierEnhancer) model).setNumExecutionSlots(numCPUs);
                }
                Instances cutData = new Instances(data.instances);
                Remove removeFilter = new Remove();
                int removingAttributes[] = new int[data.instances.numAttributes() - 2];
                int counter = 0;
                for (int j = 0; j < data.instances.numAttributes(); j++) {
                    if (!data.instances.attribute(j).name().equals(selectedMetrics.get(i).name) && !data.instances.attribute(j).name().equals(className) && !data.instances.attribute(j).name().equals(timeName)) {
                        removingAttributes[counter] = j;
                        counter = counter + 1;
                    }
                }
                removeFilter.setAttributeIndicesArray(removingAttributes);
                removeFilter.setInputFormat(data.instances);
                cutData = Filter.useFilter(cutData, removeFilter);
                cutData.setClass(cutData.attribute(className));
                model.buildClassifier(cutData);
                output[i].model = model;

                output[i].headerNames = new String[cutData.numAttributes()];
                for (int j = 0; j < cutData.numAttributes(); j++) {
                    output[i].headerNames[j] = cutData.attribute(j).name();
                }
                String tempString = cutData.toString();
                output[i].readyHeader = tempString.substring(0, tempString.indexOf("@data"));
                output[i].classIndex=cutData.classIndex();

            }
            return output;
        } catch (Exception ex) {
            Logger.getLogger(TriggerMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * By using this function, the device will not blocked for checking triggers
     * Therefore, it always detects trigger with delay.
     *
     * @param message
     */
    public void checkTriggersParallel(DataExchange message) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TriggerState triggerStateArray[] = new TriggerState[triggers.size()];
                boolean needsModelBeSolved = false;
                for (int i = 0; i < triggers.size(); i++) {
                    TriggerState triggerState = triggers.get(i).isSensingTriggered();
                    triggerStateArray[i] = triggerState;
                    if (triggerState.isActivated) {
                        needsModelBeSolved = true;
                        message.metric.triggerBuffer.add(triggerState);
                        message.toDevice.triggerConsole.append("Trigger: Activated");
                        message.toDevice.triggerConsole.append("Time: ").append(message.time).append(" ");
                        message.toDevice.triggerConsole.append("Metrices: ");
                        for (int j = 0; j < triggers.get(i).metrics.size(); j++) {
                            message.toDevice.triggerConsole.append(triggers.get(i).metrics.get(j).name).append(" ");
                        }
                        message.toDevice.triggerConsole.append("Type: ").append(triggers.get(i).type);

                        message.toDevice.triggerConsole.append(System.lineSeparator());

                        if (message.toDevice.triggerConsole.length() > message.toDevice.maxConsoleSize) {
                            message.toDevice.triggerConsole.delete(0, 100);
                        }
                    } else if (triggerState.isDeactivated) {
                        needsModelBeSolved = true;
                        message.metric.triggerBuffer.add(triggerState);
                        message.toDevice.triggerConsole.append("Trigger: Deactivated");
                        message.toDevice.triggerConsole.append("Time: ").append(message.time).append(" ");
                        message.toDevice.triggerConsole.append("Metrices: ");
                        for (int j = 0; j < triggers.get(i).metrics.size(); j++) {
                            message.toDevice.triggerConsole.append(triggers.get(i).metrics.get(j).name).append(" ");
                        }
                        message.toDevice.triggerConsole.append("Type: ").append(triggers.get(i).type);

                        message.toDevice.triggerConsole.append(System.lineSeparator());

                        if (message.toDevice.triggerConsole.length() > message.toDevice.maxConsoleSize) {
                            message.toDevice.triggerConsole.delete(0, 100);
                        }
                    } else {
                        message.metric.triggerBuffer.add(triggerState);
                    }
                }
                if (needsModelBeSolved == true) {
                    TriggerCombination problem = new TriggerCombination(triggerStateArray);
                    FrequencySolution solution = parent.solutionStorageManager.getSolution(problem);
                    if (solution == null) {
                        parent.optimizer.solveModelOnDemand(problem);
                    }
                }
            }
        });
        thread.start();
    }

    /**
     * This function is blocking trigger checking.
     *
     * @param message
     */
    public void checkSensingTriggersBlocking(DataExchange message) {
        TriggerState triggerStateArray[] = new TriggerState[triggers.size()];
        boolean needsModelBeSolved = false;
        for (int i = 0; i < triggers.size(); i++) {
            TriggerState triggerState = triggers.get(i).isSensingTriggered();
            triggerStateArray[i] = triggerState;
            if (triggerState.isActivated) {
                needsModelBeSolved = true;
                message.metric.triggerBuffer.add(triggerState);
                message.toDevice.triggerConsole.append("Trigger: Activated");
                message.toDevice.triggerConsole.append("Time: ").append(message.time).append(" ");
                message.toDevice.triggerConsole.append("Metrices: ");
                for (int j = 0; j < triggers.get(i).metrics.size(); j++) {
                    message.toDevice.triggerConsole.append(triggers.get(i).metrics.get(j).name).append(" ");
                }
                message.toDevice.triggerConsole.append("Type: ").append(triggers.get(i).type);

                message.toDevice.triggerConsole.append(System.lineSeparator());

                if (message.toDevice.triggerConsole.length() > message.toDevice.maxConsoleSize) {
                    message.toDevice.triggerConsole.delete(0, 100);
                }
            } else if (triggerState.isDeactivated) {
                needsModelBeSolved = true;
                message.metric.triggerBuffer.add(triggerState);
                message.toDevice.triggerConsole.append("Trigger: Deactivated");
                message.toDevice.triggerConsole.append("Time: ").append(message.time).append(" ");
                message.toDevice.triggerConsole.append("Metrices: ");
                for (int j = 0; j < triggers.get(i).metrics.size(); j++) {
                    message.toDevice.triggerConsole.append(triggers.get(i).metrics.get(j).name).append(" ");
                }
                message.toDevice.triggerConsole.append("Type: ").append(triggers.get(i).type);

                message.toDevice.triggerConsole.append(System.lineSeparator());

                if (message.toDevice.triggerConsole.length() > message.toDevice.maxConsoleSize) {
                    message.toDevice.triggerConsole.delete(0, 100);
                }
            } else {
                message.metric.triggerBuffer.add(triggerState);
            }
        }
        if (needsModelBeSolved == true) {
            TriggerCombination problem = new TriggerCombination(triggerStateArray);
            FrequencySolution solution = parent.solutionStorageManager.getSolution(problem);
            if (solution == null) {
                message.toDevice.runOptimization(parent.optimizer, problem);
            }
        }
    }

    /**
     * This function is blocking trigger checking.
     *
     * @param device
     */
    public void checkPredictionTriggersBlocking(Device device) {
        ArrayList<TriggerState[]> triggerStateArrayListOfArray = new ArrayList();
        boolean needsModelBeSolved = false;
        for (int i = 0; i < triggers.size(); i++) {
            TriggerState triggerState[] = triggers.get(i).isPredictionTriggered(device.numberOfTimeStampsToPredict);
            triggerStateArrayListOfArray.add(triggerState);
        }
        int closestTrigger = findClosestTrigger(triggerStateArrayListOfArray);
        if (closestTrigger < Integer.MAX_VALUE) {
            needsModelBeSolved = true;
        }
        if (needsModelBeSolved == true) {
            TriggerCombination problem = new TriggerCombination(triggerStateArrayListOfArray.get(closestTrigger));
            FrequencySolution solution = parent.solutionStorageManager.getSolution(problem);
            if (solution == null) {
                parent.optimizer.solveModelOnPrediction(problem);
            }
        }
    }

    private int findClosestTrigger(ArrayList<TriggerState[]> triggers) {
        int closestIndexForTriggers = Integer.MAX_VALUE;
        for (int i = 0; i < triggers.size(); i++) {
            for (int j = 0; j < triggers.get(i).length; j++) {
                if (triggers.get(i)[j].isActivated || triggers.get(i)[j].isDeactivated) {
                    if (j < closestIndexForTriggers) {
                        closestIndexForTriggers = j;
                    }
                }
            }
        }
        return closestIndexForTriggers;
    }

}
