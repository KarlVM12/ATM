import java.util.ArrayList;

public interface BankInterface
{

    // addAccount() takes in an account and adds it to the accounts array
    public abstract void addAccount(Account account);

    // removeAccount() takes in an id and removes the account if it exists
    public abstract void removeAccount(String id);

    // search() takes in a String, the id of an account, searches through accounts and returns the account with that id
    public abstract Account search(String id);

	// searchForAccountInfo() searches for one account's data based on the id and returns it
    public abstract String searchForAccountInfo(String id);

    // deposit() takes in an id string and an amount of money and deposits that money to the account with that id
    public abstract void deposit(String id, Money amount);

    // withdraw() take in an id string and an amount of money and withdraws that money from the account with that id
    public abstract void withdraw(String id, Money amount);

	// sortAaccounts() sorts the accounts in an obj
    public abstract void sortAccounts();

    // toString() prints all the data of a Bank obj
    public abstract String toString();

    public abstract String getNameOfBank();
    
    public abstract int getNumOfAccounts();

}