package Binarytreetraversal;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode question uri:
 *  *  https://leetcode.cn/problems/print-binary-tree/
 *  *
 *  *  给你一棵二叉树的根节点 root ，请你构造一个下标从 0 开始、大小为 m x n 的字符串矩阵 res ，用以表示树的 格式化布局 。构造此格式化布局矩阵需要遵循以下规则：
 *  *
 *  * 树的 高度 为 height ，矩阵的行数 m 应该等于 height + 1 。
 *  * 矩阵的列数 n 应该等于 2height+1 - 1 。
 *  * 根节点 需要放置在 顶行 的 正中间 ，对应位置为 res[0][(n-1)/2] 。
 *  * 对于放置在矩阵中的每个节点，设对应位置为 res[r][c] ，将其左子节点放置在 res[r+1][c-2height-r-1] ，右子节点放置在 res[r+1][c+2height-r-1] 。
 *  * 继续这一过程，直到树中的所有节点都妥善放置。
 *  * 任意空单元格都应该包含空字符串 "" 。
 *  * 返回构造得到的矩阵 res 。
 *  *
 *  * 来源：力扣（LeetCode）
 *  * 链接：https://leetcode.cn/problems/print-binary-tree
 *  * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class PQS_Code {

    private static int height = 0;

    public List<List<String>> printTree(TreeNode root) {
        height = getDepth(root)-1;
        // System.out.println("height:"+height);
        // 行数
        int row = height + 1;
        // 列数
        int col = (int) Math.pow(2, row) - 1;
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            List<String> ll = new ArrayList<>(col);
            for (int i1 = 0; i1 < col; i1++) {
                ll.add("");
            }
            list.add(ll);
        }
        depthFirst(list, root, 0, (col - 1) / 2);
        return list;
    }

    static void depthFirst(List<List<String>> list, TreeNode node, int row, int col) {
        if (node == null) {
            return;
        }
        list.get(row).set(col, getValue(node));
        int offset = (int) Math.pow(2, height - row - 1);
        // System.out.println("row:"+row+"  col:"+col);
        depthFirst(list, node.left, row + 1, col - offset);
        depthFirst(list, node.right, row + 1, col + offset);
    }

    static String getValue(TreeNode node) {
        return node == null ? "" : String.valueOf(node.val);
    }

    static int getDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        // System.out.println("node:"+node.val);
        return Math.max(getDepth(node.left), getDepth(node.right))+1;
    }

}
