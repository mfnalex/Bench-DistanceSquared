package test;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode({org.openjdk.jmh.annotations.Mode.AverageTime})
public class DistanceTest {

    private record Pos(double x, double y, double z) {
        private double distanceSquaredStandalone(Pos other) {
            double dx = x - other.x;
            double dy = y - other.y;
            double dz = z - other.z;
            return dx * dx + dy * dy + dz * dz;
        }
        private double distanceSquared(Pos other) {
            return Math.sqrt(distance(other));
        }
        private double distance(Pos other) {
            double dx = x - other.x;
            double dy = y - other.y;
            double dz = z - other.z;
            return Math.sqrt(dx * dx + dy * dy + dz * dz);
        }
    }
    private int nums = 1000;
    private Pos[] pos1 = new Pos[nums];
    private Pos[] pos2 = new Pos[nums];
    @Setup(Level.Invocation)
    public void init() {
        //System.out.println("init called");
        for(int i = 0; i < nums; i++) {
            pos1[i] = (new Pos(Math.random(), Math.random(), Math.random()));
            pos2[i] = (new Pos(Math.random(), Math.random(), Math.random()));
        }
    }
    @Benchmark
    public void testDistance(Blackhole hole) {
        for(int i = 0; i < nums; i++) {
            hole.consume(pos1[i].distance(pos2[i]));
        }
    }
    @Benchmark
    public void testDistanceSquared(Blackhole hole) {
        for(int i = 0; i < nums; i++) {
            hole.consume(pos1[i].distanceSquared(pos2[i]));
        }
    }
    @Benchmark
    public void testDistanceSquaredStandalone(Blackhole hole) {
        for(int i = 0; i < nums; i++) {
            hole.consume(pos1[i].distanceSquaredStandalone(pos2[i]));
        }
    }

}
