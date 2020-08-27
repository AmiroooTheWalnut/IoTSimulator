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
public class TriggerState implements Serializable {
    public Trigger trigger;
    
    public Boolean isActivated;
    public Boolean isDeactivated;
    public Boolean isUnchanged;
    
    public TriggerState(Trigger passed_trigger, boolean passed_isActivated,boolean passed_isDeactivated,boolean passed_isUnchanged)
    {
        trigger=passed_trigger;
        isActivated=passed_isActivated;
        isDeactivated=passed_isDeactivated;
        isUnchanged=passed_isUnchanged;
        if((isActivated ? 1 : 0)+(isDeactivated ? 1 : 0)+(isUnchanged ? 1 : 0)!=1)
        {
            throw new IllegalArgumentException();
        }
    }
    
}
