
/**
 * Interval dynamic programming for minimum weighted search cost.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class OptimalBST {
    public static long minCost(int[] freq){int n=freq.length;if(n==0)return 0;long[] prefix=new long[n+1];for(int i=0;i<n;i++)prefix[i+1]=prefix[i]+freq[i];long[][] dp=new long[n][n];for(int len=1;len<=n;len++)for(int i=0;i+len<=n;i++){int j=i+len-1;long sum=prefix[j+1]-prefix[i];dp[i][j]=Long.MAX_VALUE/4;for(int r=i;r<=j;r++){long left=r>i?dp[i][r-1]:0,right=r<j?dp[r+1][j]:0;dp[i][j]=Math.min(dp[i][j],left+right+sum);}}return dp[0][n-1];}
}
