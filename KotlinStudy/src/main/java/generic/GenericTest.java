package generic;

import generic.clazz.AnyToIntAdder;
import generic.clazz.IntToStringAdder;

import java.util.concurrent.atomic.AtomicInteger;

public class GenericTest {
    public static void testAdder() {
        AnyToIntAdder<Double> adder = new AnyToIntAdder<>();
        System.out.println("result: " + adder.add(1.1, 2.2));
        IntToStringAdder adder1 = new IntToStringAdder();
        System.out.println("result2: " + adder1.add(3, 4));
        AtomicInteger a = new AtomicInteger(1);
        int x = a.get();
    }
}
