package sk.tomsik68.bukkit.autocommand.err;

import sk.tomsik68.bukkit.autocommand.CustomCommandExecutor;

public class InvalidArgumentCountException extends CommandExecutionException {

    public InvalidArgumentCountException(CustomCommandExecutor exec) {
        super(exec);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -6528484433729923318L;

}
