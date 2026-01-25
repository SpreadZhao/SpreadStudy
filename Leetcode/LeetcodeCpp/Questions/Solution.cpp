//
// Created by SpreadZhao on 2024/7/21.
//
#include "Solution.h"

#include <limits.h>

#include <algorithm>
#include <cstddef>
#include <cstdlib>
#include <cstring>
#include <iterator>
#include <list>
#include <map>
#include <queue>
#include <set>
#include <stack>
#include <string>
#include <type_traits>
#include <utility>
#include <vector>

#include "../Util/UnionFind.h"

string Solution::addBinary(string a, string b) {
    int i = a.size() - 1;
    int j = b.size() - 1;
    int carry = 0;
    vector<int> res;
    while (i >= 0 || j >= 0) {
        int x = 0, y = 0;
        if (i >= 0) {
            x = a[i] - '0';
        }
        if (j >= 0) {
            y = b[j] - '0';
        }
        cout << "x: " << x << endl;
        cout << "y: " << y << endl;
        int num = (x + y + carry) % 2;
        if (x + y + carry > 1) {
            carry = 1;
        } else {
            carry = 0;
        }
        res.insert(res.begin(), num);
        i--;
        j--;
    }
    if (carry == 1) {
        res.insert(res.begin(), 1);
    }
    stringstream ss;
    for (int re : res) {
        ss << re;
    }
    return ss.str();
}

vector<vector<string>> Solution::accountsMerge(
    vector<vector<string>> &accounts) {
    map<string, int> emailToIndex;
    map<string, string> emailToName;
    int emailsCount = 0;
    for (auto &account : accounts) {
        string &name = account[0];
        int size = account.size();
        for (int i = 1; i < size; i++) {
            string &email = account[i];
            if (!emailToIndex.count(email)) {
                emailToIndex[email] = emailsCount++;
                emailToName[email] = name;
            }
        }
    }
    UnionFind uf(emailsCount);
    for (auto &account : accounts) {
        string &firstEmail = account[1];
        int firstIndex = emailToIndex[firstEmail];
        int size = account.size();
        for (int i = 2; i < size; i++) {
            string &nextEmail = account[i];
            int nextIndex = emailToIndex[nextEmail];
            uf.unionSet(firstIndex, nextIndex);
        }
    }
    map<int, vector<string>> indexToEmails;
    for (auto &[email, _] : emailToIndex) {
        int index = uf.find(emailToIndex[email]);
        vector<string> &account = indexToEmails[index];
        account.emplace_back(email);
        indexToEmails[index] = account;
    }
    vector<vector<string>> merged;
    for (auto &[_, emails] : indexToEmails) {
        sort(emails.begin(), emails.end());
        string &name = emailToName[emails[0]];
        vector<string> account;
        account.emplace_back(name);
        for (auto &email : emails) {
            account.emplace_back(email);
        }
        merged.emplace_back(account);
    }
    return merged;
}

int Solution::numRescueBoats(vector<int> &people, int limit) {
    int i = 0, j = people.size() - 1;
    std::sort(people.begin(), people.end());
    int numOfBoats = 0;
    while (i <= j) {
        if (i == j) {
            numOfBoats++;
            break;
        }
        int heavy = people.at(j);
        int light = people.at(i);
        if (heavy + light <= limit) {
            i++;
            j--;
        } else {
            j--;
        }
        numOfBoats++;
    }
    return numOfBoats;
}

int findNext1(vector<int> &flowerbed, int start) {
    for (int i = start + 1; i < flowerbed.size(); ++i) {
        int flower = flowerbed.at(i);
        if (flower == 1) {
            return i;
        }
    }
    return -1;
}

bool Solution::canPlaceFlowers(vector<int> &flowerbed, int n) {
    int zeroCount = 0, lastIndexOf1 = 0, indexOf1 = 0, flowerCount = 0;
    // Find first 1
    int first1Index = findNext1(flowerbed, -1);
    if (first1Index >= 0) {
        zeroCount = first1Index;
        if (CommonUtil::isEven(zeroCount)) {
            flowerCount += zeroCount / 2;
        } else {
            flowerCount += (zeroCount - 1) / 2;
        }
        indexOf1 = first1Index;
        lastIndexOf1 = first1Index;
        while ((indexOf1 = findNext1(flowerbed, indexOf1)) >= 0) {
            zeroCount = indexOf1 - lastIndexOf1 - 1;
            if (CommonUtil::isEven(zeroCount)) {
                flowerCount += zeroCount / 2 - 1;
            } else {
                flowerCount += (zeroCount + 1) / 2 - 1;
            }
            lastIndexOf1 = indexOf1;
        }
        if (lastIndexOf1 < flowerbed.size() - 1) {
            zeroCount = flowerbed.size() - lastIndexOf1 - 1;
            if (CommonUtil::isEven(zeroCount)) {
                flowerCount += zeroCount / 2;
            } else {
                flowerCount += (zeroCount - 1) / 2;
            }
        }
    } else {
        zeroCount = flowerbed.size();
        if (CommonUtil::isEven(zeroCount)) {
            flowerCount += zeroCount / 2;
        } else {
            flowerCount += (zeroCount + 1) / 2;
        }
    }
    return flowerCount >= n;
}

int findNext12(vector<int> &flowerbed, int start) {
    for (int i = start + 1; i < flowerbed.size(); ++i) {
        int flower = flowerbed.at(i);
        if (flower == 1) {
            return i;
        }
    }
    return flowerbed.size();
}

bool Solution::canPlaceFlowers2(vector<int> &flowerbed, int n) {
    int i = -1, j = -1, flowerCount = 0;
    // https://stackoverflow.com/questions/16250058/why-is-1-a-size-false-even-though-stdvectors-size-is-positive
    while (i == -1 || i < flowerbed.size()) {
        j = findNext12(flowerbed, i);
        int zeroCount = j - i - 1;
        if (i == -1) zeroCount++;
        if (j == flowerbed.size()) zeroCount++;
        flowerCount += zeroCount == 0 ? 0 : (zeroCount + 1) / 2 - 1;
        i = j;
    }
    return flowerCount >= n;
}

int Solution::makeConnected(int n, vector<vector<int>> &connections) {
    if (connections.size() < n - 1) {
        return -1;
    }
    CommonUtil::DFSResponse response = CommonUtil::dfs(n, connections);
    return response.islandNum - 1;
}

int Solution::maxArea(vector<int> &height) {
    int i = 0, j = height.size() - 1;
    int maxVolume = 0;
    while (i < j) {
        int volume = (j - i) * min(height[i], height[j]);
        if (volume > maxVolume) {
            maxVolume = volume;
        }
        if (height[i] < height[j]) {
            i++;
        } else {
            j--;
        }
    }
    return maxVolume;
}

bool contains(vector<int> &nums, int target) {
    const auto res = find(nums.begin(), nums.end(), target);
    return res != nums.end();
}

vector<vector<int>> Solution::findDifference(vector<int> &nums1,
                                             vector<int> &nums2) {
    vector<int> list1;
    vector<int> list2;
    for (auto num : nums1) {
        if (!contains(nums2, num) && !contains(list1, num)) {
            list1.emplace_back(num);
        }
    }
    for (auto num : nums2) {
        if (!contains(nums1, num) && !contains(list2, num)) {
            list2.emplace_back(num);
        }
    }
    return vector<vector<int>>{list1, list2};
}

vector<vector<int>> Solution::findDifference2(vector<int> &nums1,
                                              vector<int> &nums2) {
    unordered_set<int> set1, set2;
    for (int num : nums1) {
        set1.insert(num);
    }
    for (int num : nums2) {
        set2.insert(num);
    }
    vector<vector<int>> res(2);
    for (int num : set1) {
        if (!set2.count(num)) {
            res[0].push_back(num);
        }
    }
    for (int num : set2) {
        if (!set1.count(num)) {
            res[1].push_back(num);
        }
    }
    return res;
}

