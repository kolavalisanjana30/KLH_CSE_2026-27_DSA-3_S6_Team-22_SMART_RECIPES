
/**
 * Quicksort with random pivot selection.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

import java.util.Random;

public class RandomizedQuickSort {
    private static final Random R=new Random(42);
    public static void sort(int[] a){quick(a,0,a.length-1);}
    private static void quick(int[] a,int l,int r){if(l>=r)return;int p=l+R.nextInt(r-l+1);swap(a,p,r);int x=a[r],i=l;for(int j=l;j<r;j++)if(a[j]<=x){swap(a,i,j);i++;}swap(a,i,r);quick(a,l,i-1);quick(a,i+1,r);}
    private static void swap(int[] a,int i,int j){int t=a[i];a[i]=a[j];a[j]=t;}
}
