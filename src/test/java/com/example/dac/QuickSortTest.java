package com.example.dac;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.Arrays;

public class QuickSortTest {
    @Test
    void sortsRandomAndAdversarial() {
        Random r = new Random(321);
        for (int n : new int[]{1,2,3,5,10,100,10_000}) {
            int[] a = new int[n];
            for (int i=0;i<n;i++) a[i] = r.nextInt(1000);
            int[] expected = Arrays.copyOf(a, n);
            Arrays.sort(expected);
            Counters c = new Counters();
            DepthTracker dt = new DepthTracker();
            QuickSort.sort(a, c, dt);
            assertArrayEquals(expected, a);
            // depth should be O(log n) with randomized pivot + smaller-first
            int bound = 2 * (int)Math.floor(Math.log(Math.max(1,n))/Math.log(2)) + 50;
            assertTrue(dt.getMaxDepth() <= bound, "depth="+dt.getMaxDepth()+" bound="+bound);
        }

        // adversarial: descending, duplicates
        int[] desc = new int[1000];
        for (int i=0;i<desc.length;i++) desc[i] = desc.length - i;
        int[] expDesc = Arrays.copyOf(desc, desc.length);
        Arrays.sort(expDesc);
        Counters c = new Counters();
        DepthTracker dt = new DepthTracker();
        QuickSort.sort(desc, c, dt);
        assertArrayEquals(expDesc, desc);
    }
}
