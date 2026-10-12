
/**
 * Level-graph blocking-flow implementation.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class Dinic {
    private static class Edge{int to,rev;long cap;Edge(int t,int r,long c){to=t;rev=r;cap=c;}}
    private Edge[][] g;private int[] level,it;private int[] deg;
    public Dinic(int n){g=new Edge[n][];deg=new int[n];}
    public void addEdge(int u,int v,long c){deg[u]++;deg[v]++;Edge a=new Edge(v,deg[v]-1,c),b=new Edge(u,deg[u]-1,0);addRaw(u,a);addRaw(v,b);}
    private void addRaw(int u,Edge e){Edge[] old=g[u];if(old==null){g[u]=new Edge[]{e};}else{Edge[] x=new Edge[old.length+1];for(int i=0;i<old.length;i++)x[i]=old[i];x[old.length]=e;g[u]=x;}}
    private boolean bfs(int s,int t){level=new int[g.length];for(int i=0;i<level.length;i++)level[i]=-1;int[] q=new int[g.length];int h=0,tt=0;q[tt++]=s;level[s]=0;while(h<tt){int u=q[h++];for(Edge e:g[u])if(e.cap>0&&level[e.to]<0){level[e.to]=level[u]+1;q[tt++]=e.to;}}return level[t]>=0;}
    private long dfs(int u,int t,long f){if(u==t)return f;for(;it[u]<g[u].length;it[u]++){Edge e=g[u][it[u]];if(e.cap>0&&level[e.to]==level[u]+1){long x=dfs(e.to,t,Math.min(f,e.cap));if(x>0){e.cap-=x;g[e.to][e.rev].cap+=x;return x;}}}return 0;}
    public long maxFlow(int s,int t){long ans=0,x;while(bfs(s,t)){it=new int[g.length];while((x=dfs(s,t,Long.MAX_VALUE))>0)ans+=x;}return ans;}
}
