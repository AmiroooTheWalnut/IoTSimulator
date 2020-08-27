/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import iotsimulator.IOTSimulator;
import iotsimulator.Structure.TopologyLevel.TopologyRole;
import java.io.Serializable;

/**
 *
 * @author user
 */
public class TopologyDefinition implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public Topology topology;
    
    public TopologyDefinition(IOTSimulator iOTSimulator)
    {
        topology=new Topology();
        TopologyLevel initTopologyLevel1 = new TopologyLevel();
        Device device=new Device();
        device.name="CloudDevice";
        device.ownerTopology=initTopologyLevel1;
        device.rootSimulator=iOTSimulator;
        initTopologyLevel1.devices.add(device);
        initTopologyLevel1.name="Cloud";
        initTopologyLevel1.roles.add(TopologyRole.Rearrangment);
        
        TopologyLevel initTopologyLevel2 = new TopologyLevel();
        device=new Device();
        device.name="Sensor1";
        device.ownerTopology=initTopologyLevel2;
        device.rootSimulator=iOTSimulator;
        initTopologyLevel2.devices.add(device);
        initTopologyLevel2.name="Sensors";
        initTopologyLevel2.roles.add(TopologyRole.Trigger_monitoring);
        initTopologyLevel2.roles.add(TopologyRole.Sensing);
        
        initTopologyLevel1.devices.get(0).children.add(initTopologyLevel2.devices.get(0));
        initTopologyLevel2.devices.get(0).parentDevice=initTopologyLevel1.devices.get(0);
        initTopologyLevel2.devices.get(0).parentDeviceIndex=0;
        topology.topologyLevels.add(initTopologyLevel1);
        topology.topologyLevels.add(initTopologyLevel2);
    }
    
}
