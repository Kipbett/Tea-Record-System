/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brian.records;

/**
 *
 * @author brian
 */
class MonthlyReport {
    private int factory_weight;
    private int field_weight;
    private int variance;
    private int day_expense;
    private int day_income;
    private int day_profit;

    public MonthlyReport(int factory_weight, int field_weight, int variance, int day_expense, int day_profit) {
        this.factory_weight = factory_weight;
        this.field_weight = field_weight;
        this.variance = variance;
        this.day_expense = day_expense;
        this.day_profit = day_profit;
    }

    public int getFactory_weight() {
        return factory_weight;
    }

    public int getField_weight() {
        return field_weight;
    }

    public int getVariance() {
        return variance;
    }

    public int getDay_expense() {
        return day_expense;
    }

    public int getDay_profit() {
        return day_profit;
    }
    
}
