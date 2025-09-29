package com.example.dac;

public class DepthTracker {
    private int depth = 0;
    private int maxDepth = 0;

    public void push() {
        depth++;
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public void pop() {
        depth--;
        if (depth < 0) depth = 0;
    }

    public int getDepth() { return depth; }
    public int getMaxDepth() { return maxDepth; }
}
