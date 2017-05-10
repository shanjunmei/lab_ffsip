/*
package com.ffzx.common.context;

import com.ffzx.common.service.impl.BaseServiceImpl;
import com.ffzx.ffsip.mapper.UserMapper;
import com.ffzx.orm.common.mongo.mapper.impl.BaseMapperImpl;
import org.springframework.asm.*;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

*/
/**
 * Created by Administrator on 2017/3/9.
 *//*

public class MarkerFactoryBean<T> implements FactoryBean<T> {

    Class<T> target;

    public MarkerFactoryBean(Class<T> target) {
        this.target = target;
    }

    public static void main(String args[]) {
        MarkerFactoryBean t = new MarkerFactoryBean(UserMapper.class);
        UserMapper userMapper = (UserMapper) t.createIncance(UserMapper.class);
        System.out.println(userMapper);
    }

    @Override
    public T getObject() throws Exception {
        T t = MarkerFactoryBeanHolder.get(target);
        if (t == null) {
            t = createIntance();
            MarkerFactoryBeanHolder.set(target, t);
        }
        return t;
    }

    public Object createIncance(Class cls) {
        try {
            String className=cls.getName() + "Impl";

            byte[] data = createMapperImpl(cls, className);

            MarkerLoader loader=new MarkerLoader();
            Class<?> t=loader.loadClass(className,data);

            return t.newInstance();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public byte[] createServiceImpl(Class cls,String className){
        String _base = BaseServiceImpl.class.getName();
        Class<?> typeClass = (Class<T>) ((ParameterizedType) cls.getGenericInterfaces()[0]).getActualTypeArguments()[0];
        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, className.replace(".","/"), "L"+_base.replace(".","/")+"<L"+typeClass.getName().replace(".","/")+";Ljava/lang/String;>;L"+className.replace(".","/")+";", _base.replace(".","/"), new String[] { className.replace(".","/") });

        {
            av0 = cw.visitAnnotation("Lorg/springframework/stereotype/Service;", true);
            av0.visitEnd();
        }
        {
            fv = cw.visitField(Opcodes.ACC_PRIVATE, "mapper", "Lcom/ffzx/ffsip/mapper/"+typeClass.getSimpleName()+"Mapper;", null, null);
            {
                av0 = fv.visitAnnotation("Ljavax/annotation/Resource;", true);
                av0.visitEnd();
            }
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, _base.replace(".","/"), "<init>", "()V", false);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "getMapper", "()Lcom/ffzx/ffsip/mapper/"+typeClass.getSimpleName()+"Mapper;", null, null);
            mv.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, className.replace(".","/"), "mapper", "Lcom/ffzx/ffsip/mapper/"+typeClass.getSimpleName()+"Mapper;");
            mv.visitInsn(Opcodes.ARETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_BRIDGE + Opcodes.ACC_SYNTHETIC, "getMapper", "()Lcom/ffzx/orm/common/mongo/mapper/Mapper;", null, null);
            mv.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className.replace(".","/"), "getMapper", "()Lcom/ffzx/ffsip/mapper/"+typeClass.getSimpleName()+"Mapper;", false);
            mv.visitInsn(Opcodes.ARETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

    public byte[] createMapperImpl(Class cls, String className) {
        String _base = BaseMapperImpl.class.getName();
        Class<?> typeClass = (Class<T>) ((ParameterizedType) cls.getGenericInterfaces()[0]).getActualTypeArguments()[0];


        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, className.replace(".","/"), "L"+_base.replace(".","/")+"<L"+typeClass.getName().replace(".","/")+";>;L"+cls.getName().replace(".","/")+";", _base.replace(".","/"), new String[] { cls.getName().replace(".","/") });

        {
            av0 = cw.visitAnnotation("Lorg/springframework/stereotype/Service;", true);
            av0.visitEnd();
        }
        {
            fv = cw.visitField(Opcodes.ACC_PRIVATE, "datastore", "Lorg/mongodb/morphia/Datastore;", null, null);
            {
                av0 = fv.visitAnnotation("Ljavax/annotation/Resource;", true);
                av0.visitEnd();
            }
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, _base.replace(".","/"), "<init>", "()V", false);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "getDatastore", "()Lorg/mongodb/morphia/Datastore;", null, null);
            mv.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, className.replace(".","/"), "datastore", "Lorg/mongodb/morphia/Datastore;");
            mv.visitInsn(Opcodes.ARETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

    public T createIntance() {
        T t=(T)createIncance(target);
        */
/*t = (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{target}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                if ("toString".equals(method.getName())) {
                    return target.toString();
                } else {
                    System.out.println("hello proxy");
                }

                return null;
            }
        });*//*

        return t;
    }


    @Override
    public Class<T> getObjectType() {
        return target;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

   static class MarkerLoader extends  ClassLoader{

        public  Class<?> loadClass(String name,byte[] body){
            return defineClass(name,body,0,body.length);
        }
    }
}
*/
