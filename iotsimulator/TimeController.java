/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import iotsimulator.Structure.DataExchange;
import iotsimulator.Structure.Device;
import iotsimulator.Structure.Metric;
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
    
    transient IOTSimulator parent;
    
    transient TimeController thisTimeController=this;

    public int predictionBufferSize = 50;
    public int interpolationBufferSize = 40;
    public int simulationLengthPercentage = 100;

    public int numTimeSteps;

    transient public long currentTime;
    
    transient public long simulationVirtualStartTime;

    private int refreshRate = 1000;

    transient public long actualEndingTime;

    transient private long simulationRealStartTime;

    transient ArrayList<Timer> watches;
    transient Timer trunkTimer;

    transient Topology model;
    transient public ArrayList<Device> allDevices = new ArrayList();

    transient public boolean isActive = false;
    
    TimeController(IOTSimulator iOTSimulator)
    {
        parent=iOTSimulator;
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
    
    public void start(Topology passed_model, MetricManager metricManager) {
        isActive = true;
        simulationVirtualStartTime = metricManager.startingTime;
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
        simulationVirtualStartTime=currentTime;
        trunkTimer.cancel();
        for (int i = 0; i < watches.size(); i++) {
            watches.get(i).cancel();
            watches.set(i, new Timer());
        }
    }

    public void resume(MetricManager metricManager) {
        int watchCounter = 0;
        for (int i = 0; i < allDevices.size(); i++) {
            for (int j = 0; j < allDevices.get(i).metrics.size(); j++) {
                setupTimer(watchCounter, i, j, metricManager);
                watchCounter = watchCounter + 1;
            }
        }
        trunkTimer = new Timer();
        simulationRealStartTime = System.currentTimeMillis();
        trunkTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                long passedTimeFromStart = System.currentTimeMillis() - simulationRealStartTime;
                currentTime = simulationVirtualStartTime + passedTimeFromStart;
                if (currentTime > actualEndingTime) {
                    finishSimulatiom();
                    System.out.println("Simulation finished");
                }
            }
        }, 0, refreshRate);
    }

    private void setupTimer(int watchCounter, int deviceIndex, int metricIndex, MetricManager metricManager) {
        watches.get(watchCounter).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double generatedMetricValue = metricManager.getMetricValue(allDevices.get(deviceIndex).metrics.get(metricIndex), currentTime);
                DataExchange message=new DataExchange(allDevices.get(deviceIndex),allDevices.get(deviceIndex).parent,currentTime,String.valueOf(generatedMetricValue),allDevices.get(deviceIndex).metrics.get(metricIndex));
                allDevices.get(deviceIndex).sendToParent(message,thisTimeController);
                message.toDevice.checkTriggers(parent.triggerMonitor,currentTime);
            }
        }, 0, allDevices.get(deviceIndex).metrics.get(metricIndex).frequency);
    }

    private void finishSimulatiom() {
        trunkTimer.cancel();
        for (int i = 0; i < watches.size(); i++) {
            watches.get(i).cancel();
        }
        isActive = false;
    }

}
