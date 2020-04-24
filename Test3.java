给定一个整数数组，判断数组中是否有两个不同的索引 i 和 j，使得 nums [i] 和 nums [j] 的差的绝对值最大为 t，并且 i 和 j 之间的差的绝对值最大为 ķ。
class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        TreeSet<Long> set=new TreeSet<>();
        for(int i=0;i<nums.length;i++){
            Long u=set.ceiling((long)nums[i]-t);
            if(u!=null&&u<=((long)nums[i]+(long)(t))){
                return true;
            }
            set.add((long)nums[i]);
            if(set.size()>k){
                set.remove((long)nums[i-k]);
            }
        }
        return false;
    } 
}

将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。

本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return sort(nums,0,nums.length-1);
    }
    private TreeNode sort(int[] nums,int left,int right){
        if(left>right){
            return null;
        }
        int mid=left+(right-left)/2;
        TreeNode root=new TreeNode(nums[mid]);
        root.left=sort(nums,left,mid-1);
        root.right=sort(nums,mid+1,right);
        return root;
    }
}

实现一个 MapSum 类里的两个方法，insert 和 sum。

对于方法 insert，你将得到一对（字符串，整数）的键值对。字符串表示键，整数表示值。如果键已经存在，那么原来的键值对将被替代成新的键值对。

对于方法 sum，你将得到一个表示前缀的字符串，你需要返回所有以该前缀开头的键的值的总和。
class MapSum {
    private static class Node{
        private int val;
        private Node[] next=new Node[26];
        private Node(){
            this.val=0;
        }
        public Node(int val){
            this.val=val;
        }
    }
    /** Initialize your data structure here. */
    private Node root; 
    private Map<String,Integer> map;
    public MapSum() {
        root=new Node();
        map=new HashMap<>();
    }
    
    public void insert(String key, int val) {
        Node cur=root;
        if(map.containsKey(key)){
            int rep=map.get(key);
            for(int i=0;i<key.length();i++){
                int index=key.charAt(i)-'a';
                cur=cur.next[index];
                cur.val+=(val-rep);
            }
            
        }else{
            for(int i=0;i<key.length();i++){
            int index=key.charAt(i)-'a';
            if(cur.next[index]==null){
                cur.next[index]=new Node(val);
            }else{
                cur.next[index].val+=val;
            }
            cur=cur.next[index];
            }
            map.put(key,val);
        }
        
        
    }
    
    public int sum(String prefix) {
        Node cur=root;
        for(int i=0;i<prefix.length();i++){
            int index=prefix.charAt(i)-'a';
            if(cur.next[index]==null){
                return 0;
            }
            cur=cur.next[index];
        }
        return cur.val;
    }
}


在英语中，我们有一个叫做 词根(root)的概念，它可以跟着其他一些词组成另一个较长的单词——我们称这个词为 继承词(successor)。例如，词根an，跟随着单词 other(其他)，可以形成新的单词 another(另一个)。

现在，给定一个由许多词根组成的词典和一个句子。你需要将句子中的所有继承词用词根替换掉。如果继承词有许多可以形成它的词根，则用最短的词根替换它。

你需要输出替换之后的句子。
class Solution {
    public String replaceWords(List<String> dict, String sentence) {
        Trie trie=new Trie();
        for(String s:dict){
            trie.insert(s);
        }
        Node root=trie.root;
        String[] sent=sentence.split(" ");
        StringBuilder sb=new StringBuilder();
        for(String s:sent){
            Node cur=root;
            if(cur.next[s.charAt(0)-'a']==null){
                sb.append(s).append(" ");
            }else{
                int index=0;
                boolean isAdd=false;
                for(int i=0;i<s.length();i++){
                    index=s.charAt(i)-'a';
                    if(cur.next[index]==null){
                        isAdd=true;
                        sb.append(s).append(" ");
                        break;
                    }else if(cur.next[index].isEnd){
                        isAdd=true;
                        sb.append(s.substring(0,i+1)).append(" ");
                        break;
                    } 
                    cur=cur.next[index];
                }
                
                if(!isAdd){
                    sb.append(s).append(" ");
                }
            }
        }
        
        return sb.substring(0,sb.length()-1);
    }
}
class Trie{
    public Node root=new Node();
    public void insert(String s){
        Node cur=root;
        for(int i=0;i<s.length();i++){
            int index=s.charAt(i)-'a';
            if(cur.next[index]==null){
                cur.next[index]=new Node();
            }
            cur=cur.next[index];
        }
        cur.isEnd=true;
    }
}
class Node{
    public Node[] next=new Node[26];
    public boolean isEnd;
}
添加与搜索单词 - 数据结构设计
class WordDictionary {
    private static class Node{
        private Node[] next=new Node[26];
        private boolean isEnd;
    }
    /** Initialize your data structure here. */
    private Node root;
    public WordDictionary() {
        root=new Node();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        Node cur=root;
        for(int i=0;i<word.length();i++){
            int index=word.charAt(i)-'a';
            if(cur.next[index]==null){
                cur.next[index]=new Node();
            }
            cur=cur.next[index];
        }
        cur.isEnd=true;
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return search(word,root);
    }
    private boolean search(String word,Node root){
        Node cur=root;
        for(int i=0;i<word.length();i++){
           if(word.charAt(i)=='.'){
               for(int j=0;j<26;j++){
                   if(cur.next[j]!=null){
                       if(search(word.substring(i+1),cur.next[j])){
                           return true;
                       }
                   }
               }
               return false;
           }else{
               int index=word.charAt(i)-'a';
               if(cur.next[index]==null){
                   return false;
               }
               cur=cur.next[index];
           }
        }
        return cur.isEnd;
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
