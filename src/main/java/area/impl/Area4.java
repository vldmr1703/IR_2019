package area.impl;

import area.Area;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Area4 implements Area {

    public double[] a(double t) {
        return new double[]{a1(t), a2(t)};
    }

    public double[] aD(double t) {
        return new double[]{a1D(t), a2D(t)};
    }

    public double[] aDD(double t) {
        return new double[]{a1DD(t), a2DD(t)};
    }

    public double a1(double t) {
        return 0.5 * cos(t);
    }

    public double a2(double t) {
        return sin(t);
    }

    public double a1D(double t) {
        return -0.5 * sin(t);
    }

    public double a2D(double t) {
        return cos(t);
    }

    public double a1DD(double t) {
        return -0.5 * cos(t);
    }

    public double a2DD(double t) {
        return -sin(t);
    }

    public double[] b(double t) {
        return new double[]{b1(t), b2(t)};
    }

    public double[] bD(double t) {
        return new double[]{b1D(t), b2D(t)};
    }

    public double[] bDD(double t) {
        return new double[]{b1DD(t), b2DD(t)};
    }

    public double b1(double t) {
        return 2 * cos(t);
    }

    public double b2(double t) {
        return 2 * sin(t);
    }

    public double b1D(double t) {
        return -2 * sin(t);
    }

    public double b2D(double t) {
        return 2 * cos(t);
    }

    public double b1DD(double t) {
        return -2 * cos(t);
    }

    @Override
    public double[] xIn() {
        return new double[]{0,0};
    }

    @Override
    public double[] yOut() {
        return new double[]{2,0};
    }

    public double b2DD(double t) {
        return -2 * sin(t);
    }
}
