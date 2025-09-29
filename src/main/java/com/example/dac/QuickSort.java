package com.example.dac;

import java.util.Random;

public class QuickSort {
    private static final Random R = new Random();
    private static final int CUTOFF = 24;

    public static void sort(int[] a, Counters c, DepthTracker dt) {
        quicksort(a, 0, a.length - 1, c, dt);
    }

    private static void quicksort(int[] a, int lo, int hi, Counters c, DepthTracker dt) {
        while (lo < hi) {
            if (hi - lo + 1 <= CUTOFF) {
                insertionSort(a, lo, hi, c);
                return;
            }
            int p = partitionHoareRandom(a, lo, hi, c);
            // recurse on smaller side; iterate (tail-call) on larger
            int leftSize = p - lo + 1;       // Hoare partition returns index p s.t. [lo..p] <= pivot <= [p+1..hi]
            int rightSize = hi - (p + 1) + 1;
            if (leftSize < rightSize) {
                dt.push();
                quicksort(a, lo, p, c, dt);   // smaller first
                dt.pop();
                lo = p + 1;                   // iterate on the larger
            } else {
                dt.push();
                quicksort(a, p + 1, hi, c, dt);
                dt.pop();
                hi = p;
            }
        }
    }

    private static int partitionHoareRandom(int[] a, int lo, int hi, Counters c) {
        int pivotIndex = lo + R.nextInt(hi - lo + 1);
        int pivot = a[pivotIndex];
        swap(a, lo, pivotIndex, c); // move pivot near lo for cache locality
        int i = lo - 1;
        int j = hi + 1;
        while (true) {
            do { i++; c.comparisons++; } while (a[i] < pivot);
            do { j--; c.comparisons++; } while (a[j] > pivot);
            if (i >= j) return j;
            swap(a, i, j, c);
        }
    }

    private static void insertionSort(int[] a, int lo, int hi, Counters c) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                c.comparisons++;
                if (a[j] <= key) break;
                a[j + 1] = a[j]; c.swaps++;
                j--;
            }
            a[j + 1] = key; c.swaps++;
        }
    }

    private static void swap(int[] a, int i, int j, Counters c) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t; c.swaps += 2;
    }
}
