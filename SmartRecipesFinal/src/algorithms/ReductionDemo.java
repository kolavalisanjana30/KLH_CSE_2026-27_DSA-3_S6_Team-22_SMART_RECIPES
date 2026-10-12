
/**
 * Small reduction and subset-sum demonstrations for the complexity module.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class ReductionDemo {
    // Builds the graph used in the standard 3-SAT -> CLIQUE reduction.
    // Each clause contributes one vertex for each literal; connect vertices from different clauses
    // when their literals are not contradictory.
    public static boolean[][] threeSatToClique(int[][] literals){
        int clauses=literals.length, vertices=clauses*3;boolean[][] g=new boolean[vertices][vertices];
        for(int i=0;i<clauses;i++)for(int a=0;a<3;a++)for(int j=i+1;j<clauses;j++)for(int b=0;b<3;b++){int x=literals[i][a],y=literals[j][b];if(x!=-y)g[i*3+a][j*3+b]=g[j*3+b][i*3+a]=true;}
        return g;
    }
    public static boolean subsetSum(int[] a,int target){boolean[] dp=new boolean[target+1];dp[0]=true;for(int x:a)for(int s=target;s>=x;s--)dp[s]|=dp[s-x];return dp[target];}
}
