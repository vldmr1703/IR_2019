package utils;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Utils {
    public static double mod(double[] x) {
        return sqrt(pow(x[0], 2) + pow(x[1], 2));
    }

    private static double[] diff(double[] x, double[] y) {
        return new double[]{x[0] - y[0], x[1] - y[1]};
    }

    public static double r(double[] x, double[] y) {
        return mod(diff(x, y));
    }

//    public static double[] xIn() {
//        return new double[]{0.5,0.1};
//    }
//
//    public static double[] yOut() {
//        return new double[]{0,0};
//    }
}
