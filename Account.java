//import java.util.Comparable;
public class Account implements Comparable
{
    // Class attributes
    private String name, id;
    private Money balance;

    // Constructor: if no balance is based through, but name and id is
    public Account(String theName, String theid)
    {
        this.name = theName;
        this.id = theid;
        balance = new Money(0, 0);
    }

    // Constructor: name, id, and balance passed through
    public Account(String theName, String theid, Money theBalance)
    {
        this.name = theName;
        this.id = theid;
        balance = theBalance;
    }

    // getName(): return name
    public String getName()
    {
        return this.name;
    }

    // getId(): returns id
    public String getId()
    {
        return this.id;
    }

    // getBalance(): returns balance
    public Money getBalance()
    {
        return this.balance;
    }

    // deposit(): adds money to the balance of this account
    public void deposit(Money that)
    {
		this.balance = this.balance.add(that);

    }

    // withdraw(): takes money away from the balance of this account
    public void withdraw(Money that)
    {
		this.balance = this.balance.subtract(that);
    }

    // transfer(): takes money away from this account and gives that amount to another account
    public void transfer(Account other, Money that)
    {
        this.balance = this.balance.subtract(that);
        other.balance = other.balance.add(that);
    }

    // toString(): returns the data of the object formatted
    public String toString()
    {
        String result = "Name: " + this.name;
        result += "\nID Number: " + this.id;
        result += "\nBalance: " + this.balance;
        return result;
    }

    // equals(): compares two accounts to see if they are the same
    public boolean equals(Account that)
    {
        return (this.name.equals(that.name) && this.id.equals(that.id) && this.balance.equals(that.balance));
    }

    // compareTo has to be overridden as a result of implementing comparable
    @Override
    public int compareTo(Object o)
    {
        int result = 0;
        if (o instanceof Account)
        {
            Account acc = (Account)o;

            if (this.id.compareTo(acc.id)==0)
                result = 0;
            else if (this.id.compareTo(acc.id) < 0)
                result = -1;
            else if (this.id.compareTo(acc.id) > 0)
                result = 1;                
        }

        return result;
    }

}