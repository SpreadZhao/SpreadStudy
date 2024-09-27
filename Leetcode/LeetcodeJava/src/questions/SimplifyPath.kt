package questions

import java.util.*

/**
 * Link: [Simplify Path](https://leetcode.com/problems/simplify-path/)
 */
class SimplifyPath {
    fun simplifyPath(path: String): String {
        val tokens = StringTokenizer(path, "/")
        val builder = StringBuilder()
        while (tokens.hasMoreTokens()) {
            val token = tokens.nextToken()
            if (token == ".") continue
            if (token == "..") {
                val start = builder.toString().lastIndexOf("/")
                if (start != -1) builder.delete(start, builder.toString().length)
                continue
            }
            builder.append("/").append(token)
        }
        if (builder.toString() == "") builder.append("/")
        return builder.toString()
    }
    // /home/foo/../haha


    // Using Stack
    fun simplifyPath2(path: String): String {
        val stack = Stack<String>()
        val tokens = path.split("/")
        val builder = StringBuilder()
        for (token in tokens) {
            if (!stack.empty() && token == "..") stack.pop()
            else if (token != "" && token != "." && token != "..") stack.push(token)
        }
        if (stack.empty()) return "/"
        while (!stack.empty()) {
            builder.insert(0, stack.pop()).insert(0, "/")
        }
        return builder.toString()
    }
}