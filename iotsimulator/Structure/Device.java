/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import iotsimulator.TimeController;
import iotsimulator.TriggerMonitor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;

/**
 *
 * @author user
 */
public class Device implements Serializable {

    static final long serialVersionUID = 1L;

    public long latency = 20;//Miliseconds //EACH DEVICE SHOULD HAVE UNIQUE LATENCY***REVISE LATER
    public long timeout = 1000;//Miliseconds trying to send to parent.

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

    public void checkTriggers(DataExchange message,TriggerMonitor triggerMonitor, long time) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < triggerMonitor.triggers.size(); i++) {
                    if (triggerMonitor.triggers.get(i).isTriggered() == true) {
                        message.metric.triggerBuffer.add(true);
                        triggerConsole.append("Trigger: ");
                        triggerConsole.append("Time: ").append(time).append(" ");
                        triggerConsole.append("Metrices: ");
                        for (int j = 0; j < triggerMonitor.triggers.get(i).metrics.size(); j++) {
                            triggerConsole.append(triggerMonitor.triggers.get(i).metrics.get(j).name).append(" ");
                        }
                        triggerConsole.append("Type: ").append(triggerMonitor.triggers.get(i).type);

                        triggerConsole.append(System.lineSeparator());

                        if (triggerConsole.length() > maxConsoleSize) {
                            triggerConsole.delete(0, 100);
                        }
                    }else{
                        message.metric.triggerBuffer.add(false);
                    }
                }
            }
        });
        thread.start();
    }

    public void predictNextMetric() {

    }

    public void receiveMessageFromChild(DataExchange message) {
        receivingQueue.add(message);

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

    public void sendToParent(DataExchange message, TimeController timeController) {
        sendingQueue.add(message);
        Double messageValue = Double.valueOf(message.message);
        message.metric.interpolationBuffer.add(message);
        if (message.metric.interpolationBuffer.size() > timeController.interpolationBufferSize) {
            message.metric.interpolationBuffer.remove(0);
        }
        message.metric.predictionBuffer.add(message);
        if (message.metric.predictionBuffer.size() > timeController.predictionBufferSize) {
            message.metric.predictionBuffer.remove(0);
            message.metric.triggerBuffer.remove(0);
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
            return;
        }
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
