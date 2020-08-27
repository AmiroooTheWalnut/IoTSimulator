/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class TriggerCombination implements Serializable {

    static final long serialVersionUID = 1L;
    
    public TriggerState isTriggered[];
    
    public TriggerCombination(TriggerState triggers[])
    {
        isTriggered=triggers;
    }
}
