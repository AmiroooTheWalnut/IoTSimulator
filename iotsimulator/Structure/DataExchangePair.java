/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

/**
 *
 * @author randomuser
 */
public class DataExchangePair {
    
    public DataExchange highFrequency;
    public DataExchange lowFrequency;
    
    public DataExchangePair(DataExchange passed_highFrequency,DataExchange passed_lowFrequency)
    {
        highFrequency=passed_highFrequency;
        lowFrequency=passed_lowFrequency;
    }
}
