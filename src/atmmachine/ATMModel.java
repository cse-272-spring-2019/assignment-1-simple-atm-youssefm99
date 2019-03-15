/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmmachine;

import java.util.ArrayList;

/**
 *
 * @author carnival
 */
public class ATMModel {

    private double balance;
    private String client;
    private ArrayList<Transaction> last5;

    public ATMModel(String client) {
        this.balance = 1000;
        this.client = client;
        this.last5 = new ArrayList<>();
    }

    public boolean validateClient(String passcode) {
        return client.equals(passcode);
    }

    public boolean deposit(double deposit) {
        balance = balance + deposit;
        this.last5.add(new Transaction(Transaction.DEPOSIT, deposit));
        if (last5.size() > 5) {
            this.last5.remove(0);
        }
        return true;
    }

    public boolean withdraw(double withdraw) {
        if (withdraw > balance) {
            return false;
        } else {
            balance = balance - withdraw;
            this.last5.add(new Transaction(Transaction.WITHDRAW, withdraw));
            if (last5.size() > 5) {
                this.last5.remove(0);
            }
            return true;
        }
    }

    public ArrayList<Transaction> getLast5() {
        return last5;
    }

    public double getBalance() {
        return balance;
    }

}
