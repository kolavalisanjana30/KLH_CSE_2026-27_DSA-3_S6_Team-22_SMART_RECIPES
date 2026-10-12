
/**
 * Uniform reservoir sampling for a stream.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

import java.util.Random;

public class ReservoirSampling {
    public static int[] sample(int[] stream,int k){if(k<=0||k>stream.length)throw new IllegalArgumentException("Invalid k");int[] res=new int[k];for(int i=0;i<k;i++)res[i]=stream[i];Random r=new Random(7);for(int i=k;i<stream.length;i++){int j=r.nextInt(i+1);if(j<k)res[j]=stream[i];}return res;}
}
