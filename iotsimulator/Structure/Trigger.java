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
public class Trigger implements Serializable {

    static final long serialVersionUID = 1L;

    public String type;//HIGHER_THAN,LOWER_THAN,BETWEEN,MODEL
    public double threshold;
    public double thresholdHigh;
    public double thresholdLow;
    //public AbstractClassifier model;
    public static String HIGHER_THAN = "HIGHER_THAN";//ALL METRICS BE HIGHER THAN (&&) IF USER WANTS (||), SEPARATE TRIGGERS MUST BE USED
    public static String LESS_THAN = "LOWER_THAN";//ALL METRICS BE LESS THAN (&&) IF USER WANTS (||), SEPARATE TRIGGERS MUST BE USED
    public static String BETWEEN = "BETWEEN";//ALL METRICS BE BETWEEN (&&) IF USER WANTS (||), SEPARATE TRIGGERS MUST BE USED
    public static String MODEL = "MODEL";

    public ArrayList<Metric> metrics = new ArrayList();

    public int metricIndices[];

    public boolean isTriggered() {
        switch (type) {
            case "HIGHER_THAN":
                boolean isTriggered = true;
                for (int i = 0; i < metrics.size(); i++) {
                    if (metrics.get(i).interpolationBuffer.size() > 1) {
                        if (!(metrics.get(i).interpolationBuffer.get(metrics.get(i).interpolationBuffer.size() - 1) > threshold)) {
                            isTriggered = false;
                        }
                    }
                }
                return isTriggered;
            case "LOWER_THAN":
                isTriggered = true;
                for (int i = 0; i < metrics.size(); i++) {
                    if (metrics.get(i).interpolationBuffer.size() > 1) {
                        if (!(metrics.get(i).interpolationBuffer.get(metrics.get(i).interpolationBuffer.size() - 1) < threshold)) {
                            isTriggered = false;
                        }
                    }

                }
                return isTriggered;
            case "BETWEEN":
                isTriggered = true;
                for (int i = 0; i < metrics.size(); i++) {
                    if (metrics.get(i).interpolationBuffer.size() > 1) {
                        if (!(metrics.get(i).interpolationBuffer.get(metrics.get(i).interpolationBuffer.size() - 1) < thresholdHigh && metrics.get(i).interpolationBuffer.get(metrics.get(i).interpolationBuffer.size() - 1) > thresholdLow)) {
                            isTriggered = false;
                        }
                    }

                }
                return isTriggered;
            case "MODEL":
                return false;//NOT IMPLEMENTED YET
            default:
                return false;
        }
    }

}
