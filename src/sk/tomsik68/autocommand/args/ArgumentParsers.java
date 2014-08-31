package sk.tomsik68.autocommand.args;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.lang.Validate;

import sk.tomsik68.autocommand.ContextArg;
import sk.tomsik68.autocommand.context.CommandExecutionContext;
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

    public Object[] parse(CommandExecutionContext context, Method method, Collection<ContextParameterProvider> contextParameterProviders,
            String commandArguments) throws ArgumentParserException {
        Class<?>[] classes = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();
        ArrayList<Object> resultList = new ArrayList<Object>();
        // 1) split up args also based on quotes
        String[] tokenizedArguments = tokenizer.tokenize(commandArguments);
        // 2) each class needs to be parsed from args list
        for (int argumentNumber = 0, methodParamNumber = 1; methodParamNumber < classes.length; argumentNumber++, methodParamNumber = Math.max(1, methodParamNumber + 1)) {
            String arg;
             try{
                 arg = tokenizedArguments[argumentNumber];
             }catch(ArrayIndexOutOfBoundsException e123){
                 arg = "";
             }
            // classes[0] is always command sender
            Class<?> clz = classes[methodParamNumber];
            try {
                Object obj = parse(arg, clz);
                resultList.add(obj);
            } catch (ArgumentParserException ape) {
                if (contextParameterProviders != null && !contextParameterProviders.isEmpty() && annotations[methodParamNumber].length > 0
                        && annotations[methodParamNumber][0] instanceof ContextArg) {
                    String[] providerArgs = ((ContextArg) annotations[methodParamNumber][0]).value();
                    for (ContextParameterProvider paramProvider : contextParameterProviders) {
                        if (paramProvider.provides(context, clz, providerArgs)) {
                            resultList.add(paramProvider.provide(context, clz, providerArgs));
                            // parse this argument again...
                            --argumentNumber;
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
