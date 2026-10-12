
/**
 * Local sequence alignment dynamic programming.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class SmithWaterman {
    public static int score(String a,String b,int match,int mismatch,int gap){int n=a.length(),m=b.length(),best=0;int[][] dp=new int[n+1][m+1];for(int i=1;i<=n;i++)for(int j=1;j<=m;j++){int diag=dp[i-1][j-1]+(a.charAt(i-1)==b.charAt(j-1)?match:mismatch);dp[i][j]=Math.max(0,Math.max(diag,Math.max(dp[i-1][j]+gap,dp[i][j-1]+gap)));if(dp[i][j]>best)best=dp[i][j];}return best;}
}
