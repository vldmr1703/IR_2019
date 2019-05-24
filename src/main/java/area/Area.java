package area;

import static java.lang.Math.PI;
import static java.lang.Math.log;
import static utils.Utils.r;
import static utils.Utils.xIn;
import static utils.Utils.yOut;

public interface Area {

    public double[] a(double t);

    public double[] aD(double t);

    public double[] aDD(double t);

    public double a1(double t);

    public double a2(double t);

    public double a1D(double t);

    public double a2D(double t);

    public double a1DD(double t);

    public double a2DD(double t);

    public double[] b(double t);

    public double[] bD(double t);

    public double[] bDD(double t);

    public double b1(double t);

    public double b2(double t);

    public double b1D(double t);

    public double b2D(double t);

    public double b1DD(double t);

    public double b2DD(double t);

    default double getExactSolution() {
        return log(r(xIn(), yOut())) / (2 * PI);
    }

    public double[] xIn();

    public double[] yOut();
}
