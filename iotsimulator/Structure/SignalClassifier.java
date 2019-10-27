/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import java.io.Serializable;
import weka.classifiers.Classifier;

/**
 *
 * @author user
 */
public class SignalClassifier implements Serializable {
    static final long serialVersionUID = 1L;
    
    Classifier model;
    
}
