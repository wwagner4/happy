package happy;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EasingMain {

    public static void main(String[] args) {

        boolean plot = true;

        List<Cfg> cfgsa = Arrays.asList(
                new Cfg("1 A", 20, 64),
                new Cfg("2 A", 23, 128),
                new Cfg("3 A", 25, 192),
                new Cfg("4 A", 28, 256),
                new Cfg("5 A", 30, 320),
                new Cfg("6 A", 33, 384),
                new Cfg("7 A", 35, 448),
                new Cfg("8 A", 38, 512),
                new Cfg("9 A", 40, 576),
                new Cfg("A A", 43, 640),
                new Cfg("B A", 45, 704),
                new Cfg("C A", 48, 768),
                new Cfg("D A", 50, 864)
        );
        List<Cfg> cfgsb = Arrays.asList(
                new Cfg("1 B", 20, 96),
                new Cfg("2 B", 23, 160),
                new Cfg("3 B", 25, 224),
                new Cfg("4 B", 28, 288),
                new Cfg("5 B", 30, 352),
                new Cfg("6 B", 33, 416),
                new Cfg("7 B", 35, 480),
                new Cfg("8 B", 38, 544),
                new Cfg("9 B", 40, 608),
                new Cfg("A B", 43, 672),
                new Cfg("B B", 45, 736),
                new Cfg("C B", 48, 800),
                new Cfg("D B", 50, 896)
        );
        cfgsa.forEach(cfg -> runCfg(cfg, plot));
        cfgsb.forEach(cfg -> runCfg(cfg, plot));
    }

    private static void runCfg(Cfg cfg, boolean plot) {
        List<Pair<Double, Double>> data = IntStream.range(0, cfg.n + 1)
                .mapToDouble(i -> i)
                .mapToObj(d -> new ImmutablePair<>(d, Sin.f().apply(transform1(d, cfg.n))))
                .map(p -> new ImmutablePair<>(p.getLeft(), transform(p.getRight(), cfg.len)))
                .collect(Collectors.toList());
        table(cfg, data);
        if (plot) {
            plot(cfg, data);
        }
    }

    private static double transform(double val, double max) {
        double a = max / 2.0;
        double k = max / 2.0;
        return a + k * val;
    }

    private static double transform1(double val, double n) {
        double a = -1;
        double k = 2.0 / n;
        return a + k * val;
    }

    static void table(Cfg cfg, List<Pair<Double, Double>> data) {
        data.forEach(p -> System.out.printf("%5s %5.0f %5.2f%n", cfg.name, p.getLeft(), p.getRight()));
    }

    static void plot(Cfg cfg, List<Pair<Double, Double>> data) {

        try {
            double[] xData = new double[data.size()];
            double[] yData = new double[data.size()];
            for (int i = 0; i < data.size(); i++) {
                xData[i] = data.get(i).getLeft();
                yData[i] = data.get(i).getRight();
            }
            XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

            String name = "target/Sample_Chart_" + cfg.name;
            BitmapEncoder.saveBitmap(chart, name, BitmapEncoder.BitmapFormat.PNG);

            System.out.println("Wrote chart to " + name);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}

class Cfg {
    final String name;
    final int n;
    final double len;

    Cfg(String name, int n, double len) {
        this.name = name;
        this.n = n;
        this.len = len;
    }
}

class Sin {

    private Sin() {
    }

    static Function<Double, Double> f() {
        return x -> {
            double x1 = Math.pow(x * Math.PI / 2.0, 1.0);
            return Math.sin(x1);
        };
    }

}
