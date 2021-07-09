package arc.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ArcAnnotate{
    /** Indicates that a method return or field can be null.*/
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Nullable{

    }

    /** Indicates that a method return or field cannot be null.*/
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NonNull{

    }

//    /**  Denotes that any overriding methods should invoke this method as well. */
//    @Target({ElementType.METHOD})
//    @Retention(RetentionPolicy.RUNTIME)
//    public @interface CallSuper {
//    }
}
