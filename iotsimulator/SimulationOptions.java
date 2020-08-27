/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class SimulationOptions implements Serializable {
    
    static final long serialVersionUID = 1L;
    
    public boolean isEmulate=true;
    public boolean isPredict=false;
    public boolean isReactive=false;
    public boolean isProactive=false;
}
