/*
 * English Dependencies Demo Project
 * Author: jcyuyi@gmail.com
 */

package englishdependenciesdemo;

import edu.stanford.nlp.trees.TypedDependency;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author airjcy
 */
public class NLPTaskPanel extends javax.swing.JPanel {
    private TaskTableModel tableModel;
    private MainFrame parentFrame;
    /**
     * Creates new form NLPTaskPanel
     */
    public NLPTaskPanel() {
        initComponents();    
        
        setTasks(new ArrayList<>());
         
    }
    
    public void setParentFrame(MainFrame f) {
        parentFrame = f;
    }
    
    public void setTasks(List<NLPEntry> tasks) {
        tableModel = new TaskTableModel();
        tableModel.addTasks(tasks);
        taskTable.setModel(tableModel);
        ListSelectionModel selectionModel = taskTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                String strSource= e.getSource().toString();
                  // e.getSource() returns an object like this
                    // javax.swing.DefaultListSelectionModel 1052752867 ={11}
                // where 11 is the index of selected element when mouse button is released
                int start = strSource.indexOf("{")+1,
                stop  = strSource.length()-1;
                int sel = Integer.parseInt(strSource.substring(start, stop));
                System.out.println(sel);
                NLPEntry selectedEntry = tableModel.getNLPEntry(sel);
                parentFrame.setWorkingEntry(selectedEntry);
            }
        });
    }
    
    class TaskTableModel extends AbstractTableModel {
        private List<NLPEntry> entries;
        private List<Boolean> selectList;
        private String[] columnNames = { "Select","Status" , "Text" , "Add Date" , "Edit Date" ,"Tree", "Dependency" };
        public void addTasks(List<NLPEntry> tasks) {
            if (entries == null) {
                entries = new ArrayList<>();
            }
            if (selectList == null) {
                selectList = new ArrayList<>();
            }
            if (tasks != null) {
                selectList = new ArrayList<>();
                for (int i = 0; i < tasks.size(); i++) {
                    entries.add(tasks.get(i));
                    selectList.add(Boolean.TRUE);
                }
            }
            fireTableStructureChanged(); 
            
        }
        public int getColumnCount() {
            return columnNames.length;
        }
        public int getRowCount() {
            return entries.size();
        }
        public String getColumnName(int col) {
            return columnNames[col];
        }
        public NLPEntry getNLPEntry(int row) {
            return entries.get(row);
        }
        public Object getValueAt (int row, int col)
        {
            String colName = getColumnName(col);
            switch (colName) {

                case "Select":
                    return selectList.get(row);
                case "Status":
                    return entries.get(row).getStatus().toString();
                case "Text":
                    return entries.get(row).getText();
                case "Add Date":
                    Date addDate = entries.get(row).getAddDate();
                    if (addDate != null) {
                        return addDate.toString();
                    } else {
                        return "";
                    }
                case "Edit Date":
                    Date editDate = entries.get(row).getEditDate();
                    if (editDate != null) {
                        return editDate.toString();
                    } else {
                        return "";
                    }
                case "Tree":
                    return entries.get(row).getTreeString();
                case "Dependency":
                    return entries.get(row).getDependency().toString();
                default:
                    return "ERROR";
            }
                    
        }
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        public boolean isCellEditable(int row,int col) {
            if (getColumnName(col) ==  "Select") {
                return true;
            }
            else
                return false;
        }
        public void setValueAt(Object value, int row, int col) {
            String colName = getColumnName(col);
            switch (colName) {

                case "Select":
                    selectList.set(row, (Boolean)value);
                    break;
                case "Status":
                    entries.get(row).setStatus((NLPEntry.EntryStatus)value);
                    break;
                case "Text":
                    break;
                case "Add Date":
                    break;
                case "Edit Date":
                    Date editDate = (Date)value;
                    entries.get(row).setEditDate(editDate);
                    break;
                case "Tree":
                    entries.get(row).setTreeString((String)value);
                    break;
                case "Dependency":
                    entries.get(row).setDependency((List<TypedDependency>)value);
                    break;
                default:
                    
            }
            fireTableCellUpdated(row, col); //redraw
        }
        
        public void removeRow(int row) {
            entries.remove(row);
            selectList.remove(row);
            fireTableRowsDeleted(row,row);
        }
    }
    
    
    //=========================== Main Test ================================
    
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        NLPTaskPanel taskPanel = new NLPTaskPanel();
        taskPanel.setTasks(null);
        f.add(taskPanel);
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        taskTable = new javax.swing.JTable();
        btnAnalyse = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnMarkTD = new javax.swing.JButton();
        btnMarkFin = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(530, 146));

        taskTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(taskTable);

        btnAnalyse.setText("Analyse");
        btnAnalyse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalyseActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnMarkTD.setText("Mark To Do");
        btnMarkTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarkTDActionPerformed(evt);
            }
        });

        btnMarkFin.setText("Mark Close");
        btnMarkFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarkFinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAnalyse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMarkFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMarkTD, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAnalyse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMarkTD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMarkFin)
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnMarkTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarkTDActionPerformed
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean b = (Boolean)(tableModel.getValueAt(i, 0)); //Select
            if (b) {
                tableModel.setValueAt(NLPEntry.EntryStatus.OPEN, i, 1);//Status
            }
        }
    }//GEN-LAST:event_btnMarkTDActionPerformed

    private void btnMarkFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarkFinActionPerformed
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean b = (Boolean) (tableModel.getValueAt(i, 0)); //Select
            if (b) {
                tableModel.setValueAt(NLPEntry.EntryStatus.CLOSE, i, 1);//Status
            }
        }
    }//GEN-LAST:event_btnMarkFinActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        List<Integer> selectedRows = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean b = (Boolean) (tableModel.getValueAt(i, 0)); //Select
            if (b) {
                selectedRows.add(i);
            }
        }
        int bias = 0; //row number will change when delete
        for (int i = 0; i < selectedRows.size(); i++) {
            tableModel.removeRow(selectedRows.get(i) - bias);
            bias++;
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAnalyseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalyseActionPerformed
        for (int i = 0; i < tableModel.getRowCount(); i++) {
             Boolean b = (Boolean) (tableModel.getValueAt(i, 0)); //Select
            if (b) {
                NLPParser p = new NLPParser((String)(tableModel.getValueAt(i, 2)));//text
                String treeString = p.getTreeString();
                List<TypedDependency> dep = p.getTypedDependencyList();
                tableModel.setValueAt(treeString, i, 5);
                tableModel.setValueAt(dep, i, 6);
            }
        }
    }//GEN-LAST:event_btnAnalyseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalyse;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnMarkFin;
    private javax.swing.JButton btnMarkTD;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable taskTable;
    // End of variables declaration//GEN-END:variables
}