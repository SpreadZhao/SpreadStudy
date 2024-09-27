package questions;

import java.util.HashMap;
import java.util.Map;

/**
 * Link: <a href="https://leetcode.com/problems/design-add-and-search-words-data-structure">Design Add and Search Words Data Structure</a>
 * <p>
 * Very similar to Trie
 */
public class WordDictionary {

    TrieNode root;

    public WordDictionary() {
        root = new TrieNode();
    }

    public void addWord(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!p.children.containsKey(ch))
                p.children.put(ch, new TrieNode());
            p = p.children.get(ch);
        }
        p.isTerminal = true;
    }

    public boolean search(String word) {
        return searchHelper(root, word, 0);
    }

    private boolean searchHelper(TrieNode start, String word, int index) {
        if (index == word.length() && start.isTerminal) return true;
        if (index >= word.length()) return false;
        char ch = word.charAt(index);
        if (ch == '.') {
            index++;
            for (TrieNode child : start.children.values()) {
                if (searchHelper(child, word, index)) return true;
            }
            return false;
        } else {
            if (!start.children.containsKey(ch)) return false;
            return searchHelper(start.children.get(ch), word, ++index);
        }
    }

    class TrieNode {
        private Map<Character, TrieNode> children;
        private boolean isTerminal;

        TrieNode() {
            children = new HashMap<>();
            isTerminal = false;
        }
    }
}
