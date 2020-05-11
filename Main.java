public class Main
{
    public static void main(String[] beans)
    {
        /* Checking acc = new Checking("karl", "1251", new Money(40,0));
        acc.withdraw(new Money(66,0)); */

        /* Bank2 newBank = new Bank2("City Bank");
        Account beans1 = new Account("k","18",new Money (999,99));
        Account moreBeans = new Account("m", "129", new Money(84,9));
        newBank.addAccount(moreBeans);
        newBank.withdraw("129", new Money(84,0));
        newBank.addAccount(beans1);
        newBank.deposit("18", new Money(100));
        System.out.println(newBank);
        newBank.sortAccounts();
        System.out.println(newBank); */
		Bank2Test test = new Bank2Test();
		test.setUp();
		test.testCreation();
		test.tearDown();
		test.setUp();
		test.testToString();
		test.tearDown();
		test.testAddAccount();
		test.testSearch();
		test.testDeposit();
		test.testWithdraw();
		test.testSortAccounts();
        
		CheckingTest newTest = new CheckingTest();
		newTest.setUp();
		newTest.testCreation();
		newTest.tearDown();
		newTest.testWithdraw();
		newTest.testOverWithdraw();
		newTest.setUp();
		newTest.testToString();
		newTest.tearDown();

		String[] s = {""};
		ATM.main(s);
    }
}