TreeNode *Solution::invertTree(TreeNode *root) {
    if (root == nullptr) {
        return root;
    }
    TreeNode *temp = root->left;
    root->left = root->right;
    root->right = temp;
    invertTree(root->left);
    invertTree(root->right);
    return root;
}

int Solution::numWays(vector<string> &words, string target) {
    constexpr int ALPHABET = 26;
    constexpr int MOD = 1e9 + 7;
    const size_t k = words[0].size();
    int columnToCount[ALPHABET][k];
    memset(columnToCount, 0, sizeof(columnToCount));
    // iterator over the columns, calculating the count of every character.
    for (int j = 0; j < k; ++j) {
        for (auto str : words) {
            columnToCount[str[j] - 'a'][j]++;
        }
    }
    // CommonUtil::printMetric(&columnToCount[0][0], ALPHABET, k);
    const size_t m = target.size();
    vector<vector<int>> dp(m + 1, vector<int>(k + 1, 0));
    dp[0][0] = 1;
    for (int i = 0; i <= m; i++) {
        for (int j = 0; j < k; j++) {
            if (i < m) {
                dp[i + 1][j + 1] += static_cast<int>(
                    static_cast<long>(columnToCount[target[i] - 'a'][j]) *
                    dp[i][j] % MOD);
            }
            dp[i][j + 1] += dp[i][j] % MOD;
        }
    }
    return dp[m][k];
}

vector<bool> Solution::kidsWithCandies(vector<int> &candies, int extraCandies) {
    auto maxCandies = max_element(candies.begin(), candies.end());
    vector<bool> res;
    if (maxCandies != candies.end()) {
        const int maxCandiesCount = *maxCandies;
        for (const auto candie : candies) {
            res.emplace_back(candie + extraCandies > maxCandiesCount);
        }
    }
    return res;
}

int Solution::lengthOfLIS(vector<int> &nums) {
    const size_t size = nums.size();
    int dp[size];
    memset(dp, 0, sizeof(dp));
    dp[0] = 1;
    int res = 1;
    for (int i = 1; i < size; i++) {
        dp[i] = 1;
        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j]) {
                dp[i] = std::max(dp[i], dp[j] + 1);
            }
        }
        res = std::max(res, dp[i]);
    }
    cout << endl;
    return res;
}

