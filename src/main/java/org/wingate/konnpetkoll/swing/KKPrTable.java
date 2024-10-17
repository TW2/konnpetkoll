/*
 * Copyright (C) 2024 util2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.wingate.konnpetkoll.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author util2
 */
public class KKPrTable extends JTable {
    
    private String subjectColumnName;
    private String valueColumnName;
    private KKPrTableModel model;
    private TypeRenderer typeRenderer;
    private TypeEditor typeEditor;

    public KKPrTable(String subjectColumnName, String valueColumnName) {
        initComponents();
        this.subjectColumnName = subjectColumnName;
        this.valueColumnName = valueColumnName;
        
        model = new KKPrTableModel();
        setModel(model);
        
        typeRenderer = new TypeRenderer();
        setDefaultRenderer(Value.class, typeRenderer);
        
        typeEditor = new TypeEditor();
        setDefaultEditor(Value.class, typeEditor);
    }
    
    public KKPrTable() {
        this("Property", "Value");
    }

    public String getSubjectColumnName() {
        return subjectColumnName;
    }

    public void setSubjectColumnName(String subjectColumnName) {
        this.subjectColumnName = subjectColumnName;
        updateUI();
    }

    public String getValueColumnName() {
        return valueColumnName;
    }

    public void setValueColumnName(String valueColumnName) {
        this.valueColumnName = valueColumnName;
        updateUI();
    }
    
    public void addLine(String property, Object value){
        model.addCellValue(property, value);
        updateUI();
    }
    
    public void insertLineAt(int row, String property, Object value){
        model.insertCellValue(row, property, value);
        updateUI();
    }
    
    public void setCellAt(int row, int column, Object object){
        switch(column){
            case 0 -> { model.setCellValueProperty(row, object.toString()); }
            case 1 -> { model.setCellValueValue(row, object); }
        }
        updateUI();
    }
    
