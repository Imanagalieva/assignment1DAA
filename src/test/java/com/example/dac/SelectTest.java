package com.example.dac;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import java.util.Arrays;

public class SelectTest {
    @Test
    void compareAgainstSorting() {
        Random r = new Random(777);
        for (int trial = 0; trial < 100; trial++) {
            int n = 100 + r.nextInt(200);
            int[] a = new int[n];
            for (int i=0;i<n;i++) a[i] = r.nextInt(10_000);
            int k = r.nextInt(n);
            int[] sorted = Arrays.copyOf(a, n);
            Arrays.sort(sorted);
            int expected = sorted[k];
            Counters c = new Counters();
            int sel = Select.select(a, k, c);
            assertEquals(expected, sel);
        }
    }
}
