package com.yuwuquan.demo.common.leetcode;

/**
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。
 *
 * 示例 :
 * 给定二叉树
 *
 *           1
 *          / \
 *         2   3
 *        / \
 *       4   5
 * 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
 */
public class TreeMaxLength {
    public int max;
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public int digui(TreeNode root,TreeMaxLength treeMaxLength) {
        if(root.left == null && root.right == null) return 0;
        int left=0;
        int right = 0;
        if(root.left != null){
            left = digui(root.left,treeMaxLength) + 1;
        }
        if(root.right != null){
            right = digui(root.right,treeMaxLength) + 1;
        }
        if ((left + right) > treeMaxLength.max){
            treeMaxLength.max = (left + right);
        }
        return left>right?left:right;
    }
    public int diameterOfBinaryTree(TreeNode root){
        if(root == null) return 0;
        TreeMaxLength treeMaxLength = new TreeMaxLength();
        digui(root, treeMaxLength);
        return treeMaxLength.max;
    }
    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(1);
        TreeNode root2 = new TreeNode(2);
        TreeNode root3 = new TreeNode(3);
        TreeNode root4 = new TreeNode(4);
        TreeNode root5 = new TreeNode(5);
//        root1.left = root2;
//        root1.right = root3;
//        root2.left = root4;
//        root2.right = root5;
        System.out.println(new TreeMaxLength().diameterOfBinaryTree(root1));
    }
}