    public void removeLineAt(int row){
        model.removeCellValue(row);
        updateUI();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Value : Objet caméléon">
    public class Value<T> {
        private T object;

        public Value(T object) {
            this.object = object;
        }

        public T getObject() {
            return object;
        }

        public void setObject(T object) {
            this.object = object;
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CellValue : Objet de propriété et valeur">
    public class CellValue {
        private String propertyName;
        private Value value;

        public CellValue(String propertyName, Value value) {
            this.propertyName = propertyName;
            this.value = value;
        }

        public CellValue(String propertyName) {
            this(propertyName, null);
        }

        public CellValue() {
            this("", null);
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Contrôles personnalisés">
    public static class KKList<T> {
        private final List<T> list;
        private int currentIndex;

        public KKList() {
            currentIndex = 0;
            list = new ArrayList<>();
        }

        public List<T> getList() {
            return list;
        }

        public int getCurrentIndex() {
            return currentIndex;
        }

        public void setCurrentIndex(int currentIndex) {
            this.currentIndex = 
                    currentIndex < list.size() && currentIndex >= 0 ?
                    currentIndex : currentIndex > list.size() - 1 ?
                    list.size() - 1 : 0;
        }
    }
    
    public static class KKCheckBox extends JCheckBox {
        private String selectedStateText;
        private String unselectedStateText;

        public KKCheckBox(boolean selected, String selectedStateText, String unselectedStateText) {
            setSelected(selected);
            this.selectedStateText = selectedStateText;
            this.unselectedStateText = unselectedStateText;
        }

        public KKCheckBox(String selectedStateText, String unselectedStateText) {
            this(false, selectedStateText, unselectedStateText);
        }

        public KKCheckBox(boolean selected) {
            this(selected, "Enabled", "Disabled");
        }

        public KKCheckBox() {
            this(false, "Enabled", "Disabled");
        }

        public String getSelectedStateText() {
            return selectedStateText;
        }

        public void setSelectedStateText(String selectedStateText) {
            this.selectedStateText = selectedStateText;
        }

        public String getUnselectedStateText() {
            return unselectedStateText;
        }

        public void setUnselectedStateText(String unselectedStateText) {
            this.unselectedStateText = unselectedStateText;
        }
    }
    
    public static class KKNumber<T> {
        private T object;

        public KKNumber(T object) {
            this.object = object;
        }

        public T getObject() {
            return object;
        }

        public void setObject(T object) {
            this.object = object;
        }
    }
    
    public static class KKDate {
        private Date date;
        private Date start;
        private Date end;
        private int calendarField;
        
        public KKDate(){
            this(Date.from(Instant.now()), Date.from(Instant.MIN), Date.from(Instant.MAX), Calendar.DAY_OF_MONTH);
        }
        
        public KKDate(Date date){
            this(date, Date.from(Instant.MIN), Date.from(Instant.MAX), Calendar.DAY_OF_MONTH);
        }
        
        public KKDate(Date date, Date start, Date end){
            this(date, start, end, Calendar.DAY_OF_MONTH);
        }

        public KKDate(Date date, Date start, Date end, int calendarField) {
            this.date = date;
            this.start = start;
            this.end = end;
            this.calendarField = calendarField;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }

        public int getCalendarField() {
            return calendarField;
        }

        public void setCalendarField(int calendarField) {
            this.calendarField = calendarField;
        }
        
    }
    
    public static class KKWithDialog<T> extends JPanel {
        private T object;
        private final JButton btnDialog;
        private final JPanel embedPanel;
        private final JComponent leftComponent;

        public KKWithDialog(T object) {
            this.object = object;
            
            btnDialog = new JButton("...");
            embedPanel = new JPanel();
            embedPanel.setLayout(new BorderLayout());
            
            setLayout(new BorderLayout());
            add(embedPanel, BorderLayout.CENTER);
            add(btnDialog, BorderLayout.EAST);
            
            switch(object){
                case String v -> {
                    leftComponent = new JTextField(v);                    
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
                case Integer v -> {
                    leftComponent = new JSpinner(new SpinnerNumberModel(
                            (int)v,
                            Integer.MIN_VALUE,
                            Integer.MAX_VALUE,
                            1
                    ));
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
                case Long v -> {
                    leftComponent = new JSpinner(new SpinnerNumberModel(
                            (Number)v,
                            Long.MIN_VALUE,
                            Long.MAX_VALUE,
                            1
                    ));
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
                case Float v -> {
                    leftComponent = new JSpinner(new SpinnerNumberModel(
                            (Number)v,
                            Float.MIN_VALUE,
                            Float.MAX_VALUE,
                            1
                    ));
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
                case Double v -> {
                    leftComponent = new JSpinner(new SpinnerNumberModel(
                            (double)v,
                            Double.MIN_VALUE,
                            Double.MAX_VALUE,
                            1
                    ));
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
                case Byte v -> {
                    leftComponent = new JTextField(v.toString());
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
                case Character v -> {
                    leftComponent = new JTextField(v.toString());
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
                case List v -> {
                    leftComponent = new JSpinner(new SpinnerListModel(v.toArray()));
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
                case Color v -> {
                    JLabel lblColor = new JLabel();
                    lblColor.setOpaque(true);
                    lblColor.setBackground(v);
                    leftComponent = lblColor;
                    btnDialog.addActionListener((e)->{
                        Color c = JColorChooser.showDialog(this, "Choose a color", v);
                        if(c != null){
                            lblColor.setBackground(c);
                        }
                    });
                }
                case KKList v -> {
                    JComboBox cbList = new JComboBox();
                    for(Object o : v.getList().toArray()){
                        cbList.addItem(o);
                    }
                    cbList.setSelectedIndex(v.getCurrentIndex());
                    leftComponent = cbList;
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
                case KKCheckBox v -> {
                    leftComponent = v;
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
                case KKDate v -> {
                    leftComponent = new JSpinner(new SpinnerDateModel(
                            v.getDate(),
                            v.getStart(),
                            v.getEnd(),
                            v.getCalendarField()
                    ));
                    
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
                default -> {
                    leftComponent = new JTextField(object.toString());
                    btnDialog.addActionListener((e)->{
                        
                    });
                }
            }
            
            embedPanel.add(leftComponent, BorderLayout.CENTER);
        }

        public T getObject() {
            return object;
        }

        public void setObject(T object) {
            this.object = object;
        }

        public JButton getBtnDialog() {
            return btnDialog;
        }

        public JPanel getEmbedPanel() {
            return embedPanel;
        }

        public JComponent getLeftComponent() {
            return leftComponent;
        }
        
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Modèle du tableau">
    public class KKPrTableModel extends DefaultTableModel {
        
        private final List<CellValue> cellValues;

        public KKPrTableModel() {
            cellValues = new ArrayList<>();
        }

        public List<CellValue> getCellValues() {
            return cellValues;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch(columnIndex){
                case 0 -> { return String.class; }
                case 1 -> { return Value.class; }
            }
            return super.getColumnClass(columnIndex);
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            switch(column){
                case 0 -> { return subjectColumnName; }
                case 1 -> { return valueColumnName; }
            }
            return super.getColumnName(column);
        }

        @Override
        public int getRowCount() {
            return cellValues == null || cellValues.isEmpty() ? 0 : cellValues.size();
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1;
        }

        @Override
        public Object getValueAt(int row, int column) {            
            switch(column){
                case 0 -> { return cellValues.get(row).getPropertyName(); }
                case 1 -> { return cellValues.get(row).getValue(); }
            }
            return super.getValueAt(row, column);
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            CellValue cv = cellValues.get(row);
            switch(column){
                case 0 -> { cv.setPropertyName(aValue.toString()); }
                case 1 -> { cv.setValue(new Value(aValue)); }
            }
            cellValues.set(row, cv);
            super.fireTableCellUpdated(row, column);
        }
        
        public void addCellValue(String property, Object value){
            cellValues.add(new CellValue(property, new Value(value)));
            int index = cellValues.size() - 1;
            super.fireTableRowsInserted(index, index);
        }
        
        public void setCellValueProperty(int index, String property){
            if(index > cellValues.size() - 1) return;
            CellValue cv = cellValues.get(index);
            cv.setPropertyName(property);
            super.fireTableCellUpdated(index, 0);
        }
        
        public void setCellValueValue(int index, Object value){
            if(index > cellValues.size() - 1) return;
            CellValue cv = cellValues.get(index);
            cv.setValue(new Value(value));
            super.fireTableCellUpdated(index, 1);
        }
        
        public void insertCellValue(int index, String property, Object value){
            if(index > cellValues.size() - 1){
                cellValues.add(new CellValue(property, new Value(value)));
                index = cellValues.size() - 1;
                super.fireTableRowsInserted(index, index);
            }else{
                cellValues.add(index, new CellValue(property, new Value(value)));
                super.fireTableRowsInserted(index, index);
            }
        }
        
        public void removeCellValue(int index){
            if(index > cellValues.size() - 1) return;
            cellValues.remove(index);
            super.fireTableRowsDeleted(index, index);
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Renderer du tableau">
    public class TypeRenderer extends JPanel implements TableCellRenderer {
        
        public TypeRenderer() {
            setLayout(new BorderLayout());
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            if(value instanceof Value v){
                switch(v.getObject()){
                    case String s -> {
                        removeAll();
                        JLabel lbl = new JLabel(s);
                        lbl.setOpaque(true);
                        lbl.setBackground(table.getBackground());
                        lbl.setForeground(table.getForeground());
                        add(lbl, BorderLayout.CENTER);
                    }
                    case Integer i -> {
                        removeAll();
                        JLabel lbl = new JLabel(Integer.toString(i));
                        lbl.setOpaque(true);
                        lbl.setBackground(table.getBackground());
                        lbl.setForeground(table.getForeground());
                        add(lbl, BorderLayout.CENTER);
                    }
                    case Float f -> {
                        removeAll();
                        JLabel lbl = new JLabel(Float.toString(f));
                        lbl.setOpaque(true);
                        lbl.setBackground(table.getBackground());
                        lbl.setForeground(table.getForeground());
                        add(lbl, BorderLayout.CENTER);
                    }
                    case Long l -> {
                        removeAll();
                        JLabel lbl = new JLabel(Long.toString(l));
                        lbl.setOpaque(true);
                        lbl.setBackground(table.getBackground());
                        lbl.setForeground(table.getForeground());
                        add(lbl, BorderLayout.CENTER);
                    }
                    case Double d -> {
                        removeAll();
                        JLabel lbl = new JLabel(Double.toString(d));
                        lbl.setOpaque(true);
                        lbl.setBackground(table.getBackground());
                        lbl.setForeground(table.getForeground());
                        add(lbl, BorderLayout.CENTER);
                    }
                    case Color c -> {
                        removeAll();
                        JLabel lbl = new JLabel("");
                        lbl.setOpaque(true);
                        lbl.setBorder(new LineBorder(Color.black));
                        lbl.setBackground(c);
                        add(lbl, BorderLayout.CENTER);
                    }
                    case KKList list -> {
                        removeAll();
                        JLabel lbl = new JLabel(
                                list.getList().get(list.getCurrentIndex()).toString()
                        );
                        lbl.setOpaque(true);
                        lbl.setBackground(table.getBackground());
                        lbl.setForeground(table.getForeground());
                        add(lbl, BorderLayout.CENTER);
                    }
                    case KKCheckBox chk -> {
                        removeAll();
                        chk.setText(chk.isSelected() ?
                                chk.getSelectedStateText() :
                                chk.getUnselectedStateText());
                        chk.setBackground(table.getBackground());
                        chk.setForeground(table.getForeground());
                        add(chk, BorderLayout.CENTER);
                    }
                    case KKWithDialog dia -> {
                        removeAll();
                        add(dia, BorderLayout.CENTER);
                    }
                    default -> { }
                }
            }
            
            return this;
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Editor du tableau">
    public class TypeEditor extends AbstractCellEditor implements TableCellEditor {
        
        public enum Use {
            Undefined, Color, Combobox, Checkbox, TextBox, Integer, Double,
            File, Font, KKDialog;
        }
        
        private Use use = Use.Undefined;
        
        // <editor-fold defaultstate="collapsed" desc="TextBox (Plain string)">
        private final JTextField tfTextBox = new JTextField();
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Integer">
        private final JSpinner spinnerInteger = new JSpinner(
                new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1)
        );
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Double">
        private final JSpinner spinnerDouble = new JSpinner(
                new SpinnerNumberModel(0d, Long.MIN_VALUE, Long.MAX_VALUE, 0.01d)
        );
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Color">
        private Color currentColor;
        private JButton btnColor = null;
        private JColorChooser colorChooser = null;
        private JDialog dlgColor;
        protected static final String EDIT = "edit";
        private final ActionListener colorListener = (e) -> {
            if(EDIT.equals(e.getActionCommand())){
                btnColor.setBackground(currentColor);
                colorChooser.setColor(currentColor);
                dlgColor.setVisible(true);

                fireEditingStopped();
            }else{
                currentColor = colorChooser.getColor();
            }     
        };
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="KKList">
        private KKList<?> kkList = null;
        private JComboBox comboboxKKList = null;
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="KKCheckBox">
        private KKCheckBox checkbox = null;
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="KKWithDialog">
        private KKWithDialog withDialog = null;
        // </editor-fold>

        public TypeEditor() {
            // <editor-fold defaultstate="collapsed" desc="Color">
            btnColor = new JButton();
            btnColor.setActionCommand(EDIT);
            btnColor.addActionListener(colorListener);
            btnColor.setBorderPainted(false);
            
            colorChooser = new JColorChooser();
            dlgColor = JColorChooser.createDialog(
                    btnColor,
                    "Change color",
                    true,
                    colorChooser,
                    colorListener,
                    null
            );
            // </editor-fold>
            
        }
        
        @Override
        public Object getCellEditorValue() {
            // Valeur de sortie
            
            switch(use){
                case Undefined -> {}
                case TextBox -> {
                    return tfTextBox.getText();
                }
                case Integer -> {
                    return spinnerInteger.getValue();
                }
                case Double -> {
                    return spinnerDouble.getValue();
                }
                case Color -> {
                    return currentColor;
                }
                case Combobox -> {
                    int selected = comboboxKKList.getSelectedIndex();
                    kkList.setCurrentIndex(selected);
                    return kkList;
                }
                case Checkbox -> {
                    return checkbox;
                }
                case KKDialog -> {
                    return withDialog;
                }
            }
            
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            // Valeur d'entrée
            
            if(value instanceof Value v){
                switch(v.getObject()){
                    case String s -> {
                        use = Use.TextBox;
                        tfTextBox.setText(s);
                        return tfTextBox;
                    }
                    case Integer i -> {
                        use = Use.Integer;
                        spinnerInteger.setValue(i);
                        return spinnerInteger;
                    }
                    case Double d -> {
                        use = Use.Double;
                        spinnerDouble.setValue(d);
                        return spinnerDouble;
                    }
                    case Color c -> {
                        use = Use.Color;
                        currentColor = c;
                        return btnColor;
                    }
                    case KKList list -> {
                        use = Use.Combobox;
                        kkList = list;
                        comboboxKKList = new JComboBox();
                        for(Object o : list.getList().toArray()){
                            comboboxKKList.addItem(o);
                        }
                        comboboxKKList.setSelectedIndex(kkList.getCurrentIndex());
                        return comboboxKKList;
                    }
                    case KKCheckBox chk -> {
                        use = Use.Checkbox;
                        checkbox = chk;
                        checkbox.addChangeListener((e) -> {
                            checkbox.setText(checkbox.isSelected() ?
                                    checkbox.getSelectedStateText() :
                                    checkbox.getUnselectedStateText());
                        });
                        return checkbox;
                    }
                    case KKWithDialog dial -> {
                        use = Use.KKDialog;
                        withDialog = dial;
                        java.awt.Point cursor = table.getMousePosition();
                        if(cursor != null && withDialog.getBtnDialog().contains(cursor)){
                            return withDialog.getBtnDialog();
                        }else{
                            return withDialog.getLeftComponent();
                        }                        
                    }
                    default -> { }
                }
            }
            
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    // </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}