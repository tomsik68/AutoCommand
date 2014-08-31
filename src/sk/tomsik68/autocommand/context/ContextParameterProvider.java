package sk.tomsik68.autocommand.context;

public interface ContextParameterProvider {
    
    public boolean provides(Class<?> paramClass, String... providerArgs);
    
    public Object provide(Class<?> paramClass, String... providerArgs);

    public boolean canProvide(CommandExecutionContext context);
}
