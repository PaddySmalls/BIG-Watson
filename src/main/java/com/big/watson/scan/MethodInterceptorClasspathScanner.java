package com.big.watson.scan;

import aj.org.objectweb.asm.ClassReader;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Created by patrick on 17.07.15.
 */

public class MethodInterceptorClasspathScanner {

    private MethodInterceptorClassVisitor classVisitor;

    private Set<MethodInterceptor> interceptorCache;

    @Autowired
    public MethodInterceptorClasspathScanner(MethodInterceptorClassVisitor classVisitor) {
        this.classVisitor = classVisitor;
    }

    public Set<MethodInterceptor> loadMethodInterceptors() {
        if (interceptorCache == null) {
            for (URL url : getRootURLs()) {
                File file = new File(url.getPath());
                if (file.isDirectory()) {
                    visitFile(file);
                } else {
                    visitJAR(url);
                }
            }
            interceptorCache = classVisitor.getMethodInterceptors();
        }
        return interceptorCache;
    }

    private List<URL> getRootURLs() {
        List<URL> classPathURLs = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        while (classLoader != null) {
            if (classLoader instanceof URLClassLoader) {
                URL[] urls = ((URLClassLoader) classLoader).getURLs();
                classPathURLs.addAll(Arrays.asList(urls));
            }
            classLoader = classLoader.getParent();
        }
        return classPathURLs;
    }

    private void visitFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                Arrays.asList(files).forEach(aFile -> visitFile(aFile));
            }
        } else if (file.getName().endsWith(".class")) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                handleClass(fileInputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void visitJAR(URL url) {
        try {
            JarInputStream jarInputStream = new JarInputStream(url.openStream());
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                if (jarEntry.getName().endsWith(".class")) {
                    handleClass(jarInputStream);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleClass(InputStream inputStream) throws IOException {
        new ClassReader(inputStream).accept(classVisitor, 0);
    }


}
