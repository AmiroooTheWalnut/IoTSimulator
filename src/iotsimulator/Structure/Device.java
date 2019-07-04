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
public class Device implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public String name="UnnamedDevice";
    public Device parent;
    public Device child;
    public int parentDeviceIndex=-1;
    public ArrayList<Metric> metrics=new ArrayList();
    public double bandWidthCapacity=1;
    public double memoryCapacity=1;
    public double storageCapacity=1;
    public double CPUCapacity=1;
    public int metricIndices[];
    
}
