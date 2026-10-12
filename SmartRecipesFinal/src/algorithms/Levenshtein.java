
/**
 * Wagner-Fischer dynamic programming for minimum insert/delete/substitute distance.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class Levenshtein {
    public static int distance(String a, String b) {
        a=a.toLowerCase(); b=b.toLowerCase();
        int n=a.length(),m=b.length();
        int[] prev=new int[m+1],cur=new int[m+1];
        for(int j=0;j<=m;j++)prev[j]=j;
        for(int i=1;i<=n;i++){cur[0]=i;for(int j=1;j<=m;j++){int cost=a.charAt(i-1)==b.charAt(j-1)?0:1;cur[j]=Math.min(Math.min(cur[j-1]+1,prev[j]+1),prev[j-1]+cost);}int[] t=prev;prev=cur;cur=t;}
        return prev[m];
    }
    public static double similarity(String a,String b){int max=Math.max(a.length(),b.length());return max==0?1.0:1.0-(double)distance(a,b)/max;}
}
