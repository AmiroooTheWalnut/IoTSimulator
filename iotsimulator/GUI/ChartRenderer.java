/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.GUI;

import java.awt.Color;
import java.awt.Paint;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;

/**
 *
 * @author user
 */
public class ChartRenderer extends DefaultXYItemRenderer {

    SimulationRunDialog parentDialog;

    ChartRenderer(SimulationRunDialog passed_parent) {
        parentDialog = passed_parent;
    }

//    @Override
//    public Paint getItemPaint(int series, int item) {
//        if (series == 0) {
//            return Color.green;
//        } else {
//            if (parentDialog.xYDataset.getYValue(series, item) > 0.0) {
//                return Color.blue;
//            } else {
//                return Color.red;
//            }
//        }
//    }
}
