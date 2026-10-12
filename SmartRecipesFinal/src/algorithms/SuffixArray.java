package algorithms;

/** Suffix array by the doubling + counting-sort method: O(n log n). */
public class SuffixArray {
    public static int[] build(String s) {
        int n = s.length();
        if (n == 0) return new int[0];
        char[] a = new char[n + 1];
        a[0] = 0;
        for (int i = 0; i < n; i++) a[i + 1] = (char)(s.charAt(i) + 1);
        int N = n + 1;
        int[] p = new int[N], c = new int[N];
        int alphabet = Math.max(256, N + 2);
        int[] cnt = new int[Math.max(alphabet, 65538)];
        for (int i = 0; i < N; i++) cnt[a[i]]++;
        for (int i = 1; i < cnt.length; i++) cnt[i] += cnt[i - 1];
        for (int i = N - 1; i >= 0; i--) p[--cnt[a[i]]] = i;
        int classes = 1;
        c[p[0]] = 0;
        for (int i = 1; i < N; i++) { if (a[p[i]] != a[p[i-1]]) classes++; c[p[i]] = classes - 1; }
        int[] pn = new int[N], cn = new int[N];
        for (int h = 0; (1 << h) < N; h++) {
            int shift = 1 << h;
            for (int i = 0; i < N; i++) { pn[i] = p[i] - shift; if (pn[i] < 0) pn[i] += N; }
            for (int i = 0; i < classes; i++) cnt[i] = 0;
            for (int i = 0; i < N; i++) cnt[c[pn[i]]]++;
            for (int i = 1; i < classes; i++) cnt[i] += cnt[i-1];
            for (int i = N - 1; i >= 0; i--) p[--cnt[c[pn[i]]]] = pn[i];
            cn[p[0]] = 0; int newClasses = 1;
            for (int i = 1; i < N; i++) {
                int cur1 = c[p[i]], cur2 = c[(p[i] + shift) % N];
                int prev1 = c[p[i-1]], prev2 = c[(p[i-1] + shift) % N];
                if (cur1 != prev1 || cur2 != prev2) newClasses++;
                cn[p[i]] = newClasses - 1;
            }
            int[] tmp = c; c = cn; cn = tmp; classes = newClasses;
            if (classes == N) break;
        }
        int[] sa = new int[n]; int k = 0;
        for (int x : p) if (x != 0) sa[k++] = x - 1;
        return sa;
    }

    /** Kasai LCP: lcp[i] is LCP(sa[i], sa[i+1]). */
    public static int[] lcpKasai(String s, int[] sa) {
        int n = s.length();
        if (n <= 1) return new int[0];
        int[] rank = new int[n], lcp = new int[n - 1];
        for (int i = 0; i < n; i++) rank[sa[i]] = i;
        int h = 0;
        for (int i = 0; i < n; i++) {
            int r = rank[i];
            if (r == n - 1) { h = 0; continue; }
            int j = sa[r + 1];
            while (i + h < n && j + h < n && s.charAt(i + h) == s.charAt(j + h)) h++;
            lcp[r] = h;
            if (h > 0) h--;
        }
        return lcp;
    }
}
