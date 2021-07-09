package arc.ecs;

@SuppressWarnings("serial")
public class InvalidComponentException extends RuntimeException{
    private Class<?> componentClass;

    public InvalidComponentException(Class<?> componentClass, String string){
        super(message(componentClass, string));
        this.componentClass = componentClass;
    }

    public InvalidComponentException(Class<?> componentClass, String string, Exception e){
        super(message(componentClass, string), e);
        this.componentClass = componentClass;
    }

    private static String message(Class<?> componentClass, String string){
        return componentClass.getSimpleName() + ": " + string;
    }

    public Class<?> getComponentClass(){
        return componentClass;
    }
}
