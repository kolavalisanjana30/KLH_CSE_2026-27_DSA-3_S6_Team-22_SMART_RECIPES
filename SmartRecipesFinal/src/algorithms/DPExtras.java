
/**
 * Tree dynamic programming for a maximum-weight independent set.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class DPExtras {
    public static int treeIndependentSet(int[][] edges,int n,int[] weight){int[] deg=new int[n];for(int[] e:edges){deg[e[0]]++;deg[e[1]]++;}int[][] g=new int[n][];for(int i=0;i<n;i++)g[i]=new int[deg[i]];int[] p=deg.clone();for(int[] e:edges){g[e[0]][--p[e[0]]]=e[1];g[e[1]][--p[e[1]]]=e[0];}int[] par=new int[n],order=new int[n];for(int i=0;i<n;i++)par[i]=-2;int h=0,t=0;order[t++]=0;par[0]=-1;while(h<t){int u=order[h++];for(int v:g[u])if(par[v]==-2){par[v]=u;order[t++]=v;}}int[] take=new int[n],skip=new int[n];for(int i=n-1;i>=0;i--){int u=order[i];take[u]=weight[u];for(int v:g[u])if(par[v]==u){take[u]+=skip[v];skip[u]+=Math.max(take[v],skip[v]);}}return Math.max(take[0],skip[0]);}
}
