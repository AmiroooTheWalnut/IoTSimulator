/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

/**
 *
 * @author user
 */
public class DataExchange {
    public Device fromDevice;
    public Device toDevice;
    public long time;//MILI SECONDS
    public String message;
    public Metric metric;
    
    public DataExchange(Device passed_fromDevice,Device passed_toDevice,long passed_time,String passed_message,Metric passed_metric)
    {
        fromDevice=passed_fromDevice;
        toDevice=passed_toDevice;
        time=passed_time;
        message=passed_message;
        metric=passed_metric;
    }
}
