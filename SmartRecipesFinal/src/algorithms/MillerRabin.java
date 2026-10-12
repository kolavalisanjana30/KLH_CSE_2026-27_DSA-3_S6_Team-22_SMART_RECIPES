
/**
 * Probabilistic primality testing with modular exponentiation.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabin {
    public static boolean isPrime(long n,int rounds){if(n<2)return false;int[] small={2,3,5,7,11,13,17,19,23,29,31,37};for(int p:small){if(n==p)return true;if(n%p==0)return false;}long d=n-1;int s=0;while((d&1)==0){d>>=1;s++;}Random r=new Random(12345L+n);for(int i=0;i<rounds;i++){long a=2+Math.floorMod(r.nextLong(),n-3);long x=powMod(a,d,n);if(x==1||x==n-1)continue;boolean witness=true;for(int j=1;j<s;j++){x=mulMod(x,x,n);if(x==n-1){witness=false;break;}}if(witness)return false;}return true;}
    private static long mulMod(long a,long b,long mod){return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(mod)).longValue();}
    private static long powMod(long a,long e,long mod){long r=1;while(e>0){if((e&1)==1)r=mulMod(r,a,mod);a=mulMod(a,a,mod);e>>=1;}return r;}
}
