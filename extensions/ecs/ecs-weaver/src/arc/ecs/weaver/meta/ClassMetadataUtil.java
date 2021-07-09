package arc.ecs.weaver.meta;

import java.io.*;
import java.util.*;

import static org.objectweb.asm.Opcodes.*;

public final class ClassMetadataUtil{

    private ClassMetadata meta;

    public ClassMetadataUtil(ClassMetadata meta){
        this.meta = meta;
    }

    public static List<FieldDescriptor> instanceFields(ClassMetadata meta){
        List<FieldDescriptor> instanceFields = new ArrayList<FieldDescriptor>();
        for(FieldDescriptor field : meta.fields()){
            if("$data".equals(field.name) || "$world".equals(field.name))
                continue;

            if((field.access & (ACC_FINAL | ACC_STATIC)) == 0)
                instanceFields.add(field);
        }

        // sorting fields so that RW operations are type aligned
        Collections.sort(instanceFields, new PrimitiveSizeComparator());

        return instanceFields;
    }

    public boolean hasSetter(FieldDescriptor f){
        String methodDesc = "(" + f.desc + ")";
        for(MethodDescriptor m : meta.methods){
            if(m.name.equals(f.name) && m.desc.startsWith(methodDesc))
                return true;
        }

        return false;
    }

    public boolean hasGetter(FieldDescriptor f){
        String methodDesc = "()" + f.desc;
        for(MethodDescriptor m : meta.methods){
            if(m.name.equals(f.name) && m.desc.equals(methodDesc))
                return true;
        }

        return false;
    }

    public static Set<String> instanceFieldTypes(ClassMetadata meta){
        Set<String> instanceFields = new HashSet<String>();
        for(FieldDescriptor f : instanceFields(meta)){
            instanceFields.add(f.desc);
        }
        return instanceFields;
    }

    public static String superName(ClassMetadata meta){
        switch(meta.annotation){
            case POOLED:
                return "arc/ecs/PooledComponent";
            case NONE:
            default:
                throw new RuntimeException("Missing case : " + meta.annotation);
        }
    }

    public static int sizeOf(FieldDescriptor fd){
        switch(fd.desc.charAt(0)){
            case 'J': // long
            case 'D': // double
                return 8;
            case 'I': // int
            case 'F': // float
                return 4;
            case 'S': // short
            case 'C': // char
                return 2;
            case 'B': // byte
            case 'Z': // boolean
                return 1;
            case 'L': // object
                return 0;
            default:
                throw new RuntimeException("Unknown primtive type: " + fd.desc);
        }
    }

    public static int sizeOf(ClassMetadata meta){
        int size = 0;
        for(FieldDescriptor fd : meta.fields()){
            size += sizeOf(fd);
        }

        return size;
    }


    private static final class PrimitiveSizeComparator implements Comparator<FieldDescriptor>, Serializable{

        @Override
        public int compare(FieldDescriptor o1, FieldDescriptor o2){
            return sizeOf(o1) - sizeOf(o2);
        }
    }
}
