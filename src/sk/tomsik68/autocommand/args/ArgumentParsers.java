package sk.tomsik68.autocommand.args;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.Validate;

public class ArgumentParsers {
    private HashMap<Class<?>, ArgumentParser> availableParsers = new HashMap<Class<?>, ArgumentParser>();
    private ArgumentTokenizer tokenizer;

    public ArgumentParsers(ArgumentTokenizer tok) {
        tokenizer = tok;
        BasicParsers.registerBasicParsers(this);
    }

    public void registerArgumentParser(Class<?> clazz, ArgumentParser parser) {
        Validate.notNull(clazz);
        Validate.notNull(parser);
        availableParsers.put(clazz, parser);
    }

    public Object[] parse(Class<?>[] classes, String[] args) throws ArgumentParserException {
        ArrayList<Object> resultList = new ArrayList<Object>();
        // 1) split up args also based on quotes
        args = tokenizer.retokenize(args);
        // 2) each class needs to be parsed from args list
        for (int i = 0; i < args.length && i < classes.length - 1; i++) {
            String arg = args[i];
            // classes[0] is always command sender
            Class<?> clz = classes[i + 1];
            Object obj = parse(arg, clz);
            resultList.add(obj);
        }
        return resultList.toArray(new Object[0]);
    }

    private Object parse(String arg, Class<?> clz) throws ArgumentParserException {
        if (!availableParsers.containsKey(clz))
            throw new ArgumentParserException("No parser registered for " + clz.getName());
        ArgumentParser parser = availableParsers.get(clz);
        return parser.parse(arg);

    }
}
