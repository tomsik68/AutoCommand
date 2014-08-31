package sk.tomsik68.autocommand.args;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.lang.Validate;

import sk.tomsik68.autocommand.ContextArg;
import sk.tomsik68.autocommand.context.ContextParameterProvider;

public class ArgumentParsers {
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
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

    public Object[] parse(Class<?>[] classes, Annotation[][] annotations, Collection<ContextParameterProvider> contextParameterProviders,
            String commandArguments) throws ArgumentParserException {
        ArrayList<Object> resultList = new ArrayList<Object>();
        // 1) split up args also based on quotes
        String[] tokenizedArguments = tokenizer.tokenize(commandArguments);
        // 2) each class needs to be parsed from args list
        for (int i = 0; i < tokenizedArguments.length && i < classes.length - 1; i++) {
            String arg = tokenizedArguments[i];
            // classes[0] is always command sender
            Class<?> clz = classes[i + 1];
            try {
                Object obj = parse(arg, clz);
                resultList.add(obj);
            } catch (ArgumentParserException ape) {
                if (contextParameterProviders != null && !contextParameterProviders.isEmpty() && annotations[i + 1].length > 0
                        && annotations[i + 1][0] instanceof ContextArg) {
                    String[] providerArgs = ((ContextArg) annotations[i + 1][0]).value();
                    for (ContextParameterProvider paramProvider : contextParameterProviders) {
                        if (paramProvider.provides(clz, providerArgs)){
                            resultList.add(paramProvider.provide(clz, providerArgs));
                            // parse this parameter from command with different parser
                            --i;
                            break;
                        }
                    }
                } else
                    // throw the ape
                    throw ape;
            }
        }
        return resultList.toArray(EMPTY_OBJECT_ARRAY);
    }

    private Object parse(String arg, Class<?> clz) throws ArgumentParserException {
        if (!availableParsers.containsKey(clz))
            throw new ArgumentParserException("No parser registered for " + clz.getName());
        ArgumentParser parser = availableParsers.get(clz);
        return parser.parse(arg);

    }
}
