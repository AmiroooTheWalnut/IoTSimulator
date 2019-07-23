/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 *
 * @author user
 */
public class IOTSimulator implements Serializable {

    static final long serialVersionUID = 1L;

    public MetricManager metricManager;
    public TopologyDefinition topologyDefinition;
    public Optimizer optimizer;
    public SolutionStorageManager solutionStorageManager;
    public TimeController timeController;
    public TriggerMonitor triggerMonitor;
    public TriggerPredictor triggerPredictor;

    public IOTSimulator() {

    }

    public IOTSimulator(IOTSimulator input) {
        metricManager = input.metricManager;
        topologyDefinition = input.topologyDefinition;
        optimizer = input.optimizer;
        solutionStorageManager = input.solutionStorageManager;
        timeController = input.timeController;
        triggerMonitor = input.triggerMonitor;
        triggerPredictor = input.triggerPredictor;
    }

    public void unpackProject(IOTSimulator input) {
        this.metricManager = input.metricManager;
        this.topologyDefinition = input.topologyDefinition;
        this.optimizer = input.optimizer;
        this.solutionStorageManager = input.solutionStorageManager;
        this.timeController = input.timeController;
        this.triggerMonitor = input.triggerMonitor;
        this.triggerPredictor = input.triggerPredictor;
    }

    public void newProject() {
        metricManager = new MetricManager();
        topologyDefinition = new TopologyDefinition();
        optimizer = new Optimizer();
        solutionStorageManager = new SolutionStorageManager();
        timeController = new TimeController();
        triggerMonitor = new TriggerMonitor();
        triggerPredictor = new TriggerPredictor();
    }

    private IOTSimulator deepClone() {
        IOTSimulator project = new IOTSimulator(this);
        return project;
    }

    public void saveSerializable(String passed_file_path) {
        String file_path = passed_file_path;
        FileOutputStream f_out;
        try {
            f_out = new FileOutputStream(file_path + ".data");
            ObjectOutputStream obj_out;
            try {
                IOTSimulator savingObject = deepClone();
                obj_out = new ObjectOutputStream(f_out);
                obj_out.writeObject(savingObject);
                obj_out.close();
            } catch (IOException ex) {
                Logger.getLogger(IOTSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOTSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadSerializable(String file_path, String file_name) {
        FileInputStream f_in;
        try {
            f_in = new FileInputStream(file_path + "\\" + file_name + "." + "data");
            ObjectInputStream obj_in;
            try {
                obj_in = new ObjectInputStream(f_in);
                try {
                    Object obj = obj_in.readObject();
                    if (obj instanceof IOTSimulator) {
                        IOTSimulator tempRead = (IOTSimulator) obj;
                        unpackProject(tempRead);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(IOTSimulator.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
                Logger.getLogger(IOTSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOTSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveKryo(String passed_file_path) {
        Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        Output output;
        try {
            output = new Output(new FileOutputStream(passed_file_path + ".bin"));
            IOTSimulator savingObject = deepClone();
            kryo.writeObject(output, savingObject);
            output.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOTSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadKryo(String file_path, String file_name) {
        Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        Input input;
        try {

            input = new Input(new FileInputStream(file_path + "/" + file_name + "." + "bin"));
            IOTSimulator tempRead = kryo.readObject(input, IOTSimulator.class);
            unpackProject(tempRead);
            input.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOTSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static String OS = System.getProperty("os.name").toLowerCase();
    
    public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
		
	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String projectFile = args[0];

    }

}
