/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.Structure;

import arima.ARIMA;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.timeseries.WekaForecaster;
import weka.core.Instances;
import weka.filters.supervised.attribute.TSLagMaker;

/**
 *
 * @author user
 */
public class Predictor {

    String type;
    Classifier classifier;
    String name;
    public double frequency=1000;//MILLISECONDS AFTER GETTING IDLE

    static Double results[];

    public double estimateNextSensorValues(Instances realSensorValues, int numberOfPredictionSteps) {
//        boolean success = false;
        if (type.equals("DM")) {
            WekaForecaster forecaster = new WekaForecaster();
            TSLagMaker lagMaker = forecaster.getTSLagMaker();

            try {
                forecaster.setFieldsToForecast("Demand");
                forecaster.setCalculateConfIntervalsForForecasts(1);
                forecaster.setBaseForecaster(classifier);
                lagMaker.setTimeStampField("Month");
                lagMaker.setMinLag(1);
                lagMaker.setMaxLag(12);
                lagMaker.setAddMonthOfYear(true);
                lagMaker.setAddQuarterOfYear(true);
                forecaster.buildForecaster(realSensorValues);
//                forecaster.buildForecaster(new Instances(RealDemands, 0, knownPartWarmup));
//                forecaster.primeForecaster(new Instances(RealDemands, 0, currentMonthIndex + 1));

                int numStepsToForecast = numberOfPredictionSteps;
                List<List<NumericPrediction>> forecast = forecaster.forecast(numStepsToForecast);
//            List<List<NumericPrediction>> forecast=forecaster.forecast(1, new Instances(RealDemands,0,currentMonthIndex+1));

                double forecastedValues[] = getPredictionForNextMonth(forecast, numStepsToForecast);
//            success = true;
//            System.out.println(success);
//            System.out.println(forecastedValues[1]);
//                double rangeMidToHighConfidenceInterval = forecastedValues[0] - forecastedValues[1];
//                double offset = rangeMidToHighConfidenceInterval / 1.0;
                return forecastedValues[0];
            } catch (Exception ex) {
                ex.printStackTrace();
                String msg = ex.getMessage().toLowerCase();
                if (msg.indexOf("not in classpath") > -1) {
                    return -1;
                }
            }
        } else if (type.equals("ARMA")) {
//            try {
//                double[] dataArray = new double[currentMonthIndex];
//                for (int d = 0; d < dataArray.length; d++) {
//                    dataArray[d] = RealDemands.instance(d).value(2);
//                }
//
//                //System.out.println(arraylist.size());
//                ARIMA arima = new ARIMA(dataArray);
//
//                double range = 0;
//                double counter = 0;
//                for (int d = 0; d < 4; d++) {
//                    range = Math.abs(dataArray[dataArray.length - 1 - d] - dataArray[dataArray.length - 1 - d - 1]);
//                    counter = counter + 1;
//                }
//                range = range / counter;
//
//                int[] model = arima.getARIMAmodel();
//                return arima.aftDeal(arima.predictValue(model[0], model[1])) + range / 1.0;
//            } catch (Exception ex) {
//                return -1;
//            }
        }

        return -1;
    }

    static private double[] getPredictionForNextMonth(List<List<NumericPrediction>> preds, int steps) {
        double output[] = new double[3];

        for (int i = 0; i < steps; i++) {
            List<NumericPrediction> predsForTargetsAtStep = preds.get(i);

            for (int j = 0; j < predsForTargetsAtStep.size(); j++) {
                NumericPrediction p = predsForTargetsAtStep.get(j);
                double[][] limits = p.predictionIntervals();
                output[0] = p.predicted();
                output[1] = limits[0][0];
                output[2] = limits[0][1];
            }
        }
        return output;
    }

}
