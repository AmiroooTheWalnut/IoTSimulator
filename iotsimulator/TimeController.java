/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import iotsimulator.Structure.DataExchange;
import iotsimulator.Structure.Device;
import iotsimulator.Structure.Topology;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author user
 */
public class TimeController implements Serializable {

    static final long serialVersionUID = 1L;

    public IOTSimulator parent;

    transient TimeController thisTimeController = this;

    public int predictionBufferSize = 50;
    public int interpolationBufferSize = 40;
    public int simulationLengthPercentage = 100;

    public int numTimeSteps;

    transient public long currentTime;

    transient public long simulationVirtualStartTime;

    private int refreshRate = 1000;

    transient public long actualEndingTime;

    transient private long simulationRealStartTime;

    transient ArrayList<ScheduledThreadPoolExecutor> metricWatches;
    transient ArrayList<ScheduledThreadPoolExecutor> predictionWatches;
    transient ScheduledThreadPoolExecutor trunkTimer;

    transient Topology model;
    transient public ArrayList<Device> allDevices = new ArrayList();

    transient public boolean isActive = false;

    TimeController(IOTSimulator iOTSimulator) {
        parent = iOTSimulator;
    }

    public void initDevices(Topology passed_model) {
        allDevices = new ArrayList();
        for (int i = 0; i < passed_model.topologyLevels.size(); i++) {
            for (int j = 0; j < passed_model.topologyLevels.get(i).devices.size(); j++) {
                passed_model.topologyLevels.get(i).devices.get(j).clearAllResources();
                passed_model.topologyLevels.get(i).devices.get(j).clearConsole();
                allDevices.add(passed_model.topologyLevels.get(i).devices.get(j));
            }
        }
    }

    public void start(Topology passed_model, IOTSimulator iOTSimulator) {
        isActive = true;
        simulationVirtualStartTime = iOTSimulator.metricManager.startingTime;
        actualEndingTime = (long) (iOTSimulator.metricManager.endingTime * (simulationLengthPercentage / 100f));
        model = passed_model;
        initDevices(model);
        predictionWatches = new ArrayList();
        metricWatches = new ArrayList();
        for (int i = 0; i < allDevices.size(); i++) {
            for (int j = 0; j < allDevices.get(i).ownerTopology.roles.size(); j++) {
                if (allDevices.get(i).ownerTopology.roles.get(j).equals("Sensing")) {
                    for (int k = 0; k < allDevices.get(i).metrics.size(); k++) {
                        allDevices.get(i).metrics.get(k).lastRecordIndex = 0;
                        allDevices.get(i).allocateResourcesToMetric(allDevices.get(i).metrics.get(k));
                        metricWatches.add(new ScheduledThreadPoolExecutor(1));
                    }
                } else if (allDevices.get(i).ownerTopology.roles.get(j).equals("Trigger monitoring")) {
                    allDevices.get(i).allocateResourcesToTriggerMonitor(parent.triggerMonitor);
                    predictionWatches.add(new ScheduledThreadPoolExecutor(1));
                } else if (allDevices.get(i).ownerTopology.roles.get(j).equals("Rearrangment")) {

                }
            }

        }
        resume(iOTSimulator);
    }

    public void pause() {
        simulationVirtualStartTime = currentTime;
        trunkTimer.shutdownNow();
        for (int i = 0; i < metricWatches.size(); i++) {
            metricWatches.get(i).shutdownNow();
            metricWatches.set(i, new ScheduledThreadPoolExecutor(1));
        }
        for (int i = 0; i < predictionWatches.size(); i++) {
            predictionWatches.get(i).shutdownNow();
            predictionWatches.set(i, new ScheduledThreadPoolExecutor(1));
        }
    }

    public void resume(IOTSimulator iOTSimulator) {
        
        for (int i = 0; i < allDevices.size(); i++) {
            for (int j = 0; j < allDevices.get(i).ownerTopology.roles.size(); j++) {
                if (allDevices.get(i).ownerTopology.roles.get(j).equals("Sensing")) {
                    int watchCounter = 0;
                    for (int k = 0; k < allDevices.get(i).metrics.size(); k++) {
                        setupTimerForSensing(watchCounter, i, k, iOTSimulator.metricManager);
                        watchCounter = watchCounter + 1;
                    }
                } else if (allDevices.get(i).ownerTopology.roles.get(j).equals("Trigger monitoring")) {
                    for (int k = 0; k < allDevices.size(); k++) {
                        setupTimerForTriggerMonitoring(i,i,iOTSimulator.triggerPredictor);
                    }
                } else if (allDevices.get(i).ownerTopology.roles.get(j).equals("Rearrangment")) {

                }
            }
        }
        trunkTimer = new ScheduledThreadPoolExecutor(1);

        simulationRealStartTime = System.currentTimeMillis();
        trunkTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long passedTimeFromStart = System.currentTimeMillis() - simulationRealStartTime;
                currentTime = simulationVirtualStartTime + passedTimeFromStart;
                if (currentTime > actualEndingTime) {
                    finishSimulatiom();
                    System.out.println("Simulation finished");
                }
            }
        }, 0, refreshRate, TimeUnit.MILLISECONDS);
    }

    private void setupTimerForSensing(int watchCounter, int deviceIndex, int metricIndex, MetricManager metricManager) {
        metricWatches.get(watchCounter).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double generatedMetricValue = metricManager.getMetricValue(allDevices.get(deviceIndex).metrics.get(metricIndex), currentTime);
                DataExchange message = new DataExchange(allDevices.get(deviceIndex), allDevices.get(deviceIndex).parentDevice, currentTime, String.valueOf(generatedMetricValue), allDevices.get(deviceIndex).metrics.get(metricIndex));
                message.toDevice.checkTriggers(message, parent.triggerMonitor, currentTime);
                allDevices.get(deviceIndex).sendToParent(message, thisTimeController);
            }
        }, 0, allDevices.get(deviceIndex).metrics.get(metricIndex).frequency, TimeUnit.MILLISECONDS);
    }

    private void setupTimerForTriggerMonitoring(int watchCounter, int deviceIndex, TriggerPredictor triggerPredictor) {
//        predictionWatches.get(watchCounter).scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                triggerPredictor.generateInstances(allDevices.get(deviceIndex));
//                triggerPredictor.generateNextSensorValue(realSensorValues, numTimeSteps)
//                double generatedMetricValue = metricManager.getMetricValue(allDevices.get(deviceIndex).metrics.get(metricIndex), currentTime);
//                DataExchange message = new DataExchange(allDevices.get(deviceIndex), allDevices.get(deviceIndex).parentDevice, currentTime, String.valueOf(generatedMetricValue), allDevices.get(deviceIndex).metrics.get(metricIndex));
//                message.toDevice.checkTriggers(message, parent.triggerMonitor, currentTime);
//                allDevices.get(deviceIndex).sendToParent(message, thisTimeController);
//            }
//        }, 0, allDevices.get(deviceIndex).metrics.get(metricIndex).frequency, TimeUnit.MILLISECONDS);
    }

    private void finishSimulatiom() {
        trunkTimer.shutdownNow();
        for (int i = 0; i < metricWatches.size(); i++) {
            metricWatches.get(i).shutdownNow();
        }
        isActive = false;
    }

}
