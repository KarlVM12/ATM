public class Bank implements BankInterface
{
    // Name of bank, accounts array, number of accounts, amnt of accounts bank holds
    private String nameOfBank;
    private Account[] accounts;
    private int numOfAccounts;
    private final int MAX_NUM_OF_ACCOUNTS = 10;

    // Constructor takes in name of bank, sets numOfAccounts to 0, and allocates memory for accounts[]
    public Bank(String theNameOfBank)
    {
        this.nameOfBank = theNameOfBank;
        this.numOfAccounts = 0;
        this.accounts = new Account[MAX_NUM_OF_ACCOUNTS];
    }

    // addAccount() takes in an account and adds it to the accounts array
    public void addAccount(Account account)
    {
        accounts[numOfAccounts] = account;
        numOfAccounts += 1;
    }

    // search() takes in a String, the id of an account, searches through accounts[] and returns the account with that id
    public Account search(String id)
    {

        Account accountFound = null;
        
        for(int i = 0; i < numOfAccounts; i ++){
            if (accounts[i].getId().equals(id)){
                accountFound = accounts[i];
            }
        }
        return accountFound;
    }

    // deposit() takes in an id string and an amount of money and deposits that money to the account with that id
    public void deposit(String id, Money amount)
    {
        Account accountToDepositTo = this.search(id);
        accountToDepositTo.deposit(amount);
    }

    // withdraw() take in an id string and an amount of money and withdraws that money from the account with that id
    public void withdraw(String id, Money amount)
    {
        Account accountToWithdrawFrom = this.search(id);
        accountToWithdrawFrom.withdraw(amount);
    }

    // toString() prints all the data of a Bank obj
    public String toString()
    {
        String result = this.nameOfBank + " Accounts:\n";
        for(int i = 0; i < numOfAccounts; i++){
            result += accounts[i].toString() + "\n";
        }

        return result;
    }

    public String getNameOfBank()
    {
        return this.nameOfBank;
    }

    public int getNumOfAccounts()
    {
        return this.numOfAccounts;  
    }

    public Account[] getAccounts()
    {
        return this.accounts;
    }

    public void sortAccounts()
    {
        SortsClass.insertionSort(accounts, numOfAccounts);
    }

	// searches for all the account info for one account
	public String searchForAccountInfo(String id)
	{
		String accountInfo = "";
        Account account = this.search(id);

        if (account != null)
            return account.toString();
        else
            throw new AccountDoesNotExistException("Account with id " + id + " does not exist.");
	}

	// removes an account from the accounts array
	public void removeAccount(String id)
	{
		Account account = this.search(id);

		if (account.equals(null))
			throw new AccountDoesNotExistException("Account with id" + id + " does not exist");
		else
			for(int i = 0; i <= numOfAccounts;i++)
				if (accounts[i].equals(account))
					accounts[i] = null;

	}

}