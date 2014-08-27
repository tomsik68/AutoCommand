package sk.tomsik68.autocommand.err;

public class DefaultErrorMessageProvider implements IErrorMessageProvider {

    @Override
    public String invalidArgumentCount() {
        return "Invalid argument count!";
    }

    @Override
    public String noPermission() {
        return "You don't have permission to do that!";
    }

    @Override
    public String unknownCommand() {
        return "Unknown command!";
    }

    @Override
    public String unknownError() {
        return "Unknown error occured!";
    }

}
