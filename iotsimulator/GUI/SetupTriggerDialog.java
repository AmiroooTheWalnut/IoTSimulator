/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.GUI;

import iotsimulator.Structure.Trigger;
import java.awt.Frame;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 *
 * @author user
 */
public class SetupTriggerDialog extends javax.swing.JDialog {

    MainFrame parent;
    
    Classifier temporaryModel;

    //AbstractClassifier generatedModel;NOT IMPLEMENTED YET.
    /**
     * Creates new form SetupTriggerDialog
     */
    public SetupTriggerDialog(java.awt.Frame passed_parent, boolean modal) {
        super(passed_parent, modal);
        initComponents();
        parent = (MainFrame) passed_parent;

        jList1.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });

        refreshDialog();
    }

    public void refreshDialog() {
        jList1.setModel(new javax.swing.AbstractListModel() {
            @Override
            public int getSize() {
                return parent.iOTSimulator.metricManager.selectedMetrics.size();
            }

            @Override
            public Object getElementAt(int index) {
                return parent.iOTSimulator.metricManager.selectedMetrics.get(index).name;
            }
        });

        jList2.setModel(new javax.swing.AbstractListModel() {
            @Override
            public int getSize() {
                return parent.iOTSimulator.triggerMonitor.triggers.size();
            }

            @Override
            public Object getElementAt(int index) {
                return parent.iOTSimulator.triggerMonitor.triggers.get(index).name;
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Metric"));

        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Type"));
        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Higher than");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton2);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Lower than");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton3);

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("Between");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton4);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Model");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton1);

        getContentPane().add(jPanel1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Definition"));

        jLabel1.setText("Threshold:");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField1.setText("0");

        jLabel2.setText("High threshold:");

        jLabel3.setText("Low threshold:");

        jFormattedTextField2.setText("1");
        jFormattedTextField2.setEnabled(false);

        jFormattedTextField3.setText("0");
        jFormattedTextField3.setEnabled(false);

        jLabel4.setText("Model:");

        jButton1.setText("Make model");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("No model");

        jButton3.setText("Confirm");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setText("Name:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jFormattedTextField1)
                    .addComponent(jFormattedTextField3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(jButton3)
                            .addComponent(jLabel6)
                            .addComponent(jTextField1))
                        .addGap(0, 124, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        getContentPane().add(jPanel3);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Generated triggers"));

        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList2);

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        getContentPane().add(jPanel5);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        handleTypeRadioButtons();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        handleTypeRadioButtons();
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        handleTypeRadioButtons();
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        handleTypeRadioButtons();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int metricIndices[] = jList1.getSelectedIndices();
        if (metricIndices.length > 0) {
            Trigger tempTrigger = new Trigger();
            tempTrigger.metricIndices = metricIndices;
            if (jRadioButton2.isSelected()) {
                tempTrigger.type = Trigger.HIGHER_THAN;
                tempTrigger.threshold = Double.parseDouble(jFormattedTextField1.getText());
            } else if (jRadioButton3.isSelected()) {
                tempTrigger.type = Trigger.LESS_THAN;
                tempTrigger.threshold = Double.parseDouble(jFormattedTextField1.getText());
            } else if (jRadioButton4.isSelected()) {
                tempTrigger.type = Trigger.BETWEEN;
                tempTrigger.thresholdHigh = Double.parseDouble(jFormattedTextField2.getText());
                tempTrigger.thresholdLow = Double.parseDouble(jFormattedTextField3.getText());
            } else if (jRadioButton1.isSelected()) {
                tempTrigger.type = Trigger.MODEL;
                tempTrigger.triggerModel=temporaryModel;
            }
            for (int i = 0; i < metricIndices.length; i++) {
                tempTrigger.metrics.add(parent.iOTSimulator.metricManager.selectedMetrics.get(metricIndices[i]));
                parent.iOTSimulator.metricManager.selectedMetrics.get(metricIndices[i]).triggersInvolved.add(tempTrigger);
            }
            if (jTextField1.getText().length() > 0) {
                tempTrigger.name = jTextField1.getText();
            } else {
                tempTrigger.name = String.valueOf("Trigger" + parent.iOTSimulator.triggerMonitor.triggers.size());
            }

            parent.iOTSimulator.triggerMonitor.triggers.add(tempTrigger);
        }
        jList2.setModel(new javax.swing.AbstractListModel() {
            @Override
            public int getSize() {
                return parent.iOTSimulator.triggerMonitor.triggers.size();
            }

            @Override
            public Object getElementAt(int index) {
                return parent.iOTSimulator.triggerMonitor.triggers.get(index).name;
            }
        });
        jList1.clearSelection();
        jRadioButton2.setSelected(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SetupModelTriggerDialog setupModelTriggerDialog=new SetupModelTriggerDialog(this,jList1.getSelectedIndices(),(Frame)SwingUtilities.windowForComponent(this),true);
        setupModelTriggerDialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int deletingTrigger = jList2.getSelectedIndex();
        for (int i = 0; i < parent.iOTSimulator.triggerMonitor.triggers.get(deletingTrigger).metrics.size(); i++) {
            parent.iOTSimulator.triggerMonitor.triggers.get(deletingTrigger).metrics.get(i).triggersInvolved.remove(parent.iOTSimulator.triggerMonitor.triggers.get(deletingTrigger));
        }
        parent.iOTSimulator.triggerMonitor.triggers.remove(parent.iOTSimulator.triggerMonitor.triggers.get(deletingTrigger));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        int selectedTrigger = jList2.getSelectedIndex();
        if (selectedTrigger > -1) {
            jList1.setSelectedIndices(parent.iOTSimulator.triggerMonitor.triggers.get(selectedTrigger).metricIndices);
            switch (parent.iOTSimulator.triggerMonitor.triggers.get(selectedTrigger).type) {
                case "HIGHER_THAN":
                    jRadioButton2.setSelected(true);
                    jFormattedTextField1.setText(String.valueOf(parent.iOTSimulator.triggerMonitor.triggers.get(selectedTrigger).threshold));
                    handleTypeRadioButtons();
                    break;
                case "LOWER_THAN":
                    jRadioButton3.setSelected(true);
                    jFormattedTextField1.setText(String.valueOf(parent.iOTSimulator.triggerMonitor.triggers.get(selectedTrigger).threshold));
                    handleTypeRadioButtons();
                    break;
                case "BETWEEN":
                    jRadioButton4.setSelected(true);
                    jFormattedTextField2.setText(String.valueOf(parent.iOTSimulator.triggerMonitor.triggers.get(selectedTrigger).thresholdHigh));
                    jFormattedTextField3.setText(String.valueOf(parent.iOTSimulator.triggerMonitor.triggers.get(selectedTrigger).thresholdLow));
                    handleTypeRadioButtons();
                    break;
                case "MODEL":
                    jRadioButton1.setSelected(true);
                    jLabel5.setText("A model!");
                    handleTypeRadioButtons();
                    break;
            }
        }
    }//GEN-LAST:event_jList2ValueChanged

    public void handleTypeRadioButtons() {
        if (jRadioButton2.isSelected()) {
            jFormattedTextField1.setEnabled(true);
            jFormattedTextField2.setEnabled(false);
            jFormattedTextField3.setEnabled(false);
            jButton1.setEnabled(false);
        } else if (jRadioButton3.isSelected()) {
            jFormattedTextField1.setEnabled(true);
            jFormattedTextField2.setEnabled(false);
            jFormattedTextField3.setEnabled(false);
            jButton1.setEnabled(false);
        } else if (jRadioButton4.isSelected()) {
            jFormattedTextField1.setEnabled(false);
            jFormattedTextField2.setEnabled(true);
            jFormattedTextField3.setEnabled(true);
            jButton1.setEnabled(false);
        } else if (jRadioButton1.isSelected()) {
            jFormattedTextField1.setEnabled(false);
            jFormattedTextField2.setEnabled(false);
            jFormattedTextField3.setEnabled(false);
            jButton1.setEnabled(true);
        }
    }
    
    public void setTemporaryModel(Classifier preparedModel)
    {
        temporaryModel=preparedModel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
