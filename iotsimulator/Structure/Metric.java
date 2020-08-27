/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Metric implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public String name;
    public int columnIndex=-1;
    String columnName=null;
    public String type;
    public long frequency=1000;//IN MILI SECONDS
    
    public boolean isAssigned=false;
    
    public double weight;
    
    public double memoryUsageForActivity=1;
    public double cPUUsageForActivity=1;
    public double storageUsageForActivity=1;
    public double bandwidthUsageForEachTransmit=1;
    public double memoryUsageForEachTransmit=1;
    public double cPUUsageForEachTransmit=1;
    public double storageUsageForTransmit=1;
    
    public ArrayList<DataExchange> predictionBuffer=new ArrayList();
    public ArrayList<DataExchange> predictedBuffer=new ArrayList();
    public ArrayList<DataExchange> interpolationBuffer=new ArrayList();// No memory usage in device
    public ArrayList<TriggerState> triggerBuffer=new ArrayList();// No memory usage in device
    
    public ArrayList<Trigger> triggersInvolved=new ArrayList();
    
    public int lastRecordIndex=0;
    
    public Metric(int passed_columnIndex,String passed_columnName,String passed_type)
    {
        columnIndex=passed_columnIndex;
        columnName=passed_columnName;
        type=passed_type;
        name=columnName;
    }
    
    public void checkBufferSizes()
    {
        
    }
    
    
}
