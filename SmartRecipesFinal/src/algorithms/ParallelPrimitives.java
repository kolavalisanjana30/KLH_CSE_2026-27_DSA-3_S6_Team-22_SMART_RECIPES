
/**
 * Reference implementations for reduction, prefix sums, and work/span discussion.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class ParallelPrimitives {
    // Sequential reference versions of work/span primitives. They provide the algorithmic baseline
    // before introducing Java threads or GPU/distributed execution.
    public static long parallelReduceReference(int[] a){long sum=0;for(int x:a)sum+=x;return sum;}
    public static int[] parallelPrefixReference(int[] a){int[] out=new int[a.length];int s=0;for(int i=0;i<a.length;i++){s+=a[i];out[i]=s;}return out;}
    public static void parallelSortReference(int[] a){RandomizedQuickSort.sort(a);}
    public static String workSpanExplanation(){return "Reduce/scan are data-parallel: work is O(n), ideal span is O(log n). Brent's theorem bounds practical speedup by available parallelism and scheduling overhead.";}
}
