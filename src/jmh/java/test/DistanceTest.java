import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;

public class DistanceTest {

    private record Pos(double x, double y, double z) {

        private double distanceSquared(Pos other) {
            double dx = x - other.x;
            double dy = y - other.y;
            double dz = z - other.z;
            return dx * dx + dy * dy + dz * dz;
        }

        private double distance(Pos other) {
            return Math.sqrt(distanceSquared(other));
        }

    }

    private List<Pos> pos1 = new ArrayList<>();
    private List<Pos> pos2 = new ArrayList<>();
    private int nums = 10000;

    @Setup
    public void init() {
        for(int i = 0; i < nums; i++) {
            pos1.add(new Pos(Math.random(), Math.random(), Math.random()));
            pos2.add(new Pos(Math.random(), Math.random(), Math.random()));
        }
    }

    @Benchmark
    public void testDistance(Blackhole hole) {
        for(int i = 0; i < nums; i++) {
            hole.consume(pos1.get(i).distance(pos2.get(i)));
        }
    }

    @Benchmark
    public void testDistanceSquared(Blackhole hole) {
        for(int i = 0; i < nums; i++) {
            hole.consume(pos1.get(i).distanceSquared(pos2.get(i)));
        }
    }

}
