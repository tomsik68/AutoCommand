package sk.tomsik68.autocommand.args;

import java.util.ArrayList;

public class StringRespectingArgumentTokenizer implements ArgumentTokenizer {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    @Override
    public String[] retokenize(String[] args) {
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
                if (c == '\\') {
                    tempArg += argsString.charAt(++index);
                }
            }

            ++index;
        }
        return resultList.toArray(EMPTY_STRING_ARRAY);
    }

}
