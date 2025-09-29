package com.example.dac;

import java.util.Arrays;

public class Select {
    // k is 0-based
    public static int select(int[] input, int k, Counters c) {
        if (k < 0 || k >= input.length) throw new IllegalArgumentException("k out of range");
        int[] a = Arrays.copyOf(input, input.length); // keep original intact for testing
        c.allocations++; // copy array
        return selectInPlace(a, 0, a.length - 1, k, c);
    }

    private static int selectInPlace(int[] a, int lo, int hi, int k, Counters c) {
        while (true) {
            if (lo == hi) return a[lo];
            int pivotVal = medianOfMedians(a, lo, hi, c);
            int p = partitionByValue(a, lo, hi, pivotVal, c);
            if (k == p) return a[p];
            if (k < p) hi = p - 1;
            else lo = p + 1;
        }
    }

    // Partition around a pivot value (not index), Lomuto-style
    private static int partitionByValue(int[] a, int lo, int hi, int pivotVal, Counters c) {
        int i = lo;
        for (int j = lo; j < hi; j++) {
            c.comparisons++;
            if (a[j] < pivotVal) { swap(a, i, j, c); i++; }
        }
        // place one pivot occurrence at i
        int pivotPos = -1;
        for (int j = hi; j >= lo; j--) {
            if (a[j] == pivotVal) { pivotPos = j; break; }
        }
        if (pivotPos == -1) pivotPos = hi; // should not happen
        swap(a, i, pivotPos, c);
        return i;
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Counters c) {
        int n = hi - lo + 1;
        if (n <= 5) {
            insertionSort(a, lo, hi, c);
            return a[lo + n / 2];
        }
        int numGroups = (n + 4) / 5;
        for (int g = 0; g < numGroups; g++) {
            int start = lo + 5 * g;
            int end = Math.min(start + 4, hi);
            insertionSort(a, start, end, c);
            int medianIndex = start + (end - start) / 2;
            swap(a, lo + g, medianIndex, c); // store medians at the front
        }
        // recursively select the median of the medians
        int medianOfMediansIndex = lo + numGroups / 2;
        return selectInPlace(a, lo, lo + numGroups - 1, medianOfMediansIndex, c);
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
