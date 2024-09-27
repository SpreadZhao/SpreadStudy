//
// Created by SpreadZhao on 2024/7/21.
//
#include "Solution.h"

#include <cstring>
#include <set>

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

vector<vector<string>> Solution::accountsMerge(vector<vector<string>>& accounts) {
    map<string, int> emailToIndex;
    map<string, string> emailToName;
    int emailsCount = 0;
    for (auto& account : accounts) {
        string& name = account[0];
        int size = account.size();
        for (int i = 1; i < size; i++) {
            string& email = account[i];
            if (!emailToIndex.count(email)) {
                emailToIndex[email] = emailsCount++;
                emailToName[email] = name;
            }
        }
    }
    UnionFind uf(emailsCount);
    for (auto& account : accounts) {
        string& firstEmail = account[1];
        int firstIndex = emailToIndex[firstEmail];
        int size = account.size();
        for (int i = 2; i < size; i++) {
            string& nextEmail = account[i];
            int nextIndex = emailToIndex[nextEmail];
            uf.unionSet(firstIndex, nextIndex);
        }
    }
    map<int, vector<string>> indexToEmails;
    for (auto& [email, _] : emailToIndex) {
        int index = uf.find(emailToIndex[email]);
        vector<string>& account = indexToEmails[index];
        account.emplace_back(email);
        indexToEmails[index] = account;
    }
    vector<vector<string>> merged;
    for (auto& [_, emails] : indexToEmails) {
        sort(emails.begin(), emails.end());
        string& name = emailToName[emails[0]];
        vector<string> account;
        account.emplace_back(name);
        for (auto& email : emails) {
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

int Solution::makeConnected(int n, vector<vector<int>>& connections) {
    if (connections.size() < n - 1) {
        return -1;
    }
    CommonUtil::DFSResponse response = CommonUtil::dfs(n, connections);
    return response.islandNum - 1;
}

int Solution::maxArea(vector<int>& height) {
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

vector<vector<int>> Solution::findDifference(vector<int>& nums1, vector<int>& nums2) {
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
    return vector<vector<int>> {list1, list2};
}

vector<vector<int>> Solution::findDifference2(vector<int>& nums1, vector<int>& nums2) {
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
                dp[i + 1][j + 1] += static_cast<int>(static_cast<long>(columnToCount[target[i] - 'a'][j]) * dp[i][j] % MOD);
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
            if(nums[i] > nums[j]) {
                dp[i] = std::max(dp[i], dp[j] + 1);
            }
        }
        res = std::max(res, dp[i]);
    }
    return dp[size - 1];
}

static const vector<string> digitsToLetters = {
    "",         // 0
    "",         // 1
    "abc",
    "def",
    "ghi",
    "jkl",
    "mno",
    "pqrs",
    "tuv",
    "wxyz"      // 9
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
    queue<TreeNode*> nodes;
    nodes.push(root);
    while (!nodes.empty()) {
        int currLevelSize = nodes.size();
        res.emplace_back();
        for (int i = 0; i < currLevelSize; i++) {
            auto node = nodes.front();
            nodes.pop();
            res.back().emplace_back(node->val);
            if (node->left) { nodes.push(node->left); }
            if (node->right) { nodes.push(node->right); }
        }
    }
    return res;
}

ListNode * Solution::getIntersectionNode(ListNode *headA, ListNode *headB) {
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

ListNode * Solution::getIntersectionNode2(ListNode *headA, ListNode *headB) {
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
    map<char, bool>showed;
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
    map<char, int>showAt;
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

TreeNode * Solution::lowestCommonAncestor(TreeNode *root, TreeNode *p, TreeNode *q) {
    TreeNode *curr = root;
    int target = p->val;
    set<TreeNode *>route;
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

TreeNode * Solution::lowestCommonAncestor2(TreeNode *root, TreeNode *p, TreeNode *q) {
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
    queue<TreeNode*> q;
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



