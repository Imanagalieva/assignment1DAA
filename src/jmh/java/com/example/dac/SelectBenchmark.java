package com.example.dac;

import org.openjdk.jmh.annotations.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class SelectBenchmark {
    @Param({"1000","10000","100000"})
    int n;

    int[] data;
    int k;
    Counters c;

    @Setup(Level.Invocation)
    public void setup() {
        Random r = new Random(987);
        data = new int[n];
        for (int i=0;i<n;i++) data[i] = r.nextInt();
        k = n/2;
        c = new Counters();
    }

    @Benchmark
    public int deterministicSelect() {
        return Select.select(data, k, c);
    }

    @Benchmark
    public int fullSortThenPick() {
        int[] copy = Arrays.copyOf(data, data.length);
        Arrays.sort(copy);
        return copy[k];
    }
}
