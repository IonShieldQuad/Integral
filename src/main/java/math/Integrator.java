package math;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public interface Integrator {
    DoubleBinaryOperator integrate(DoubleUnaryOperator function, int n);
}
