package sk.tomsik68.autocommand.context;

public interface ContextParameterProvider {
    
    public boolean provides(CommandExecutionContext context, Class<?> paramClass, String... providerArgs);
    
    public Object provide(CommandExecutionContext context, Class<?> paramClass, String... providerArgs);

    public boolean canProvide(CommandExecutionContext context);
}
