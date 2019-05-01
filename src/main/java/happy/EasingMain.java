package happy;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EasingMain {

    public static void main(String[] args) {

        List<Cfg> cfgsb = Arrays.asList(
                new Cfg("1", 20, 96),
                new Cfg("2", 23, 160),
                new Cfg("3", 25, 224),
                new Cfg("4", 28, 288),
                new Cfg("5", 30, 352),
                new Cfg("6", 33, 416),
                new Cfg("7", 35, 480),
                new Cfg("8", 38, 544),
                new Cfg("9", 40, 608),
                new Cfg("A", 43, 672),
                new Cfg("B", 45, 736),
                new Cfg("C", 48, 800),
                new Cfg("D", 50, 896)
        );
        Stream<Diff> data = cfgsb.stream().flatMap(EasingMain::runCfg);
        data.forEach(p -> System.out.printf("%5s %5d %10.2f %10.2f %10.0f%n", p.nam, p.nr, p.abs, p.rel, p.rel));
    }

    private static Stream<Diff> runCfg(Cfg cfg) {
        return IntStream.range(0, cfg.n + 1)
                .mapToDouble(i -> i)
                .mapToObj(d -> new Diff(cfg.name, Double.valueOf(d).intValue(), Sin.f().apply(transform1(d, cfg.n)), Sin.f().apply(transform1(Math.max(d - 1, 0.0), cfg.n))))
                .map(p -> new Diff(cfg.name, p.nr, transform(p.abs, cfg.len), transform(p.abs, cfg.len) - transform(p.rel, cfg.len)));
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

class Diff {
    final String nam;
    final int nr;
    final double abs;
    final double rel;

    Diff(String nam, int nr, double abs, double rel) {
        this.nam = nam;
        this.nr = nr;
        this.abs = abs;
        this.rel = rel;
    }
}
