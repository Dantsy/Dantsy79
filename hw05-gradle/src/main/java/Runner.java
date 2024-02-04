import Annotations.Before;
import Annotations.Test;
import Annotations.After;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    private int passed = 0;
    private int failed = 0;
    private List<Throwable> exceptions = new ArrayList<>();

    public void runTests(String className) {
        try {
            Class<?> testClass = Class.forName(className);
            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            for (Method method : testClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Before.class)) {
                    method.setAccessible(true);
                    method.invoke(testInstance);
                }
            }

            for (Method method : testClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    try {
                        method.setAccessible(true);
                        method.invoke(testInstance);
                        passed++;
                    } catch (Throwable e) {
                        failed++;
                        exceptions.add(e);
                    }
                }
            }

            for (Method method : testClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(After.class)) {
                    method.setAccessible(true);
                    method.invoke(testInstance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        printStatistics();
    }

    private void printStatistics() {
        System.out.println("Статистика выполнения тестов:");
        System.out.println("Пройденный: " + passed);
        System.out.println("Ошибка: " + failed);
        System.out.println("Результат: " + (passed + failed));

        if (!exceptions.isEmpty()) {
            System.out.println("Пропуск:");
            for (Throwable e : exceptions) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.runTests("Testing");
    }
}