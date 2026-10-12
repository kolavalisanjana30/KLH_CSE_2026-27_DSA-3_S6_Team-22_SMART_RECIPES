
/**
 * Universal-hashing demonstration.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class HashingDemo {
    public static int universalHash(int key,int a,int b,int p,int m){long value=((long)a*key+b)%p;return (int)(value%m);}
}
