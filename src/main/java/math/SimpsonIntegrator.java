package math;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class SimpsonIntegrator implements Integrator {
    private static final double EPSILON = 0.0001;
    
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
            
            double e = (a + b) / 2;
            double r = (b - a) * Math.pow(h, 4) * derivative(function, e, 4) / 180;
            
            if (!Double.isNaN(r) && Double.isFinite(r)) {
                res += r;
            }
            
            return res;
        };
    }
    
    private double derivative(DoubleUnaryOperator f, double x, int order) {
        if (order < 0) {
            throw new IllegalArgumentException("Invalid order: " + order);
        }
        if (order == 0) {
            return f.applyAsDouble(x);
        }
        return (derivative(f, x + EPSILON, order - 1) - derivative(f, x, order - 1)) / EPSILON;
    }
}
