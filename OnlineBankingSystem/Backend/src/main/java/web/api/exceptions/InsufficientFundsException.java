package web.api.exceptions;

public class InsufficientFundsException extends Exception{
    private String message;

    public InsufficientFundsException(String message){
        super(message);
    }
}
