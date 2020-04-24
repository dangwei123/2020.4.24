实现一个二叉搜索树迭代器。你将使用二叉搜索树的根节点初始化迭代器。

调用 next() 将返回二叉搜索树中的下一个最小的数。
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class BSTIterator {
    private Stack<TreeNode> stack=new Stack<>();
    public BSTIterator(TreeNode root) {
        myPush(root);
    }

    private void myPush(TreeNode root){
        while(root!=null){
            stack.push(root);
            root=root.left;
        }
    }
    
    /** @return the next smallest number */
    public int next() {
        TreeNode node=stack.pop();
        myPush(node.right);
        return node.val;
    }
    
    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.isEmpty();
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
 
给定二叉搜索树（BST）的根节点和一个值。 你需要在BST中找到节点值等于给定值的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 NULL
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
    public TreeNode searchBST(TreeNode root, int val) {
        if(root==null){
            return null;
        }
        if(root.val==val){
            return root;
        }else if(val<root.val){
            return searchBST(root.left,val);
        }else{
            return searchBST(root.right,val);
        }
    }
}
给定二叉搜索树（BST）的根节点和要插入树中的值，将值插入二叉搜索树。 返回插入后二叉搜索树的根节点。 保证原始二叉搜索树中不存在新值。

注意，可能存在多种有效的插入方式，只要树在插入后仍保持为二叉搜索树即可。 你可以返回任意有效的结果。
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
    public TreeNode insertIntoBST(TreeNode root, int val) {
        TreeNode pre=null;
        TreeNode cur=root;
        while(cur!=null){
            if(val<cur.val){
                pre=cur;
                cur=cur.left;
            }else{
                pre=cur;
                cur=cur.right;
            }
        }
        TreeNode node=new TreeNode(val);
        if(val<pre.val){
            pre.left=node;
        }else{
            pre.right=node;
        }
        return root;
    }
}
给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。

一般来说，删除节点可分为两个步骤：

首先找到需要删除的节点；
如果找到了，删除它。
说明： 要求算法时间复杂度为 O(h)，h 为树的高度。
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode pre=null;
        TreeNode cur=root;
        while(cur!=null){
            if(key==cur.val){
                break;
            }else if(key<cur.val){
                pre=cur;
                cur=cur.left;
            }else{
                pre=cur;
                cur=cur.right;
            }
        }
        if(cur==null) return root;
        if(cur.left==null){
            if(cur==root){
                root=cur.right;
            }else if(cur==pre.left){
                pre.left=cur.right;
            }else{
                pre.right=cur.right;
            }
        }else if(cur.right==null){
            if(cur==root){
                root=cur.left;
            }else if(cur==pre.left){
                pre.left=cur.left;
            }else{
                pre.right=cur.left;
            }
        }else{
            TreeNode goatParent=cur;
            TreeNode goat=cur.right;
            while(goat.left!=null){
                goatParent=goat;
                goat=goat.left;
            }
            cur.val=goat.val;
            if(goat==goatParent.left){
                goatParent.left=goat.right;
            }else{
                goatParent.right=goat.right;
            }
        }
        return root;
    }
    private void delete(TreeNode pre,TreeNode cur){
        
    }
}

