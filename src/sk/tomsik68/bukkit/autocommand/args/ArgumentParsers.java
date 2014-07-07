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

    public static Object[] parse(Class[] classes, String[] args) throws ArgumentParserException {

        // 1) split up args also based on quotes
        args = convertArgs(args);
        // 2) each class needs to be parsed from args list
        for (String arg : args) {

        }
        return null;
    }

    static String[] convertArgs(String[] args) {
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
            }

            ++index;
        }
        return resultList.toArray(new String[0]);
    }

}
