/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.GUI;

import iotsimulator.Structure.Device;
import iotsimulator.Structure.TopologyLevel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author user
 */
public class SetupTopologyDialog extends javax.swing.JDialog {

    MainFrame parent;
    
    
    /**
     * Creates new form SetupTopologyDialog
     */
    public SetupTopologyDialog(java.awt.Frame passed_parent, boolean modal) {
        super(passed_parent, modal);
        initComponents();
        parent=(MainFrame)passed_parent;
        
        refreshDialog();
        
        jList4.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });
        
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            
            public void warn() {
                int topologySelectionLevel=jList1.getSelectedIndex();
                if(topologySelectionLevel>-1)
                {
                    if(jTextField1.getText().length()>0)
                    {
                        parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).name=jTextField1.getText();
                    }
                }
            }
        });
        
        jTextField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            
            public void warn() {
                int topologySelectionLevel=jList1.getSelectedIndex();
                int deviceSelection=jList2.getSelectedIndex();
                if(topologySelectionLevel>-1 && deviceSelection>-1)
                {
                    if(jTextField2.getText().length()>0)
                    {
                        parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection).name=jTextField2.getText();
                    }
                }
            }
        });
        
        jFormattedTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            
            public void warn() {
                int topologySelectionLevel=jList1.getSelectedIndex();
                int deviceSelection=jList2.getSelectedIndex();
                if(topologySelectionLevel>-1 && deviceSelection>-1)
                {
                    if(jFormattedTextField1.getText().length()>0)
                    {
                        parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection).bandWidthCapacity=Double.valueOf(jFormattedTextField1.getText());
                    }
                }
            }
        });
        
        jFormattedTextField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            
            public void warn() {
                int topologySelectionLevel=jList1.getSelectedIndex();
                int deviceSelection=jList2.getSelectedIndex();
                if(topologySelectionLevel>-1 && deviceSelection>-1)
                {
                    if(jFormattedTextField2.getText().length()>0)
                    {
                        parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection).memoryCapacity=Double.valueOf(jFormattedTextField2.getText());
                    }
                }
            }
        });
        
        jFormattedTextField3.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            
            public void warn() {
                int topologySelectionLevel=jList1.getSelectedIndex();
                int deviceSelection=jList2.getSelectedIndex();
                if(topologySelectionLevel>-1 && deviceSelection>-1)
                {
                    if(jFormattedTextField3.getText().length()>0)
                    {
                        parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection).storageCapacity=Double.valueOf(jFormattedTextField3.getText());
                    }
                }
            }
        });
        
        jFormattedTextField4.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            
            public void warn() {
                int topologySelectionLevel=jList1.getSelectedIndex();
                int deviceSelection=jList2.getSelectedIndex();
                if(topologySelectionLevel>-1 && deviceSelection>-1)
                {
                    if(jFormattedTextField4.getText().length()>0)
                    {
                        parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection).CPUCapacity=Double.valueOf(jFormattedTextField4.getText());
                    }
                }
            }
        });
        
        jFormattedTextField5.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            
            public void warn() {
                int topologySelectionLevel=jList1.getSelectedIndex();
                int deviceSelection=jList2.getSelectedIndex();
                if(topologySelectionLevel>-1 && deviceSelection>-1)
                {
                    if(jFormattedTextField5.getText().length()>0)
                    {
                        parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection).latency=Long.valueOf(jFormattedTextField5.getText());
                    }
                }
            }
        });
    }
    
    public void refreshDialog()
    {
        refreshTopologyLayerList();
        refreshDevicesList();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList<>();
        jLabel11 = new javax.swing.JLabel();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList4 = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Step1"));

        jLabel1.setText("Number of layers:");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(2, 2, null, 1));
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel2.setText("Name:");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2))
                        .addGap(0, 30, Short.MAX_VALUE))
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Step2"));

        jLabel3.setText("Number of devices:");

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        jSpinner2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner2StateChanged(evt);
            }
        });

        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList2);

        jLabel4.setText("Device name:");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 19, Short.MAX_VALUE))
                    .addComponent(jTextField2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Step3"));

        jLabel5.setText("Bandwidth (KB/s):");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyPressed(evt);
            }
        });

        jLabel6.setText("Memory (MB):");

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyPressed(evt);
            }
        });

        jLabel7.setText("Storage (MB):");

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField3KeyPressed(evt);
            }
        });

        jLabel8.setText("CPU (Ghz):");

        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField4KeyPressed(evt);
            }
        });

        jLabel9.setText("Parent device:");

        jList3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList3.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList3ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jList3);

        jLabel11.setText("Latency (miliseconds):");

        jFormattedTextField5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField5KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextField1)
                    .addComponent(jFormattedTextField2)
                    .addComponent(jFormattedTextField3)
                    .addComponent(jFormattedTextField4)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11))
                        .addGap(0, 81, Short.MAX_VALUE))
                    .addComponent(jFormattedTextField5))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFormattedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Step4"));

        jLabel10.setText("Initial metrics:");

        jList4.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList4ValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(jList4);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 120, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel4);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        int numCurrentTopologyLevels=parent.iOTSimulator.topologyDefinition.topology.topologyLevels.size();
        int targetTopologyLevels=(int)jSpinner1.getValue();
        if(targetTopologyLevels>numCurrentTopologyLevels)
        {
            for(int i=numCurrentTopologyLevels;i<targetTopologyLevels;i++)
            {
                TopologyLevel addedTopology=new TopologyLevel();
                addedTopology.devices.add(new Device());
                parent.iOTSimulator.topologyDefinition.topology.topologyLevels.add(new TopologyLevel());
            }
        }
        if(targetTopologyLevels<numCurrentTopologyLevels)
        {
            for(int i=numCurrentTopologyLevels;i>targetTopologyLevels;i--)
            {
                parent.iOTSimulator.topologyDefinition.topology.topologyLevels.remove(i-1);
            }
        }
        refreshTopologyLayerList();
    }//GEN-LAST:event_jSpinner1StateChanged

    private void jSpinner2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner2StateChanged
        int topologySelectionLevel=jList1.getSelectedIndex();
        if(topologySelectionLevel>-1)
        {
            int numCurrentDevices = parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.size();
            int targetDevices = (int) jSpinner2.getValue();
            if (targetDevices > numCurrentDevices) {
                for (int i = numCurrentDevices; i < targetDevices; i++) {
                    parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.add(new Device());
                }
            }
            if (targetDevices < numCurrentDevices) {
                for (int i = numCurrentDevices; i > targetDevices; i--) {
                    parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.remove(i - 1);
                }
            }
            refreshDevicesList();
        }
        
    }//GEN-LAST:event_jSpinner2StateChanged

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        int topologySelectionLevel=jList1.getSelectedIndex();
        if(topologySelectionLevel>-1)
        {
            jTextField1.setText(parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).name);
            refreshDevicesList();
            jSpinner2.setValue(parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.size());
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        int topologySelectionLevel=jList1.getSelectedIndex();
        int deviceSelection=jList2.getSelectedIndex();
        if (deviceSelection > -1) {
            Device targetDevice = parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection);
            jTextField2.setText(targetDevice.name);

            jFormattedTextField1.setText(String.valueOf(targetDevice.bandWidthCapacity));
            jFormattedTextField2.setText(String.valueOf(targetDevice.memoryCapacity));
            jFormattedTextField3.setText(String.valueOf(targetDevice.storageCapacity));
            jFormattedTextField4.setText(String.valueOf(targetDevice.CPUCapacity));
            
            jFormattedTextField5.setText(String.valueOf(targetDevice.latency));

            int parentTopologySelectionLevel = topologySelectionLevel - 1;
            if (parentTopologySelectionLevel > -1) {
                jList3.setModel(new javax.swing.AbstractListModel() {
                    @Override
                    public int getSize() {
                        return parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(parentTopologySelectionLevel).devices.size();
                    }

                    @Override
                    public Object getElementAt(int index) {
                        return parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(parentTopologySelectionLevel).devices.get(index).name;
                    }
                });
                if(parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection).parentDeviceIndex>-1)
                {
                    jList3.setSelectedIndex(parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection).parentDeviceIndex);
                }
            }
            refreshNotAssignedMetrics();
        }else{
            jTextField2.setText("");
            jFormattedTextField1.setText("");
            jFormattedTextField2.setText("");
            jFormattedTextField3.setText("");
            jFormattedTextField4.setText("");
            jList3.setModel(new javax.swing.AbstractListModel() {
                @Override
                public int getSize() {
                    return 0;
                }

                @Override
                public Object getElementAt(int index) {
                    return null;
                }
            });
            jList4.setModel(new javax.swing.AbstractListModel() {
                @Override
                public int getSize() {
                    return 0;
                }

                @Override
                public Object getElementAt(int index) {
                    return null;
                }
            });
        }
    }//GEN-LAST:event_jList2ValueChanged

    private void jList4ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList4ValueChanged
        int selectedIndices[] = jList4.getSelectedIndices();
        int topologySelectionLevel = jList1.getSelectedIndex();
        int deviceSelection = jList2.getSelectedIndex();
        if (topologySelectionLevel > -1 && deviceSelection > -1) {
            Device device = parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection);
            for (int i = 0; i < device.metrics.size(); i++) {
                device.metrics.get(i).isAssigned = false;
            }
            if (selectedIndices.length > 0) {
                device.metrics.clear();
                device.metricIndices = new int[selectedIndices.length];
                for (int i = 0; i < selectedIndices.length; i++) {
                    device.metrics.add(parent.iOTSimulator.metricManager.selectedMetrics.get(selectedIndices[i]));
                    parent.iOTSimulator.metricManager.selectedMetrics.get(selectedIndices[i]).isAssigned = true;
                    device.metricIndices[i] = selectedIndices[i];
                }
            }
        }
        
    }//GEN-LAST:event_jList4ValueChanged

    private void jList3ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList3ValueChanged
        int topologySelectionLevel=jList1.getSelectedIndex()-1;
        int deviceSelection = jList2.getSelectedIndex();
        int selectedParentIndex=jList3.getSelectedIndex();
        if(topologySelectionLevel>-1 && selectedParentIndex>-1)
        {
            parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel+1).devices.get(deviceSelection).parent=parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(selectedParentIndex);
            parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel+1).devices.get(deviceSelection).parentDeviceIndex=selectedParentIndex;
            parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(selectedParentIndex).children.add(parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel+1).devices.get(deviceSelection));
        }
        
    }//GEN-LAST:event_jList3ValueChanged

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            jList1.requestFocusInWindow();
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            jList2.requestFocusInWindow();
        }
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jFormattedTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            jPanel3.requestFocusInWindow();
        }
    }//GEN-LAST:event_jFormattedTextField1KeyPressed

    private void jFormattedTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            jPanel3.requestFocusInWindow();
        }
    }//GEN-LAST:event_jFormattedTextField2KeyPressed

    private void jFormattedTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField3KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            jPanel3.requestFocusInWindow();
        }
    }//GEN-LAST:event_jFormattedTextField3KeyPressed

    private void jFormattedTextField4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField4KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            jPanel3.requestFocusInWindow();
        }
    }//GEN-LAST:event_jFormattedTextField4KeyPressed

    private void jFormattedTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField5KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            jPanel3.requestFocusInWindow();
        }
    }//GEN-LAST:event_jFormattedTextField5KeyPressed

    public void refreshNotAssignedMetrics()
    {
        jList4.setModel(new javax.swing.AbstractListModel() {
                @Override
                public int getSize() {
                    return parent.iOTSimulator.metricManager.selectedMetrics.size();
                }

                @Override
                public Object getElementAt(int index) {
                    if(parent.iOTSimulator.metricManager.selectedMetrics.get(index).isAssigned==false)
                    {
                        return parent.iOTSimulator.metricManager.selectedMetrics.get(index).name;
                    }else{
                        return "SELECTED"+parent.iOTSimulator.metricManager.selectedMetrics.get(index).name;
                    }
                }
            });
        int topologySelectionLevel=jList1.getSelectedIndex();
        int deviceSelection=jList2.getSelectedIndex();
        if(parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection).metricIndices!=null)
        {
            jList4.setSelectedIndices(parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(deviceSelection).metricIndices);
        }
        
//        parent.iOTSimulator.metricManager.selectedMetrics.size()
//        for(int i=0;i<parent.iOTSimulator.topologyDefinition.topology.topologyLevels.size();i++)
//        {
//            for(int j=0;j<parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(i).devices.size();j++)
//            {
//                
//                parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(i).devices.get(j).
//            }
//            
//        }
        
    }
    
    public void refreshTopologyLayerList()
    {
        jList1.setModel(new javax.swing.AbstractListModel() {
            @Override
            public int getSize() {
                return parent.iOTSimulator.topologyDefinition.topology.topologyLevels.size();
            }

            @Override
            public Object getElementAt(int index) {
                return parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(index).name;
            }
        });
    }
    
    public void refreshDevicesList()
    {
        int topologySelectionLevel=jList1.getSelectedIndex();
        if (topologySelectionLevel > -1) {
            jList2.setModel(new javax.swing.AbstractListModel() {
                @Override
                public int getSize() {
                    return parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.size();
                }

                @Override
                public Object getElementAt(int index) {
                    return parent.iOTSimulator.topologyDefinition.topology.topologyLevels.get(topologySelectionLevel).devices.get(index).name;
                }
            });
        }
        
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JList<String> jList3;
    private javax.swing.JList<String> jList4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
