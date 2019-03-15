/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmmachine;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author carnival
 */
public class ATMController implements Initializable {
    
   
   
    @FXML
    private Label msgLbl;
    @FXML
    private Label resultLbl;
    
    @FXML
    private TextField userInputTF;
    
    @FXML
    private Button oneBtn;        
    @FXML
    private Button twoBtn; 
    @FXML
    private Button threeBtn; 
    @FXML
    private Button fourBtn; 
    @FXML
    private Button fiveBtn; 
    @FXML
    private Button sixBtn;
    @FXML
    private Button sevenBtn; 
    @FXML
    private Button eightBtn; 
    @FXML
    private Button nineBtn;
    @FXML
    private Button zeroBtn; 
    @FXML
    private Button nextBtn;
    @FXML
    private Button previousBtn;
    
    @FXML
    private Button withdrawBtn;
    @FXML
    private Button depositBtn;
    @FXML
    private Button showBalanceBtn;
    @FXML
    private Button showTransactionsBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button leaveBtn;
    
    // Variables
    ATMModel atm;
    int transactionType;
    int idx;
    
    
    // For the numbers Button
    EventHandler<MouseEvent> inputDigit = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            String originalText = userInputTF.getText();
            if (originalText.length() > 6){
                return;
            }
            Button button = (Button) event.getSource();
            String digit = button.getText();
            userInputTF.setText(originalText + digit);
        }
            
    };
    
    // For 'submit' Button    
    EventHandler<MouseEvent> login = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            String clientNum = userInputTF.getText();
            if (atm.validateClient(clientNum)){
                // Successful login
                msgLbl.setText("Welcome!\nPlease choose type of service you would like to execute"); 
                userInputTF.setText("");
                // Activate transaction buttons (or Radio Buttons)
                depositBtn.setDisable(false);
                withdrawBtn.setDisable(false);
                showBalanceBtn.setDisable(false);
                showTransactionsBtn.setDisable(false);
                cancelBtn.setDisable(false);
               leaveBtn.setDisable(false);
                // change submit button function
                submitBtn.removeEventFilter(MouseEvent.MOUSE_CLICKED, login);
                submitBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, commitTransaction);
            }
        }
            
    };
    EventHandler<MouseEvent> commitTransaction = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            if(transactionType == Transaction.BALANCE){
                double balance = atm.getBalance();
                resultLbl.setText("Your Current Balance: " + balance);
            }
            else if(transactionType == Transaction.LAST5){
               idx = 0;
               resultLbl.setText( atm.getLast5().get(idx).toString()); 
              if(atm.getLast5().size()> 1){
                  previousBtn.setDisable(false);
                  nextBtn.setDisable(false);
              }
               
            }
            else if (transactionType == Transaction.WITHDRAW){
                Double amountToWithdraw = Double.valueOf(userInputTF.getText());
                if(atm.withdraw(amountToWithdraw)){
                    resultLbl.setText("Amount successfully withdrawn\nYour balance: " + atm.getBalance());
                }
                else{
                    resultLbl.setText("sorry this transaction cant be performed due to insufficient credit");
                }
            }
            else if(transactionType == Transaction.DEPOSIT){
                Double amountToDeposit = Double.valueOf(userInputTF.getText());
                atm.deposit(amountToDeposit);
                resultLbl.setText("deposit successfull");
            }
            transactionType = -1;
            userInputTF.setText("");
        }
            
    };

    // For the 'leave' Button
    EventHandler<MouseEvent> logout = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            userInputTF.setText("");
            nextBtn.setDisable(true);
            previousBtn.setDisable(true);
            withdrawBtn.setDisable(true);
            depositBtn.setDisable(true);
            showBalanceBtn.setDisable(true);
            showTransactionsBtn.setDisable(true);
            cancelBtn.setDisable(true);
            //
            submitBtn.removeEventFilter(MouseEvent.MOUSE_CLICKED, commitTransaction);
            submitBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, login);
        }
            
    };
    
    // TRansaction Buttons
    EventHandler<MouseEvent> setTransaction = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent event) {
            String buttonTxt = ((Button) event.getSource()).getText();
            if(buttonTxt.equals("Deposit")){
                transactionType = Transaction.DEPOSIT;
            }
            else if(buttonTxt.equals("Withdraw")){
                transactionType = Transaction.WITHDRAW;
            }
            else if(buttonTxt.equals("Show Balance")){
                transactionType = Transaction.BALANCE;
            }
            else if(buttonTxt.equals("My Transactions")){
                transactionType = Transaction.LAST5;
            }
            else { // Cancel button
                userInputTF.setText("");
                transactionType = -1;
            }
        }           
    };
    public void nextButtonPressed(){
        idx++;
        resultLbl.setText( atm.getLast5().get(idx).toString());
        
    }
    public void previousButtonPressed(){
        idx--;
         resultLbl.setText( atm.getLast5().get(idx).toString());
    }
    
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        atm = new ATMModel("1248");
        transactionType = -1;
        oneBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, inputDigit);
        twoBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, inputDigit);
        threeBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, inputDigit);
        fourBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, inputDigit);
        fiveBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, inputDigit);
        sixBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, inputDigit);
        sevenBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, inputDigit);
        eightBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, inputDigit);
        nineBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, inputDigit);
        zeroBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, inputDigit);
        
        // Submit and Leave buttos
        submitBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, login);
        leaveBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, logout);
        
        // Transaction Buttons
        depositBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, setTransaction);
        depositBtn.setDisable(true);
        
        withdrawBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, setTransaction);
        withdrawBtn.setDisable(true);
        
        showBalanceBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, setTransaction);
        showBalanceBtn.setDisable(true);
        
        showTransactionsBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, setTransaction);
        showTransactionsBtn.setDisable(true);
        
        cancelBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, setTransaction);
        cancelBtn.setDisable(true);
        nextBtn.setDisable(true);
        previousBtn.setDisable(true);
        leaveBtn.setDisable(true);
    }
}
        
    

