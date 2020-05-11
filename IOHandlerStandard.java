import java.util.Scanner;
/**
 * IOHandlerStandard: uses standard IO for input (via get method) and output (via put method).
 * Note: exceptions are potentially thrown by methods of Scanner; refer to documentation in Java standard library. 
 */
public class IOHandlerStandard implements IOHandlerInterface
{
    // Attributes
    private Scanner input;
    
    /**
     * constructor: 
     * @param none
     */
    public IOHandlerStandard()
    {
        input = new Scanner (System.in);
    }
    
    /**
     * get: 
     * @param String, prompt to display to standard output destination.
     * @return String, user input from standard input source.
     */
    @Override
    public String get(String prompt)
    {
         System.out.println (prompt);
         return input.next();
    } // end get
    
    /**
     * put: 
     * @param String to write to the selected destination.
     */
    @Override
    public void put(String output)
    {
         System.out.println (output);
    } // end put

	/**
	 * multipleChoices: allows user to pick from certain choices
	 * @param String[] string array of choice
	 * @return String returns choice of user
     */
	@Override
    public String multipleChoices(String[] choices)
    {
		this.put("Possible transactions:");
		for(int i = 0;i <= choices.length; i++)
			System.out.print(choices[i] + " ");
		String choice = this.get("Choose one:");

		return choice;
		
	}
    
} // end IOHandlerStandard
