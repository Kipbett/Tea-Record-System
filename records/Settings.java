/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brian.records;

import com.brian.tea_records_interface.HomeInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author brian
 */
public class Settings {
    
    private int factory_price;
    private int new_price;
    private String factory_name;
    
    Dbconnect dc = new Dbconnect();
    HomeInterface hi = new HomeInterface();
    
    public Settings(){
        
    }
    
    public int getFactoryPrice(){
        try{
            Statement stmt = dc.conn.createStatement();
            String sql = "SELECT factory_price FROM factory WHERE factory_name=";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                factory_price = rs.getInt("factory_price");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
        return factory_price;
    }
    
    public void updatePrices(int new_price, String factory_name){
        this.new_price = new_price;
        this.factory_name = factory_name;
        try{
            Statement stmt = dc.conn.createStatement();
            String sql = "UPDATE factory SET factory_price="+new_price+" WHERE factory_name = ";
            stmt.executeUpdate(sql);
            int count = stmt.getUpdateCount();
            if(count == 1){
                JOptionPane.showConfirmDialog(hi, "Change price to "+new_price, "Change Price", JOptionPane.OK_CANCEL_OPTION);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(hi, e);
        }
    }
}
