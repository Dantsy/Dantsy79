import Annotations.After;
import Annotations.Before;
import Annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void runTests(String testClassName) {
        try {
            Class<?> testClass = Class.forName(testClassName);
            Object testObject = testClass.getDeclaredConstructor().newInstance();

            Method[] methods = testClass.getMethods();
            List<Method> beforeMethods = new ArrayList<>();
            List<Method> testMethods = new ArrayList<>();
            List<Method> afterMethods = new ArrayList<>();

            for (Method method : methods) {
                if (method.isAnnotationPresent(Before.class)) {
                    beforeMethods.add(method);
                } else if (method.isAnnotationPresent(Test.class)) {
                    testMethods.add(method);
                } else if (method.isAnnotationPresent(After.class)) {
                    afterMethods.add(method);
                }
            }

            int successfulTests = 0;
            int failedTests = 0;

            for (Method testMethod : testMethods) {
                try {
                    invokeMethods(beforeMethods, testObject);
                    testMethod.invoke(testObject);
                    invokeMethods(afterMethods, testObject);
                    successfulTests++;
                } catch (Exception e) {
                    failedTests++;
                    e.printStackTrace();
                }
            }

            System.out.println("Пройденный: " + successfulTests);
            System.out.println("Ошибка: " + failedTests);
            System.out.println("Результат: " + (successfulTests + failedTests));

        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void invokeMethods(List<Method> methods, Object testObject) throws IllegalAccessException, InvocationTargetException {
        for (Method method : methods) {
            method.invoke(testObject);
        }
    }

    public static void main(String[] args) {
        Runner.runTests("Testing");
    }
}