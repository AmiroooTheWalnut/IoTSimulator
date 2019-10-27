/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import java.io.Serializable;
import weka.core.Instances;

/**
 *
 * @author user
 */
public class DataArff implements Serializable {
    static final long serialVersionUID = 1L;
    
    public Instances instances;
    
    public DataArff(Instances passed_Instances)
    {
        instances=passed_Instances;
    }
    
}
