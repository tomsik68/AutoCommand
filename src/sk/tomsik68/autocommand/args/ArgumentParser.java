package sk.tomsik68.autocommand.args;

public interface ArgumentParser {
    public Object parse(String str) throws ArgumentParserException;
}
