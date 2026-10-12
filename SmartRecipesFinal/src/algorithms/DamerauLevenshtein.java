
/**
 * Edit-distance dynamic programming extended with adjacent transposition.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class DamerauLevenshtein {
    public static int distance(String a,String b){
        a=a.toLowerCase();b=b.toLowerCase();int n=a.length(),m=b.length();int[][] d=new int[n+1][m+1];
        for(int i=0;i<=n;i++)d[i][0]=i;for(int j=0;j<=m;j++)d[0][j]=j;
        for(int i=1;i<=n;i++)for(int j=1;j<=m;j++){int cost=a.charAt(i-1)==b.charAt(j-1)?0:1;d[i][j]=Math.min(Math.min(d[i-1][j]+1,d[i][j-1]+1),d[i-1][j-1]+cost);if(i>1&&j>1&&a.charAt(i-1)==b.charAt(j-2)&&a.charAt(i-2)==b.charAt(j-1))d[i][j]=Math.min(d[i][j],d[i-2][j-2]+1);}return d[n][m];
    }
}
