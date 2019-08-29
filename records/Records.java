package com.brian.records;

import com.brian.tea_records_interface.Add_day_record;
import com.brian.tea_records_interface.HomeInterface;
import com.brian.tea_records_interface.Update_day_record;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.swing.JOptionPane;

/**
 * @author brian
 */
public class Records {
    
    Add_day_record adr = new Add_day_record();
    Update_day_record udr = new Update_day_record();
    Dbconnect dc = new Dbconnect();
    HomeInterface hi = new HomeInterface();
    
    private Timestamp date;
    private int initial_amount;
    private int added_amount;
    private int total_amount;
    private int field_weight;
    private int factory_weight;
    private int weight_bought;
    private int bought_amount;
    private int debt_weight_13;
    private int debt_weight_20;
    private int debt_paid_13;
    private int debt_paid_20;
    private int lunch_expenses;
    private int fuel_expenses;
    private int other_expenses;
    private String factory_went;
    private int amount_spent;
    private int balance;
    private int variance;
    private int total_debt_weight;
    private int expense_id;
    private int profit;
    

    public Records() {
    }
    
    //Enterday's record
    public void day_record(int initial_amount){
        
        this.initial_amount = initial_amount;
        
        try{
            Statement stmt = dc.conn.createStatement();
            String query = "INSERT INTO day_expense(initial_amount) VALUES(" +initial_amount+ ")";
            stmt.executeUpdate(query);
            int count = stmt.getUpdateCount();
            //JOptionPane.showMessageDialog(hi, count);
            if(count==1){
                Statement stmt2 = dc.conn.createStatement();
                String sql = "SELECT expense_id FROM day_expense WHERE total_amount=0";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int expense = rs.getInt("expense_id");
                    JOptionPane.showMessageDialog(hi, "Your Day's expense id is " + expense);
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
    }
    
    //Get amount spent for the day.
    public int getAmount_spent(int expense_id){
        this.expense_id = expense_id;
        try{
            Statement stmt = dc.conn.createStatement();
            String sql = "SELECT total_expenses from day_expense where expense_id="+expense_id;
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                amount_spent = rs.getInt("total_expenses");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
        return amount_spent;
    }
    
    //Update day's record in the database.
    public void update_record(int expense_id, int added_amount, int total_amount,int debt_paid_13, int lunch_expenses, int fuel_expenses, 
                                int other_expenses, int amount_spent, int balance, int bought_amount){
        
        Records r  = new Records();
        
        this.expense_id = expense_id;
        this.added_amount = added_amount;
        this.total_amount = total_amount;
        this.bought_amount = bought_amount;
        this.debt_paid_13 = debt_paid_13;
        this.lunch_expenses = lunch_expenses;
        this.fuel_expenses = fuel_expenses;
        this.other_expenses = other_expenses;
        this.amount_spent = amount_spent;
        this.balance = balance;
        
        try{
            Statement stmt = dc.conn.createStatement();
            String query = "UPDATE day_expense SET added_amount="+added_amount+", total_amount="+total_amount+
                    ", weight_amount="+bought_amount+", farmers_pay="+debt_paid_13+", lunch_expenses="+lunch_expenses+", fuel_expenses="+fuel_expenses+
                    ", other_expenses="+other_expenses+", total_expenses="+amount_spent+", balance="+balance+" WHERE expense_id="+expense_id;
            stmt.executeUpdate(query);
            int count = stmt.getUpdateCount();
            if(count == 1){
                JOptionPane.showMessageDialog(hi, "Update Successful!");
                int factory_amount = 20 * r.getFactory_weight(expense_id);
                profit = factory_amount - r.getAmount_spent(expense_id);
                Statement statement = dc.conn.createStatement();
                String sql = "UPDATE day_expense SET profit="+profit+ " WHERE expense_id="+expense_id;
                statement.executeUpdate(sql);
                if(statement.getUpdateCount() == 1){
                    JOptionPane.showMessageDialog(adr, "Your Day's Profit is "+profit);
                }else{
                    JOptionPane.showMessageDialog(adr, "No Profit Update Made!!!");
                }
            }
            else{
                JOptionPane.showMessageDialog(hi, "No update Made. \nTry Again");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }       
    }
    
    //Add weight record for the day.
    public void weight(int field_weight, int factory_weight, int weight_bought){
        this.field_weight = field_weight;
        this.factory_weight = factory_weight;
        this.weight_bought = weight_bought;
        
        variance = factory_weight - field_weight;
        int debt_weight = field_weight-weight_bought;
        if(debt_weight < 0){
            JOptionPane.showMessageDialog(udr, "You enterd the wrong Field Weight Value.\nCheck the values again!!!");
        }
        else{
            try{
                Statement stmt = dc.conn.createStatement();
                String query = "INSERT INTO weight(feild_weight, factory_weight, variance, bought_weight, debt_weight)"
                        + "VALUES("+field_weight+","+factory_weight+","+variance+","+weight_bought+","+debt_weight+")";
                stmt.executeUpdate(query);
                int count = stmt.getUpdateCount();
                if(count == 1){
                    JOptionPane.showMessageDialog(hi, "Data Inserted Successfully!");
                }
                else{
                    JOptionPane.showMessageDialog(hi, "No data entered.\nPlease try again.");
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(hi, e);
            }
        }   
    }
    
    //Get factory weight to calculate profit.
    public int getFactory_weight(int expense_id){
        this.expense_id = expense_id;
        try{
            Statement stmt = dc.conn.createStatement();
            String sql = "SELECT factory_weight from weight WHERE weight_id ="+expense_id;
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                factory_weight = rs.getInt("factory_weight");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
        return factory_weight;
    }
    
    //Get the days initial amount.
    public String getInitial_amount(int expense_id){
        this.expense_id = expense_id;
        /*String Expenses = udr.jTextField1.getText();
        int expense_id = Integer.parseInt(Expenses);*/
        int i_amount;
        String amount = "";
        try{
            Statement stmt = dc.conn.createStatement();
            String query = "SELECT initial_amount from day_expense where expense_id="+expense_id;
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                i_amount = rs.getInt("initial_amount");
                amount = Integer.toString(i_amount);
                //udr.jTextField10.setText(amount);
            }else{
                JOptionPane.showMessageDialog(hi, expense_id+" does not Exist!!!");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        } 
        return amount;
    }
}
