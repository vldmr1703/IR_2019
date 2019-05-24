package nystrom;

import area.Area;
import boundary.Function;
import systemsolver.Gauss;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.sin;
import static utils.Utils.mod;
import static utils.Utils.r;

public class NystromMethod {

    private int n;
    private double[][] m;
    private double[] p;
    private double[] t;
    private Area area;
    private Function f;
    private Function g;
    double[] phi;

    public NystromMethod(int n, Area area, Function f, Function g) {
        this.n = n;
        m = new double[4 * n][4 * n];
        p = new double[4 * n];
        t = new double[2 * n];
        this.area = area;
        this.f = f;
        this.g = g;
        for (int i = 0; i < 2 * n; i++) {
            t[i] = i * PI / n;
        }
    }

    private void createSystem() {
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < 2 * n; j++) {
//                m[i][j] = K2(t[i], t[j]) / (2 * n);
//                m[i][j] = (H2(t[i], t[j]) / (2 * n) + R(j, t[i]) / 2); //right
                m[i][j] = L2(t[i], t[j]) / (2 * n) + L1(t[i], t[j]) * R(j, t[i]);
            }
            for (int j = 2 * n; j < 4 * n; j++) {
//                m[i][j] = (H2(t[i], t[j - 2 * n]) / (2 * n) + R(j - 2 * n, t[i]) / 2);
                m[i][j] = K2(t[i], t[j - 2 * n]) / (2 * n);
            }
            p[i] = f.getValue(t[i]);
        }
        for (int i = 2 * n; i < 4 * n; i++) {
            for (int j = 0; j < 2 * n; j++) {
                m[i][j] = (K3(t[i - 2 * n], t[j]) / (2 * n));
            }
            for (int j = 2 * n; j < 4 * n; j++) {
                m[i][j] = (K4(t[i - 2 * n], t[j - 2 * n]) / (2 * n));
                if (i == j) {
                    m[i][j] -= 0.5;
                }
            }
            p[i] = g.getValue(t[i - 2 * n]);
        }
    }

    private double[] getPhi() {
        createSystem();
        Gauss gauss = new Gauss(m, p);
        return gauss.getX();
    }

    public double getSolution() {
        phi = getPhi();
        double u = 0;
        for (int i = 0; i < 2 * n; i++) {
            u += phi[i] * K1Ex(t[i]) + phi[i + 2 * n] * K2Ex(t[i]);
        }
        u = u / (2 * n);
        return u;
    }

    public double getDerivative() {
        getSolution();
        double v = 0;
        for (int i = 0; i < 2 * n; i++) {
            v += phi[i] * DU1(t[i]) + phi[i + 2 * n] * DU2(t[i]);
        }
        v = v / (2 * n);
        return v;
    }

//    public double getExactSolution() {
//        return log(r(area.xIn(), yOut())) / (2 * PI);
//    }

    private double K1Ex(double t) {
        double[] x = area.xIn();
        return -log(r(x, area.a(t))) * mod(area.aD(t));
    }

    private double K2Ex(double t) {
        double[] x = area.xIn();
        return -log(r(x, area.b(t))) * mod(area.bD(t));
    }

    private double DU1(double t) {
        double[] x = area.xIn();
        double r = r(area.a(t), x);
        return -((area.a1(t) - x[0]) * area.a2D(t) - (area.a2(t) - x[1]) * area.a1D(t)) * mod(area.aD(t)) / (r * r * mod(area.aD(t)));
    }

    private double DU2(double t) {
        double[] x = area.xIn();
        double r = r(area.b(t), x);
        return -((area.b1(t) - x[0]) * area.b2D(t) - (area.b2(t) - x[1]) * area.b1D(t)) * mod(area.bD(t)) / (r * r * mod(area.bD(t)));
    }

    private double K1(double t, double T) {
        double K;
        if (t != T) {
            double r = r(area.a(t), area.a(T));
            K = -((area.a1(T) - area.a1(t)) * area.a2D(T) - (area.a2(T) - area.a2(t)) * area.a1D(T)) / (r * r);
        } else {
            double mod = mod(area.aD(t));
            K = -(area.a1D(t) * area.a2DD(t) - area.a2D(t) * area.a1DD(t)) / (mod * mod);
        }
        return K;
    }

    private double K2(double t, double T) {
        return -log(r(area.a(t), area.b(T))) * mod(area.bD(T));
    }

    private double K3(double t, double T) {
        double K;
        double r = r(area.b(t), area.a(T));///////////////////////////////////////////////////////////////////
        K = -((area.b1(t) - area.a1(T)) * area.b2D(t) - (area.b2(t) - area.a2(T)) * area.b1D(t)) * mod(area.aD(T)) / (r * r * mod(area.bD(t)));
        return K;
    }

    private double K4(double t, double T) {
        double K;
        double mod = mod(area.bD(t));
        if (t != T) {
            double r = r(area.b(t), area.b(T));
            K = -((area.b1(t) - area.b1(T)) * area.b2D(t) - (area.b2(t) - area.b2(T)) * area.b1D(t)) * mod(area.bD(T)) / (r * r * mod(area.bD(t)));
        } else {

            K = -(area.b1D(t) * area.b2DD(t) - area.b2D(t) * area.b1DD(t)) / (mod * mod);
        }
        return K;
    }

//    private double L1(double t, double T) {
//        return -0.5 * Math.E * mod(area.bD(T));
//    }
//
//    private double L2(double t, double T) {
//        return -Math.E * mod(area.bD(T)) * log(mod(area.bD(T)));
//    }

    private double L1(double t, double T) {
        return -0.5 * mod(area.aD(T));
//        return -0.5 * log(Math.E * Math.pow(mod(area.aD(T)), 2));
    }

    private double L2(double t, double T) {
        return -mod(area.aD(T)) * log(mod(area.aD(T)));
    }

    private double H2(double t, double T) {
        if (t == T) {
            return -0.5 * log(Math.E * Math.pow(mod(area.aD(T)), 2));
        } else {
            return -0.5 * log(Math.E * Math.pow(r(area.a(t), area.a(T)), 2) * 4 * Math.pow(sin((t - T) / 2), 2));
        }
    }

//    private double H2(double t, double T) {
//        if (t == T) {
//            return -0.5 * log(Math.E * Math.pow(mod(area.aD(T)), 2));
//        } else {
//            return -0.5 * log(Math.E * Math.pow(r(area.a(t), area.a(T)), 2) * 4 * Math.pow(sin((t - T) / 2), 2));
//        }
//    }

    double R(int j, double t) {
        double sum = 0;
        for (int i = 1; i <= n - 1; i++) {
            sum = sum + cos(i * (t - this.t[j])) / i;
        }
        sum = sum + cos(n * (t - this.t[j])) / (2 * n);
        return -sum / n;
    }

//    private double f(double t) {
//        return -log(r(area.a(t), yOut())) / (2 * PI);
//    }
//
//    private double g(double t) {
//        double[] y = yOut();
//        double r = r(area.b(t), y);
//        return (-((area.b1(t) - y[0]) * area.b2D(t) - (area.b2(t) - y[1]) * area.b1D(t)) / (r * r * mod(area.bD(t)))) / (2 * PI);
//    }

    public void print() {
        int n = p.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println(p[i]);
        }
        System.out.println();
    }

}
