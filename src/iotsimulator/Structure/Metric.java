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
public class Metric implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    public String name;
    int columnIndex=-1;
    String columnName=null;
    int frequency=1;//IN SECONDS
    
    public boolean isAssigned=false;
    
    ArrayList<Double> predictionBuffer=new ArrayList();
    
    public Metric(int passed_columnIndex,String passed_columnName)
    {
        columnIndex=passed_columnIndex;
        columnName=passed_columnName;
        name=columnName;
    }
}
