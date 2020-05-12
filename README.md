# ATM
A working ATM based on classes of banks, accounts, and money using JOptionPane

### How To Use
Running the ATM.class will bring up a dialogue box using JOptionPane that asks for user input.
There are only certain valid inputs you can enter based on the in_accounts.txt file.
There are five accounts in in_accounts.txt that are read into the program and assigned to a bank class.
Valid input counts as one of the id numbers in the five accounts.

### Transactions
Once a valid id is entered, you will be able to interact with a certain account and perform certain actions.
You have five choices to choose from: Check Balance, Deposit, Withdraw, Access Another Account, and Exit ATM.

### Output File
If the user chooses to stop all transactions or exit the ATM, the data in a bank class will be sorted and
written to an output file titled out_accounts.txt

## Alternate I/O system
The ATM is implemented in a way so that dialogue boxes are used via JOptionPane. However, a cmd line i/o system
is also available to use. Both i/o systems implement IOHandlerInterface and thus can be changed by swapping 
IOHandlerDialog with IOHandlerStandard or vice versa.
