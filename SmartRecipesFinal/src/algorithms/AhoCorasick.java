
/**
 * Trie with failure links for searching several ingredient patterns in one pass.
 *
 * The implementation intentionally uses primitive arrays and explicit loops so the
 * algorithmic steps remain visible for the DSA-3 project review.
 */
package algorithms;

public class AhoCorasick {
    private static class Node {
        int[] next = new int[128];
        int fail = 0;
        boolean output = false;
        Node() { for (int i = 0; i < 128; i++) next[i] = -1; }
    }
    private Node[] nodes = new Node[512];
    private int size = 1;

    public AhoCorasick() { nodes[0] = new Node(); }
    private void ensure() { if (size == nodes.length) { Node[] n = new Node[nodes.length * 2]; for (int i=0;i<size;i++) n[i]=nodes[i]; nodes=n; } }
    public void addPattern(String p) {
        int cur=0; p=p.toLowerCase();
        for(int i=0;i<p.length();i++){ int c=p.charAt(i); if(c>=128) continue; if(nodes[cur].next[c]==-1){ensure();nodes[size]=new Node();nodes[cur].next[c]=size++;} cur=nodes[cur].next[c]; }
        nodes[cur].output=true;
    }
    public void build() {
        int[] q=new int[size]; int head=0,tail=0;
        for(int c=0;c<128;c++){int v=nodes[0].next[c]; if(v!=-1){nodes[v].fail=0;q[tail++]=v;} else nodes[0].next[c]=0;}
        while(head<tail){int v=q[head++]; for(int c=0;c<128;c++){int u=nodes[v].next[c]; if(u!=-1){nodes[u].fail=nodes[nodes[v].fail].next[c]; nodes[u].output |= nodes[nodes[u].fail].output; q[tail++]=u;} else nodes[v].next[c]=nodes[nodes[v].fail].next[c];}}
    }
    public boolean containsAny(String text){int cur=0;text=text.toLowerCase();for(int i=0;i<text.length();i++){int c=text.charAt(i);if(c>=128){cur=0;continue;}cur=nodes[cur].next[c];if(nodes[cur].output)return true;}return false;}
}
