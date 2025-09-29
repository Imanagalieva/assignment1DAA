# Divide-and-Conquer Algorithms – Assignment 1

##  Project Overview
This project implements and analyzes several classic divide-and-conquer algorithms:
- **MergeSort** (recursive merging with optimizations),
- **QuickSort** (randomized pivot, balanced recursion),
- **Deterministic Selection (Median of Medians)**,
- **Closest Pair of Points** (geometric divide-and-conquer).

The implementation includes:
- Depth tracking and allocation counters,
- Benchmarking scripts with CSV outputs,
- Plots for runtime and recursion depth,
- A structured GitHub workflow (branches, commits, releases).

---

##  Architecture Notes
- **Recursion depth** is explicitly tracked using a counter class.
- **Memory allocations** are controlled by:
  - Reusing buffers for MergeSort,
  - Avoiding redundant array copies in QuickSort and Select,
  - Iterative base cases for small arrays.
- **Metrics collection**: Each algorithm reports comparisons, allocations, and recursion depth, which are then written to CSV for plotting.
- **CLI interface** allows running algorithms with parameters and exporting results.

---

##  Algorithm Analysis

### 1. MergeSort
- **Recurrence:** T(n) = 2T(n/2) + Θ(n)
- **Method:** Master Theorem, Case 2.
- **Result:** Θ(n log n).
- **Notes:** Using buffer reuse reduces allocation overhead significantly.

### 2. QuickSort
- **Recurrence (average):** T(n) = 2T(n/2) + Θ(n).
- **Method:** Master Theorem, Case 2 (expected).
- **Result:** Θ(n log n) average, Θ(n²) worst-case.
- **Notes:** Randomized pivot keeps recursion depth ≲ 2·log₂(n) with high probability.

### 3. Deterministic Select (Median of Medians, MoM5)
- **Recurrence:** T(n) = T(n/5) + T(7n/10) + Θ(n).
- **Method:** Akra–Bazzi intuition.
- **Result:** Θ(n).
- **Notes:** Guarantees linear time even on adversarial inputs.

### 4. Closest Pair of Points
- **Recurrence:** T(n) = 2T(n/2) + Θ(n).
- **Method:** Master Theorem, Case 2.
- **Result:** Θ(n log n).
- **Notes:** Validated against the O(n²) brute-force algorithm for small n.

---

## 📈 Experimental Results

- **Time vs n:**
  - MergeSort and QuickSort showed Θ(n log n) scaling.
  - QuickSort was faster on average but with slightly higher depth variance.
  - Select confirmed linear behavior.
  - Closest Pair matched theory for n ≤ 10⁵.

- **Depth vs n:**
  - MergeSort: depth ≈ log₂(n).
  - QuickSort: randomized pivot depth stayed within ~2·log₂(n).
  - Select: recursion depth bounded by ~log₅(n).

- **Constant-factor effects:**
  - JVM warm-up and garbage collection introduced noise for small n.
  - Cache effects made MergeSort faster than expected on medium input sizes.

---

## 📑 Summary
- **Theory vs Measurements:**
  - The Θ results from Master and Akra–Bazzi matched empirical plots.
  - Constant factors mattered: QuickSort outperformed MergeSort on random data despite same asymptotic cost.
  - Memory reuse improved MergeSort performance significantly.

- **Conclusion:**  
  The implementation confirms theoretical complexity bounds while demonstrating practical optimizations for recursion depth and memory efficiency.

---

## 🔀 GitHub Workflow

### Branching Model
- **main** → stable releases only (tags: `v0.1`, `v1.0`).
- **feature/** branches:
  - `feature/mergesort`
  - `feature/quicksort`
  - `feature/select`
  - `feature/closest`
  - `feature/metrics`

### Commit Storyline
- `init: maven, junit5, readme`
- `feat(metrics): counters, depth tracker, CSV writer`
- `feat(mergesort): baseline + reuse buffer + cutoff + tests`
- `feat(quicksort): smaller-first recursion, randomized pivot + tests`
- `refactor(util): partition, swap, shuffle, guards`
- `feat(select): deterministic select (MoM5) + tests`
- `feat(closest): divide-and-conquer implementation + tests`
- `feat(cli): parse args, run algos, emit CSV`
- `bench(jmh): harness for select vs sort`
- `docs(report): master cases & AB intuition, initial plots`
- `fix: edge cases (duplicates, tiny arrays)`
- `release: v1.0`

---

## ✅ Testing
- **Sorting algorithms:** Verified correctness on random and adversarial arrays; recursion depth checks.
- **Select:** Compared against `Arrays.sort(a)[k]` for 100 random trials.
- **Closest Pair:** Compared to O(n²) solution for n ≤ 2000; only the fast version used for large n.

---

## 📎 Submission Info
Final submission is made as a GitHub repository.  
**Repository link (example):**  
[https://github.com/Imangaliyeva/assignment1DAA](https://github.com/Imangaliyeva/assignment1DAA)

---
