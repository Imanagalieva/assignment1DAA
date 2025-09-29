package com.example.dac;

public class Counters {
    public long comparisons = 0;   // # of element comparisons
    public long swaps = 0;         // # of element writes / swaps
    public long allocations = 0;   // # of temporary arrays allocated

    public void add(Counters other) {
        this.comparisons += other.comparisons;
        this.swaps += other.swaps;
        this.allocations += other.allocations;
    }

    @Override
    public String toString() {
        return "comparisons=" + comparisons + ", swaps=" + swaps + ", allocations=" + allocations;
    }
}
