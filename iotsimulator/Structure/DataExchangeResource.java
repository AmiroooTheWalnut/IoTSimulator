/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author user
 */
public class DataExchangeResource {
    public double usedBandWidthAddition;

    public double usedCPUAddition;
    public double usedMemoryAddition;
    public double usedStorageAddition;
    
    DataExchangeResource(double passed_usedBandWidthAddition,double passed_usedCPUAddition,double passed_usedMemoryAddition,double passed_usedStorageAddition)
    {
        usedBandWidthAddition = passed_usedBandWidthAddition;

        usedCPUAddition = passed_usedCPUAddition;
        usedMemoryAddition = passed_usedMemoryAddition;
        usedStorageAddition = passed_usedStorageAddition;
    }
    
    
    
}
