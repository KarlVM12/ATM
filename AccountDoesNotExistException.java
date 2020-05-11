public class AccountDoesNotExistException extends RuntimeException
{
    public AccountDoesNotExistException(String s)
    {
        super(s);
    }
}