package com.baomidou.springmvc.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class BookServiceFacadeCglib implements MethodInterceptor {
    private Object target;

    private BookServiceFacadeCglib(Object target) {
        this.target = target;
    }

    /**
     * 创建代理对象
     *
     * @param target
     * @return
     */
    public static Object getInstance(Object target) {
        BookServiceFacadeCglib cglib = new BookServiceFacadeCglib(target);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(cglib);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.err.println("事物开始");
        System.err.println(method.getName());
        Object rt = methodProxy.invokeSuper(o, args);
        System.err.println("事物结束");
        return rt;
    }

    static class BookService {
        public String addBook() {
            System.err.println("增加图书的普通方法...");
            return "ok";
        }
    }

    public static void main(String[] args) {
        BookService bookProxy = (BookService) BookServiceFacadeCglib.getInstance(new BookService());
        System.err.println(bookProxy.addBook());
    }
}
