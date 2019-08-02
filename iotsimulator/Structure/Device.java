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

    public String name = "UnnamedDevice";
    public Device parent;
    public ArrayList<Device> children = new ArrayList();
    public int parentDeviceIndex = -1;
    public ArrayList<Metric> metrics = new ArrayList();
    public double bandWidthCapacity = 10;
    public double memoryCapacity = 10;
    public double storageCapacity = 10;
    public double CPUCapacity = 10;
    public int metricIndices[];

    public double usedBandWidth = 0;
    public double usedMemory = 0;
    public double usedStorage = 0;
    public double usedCPU = 0;

    transient public StringBuilder signalConsole;
    transient public StringBuilder triggerConsole;

    transient public JSONArray readyJson;

    public void checkTriggers(TriggerMonitor triggerMonitor,long time) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //for (int i = 0; i < triggerMonitor.triggers.size(); i++) {
                    if (triggerMonitor.triggers.get(0).isTriggered() == true) {
                        triggerConsole.append("Trigger: ");
                        triggerConsole.append("Time: ").append(time).append(" ");
                        triggerConsole.append("Metrices: ");
                        for (int j = 0; j < triggerMonitor.triggers.get(0).metrics.size(); j++) {
                            triggerConsole.append(triggerMonitor.triggers.get(0).metrics.get(j).name).append(" ");
                        }
                        triggerConsole.append("Type: ").append(triggerMonitor.triggers.get(0).type);
                        
                        triggerConsole.append(System.lineSeparator());
                    }
                //}
            }
        });
        thread.start();
    }

    public void predictNextMetric() {

    }

    public void receiveMessageFromChild(DataExchange message) {
        allocateResourceOfTransmit(message.metric, message.message);

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
    }

    public void clearAllResources() {
        usedBandWidth = 0;
        usedMemory = 0;
        usedStorage = 0;
        usedCPU = 0;
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

    public void sendToParent(DataExchange message, TimeController timeController) {
        Double messageValue = Double.valueOf(message.message);
        message.metric.interpolationBuffer.add(messageValue);
        if (message.metric.interpolationBuffer.size() > timeController.interpolationBufferSize) {
            message.metric.interpolationBuffer.remove(0);
        }
        message.metric.predictionBuffer.add(messageValue);
        if (message.metric.predictionBuffer.size() > timeController.predictionBufferSize) {
            message.metric.predictionBuffer.remove(0);
        }
        allocateResourceOfTransmit(message.metric, message.message);
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
    }

    private void allocateResourceOfTransmit(Metric metric, String message) {
        final double usedBandWidthAddition = message.getBytes().length / Math.pow(2, 10);

        final double usedCPUAddition = metric.cPUUsageForEachTransmit;
        final double usedMemoryAddition = metric.memoryUsageForEachTransmit + message.getBytes().length / Math.pow(2, 20);
        final double usedStorageAddition = metric.storageUsageForTransmit;
        usedBandWidth = usedBandWidth + usedBandWidthAddition;

        usedCPU = usedCPU + usedCPUAddition;
        usedMemory = usedMemory + usedMemoryAddition;
        usedStorage = usedStorage + usedStorageAddition;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                usedBandWidth = usedBandWidth - usedBandWidthAddition;

                usedCPU = usedCPU - usedCPUAddition;
                usedMemory = usedMemory - usedMemoryAddition;
                usedStorage = usedStorage - usedStorageAddition;
            }
        }, latency);
    }

    public void clearConsole() {
        signalConsole = new StringBuilder();
        triggerConsole = new StringBuilder();
    }

}
