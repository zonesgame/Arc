package arc.z.util;

/**
 *
 */
public class ZonesAnnotate {

    public @interface ZTest{
    }

//    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
//    @Retention(RetentionPolicy.RUNTIME)
    public @interface ZAdd{

    }

    /** 通过映射加载的类属性数据*/
//    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
//    @Retention(RetentionPolicy.RUNTIME)
    public @interface ZField{
    }

    /** 通过映射执行的函数
     * @apiNote 参数不能使用内部类 ps: int 必须使用 Integer
     * */
    public @interface ZMethod{
    }

    /** AI执行需要属性*/
    public @interface ZAIField{
    }

    /** AI执行需要函数*/
    public @interface ZAIMethod{
    }

}
