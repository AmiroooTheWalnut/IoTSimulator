/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import iotsimulator.Structure.Trigger;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class TriggerMonitor implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public double memoryUsageForActivity=1;
    public double cPUUsageForActivity=1;
    public double storageUsageForActivity=1;
    
    public ArrayList<Trigger> triggers=new ArrayList();
    
}
