//
// Created by SpreadZhao on 2024/7/21.
//
#include "Solution.h"

#include <cstring>
#include <limits.h>
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
    for (int re: res) {
        ss << re;
    }
    return ss.str();
}

vector<vector<string> > Solution::accountsMerge(vector<vector<string> > &accounts) {
    map<string, int> emailToIndex;
    map<string, string> emailToName;
    int emailsCount = 0;
    for (auto &account: accounts) {
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
    for (auto &account: accounts) {
        string &firstEmail = account[1];
        int firstIndex = emailToIndex[firstEmail];
        int size = account.size();
        for (int i = 2; i < size; i++) {
            string &nextEmail = account[i];
            int nextIndex = emailToIndex[nextEmail];
            uf.unionSet(firstIndex, nextIndex);
        }
    }
    map<int, vector<string> > indexToEmails;
    for (auto &[email, _]: emailToIndex) {
        int index = uf.find(emailToIndex[email]);
        vector<string> &account = indexToEmails[index];
        account.emplace_back(email);
        indexToEmails[index] = account;
    }
    vector<vector<string> > merged;
    for (auto &[_, emails]: indexToEmails) {
        sort(emails.begin(), emails.end());
        string &name = emailToName[emails[0]];
        vector<string> account;
        account.emplace_back(name);
        for (auto &email: emails) {
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

int Solution::makeConnected(int n, vector<vector<int> > &connections) {
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

vector<vector<int> > Solution::findDifference(vector<int> &nums1, vector<int> &nums2) {
    vector<int> list1;
    vector<int> list2;
    for (auto num: nums1) {
        if (!contains(nums2, num) && !contains(list1, num)) {
            list1.emplace_back(num);
        }
    }
    for (auto num: nums2) {
        if (!contains(nums1, num) && !contains(list2, num)) {
            list2.emplace_back(num);
        }
    }
    return vector<vector<int> >{list1, list2};
}

vector<vector<int> > Solution::findDifference2(vector<int> &nums1, vector<int> &nums2) {
    unordered_set<int> set1, set2;
    for (int num: nums1) {
        set1.insert(num);
    }
    for (int num: nums2) {
        set2.insert(num);
    }
    vector<vector<int> > res(2);
    for (int num: set1) {
        if (!set2.count(num)) {
            res[0].push_back(num);
        }
    }
    for (int num: set2) {
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
        for (auto str: words) {
            columnToCount[str[j] - 'a'][j]++;
        }
    }
    // CommonUtil::printMetric(&columnToCount[0][0], ALPHABET, k);
    const size_t m = target.size();
    vector<vector<int> > dp(m + 1, vector<int>(k + 1, 0));
    dp[0][0] = 1;
    for (int i = 0; i <= m; i++) {
        for (int j = 0; j < k; j++) {
            if (i < m) {
                dp[i + 1][j + 1] += static_cast<int>(
                    static_cast<long>(columnToCount[target[i] - 'a'][j]) * dp[i][j] % MOD);
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
        for (const auto candie: candies) {
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
    "", // 0
    "", // 1
    "abc",
    "def",
    "ghi",
    "jkl",
    "mno",
    "pqrs",
    "tuv",
    "wxyz" // 9
};

void putCh(vector<string> &res, string digits, string letters, int digitIndex) {
    if (digitIndex >= digits.size()) {
        res.emplace_back(letters);
        return;
    }
    for (auto ch: digitsToLetters[digits[digitIndex] - '0']) {
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

vector<vector<int> > Solution::levelOrder(TreeNode *root) {
    vector<vector<int> > res;
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
            if (node->left) { nodes.push(node->left); }
            if (node->right) { nodes.push(node->right); }
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

TreeNode *Solution::lowestCommonAncestor(TreeNode *root, TreeNode *p, TreeNode *q) {
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

TreeNode *Solution::lowestCommonAncestor2(TreeNode *root, TreeNode *p, TreeNode *q) {
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
                         unordered_map<int, vector<pair<int, int> > > edgeMap) {
    visited[curr - 1] = true;
    for (auto neighbor: edgeMap[curr]) {
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

int Solution::minScore(int n, vector<vector<int> > &roads) {
    // build map for edges
    unordered_map<int, vector<pair<int, int> > > edgeMap;
    for (auto road: roads) {
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
    for (const auto ch: s) {
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

void pickOne(vector<vector<int> > &result, vector<int> &curr, vector<int> &nums, int num) {
    if (!contains(curr, num)) {
        curr.push_back(num);
    }
    if (curr.size() == nums.size()) {
        result.push_back(curr);
        return;
    }
    for (auto n: nums) {
        if (!contains(curr, n)) {
            pickOne(result, curr, nums, n);
        }
    }
}

vector<vector<int> > Solution::permute(vector<int> &nums) {
    vector<vector<int> > result;
    vector<int> curr;
    for (auto num: nums) {
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
        if (reversed > BOUNDARY_P || reversed == BOUNDARY_P && curr > 7) return 0;
        if (reversed < BOUNDARY_N || reversed == BOUNDARY_N && curr < -8) return 0;
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
        for (char ch: s) {
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
    for (string &name: names) {
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
        for (string &name: stack) {
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

vector<int> Solution::successfulPairs(vector<int> &spells, vector<int> &potions, long long success) {
    vector<int> res;
    for (auto spell: spells) {
        int count = 0;
        for (auto potion: potions) {
            long long product = static_cast<long long>(spell) * potion;
            if (product >= success) {
                ++count;
            }
        }
        res.push_back(count);
    }
    return res;
}

vector<int> Solution::successfulPairs2(vector<int> &spells, vector<int> &potions, long long success) {
    vector<int> res;
    sort(potions.begin(), potions.end());
    for (auto spell: spells) {
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

vector<int> Solution::successfulPairs3(vector<int> &spells, vector<int> &potions, long long success) {
    vector<int> res;
    sort(potions.begin(), potions.end());
    for (auto spell: spells) {
        int low = 0, high = potions.size() - 1;
        bool set = false;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (low == high && (mid == 0 || mid == potions.size() - 1)) {
                break;
            }
            long long thisProduct = static_cast<long long>(spell) * potions[mid];
            long long nextProduct = static_cast<long long>(spell) * potions[mid + 1];
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
                long long product = static_cast<long long>(spell) * potions[low];
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
                long long product = static_cast<long long>(spell) * potions[high];
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
            if (count == total) { return res; }
        }
        --j;
        ++i;
        while (i < m - k) {
            res.push_back(matrix[i][j]);
            ++count;
            ++i;
            if (count == total) { return res; }
        }
        --i;
        --j;
        while (j >= k) {
            res.push_back(matrix[i][j]);
            ++count;
            --j;
            if (count == total) { return res; }
        }
        ++j;
        --i;
        while (i > k) {
            res.push_back(matrix[i][j]);
            ++count;
            --i;
            if (count == total) { return res; }
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

vector<vector<int> > Solution::threeSum2(vector<int> &nums) {
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

vector<int> Solution::twoSum(vector<int> &nums, int target) {
    vector<vector<int>> nums2 = {};
    for (int i = 0; i < nums.size(); i++) {
        nums2.push_back({nums[i], i});
    }
    sort(nums2.begin(), nums2.end(),  [](const vector<int> &a, const vector<int> &b) {
        return a[0] < b[0];
    });
    int i = 0, j = nums.size() - 1;
    while (i < j) {
        const int sum = nums2[i][0] + nums2[j][0];
        if (sum == target) {
            return {nums2[i][1], nums2[j][1]};
        }
        if (sum > target) j--;
        else i++;
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

int Solution::sumNumbers(TreeNode *root) {
    return addThis(0, root);
}
