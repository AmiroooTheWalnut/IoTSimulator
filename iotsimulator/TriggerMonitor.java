/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import iotsimulator.Structure.Trigger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.core.Instances;

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
    
    public Classifier makeModel(Instances data, Classifier model)
    {
        try {
            model.buildClassifier(data);
            return model;
        } catch (Exception ex) {
            Logger.getLogger(TriggerMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
