/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import java.io.Serializable;

/**
 *
 * @author myname
 */
public class CustomTuple3 implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public String time;
    public String name;
    public String value;
    
    public CustomTuple3(String passed_firstItem,String passed_secondItem,String passed_thirdIem)
    {
        time=passed_firstItem;
        name=passed_secondItem;
        value=passed_thirdIem;
    }
}
