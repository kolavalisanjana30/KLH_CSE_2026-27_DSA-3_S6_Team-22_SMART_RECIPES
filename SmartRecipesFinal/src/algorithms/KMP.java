
/**
 * Exact substring search used by the recipe search feature. Implements the prefix/failure preprocessing manually.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class KMP {
    public static int[] buildLPS(String pattern) {
        pattern = pattern.toLowerCase();
        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) lps[i++] = ++len;
            else if (len > 0) len = lps[len - 1];
            else lps[i++] = 0;
        }
        return lps;
    }

    public static boolean contains(String text, String pattern) {
        return indexOf(text, pattern) >= 0;
    }

    public static int indexOf(String text, String pattern) {
        text = text.toLowerCase(); pattern = pattern.toLowerCase();
        if (pattern.length() == 0) return 0;
        int[] lps = buildLPS(pattern);
        int i = 0, j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) { i++; j++; if (j == pattern.length()) return i - j; }
            else if (j > 0) j = lps[j - 1];
            else i++;
        }
        return -1;
    }
}
