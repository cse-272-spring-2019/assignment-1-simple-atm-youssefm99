/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmmachine;

import java.sql.Time;


/**
 *
 * @author carnival
 */
public class Transaction {
    public static final int WITHDRAW = 1;
    public static final int DEPOSIT = 2;
    public static final int LAST5 = 3;
    public static final int BALANCE = 4;
    
    private int type;
    private double amount;
    private Time time;

    public Transaction(int type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        String strType = type==1?"Withdraw":"Deposit";
        
        return /* this.time + "  " + */ strType + "  " + this.amount;
        
    }
     
}
