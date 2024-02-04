package task.model;

public class TestClass {
    public static void main(String[] args) {
        MyClass myObject = new MyClass();
        myObject.setField1("Hello");
        myObject.setField2(42);
        System.out.println(myObject.toString());
    }
}