import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccountTest
{
    private Account _account;

    public AccountTest()
    {

    }

    @Before
    public void setUp()
    {
        _account = new Account("Karl Muller", "1251", new Money(0, 0));
    }

    @After
    public void tearDown()
    {
        _account = null;
    }

	// Tests if the objects creates correctly
    @Test
    public void testCreate()
    {
		// First test: Checking name and id with zero balance initialized in object
        assertEquals("Error in testCreate", "Karl Muller", _account.getName());
        assertEquals("Error in testCreate", "1251", _account.getId());
        assertTrue("Error in testCreate", (new Money(0, 0)).equals( _account.getBalance()));

		// Second test: Checking name and id with no balance passed through
		Account account1 = new Account("Beans","90");

		assertEquals("Error in testCreate", "Beans", account1.getName());
        assertEquals("Error in testCreate", "90", account1.getId());
        assertTrue("Error in testCreate", (new Money(0, 0)).equals(account1.getBalance()));

		// Third test: Checking name and id with negative Money value
		Money dough = new Money(-1, -35);
		Account account2 = new Account("Marie", "29", dough);

		assertEquals("Error in testCreate", "Marie", account2.getName());
		assertEquals("Error in testCreate", "29", account2.getId());
		assertEquals("Error in testCreate", dough, account2.getBalance());

    }

	// Tests if the data of the objects format correctly
	@Test
	public void testToString()
	{
		// First Test: 0 balance
		// Expected Value
		String testString1 = "Name: Karl Muller\nID Number: 1251\nBalance: $0.00";

		assertEquals("Error in testToString", testString1, _account.toString());

		// Second Test: balance with cents less than 10
		Money dough = new Money(1, 5);
		Account account2 = new Account("Marie", "29", dough);

		// Expected Value
		String testString2 = "Name: Marie\nID Number: 29\nBalance: $1.05";

		assertEquals("Error in testToString", testString2, account2.toString());

		// Third Test: balance with cents greater than 10
		Account account3 = new Account("Richard", "59", new Money(87,98));

		// Expected Value
		String testString3 = "Name: Richard\nID Number: 59\nBalance: $87.98";

		assertEquals("Error in testToString", testString3, account3.toString());

	}

	// Tests if two accounts equal each other properly
	@Test
	public void testEquality()
	{
		// First Test: same everything with balance passed in constructor
		Account accSame = new Account("Karl Muller", "1251", new Money(0,0));

		assertTrue("Error in testEquality", _account.equals(accSame));	

		// Second Test: same everything with no balance passed through
		Account accountant = new Account("Beans", "78");
		Account accounting = new Account("Beans", "78");

		assertTrue("Error in testEquality", accountant.equals(accounting));
	}

	// Tests if unequal accounts are unequal
	@Test
	public void testInequality()
	{
		// Six tests that all have differences to original _account object
		Account sameNameDiffAll = new Account("Karl Muller","1252",new Money(1,0));
		Account sameIdDiffAll = new Account ("Marie", "1251", new Money(2,4));
		Account sameBalDiffAll = new Account ("Bob","34", new Money(0,0));
		Account sameNameAndIdDiffBal = new Account("Karl Muller","1251", new Money(9,23));
		Account sameNameAndBalDiffId = new Account ("Karl Muller", "67", new Money(0,0));
		Account sameBalAndIdDiffName = new Account ("Bobby", "1251", new Money(0,0));
		
		assertFalse("Error in testInequality",_account.equals(sameNameDiffAll));
		assertFalse("Error in testInequality",_account.equals(sameIdDiffAll));
		assertFalse("Error in testInequality",_account.equals(sameBalDiffAll));
		assertFalse("Error in testInequality",_account.equals(sameNameAndIdDiffBal));
		assertFalse("Error in testInequality", _account.equals(sameNameAndBalDiffId));
		assertFalse("Error in testInequality",_account.equals(sameBalAndIdDiffName));
	}

	// Tests if money is correctly deposited into an account
	@Test
	public void testDeposit()
	{
		// First Test: deposit money with 0 balance start
		Money paycheck = new Money(500, 95);
		
		_account.deposit(paycheck);
		
		assertTrue("Error in testDeposit", paycheck.equals(_account.getBalance()));

		// Second Test: deposit money with balance already in account
		Account offshore = new Account("Senator", "50", new Money(200,84));
		Money bribe = new Money(1250, 16);
		
		offshore.deposit(bribe);

		// Expected Value
		Money expected = new Money(1451,0);
		
		assertTrue("Error in testDeposit", expected.equals(offshore.getBalance())); 
	}

	// Tests if money is properly withdrawn
	@Test 
	public void testWithdraw()
	{
		// First Test: withdraw more than is in account so balance is negative
		Money bouncedPaycheck = new Money(500, 95);
		
		_account.withdraw(bouncedPaycheck);

		// Expected Value
		Money expected = new Money(-500,-95);
		
		assertTrue("Error in testDeposit", expected.equals(_account.getBalance()));

		// Second Test: withdraw amount that doesn't make balance negative
		Account offshore = new Account("Senator", "50", new Money(200,84));
		Money payoff = new Money(150, 16);
		
		offshore.withdraw(payoff);

		// Expected Value
		expected = new Money(50,68);
		
		assertTrue("Error in testDeposit", expected.equals(offshore.getBalance()));
	}

	// Tests if money properly transfers between accounts
	@Test
	public void testTransfer()
	{
		// First Test: Make one account completely negative while the other gains double
		Money rentCheck = new Money(1250,55);
		Account owner = new Account("Bob","90",new Money(1250,55));

		_account.transfer(owner, rentCheck);

		// Expected Values
		Money _accountExpected = new Money(-1250,-55);
		Money ownerExpected = new Money(2501,10);

		assertTrue("Error in testTransfer", _accountExpected.equals(_account.getBalance()));
		assertTrue("Error in testTransfer", ownerExpected.equals(owner.getBalance()));

		// Second Test: Take money from one account and gives it to the other while both stay positive
		Account buyer = new Account("Cisco", "101", new Money(5555,96));
		Account seller = new Account("Robert", "109", new Money(2575,85));
		Money yachtCost = new Money(5499,99);

		buyer.transfer(seller, yachtCost);

		// Expected Values
		Money buyerExpected = new Money(55, 97);
		Money sellerExpected = new Money(8075,84);

		assertTrue("Error in testTransfer", buyerExpected.equals(buyer.getBalance()));
		assertTrue("Error in testTransfer", sellerExpected.equals(seller.getBalance()));
	}


}