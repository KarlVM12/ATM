import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class WriteFile
{
	/**
	 * writeOutputFile:     sorts data of a BankInterface object and writes it to a file
	 *
	 * @param 		 String name of the output file
	 * @param BankInterface obj to be written to output file
	*/
    public static void writeOutputFile(String outputFileName, BankInterface bank)
    {
        PrintWriter outputStream = null;
        Bank2 myBank = null;

		// need to convert to Bank2 obj
        if (bank instanceof Bank2)
            myBank = (Bank2)bank;

        try
        {
			// define outStream to be able to write to outputFileName
            outputStream = new PrintWriter(new FileWriter(outputFileName));

			// sort and get accounts in Bank2 obj
            myBank.sortAccounts();
            ArrayList<Account> accounts = myBank.getAccounts();

			// for each Account in the Bank2 obj, it is written to the output file
            for( Account account : accounts)
            {
                outputStream.print(account.getName() + "," + account.getId() + "," + account.getBalance().getDollars() + "," + account.getBalance().getCents() + ",");

				// if Account is a Checking obj, adds the overdraft amount to file
                if (account instanceof Checking)
                {
                    Checking checkingAccount = (Checking)account;
                    outputStream.println("c" + "," + checkingAccount.getOverdraftMaximum().getDollars() + "," + checkingAccount.getOverdraftMaximum().getCents());
                }
                else
                    outputStream.println("r");
            }

			// closes file after writting everything
            outputStream.close();
            
        }
        catch (IOException e) 
        {
            System.out.println("Error copying file");
        } 
        finally 
        {
			// if exception was thrown during copying, makes sure output files is closed
            if (outputStream != null) 
                outputStream.close();
            
        } 

    }
}