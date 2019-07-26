/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotsimulator.GUI;

import com.hashmap.tempus.processors.GenerateTimeSeriesFlowFile;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.writer.CsvWriter;
import static iotsimulator.GUI.CSVDataPanel.convertIntegers;
import iotsimulator.IOTSimulator;
import iotsimulator.Structure.CustomTuple3;
import iotsimulator.Structure.Metric;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import org.json.*;
import scala.Tuple3;

/**
 *
 * @author user
 */
public class GeneratedDataPanel extends javax.swing.JPanel {

    MainFrame parent;

    public int generatedMetricesIndices[];

    CsvReader cSVReader = new CsvReader();

    JSONObject finalObj;

    ArrayList<String> metricNames;

    List<String> columns;

    /**
     * Creates new form GeneratedDataPanel
     */
    public GeneratedDataPanel(MainFrame passed_parent) {
        initComponents();
        parent = passed_parent;

//        GenerateTimeSeriesFlowFile generateTimeSeriesFlowFile=new GenerateTimeSeriesFlowFile();
//        // create default time zone object
//        TimeZone timezonedefault = TimeZone.getDefault();
//        
//        // get display name
//        String disname = timezonedefault.getDisplayName(); 
//        generateTimeSeriesFlowFile.generateData(true, true, disname, "CSV", null, null);
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
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Step 1"));

        jLabel5.setText("From:");

        jLabel4.setText("Example:");

        jLabel6.setText("2016-01-01 14:32:15.753");

        jTextField2.setText("2016-01-01 00:00:00.000");

        jLabel7.setText("To:");

        jTextField3.setText("2016-01-02 00:00:00.000");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 387, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        add(jPanel1);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Step2"));
        jPanel5.setLayout(new java.awt.GridLayout(3, 0));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Load from file"));

        jButton1.setText("Load");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(1);
        jTextArea2.setText("The generator is for all metrics.\nYou can write generators and exported from scratch.\nNote: The start and end dates will be replaced.");
        jTextArea2.setEnabled(false);
        jScrollPane3.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.add(jPanel4);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setText("Generators:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel3);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("Exported:");

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane4.setViewportView(jTextArea3);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6);

        add(jPanel5);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Step3"));

        jButton2.setText("Generate data");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jScrollPane1.setViewportView(jTextArea4);

        jButton3.setText("Save as CSV");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
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
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addGap(0, 124, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser1.setAcceptAllFileFilterUsed(false);
        int returnVal = jFileChooser1.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String loaded_file_path = jFileChooser1.getCurrentDirectory().getPath();
            String file_name = jFileChooser1.getSelectedFile().getName();
            if (file_name.lastIndexOf(".") != -1) {
                file_name = file_name.substring(0, file_name.lastIndexOf("."));
            }
            final String passed_file_name = file_name;
            String content;
            try {
                content = new String(Files.readAllBytes(Paths.get(jFileChooser1.getSelectedFile().getAbsolutePath())));
                JSONObject obj = new JSONObject(content);
                JSONArray generators = obj.getJSONArray("generators");
                JSONArray exported = obj.getJSONArray("exported");

                metricNames = new ArrayList();
                for (int i = 0; i < exported.length(); i++) {
                    metricNames.add(exported.getJSONObject(i).getString("name"));
                }
                jTextArea1.setText(generators.toString(4));
                jTextArea3.setText(exported.toString(4));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
//            finalObj.write(new FileWriter("./temporaryConfig.json"));

//            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./temporaryConfig.json"), "utf-8"));
            finalObj = new JSONObject();

            JSONArray generators = new JSONArray(jTextArea1.getText());
            JSONArray exported = new JSONArray(jTextArea3.getText());

            finalObj.put("generators", generators);
            finalObj.put("exported", exported);

            finalObj.put("from", jTextField2.getText());
            finalObj.put("to", jTextField3.getText());

            parent.iOTSimulator.metricManager.generators = jTextArea1.getText();
            parent.iOTSimulator.metricManager.exported = jTextArea3.getText();
            parent.iOTSimulator.metricManager.dataGenerationConfig = String.valueOf(finalObj);

            String str = finalObj.toString(4).replace("\\", "");
            System.out.println(str);
            Files.write(Paths.get("./temporaryConfig.json"), str.getBytes());
//            writer.write(str);
//            writer.flush();
//            writer.close();

            String currentFolder = "";
            File pwd = new File("./");
//            for(String fileNames : pwd.list()) System.out.println(fileNames);

            currentFolder = pwd.getAbsolutePath();

            ProcessBuilder processBuilder = new ProcessBuilder();

            if (IOTSimulator.isUnix()) {
                processBuilder.command("bash ", "-c ", "java -jar " + currentFolder + "/lib/tsimulus-cli.jar ./temporaryConfig.json");
            } else if (IOTSimulator.isWindows()) {
                processBuilder.command("cmd", "/c", "java -jar " + ".\\lib\\tsimulus-cli.jar .\\temporaryConfig.json");
            } else {
                System.out.println("NOT IMPLREMENTED FOR YOUR OS! EXITING");
                return;
            }

            try {
                Process process = processBuilder.start();

                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                ArrayList<CustomTuple3> generatedValues = new ArrayList();
                while ((line = reader.readLine()) != null) {
                    try {
//                        System.out.println(line);
                        String speratedLine[] = line.split(";");
                        generatedValues.add(new CustomTuple3(speratedLine[0], speratedLine[1], speratedLine[2]));
                    } catch (Exception ex) {
                        System.out.println(line);
                    }
                }

                int exitCode = process.waitFor();
                System.out.println("\nExited with error code : " + exitCode);

                if (exitCode == 0) {
                    String resultString = createCsv(true, true, "America/Phoenix", generatedValues, metricNames);
                    jTextArea4.setText(resultString);
                    cSVReader.setContainsHeader(true);
                    try {
                        parent.iOTSimulator.metricManager.data = cSVReader.read(new StringReader(resultString));

                        int timeIndex = 0;
                        columns = parent.iOTSimulator.metricManager.data.getHeader();

                        ArrayList<Integer> indicesArrayList = new ArrayList();
                        for (int i = 0; i < columns.size(); i++) {
                            if (i != timeIndex) {
                                indicesArrayList.add(i);
                            }
                        }
                        generatedMetricesIndices = convertIntegers(indicesArrayList);
                    } catch (IOException ex) {
                        Logger.getLogger(CSVDataPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException ex) {
            Logger.getLogger(GeneratedDataPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        JFileChooser fc_save = new JFileChooser();
        fc_save.setAcceptAllFileFilterUsed(false);
        fc_save.setFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                return "Comma separated values (*.csv)";
            }

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".csv");
                }
            }
        });
        int returnVal = fc_save.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                CsvWriter writer = new CsvWriter();
                ArrayList<String[]> outputData = new ArrayList();

                String item[] = new String[parent.iOTSimulator.metricManager.data.getHeader().size()];
                for (int j = 0; j < parent.iOTSimulator.metricManager.data.getHeader().size(); j++) {
                    item[j] = parent.iOTSimulator.metricManager.data.getHeader().get(j);
                }
                outputData.add(item);

                for (int i = 0; i < parent.iOTSimulator.metricManager.data.getRowCount(); i++) {
                    item = new String[parent.iOTSimulator.metricManager.data.getHeader().size()];
                    for (int j = 0; j < parent.iOTSimulator.metricManager.data.getHeader().size(); j++) {
                        item[j] = parent.iOTSimulator.metricManager.data.getRow(i).getField(j);
                    }
                    outputData.add(item);
                }
                writer.write(fc_save.getSelectedFile().toPath(), Charset.forName("UTF-8"), outputData);
            } catch (Exception ex) {
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private String createCsv(boolean printHeader, boolean longTimestamp, String Timezone, ArrayList<CustomTuple3> generatedValues, ArrayList<String> metricNames) {
        StringBuilder dataValueString = new StringBuilder();

        if (printHeader) {
            dataValueString.append("time");
            dataValueString.append(",");
            for (int i = 0; i < metricNames.size(); i++) {
                dataValueString.append(metricNames.get(i));
                if (i != metricNames.size() - 1) {
                    dataValueString.append(",");
                }
            }
            dataValueString.append(System.lineSeparator());
        }

        String lastTimeStamp = generatedValues.get(1).time;//SET THE LAST TIME TO THE FIRST RECORD
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        DateTime datetime = formatter.parseDateTime(lastTimeStamp);
        String lastTime;
        if (longTimestamp) {
            lastTime = String.valueOf(datetime.getMillis());
        } else {
            lastTime = datetime.toString();
        }
        ArrayList<String> row = new ArrayList();
        for (int i = 0; i < metricNames.size(); i++) {
            row.add("NULL");
        }
        for (int i = 1; i < generatedValues.size(); i++)//SKIPPING THE FIRST ROW
        {
            if (generatedValues.get(i) != null) {
                String temporary_time = generatedValues.get(i).time;
                datetime = formatter.parseDateTime(temporary_time);
                String time;
                if (longTimestamp) {
                    time = String.valueOf(datetime.getMillis());
                } else {
                    time = datetime.toString();
                }

                if (!lastTimeStamp.equals(temporary_time)) {
                    dataValueString.append(lastTime).append(",");
                    for (int c = 0; c < row.size(); c++) {
                        dataValueString.append(row.get(c));
                        if (c != row.size() - 1) {
                            dataValueString.append(",");
                        }
                    }
                    dataValueString.append(System.lineSeparator());
                    row = new ArrayList();
                    for (int c = 0; c < metricNames.size(); c++) {
                        row.add("NULL");
                    }
                }
                for (int m = 0; m < metricNames.size(); m++) {
                    if (generatedValues.get(i).name.equals(metricNames.get(m))) {
                        row.set(m, generatedValues.get(i).value);
                        break;
                    }
                }
                lastTimeStamp = temporary_time;
                lastTime = time;
            }
        }

        return dataValueString.toString().trim();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}