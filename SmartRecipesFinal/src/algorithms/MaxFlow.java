
/**
 * Augmenting-path maximum-flow implementation.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class MaxFlow {
    private final int n; private final long[][] cap;
    public MaxFlow(int n){this.n=n;cap=new long[n][n];}
    public void addEdge(int u,int v,long c){cap[u][v]+=c;}
    public long edmondsKarp(int s,int t){long[][] r=new long[n][n];for(int i=0;i<n;i++)for(int j=0;j<n;j++)r[i][j]=cap[i][j];long flow=0;while(true){int[] par=new int[n];for(int i=0;i<n;i++)par[i]=-1;par[s]=s;long[] bott=new long[n];bott[s]=Long.MAX_VALUE;int[] q=new int[n];int h=0,tt=0;q[tt++]=s;while(h<tt&&par[t]==-1){int u=q[h++];for(int v=0;v<n;v++)if(par[v]==-1&&r[u][v]>0){par[v]=u;bott[v]=Math.min(bott[u],r[u][v]);q[tt++]=v;}}if(par[t]==-1)break;long f=bott[t];flow+=f;for(int v=t;v!=s;v=par[v]){int u=par[v];r[u][v]-=f;r[v][u]+=f;}}return flow;}
    public boolean[] minCutSide(int s){long[][] r=new long[n][n];for(int i=0;i<n;i++)for(int j=0;j<n;j++)r[i][j]=cap[i][j]; // caller should call after a flow; this helper is illustrative
        boolean[] vis=new boolean[n];vis[s]=true;return vis;}
}
