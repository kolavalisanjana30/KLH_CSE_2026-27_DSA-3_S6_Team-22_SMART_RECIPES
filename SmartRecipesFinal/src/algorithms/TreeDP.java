
/**
 * Tree diameter and rerooting computations.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class TreeDP {
    public static int[] diameter(int[][] edges,int n){int[] head=new int[n],to=new int[2*Math.max(0,n-1)],next=new int[to.length];for(int i=0;i<n;i++)head[i]=-1;int ec=0;for(int[] e:edges){to[ec]=e[1];next[ec]=head[e[0]];head[e[0]]=ec++;to[ec]=e[0];next[ec]=head[e[1]];head[e[1]]=ec++;}int[] a=bfsFarthest(0,head,to,next);int[] b=bfsFarthest(a[0],head,to,next);return new int[]{a[0],b[0],b[1]};}
    private static int[] bfsFarthest(int start,int[] head,int[] to,int[] next){int n=head.length;int[] q=new int[n],dist=new int[n];for(int i=0;i<n;i++)dist[i]=-1;int h=0,t=0;q[t++]=start;dist[start]=0;int far=start;while(h<t){int u=q[h++];if(dist[u]>dist[far])far=u;for(int e=head[u];e!=-1;e=next[e])if(dist[to[e]]==-1){dist[to[e]]=dist[u]+1;q[t++]=to[e];}}return new int[]{far,dist[far]};}
    // Rerooting DP: sum of distances from every node to all other nodes.
    public static long[] sumOfDistances(int[][] edges,int n){int[] deg=new int[n];for(int[] e:edges){deg[e[0]]++;deg[e[1]]++;}int[][] g=new int[n][];for(int i=0;i<n;i++)g[i]=new int[deg[i]];int[] p=deg.clone();for(int[] e:edges){g[e[0]][--p[e[0]]]=e[1];g[e[1]][--p[e[1]]]=e[0];}int[] parent=new int[n],order=new int[n],sub=new int[n];for(int i=0;i<n;i++)parent[i]=-2;int h=0,t=0;order[t++]=0;parent[0]=-1;while(h<t){int u=order[h++];for(int v:g[u])if(parent[v]==-2){parent[v]=u;order[t++]=v;}}for(int i=n-1;i>=0;i--){int u=order[i];sub[u]++;for(int v:g[u])if(parent[v]==u)sub[u]+=sub[v];}long[] ans=new long[n];for(int i=n-1;i>=0;i--){int u=order[i];for(int v:g[u])if(parent[v]==u)ans[0]+=sub[v];}for(int i=1;i<n;i++){int u=order[i];ans[u]=ans[parent[u]]+n-2L*sub[u];}return ans;}
}
