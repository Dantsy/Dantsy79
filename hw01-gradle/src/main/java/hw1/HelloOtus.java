package hw1;
import com.google.common.collect.Collections2;
import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.Collection;
public class HelloOtus {
    public static void main(String[] args) {
        Collection<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Predicate<Integer> isEven = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer number) {
                return number % 2 == 0;
            }
        };

        Collection<Integer> evenNumbers = Collections2.filter(numbers, isEven);
        System.out.println("Четные числа: " + evenNumbers);
    }
}