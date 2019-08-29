/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brian.records;

import com.brian.tea_records_interface.Daily_report;
import com.brian.tea_records_interface.HomeInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author brian
 */
public class Reports {
    Dbconnect dc = new Dbconnect();
    HomeInterface hi = new HomeInterface();
    public Reports(){   
    }
    
    /*public void getDailyReport(){
        try{
            Statement stmt = dc.conn.createStatement();
            String query = "SELECT * FROM Tea_records.day_expense" +
                            "join debt_payment" +
                            "join weight" +
                            "on weight.weight_id = expense_id";
            ResultSet rs = stmt.executeQuery(query);
            if(rs.first()){
                
            }else{
                JOptionPane.showMessageDialog(hi, "No Reports Found!");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
    }
    
    public void getMonthlyReport(){
        try{
            Statement stmt = dc.conn.createStatement();
            String query = "SELECT * FROM Tea_records.day_expense" +
                            "join debt_payment" +
                            "join weight" +
                            "on weight.weight_id = expense_id";
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                
            }else{
                JOptionPane.showMessageDialog(hi, "No Reports Found!");
            }
        }catch(SQLException e){
            
        }
    }*/
    
    public ArrayList<MonthlyReport> Months(){
        ArrayList<MonthlyReport> month = new ArrayList<>();
        try{
            Statement stmt = dc.conn.createStatement();
            String query = "SELECT * FROM Tea_records.day_expense" +
                            "join debt_payment" +
                            "join weight" +
                            "on weight.weight_id = expense_id";
            ResultSet rs = stmt.executeQuery(query);
            MonthlyReport mr;
            while(rs.next()){
                mr = new MonthlyReport(rs.getInt("factory_weght"), rs.getInt("field_weight"), rs.getInt("variance"), rs.getInt("total_expenses"), rs.getInt("profit"));
                month.add(mr);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
        return month;
    }
    
    public ArrayList<DailyReport> Daily(){
        ArrayList<DailyReport> days = new ArrayList<>();
        
        try{
            Statement stmt = dc.conn.createStatement();
            String sql = "SELECT * FROM Tea_records.day_expense join weight on weight.weight_id = weight_id";
            ResultSet rs = stmt.executeQuery(sql);
            DailyReport r;
            while(rs.next()){
                r = new DailyReport(rs.getInt("factory_weight"), rs.getInt("feild_weight"), rs.getInt("variance"), rs.getInt("total_expenses"), rs.getInt("profit"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
        
        return days;
    }
    
    public void showdata(){
        ArrayList<DailyReport> expense = Daily();
        Daily_report dr = new Daily_report();
        DefaultTableModel table = (DefaultTableModel) dr.jTable1.getModel();
        Object[] row = new Object[5];
        for(int i = 0; i < expense.size(); i++){
            row[0] = expense.get(i).getFactory_weight();
            row[1] = expense.get(i).getField_weight();
            row[2] = expense.get(i).getVariance();
            row[3] = expense.get(i).getDay_expense();
            row[4] = expense.get(i).getDay_profit();
            table.addRow(row);
        }
    }
}
