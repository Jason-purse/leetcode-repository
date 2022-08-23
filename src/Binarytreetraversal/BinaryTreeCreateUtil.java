package Binarytreetraversal;

import java.util.LinkedList;
/**
 * @author FLJ
 * @date 2022/8/23
 * @time 14:22
 * @Description 根据 leetcode 二叉树的 数组列表生成对应的TreeNode ...
 */
public class BinaryTreeCreateUtil {

    private BinaryTreeCreateUtil() {

    }

    public static TreeNode createTree(Integer[] values) {
        LinkedList<TreeNode> stack = new LinkedList<>();

        int i = 1;
        TreeNode root = new TreeNode(values[0]);
        stack.add(root);
        do {
            Integer left = values[i];
            TreeNode peek = stack.peek();
            peek.left = left != null ? new TreeNode(left) : null;
            if (i + 1 >= values.length) {
                break;
            }
            Integer right = values[i + 1];
            peek.right = right != null ? new TreeNode(right) : null;
            if (left != null) {
                stack.add(peek.left);
            }
            if (right != null) {
                stack.add(peek.right);
            }
            i += 2;
            stack.pop();
        } while (stack.size() > 0 && i < values.length);

        return root;
    }
}
