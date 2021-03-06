package sk.tomsik68.autocommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoCommand {
    public String name() default "";

    public String help() default "";

    public String usage() default "";

    public String permission() default "";
}
