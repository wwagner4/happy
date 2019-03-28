package happy;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EasingMain {

    public static void main(String[] args) throws IOException {
        System.out.println("hello");

        Stream<Pair<Double, Double>> data = IntStream.range(0, 100)
                .mapToDouble(i -> i)
                .mapToObj(d -> new ImmutablePair(d, Math.sin(d)));
        plot(data);
    }

    private static void plot(Stream<Pair<Double, Double>> data) throws IOException {
        List<Pair<Double, Double>> d = data.collect(Collectors.toList());

        double[] xData = new double[d.size()];
        double[] yData = new double[d.size()];
        for (int i=0; i< d.size(); i++) {
            xData[i] = d.get(i).getLeft();
            yData[i] = d.get(i).getRight();
        }
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        new SwingWrapper(chart).displayChart();
        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
    }

}
