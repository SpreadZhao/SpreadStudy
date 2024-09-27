package questions;

import java.util.HashMap;
import java.util.Map;

/**
 * Link: <a href="https://leetcode.com/problems/implement-trie-prefix-tree/">Implement Tree</a>
 * <p>
 * Reference: <a href="https://en.wikipedia.org/wiki/Trie">Wikipedia</a>
 */
public class Trie {

    private Map<Character, Trie> children;
    private boolean isTerminal;
    private String val;

    public Trie() {
        isTerminal = false;
        children = new HashMap<>();
    }

    public Trie(String val) {
        isTerminal = false;
        children = new HashMap<>();
        this.val = val;
    }

    public void insert(String word) {
        Trie p = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!p.children.containsKey(ch))
                p.children.put(ch, new Trie(word.substring(0, i + 1)));
            p = p.children.get(ch);
        }
        p.setTerminal();
    }

    public void setTerminal() {
        this.isTerminal = true;
    }

    public boolean search(String word) {
        Trie p = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!p.children.containsKey(ch)) return false;
            p = p.children.get(ch);
        }
        return p.isTerminal;
    }

    public boolean startsWith(String prefix) {
        Trie p = this;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            if (!p.children.containsKey(ch)) return false;
            p = p.children.get(ch);
        }
        return true;
    }
}
