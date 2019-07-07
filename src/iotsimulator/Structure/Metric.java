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
    int columnIndex=-1;
    String columnName=null;
    public long frequency=1000;//IN MILI SECONDS
    
    public boolean isAssigned=false;
    
    public double memoryUsageForActivity=1;
    public double cPUUsageForActivity=1;
    public double storageUsageForActivity=1;
    public double bandwidthUsageForEachTransmit=1;
    public double memoryUsageForEachTransmit=1;
    public double cPUUsageForEachTransmit=1;
    public double storageUsageForTransmit=1;
    
    public ArrayList<Double> predictionBuffer=new ArrayList();
    public ArrayList<Double> interpolationBuffer=new ArrayList();
    
    public int lastRecordIndex=0;
    
    public Metric(int passed_columnIndex,String passed_columnName)
    {
        columnIndex=passed_columnIndex;
        columnName=passed_columnName;
        name=columnName;
    }
}
