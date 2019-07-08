/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import iotsimulator.Structure.Device;
import iotsimulator.Structure.Topology;
import iotsimulator.Structure.TopologyLevel;
import java.io.Serializable;

/**
 *
 * @author user
 */
public class TopologyDefinition implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public Topology topology;
    
    TopologyDefinition()
    {
        topology=new Topology();
        TopologyLevel initTopologyLevel1 = new TopologyLevel();
        initTopologyLevel1.devices.add(new Device());
        
        TopologyLevel initTopologyLevel2 = new TopologyLevel();
        initTopologyLevel2.devices.add(new Device());
        
        initTopologyLevel1.devices.get(0).children.add(initTopologyLevel2.devices.get(0));
        initTopologyLevel2.devices.get(0).parent=initTopologyLevel1.devices.get(0);
        initTopologyLevel2.devices.get(0).parentDeviceIndex=0;
        topology.topologyLevels.add(initTopologyLevel1);
        topology.topologyLevels.add(initTopologyLevel2);
    }
    
}
