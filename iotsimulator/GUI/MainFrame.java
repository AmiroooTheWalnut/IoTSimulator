/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.GUI;

import com.hashmap.tempus.processors.GenerateTimeSeriesFlowFile;
import iotsimulator.IOTSimulator;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author user
 */
public class MainFrame extends javax.swing.JFrame {

    SetupSensorDataDialog setupSensorDataDialog;
    SetupTriggerDialog setupTriggerDialog;
    SetupTopologyDialog setupTopologyDialog;
    SetupSimulatorDialog simulatorSetupDialog;
    SimulationRunDialog simulationRunDialog;
    IOTSimulator iOTSimulator;
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        iOTSimulator=new IOTSimulator();
        iOTSimulator.newProject();
        initComponents();
        setupAllDialogs();
        
    }
    
    private void setupAllDialogs()
    {
        setupSensorDataDialog=new SetupSensorDataDialog(this,true);
        setupTriggerDialog=new SetupTriggerDialog(this,true);
        setupTopologyDialog=new SetupTopologyDialog(this,true);
        simulatorSetupDialog=new SetupSimulatorDialog(this,true);
        /**UNDER CONSTRUCTION
        * IT SHOULD BE AUTO-FILLED IF A PROJECT IS LOADED.
        */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton7 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        setupSensorDataButton = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        setupTriggersButton = new javax.swing.JButton();
        jCheckBox5 = new javax.swing.JCheckBox();
        setupTopologyButton = new javax.swing.JButton();
        jCheckBox2 = new javax.swing.JCheckBox();
        setupSimulationButton = new javax.swing.JButton();
        jCheckBox3 = new javax.swing.JCheckBox();
        jButton4 = new javax.swing.JButton();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jCheckBox6 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jCheckBox7 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        loadKryoButton = new javax.swing.JButton();
        saveKryoButton = new javax.swing.JButton();
        loadSerializableButton = new javax.swing.JButton();
        saveSerializableButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton7.setText("Results");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Steps"));

        setupSensorDataButton.setText("Setup sensor data");
        setupSensorDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setupSensorDataButtonActionPerformed(evt);
            }
        });

        jCheckBox1.setEnabled(false);

        setupTriggersButton.setText("Setup triggers");
        setupTriggersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setupTriggersButtonActionPerformed(evt);
            }
        });

        jCheckBox5.setEnabled(false);

        setupTopologyButton.setText("Setup topology");
        setupTopologyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setupTopologyButtonActionPerformed(evt);
            }
        });

        jCheckBox2.setEnabled(false);

        setupSimulationButton.setText("Setup simulation");
        setupSimulationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setupSimulationButtonActionPerformed(evt);
            }
        });

        jCheckBox3.setEnabled(false);

        jButton4.setText("Run simulation");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jCheckBox4.setEnabled(false);

        jButton1.setText("Setup prediction model");

        jCheckBox6.setEnabled(false);

        jButton2.setText("Setup roles");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBox7.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(setupSensorDataButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(setupTriggersButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(setupTopologyButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox2)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(setupSimulationButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox4)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBox1)
                    .addComponent(setupSensorDataButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(setupTriggersButton)
                    .addComponent(jCheckBox5))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jCheckBox7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(setupTopologyButton)
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jCheckBox6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(setupSimulationButton)
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jCheckBox4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Save/Load project"));

        loadKryoButton.setText("Load kryo");
        loadKryoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadKryoButtonActionPerformed(evt);
            }
        });

        saveKryoButton.setText("Save kryo");
        saveKryoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveKryoButtonActionPerformed(evt);
            }
        });

        loadSerializableButton.setText("Load serializable");
        loadSerializableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadSerializableButtonActionPerformed(evt);
            }
        });

        saveSerializableButton.setText("Save serializable");
        saveSerializableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveSerializableButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(loadKryoButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveKryoButton))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(loadSerializableButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveSerializableButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadKryoButton)
                    .addComponent(saveKryoButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadSerializableButton)
                    .addComponent(saveSerializableButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setupSensorDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setupSensorDataButtonActionPerformed
        setupSensorDataDialog.setVisible(true);
    }//GEN-LAST:event_setupSensorDataButtonActionPerformed

    private void setupTopologyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setupTopologyButtonActionPerformed
        setupTopologyDialog.refreshDialog();
        setupTopologyDialog.setVisible(true);
    }//GEN-LAST:event_setupTopologyButtonActionPerformed

    private void saveKryoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveKryoButtonActionPerformed
        JFileChooser fc_save = new JFileChooser(new java.io.File("."));
        fc_save.setAcceptAllFileFilterUsed(false);
        int returnVal = fc_save.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String file_name = fc_save.getSelectedFile().getName();
            String save_file_path = fc_save.getSelectedFile().getAbsolutePath();
            try {
                iOTSimulator.saveKryo(save_file_path);
            } catch (Exception ex) {
            }
        }
    }//GEN-LAST:event_saveKryoButtonActionPerformed

    private void loadKryoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadKryoButtonActionPerformed
        JFileChooser jFileChooser1 = new javax.swing.JFileChooser(new java.io.File("."));
        jFileChooser1.setAcceptAllFileFilterUsed(false);
        int returnVal = jFileChooser1.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final String loaded_file_path = jFileChooser1.getCurrentDirectory().getPath();
            String file_name = jFileChooser1.getSelectedFile().getName();
            if (file_name.lastIndexOf(".") != -1) {
                file_name = file_name.substring(0, file_name.lastIndexOf("."));
            }
            final String passed_file_name = file_name;
            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    iOTSimulator.loadKryo(loaded_file_path, passed_file_name);
                    setupAllDialogs();
                }
            });
            readThread.start();
            try {
                readThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_loadKryoButtonActionPerformed

    private void saveSerializableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveSerializableButtonActionPerformed
        JFileChooser fc_save = new JFileChooser(new java.io.File("."));
        fc_save.setAcceptAllFileFilterUsed(false);
        int returnVal = fc_save.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String save_file_path = fc_save.getSelectedFile().getAbsolutePath();
            try {
                iOTSimulator.saveSerializable(save_file_path);
            } catch (Exception ex) {
                
            }
        }
    }//GEN-LAST:event_saveSerializableButtonActionPerformed

    private void loadSerializableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadSerializableButtonActionPerformed
        JFileChooser jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser1.setAcceptAllFileFilterUsed(false);
        int returnVal = jFileChooser1.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final String loaded_file_path = jFileChooser1.getCurrentDirectory().getPath();
            String file_name = jFileChooser1.getSelectedFile().getName();
            if (file_name.lastIndexOf(".") != -1) {
                file_name = file_name.substring(0, file_name.lastIndexOf("."));
            }

            final String passed_file_name = file_name;
            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    iOTSimulator.loadSerializable(loaded_file_path, passed_file_name);
                    setupAllDialogs();
                }
            });
            readThread.start();
            try {
                readThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_loadSerializableButtonActionPerformed

    private void setupSimulationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setupSimulationButtonActionPerformed
        simulatorSetupDialog.refreshDialog();
        simulatorSetupDialog.setVisible(true);
    }//GEN-LAST:event_setupSimulationButtonActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        simulationRunDialog=new SimulationRunDialog(this,true);
        iOTSimulator.timeController.initDevices(iOTSimulator.topologyDefinition.topology);
        simulationRunDialog.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void setupTriggersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setupTriggersButtonActionPerformed
        setupTriggerDialog.refreshDialog();
        setupTriggerDialog.setVisible(true);
    }//GEN-LAST:event_setupTriggersButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton loadKryoButton;
    private javax.swing.JButton loadSerializableButton;
    private javax.swing.JButton saveKryoButton;
    private javax.swing.JButton saveSerializableButton;
    private javax.swing.JButton setupSensorDataButton;
    private javax.swing.JButton setupSimulationButton;
    private javax.swing.JButton setupTopologyButton;
    private javax.swing.JButton setupTriggersButton;
    // End of variables declaration//GEN-END:variables
}
