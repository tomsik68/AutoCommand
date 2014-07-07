package sk.tomsik68.bukkit.autocommand;

public @interface AutoCommand {
    boolean player();

    boolean console();

    public String name() default "";

    public String help() default "";

    public String usage() default "";

    public String permission() default "";

}
