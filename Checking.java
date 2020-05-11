public class Checking extends Account
{
    // Overdraft amount accepted is $25
    private Money overdraftMaximum;

    // Constructor with no balance passed
    public Checking(String theName, String theId, Money theOverdraftMax)
    {
        super(theName, theId, new Money(0,0));
        this.overdraftMaximum = theOverdraftMax;

    }

    // Constructor with Money balance passed 
    public Checking(String theName, String theId, Money theBalance, Money theOverdraftMax)
    {
        super(theName, theId, theBalance);
        this.overdraftMaximum = theOverdraftMax;
    }

    // Overridden withdraw() method checks if amount withdraw doesn't go above certain amount
    @Override
    public void withdraw(Money amount)
    {
        Money balance = super.getBalance();
        Money overdraftAmount = balance.add(this.overdraftMaximum);

        // compareTo() returns a 0 if equal and -1 if less than therefore <= 0 works
        if(amount.compareTo(overdraftAmount) <= 0)
            super.withdraw(amount);
		else
			throw new InsufficientFundsException("Invalid Amount. Insufficient Funds.");

    }

    public Money getOverdraftMaximum()
    {
        return this.overdraftMaximum;
    }

    @Override
    public String toString()
    {
        String result = super.toString();
        result += "\nOverdraft Maximum Amount: " + this.overdraftMaximum;

        return result;
    }
}