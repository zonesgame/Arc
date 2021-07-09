package arc.ecs;


/**
 * Identifies components in artemis without having to use classes.
 * <p>
 * Contains ordinal for a component type, which allows for fast
 * retrieval of components.
 * @author Arni Arent
 * @author Adrian Papari
 */
public class ComponentType{
    public final boolean isPooled;

    /** The class type of the component type. */
    private final Class<? extends Component> type;

    /** Ordinal for fast lookups. */
    private final int index;

    ComponentType(Class<? extends Component> type, int index){
        this.index = index;
        this.type = type;
        isPooled = (PooledComponent.class.isAssignableFrom(type));
    }

    /**
     * Get the component type's index.
     * <p>
     * Index is distinct for each {@link Base} instance,
     * allowing for fast lookups.
     * @return the component types index
     */
    public int getIndex(){
        return index;
    }

    /**
     * @return {@code Class} that this type represents.
     */
    public Class<? extends Component> getType(){
        return type;
    }

    @Override
    public String toString(){
        return "ComponentType[" + type.getSimpleName() + "] (" + index + ")";
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ComponentType that = (ComponentType)o;

        return index == that.index;
    }

    @Override
    public int hashCode(){
        return index;
    }
}
