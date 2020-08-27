/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import ilog.concert.IloException;
import ilog.cplex.IloCplex;
import iotsimulator.Structure.FrequencySolution;
import iotsimulator.Structure.TriggerCombination;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import ilog.opl.IloOplDataSource;
import ilog.opl.IloOplErrorHandler;
import ilog.opl.IloOplFactory;
import ilog.opl.IloOplModel;
import ilog.opl.IloOplModelDefinition;
import ilog.opl.IloOplModelSource;
import ilog.opl.IloOplSettings;

/**
 *
 * @author user
 */
public class Optimizer implements Serializable {

    static final long serialVersionUID = 1L;

    public IOTSimulator parent;

    public boolean isIdle = true;

    public Optimizer(IOTSimulator iOTSimulator) {
        parent = iOTSimulator;
    }

    public void terminatePredictionTask() {
        //solveModel();
    }

    public void terminateOnDemandTask() {
        //solveModel();
    }

    public void solveModelOnDemand(TriggerCombination input) {
        solveModel(input);
    }

    public void solveModelOnPrediction(TriggerCombination input) {
        if (isIdle == true) {
            isIdle = false;
            solveModel(input);
        }
    }

    private void solveModel(TriggerCombination input) {
        parent.metricManager.setMetricWeights(input);

        FrequencySolution solution = new FrequencySolution();

        parent.solutionStorageManager.storeSolution(solution);
    }

    public void testCplex() {
        try {
            IloOplFactory.setDebugMode(true);
            IloOplFactory oplF = new IloOplFactory();
            IloOplErrorHandler errHandler = oplF.createOplErrorHandler();
            IloOplModelSource modelSource = oplF.createOplModelSource("./Conference3ModelIOT.mod");
            IloOplSettings settings = oplF.createOplSettings(errHandler);
            IloOplModelDefinition def = oplF.createOplModelDefinition(modelSource, settings);
            ilog.opl.IloCplex cplex = oplF.createCplex();
            cplex.setOut(null);
            IloOplModel opl = oplF.createOplModel(def, cplex);
            IloOplDataSource dataSource = oplF.createOplDataSource("./GenFile0.dat");
            opl.addDataSource(dataSource);
            opl.generate();
            if (cplex.solve()) {
                System.out.println("OBJECTIVE: " + opl.getCplex().getObjValue());
                opl.postProcess();
                opl.printSolution(System.out);
            } else {
                System.out.println("No solution!");
            }
            oplF.end();
        } catch (IloException ex) {
            Logger.getLogger(Optimizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
