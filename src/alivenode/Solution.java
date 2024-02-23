package alivenode;

public class Solution {
    static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val){
            this.val = val;
        }
    }

    static int max = Integer.MIN_VALUE;
    public static int maxPathSum(TreeNode root){
        pathSum(root);
        return max;
    }

    public static int pathSum(TreeNode root){
        if(root == null){
            return 0;
        }

        int left = Math.max(pathSum(root.left), 0);
        int right = Math.max(pathSum(root.right),0);

        max = Math.max(max, left + right + root.val);
        return Math.max(left, right) + root.val;

    }
    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(25);
        root.right = new TreeNode(0);
        root.right.left = new TreeNode(14);
        root.right.right = new TreeNode(15);

        System.out.println("Max Path Sum: " + maxPathSum(root));
    }
}
