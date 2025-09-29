package com.example.dac;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.Arrays;

public class MergeSortTest {
    @Test
    void sortsRandomArrays() {
        Random r = new Random(123);
        for (int n : new int[]{0,1,2,3,5,10,100,1_000}) {
            int[] a = new int[n];
            for (int i=0;i<n;i++) a[i] = r.nextInt(1000);
            int[] expected = Arrays.copyOf(a, n);
            Arrays.sort(expected);
            Counters c = new Counters();
            DepthTracker dt = new DepthTracker();
            MergeSort.sort(a, c, dt);
            assertArrayEquals(expected, a);
            assertTrue(dt.getMaxDepth() <= (int)(Math.floor(Math.log(Math.max(1,n))/Math.log(2))) + 32);
        }
    }
}
