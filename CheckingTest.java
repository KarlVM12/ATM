import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CheckingTest
{
    private Checking _checkingAcc;

    @Before
    public void setUp()
    {
        _checkingAcc = new Checking("Karl Muller", "1251", new Money(0,0), new Money(25,0));
    }

    @After
    public void tearDown()
    {
        _checkingAcc = null;
    }

    // Test if checkings account created properly
    @Test
    public void testCreation()
    {
        // First test: Checking name and id with zero balance initialized in object
        assertEquals("Error in testCreation", "Karl Muller", _checkingAcc.getName());
        assertEquals("Error in testCreation", "1251", _checkingAcc.getId());
        assertTrue("Error in testCreation", (new Money(0, 0)).equals( _checkingAcc.getBalance()));
		assertTrue("Error in testCreation", _checkingAcc.getOverdraftMaximum().equals(new Money(25,0)));

		// Second test: Checking name and id with no balance passed through
		Checking checkAccount1 = new Checking("Beans","90", new Money(34, 99));

		assertEquals("Error in testCreation", "Beans", checkAccount1.getName());
        assertEquals("Error in testCreation", "90", checkAccount1.getId());
        assertTrue("Error in testCreation", (new Money(0, 0)).equals(checkAccount1.getBalance()));
		assertTrue("Error in testCreation", (new Money(34, 99).equals(checkAccount1.getOverdraftMaximum())));

		// Third test: Checking name and id with negative Money value
		Money dough = new Money(-1, -35);
		Money overdraft = new Money(15, 50);
		Checking checkingsAccount = new Checking("Marie", "29", dough, overdraft);

		assertEquals("Error in testCreation", "Marie", checkingsAccount.getName());
		assertEquals("Error in testCreation", "29", checkingsAccount.getId());
		assertEquals("Error in testCreation", dough, checkingsAccount.getBalance());
		assertTrue("Error in testCreation", (new Money(15,50).equals(checkingsAccount.getOverdraftMaximum())));

    }

	// test the toString fot Checking
    @Test
    public void testToString()
    {
        // First Test: 0 balance, $25 overdraft
		// Expected Value
		String testString1 = "Name: Karl Muller\nID Number: 1251\nBalance: $0.00\nOverdraft Maximum Amount: $25.00";

		assertEquals("Error in testToString", testString1, _checkingAcc.toString());

		// Second Test: balance with cents less than 10, $25 overdraft
		Money dough = new Money(1, 5);
		Checking account2 = new Checking("Marie", "29", dough, new Money(25,0));

		// Expected Value
		String testString2 = "Name: Marie\nID Number: 29\nBalance: $1.05\nOverdraft Maximum Amount: $25.00";

		assertEquals("Error in testToString", testString2, account2.toString());

		// Third Test: balance with cents greater than 10, $34.99 overdraft
		Checking account3 = new Checking("Richard", "59", new Money(87,98),new Money(34,99));

		// Expected Value
		String testString3 = "Name: Richard\nID Number: 59\nBalance: $87.98\nOverdraft Maximum Amount: $34.99";

		assertEquals("Error in testToString", testString3, account3.toString());

    }

    // Tests if overridden withdraw method works in Checking
    @Test
    public void testWithdraw()
    {
        // First Test: withdraw an amount below current balance
		Money overdraft = new Money(25,0);
        Checking k2511 = new Checking("Kyle","2511", new Money(762,34),overdraft);
        k2511.withdraw(new Money(134, 34));

        // Expected amount
        Money expectedK2511 = new Money(628,0);

        assertTrue("Error in testWithdraw", expectedK2511.equals(k2511.getBalance()));
		
        // Second Test: withdraw an amount of the exact balance
		Checking m452 = new Checking("Maclachlan", "452", new Money(78, 6),overdraft);
        m452.withdraw(new Money(78, 6));

        // Expected amount
        Money expectedM452 = new Money(0,0);

        assertTrue("Error in testWithdraw", expectedM452.equals(m452.getBalance()));

        // Third Test: withdraw an amount above current balance, but below overdraft maximum 
        Checking b111 = new Checking("BOB", "111", new Money(99,99),overdraft);
        b111.withdraw(new Money(111, 50));

        // Expected amount
        Money expectedB111 = new Money(-11,-51);

        assertTrue("Error in testWithdraw", expectedB111.equals(b111.getBalance()));

    }

	// Testing if InsufficientFundsException is properly thrown and caught
	@Test
	public void testOverWithdraw()
	{
		Checking check = new Checking("Karl","99", new Money(40,0), new Money(25, 0));

		Money tooMuch = new Money(65,1);

		try 
		{ 
			check.withdraw(tooMuch);

			fail();		
		}
		catch (InsufficientFundsException ife)
		{
			System.out.println("InsufficientFunds Exception on testOverWithdraw");
			ife.printStackTrace();
		}
	} 

}