int Solution::lengthOfLIS2(vector<int> &nums) {
    int len = 1, n = nums.size();
    if (n == 0) {
        return 0;
    }
    int d[n + 1];
    memset(d, 0, sizeof(d));
    d[len] = nums[0];
    for (int i = 1; i < n; i++) {
        if (nums[i] > d[len]) {
            d[++len] = nums[i];
        } else {
            int l = 1, r = len, pos = 0;
            while (l <= r) {
                int mid = (l + r) >> 1;
                if (d[mid] < nums[i]) {
                    pos = mid;
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            d[pos + 1] = nums[i];
        }
    }
    for (int i = 0; i < n + 1; i++) {
        cout << "d[" << i << "] = " << d[i] << ", ";
    }
    cout << endl;
    return len;
}

static const vector<string> digitsToLetters = {
    "",  // 0
    "",  // 1
    "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv",
    "wxyz"  // 9
};

void putCh(vector<string> &res, string digits, string letters, int digitIndex) {
    if (digitIndex >= digits.size()) {
        res.emplace_back(letters);
        return;
    }
    for (auto ch : digitsToLetters[digits[digitIndex] - '0']) {
        putCh(res, digits, letters + ch, digitIndex + 1);
    }
}

vector<string> Solution::letterCombinations(string digits) {
    if (digits.empty()) {
        return {};
    }
    vector<string> letters;
    putCh(letters, digits, "", 0);
    return letters;
}

vector<vector<int>> Solution::levelOrder(TreeNode *root) {
    vector<vector<int>> res;
    if (root == nullptr) {
        return res;
    }
    queue<TreeNode *> nodes;
    nodes.push(root);
    while (!nodes.empty()) {
        int currLevelSize = nodes.size();
        res.emplace_back();
        for (int i = 0; i < currLevelSize; i++) {
            auto node = nodes.front();
            nodes.pop();
            res.back().emplace_back(node->val);
            if (node->left) {
                nodes.push(node->left);
            }
            if (node->right) {
                nodes.push(node->right);
            }
        }
    }
    return res;
}

ListNode *Solution::getIntersectionNode(ListNode *headA, ListNode *headB) {
    if (headA == nullptr || headB == nullptr) {
        return nullptr;
    }
    ListNode *p1 = headA, *p2 = headB;
    int terminate = 0;
    while (p1 != p2) {
        if (p1->next == nullptr) {
            p1 = headB;
            if (terminate > 0) {
                return nullptr;
            }
            terminate++;
        } else {
            p1 = p1->next;
        }
        if (p2->next == nullptr) {
            p2 = headA;
        } else {
            p2 = p2->next;
        }
    }
    return p1;
}

ListNode *Solution::getIntersectionNode2(ListNode *headA, ListNode *headB) {
    if (headA == nullptr || headB == nullptr) {
        return nullptr;
    }
    ListNode *p1 = headA, *p2 = headB;
    while (p1 != p2) {
        if (p1 == nullptr) {
            p1 = headB;
        } else {
            p1 = p1->next;
        }
        if (p2 == nullptr) {
            p2 = headA;
        } else {
            p2 = p2->next;
        }
    }
    return p1;
}

string Solution::longestCommonPrefix(vector<string> &strs) {
    string res = "";
    sort(strs.begin(), strs.end());
    string first = strs[0];
    string last = strs.back();
    for (int i = 0; i < first.size(); i++) {
        if (first[i] != last[i]) {
            break;
        }
        res += first[i];
    }
    return res;
}

int Solution::longestPalindromeSubseq(string s) {
    int dp[s.size()][s.size()];
    for (int i = 0; i < s.size(); i++) {
        dp[i][i] = 1;
    }
    for (int j = 1; j < s.size(); j++) {
        for (int i = j - 1; i >= 0; i--) {
            if (j - i < 2) {
                dp[i][j] = s[i] == s[j] ? 2 : 1;
            } else {
                if (s[i] == s[j]) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
    }
    return dp[0][s.size() - 1];
}

string Solution::longestPalindromicSubstring(string s) {
    if (s.empty()) {
        return "";
    }
    bool dp[s.size()][s.size()];
    memset(dp, false, sizeof(dp));
    for (int i = 0; i < s.size(); i++) {
        dp[i][i] = true;
    }
    string res(1, s[0]);
    for (int j = 1; j < s.size(); j++) {
        for (int i = j - 1; i >= 0; i--) {
            if (j - i == 1) {
                dp[i][j] = s[i] == s[j];
            } else {
                dp[i][j] = s[i] == s[j] && dp[i + 1][j - 1];
            }
            if (dp[i][j] && j - i + 1 > res.size()) {
                res = s.substr(i, j - i + 1);
            }
        }
    }
    return res;
}

int Solution::lengthOfLongestSubstring(string s) {
    if (s.empty()) {
        return 0;
    }
    if (s.size() == 1) {
        return 1;
    }
    if (s.size() == 2) {
        if (s[0] == s[1]) {
            return 1;
        }
        return 2;
    }
    int i = 0, j = 1, temp = 1, res = 1;
    map<char, bool> showed;
    showed[s[0]] = true;
    while (j < s.size()) {
        if (showed[s[j]]) {
            showed.clear();
            if (temp > res) {
                res = temp;
            }
            temp = 1;
            j = i + 1;
            i = j;
        } else {
            temp++;
        }
        showed[s[j]] = true;
        j++;
    }
    if (temp == res) {
        res = temp;
    }
    return res;
}

int Solution::lengthOfLongestSubstring2(string s) {
    map<char, int> showAt;
    int i = 0, j = 0, res = 0;
    while (j < s.size()) {
        char ch = s[j];
        if (showAt.count(ch)) {
            i = max(i, showAt[ch] + 1);
        }
        showAt[ch] = j;
        res = max(res, j - i + 1);
        j++;
    }
    return res;
}

TreeNode *Solution::lowestCommonAncestor(TreeNode *root, TreeNode *p,
                                         TreeNode *q) {
    TreeNode *curr = root;
    int target = p->val;
    set<TreeNode *> route;
    while (curr->val != target) {
        route.insert(curr);
        if (curr->val < target) {
            curr = curr->right;
        } else {
            curr = curr->left;
        }
    }
    route.insert(curr);
    curr = root;
    TreeNode *prev = nullptr;
    target = q->val;
    while (curr->val != target) {
        if (!route.count(curr)) {
            return prev;
        }
        prev = curr;
        if (curr->val < target) {
            curr = curr->right;
        } else {
            curr = curr->left;
        }
    }
    if (route.count(curr)) {
        return curr;
    }
    return prev;
}

TreeNode *Solution::lowestCommonAncestor2(TreeNode *root, TreeNode *p,
                                          TreeNode *q) {
    TreeNode *curr = root;
    while (true) {
        if (curr->val < p->val && curr->val < q->val) {
            curr = curr->right;
        } else if (curr->val > p->val && curr->val > q->val) {
            curr = curr->left;
        } else {
            break;
        }
    }
    return curr;
}

TreeNode *Solution::lowestCommonAncestor3(TreeNode *root, TreeNode *p,
                                          TreeNode *q) {
    if (root == nullptr || root == p || root == q) {
        return root;
    }
    auto *left = lowestCommonAncestor3(root->left, p, q);
    auto *right = lowestCommonAncestor3(root->right, p, q);
    if (left != nullptr && right != nullptr) {
        return root;
    }
    return left != nullptr ? left : right;
}

int Solution::maxDepth(TreeNode *root) {
    if (root == nullptr) {
        return 0;
    }
    if (root->left == nullptr && root->right == nullptr) {
        return 1;
    }
    int depth = 1;
    depth += max(maxDepth(root->left), maxDepth(root->right));
    return depth;
}

int Solution::maxDepth2(TreeNode *root) {
    if (root == nullptr) {
        return 0;
    }
    queue<TreeNode *> q;
    q.push(root);
    int depth = 0;
    while (!q.empty()) {
        depth++;
        const size_t size = q.size();
        for (size_t i = 0; i < size; i++) {
            TreeNode *curr = q.front();
            if (curr->left != nullptr) {
                q.push(curr->left);
            }
            if (curr->right != nullptr) {
                q.push(curr->right);
            }
            q.pop();
        }
    }
    return depth;
}

int Solution::minimizeArrayValueError(vector<int> &nums) {
    int minMax = nums[0];
    for (int i = 1; i < nums.size(); i++) {
        if (nums[i] > minMax) {
            int sum = nums[i] + nums[i - 1];
            if (sum % 2 == 0) {
                nums[i - 1] = sum / 2;
                nums[i] = sum / 2;
            } else {
                nums[i - 1] = sum / 2;
                nums[i] = sum / 2 + 1;
            }
            if (i == 1) {
                minMax = nums[i];
            } else if (nums[i] > minMax) {
                minMax = nums[i];
            }
        }
    }
    return minMax;
}

int Solution::minimizeArrayValue(vector<int> &nums) {
    long minMax = nums[0];
    long sum = minMax;
    for (int i = 1; i < nums.size(); i++) {
        sum += nums[i];
        long avg;
        if (sum % (i + 1) == 0) {
            avg = sum / (i + 1);
        } else {
            avg = sum / (i + 1) + 1;
        }
        minMax = max(minMax, avg);
    }
    return static_cast<int>(minMax);
}

static void dfs_minScore(int &minDis, vector<bool> &visited, int curr,
                         unordered_map<int, vector<pair<int, int>>> edgeMap) {
    visited[curr - 1] = true;
    for (auto neighbor : edgeMap[curr]) {
        const auto other = neighbor.first;
        const auto distance = neighbor.second;
        if (distance < minDis) {
            minDis = distance;
        }
        if (!visited[other - 1]) {
            dfs_minScore(minDis, visited, other, edgeMap);
        }
    }
}

int Solution::minScore(int n, vector<vector<int>> &roads) {
    // build map for edges
    unordered_map<int, vector<pair<int, int>>> edgeMap;
    for (auto road : roads) {
        const int node1 = road[0];
        const int node2 = road[1];
        const int distance = road[2];
        edgeMap[node1].emplace_back(node2, distance);
        edgeMap[node2].emplace_back(node1, distance);
    }
    int minDistance = INT_MAX;
    vector<bool> visited(n, false);
    dfs_minScore(minDistance, visited, 1, edgeMap);
    return minDistance;
}

int Solution::partitionString(string s) {
    bool appear[26] = {};
    int count = 0;
    for (int i = 0; i < s.length(); i++) {
        const char ch = s[i];
        if (appear[ch - 'a']) {
            count++;
            memset(appear, 0, sizeof(appear));
        }
        appear[ch - 'a'] = true;
    }
    return ++count;
}

int Solution::partitionString2(string s) {
    int count = 0, merge = 0;
    for (const auto ch : s) {
        if ((merge & (1 << ch - 'a')) != 0) {
            count++;
            merge = 0;
        }
        merge |= (1 << ch - 'a');
    }
    return ++count;
}

bool Solution::isPalindrome(int x) {
    if (x < 0) {
        return false;
    }
    if (x == 0) {
        return true;
    }
    string s = to_string(x);
    int i = 0, j = s.length() - 1;
    while (i <= j) {
        if (i == j) {
            return true;
        }
        if (s[i] != s[j]) {
            return false;
        }
        i++;
        j--;
    }
    return true;
}

bool Solution::isPalindrome2(int x) {
    if (x < 0) {
        return false;
    }
    if (x == 0) {
        return true;
    }
    long y = 0;
    int xx = x;
    while (xx > 0) {
        const int curr = xx % 10;
        y = y * 10 + curr;
        xx /= 10;
    }
    return y == x;
}

void pickOne(vector<vector<int>> &result, vector<int> &curr, vector<int> &nums,
             int num) {
    if (!contains(curr, num)) {
        curr.push_back(num);
    }
    if (curr.size() == nums.size()) {
        result.push_back(curr);
        return;
    }
    for (auto n : nums) {
        if (!contains(curr, n)) {
            pickOne(result, curr, nums, n);
        }
    }
}

vector<vector<int>> Solution::permute(vector<int> &nums) {
    vector<vector<int>> result;
    vector<int> curr;
    for (auto num : nums) {
        pickOne(result, curr, nums, num);
    }
    return result;
}

int Solution::reverse(int x) {
    int reversed = 0;
    int xx = x;
    constexpr int BOUNDARY_P = INT_MAX / 10;
    constexpr int BOUNDARY_N = INT_MIN / 10;
    while (xx != 0) {
        const int curr = xx % 10;
        if (reversed > BOUNDARY_P || reversed == BOUNDARY_P && curr > 7)
            return 0;
        if (reversed < BOUNDARY_N || reversed == BOUNDARY_N && curr < -8)
            return 0;
        reversed = reversed * 10 + curr;
        xx /= 10;
    }
    return reversed;
}

ListNode *Solution::reverseList(ListNode *head) {
    if (head == nullptr) {
        return head;
    }
    auto *newHead = new ListNode(head->val);
    ListNode *p = head->next;
    while (p != nullptr) {
        const int value = p->val;
        auto *node = new ListNode(value);
        node->next = newHead;
        newHead = node;
        p = p->next;
    }
    return newHead;
}

ListNode *Solution::reverseList2(ListNode *head) {
    ListNode *prev = nullptr;
    ListNode *curr = head;
    while (curr != nullptr) {
        ListNode *next = curr->next;
        curr->next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}

ListNode *Solution::reverseBetween(ListNode *head, int left, int right) {
    if (head == nullptr || left == right) {
        return head;
    }
    // TODO: Why it can be const?
    ListNode *leftNode = head;
    ListNode *leftPrev = nullptr;
    REPEAT(left - 1) {
        if (i == left - 2) {
            leftPrev = leftNode;
        }
        leftNode = leftNode->next;
    }
    ListNode *prev = leftNode;
    ListNode *curr = prev->next;
    REPEAT(right - left) {
        ListNode *next = curr->next;
        curr->next = prev;
        prev = curr;
        curr = next;
    }
    if (leftPrev != nullptr) {
        leftPrev->next = prev;
    }
    leftNode->next = curr;
    if (leftNode == head) {
        return prev;
    }
    return head;
}

int Solution::searchInsert(vector<int> &nums, int target) {
    int i = 0, j = nums.size() - 1;
    while (i < j) {
        const int mid = (i + j) / 2;
        const int midValue = nums[mid];
        if (midValue == target) {
            return mid;
        }
        if (midValue < target) {
            // right part of
            i = mid + 1;
        } else if (midValue > target) {
            j = mid - 1;
        }
    }
    if (nums[i] < target) {
        return i + 1;
    }
    // equal or larger
    return i;
}

string getElem(string path, int start) {
    if (start >= path.size()) return "";
    int nextSlashIndex = path.find('/', start);
    string elem = path.substr(start, nextSlashIndex - start);
    return elem;
}

string Solution::simplifyPath(string path) {
    if (path == "/") return path;
    int i = 1;
    string res;
    while (true) {
        if (path[i] == '/') {
            i++;
            continue;
        }
        string elem = getElem(path, i);
        if (elem.empty()) {
            break;
        }
        if (elem == ".") {
            i++;
            continue;
        }
        if (elem == "..") {
            int lastSlashIndex = res.find_last_of('/');
            res = res.substr(0, lastSlashIndex);
        } else {
            res += "/" + elem;
        }
        i += elem.size();
    }
    if (res.empty()) {
        res = "/";
    }
    return res;
}

string Solution::simplifyPath2(string path) {
    auto split = [](const string &s, char delim) -> vector<string> {
        vector<string> res;
        string cur;
        for (char ch : s) {
            if (ch == delim) {
                res.push_back(cur);
                cur.clear();
            } else {
                cur += ch;
            }
        }
        res.push_back(cur);
        return res;
    };
    vector<string> names = split(path, '/');
    vector<string> stack;
    for (string &name : names) {
        if (name == ".." && !stack.empty()) {
            stack.pop_back();
        } else if (!name.empty() && name != "." && name != "..") {
            stack.push_back(name);
        }
    }
    string res;
    if (stack.empty()) {
        res = "/";
    } else {
        for (string &name : stack) {
            res += "/" + name;
        }
    }
    return res;
}

int Solution::singleNonDuplicate(vector<int> &nums) {
    int low = 0, high = nums.size() - 1;
    while (low <= high) {
        int mid = (low + high) / 2;
        if (mid == 0 || mid == nums.size() - 1) {
            // overflow
            return nums[mid];
        }
        if (nums[mid] == nums[mid - 1]) {
            if (mid % 2 == 0) {
                // target is in low - mid
                high = mid;
            } else {
                // target is in (mid + 1) - high
                low = mid + 1;
            }
        } else if (nums[mid] == nums[mid + 1]) {
            if (mid % 2 == 0) {
                // target is in mid - high
                low = mid;
            } else {
                // target is in low - (mid - 1)
                high = mid - 1;
            }
        } else {
            return nums[mid];
        }
    }
    return nums[low];
}

static TreeNode *cutRecursively(const vector<int> &nums, int low, int high) {
    if (low == high) {
        return new TreeNode(nums[low]);
    }
    if (low > high) {
        return nullptr;
    }
    int mid = (low + high) / 2;
    auto *curr = new TreeNode(nums[mid]);
    curr->left = cutRecursively(nums, low, mid - 1);
    curr->right = cutRecursively(nums, mid + 1, high);
    return curr;
}

TreeNode *Solution::sortedListToBST(ListNode *head) {
    if (head == nullptr) {
        return nullptr;
    }
    const auto *curr = head;
    vector<int> nums;
    while (curr) {
        nums.push_back(curr->val);
        curr = curr->next;
    }
    return cutRecursively(nums, 0, nums.size() - 1);
}

static ListNode *getMedian(ListNode *low, ListNode *high) {
    ListNode *fast = low;
    ListNode *slow = low;
    while (fast != high && fast->next != high) {
        fast = fast->next->next;
        slow = slow->next;
    }
    return slow;
}

static TreeNode *cutRecursive2(ListNode *low, ListNode *high) {
    if (low == high) {
        return nullptr;
    }
    ListNode *mid = getMedian(low, high);
    TreeNode *root = new TreeNode(mid->val);
    root->left = cutRecursive2(low, mid);
    root->right = cutRecursive2(mid->next, high);
    return root;
}

TreeNode *Solution::sortedListToBST2(ListNode *head) {
    if (head == nullptr) {
        return nullptr;
    }
    return cutRecursive2(head, nullptr);
}

vector<int> Solution::successfulPairs(vector<int> &spells, vector<int> &potions,
                                      long long success) {
    vector<int> res;
    for (auto spell : spells) {
        int count = 0;
        for (auto potion : potions) {
            long long product = static_cast<long long>(spell) * potion;
            if (product >= success) {
                ++count;
            }
        }
        res.push_back(count);
    }
    return res;
}

vector<int> Solution::successfulPairs2(vector<int> &spells,
                                       vector<int> &potions,
                                       long long success) {
    vector<int> res;
    sort(potions.begin(), potions.end());
    for (auto spell : spells) {
        int count = 0;
        for (int i = 0; i < potions.size(); i++) {
            int potion = potions[i];
            long long product = static_cast<long long>(spell) * potion;
            if (product >= success) {
                count += potions.size() - i;
                break;
            }
        }
        res.push_back(count);
    }
    return res;
}

vector<int> Solution::successfulPairs3(vector<int> &spells,
                                       vector<int> &potions,
                                       long long success) {
    vector<int> res;
    sort(potions.begin(), potions.end());
    for (auto spell : spells) {
        int low = 0, high = potions.size() - 1;
        bool set = false;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (low == high && (mid == 0 || mid == potions.size() - 1)) {
                break;
            }
            long long thisProduct =
                static_cast<long long>(spell) * potions[mid];
            long long nextProduct =
                static_cast<long long>(spell) * potions[mid + 1];
            if (thisProduct < success) {
                if (nextProduct >= success) {
                    res.push_back(potions.size() - mid - 1);
                    set = true;
                    break;
                }
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        if (!set) {
            if (low == high) {
                long long product =
                    static_cast<long long>(spell) * potions[low];
                if (product >= success) {
                    res.push_back(potions.size() - low);
                } else {
                    res.push_back(potions.size() - low - 1);
                }
            } else {
                // low > high
                if (high < 0) {
                    high = 0;
                }
                if (low > potions.size() - 1) {
                    high = potions.size() - 1;
                }
                long long product =
                    static_cast<long long>(spell) * potions[high];
                if (product >= success) {
                    res.push_back(potions.size() - high);
                } else {
                    res.push_back(potions.size() - high - 1);
                }
            }
        }
    }
    return res;
}

vector<int> Solution::successfulPairs4(vector<int> &spells,
                                       vector<int> &potions,
                                       long long success) {
    vector<int> res;
    sort(potions.begin(), potions.end());
    for (auto spell : spells) {
        long long target = success / spell - (success % spell == 0 ? 1 : 0);
        auto it = upper_bound(potions.begin(), potions.end(), target);
        int count = potions.size() - (it - potions.begin());
        res.push_back(count);
    }
    return res;
}

vector<int> Solution::spiralOrder(vector<vector<int>> &matrix) {
    vector<int> res;
    int i = 0, j = 0, k = 0;
    int count = 0;
    const size_t m = matrix.size(), n = matrix[0].size();
    if (matrix.empty()) {
        return res;
    }
    const size_t total = matrix.size() * matrix[0].size();
    while (true) {
        i = k, j = k;
        while (j < n - k) {
            res.push_back(matrix[i][j]);
            ++count;
            ++j;
            if (count == total) {
                return res;
            }
        }
        --j;
        ++i;
        while (i < m - k) {
            res.push_back(matrix[i][j]);
            ++count;
            ++i;
            if (count == total) {
                return res;
            }
        }
        --i;
        --j;
        while (j >= k) {
            res.push_back(matrix[i][j]);
            ++count;
            --j;
            if (count == total) {
                return res;
            }
        }
        ++j;
        --i;
        while (i > k) {
            res.push_back(matrix[i][j]);
            ++count;
            --i;
            if (count == total) {
                return res;
            }
        }
        ++k;
    }
}

static int next(const vector<int> &nums, int curr) {
    if (curr >= nums.size() - 1) {
        return curr + 1;
    }
    int next = curr + 1;
    while (next < nums.size() && nums[next] == nums[curr]) {
        ++next;
    }
    return next;
}

vector<vector<int>> Solution::threeSum(vector<int> &nums) {
    // timeover
    vector<vector<int>> res;
    sort(nums.begin(), nums.end());
    for (int i = 0; i < nums.size() - 2; i++) {
        for (int j = i + 1; j < nums.size() - 1; j = next(nums, j)) {
            for (int k = j + 1; k < nums.size(); k = next(nums, k)) {
                const int sum = nums[i] + nums[j] + nums[k];
                if (sum > 0) {
                    break;
                }
                if (sum == 0) {
                    res.push_back({nums[i], nums[j], nums[k]});
                    break;
                }
            }
        }
    }
    return res;
}

vector<vector<int>> Solution::threeSum2(vector<int> &nums) {
    // error
    vector<vector<int>> res;
    sort(nums.begin(), nums.end());
    for (int i = 0; i < nums.size() - 2; i = next(nums, i)) {
        int j = i + 1, k = nums.size() - 1;
        while (j < k) {
            const int sum = nums[i] + nums[j] + nums[k];
            if (sum == 0) {
                res.push_back({nums[i], nums[j], nums[k]});
                j++;
                k--;
            } else if (sum > 0) {
                k--;
            } else {
                j++;
            }
        }
    }
    return res;
}

vector<vector<int>> Solution::threeSum3(vector<int> &nums) {
    sort(nums.begin(), nums.end());
    vector<vector<int>> res;
    for (int i = 0; i < nums.size(); i++) {
        if (i > 0 && nums[i] == nums[i - 1]) {
            continue;
        }
        int j = i + 1, k = nums.size() - 1;
        const int target = -nums[i];
        while (j < k) {
            const int sum = nums[j] + nums[k];
            if (sum == target) {
                res.push_back({nums[i], nums[j], nums[k]});
                j++;
                k--;
                while (j < k && nums[j] == nums[j - 1]) j++;
                while (j < k && nums[k] == nums[k + 1]) k--;
            } else if (sum < target) {
                j++;
            } else {
                k--;
            }
        }
    }
    return res;
}

vector<vector<int>> Solution::threeSum4(vector<int> &nums) {
    sort(nums.begin(), nums.end());
    vector<vector<int>> res;
    int i = 0;
    while (i < nums.size() - 2) {
        int j = i + 1, k = nums.size() - 1;
        while (j < k) {
            int sum = nums[i] + nums[j] + nums[k];
            if (sum == 0) {
                res.push_back({nums[i], nums[j], nums[k]});
                while (j < k && nums[j] == nums[j + 1]) {
                    j++;
                }
                while (j < k && nums[k] == nums[k - 1]) {
                    k--;
                }
                j++;
                k--;
            } else if (sum > 0) {
                k--;
            } else {
                j++;
            }
        }
        while (i < nums.size() - 2 && nums[i] == nums[i + 1]) {
            i++;
        }
        i++;
    }
    return res;
}

vector<int> Solution::twoSum(vector<int> &nums, int target) {
    vector<vector<int>> nums2 = {};
    for (int i = 0; i < nums.size(); i++) {
        nums2.push_back({nums[i], i});
    }
    sort(
        nums2.begin(), nums2.end(),
        [](const vector<int> &a, const vector<int> &b) { return a[0] < b[0]; });
    int i = 0, j = nums.size() - 1;
    while (i < j) {
        const int sum = nums2[i][0] + nums2[j][0];
        if (sum == target) {
            return {nums2[i][1], nums2[j][1]};
        }
        if (sum > target)
            j--;
        else
            i++;
    }
    return {};
}

vector<int> Solution::twoSum2(vector<int> &nums, int target) {
    unordered_map<int, int> numToIndex;
    for (int i = 0; i < nums.size(); i++) {
        auto it = numToIndex.find(target - nums[i]);
        if (it != numToIndex.end()) {
            return {it->second, i};
        }
        numToIndex[nums[i]] = i;
    }
    return {};
}

int Solution::threeSumCloset(vector<int> &nums, int target) {
    sort(nums.begin(), nums.end());
    int diff = INT_MAX;
    int res = INT_MAX;
    for (int i = 0; i < nums.size(); i++) {
        if (i > 0 && nums[i] == nums[i - 1]) {
            continue;
        }
        int j = i + 1, k = nums.size() - 1;
        while (j < k) {
            const int sum = nums[i] + nums[j] + nums[k];
            if (sum == target) {
                return sum;
            } else if (sum < target) {
                if (abs(sum - target) < diff) {
                    diff = abs(sum - target);
                    res = sum;
                }
                j++;
            } else {
                if (abs(sum - target) < diff) {
                    diff = abs(sum - target);
                    res = sum;
                }
                k--;
            }
        }
    }
    return res;
}

static int addThis(int sum, TreeNode *curr) {
    if (curr == nullptr) {
        // In case that root is null
        return sum;
    }
    sum = sum * 10 + curr->val;
    if (curr->left == nullptr && curr->right == nullptr) {
        return sum;
    }
    int res = 0;
    if (curr->left != nullptr) {
        res += addThis(sum, curr->left);
    }
    if (curr->right != nullptr) {
        res += addThis(sum, curr->right);
    }
    return res;
}

int Solution::sumNumbers(TreeNode *root) { return addThis(0, root); }

static bool traverse(TreeNode *curr1, TreeNode *curr2) {
    if (curr1 == nullptr && curr2 == nullptr) {
        return true;
    }
    if (curr1 != nullptr && curr2 == nullptr) {
        return false;
    }
    if (curr1 == nullptr) {
        return false;
    }
    if (curr1->val != curr2->val) {
        return false;
    }
    return traverse(curr1->left, curr2->right) &&
           traverse(curr1->right, curr2->left);
}

bool Solution::isSymmetric(TreeNode *root) {
    if (root == nullptr) {
        return true;
    }
    if (root->left == nullptr && root->right == nullptr) {
        return true;
    }
    if (root->left != nullptr && root->right == nullptr) {
        return false;
    }
    if (root->left == nullptr) {
        return false;
    }
    return traverse(root->left, root->right);
}

Solution::Trie::Trie() = default;

void Solution::Trie::insert(string word) {
    if (word.empty()) {
        return;
    }
    // find root
    TrieNode *curr = this->roots[word[0] - 'a'];
    if (curr == nullptr) {
        curr = new TrieNode();
        this->roots[word[0] - 'a'] = curr;
    }
    // now curr == root
    for (size_t i = 1; i < word.size(); i++) {
        char ch = word[i];
        auto &children = curr->children;
        if (children.find(ch) == children.end()) {
            children[ch] = new TrieNode();
        }
        curr = children[ch];
    }
    curr->is_last = true;
}
bool Solution::Trie::search(string word) {
    if (word.empty()) {
        return false;
    }
    TrieNode *curr = this->roots[word[0] - 'a'];
    if (curr == nullptr) {
        return false;
    }
    for (size_t i = 1; i < word.size(); i++) {
        char ch = word[i];
        auto children = curr->children;
        if (children.find(ch) == children.end()) {
            return false;
        }
        curr = children[ch];
    }
    return curr->is_last;
}

bool Solution::Trie::startsWith(string prefix) {
    if (prefix.empty()) {
        return false;
    }
    TrieNode *curr = this->roots[prefix[0] - 'a'];
    if (curr == nullptr) {
        return false;
    }
    for (size_t i = 1; i < prefix.size(); i++) {
        char ch = prefix[i];
        auto children = curr->children;
        if (children.find(ch) == children.end()) {
            return false;
        }
        curr = children[ch];
    }
    return true;
}

long long Solution::countPairs(int n, vector<vector<int>> &edges) {
    const CommonUtil::DFSResponse response = CommonUtil::dfs(n, edges);
    const vector<long long> islandSizes = response.islandSizes;
    return CommonUtil::quickPairSum(islandSizes);
}

bool Solution::isValid(string s) {
    stack<char> stack;
    for (char ch : s) {
        if (ch == '(' || ch == '[' || ch == '{') {
            stack.push(ch);
            continue;
        }
        if (stack.empty()) {
            return false;
        }
        char top = stack.top();
        switch (ch) {
            case ')': {
                if (top == '(') {
                    stack.pop();
                    continue;
                } else {
                    return false;
                }
            }
            case ']': {
                if (top == '[') {
                    stack.pop();
                    continue;
                } else {
                    return false;
                }
            }
            case '}': {
                if (top == '{') {
                    stack.pop();
                    continue;
                } else {
                    return false;
                }
            }
        }
    }
    return stack.empty();
}

Solution::WordDictionary::WordDictionary() { this->root = new TrieNode(); }

void Solution::WordDictionary::addWord(string word) {
    TrieNode *curr = this->root;
    for (char ch : word) {
        auto &children = curr->children;
        if (children.find(ch) == children.end()) {
            children[ch] = new TrieNode();
        }
        curr = children[ch];
    }
    curr->is_last = true;
}

bool Solution::WordDictionary::search(string word) {
    return searchFrom(word, 0, root);
}

bool Solution::WordDictionary::searchFrom(string word, int index,
                                          TrieNode *node) {
    if (index == word.size()) {
        return node->is_last;
    }
    char ch = word[index];
    auto &children = node->children;
    if (ch != '.') {
        if (children.find(ch) != children.end() &&
            searchFrom(word, index + 1, children[ch])) {
            return true;
        }
    } else {
        for (char c = 'a'; c <= 'z'; c++) {
            if (children.find(c) != children.end() &&
                searchFrom(word, index + 1, children[c])) {
                return true;
            }
        }
    }
    return false;
}

string Solution::convert(string s, int numRows) {
    if (numRows == 1) {
        return s;
    }
    vector<queue<char>> queues(numRows);
    for (int i = 0; i < queues.size(); i++) {
        queues[i] = queue<char>();
    }
    int currQueueIndex = 0;
    bool forward = true;
    for (char ch : s) {
        queues[currQueueIndex].push(ch);
        if (forward) {
            if (currQueueIndex == numRows - 1) {
                forward = false;
                currQueueIndex--;
            } else {
                currQueueIndex++;
            }
        } else {
            if (currQueueIndex == 0) {
                forward = true;
                currQueueIndex++;
            } else {
                currQueueIndex--;
            }
        }
    }
    string res = "";
    for (queue<char> queue : queues) {
        while (!queue.empty()) {
            char ch = queue.front();
            res += ch;
            queue.pop();
        }
    }
    return res;
}

Solution::LRUCache::LRUCache(int capacity) { this->max_size = capacity; }

int Solution::LRUCache::get(int key) {
    if (!exist(key)) {
        return -1;
    }
    auto node_it = this->origin_data[key];
    ordered_data.splice(ordered_data.end(), ordered_data, node_it);
    return node_it->value;
}

void Solution::LRUCache::put(int key, int value) {
    if (!exist(key)) {
        Node node{};
        node.value = value;
        node.key = key;
        const int size = this->origin_data.size();
        if (size >= max_size) {
            // remove first elem
            int first_key = ordered_data.front().key;
            ordered_data.pop_front();
            origin_data.erase(first_key);
        }
        ordered_data.emplace_back(node);
        origin_data[key] = prev(ordered_data.end());
    } else {
        auto node_it = origin_data[key];
        node_it->key = key;
        node_it->value = value;
        ordered_data.splice(ordered_data.end(), ordered_data, node_it);
    }
}

void Solution::LRUCache2::move_to_tail(Node *node) {
    if (node == nullptr) {
        return;
    }
    if (origin_data.find(node->key) == origin_data.end()) {
        return;
    }
    if (tail == node) {
        return;
    }
    if (origin_data.size() <= 1) {
        return;
    }
    if (head == node) {
        head = head->next;
        head->prev = nullptr;
        node->prev = tail;
        node->next = nullptr;
        tail->next = node;
        tail = node;
        return;
    }
    node->prev->next = node->next;
    node->next->prev = node->prev;
    node->next = nullptr;
    node->prev = tail;
    tail->next = node;
    tail = node;
}
void Solution::LRUCache2::erase_head() {
    if (origin_data.empty() || head == nullptr || tail == nullptr) {
        return;
    }
    int key = head->key;
    if (head == tail) {
        delete head;
        head = nullptr;
        tail = nullptr;
    } else {
        Node *new_head = head->next;
        delete head;
        head = new_head;
        head->prev = nullptr;
    }
    origin_data.erase(key);
}

void Solution::LRUCache2::insert_to_tail(Node *node) {
    if (node == nullptr) {
        return;
    }
    if (origin_data.find(node->key) != origin_data.end()) {
        move_to_tail(node);
        return;
    }
    if (origin_data.empty()) {
        head = node;
        tail = node;
    } else {
        tail->next = node;
        node->prev = tail;
        tail = node;
    }
    origin_data[node->key] = node;
}

Solution::LRUCache2::LRUCache2(int capacity) {
    this->max_size = capacity;
    this->head = nullptr;
    this->tail = nullptr;
}

int Solution::LRUCache2::get(int key) {
    if (origin_data.empty() || origin_data.find(key) == origin_data.end()) {
        return -1;
    }
    auto *node = origin_data.at(key);
    move_to_tail(node);
    return node->value;
}

void Solution::LRUCache2::put(int key, int value) {
    if (origin_data.find(key) == origin_data.end()) {
        Node *node = new Node(key, value);
        node->prev = tail;
        node->next = nullptr;
        if (origin_data.size() >= max_size) {
            erase_head();
        }
        insert_to_tail(node);
    } else {
        Node *node = origin_data.at(key);
        node->value = value;
        move_to_tail(node);
    }
}

void insertToTail(ListNode **head, ListNode **tail, ListNode *node) {
    if (*head == nullptr || *tail == nullptr) {
        *head = node;
        *tail = node;
        return;
    }
    (*tail)->next = node;
    (*tail) = node;
}

ListNode *Solution::mergeTwoLists(ListNode *list1, ListNode *list2) {
    ListNode *p1 = list1;
    ListNode *p2 = list2;
    ListNode *head = nullptr;
    ListNode *tail = nullptr;
    while (true) {
        ListNode *inserted;
        if (p1 == nullptr && p2 != nullptr) {
            inserted = p2;
        } else if (p1 != nullptr && p2 == nullptr) {
            inserted = p1;
        } else if (p1 == nullptr) {
            // both null
            break;
        } else {
            inserted = p1->val <= p2->val ? p1 : p2;
        }
        insertToTail(&head, &tail, inserted);
        if (p1 != nullptr && p1 == inserted) {
            p1 = p1->next;
        } else if (p2 != nullptr && p2 == inserted) {
            p2 = p2->next;
        }
    }
    return head;
}

void extractNode(ListNode *prev, ListNode *node) {
    prev->next = node->next;
    node->next = nullptr;
}

ListNode *insertOneNode(ListNode *dummyHead, ListNode *lastNode,
                        ListNode *node) {
    ListNode *p = dummyHead->next;
    ListNode *prev = dummyHead;
    // try insert before p
    while (p != nullptr && p != lastNode) {
        if (p->val >= node->val) {
            break;
        }
        p = p->next;
        prev = prev->next;
    }
    if (p->val >= node->val) {
        // before
        node->next = p;
        prev->next = node;
    } else {
        // after
        node->next = p->next;
        p->next = node;
    }
    if (lastNode->val >= node->val) {
        return lastNode;
    }
    return node;
}

ListNode *Solution::insertionSortList(ListNode *head) {
    if (head == nullptr || head->next == nullptr) {
        return head;
    }
    ListNode dummyHead(-1);
    dummyHead.next = head;
    ListNode *end = head;
    ListNode *node = end->next;
    while (node != nullptr) {
        extractNode(end, node);
        end = insertOneNode(&dummyHead, end, node);
        node = end->next;
    }
    return dummyHead.next;
}

ListNode *findMiddle(ListNode *head) {
    if (head == nullptr || head->next == nullptr) {
        return head;
    }
    ListNode *slow = head;
    ListNode *fast = head;
    while (fast != nullptr && fast->next != nullptr) {
        slow = slow->next;
        fast = fast->next->next;
    }
    return slow;
}

/**
 * assert head has at least two nodes
 */
pair<ListNode *, ListNode *> split(ListNode *head) {
    ListNode *mid = findMiddle(head);
    if (mid->next == nullptr) {
        // 2 nodes
        head->next = nullptr;
        return {head, mid};
    }
    ListNode *head2 = mid->next;
    mid->next = nullptr;
    return {head, head2};  // head2 must not be null
}

ListNode *mergeSort(ListNode *head) {
    if (head == nullptr || head->next == nullptr) {
        return head;
    }
    // at least has two nodes
    // split to two sub lists
    auto [head1, head2] = split(head);
    ListNode *list1 = mergeSort(head1);
    ListNode *list2 = mergeSort(head2);
    return Solution::mergeTwoLists(list1, list2);
}

ListNode *Solution::sortList(ListNode *head) { return mergeSort(head); }

pair<ListNode *, ListNode *> mergeLists(ListNode *list1, ListNode *list2) {
    ListNode *p1 = list1;
    ListNode *p2 = list2;
    ListNode *head = nullptr;
    ListNode *tail = nullptr;
    while (true) {
        ListNode *inserted;
        if (p1 == nullptr && p2 != nullptr) {
            inserted = p2;
        } else if (p1 != nullptr && p2 == nullptr) {
            inserted = p1;
        } else if (p1 == nullptr) {
            // both null
            break;
        } else {
            inserted = p1->val <= p2->val ? p1 : p2;
        }
        insertToTail(&head, &tail, inserted);
        if (p1 != nullptr && p1 == inserted) {
            p1 = p1->next;
        } else if (p2 != nullptr && p2 == inserted) {
            p2 = p2->next;
        }
    }
    return {head, tail};
}

ListNode *Solution::sortList2(ListNode *head) {
    if (head == nullptr || head->next == nullptr) {
        return head;
    }
    int len = 0;
    for (ListNode *p = head; p != nullptr; p = p->next) {
        len++;
    }
    ListNode dummyHead(-1, head);
    for (int step = 1; step < len; step *= 2) {
        // split the whole list into ordered sub lists with length of [step]
        ListNode *curr = dummyHead.next;
        ListNode *prev = &dummyHead;
        while (curr != nullptr) {
            ListNode *left = curr;
            int leftSize = 0;
            // move node for step - 1 times,
            // thus has [step] nodes
            while (curr != nullptr && leftSize < step - 1) {
                leftSize++;
                curr = curr->next;
            }
            if (curr == nullptr) {
                prev->next = left;
                break;
            }
            ListNode *right = curr->next;
            curr->next = nullptr;
            curr = right;
            int rightSize = 0;
            while (curr != nullptr && rightSize < step - 1) {
                rightSize++;
                curr = curr->next;
            }
            ListNode *next;
            if (curr == nullptr) {
                next = nullptr;
            } else {
                next = curr->next;
                curr->next = nullptr;
            }
            auto [sub_h, sub_t] = mergeLists(left, right);
            prev->next = sub_h;
            prev = sub_t;
            curr = next;
        }
    }
    return dummyHead.next;
}

int Solution::findKthLargest(vector<int> &nums, int k) {
    MaxHeap heap(nums);
    for (int i = 1; i < k; i++) {
        heap.pop();
    }
    return heap.top();
}

int partitionForQuickSelect(vector<int> &nums, int low, int high) {
    int pivot_index = low + rand() % (high - low + 1);
    swap(nums[low], nums[pivot_index]);  // 随机化
    int pivot = nums[low];
    while (low < high) {
        while (low < high && nums[high] >= pivot) high--;
        if (low < high) {
            CommonUtil::vector_swap(nums, low, high);
        }
        while (low < high && nums[low] <= pivot) low++;
        if (low < high) {
            CommonUtil::vector_swap(nums, low, high);
        }
    }
    return low;
}

void quickSelect(vector<int> &nums, int k, int low, int high) {
    if (low >= high) {
        return;
    }
    int target_index = nums.size() - k;
    int pivot_index = partitionForQuickSelect(nums, low, high);
    if (pivot_index == target_index) {
        return;
    } else if (pivot_index < target_index) {
        quickSelect(nums, k, pivot_index + 1, high);
    } else {
        quickSelect(nums, k, low, pivot_index - 1);
    }
}

int Solution::findKthLargest2(vector<int> &nums, int k) {
    quickSelect(nums, k, 0, nums.size() - 1);
    return nums[nums.size() - k];
}

int Solution::maxSubArray(vector<int> &nums) {
    if (nums.size() == 1) {
        return nums.at(0);
    }
    int pre = nums[0];
    int max = pre;
    for (int i = 1; i < nums.size(); i++) {
        if (pre < 0) {
            // new start
            pre = nums[i];
        } else {
            // pre = std::max(pre, pre + nums[i]);
            pre += nums[i];
        }
        if (pre > max) {
            max = pre;
        }
    }
    return max;
}

int partition(vector<int> &nums, int left, int right) {
    int pivot = nums[left];
    while (left < right) {
        while (left < right && nums[right] >= pivot) {
            right--;
        }
        if (left < right) {
            CommonUtil::vector_swap(nums, left, right);
        }
        while (left < right && nums[left] <= pivot) {
            left++;
        }
        if (left < right) {
            CommonUtil::vector_swap(nums, left, right);
        }
    }
    return left;
}

void quickSort(vector<int> &nums, int left, int right) {
    if (left >= right) {
        // 1 or 0 elem, return
        return;
    }
    int pivot_index = partition(nums, left, right);
    quickSort(nums, left, pivot_index - 1);
    quickSort(nums, pivot_index + 1, right);
}

vector<int> Solution::sortArray(vector<int> &nums) {
    quickSort(nums, 0, nums.size() - 1);
    return nums;
}

inline bool isSubarraySorted(vector<int> &nums, int first_index,
                             int last_index) {
    return nums[last_index] >= nums[first_index];
}

int Solution::search(vector<int> &nums, int target) {
    int left = 0, right = nums.size() - 1;
    while (left < right) {
        int mid = (left + right) / 2;
        if (target == nums[mid]) {
            return mid;
        }
        if (isSubarraySorted(nums, left, mid)) {
            // left is sorted
            if (target == nums[left]) {
                return left;
            }
            if (target > nums[left] && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } else {
            // right is sorted
            if (target == nums[right]) {
                return right;
            }
            if (target == nums[mid + 1]) {
                return mid + 1;
            }
            if (target > nums[mid + 1] && target < nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    if (left == right) {
        return nums[left] == target ? left : -1;
    }
    return -1;
}

bool isLandIndexConnected(int thisX, int thisY, int otherX, int otherY) {
    if (thisX == otherX) {
        return abs(thisY - otherY) == 1;
    } else if (thisY == otherY) {
        return abs(thisX - otherX) == 1;
    }
    return false;
}

/**
 * return if grid[thisX][thisY] and grid[otherX][otherY] is connected
 */
bool isLandConnected(const vector<vector<char>> &grid, int thisX, int thisY,
                     int otherX, int otherY) {
    if (grid.empty()) {
        return false;
    }
    const int width = grid[0].size();
    const int height = grid.size();
    if (thisX < 0 || thisY < 0 || otherX < 0 || otherY < 0 || thisX >= height ||
        thisY >= width || otherX >= height || otherY >= width) {
        // overflow case
        return false;
    }
    const int thisChar = grid[thisX][thisY];
    const int otherChar = grid[otherX][otherY];
    return thisChar == '1' && thisChar == otherChar &&
           isLandIndexConnected(thisX, thisY, otherX, otherY);
}

vector<vector<int>> findConnectedLands(const vector<vector<char>> &grid,
                                       int startX, int startY) {
    vector<vector<int>> res;
    int upX = startX - 1;
    int upY = startY;
    int downX = startX + 1;
    int downY = startY;
    int leftX = startX;
    int leftY = startY - 1;
    int rightX = startX;
    int rightY = startY + 1;
    if (isLandConnected(grid, startX, startY, upX, upY)) {
        res.push_back({upX, upY});
    }
    if (isLandConnected(grid, startX, startY, downX, downY)) {
        res.push_back({downX, downY});
    }
    if (isLandConnected(grid, startX, startY, leftX, leftY)) {
        res.push_back({leftX, leftY});
    }
    if (isLandConnected(grid, startX, startY, rightX, rightY)) {
        res.push_back({rightX, rightY});
    }
    return res;
}

void dfsInIslands(const vector<vector<char>> &grid,
                  vector<vector<bool>> &visited, int startX, int startY) {
    if (visited[startX][startY] || grid[startX][startY] != '1') {
        return;
    }
    visited[startX][startY] = true;
    vector<vector<int>> connectedLands =
        findConnectedLands(grid, startX, startY);
    for (auto land : connectedLands) {
        dfsInIslands(grid, visited, land[0], land[1]);
    }
}

int Solution::numIslands(vector<vector<char>> &grid) {
    if (grid.empty()) {
        return 0;
    }
    const int width = grid[0].size();
    const int height = grid.size();
    int num = 0;
    vector<vector<bool>> visited(grid.size(),
                                 vector<bool>(grid[0].size(), false));
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if (grid[i][j] == '1' && !visited[i][j]) {
                dfsInIslands(grid, visited, i, j);
                num++;
            }
        }
    }
    return num;
}

ListNode *Solution::swapPairs(ListNode *head) {
    if (head == nullptr) {
        return nullptr;
    }
    ListNode *new_head = head->next == nullptr ? head : head->next;
    ListNode *p1 = head;
    ListNode *p1_prev = nullptr;
    ListNode *p2 = head->next;
    ListNode *p2_next = p2 == nullptr ? nullptr : p2->next;
    while (true) {
        if (p1 != nullptr && p2 != nullptr) {
            if (p1_prev != nullptr) {
                p1_prev->next = p2;
            }
            p2->next = p1;
            p1->next = p2_next;
            p1_prev = p1;
            p1 = p2_next;
            p2 = p1 == nullptr ? nullptr : p1->next;
            p2_next = p2 == nullptr ? nullptr : p2->next;
        } else {
            // p2 == nullptr
            break;
        }
    }
    return new_head;
}

ListNode *Solution::swapNodes(ListNode *head, int k) {
    ListNode *p1 = head;
    ListNode *p1_prev = nullptr;
    ListNode *p1_next = nullptr;
    REPEAT(k - 1) {
        p1_prev = p1;
        p1 = p1->next;
    }
    p1_next = p1->next;
    ListNode *tmp = p1;
    ListNode *p2 = head;
    ListNode *p2_prev = nullptr;
    ListNode *p2_next = nullptr;
    while (tmp->next != nullptr) {
        tmp = tmp->next;
        p2_prev = p2;
        p2 = p2->next;
    }
    p2_next = p2->next;
    ListNode *newhead = head;
    if (p1 == p2) {
        return newhead;
    }
    if (p1->next == p2) {
        if (p1_prev != nullptr) {
            p1_prev->next = p2;
        } else {
            newhead = p2;
        }
        p2->next = p1;
        p1->next = p2_next;
        return newhead;
    }
    if (p2->next == p1) {
        if (p2_prev != nullptr) {
            p2_prev->next = p1;
        } else {
            newhead = p1;
        }
        p1->next = p2;
        p2->next = p1_next;
        return newhead;
    }
    if (p1_prev != nullptr) {
        p1_prev->next = p2;
    } else {
        // swap first and last
        newhead = p2;
    }
    p2->next = p1_next;
    if (p2_prev != nullptr) {
        p2_prev->next = p1;
    } else {
        newhead = p1;
    }
    p1->next = p2_next;
    return newhead;
}

void reverseSubList(ListNode *start_prev, ListNode *start, ListNode *end) {
    if ((start_prev != nullptr && start_prev->next != start) ||
        start == nullptr || end == nullptr) {
        return;
    }
    ListNode *end_next = end->next;
    // extract the sub list
    if (start_prev != nullptr) {
        start_prev->next = nullptr;
    }
    end->next = nullptr;

    // reverse sublist
    ListNode *p_prev = nullptr;
    ListNode *p = start;
    ListNode *p_next = start->next;
    while (p != nullptr) {
        p->next = p_prev;
        p_prev = p;
        p = p_next;
        if (p_next != nullptr) {
            p_next = p_next->next;
        }
    }

    // concat
    if (start_prev != nullptr) {
        start_prev->next = end;
    }
    start->next = end_next;
}

ListNode *Solution::reverseEvenLengthGroups(ListNode *head) {
    int group_count = 1;
    ListNode *p = head;
    ListNode *group_start_prev = nullptr;
    while (p != nullptr) {
        ListNode *group_start = p;
        ListNode *group_last = p;
        int group_real_count = 0;
        for (int i = 0; i < group_count; i++) {
            if (p != nullptr) {
                group_last = p;
                p = p->next;
                group_real_count++;
            } else {
                break;
            }
        }
        if (group_real_count % 2 == 0) {
            reverseSubList(group_start_prev, group_start, group_last);
            group_start_prev = group_start;
        } else {
            group_start_prev = group_last;
        }
        group_count++;
    }
    return head;
}

ListNode *Solution::reverseKGroup(ListNode *head, int k) {
    ListNode *group_start_prev = nullptr;
    ListNode *group_start = head;
    ListNode *newhead = head;
    bool first_reverse = true;
    while (true) {
        if (group_start == nullptr) {
            return newhead;
        }
        ListNode *group_end = group_start;
        REPEAT(k - 1) {
            if (group_end == nullptr) {
                return newhead;
            }
            group_end = group_end->next;
        }
        if (group_end == nullptr) {
            return newhead;
        }
        // now we have ensured that group_end point to a non-null node
        reverseSubList(group_start_prev, group_start, group_end);
        if (first_reverse) {
            first_reverse = false;
            newhead = group_end;
        }
        group_start_prev = group_start;
        group_start = group_start->next;
    }
}
