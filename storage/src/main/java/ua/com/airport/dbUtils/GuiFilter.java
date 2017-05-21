package ua.com.airport.dbUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GuiFilter {

    private Control filterControl;
    private String sqlField;
    private String sqlTable;
    private String currentValue;
    private List<String> filterValues = new ArrayList<>();
    private boolean listType = false;

    public GuiFilter(Control filterControl, String sqlTable, String sqlField){
        this.filterControl = filterControl;
        this.sqlField = sqlField;
        this.sqlTable = sqlTable;
    }

    public GuiFilter(Control filterControl, String sqlTable, String sqlField, boolean listType){
        this(filterControl, sqlTable, sqlField);
        this.listType = listType;
    }

    public String getSqlTable() {
        return sqlTable;
    }

    public void setSqlTable(String sqlTable) {
        this.sqlTable = sqlTable;
    }

    public List<String> getFilterValues(){
        return this.filterValues;
    }

    public void setFilterValues(ObservableList<String> filterValues){
        this.filterValues = filterValues;
    }

    public void setFilterGui(){
        if (listType){
            if(filterControl instanceof ComboBox){
                ComboBox tmpControl = (ComboBox)filterControl;
                tmpControl.setItems(FXCollections.observableList(filterValues));
            } else if(filterControl instanceof ChoiceBox){
                ChoiceBox tmpControl = (ChoiceBox)filterControl;
                tmpControl.setItems(FXCollections.observableList(filterValues));
            }
        }
    }

    public String getSelectedValue(){
        if(filterControl instanceof DatePicker){
            DatePicker tmpControl = (DatePicker)filterControl;
            return (tmpControl.getValue() != null)?tmpControl.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")):null;
        } else if(filterControl instanceof TextField){
            TextField tmpControl = (TextField)filterControl;
            return (tmpControl.getText() != null && tmpControl.getText().length() != 0)?tmpControl.getText():null;
        } else if(filterControl instanceof ComboBox){
            ComboBox tmpControl = (ComboBox)filterControl;
            return (tmpControl.getSelectionModel().getSelectedItem() != null)?tmpControl.getSelectionModel().getSelectedItem().toString():null;
        } else if(filterControl instanceof ChoiceBox){
            ChoiceBox tmpControl = (ChoiceBox)filterControl;
            return (tmpControl.getSelectionModel().getSelectedItem() != null)?tmpControl.getSelectionModel().getSelectedItem().toString():null;
        } else {
            System.out.println("Unknown control");
        }
//                switch(filterControl.getClass().getSimpleName()){
//                    case "DatePicker":
//                        DatePicker tmpControl = (DatePicker)filterControl;
//                        return tmpControl.getValue().toString();
//                    case "TextField":
//                        TextField tmpControl = (TextField)filterControl;
//                        return tmpControl.getText();
//                }
        return null;
    }

    public void resetFilterGui(){
        if(filterControl instanceof DatePicker){
            DatePicker tmpControl = (DatePicker)filterControl;
            tmpControl.setValue(null);
        } else if(filterControl instanceof TextField){
            TextField tmpControl = (TextField)filterControl;
            tmpControl.setText(null);
        } else if(filterControl instanceof ComboBox){
            ComboBox tmpControl = (ComboBox)filterControl;
            tmpControl.getSelectionModel().clearSelection();
        } else if(filterControl instanceof ChoiceBox){
            ChoiceBox tmpControl = (ChoiceBox)filterControl;
            tmpControl.getSelectionModel().clearSelection();
        } else {
            System.out.println("Unknown control");
        }
    }

    public String getSqlField(){
        return sqlField;
    }

    public boolean isListTypeFromDB(){
        return this.listType;
    }
}
