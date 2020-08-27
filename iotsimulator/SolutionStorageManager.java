/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import iotsimulator.Structure.FrequencySolution;
import iotsimulator.Structure.TriggerCombination;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class SolutionStorageManager implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public IOTSimulator parent;
    public ArrayList<FrequencySolution> solutions;
    
    public SolutionStorageManager(IOTSimulator iOTSimulator) {
        parent = iOTSimulator;
        solutions=new ArrayList();
    }
    
    public FrequencySolution getSolution(TriggerCombination input)
    {
        return null;
    }
    
    public void storeSolution(FrequencySolution solution)
    {
        
    }
    
}
