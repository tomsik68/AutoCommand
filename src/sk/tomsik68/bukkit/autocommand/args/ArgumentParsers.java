package sk.tomsik68.bukkit.autocommand.args;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.Validate;

public class ArgumentParsers {
    private static HashMap<Class<?>, ArgumentParser> availableParsers = new HashMap<Class<?>, ArgumentParser>();

    private ArgumentParsers() {

    }

    public static void registerArgumentParser(Class<?> clazz, ArgumentParser parser) {
        Validate.notNull(clazz);
        Validate.notNull(parser);
        availableParsers.put(clazz, parser);
    }

    public static Object[] parse(Class<?>[] classes, String[] args) throws ArgumentParserException {
        ArrayList<Object> resultList = new ArrayList<Object>();
        // 1) split up args also based on quotes
        args = getArgumentsFromArray(args);
        // 2) each class needs to be parsed from args list
        for (int i = 0; i < args.length && i < classes.length-1; i++) {
            String arg = args[i];
            // classes[0] is always command sender
            Class<?> clz = classes[i + 1];
            Object obj = parse(arg, clz);
            resultList.add(obj);
        }
        return resultList.toArray(new Object[0]);
    }

    private static Object parse(String arg, Class<?> clz) throws ArgumentParserException {
        if (!availableParsers.containsKey(clz))
            throw new ArgumentParserException("No parser registered for " + clz.getName());
        ArgumentParser parser = availableParsers.get(clz);
        return parser.parse(arg);

    }

    static String[] getArgumentsFromArray(String[] args) {
        ArrayList<String> resultList = new ArrayList<String>();
        // join arguments into 1 string
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb = sb.append(arg).append(' ');
        }
        // split up arguments once again respecting quotes
        String argsString = sb.toString();
        int index = 0;
        String tempArg = "";
        boolean inQuotes = false;
        while (index < argsString.length()) {
            char c = argsString.charAt(index);

            if (c == '"') {
                if (tempArg.length() > 1) {
                    resultList.add(tempArg);
                }
                tempArg = "";
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                if (tempArg.length() > 1) {
                    resultList.add(tempArg);
                }
                tempArg = "";
            } else {
                tempArg += c;
                if(c == '\\'){
                    tempArg += argsString.charAt(++index);
                }
            }

            ++index;
        }
        return resultList.toArray(new String[0]);
    }

}
