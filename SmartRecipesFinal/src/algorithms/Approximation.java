
/**
 * Maximal-matching based approximation for vertex cover.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class Approximation {
    public static int[] vertexCover2Approx(boolean[][] graph){int n=graph.length;boolean[] used=new boolean[n],picked=new boolean[n];for(int u=0;u<n;u++)for(int v=u+1;v<n;v++)if(graph[u][v]&&!used[u]&&!used[v]){picked[u]=picked[v]=true;used[u]=used[v]=true;}int count=0;for(boolean x:picked)if(x)count++;int[] ans=new int[count];int k=0;for(int i=0;i<n;i++)if(picked[i])ans[k++]=i;return ans;}
    public static int maximalMatchingSize(boolean[][] graph){int n=graph.length,c=0;boolean[] used=new boolean[n];for(int u=0;u<n;u++)for(int v=u+1;v<n;v++)if(graph[u][v]&&!used[u]&&!used[v]){used[u]=used[v]=true;c++;}return c;}
}
