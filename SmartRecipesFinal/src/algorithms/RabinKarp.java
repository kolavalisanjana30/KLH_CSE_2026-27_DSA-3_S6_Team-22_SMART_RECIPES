
/**
 * Rolling-hash based pattern search. The implementation uses two modular hashes to reduce collision risk.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class RabinKarp {
    private static final long MOD1 = 1_000_000_007L;
    private static final long MOD2 = 1_000_000_009L;
    private static final long BASE = 911382323L;

    public static int indexOf(String text, String pattern) {
        text = text.toLowerCase(); pattern = pattern.toLowerCase();
        int n = text.length(), m = pattern.length();
        if (m == 0) return 0;
        if (m > n) return -1;
        long ph1 = 0, ph2 = 0, th1 = 0, th2 = 0, pow1 = 1, pow2 = 1;
        for (int i = 0; i < m; i++) {
            ph1 = (ph1 * BASE + pattern.charAt(i)) % MOD1;
            ph2 = (ph2 * BASE + pattern.charAt(i)) % MOD2;
            th1 = (th1 * BASE + text.charAt(i)) % MOD1;
            th2 = (th2 * BASE + text.charAt(i)) % MOD2;
            if (i < m - 1) { pow1 = pow1 * BASE % MOD1; pow2 = pow2 * BASE % MOD2; }
        }
        for (int i = 0; i <= n - m; i++) {
            if (th1 == ph1 && th2 == ph2 && text.regionMatches(i, pattern, 0, m)) return i;
            if (i < n - m) {
                th1 = ((th1 - text.charAt(i) * pow1) % MOD1 + MOD1) % MOD1;
                th1 = (th1 * BASE + text.charAt(i + m)) % MOD1;
                th2 = ((th2 - text.charAt(i) * pow2) % MOD2 + MOD2) % MOD2;
                th2 = (th2 * BASE + text.charAt(i + m)) % MOD2;
            }
        }
        return -1;
    }
}
