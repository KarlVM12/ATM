import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ATM
{
   public static void main (String[] args)
   {
      // initialize a new BankInterface that the ATM will use, in this case Bank2 will be used later on
      BankInterface myBank = null;

      // try to read accounts in from a file
      try
      {

      	// Read data from a file into a Bank.
     	   // Each line of the file has info for one account.  
   	   myBank = readFromFile("in_accounts.txt");
   

      } // end try	
      catch (IOException ioe)
      {
         System.out.println("IOException in main: " + ioe.getMessage()); 
    	   ioe.printStackTrace();
      } // end catch
      catch (Exception e)
      {
         System.out.println("Exception in main: " + e.getMessage());
   	   e.printStackTrace();
      } // end catch

      // initialize a new IOHandlerInterface to deal with input/output, I chose the IoHandlerDialog class
      IOHandlerInterface ioh = new IOHandlerDialog();

      // initializes a new String id that will be what the transactions in the ATM use
      String id = "";

      // Attempts to get a valid id from the user
      try
      {
         // Using a dialog box, the user's id is asked for until a valid one is entered
         id = dialogBoxGetUserID(ioh, myBank); 
      } // end try
      catch (CancelledTransactionException cte)
      {
         // Cancelling the get id process exits the ATM
         ioh.put(cte.getMessage());
      } // end catch
      catch (Exception e)
      {
         ioh.put(e.getMessage());
         e.printStackTrace();
      } // end catch
      
      // If a valid id was gotten from the try..catch above. If not, routes to the else and exits the ATM
      if (!id.equals(""))
      {   
         // Transactions that the user can choose
         String[] choices = {"Check Balance", "Deposit", "Withdraw","Access Another Account","Exit ATM"};
         String transaction = "";

         // attemps to get a transaction from the user
         try
         {
            // while user doesn't choose "Exit ATM", the user will kept being asked to choose a transaction
            do{
               // gets a transaction based on one of the choices
               transaction = ioh.multipleChoices(choices);
            
               if (transaction.equals(choices[0]))
                  ioh.put(myBank.searchForAccountInfo(id)); // if user picks "Check Balance", displays account info
               else if (transaction.equals(choices[1]))
                  transactionDeposit(ioh, myBank, id);      // if user picks "Deposit", attempts to deposit amount into account
               else if (transaction.equals(choices[2]))
                  transactionWithdraw(ioh, myBank, id);     // if user picks "Withdraw", attempts to withdraw amount from account
               else if (transaction.equals(choices[3]))
                  id = dialogBoxGetUserID(ioh, myBank);     // if user picks "Access Another Account", attempts to get another valid id from user
               

            } while(!transaction.equals(choices[4])); // end do..while

         } // end try  
         catch(AccountDoesNotExistException adnee)
         {
            // if the code gets this far and an account doesn't exist, there is a problem in the dialofBoxGetUserID() method
            ioh.put(adnee.getMessage());
         } // end catch
         catch(NumberFormatException nfe)
         {
            // if you don't enter integers in the form x.xx when trying to deposit or withdraw from an account, an exception occurs
            // however, this should already be caught in the respective deposit and withdraw methods
            ioh.put("Cannot convert String to double: " + nfe.getMessage() + ". Please enter an amount in the format x.xx");
         } // end catch
         catch(NullPointerException npe)
         {
            // If you hit cancel or close the transaction window, a NullPointerException is thrown and dealt with here
            // hitting cancel or closing the window stops ATM use
            ioh.put("Now exiting the ATM...");
         } // end catch
         catch(CancelledTransactionException cte)
         {
            // if a transaction is cancelled, it is caught here if not caught in its own method
            ioh.put(cte.getMessage());
         } // end catch
         catch(Exception e)
         {
            ioh.put(e.getMessage());
            e.printStackTrace();
         } // end catch
         finally
         {
            // after attempting any and all transactions, the ATM closes, sorting and outputting the myBank to another file
            endTransactions(ioh, myBank);
         } // end finally

      } // end if
      else
      {
         // if the id doesn't exist when first asking for it, the ATM closes, sorting and outputting the myBank to another file
         endTransactions(ioh, myBank);
      } // end else


   } // end main


   /**
    * readFromFile:          reads all the account data from a file and makes a bank object
    * 
    * @param          String readFromFile requires the name of a file as a string
    * @return  BankInterface returns an object of type BankInterface so Bank and Bank2 can use method
    */
   public static BankInterface readFromFile (String fileName) throws IOException
   {
	   // Creata a bank.
	   BankInterface myBank = new Bank2("Bank Of America");

     	// Open a file for reading.
      Scanner inputSource = new Scanner (new File(fileName));
       
      // while there are more tokens to read from the input source...
	   while (inputSource.hasNext())
      {

		   // Read one line of input from the file into an Account object
         Account acct = InputManager.readOneAccountFrom (inputSource);
		
		   // Store the account info in the bank.
		   myBank.addAccount(acct);

	   } // end while


      return myBank;

   } // end readFromFile

   /**
    * readUserID:
    * @param IOHandlerInterface ioh
    * @return String id read from user.
    */
   public static String readUserID(IOHandlerInterface ioh)
   {
       return ioh.get("Please enter your ID: (Enter nothing, hit Cancel, or close the window to exit ATM)");
   } // end readUserID

   /**
    * isValid:             checks if an account with a certain id is present in the bank
    *
    * @param BankInterface a bank obj is passed through to search accounts
    * @param        String an id of any account
    * @return      boolean returns true if account with id present in bank, false otherwise
    */
   public static boolean isValid(BankInterface bank, String id)
   {
      boolean result = false;

      Account account = bank.search(id);
      if (account != null)
         result = true;

      return result;
   } // end isValid

   /**
    * dialogBoxGetUserID:       gets a valid user id using the dialog box i/o system
    *
    * @param IOHandlerInterface type of i/o system is being used
    * @param      BankInterface a bank obj is passed through to search accounts
    * @return            String returns a valid id unless user chooses to stop using ATM, in which case it throws an exception
    */
   public static String dialogBoxGetUserID(IOHandlerInterface ioh, BankInterface myBank)
   {
      // initializes id as a blank string, and validID as false
      String id = "";
      boolean validID = false;
   
      // asks for id and then keeps doing so while the id isn't equal to ""
      do{
         id = readUserID(ioh);            // asks for user id
         validID = isValid(myBank, id);   // checks if id entered is valid

         if (id.equals("")) 
            break;   // if id still is blank, breaks loop to exit ATM as entering nothing is an option to exit ATM

         if (validID)
         {
            // if id is valid, the loop is broken and you proceed to the transactions of that account
            ioh.put("Account with id " + id + " is present in the bank.");
            break;
         } // end if

         // if id isn't valid, displays message
         ioh.put("Account with id " + id + " is not present in the bank.");
      
      }while(!id.equals("")); // end do..while

      // if id is still blank by this point, CancelledTransactionException is thrown that later exits the ATM
      if (id.equals("")) 
         throw new CancelledTransactionException("Now exiting the ATM...");

      return id;
   } // end dialogBoxGetUserID

   /**
    * transactionDeposit:       attempts to deposit an amount into an account in a BankInterface obj
    *
    * @param IOHandlerInterface type of i/o system is being used
    * @param      BankInterface a bank obj is passed through to deposit into an account
    * @param             String a valid id gotten from the user belonging to an account in myBank
    * @return              none
    */
   public static void transactionDeposit(IOHandlerInterface ioh, BankInterface myBank, String id)
   {
      // attempts to deposit an entered amount into an account with a certain id
      try
      {
         String money = ioh.get("How much do you want to deposit? (Enter in the format x.xx, any amount less than $0.01 will not be processed)\n(Enter nothing, hit Cancel, or close the window to exit transaction)");
         
         // if no amount is entered, the user wants to cancel the transaction and an excpetion is thrown
         if (money.equals(""))
            throw new CancelledTransactionException("Transaction Cancelled");

         // the entered amount is parsed into a float and multiplied by 100 to get an amount in cents
         float cents = Float.parseFloat(money) * 100;

         // if the amount in cents is less than or equal to zero, no transaction will occur and therefore and exception is thrown
         if ((long)cents <= 0)
            throw new LessThanOneCentAmountException("An amount less than $0.01 was entered. The transaction will not occur.");

         // a Money obj is created using the amount the user entered that should be deposited into the account
         Money depositAmount = new Money((long)cents);

         // displays account info before deposit
         ioh.put("Before Deposit:\n" + myBank.searchForAccountInfo(id));

         // deposits money into the account 
         myBank.deposit(id, depositAmount);

         // displays account info after deposit
         ioh.put("After Deposit:\n" + myBank.searchForAccountInfo(id));
      } // end try
      catch(CancelledTransactionException cte)
      {
         // if nothing is entered, the windowed closed, or cancel hit, the transaction is cancelled
         ioh.put(cte.getMessage());
      } // end catch
      catch(NumberFormatException nfe)
      {
         // If the user doesn't enter a valid amount to deposit, it is caught here
         ioh.put("Cannot convert String to double: " + nfe.getMessage() + ". Please enter an amount in the format x.xx");
      } // end catch
      catch(LessThanOneCentAmountException ltocae)
      {
         // if the user entered an amount less than $0.01, it is caught here
         ioh.put(ltocae.getMessage());
      } // end catch
      catch(Exception e)
      {
         ioh.put(e.getMessage());
         e.printStackTrace();
      } // end catch
      
   } // end transactionDeposit

   /**
    * transactionWithdraw:      attempts to withdraw an amount from an account in a BankInterface obj
    *
    * @param IOHandlerInterface type of i/o system is being used
    * @param      BankInterface a bank obj is passed through to withdraw from an account
    * @param             String a valid id gotten from the user belonging to an account in myBank
    * @return              none
    */
   public static void transactionWithdraw(IOHandlerInterface ioh, BankInterface myBank, String id)
   {
      // attempts to withdraw an amount the user entered from a certain account with a certain id
      try
      {
         String money = ioh.get("How much do you want to withdraw? (Enter in the format x.xx, any amount less than $0.01 will not be processed)\n(Enter nothing, hit Cancel, or close the window to exit transaction)");
         
         // if no amount is entered, the user wants to cancel the transaction and an excpetion is thrown
         if (money.equals(""))
            throw new CancelledTransactionException("Transaction Cancelled");

         // the entered amount is parsed into a float and multiplied by 100 to get an amount in cents
         float cents = Float.parseFloat(money) * 100;

         // if the amount in cents is less than or equal to zero, no transaction will occur and therefore and exception is thrown
         if ((long)cents <= 0)
            throw new LessThanOneCentAmountException("An amount less than $0.01 was entered. The transaction will not occur.");

         // a Money obj is created using the amount the user entered that should be deposited into the account
         Money withdrawAmount = new Money((long)cents);

         // displays account info before deposit
         ioh.put("Before Withdrawal:\n" + myBank.searchForAccountInfo(id));

         // deposits money into the account 
         myBank.withdraw(id, withdrawAmount);
         
         // displays account info after deposit
         ioh.put("After Withdrawal:\n" + myBank.searchForAccountInfo(id));   
      } // end try
      catch (InsufficientFundsException ife)
      {
         // if the accounts is a Checking account and the amount entered is more than the overdraft maximum, the exception is caught here
         ioh.put(ife.getMessage());
      } // end catch
      catch(CancelledTransactionException cte)
      {
         // if nothing is entered, the windowed closed, or cancel hit, the transaction is cancelled
         ioh.put(cte.getMessage());
      } // end catch
      catch(NumberFormatException nfe)
      {
         // If the user doesn't enter a valid amount to deposit, it is caught here
         ioh.put("Cannot convert String to double: " + nfe.getMessage() + ". Please enter an amount in the format x.xx");
      } // end catch
      catch(LessThanOneCentAmountException ltocae)
      {
         // if the user entered an amount less than $0.01, it is caught here
         ioh.put(ltocae.getMessage());
      } // end catch
      catch(Exception e)
      {
         ioh.put(e.getMessage());
         e.printStackTrace();
      } // end catch

   } // end transactionWithdraw

   /**
    * endTransaction:           writes all the info of the BankInterface obj to a file and closes the ATM
    *
    * @param IOHandlerInterface type of i/o system is being used
    * @param      BankInterface a bank obj is passed through to sort accounts
    * @return              none
    */
   public static void endTransactions(IOHandlerInterface ioh, BankInterface myBank)
   {
      ioh.put("Saving and Sorting Data...");

      // sorts and writes all the data of myBank to out_accounts.txt
      WriteFile.writeOutputFile("out_accounts.txt", myBank);

      ioh.put("All account info written to out_accounts.txt");  
      System.out.println("Data saved and sorted to out_accounts.txt");

   } // end endTransactions

} // end ATM
