/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brian.records;

import com.brian.tea_records_interface.HomeInterface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author brian
 */
public class Dbconnect {
    HomeInterface hi = new HomeInterface();
    Connection conn;
    public Dbconnect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Tea_records", "root", "");
            //System.out.println("Connection made...");
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(hi, "Class Dbconnect \n"+e);
        }
    }
}
