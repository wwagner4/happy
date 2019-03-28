package happy;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EasingMain {

    public static void main(String[] args) throws IOException {
        int minx = 0;
        int maxx = 100;
        int miny = 0;
        int maxy = 55;

        Stream<Pair<Double, Double>> data = IntStream.range(-100, 100)
                .mapToDouble(i -> i / 100.0)
                .mapToObj(d -> new ImmutablePair<>(d, Sin.f().apply(d)))
                .map(p -> new ImmutablePair<>(transform(p.getLeft(), minx, maxx), transform(p.getRight(), miny, maxy)));
        plot(data);
    }

    private static double transform(double val, double min, double max) {
        double a = (min + max) / 2.0;
        double k = (max - min) / 2.0;
        return a + k * val;
    }

    private static void plot(Stream<Pair<Double, Double>> data) throws IOException {
        List<Pair<Double, Double>> d = data.collect(Collectors.toList());

        double[] xData = new double[d.size()];
        double[] yData = new double[d.size()];
        for (int i = 0; i < d.size(); i++) {
            xData[i] = d.get(i).getLeft();
            yData[i] = d.get(i).getRight();
        }
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

        String name = "target/Sample_Chart";
        BitmapEncoder.saveBitmap(chart, name, BitmapEncoder.BitmapFormat.PNG);

        System.out.println("Wrote chart to " + name);
    }

}

class Sin {

    private Sin() {
    }

    static final Function<Double, Double> f() {
        return x -> {
            double x1 = Math.pow(x * Math.PI / 2.0, 1.0);
            return Math.sin(x1);
        };
    }

}
