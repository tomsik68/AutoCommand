package sk.tomsik68.autocommand.err;


public class InvalidArgumentCountException extends CommandExecutionException {

    public InvalidArgumentCountException(String correctUsage) {
        super(correctUsage);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -6528484433729923318L;

}
