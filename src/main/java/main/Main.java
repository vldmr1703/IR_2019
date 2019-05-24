package main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import area.Area;
import area.impl.Area3;
import area.impl.Area4;

import static java.lang.Math.PI;


public class Main {
    public static void main(String[] args) {
        int n = 100;
        double m = 2 * PI;
        double a = -2;
        double b = 2;
        double[] t = new double[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            t[i] = i * PI / n;
        };
        Area area = new Area4();

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeries[] exactSeries = new XYSeries[2];
        exactSeries[0] = new XYSeries("Г" + 1);
        exactSeries[1] = new XYSeries("Г" + 2);
        for (int j = 0; j < 2 * n; j++) {
            exactSeries[0].add(area.a1(t[j]), area.a2(t[j]));
            exactSeries[1].add(area.b1(t[j]), area.b2(t[j]));
//            for (int i = 0; i < m; i++) {
//            }
        }
        for (int i = 0; i < 2; i++) {
            xySeriesCollection.addSeries(exactSeries[i]);
        }

        XYDataset dataset = xySeriesCollection;
        DrawGraph chart = new DrawGraph(
                "",
                "", dataset, a, b);

        chart.pack();
        chart.setVisible(true);
    }

    static class DrawGraph extends ApplicationFrame {

        public DrawGraph(String applicationTitle, String chartTitle, XYDataset dataset, double a, double b) {
            super(applicationTitle);
            JFreeChart lineChart = ChartFactory.createXYLineChart(
                    chartTitle,
                    "Time", "Function value",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false);
            XYPlot xyPlot = lineChart.getXYPlot();
            NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
            domain.setRange(a, b);
            domain.setTickUnit(new NumberTickUnit(0.5));

            NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
            range.setRange(a,b);
            range.setTickUnit(new NumberTickUnit(0.5));
            ChartPanel chartPanel = new ChartPanel(lineChart);
            chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));
            setContentPane(chartPanel);
        }

        private DefaultCategoryDataset createDataset() {
            int n = 10;
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            double fault = 0;
            return dataset;
        }
    }
}