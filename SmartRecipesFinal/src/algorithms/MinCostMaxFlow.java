
/**
 * Successive shortest augmenting paths for minimum-cost assignment.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class MinCostMaxFlow {
    private static class E{int to,rev,cap,cost;E(int t,int r,int c,int w){to=t;rev=r;cap=c;cost=w;}}
    private E[][] g;public MinCostMaxFlow(int n){g=new E[n][];}
    private void addRaw(int u,E e){E[] a=g[u];if(a==null)g[u]=new E[]{e};else{E[] b=new E[a.length+1];for(int i=0;i<a.length;i++)b[i]=a[i];b[a.length]=e;g[u]=b;}}
    public void addEdge(int u,int v,int cap,int cost){E a=new E(v,0,cap,cost),b=new E(u,0,0,-cost);a.rev=g[v]==null?0:g[v].length;b.rev=g[u]==null?0:g[u].length;addRaw(u,a);addRaw(v,b);}
    public int[] minCostMaxFlow(int s,int t){int n=g.length,flow=0,cost=0;while(true){int[] d=new int[n],pv=new int[n],pe=new int[n];boolean[] used=new boolean[n];for(int i=0;i<n;i++){d[i]=1_000_000_000;pv[i]=-1;pe[i]=-1;}d[s]=0;for(int it=0;it<n;it++){int u=-1;for(int i=0;i<n;i++)if(!used[i]&&d[i]<1_000_000_000&&(u==-1||d[i]<d[u]))u=i;if(u==-1)break;used[u]=true;for(int j=0;j<g[u].length;j++){E e=g[u][j];if(e.cap>0&&d[e.to]>d[u]+e.cost){d[e.to]=d[u]+e.cost;pv[e.to]=u;pe[e.to]=j;}}}if(pv[t]==-1)break;int f=1_000_000_000;for(int v=t;v!=s;v=pv[v])f=Math.min(f,g[pv[v]][pe[v]].cap);for(int v=t;v!=s;v=pv[v]){E e=g[pv[v]][pe[v]];e.cap-=f;g[v][e.rev].cap+=f;cost+=f*e.cost;}flow+=f;}return new int[]{flow,cost};}
}
