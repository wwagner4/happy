package happy;

import org.apache.commons.lang3.tuple.Pair;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Plot {

    public static void plot(Stream<Pair<Double, Double>> data) throws IOException {
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
