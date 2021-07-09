package arc.ecs.fluid.generator.strategy.e;

import arc.ecs.fluid.generator.model.*;
import arc.ecs.fluid.generator.model.artemis.*;
import arc.ecs.fluid.generator.model.type.*;
import arc.ecs.fluid.generator.util.*;

import java.lang.reflect.*;

/**
 * Adds methods to interact with component fields.
 * <p>
 * {@code class Pos extends Component { int x, y };} would extend the fluid interface with things
 * like {@code e.posX(50).posY(20); float x = e.posX(); }
 * @author Daan van Yperen
 * @see ComponentFieldAccessorStrategy
 */
public class DefaultFieldProxyStrategy implements FieldProxyStrategy{

    @Override
    public int priority(){
        return Integer.MIN_VALUE; // always run last.
    }

    @Override
    public boolean matches(ComponentDescriptor component, Field field, TypeModel model){
        return true;
    }

    @Override
    public void execute(ComponentDescriptor component, Field field, TypeModel model){

        // do not expose setters on final fields.
        if(0 == (Modifier.FINAL & field.getModifiers())){
            model.add(fieldSetterMethod(component, field));
        }

        model.add(fieldGetterMethod(component, field));
    }

    /**
     * int E::posX() -> obtain field directly via interface.
     */
    private MethodDescriptor fieldGetterMethod(ComponentDescriptor component, Field field){
        return new MethodBuilder(field.getGenericType(), component.getCompositeName(field.getName()))
        .mapper("return ", component, ".create(entityId)." + field.getName())
        .debugNotes(field.toGenericString())
        .build();
    }

    /**
     * E E::posX(10) -> set field, returns fluid interface.
     */
    private MethodDescriptor fieldSetterMethod(ComponentDescriptor component, Field field){
        final String parameterName = field.getName();
        return new MethodBuilder(FluidTypes.E_TYPE, component.getCompositeName(parameterName))
        .parameter(field.getGenericType(), parameterName)
        .mapper(component, ".create(this.entityId)." + parameterName + "=" + parameterName)
        .debugNotes(field.toGenericString())
        .returnFluid()
        .build();
    }
}
