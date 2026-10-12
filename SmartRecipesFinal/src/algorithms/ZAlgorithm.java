
/**
 * Linear-time pattern preprocessing used as an alternate exact search implementation.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class ZAlgorithm {
    public static int[] zFunction(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;
        for (int i = 1; i < n; i++) {
            if (i <= r) z[i] = Math.min(r - i + 1, z[i - l]);
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;
            if (i + z[i] - 1 > r) { l = i; r = i + z[i] - 1; }
        }
        return z;
    }

    public static int indexOf(String text, String pattern) {
        if (pattern.length() == 0) return 0;
        String combined = pattern + '\u0001' + text;
        int[] z = zFunction(combined);
        for (int i = pattern.length() + 1; i < combined.length(); i++) if (z[i] >= pattern.length()) return i - pattern.length() - 1;
        return -1;
    }
}
