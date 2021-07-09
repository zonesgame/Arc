package arc.ecs.weaver;

import org.objectweb.asm.*;
import org.objectweb.asm.util.*;

import java.io.*;
import java.util.*;

public final class ClassUtil implements Opcodes{
    private ClassUtil(){
    }

    public static void injectMethodStub(ClassWriter cw, String methodName){
        MethodVisitor mv = cw.visitMethod(ACC_PROTECTED, methodName, "()V", null, null);
        mv.visitCode();
        mv.visitInsn(RETURN);
        mv.visitEnd();
    }

    public static void injectAnnotation(ClassWriter cw, String desc){
        AnnotationVisitor av = cw.visitAnnotation(desc, true);
        av.visitEnd();
    }

    public static void writeClass(ClassWriter writer, String file){
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            fos.write(writer.toByteArray());
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(fos != null) try{
                fos.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static String verifyClass(ClassWriter writer){
        StringWriter sw = new StringWriter();
        PrintWriter printer = new PrintWriter(sw);

        CheckClassAdapter.verify(new ClassReader(writer.toByteArray()), false, printer);

        return sw.toString();
    }

    public static List<File> find(File root){
        return find(Collections.singleton(root));
    }

    public static List<File> find(Set<File> roots){
        List<File> klazzes = new ArrayList<>();

        for(File root : roots){
            if(!root.isDirectory())
                throw new IllegalAccessError(root + " must be a folder.");

            addFiles(klazzes, root);
        }

        return klazzes;
    }

    private static void addFiles(List<File> files, File folder){
        for(File f : folder.listFiles()){
            if(f.isFile() && f.getName().endsWith(".class"))
                files.add(f);
            else if(f.isDirectory())
                addFiles(files, f);
        }
    }
}
