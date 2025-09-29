package com.example.dac;

import java.util.Arrays;

public class ClosestPair {
    public static record Point(double x, double y) {}

    public static double bruteForce(Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double d = dist(pts[i], pts[j]);
                if (d < best) best = d;
            }
        }
        return best;
    }

    public static double closest(Point[] pts) {
        Point[] px = Arrays.copyOf(pts, pts.length);
        Arrays.sort(px, (a, b) -> Double.compare(a.x(), b.x()));
        Point[] py = Arrays.copyOf(px, px.length);
        Arrays.sort(py, (a, b) -> Double.compare(a.y(), b.y()));
        Point[] tmp = new Point[pts.length];
        return rec(px, py, tmp, 0, px.length - 1);
    }

    private static double rec(Point[] px, Point[] py, Point[] tmp, int lo, int hi) {
        int n = hi - lo + 1;
        if (n <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = lo; i <= hi; i++) {
                for (int j = i + 1; j <= hi; j++) {
                    best = Math.min(best, dist(px[i], px[j]));
                }
            }
            // keep py sorted by y for this range
            Arrays.sort(px, lo, hi + 1, (a, b) -> Double.compare(a.y(), b.y()));
            return best;
        }

        int mid = lo + (hi - lo) / 2;
        double xm = px[mid].x();

        // Split py into left/right by x <= xm
        int lt = 0, rt = 0;
        for (int i = 0; i < py.length; i++) {
            if (py[i].x() <= xm && indexInRange(px, lo, mid, py[i])) {
                tmp[lt++] = py[i];
            } else if (indexInRange(px, mid + 1, hi, py[i])) {
                tmp[n - (++rt)] = py[i]; // fill from end to avoid overlap
            }
        }
        Point[] pyl = Arrays.copyOfRange(tmp, 0, lt);
        Point[] pyr = Arrays.copyOfRange(tmp, n - rt, n);

        double dl = rec(px, pyl, tmp, lo, mid);
        double dr = rec(px, pyr, tmp, mid + 1, hi);
        double d = Math.min(dl, dr);

        // Build strip: points within d of xm, ordered by y (pyl and pyr already sorted by y)
        int t = 0;
        for (Point p : py) {
            if (Math.abs(p.x() - xm) < d) tmp[t++] = p;
        }
        // Check up to next 7 neighbors in y-order
        for (int i = 0; i < t; i++) {
            for (int j = i + 1; j < t && (tmp[j].y() - tmp[i].y()) < d; j++) {
                d = Math.min(d, dist(tmp[i], tmp[j]));
            }
        }
        return d;
    }

    // Helper: check whether p exists in px[lo..hi] (by object identity / equal coords)
    private static boolean indexInRange(Point[] px, int lo, int hi, Point p) {
        for (int i = lo; i <= hi; i++) {
            if (px[i] == p) return true;
        }
        return false;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return Math.hypot(dx, dy);
    }
}
