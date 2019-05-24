package landveber;

import area.Area;
import area.impl.Area3;
import area.impl.Area4;
import area.impl.Circle2;
import nystrom.NystromMethod;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.log;
import static utils.Utils.mod;
import static utils.Utils.r;

public class Landveber {
    public static void main(String[] args) {
        double eps = 0.00001;
        Area area = new Area4();
        double h1 = 0;
        double h;
        int n = 64;
        double alpha = 0.1;
        int k = 0;
        double u;
        double v;
        do {
            h = h1;
            double finalH = h;
            NystromMethod nystromMethod = new NystromMethod(n, area,
                    t -> finalH,
                    t -> {
//                        return 10;
//                        return area.a2(t) * Math.exp(-area.a1(t));
                        double[] y = area.yOut();
                        double r = r(area.b(t), y);
                        return (-((area.b1(t) - y[0]) * area.b2D(t) - (area.b2(t) - y[1]) * area.b1D(t))
                                / (r * r * mod(area.bD(t)))) / (2 * PI);
                    });
            u = nystromMethod.getSolution();
            System.out.println("-------------------------------");
            System.out.println(k + "\tu\t| " + u);
            double finalU = u;
            nystromMethod = new NystromMethod(n, area,
                    t -> 0,
                    t ->
//                            u
//                            u - area.b1(t)
                            finalU - log(r(area.a(t), area.yOut())) / (2 * PI)
            );
            v = nystromMethod.getDerivative();
            System.out.println(k + "\tv\t| " + v);
            h1 = h - alpha * v;
            k++;
            System.out.println("Exact " + area.getExactSolution());
        } while (Math.abs(h1 - h) > eps);
        System.out.println(k + " iterations");
        System.out.println("Diff " + abs(area.getExactSolution() - u));
    }
}
