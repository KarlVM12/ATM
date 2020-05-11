import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class BankTest
{

    private Bank _bank;

    @Before
    public void setUp()
    {
        _bank = new Bank("TD Ameritrade");
    }

    @After
    public void tearDown()
    {
        _bank = null;
    }

    // Tests if a Bank obj is correctly initialized
    @Test
    public void testCreation()
    {
        // First Test
        Bank newBank = new Bank("TD Ameritrade");

        assertTrue("Error in testCreation", newBank.getNameOfBank().equals(_bank.getNameOfBank()));   
        assertEquals("Error in testCreation", newBank.getNumOfAccounts(), _bank.getNumOfAccounts());

        // Second Test
        Bank BoA = new Bank("Bank of America");
		String expected = "Bank of America";

        assertTrue("Error in testCreation", (BoA.getNameOfBank()).equals(expected));
        assertEquals("Error in testCreation", BoA.getNumOfAccounts(), 0);

    }

    // Tests if the data of a Bank obj is properly formatted and correct
    @Test   
    public void testToString()
    {
        // First Test: toString while only using constructor of obj
        String expected = "TD Ameritrade Accounts:\n";
        assertTrue("Error in testToString", _bank.toString().equals(expected));

        // Second Test: toString with an account added
        Bank chase = new Bank("Chase");
        chase.addAccount(new Account("Karl","1251", new Money(90, 89)));

        // Expected string
        String expectedChase = "Chase Accounts:\nName: Karl\nID Number: 1251\nBalance: $90.89\n";

        assertTrue("Error in testToString", chase.toString().equals(expectedChase));

    }

    // Tests whether accounts are properly added to a Bank obj
    @Test
    public void testAddAccount()
    {
        // First Test: seeing if one account is properly added
        Bank chase = new Bank("Chase");
        chase.addAccount(new Account("Karl","1251", new Money(90, 89)));

        // Expected accounts array
        Account[] accounts = {new Account("Karl","1251", new Money(90,89))};

        assertTrue("Error in testAddAccount", accounts.equals(chase.getAccounts()));

        // Second Test: seeing if two or more accounts are properly added to Bank obj
        Bank apple = new Bank("Apple Bank");

        Account k1251 = new Account("Karl","1251", new Money(899,99));
        apple.addAccount(k1251);

        Account m99 = new Account("Marie","99", new Money(233,4));
        apple.addAccount(m99);

        // Expected accounts array in apple obj
        Account[] appleAccounts = {k1251, m99};

        assertTrue("Error in testAddAccount", appleAccounts.equals(apple.getAccounts()));

    }
    
    // Tests if a Bank obj properly searches and finds the right account
    @Test
    public void testSearch()
    {
        // Test: add multiple accounts and see if proper account is found
		Bank apple = new Bank("Apple Bank");

        Account k1251 = new Account("Karl","1251", new Money(899,99));
        apple.addAccount(k1251);

        Account m99 = new Account("Marie","99", new Money(233,4));
        apple.addAccount(m99);

		assertTrue("Error in testSearch", k1251.equals(apple.search("1251")));
		assertTrue("Error in testSearch", m99.equals(apple.search("99")));
    }

    // Tests if money is deposited correctly to the right account
    @Test
    public void testDeposit()
    {
        // Test: deposit money to two accounts in same Bank and see if balance is properly changed
		Bank apple = new Bank("Apple Bank");

        Account k1251 = new Account("Karl","1251", new Money(899,99));
        apple.addAccount(k1251);
		apple.deposit("1251", new Money(101, 1));

        Account m99 = new Account("Marie","99", new Money(233,4));
		Money paycheck = new Money(332, 40);
        apple.addAccount(m99);
		apple.deposit("99", paycheck);

		assertTrue("Error in testDeposit", k1251.getBalance().equals(new Money(1001,0)));
		assertTrue("Error in testDeposit", m99.getBalance().equals(new Money(565,44)));

    }

    // Tests if money is properly withdraw and correct from a certain account
    @Test
    public void testWithdraw()
    {
        // Test: withdraw money from two accounts in same Bank and see if proper balance left
		Bank chase = new Bank("Chase");

		Account k2511 = new Account("Kyle","2511", new Money(0,0));
		chase.addAccount(k2511);
		chase.withdraw("2511", new Money(98, 74));

		Account m452 = new Account("Maclachlan", "452", new Money(78, 6));
		chase.addAccount(m452);
		chase.withdraw("452", new Money(65, 20));

		assertTrue("Error in testWithdraw", k2511.getBalance().equals(new Money(-98,-74)));
		assertTrue("Error in testWithdraw", m452.getBalance().equals(new Money(12,86)));

    }

    // Tests if accounts are properly sorted
    @Test
    public void testSortAccounts()
    {
        Bank cityBank = new Bank("City Bank");
        
        Account k5 = new Account("Karl","5", new Money(899,99));
        cityBank.addAccount(k5);

        Account m9 = new Account("Marie","9", new Money(233,4));
        cityBank.addAccount(m9);

        Account h1 = new Account("Helen","1", new Money(55,55));
        cityBank.addAccount(h1);

        Account m4 = new Account("Mike", "4", new Money(77,88));
        cityBank.addAccount(m4);

		Account t7 = new Account("Tim", "7", new Money(3, 0));
		cityBank.addAccount(t7);

        Account[] expectedArray = new Account[10];
		expectedArray[0] = h1;
		expectedArray[1] = m4;
		expectedArray[2] = k5;		
        expectedArray[3] = t7;
		expectedArray[4] = m9;

        assertEquals("Error in testSortAccounts", expectedArray,cityBank.getAccounts());
        
    }

}