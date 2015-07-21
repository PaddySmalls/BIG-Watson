package com.big.watson.scan;

import aj.org.objectweb.asm.AnnotationVisitor;
import aj.org.objectweb.asm.ClassVisitor;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by patrick on 17.07.15.
 */
public class MethodInterceptorClassVisitor extends ClassVisitor {

    private String visitedClassName;
    private Set<MethodInterceptor> methodInterceptors = new HashSet<>();

    private Map<String, MethodInterceptor> interceptorMap = new HashMap<>();


    public MethodInterceptorClassVisitor(int api) {
        super(api);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        visitedClassName = name.replace("/", ".");
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean b) {
        if (desc.equals("Lcom/big/watson/annotation/WatsonInterceptor;")) {
            try {
                if (!interceptorMap.containsKey(visitedClassName)) {
                    Class<?> interceptorClass = this.getClass().getClassLoader().loadClass(visitedClassName);
                    try {
                        interceptorMap.put(visitedClassName, (MethodInterceptor) interceptorClass.newInstance());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                methodInterceptors.add(interceptorMap.get(visitedClassName));

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Set<MethodInterceptor> getMethodInterceptors() {
        return methodInterceptors;
    }
}
