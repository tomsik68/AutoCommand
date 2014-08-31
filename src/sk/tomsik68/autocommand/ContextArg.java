package sk.tomsik68.autocommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextArg {
    /**
     * 
     * @return Parameters for ContextParameterProvider
     */
    String[] value() default {};
}
