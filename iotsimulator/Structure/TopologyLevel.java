/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class TopologyLevel implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public String name="Unnamed";
    public ArrayList<Device> devices=new ArrayList();
    
    public ArrayList<String> roles=new ArrayList();
    
}
