package com.example.dac;

public class MergeSort {
    private static final int CUTOFF = 24; // insertion sort cutoff

    public static void sort(int[] a, Counters c, DepthTracker dt) {
        int n = a.length;
        int[] aux = new int[n];
        c.allocations++; // one reusable buffer
        sort(a, aux, 0, n - 1, c, dt);
    }

    private static void sort(int[] a, int[] aux, int lo, int hi, Counters c, DepthTracker dt) {
        if (lo >= hi) return;
        if (hi - lo + 1 <= CUTOFF) {
            insertionSort(a, lo, hi, c);
            return;
        }
        dt.push();
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid, c, dt);
        sort(a, aux, mid + 1, hi, c, dt);
        // If already in order, skip merge
        c.comparisons++;
        if (a[mid] <= a[mid + 1]) {
            dt.pop();
            return;
        }
        merge(a, aux, lo, mid, hi, c);
        dt.pop();
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi, Counters c) {
        // copy to aux
        int len = hi - lo + 1;
        System.arraycopy(a, lo, aux, lo, len);
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            c.comparisons++;
            if (aux[i] <= aux[j]) {
                a[k++] = aux[i++]; c.swaps++;
            } else {
                a[k++] = aux[j++]; c.swaps++;
            }
        }
        while (i <= mid) { a[k++] = aux[i++]; c.swaps++; }
        while (j <= hi)  { a[k++] = aux[j++]; c.swaps++; }
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
}
