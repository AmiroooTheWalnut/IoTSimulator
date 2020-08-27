/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import iotsimulator.IOTSimulator;
import iotsimulator.Optimizer;
import iotsimulator.TimeController;
import iotsimulator.TriggerMonitor;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;

/**
 *
 * @author user
 */
public class Device implements Serializable {

    static final long serialVersionUID = 1L;

    IOTSimulator rootSimulator;

    public long latency = 20;//Miliseconds //EACH DEVICE SHOULD HAVE UNIQUE LATENCY***REVISE LATER
    public long timeout = 1000;//Miliseconds trying to send to parent.
    public long predictionFrequency=1000;
    public int numberOfTimeStampsToPredict=5;

    public boolean isWaitForParentResponse = true;
    public long retrySendToParentInterval = 10;

    public String name = "UnnamedDevice";
    public TopologyLevel ownerTopology;
    public Device parentDevice;
    public ArrayList<Device> children = new ArrayList();
    public int parentDeviceIndex = -1;
    public ArrayList<Metric> metrics = new ArrayList();
    public double bandWidthCapacity = 100;
    public double memoryCapacity = 100;
    public double storageCapacity = 100;
    public double CPUCapacity = 100;
    public int metricIndices[];

    public double usedBandWidth = 0;
    public double usedMemory = 0;
    public double usedStorage = 0;
    public double usedCPU = 0;

    transient public StringBuilder signalConsole;
    transient public StringBuilder triggerConsole;

    transient public JSONArray readyJson;

    public ArrayList<DataExchange> sendingQueue = new ArrayList();
    public ArrayList<DataExchange> receivingQueue = new ArrayList();

    transient public int maxConsoleSize = 1000;

    public void predictNextMetric() {

    }
    
    public void runOptimization(Optimizer optimizer, TriggerCombination problem)
    {
        Thread thread=new Thread(new Runnable(){
            @Override
            public void run() {
                optimizer.solveModelOnDemand(problem);
            }
        });
        thread.start();
    }

    public void receiveMessageFromChild(DataExchange message) {
        receivingQueue.add(message);

        //temporary
        for (int i = 0; i < rootSimulator.triggerMonitor.triggers.size(); i++) {
            if (rootSimulator.triggerMonitor.triggers.get(i).getSensingTriggerBoolean()) {
//            needsModelBeSolved = true;
//            message.metric.triggerBuffer.add(triggerState);
            message.toDevice.triggerConsole.append("Trigger: Activated");
            message.toDevice.triggerConsole.append("Time: ").append(message.time).append(" ");
            message.toDevice.triggerConsole.append("Metrices: ");
            for (int j = 0; j < rootSimulator.triggerMonitor.triggers.get(i).metrics.size(); j++) {
                message.toDevice.triggerConsole.append(rootSimulator.triggerMonitor.triggers.get(i).metrics.get(j).name).append(" ");
            }
            message.toDevice.triggerConsole.append("Type: ").append(rootSimulator.triggerMonitor.triggers.get(i).type);

            message.toDevice.triggerConsole.append(System.lineSeparator());

            if (message.toDevice.triggerConsole.length() > message.toDevice.maxConsoleSize) {
                message.toDevice.triggerConsole.delete(0, 100);
            }
        }
        }
        

        //temporary
//        rootSimulator.triggerMonitor.checkTriggers(message);

        signalConsole.append("Received from: ");
        signalConsole.append(message.fromDevice.name).append(" ");
        signalConsole.append("Received to: ");
        signalConsole.append(message.toDevice.name).append(" ");
        signalConsole.append("Metric: ");
        signalConsole.append(message.metric.name).append(" ");
        signalConsole.append("Time: ");
        signalConsole.append(message.time).append(" ");
        signalConsole.append("Message: ");
        signalConsole.append(message.message);
        signalConsole.append(System.lineSeparator());

        if (signalConsole.length() > maxConsoleSize) {
            signalConsole.delete(0, 100);
        }

    }

    public void clearAllResources() {
        usedBandWidth = 0;
        usedMemory = 0;
        usedStorage = 0;
        usedCPU = 0;
        sendingQueue.clear();
        receivingQueue.clear();
    }

    public void clearResourcesOfMetric(Metric metric) {
        usedCPU = usedCPU - metric.cPUUsageForActivity;
        usedMemory = usedMemory - metric.memoryUsageForActivity;
        usedStorage = usedStorage - metric.storageUsageForActivity;
    }

    public void allocateResourcesToMetric(Metric metric) {
        usedCPU = usedCPU + metric.cPUUsageForActivity;
        usedMemory = usedMemory + metric.memoryUsageForActivity;
        usedStorage = usedStorage + metric.storageUsageForActivity;
    }

    public void allocateResourcesToTriggerMonitor(TriggerMonitor triggerMonitor) {
        usedCPU = usedCPU + triggerMonitor.cPUUsageForActivity;
        usedMemory = usedMemory + triggerMonitor.memoryUsageForActivity;
        usedStorage = usedStorage + triggerMonitor.storageUsageForActivity;
    }

    public boolean sendToParent(DataExchange message, TimeController timeController) {
        sendingQueue.add(message);
        Double messageValue = Double.valueOf(message.message);
        message.metric.interpolationBuffer.add(message);
        message.metric.predictionBuffer.add(message);
        if (message.metric.interpolationBuffer.size() > timeController.interpolationBufferSize) {
            message.metric.interpolationBuffer.remove(0);
        }
        if (message.metric.predictionBuffer.size() > timeController.predictionBufferSize) {
            message.metric.predictionBuffer.remove(0);
        }
        boolean successSenderResourceConsumption = message.consumeSenderResources();
        if (successSenderResourceConsumption == true) {
            boolean successReceiverResourceConsumption = message.consumeReceiverResources();
            if (successReceiverResourceConsumption == true) {
                message.setupSuccessReleaseResourceTimer();
                message.toDevice.receiveMessageFromChild(message);

                signalConsole.append("Sending from: ");
                signalConsole.append(message.fromDevice.name).append(" ");
                signalConsole.append("Sending to: ");
                signalConsole.append(message.toDevice.name).append(" ");
                signalConsole.append("Metric: ");
                signalConsole.append(message.metric.name).append(" ");
                signalConsole.append("Time: ");
                signalConsole.append(message.time).append(" ");
                signalConsole.append("Message: ");
                signalConsole.append(message.message);
                signalConsole.append(System.lineSeparator());

                if (signalConsole.length() > maxConsoleSize) {
                    signalConsole.delete(0, 100);
                }

            } else {
                message.setupRetryReceiverResourceConsumption();
                message.setupTimeoutReleaseResourceTimer();
            }
        } else {
            return false;
        }
        return false;
    }

    public void removeSendingDataExchange(DataExchange dataExchange) {
        sendingQueue.remove(dataExchange);
    }

    public void removeReceivingDataExchange(DataExchange dataExchange) {
        receivingQueue.remove(dataExchange);
    }

    private void allocateResourceOfTransmit(DataExchange message) {

    }

    public void clearConsole() {
        signalConsole = new StringBuilder();
        triggerConsole = new StringBuilder();
    }

}
