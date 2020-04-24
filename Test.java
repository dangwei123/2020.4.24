找到给定字符串（由小写字符组成）中的最长子串 T ， 要求 T 中的每一字符出现次数都不少于 k 。输出 T 的长度。
class Solution {
    public int longestSubstring(String s, int k) {
        return longest(s.toCharArray(),k,0,s.length()-1);
    }
    private int longest(char[] chars,int k,int left,int right){
        if(right-left+1<k) return 0;
        int[] times=new int[26];
        for(int i=left;i<=right;i++){
            times[chars[i]-'a']++;
        }
        while(right-left+1>=k&&times[chars[left]-'a']<k){
            left++;
        }
        while(right-left+1>=k&&times[chars[right]-'a']<k){
            right--;
        }
        if(right-left+1<k){
            return 0;
        }
        for(int i=left;i<=right;i++){
            if(times[chars[i]-'a']<k){
                return Math.max(longest(chars,k,left,i-1),longest(chars,k,i+1,right));
            }
        }
        return right-left+1;
    }
}

给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：

每次转换只能改变一个字母。
转换过程中的中间单词必须是字典中的单词。
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> set=new HashSet<>(wordList);
        if(!set.contains(endWord)) return 0;
        Queue<String> up=new LinkedList<>();
        Queue<String> down=new LinkedList<>();
        Set<String> set1=new HashSet<>();
        Set<String> set2=new HashSet<>();
        
        up.offer(beginWord);
        set1.add(beginWord);
        down.offer(endWord);
        set2.add(endWord);
        int res=1;
        while(!up.isEmpty()&&!down.isEmpty()){
            res++;
            if(up.size()>down.size()){
                Queue<String> tmp=up;
                up=down;
                down=tmp;
                
                Set<String> t=set1;
                set1=set2;
                set2=t;
            }
            
            int size=up.size();
            while(size--!=0){
                String s=up.poll();
                char[] chars=s.toCharArray();
                for(int i=0;i<chars.length;i++){
                    char a=chars[i];
                    for(char c='a';c<='z';c++){
                        chars[i]=c;
                        String t=new String(chars);
                        if(set1.contains(t)){
                            continue;
                        }
                        if(set.contains(t)){
                            if(set2.contains(t)){
                                return res;
                            }
                            up.offer(t);
                            set1.add(t);
                        }
                    }
                    chars[i]=a;
                }
            }
            
        }
        return 0;
    }
}

输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。要求不能创建任何新的节点，只能调整树中节点指针的指向。
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val,Node _left,Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
};
*/
class Solution {
    private Node pre=null;
    public Node treeToDoublyList(Node root) {
        if(root==null) return null;
        Node first=root;
        Node last=root;
        while(first.left!=null){
            first=first.left;
        }
        while(last.right!=null){
            last=last.right;
        }
        doubleList(root);
        last.right=first;
        first.left=last;
        return first;
    }
    private void doubleList(Node root){
        if(root==null) return ;
        doubleList(root.left);
        root.left=pre;
        if(pre!=null){
            pre.right=root;
        }
        
        pre=root;
        doubleList(root.right);
    }
}

在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。
class Solution {
    private int res;
    public int reversePairs(int[] nums) {
        mergeSort(nums,0,nums.length-1);
        return res;
    }
    private void mergeSort(int[] nums,int left,int right){
        if(left>=right){
            return ;
        }
        int mid=left+(right-left)/2;
        mergeSort(nums,left,mid);
        mergeSort(nums,mid+1,right);
        merge(nums,left,mid,right);
    }
    private void merge(int[] nums,int left,int mid,int right){
        int i=left;
        int j=mid+1;
        int len=right-left+1;
        int[] tmp=new int[len];
        int k=0;
        while(i<=mid&&j<=right){
            if(nums[i]<=nums[j]){
                res+=j-mid-1;
                tmp[k++]=nums[i++];
            }else{
                tmp[k++]=nums[j++];
            }
        }
        while(i<=mid){
            res+=right-mid;
            tmp[k++]=nums[i++];
        }
        while(j<=right){
            tmp[k++]=nums[j++];
        }
        System.arraycopy(tmp,0,nums,left,len);
    }
}