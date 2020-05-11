import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Bank2 implements BankInterface
{
    private String nameOfBank;
    private int numOfAccounts;
    private ArrayList<Account> accounts;
    private Iterator<Account> iter;

    public Bank2(String theNameOfBank)
    {
        this.nameOfBank = theNameOfBank;
        this.numOfAccounts = 0;
        this.accounts = new ArrayList<Account>();

    }

    // addAccount() takes in an account and adds it to the accounts ArrayList
    public void addAccount(Account account)
    {
        this.accounts.add(account);
        this.numOfAccounts += 1;
    }

    // removeAccount() takes in an id of an account to remove and if possible does so, otherwise throws an excpetion
    public void removeAccount(String id)
    {
        Account account = this.search(id);
        
        if (account.equals(null))
            throw new AccountDoesNotExistException("Account with id " + id + " does not exist.");
        else
            this.accounts.remove(account);

    }

    // helper function to quickly look through ArrayList
    private int binarySearch(ArrayList<Account> anArray, int first, int last, String id) {

        // Searches the array items anArray.get(first) through
        // anArray.get(last) for id of account by using a binary search.
        // Precondition: 0 <= first, last <= SIZE-1, where
        // SIZE is the maximum size of the array, and
        // anArray.get(first) <= anArray.get(first+1) <= ... <= anArray.get(last).
        // Postcondition: If id of account is in the array, the method
        // returns the index of the array item that equals the id of that account;
        // otherwise the method returns -1.

        int index = -1;

        if (first > last) 
        {       
            index = -1;  // id not in original array
        } 
        else {
            // Invariant: If id is in anArray, 
            // anArray.get(first) <= id <= anArray.get(last)
            int mid = (first + last)/2;

            if (id.equals(anArray.get(mid).getId()))
                index = mid;  // id found at anArray.get(mid)
            else if (id.compareTo(anArray.get(mid).getId()) < 0) // if id entered is less than id at index mid
                index = binarySearch(anArray, first, mid-1, id);   // point X
            else                                                 // if id entered is greater than id at index mid
                index = binarySearch(anArray, mid+1, last, id);    // point Y
            // end if

        } // end if

        return index;
    }

     // search() takes in a String, the id of an account, searches through accounts and returns the account with that id
    public Account search(String id)
    {
        Account searchedAccount = null;

        // accounts ArrayList needs to be sorted before using the binary search
        this.sortAccounts();
        int index = binarySearch(this.accounts, 0, this.accounts.size()-1, id);

        // -1 if id isn't found in accounts and therefore returns a null account
        if (index == -1)
            return searchedAccount;

        // if id is found, account is gotten at that index and returned
        searchedAccount = this.accounts.get(index);
        return searchedAccount;
    }

    public String searchForAccountInfo(String id)
    {
        String accountInfo = "";
        Account account = this.search(id);

        if (account != null)
            return account.toString();
        else
            throw new AccountDoesNotExistException("Account with id " + id + " does not exist.");
        
    }


    // deposit() takes in an id string and an amount of money and deposits that money to the account with that id
    public void deposit(String id, Money amount)
    {
        Account depositAccount = this.search(id);

        depositAccount.deposit(amount);
    }

    // withdraw() take in an id string and an amount of money and withdraws that money from the account with that id
    public void withdraw(String id, Money amount)
    {
        Account withdrawAccount = this.search(id);

        withdrawAccount.withdraw(amount);
    }

    // toString() prints all the data of a Bank obj
    public String toString()
    {
        String result = this.nameOfBank + " Accounts:\n";
        this.iter = this.accounts.listIterator();

        while(iter.hasNext())
            result += iter.next() + "\n";

        return result;
    }

    // Sorts accounts in ascending order of their id
    public void sortAccounts()
    {
        Collections.sort(this.accounts);
    }


    public String getNameOfBank()
    {   
        return this.nameOfBank;
    }
    
    public int getNumOfAccounts()
    {
        return this.numOfAccounts;
    }

    public ArrayList<Account> getAccounts()
    {
        return this.accounts;
    }


}
