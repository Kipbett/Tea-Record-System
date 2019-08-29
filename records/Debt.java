/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brian.records;

import com.brian.tea_records_interface.AddDebt;
import com.brian.tea_records_interface.HomeInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/*
 * @author brian
 */
public class Debt {

    Dbconnect dc = new Dbconnect();
    HomeInterface hi = new HomeInterface();
    AddDebt ad = new AddDebt();

    private int debtor_id;
    private String debtor_name;
    private int amount_borrowed;
    private int total_debt;
    private int weight_paid;
    private int debt_paid;

    //Get Debtor's name
    public String getDebtor_name(){
        try{
            Statement stmt = dc.conn.createStatement();
            String sql = "SELECT * FROM debtor";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                debtor_name = rs.getString("first_name");
            }
            else{
                JOptionPane.showMessageDialog(hi, "No Reults Found.");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
        return debtor_name;
    }

    //Get debtors balance from the db
    public int getTotal_debt(){
        Debt d = new Debt();
        try{
           Statement stmt = dc.conn.createStatement();
           String sql = "SELECT debt.total_balance FROM debt WHERE first_name="+ad.jComboBox1.getItemAt(0);
           ResultSet rs = stmt.executeQuery(sql);
           while(rs.next()){
               total_debt = rs.getInt("total_balance");
           }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
        return total_debt;
    }


    public int setTotal_debt(int amount_borrwed){
        this.amount_borrowed = amount_borrwed;
        Debt d = new Debt();
        total_debt = amount_borrwed + d.getTotal_debt();
        return total_debt;
    }

    public void update_debt(int amount_borrowed, int total_debt){
        this.amount_borrowed = amount_borrowed;
        this.total_debt = total_debt;
        //this.debtor_name = debtor_name;

        try{
            Statement stmt = dc.conn.createStatement();
            String sql = "INSERT INTO debt (amount_given, total_balance) VALUES("+amount_borrowed+", "+total_debt+")";
            stmt.executeUpdate(sql);
            int count = stmt.getUpdateCount();
            if(count == 1){
                JOptionPane.showMessageDialog(hi, "Update Successful!!!");
            }else{
                JOptionPane.showMessageDialog(hi, "No Update Made!!!.\n Please Try Again");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
    }

    public int getWeight_paid(int weight_paid){
        this.weight_paid = weight_paid;
        return weight_paid;
    }

    public int getTotalPaid(){
        Debt d = new Debt();
        debt_paid = d.getWeight_paid(weight_paid)*13;
        return debt_paid;
    }
    
    public void getDebtRecord(){
        try{
            Statement stmt = dc.conn.createStatement();
            String query = "SELECT * FROM Tea_records.debt join debtor on debtor.debtor_id = debt_id join debt_payment on debt_payment.creditor_id = debt_id where total_balance = 2000";
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
            }else{
                JOptionPane.showMessageDialog(hi, "No records found.");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
    }
    
    

}
