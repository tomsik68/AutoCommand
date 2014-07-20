package sk.tomsik68.bukkit.autocommand.err;

public interface IErrorMessageProvider {
    public String invalidArgumentCount();

    public String noPermission();

    public String unknownCommand();

    public String unknownError();
}
