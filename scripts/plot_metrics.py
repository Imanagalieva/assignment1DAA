# Usage: python3 plot_metrics.py results.csv
import sys, csv
from collections import defaultdict
import matplotlib.pyplot as plt

if len(sys.argv) < 2:
    print("Usage: python3 plot_metrics.py results.csv")
    sys.exit(1)

path = sys.argv[1]
rows = []
with open(path, newline='') as f:
    rd = csv.DictReader(f)
    for row in rd:
        # coerce numeric
        for k in ['n','trial','nanos','depth','comparisons','swaps','allocations']:
            row[k] = int(row[k])
        rows.append(row)

def agg_by_n(algo, field):
    d = defaultdict(list)
    for r in rows:
        if r['algo'] == algo:
            d[r['n']].append(r[field])
    xs = sorted(d.keys())
    ys = [sum(d[x])/len(d[x]) for x in xs]
    return xs, ys

for algo in sorted(set(r['algo'] for r in rows)):
    xs, ys = agg_by_n(algo, 'nanos')
    plt.figure()
    plt.title(f"Time vs n ({algo})")
    plt.xlabel("n")
    plt.ylabel("nanoseconds (avg over trials)")
    plt.plot(xs, ys)
    plt.grid(True)

    xs, ys = agg_by_n(algo, 'depth')
    plt.figure()
    plt.title(f"Depth vs n ({algo})")
    plt.xlabel("n")
    plt.ylabel("max recursion depth (avg)")
    plt.plot(xs, ys)
    plt.grid(True)

plt.show()
