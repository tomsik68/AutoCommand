package sk.tomsik68.bukkit.autocommand.args;

import java.util.HashMap;

public class ArgumentParserChain {
    private HashMap<Class<?>, ArgumentParser> availableParsers = new HashMap<Class<?>, ArgumentParser>();

    public ArgumentParserChain() {

    }

    public Object[] parse(Class[] classes, String[] args) throws ArgumentParserException {
        return null;
    }
}
