package sk.tomsik68.bukkit.autocommand.args;

public class EnumParser<E extends Enum> implements ArgumentParser {
    private final Class<E> enumClass;

    public EnumParser(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public Object parse(String str) throws ArgumentParserException {
        return Enum.valueOf(enumClass, str);
    }

}
