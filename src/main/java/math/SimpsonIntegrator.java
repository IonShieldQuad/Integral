package math;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class SimpsonIntegrator implements Integrator {
    @Override
    public DoubleBinaryOperator integrate(DoubleUnaryOperator function, int n) {
        return (a, b) -> {
            double h = (b - a) / (2 * n);
            double res = 0;
            
            res += function.applyAsDouble(a);
            res += function.applyAsDouble(a + 2 * n * h);
    
            for (int i = 1; i <= 2 * n - 1; i += 2) {
                res += 4 * function.applyAsDouble(a + i * h);
            }
    
            for (int i = 2; i <= 2 * n - 2; i += 2) {
                res += 2 * function.applyAsDouble(a + i * h);
            }
            
            res *= h;
            res /= 3;
            
            return res;
        };
    }
}
