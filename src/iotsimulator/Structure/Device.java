/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author user
 */
public class Device implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public long latency=20;//Miliseconds //EACH DEVICE SHOULD HAVE UNIQUE LATENCY***REVISE LATER
    
    public String name="UnnamedDevice";
    public Device parent;
    public Device child;
    public int parentDeviceIndex=-1;
    public ArrayList<Metric> metrics=new ArrayList();
    public double bandWidthCapacity=10;
    public double memoryCapacity=10;
    public double storageCapacity=10;
    public double CPUCapacity=10;
    public int metricIndices[];
    
    public double usedBandWidth=0;
    public double usedMemory=0;
    public double usedStorage=0;
    public double usedCPU=0;
    
    public void sendMassage()
    {
        
    }
    
    public void sendMeticMassage()
    {
        
    }
    
    public void clearAllResources()
    {
        usedBandWidth=0;
        usedMemory=0;
        usedStorage=0;
        usedCPU=0;
    }
    
    public void clearResourcesOfMetric(Metric metric)
    {
        usedCPU=usedCPU-metric.cPUUsageForActivity;
        usedMemory=usedMemory-metric.memoryUsageForActivity;
        usedStorage=usedStorage-metric.storageUsageForActivity;
    }
    
    public void allocateResourcesToMetric(Metric metric)
    {
        usedCPU=usedCPU+metric.cPUUsageForActivity;
        usedMemory=usedMemory+metric.memoryUsageForActivity;
        usedStorage=usedStorage+metric.storageUsageForActivity;
    }
    
    public void allocateResourceOfTransmit(Metric metric,String message)
    {
        final double usedBandWidthAddition=message.getBytes().length/Math.pow(2, 10);
        
        final double usedCPUAddition=metric.cPUUsageForEachTransmit;
        final double usedMemoryAddition=metric.memoryUsageForEachTransmit+message.getBytes().length/Math.pow(2, 20);
        final double usedStorageAddition=metric.storageUsageForTransmit;
        usedBandWidth=usedBandWidth+usedBandWidthAddition;
        
        usedCPU=usedCPU+usedCPUAddition;
        usedMemory=usedMemory+usedMemoryAddition;
        usedStorage=usedStorage+usedStorageAddition;
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                usedBandWidth=usedBandWidth-usedBandWidthAddition;
        
                usedCPU=usedCPU-usedCPUAddition;
                usedMemory=usedMemory-usedMemoryAddition;
                usedStorage=usedStorage-usedStorageAddition;
            }
        }, latency);
    }
    
}
