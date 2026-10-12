
/**
 * Converts common substring information into a normalized recipe similarity score.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class SimilarityEngine {
    public static double similarity(String a,String b){
        String left=a.toLowerCase(),right=b.toLowerCase();String combined=left+'\u0000'+right;int[] sa=SuffixArray.build(combined);int[] lcp=SuffixArray.lcpKasai(combined,sa);int best=0;
        for(int i=0;i<lcp.length;i++){boolean x=sa[i]<left.length(),y=sa[i+1]<left.length();if(x!=y&&lcp[i]>best)best=lcp[i];}
        int total=left.length()+right.length();return total==0?1.0:(2.0*best/total);
    }
    public static int longestCommonSubstring(String a,String b){return (int)Math.round(similarity(a,b)*(a.length()+b.length())/2.0);}
}
