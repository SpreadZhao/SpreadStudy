package questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Link: <a href="https://leetcode.com/problems/regular-expression-matching/">Expression Matching</a>
 */
public class ExpressionMatching {

    private final List<Edge> edges = new ArrayList<>();

    public boolean isMatch(String s, String p) {
        // Phase One: Determine Input(ch, ch*, ., .*)
        int num = 0;
        State prev = new State(num, false);
        if (p.length() == 1) {
            State to = new State(++num, true);
            edges.add(new Edge(prev, to, p.charAt(0)));
        } else if (p.length() == 2) {
            if (p.charAt(1) == '*') {
                edges.add(new Edge(prev, prev, p.charAt(0)));
            } else {
//                if(p.charAt(0) == p.charAt(1)){
//                    edges.add(new Edge(prev, prev, p.charAt(0)));
//                }else{
                State s1 = new State(1, false);
                State s2 = new State(2, true);
                edges.add(new Edge(prev, s1, p.charAt(0)));
                edges.add(new Edge(s1, s2, p.charAt(1)));
//                }
            }
        } else {
            for (int i = 0; i < p.length(); i++) {
                char ch = p.charAt(i);
                if (i == p.length() - 1) {
                    // State to = ch == p.charAt(i - 2) ? prev : new State(++num, false);
                    State to = new State(++num, true);
                    edges.add(new Edge(prev, to, ch));
                    prev = to;
                } else if (p.charAt(i + 1) == '*') { // ch* or .
                    edges.add(new Edge(prev, prev, ch));
                    i++;
                } else { // only ch or .
                    //State to = ch == p.charAt(i - 2) ? prev : new State(++num, false);
                    State to = new State(++num, false);
                    edges.add(new Edge(prev, to, ch));
                    prev = to;
                }
//            if(Character.isLetter(ch)){
//                if(p.charAt(i + 1) == '*'){ // ch*
//                    edges.add(new Edge(prev, prev, String.valueOf(ch) + p.charAt(i + 1)));
//                }else{ // only ch
//                    State to = new State(++num, false);
//                    edges.add(new Edge(prev, to, String.valueOf(ch)));
//                    prev = to;
//                }
//            }else if(ch == '.'){
//                if(p.charAt(i + 1) == '*'){ // only .
//
//                }else{ // .*
//
//                }
//            }
            }
        }


        if (!edges.isEmpty()) edges.get(edges.size() - 1).to.isFinal = true;

        int pointer = 0, stateNum = 0;
        while (stateNum != -1 && pointer < s.length()) {
            char ch = s.charAt(pointer++);
            stateNum = findNextState(stateNum, ch);
        }

        return pointer == s.length() && edges.get(edges.size() - 1).to.num == stateNum;

    }

    private int findNextState(int fromNum, char input) {
        // rather to forward first
        for (Edge edge : edges) {
            if (edge.from.num == fromNum && (edge.input == input || edge.input == '.')) return edge.to.num;
        }
        return -1;
    }

    public boolean isMatch1(String s, String p) {
        if (p == null || p.length() == 0) return (s == null || s.length() == 0);

        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        for (int j = 2; j <= p.length(); j++) {
            dp[0][j] = p.charAt(j - 1) == '*' && dp[0][j - 2];
        }

        for (int j = 1; j <= p.length(); j++) {
            for (int i = 1; i <= s.length(); i++) {
                if (p.charAt(j - 1) == s.charAt(i - 1) || p.charAt(j - 1) == '.')
                    dp[i][j] = dp[i - 1][j - 1];
                else if (p.charAt(j - 1) == '*')
                    dp[i][j] = dp[i][j - 2] || ((s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') && dp[i - 1][j]);
            }
        }
        return dp[s.length()][p.length()];
    }

    // State of FA, 0(num) means start
    static class State {
        public boolean isFinal;
        public int num;

        public State() {
        }

        public State(int num, boolean isFinal) {
            this.isFinal = isFinal;
            this.num = num;
        }
    }

    static class Edge {
        public State from;
        public State to;
        public char input;

        public Edge(State from, State to, char input) {
            this.from = from;
            this.to = to;
            this.input = input;
        }
    }
}