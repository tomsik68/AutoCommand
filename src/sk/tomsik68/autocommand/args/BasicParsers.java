package sk.tomsik68.autocommand.args;

import java.util.Arrays;
import java.util.List;

public class BasicParsers {
    public static void registerBasicParsers(ArgumentParsers parsers) {
        parsers.registerArgumentParser(Integer.TYPE, new IntParser());
        parsers.registerArgumentParser(Integer.class, new IntParser());
        parsers.registerArgumentParser(Double.TYPE, new DoubleParser());
        parsers.registerArgumentParser(Double.class, new DoubleParser());
        parsers.registerArgumentParser(String.class, new StringParser());
    }

    public static class StringParser implements ArgumentParser {

        @Override
        public Object parse(String str) throws ArgumentParserException {
            return str;
        }

    }

    public static class IntParser implements ArgumentParser {

        @Override
        public Object parse(String str) throws ArgumentParserException {
            try {
                return Integer.parseInt(str);
            } catch (Exception e) {
                throw new ArgumentParserException(e.getMessage());
            }
        }

    }

    public static class DoubleParser implements ArgumentParser {

        @Override
        public Object parse(String str) throws ArgumentParserException {
            try {
                return Double.parseDouble(str);
            } catch (Exception e) {
                throw new ArgumentParserException(e.getMessage());
            }
        }

    }

    public static class BooleanParser implements ArgumentParser {
        private static final List<String> trueList = Arrays.asList("true", "yes", "on");

        @Override
        public Object parse(String str) throws ArgumentParserException {
            return trueList.contains(str.toLowerCase());
        }
    }

}
