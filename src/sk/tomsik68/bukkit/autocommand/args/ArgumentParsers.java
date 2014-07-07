package sk.tomsik68.bukkit.autocommand.args;

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
        args = convertArgs(args);
        return null;
    }

    static String[] convertArgs(String[] args) {
        // join arguments into 1 string
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb = sb.append(arg).append(' ');
        }
        String[] newArgs = new String[0];
        // split up arguments once again using advanced strategies
        String argsString = sb.toString();
        System.out.println("ArgsString: " + argsString);
        int index = 0;
        String tempArg = "";
        boolean inQuotes = false;
        while (index < argsString.length()) {
            char c = argsString.charAt(index);

            if (c == '"') {
                System.out.println("-Quote");

                System.out.println("--New arg");
                if (tempArg.length() > 1) {
                    System.out.println("---Add the previous arg");
                    String[] newArgs2 = new String[newArgs.length + 1];
                    System.arraycopy(newArgs, 0, newArgs2, 0, newArgs.length);
                    newArgs2[newArgs.length] = tempArg;
                    newArgs = newArgs2;
                }
                System.out.println("--tempArg is now empty");
                tempArg = "";
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                System.out.println("-whitespace!");
                if (tempArg.length() > 1) {
                    System.out.println("--Add previous arg");
                    String[] newArgs2 = new String[newArgs.length + 1];
                    System.arraycopy(newArgs, 0, newArgs2, 0, newArgs.length);
                    newArgs2[newArgs.length] = tempArg;
                    newArgs = newArgs2;
                }
                tempArg = "";
            } else {
                tempArg += c;
                System.out.print(c);
            }

            ++index;
        }
        return newArgs;
    }

}
