import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// Test the Money class. 

public class MoneyTest 
{
    private Money _amount;

    /**
     * Default constructor for test class MoneyTest
     */
    public MoneyTest()
    {
        //System.out.println("JUnit Framework calls Constructor of test class before executing test methods");
    }
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        _amount = new Money(0, 0);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() {
        _amount = null;
    }

    /**
     * Test methods 
     */
    
    // Test creation of Money objects.
    @Test
    public void testCreate()
    {
         assertEquals("Error in testCreate", 0, _amount.getDollars());
         assertEquals("Error in testCreate", 0, _amount.getCents());
         
         Money amount2 = new Money (4, 50);
         
         assertEquals("Error in testCreate", 4, amount2.getDollars());
         assertEquals("Error in testCreate", 50, amount2.getCents());
         
         Money amount3 = new Money (-4, -50);
         
         assertEquals("Error in testCreate", -4, amount3.getDollars());
         assertEquals("Error in testCreate", -50, amount3.getCents());
         
    }
   
    // Test conversion of Money object to String.
    @Test
    public void testToString()
    {
        // First test: cents is two digits
        Money amount = new Money (7, 55);
        String actual = amount.toString();
        String expected = "$7.55";
        assertTrue("Error in testToString", actual.equals(expected));
        
        // Second test: cents is one digit
        Money amount2 = new Money (7, 5);
        String actual2 = amount2.toString();
        String expected2 = "$7.05";
        assertTrue("Error in testToString", actual2.equals(expected2));
    }
    
    // Test equality of money values that are the same.
    @Test
    public void testEquality()
    {
        Money myCash = new Money (3, 75);
        Money yourCash = new Money (3, 75);
        
        assertTrue ("Error in testEquality", myCash.equals(yourCash));
        
        Money myAmount = new Money (50, 0);
        Money yourAmount = new Money (50, 0);
        
        assertTrue ("Error in testEquality", myAmount.equals(yourAmount));
    }
    
    // Test inequality of money values that are different.
    @Test
    public void testInequality()
    {
        Money myCash = new Money (3, 75);
        Money difftDollarsSameCents = new Money (4, 75);    
        Money difftDollarsDifftCents = new Money (4, 80);   
        Money sameDollarsDifftCents = new Money (3, 80);   
        
        assertFalse ("Error in testInequality", myCash.equals(difftDollarsSameCents));
        assertFalse ("Error in testInequality", myCash.equals(difftDollarsDifftCents));
        assertFalse ("Error in testInequality", myCash.equals(sameDollarsDifftCents));
    }
    
    // Test addition of money values such that the sum of the cents do not exceed 99.
    @Test
    public void testSimpleAdd()
    {
       Money amount2 = new Money (2, 30);
       Money amount3 = new Money (3, 50);
       
       Money actualAmount = amount2.add (amount3);
       // actualAmount now has the sum of amount2 + amount 3
       
       // Expected result is $5.80
       Money expectedAmount = new Money (5, 80);
       
       assertTrue ("Error in testSimpleAdd", actualAmount.equals(expectedAmount));
       //assertEquals("Error in testSimpleAdd", 5, actualAmount.getDollars());
       //assertEquals("Error in testSimpleAdd", 80, actualAmount.getCents());
    }
    
    // Test complex addition  of two money values, i.e. sum of cents is greater than or equal to 100.
    @Test
    public void testComplexAdd()
    {
        // First test: sum of cents is 100.
        
        Money myCash = new Money (3, 50);
        Money yourCash = new Money (4, 50);            
        
        // Expected result is $8.00
        Money expectedAmount = new Money (8, 0);
       
        Money actualAmount = myCash.add(yourCash);
        
        //System.out.println (actualAmount.toString()); // just for tracing purposes
        
        assertTrue ("Error in testComplexAdd", actualAmount.equals(expectedAmount));     
        
        // Second test: sum of cents is greater than 100...

        Money myAmount = new Money (3, 56);
        Money yourAmount = new Money (4, 52);

        // Expected result is $8.08
        Money myExpected = new Money (8, 8);

        Money myActual = myAmount.add(yourAmount);

        assertTrue ("Error in testComplexAdd", myActual.equals(myExpected));
        
    }

    // Test subtraction less than 100
    @Test
    public void testSimpleSubtract()
    {
        Money myCash = new Money(3, 20);
        Money yourCash = new Money(12);

        // Expected amount is $3.08
        Money expectedAmount = new Money (3, 8);

        Money actualAmount = myCash.subtract(yourCash);

        assertTrue("Error in testSimpleSubtract", actualAmount.equals(expectedAmount));
        

    }

    // Test subtraction greater or equal to 100
    @Test
    public void testComplexSubtract()
    {
        // First test: subtract 100 cents
        Money myCash = new Money(3, 20);
        Money yourCash = new Money(1, 0);

        // Expected Result is $2.20
        Money expectedCash = new Money(2, 20);
        Money actualCash = myCash.subtract(yourCash);

        assertTrue("Error in testComplexSubtract", actualCash.equals(expectedCash));

        // Second test: subtract lesser from greater amount for more than 100 cents
        Money myAmount = new Money(3, 20);
        Money yourAmount = new Money(2, 65);

        // Expected Result is $0.55
        Money expectedAmount = new Money(55);
        Money actualAmount = myAmount.subtract(yourAmount);

        assertTrue("Error in testComplexSubtract", actualAmount.equals(expectedAmount));

        // Third test: subtract greater from lesser for more than 100 cents
        Money myMoney = new Money(3, 20);
        Money yourMoney = new Money(4, 55);

        // Expected Result is -$1.35
        Money expectedMoney = new Money(-1, -35);
        Money actualMoney = myMoney.subtract(yourMoney);

        assertTrue("Error in testComplexSubtract", actualMoney.equals(expectedMoney));

    }

    // Test comparing amounts
    @Test
    public void testCompareTo()
    {
        // First test: same amounts
        Money myCash = new Money(3, 20);
        Money yourCash = new Money(3, 20);

        // Expected Result is 0, same
        int expectedResult = 0;
        int actualResult = myCash.compareTo(yourCash);

        assertEquals("Error in testCompareTo", expectedResult, actualResult);

        // Second test: this obj amount greater than other amount
        Money myAmount = new Money(4, 50);
        Money yourAmount = new Money(3, 30);

        // Expected Result is 1, greater than other
        expectedResult = 1;
        actualResult = myAmount.compareTo(yourAmount);

        assertEquals("Error in testCompareTo", expectedResult, actualResult);

        // Third test: this obj amount less than other amount 
        Money myMoney = new Money(3, 10);
        Money yourMoney = new Money(4, 40);

        // Expected Result is -1, less than other
        expectedResult = -1;     
        actualResult = myMoney.compareTo(yourMoney);

        assertEquals("Error in testCompareTo", expectedResult, actualResult);
            
    }

}



