package com.example.dac;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            System.out.println("Usage: <algo> <n> <trials> <out.csv> [seed]");
            System.out.println("algos: mergesort|quicksort|select|closest");
            return;
        }
        String algo = args[0].toLowerCase();
        int n = Integer.parseInt(args[1]);
        int trials = Integer.parseInt(args[2]);
        String out = args[3];
        int seed = (args.length >= 5) ? Integer.parseInt(args[4]) : 42;

        try (PrintWriter pw = new PrintWriter(new FileWriter(out, true))) {
            if (pw.checkError()) { /* no-op */ }
            pw.println("algo,n,trial,nanos,depth,comparisons,swaps,allocations");

            for (int t = 1; t <= trials; t++) {
                Counters c = new Counters();
                DepthTracker dt = new DepthTracker();

                long start = System.nanoTime();
                if ("mergesort".equals(algo)) {
                    int[] a = randomArray(n, seed + t);
                    MergeSort.sort(a, c, dt);
                } else if ("quicksort".equals(algo)) {
                    int[] a = randomArray(n, seed + t);
                    QuickSort.sort(a, c, dt);
                } else if ("select".equals(algo)) {
                    int[] a = randomArray(n, seed + t);
                    int k = a.length / 2;
                    Select.select(a, k, c);
                } else if ("closest".equals(algo)) {
                    ClosestPair.Point[] pts = randomPoints(n, seed + t);
                    ClosestPair.closest(pts);
                } else {
                    System.out.println("Unknown algo: " + algo);
                    return;
                }
                long end = System.nanoTime();
                pw.printf("%s,%d,%d,%d,%d,%d,%d,%d%n",
                        algo, n, t, (end - start), dt.getMaxDepth(), c.comparisons, c.swaps, c.allocations);
                pw.flush();
            }
        }
        System.out.println("Done. Results appended to " + out);
    }

    private static int[] randomArray(int n, int seed) {
        Random r = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt();
        return a;
    }

    private static ClosestPair.Point[] randomPoints(int n, int seed) {
        Random r = new Random(seed);
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(r.nextDouble(), r.nextDouble());
        }
        return pts;
    }
}
