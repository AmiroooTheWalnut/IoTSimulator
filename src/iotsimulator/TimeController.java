/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import iotsimulator.Structure.Device;
import iotsimulator.Structure.Topology;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author user
 */
public class TimeController implements Serializable {

    static final long serialVersionUID = 1L;

    public int predictionBufferSize = 50;
    public int interpolationBufferSize = 40;
    public int simulationLengthPercentage = 100;

    public int numTimeSteps;

    transient public long currentTime;

    transient public long actualEndingTime;

    transient private long simulationRealStartTime;

    transient ArrayList<Timer> watches;

    transient Topology model;
    transient public ArrayList<Device> allDevices = new ArrayList();
    transient public ArrayList<StringBuilder> allDeviceConsoles = new ArrayList();
    
    transient public boolean isActive=false;

    public void initDevices(Topology passed_model) {
        allDevices.clear();
        allDeviceConsoles.clear();
        for (int i = 0; i < passed_model.topologyLevels.size(); i++) {
            for (int j = 0; j < passed_model.topologyLevels.get(i).devices.size(); j++) {
                passed_model.topologyLevels.get(i).devices.get(j).clearAllResources();
                allDevices.add(passed_model.topologyLevels.get(i).devices.get(j));
                allDeviceConsoles.add(new StringBuilder());
            }
        }
    }

    public void start(Topology passed_model, MetricManager metricManager) {
        isActive=true;
        currentTime = metricManager.startingTime;
        actualEndingTime = (long) (metricManager.endingTime * (simulationLengthPercentage / 100f));
        model = passed_model;
        initDevices(model);
        watches = new ArrayList();
        for (int i = 0; i < allDevices.size(); i++) {
            for (int j = 0; j < allDevices.get(i).metrics.size(); j++) {
                allDevices.get(i).metrics.get(j).lastRecordIndex = 0;
                allDevices.get(i).allocateResourcesToMetric(allDevices.get(i).metrics.get(j));
                watches.add(new Timer());
            }
        }
        resume(metricManager);
    }

    public void pause() {
        for (int i = 0; i < watches.size(); i++) {
            watches.get(i).cancel();
        }
    }

    public void resume(MetricManager metricManager) {
        int watchCounter = 0;
        simulationRealStartTime = System.currentTimeMillis();
        for (int i = 0; i < allDevices.size(); i++) {
            for (int j = 0; j < allDevices.get(i).metrics.size(); j++) {
                setupTimer(watchCounter, i, j, metricManager);
                watchCounter = watchCounter + 1;
            }
        }
    }

    private void setupTimer(int watchCounter, int deviceIndex, int metricIndex, MetricManager metricManager) {
        watches.get(watchCounter).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long passedTimeFromStart = System.currentTimeMillis() - simulationRealStartTime;
                currentTime = currentTime + passedTimeFromStart;
                if (currentTime > actualEndingTime) {
                    finishSimulatiom();
                    System.out.println("Simulation finished");
                } else {
                    double generatedMetricValue = metricManager.getMetricValue(allDevices.get(deviceIndex).metrics.get(metricIndex), currentTime);
                    allDevices.get(deviceIndex).allocateResourceOfTransmit(allDevices.get(deviceIndex).metrics.get(metricIndex), String.valueOf(generatedMetricValue));
                    allDeviceConsoles.get(deviceIndex).append("Metric: ");
                    allDeviceConsoles.get(deviceIndex).append(allDevices.get(deviceIndex).metrics.get(metricIndex).name);
                    allDeviceConsoles.get(deviceIndex).append("Time: ");
                    allDeviceConsoles.get(deviceIndex).append(currentTime);
                    allDeviceConsoles.get(deviceIndex).append(": ");
                    allDeviceConsoles.get(deviceIndex).append(generatedMetricValue);
                    allDeviceConsoles.get(deviceIndex).append(System.lineSeparator());
                }
            }
        }, 0, allDevices.get(deviceIndex).metrics.get(metricIndex).frequency);
    }

    private void finishSimulatiom() {
        for (int i = 0; i < watches.size(); i++) {
            watches.get(i).cancel();
        }
        isActive=false;
    }

}
