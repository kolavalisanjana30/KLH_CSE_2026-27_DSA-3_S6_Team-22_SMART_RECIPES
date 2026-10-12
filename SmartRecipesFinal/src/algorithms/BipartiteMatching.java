
/**
 * Augmenting-path bipartite matching implementation.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class BipartiteMatching {
    public static int maxMatching(boolean[][] edge){int n=edge.length,m=edge[0].length;int[] match=new int[m];for(int i=0;i<m;i++)match[i]=-1;int ans=0;for(int u=0;u<n;u++){boolean[] seen=new boolean[m];if(dfs(u,edge,match,seen))ans++;}return ans;}
    private static boolean dfs(int u,boolean[][] e,int[] match,boolean[] seen){for(int v=0;v<e[u].length;v++)if(e[u][v]&&!seen[v]){seen[v]=true;if(match[v]==-1||dfs(match[v],e,match,seen)){match[v]=u;return true;}}return false;}
}
