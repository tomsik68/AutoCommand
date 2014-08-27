package sk.tomsik68.autocommand.args;

public class EnumParser<E extends Enum> implements ArgumentParser {
    private final Class<E> enumClass;

    public EnumParser(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object parse(String str) throws ArgumentParserException {
        Enum result = Enum.valueOf(enumClass, str);
        if (result == null)
            throw new ArgumentParserException("Invalid argument: " + str);
        return result;
    }

}
