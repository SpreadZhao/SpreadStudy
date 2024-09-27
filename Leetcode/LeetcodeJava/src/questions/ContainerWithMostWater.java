package questions;


/**
 * Link:<a href="https://leetcode.com/problems/container-with-most-water/">Container With Most Water</a>
 * <p>
 * Solution: <a href="https://leetcode.com/problems/container-with-most-water/solutions/1915172/java-c-easiest-explanations/">[Java/C++] Easiest Explanations</a>
 */
public class ContainerWithMostWater {

    // Time limit Exceed..
    public int maxArea(int[] height) {
        int maxSize = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int h = Math.min(height[i], height[j]);
                int size = (j - i) * h;
                if (size > maxSize) maxSize = size;
            }
        }
        return maxSize;
    }


    // After taught, I wrote it myself.
    public int maxArea1(int[] height) {
        int left = 0, right = height.length - 1, maxSize = 0;
        while (left < right) {
            int size;
            if (height[left] < height[right]) {
                size = (right - left) * height[left];
                ++left;
            } else {
                size = (right - left) * height[right];
                --right;
            }
            if (size > maxSize) maxSize = size;
        }
        return maxSize;
    }
}
