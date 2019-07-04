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

/**
 *
 * @author user
 */
public class TimeController implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public int numTimeSteps;
    
    Topology model;
    ArrayList<Device> allDevices;
    
    private void initDevices()
    {
        allDevices.clear();
        for(int i=0;i<model.topologyLevels.size();i++)
        {
            for(int j=0;i<model.topologyLevels.get(i).devices.size();j++)
            {
                allDevices.add(model.topologyLevels.get(i).devices.get(j));
            }
        }
    }
    
    public void start(Topology passed_model)
    {
        model=passed_model;
        initDevices();
        
    }
}
