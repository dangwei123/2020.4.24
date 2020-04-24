给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。

岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。

此外，你可以假设该网格的四条边均被水包围。
class Solution {
    private int row;
    private int col;
    public int numIslands(char[][] grid) {
        row=grid.length;
        if(row==0) return 0;
        col=grid[0].length;
        int res=0;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(grid[i][j]=='1'){
                    dfs(grid,i,j);
                    res++;
                }
            }
        }
        return res;
    }
    private void dfs(char[][] grid,int i,int j){
        if(i<0||j<0||i>=row||j>=col||grid[i][j]=='0'){
            return ;
        }
        grid[i][j]='0';
        dfs(grid,i+1,j);
        dfs(grid,i,j+1);
        dfs(grid,i-1,j);
        dfs(grid,i,j-1);
    }
}

现在你总共有 n 门课需要选，记为 0 到 n-1。

在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]

给定课程总量以及它们的先决条件，返回你为了学完所有课程所安排的学习顺序。

可能会有多个正确的顺序，你只要返回一种就可以了。如果不可能完成所有课程，返回一个空数组。
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> list=new ArrayList<>();
        int[] indegrees=new int[numCourses];
        for(int i=0;i<numCourses;i++){
            list.add(new ArrayList<>());
        }
        for(int[] pre:prerequisites){
            list.get(pre[1]).add(pre[0]);
            indegrees[pre[0]]++;
        }
        
        Queue<Integer> queue=new LinkedList<>();
        List<Integer> res=new ArrayList<>();
        for(int i=0;i<indegrees.length;i++){
            if(indegrees[i]==0){
                queue.offer(i);
            }
        }
        while(!queue.isEmpty()){
            int cur=queue.poll();
            res.add(cur);
            numCourses--;
            for(Integer i:list.get(cur)){
                if(--indegrees[i]==0){
                    queue.offer(i);
                }
            }
        }
        if(numCourses!=0){
            return new int[0];
        }
        int[] arr=new int[res.size()];
        for(int i=0;i<res.size();i++){
            arr[i]=res.get(i);
        }
        return arr;
    }
}

给定一个二维平面，平面上有 n 个点，求最多有多少个点在同一条直线上。
class Solution {
    public int maxPoints(int[][] points) {
        int max=0;
        int row=points.length;
        if(row<3) return row;
        if(row==0) return 0;
        for(int i=0;i<row;i++){
            int same=1;
            for(int j=i+1;j<row;j++){
                int count=0;
                if(points[i][0]==points[j][0]&&points[i][1]==points[j][1]){
                    same++;
                }else{
                    count++;
                    long dx=points[i][0]-points[j][0];
                    long dy=points[i][1]-points[j][1];
                    for(int k=j+1;k<row;k++){
                        if(dx*(points[i][1]-points[k][1])==dy*(points[i][0]-points[k][0])){
                            count++;
                        }
                    }
                }
                max=Math.max(max,same+count);
                
            }
            if(max>row/2){
                return max;
            }
        }
        return max;
    }
}

运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。

获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
写入数据 put(key, value) - 如果密钥已经存在，则变更其数据值；如果密钥不存在，则插入该组「密钥/数据值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
class LRUCache {
    private static class Node{
        private int key;
        private int val;
        public Node(int key,int val){
            this.key=key;
            this.val=val;
        }
    }
    private Map<Integer,Node> map;
    private LinkedList<Node> list;
    private int capacity;
    public LRUCache(int capacity) {
        map=new HashMap<>();
        list=new LinkedList<>();
        this.capacity=capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        Node node=map.get(key);
        list.remove(node);
        list.addFirst(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node=map.get(key);
            node.val=value;
            list.remove(node);
            list.addFirst(node);
        }else{
            if(map.size()==capacity){
                Node del=list.removeLast();
                map.remove(del.key);
            }
            Node node=new Node(key,value);
            map.put(key,node);
            list.addFirst(node);
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
 
 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素最多出现两次，返回移除后数组的新长度。

不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
class Solution {
    public int removeDuplicates(int[] nums) {
        int i=0;
        int count=1;
        for(int j=1;j<nums.length;j++){
            if(nums[i]==nums[j]){
                count++;
                if(count<=2){
                    ++i;
                    nums[i]=nums[j];
                }
            }else{
                ++i;
                nums[i]=nums[j];
                count=1;
            }
        }
        return ++i;
    }
}

编写一个函数，以字符串作为输入，反转该字符串中的元音字母。
class Solution {
    public String reverseVowels(String s) {
        char[] chars=s.toCharArray();
        int left=0;
        int right=s.length()-1;
        while(left<right){
            while(left<right&&!isVowels(chars[left])){
                left++;
            }
            while(left<right&&!isVowels(chars[right])){
                right--;
            }
            if(left<right){
                char c=chars[left];
                chars[left++]=chars[right];
                chars[right--]=c;
            }
        }
        return new String(chars);
    }
    private boolean isVowels(char c){
        return c=='a'||c=='e'||c=='i'||c=='o'||c=='u'||
               c=='A'||c=='E'||c=='I'||c=='O'||c=='U';
    }
}

