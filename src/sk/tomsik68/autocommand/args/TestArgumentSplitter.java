package sk.tomsik68.autocommand.args;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestArgumentSplitter {

    @Test
    public void test() {
        String command = "stopat \"world\\\" of derp\" \"something number one\" something2  ";
        String[] badArgs = command.split(" ");
        String[] goodArgs = new String[] {
                "stopat", "world\\\" of derp", "something number one", "something2"
        };
        String[] converted = ArgumentParsers.getArgumentsFromArray(badArgs);
        System.out.println("==================================");
        for(String c : converted)
            System.out.println(c);
        assertArrayEquals(goodArgs, converted);
    }

}
