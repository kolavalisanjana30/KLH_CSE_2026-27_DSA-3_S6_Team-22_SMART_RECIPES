
/**
 * Interval dynamic programming for optimal multiplication order.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class MatrixChain {
    public static long minMultiplications(int[] p){int n=p.length-1;long[][] dp=new long[n][n];for(int len=2;len<=n;len++)for(int i=0;i+len-1<n;i++){int j=i+len-1;dp[i][j]=Long.MAX_VALUE;for(int k=i;k<j;k++){long q=dp[i][k]+dp[k+1][j]+(long)p[i]*p[k+1]*p[j];if(q<dp[i][j])dp[i][j]=q;}}return n==0?0:dp[0][n-1];}
}
