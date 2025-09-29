package com.example.dac;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

public class ClosestPairTest {
    @Test
    void validateAgainstBruteForceOnSmallN() {
        Random r = new Random(555);
        for (int n : new int[]{2,3,5,10,50,200}) {
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i=0;i<n;i++) {
                pts[i] = new ClosestPair.Point(r.nextDouble(), r.nextDouble());
            }
            double fast = ClosestPair.closest(pts);
            double slow = ClosestPair.bruteForce(pts);
            assertEquals(slow, fast, 1e-9);
        }
    }
}
