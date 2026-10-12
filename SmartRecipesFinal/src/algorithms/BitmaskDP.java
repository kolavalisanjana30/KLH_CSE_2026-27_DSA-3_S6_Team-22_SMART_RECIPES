
/**
 * Subset-state dynamic programming for route and Hamiltonian-path demonstrations.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class BitmaskDP {
    public static long tsp(int[][] cost){int n=cost.length;if(n==0)return 0;int N=1<<n;long INF=Long.MAX_VALUE/4;long[][] dp=new long[N][n];for(int m=0;m<N;m++)for(int i=0;i<n;i++)dp[m][i]=INF;dp[1][0]=0;for(int mask=1;mask<N;mask++)for(int u=0;u<n;u++)if((mask&(1<<u))!=0&&dp[mask][u]<INF)for(int v=0;v<n;v++)if((mask&(1<<v))==0){int nm=mask|(1<<v);dp[nm][v]=Math.min(dp[nm][v],dp[mask][u]+cost[u][v]);}long ans=INF;for(int u=1;u<n;u++)ans=Math.min(ans,dp[N-1][u]+cost[u][0]);return n==1?0:ans;}
    public static boolean hamiltonianPath(boolean[][] g){int n=g.length,N=1<<n;boolean[][] dp=new boolean[N][n];for(int i=0;i<n;i++)dp[1<<i][i]=true;for(int m=1;m<N;m++)for(int v=0;v<n;v++)if(dp[m][v])for(int u=0;u<n;u++)if((m&(1<<u))==0&&g[v][u])dp[m|1<<u][u]=true;for(int v=0;v<n;v++)if(dp[N-1][v])return true;return false;}
    public static int[] sos(int[] f){int n=0;while((1<<n)<f.length)n++;int[] g=f.clone();for(int b=0;b<n;b++)for(int mask=0;mask<f.length;mask++)if((mask&(1<<b))!=0)g[mask]+=g[mask^(1<<b)];return g;}
}
