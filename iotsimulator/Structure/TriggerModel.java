/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import java.io.Serializable;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author user
 */
public class TriggerModel implements Serializable {
    
    static final long serialVersionUID = 1L;
    
    public Classifier model;
    public String headerNames[];
    public String readyHeader;
    public int classIndex=-1;
}
