package Binarytreetraversal;

import java.util.LinkedList;
import java.util.List;

/**
 * leetcode question uri:
 *  https://leetcode.cn/problems/print-binary-tree/
 *
 *  给你一棵二叉树的根节点 root ，请你构造一个下标从 0 开始、大小为 m x n 的字符串矩阵 res ，用以表示树的 格式化布局 。构造此格式化布局矩阵需要遵循以下规则：
 *
 * 树的 高度 为 height ，矩阵的行数 m 应该等于 height + 1 。
 * 矩阵的列数 n 应该等于 2height+1 - 1 。
 * 根节点 需要放置在 顶行 的 正中间 ，对应位置为 res[0][(n-1)/2] 。
 * 对于放置在矩阵中的每个节点，设对应位置为 res[r][c] ，将其左子节点放置在 res[r+1][c-2height-r-1] ，右子节点放置在 res[r+1][c+2height-r-1] 。
 * 继续这一过程，直到树中的所有节点都妥善放置。
 * 任意空单元格都应该包含空字符串 "" 。
 * 返回构造得到的矩阵 res 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/print-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 *  刚开始理解错了题目意思,以为是纯粹是为了(对称打印) 二叉树 ...,
 */
public class FLJ_Code {


    static List<List<String>> printTree(TreeNode root) {

        if (root == null) {
            throw new UnsupportedOperationException();
        }
        // 如果 栈里面全是空,就不处理了 ...
        int depth = searchTreeDepth(root);

        // 此时高度就是深度 ..
        // 总数量为 2^n - 1;

        int total = (int) Math.pow(2, depth) - 1;

        // 深度算出来了 ..
        return  printTreeByMiddle(root, depth, new String[depth][total]);

    }

    // 深度 中序遍历
    private static List<List<String>> printTreeByMiddle(TreeNode root, int depth, String[][] array) {
        // 开始解析左右
        int middle = ((int) (Math.pow(2, depth) - 1)) / 2;
        array[0][middle] = String.valueOf(root.val);
        if(depth > 1) {
            Point point = new Point(0, middle);
            printTreeByMiddleInternal(root.left, true, point, depth, array);
            printTreeByMiddleInternal(root.right, false, point, depth, array);
        }
        LinkedList<List<String>> result = new LinkedList<>();
        for (String[] objects : array) {
            LinkedList<String> strings = new LinkedList<>();
            for (String object : objects) {
                if(object == null) {
                    strings.add("");
                }
                else {
                    strings.add(object);
                }
            }
            result.add(strings);
        }
        return result;
    }

    public static void printTreeByMiddleInternal(TreeNode node, boolean isLeft, Point parentPoint, int depth, String[][] array) {
        int y = (int) Math.pow(2, depth - parentPoint.x - 2);
        Point point = new Point();
        point.x = parentPoint.x + 1;
        // 计算自己的位置 .
        if (isLeft) {
            point.y = parentPoint.y - y;
        } else {
            point.y = parentPoint.y + y;
        }
        array[point.x][point.y] = node == null ? "" : String.valueOf(node.val);
        if(node != null) {
            if (node.left != null) {
                printTreeByMiddleInternal(node.left, true, point, depth, array);
            }
            if(node.right != null) {
                printTreeByMiddleInternal(node.right, false, point, depth, array);
            }
        }
    }


    static class Point {
        int x;
        int y;

        public Point() {

        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * 深度优先遍历深度 ..
     * @param treeNode treeNode
     * @return 深度 ..
     */
    public static int searchTreeDepth(TreeNode treeNode) {

        if(treeNode.left == null && treeNode.right == null) {
            return 1;
        }
        int leftDepth = searchTreeDepthInternal(treeNode.left,1);
        int rightDepth = searchTreeDepthInternal(treeNode.right,1);
        return Math.max(leftDepth, rightDepth);
    }

    private static int searchTreeDepthInternal(TreeNode left, int i) {
        if(left != null) {
            int leftDepth = searchTreeDepthInternal(left.left,i + 1);
            int rightDepth = searchTreeDepthInternal(left.right,i + 1);
            return Math.max(leftDepth,rightDepth);
        }

        return i;
    }


    /**
     * 这个用于将 tree(完全二叉树) ... 进行对称打印 ... 但是与当前问题无关 ....
     * @param n 深度
     * @param tree 树
     */
    public static List<List<String>> printfNTreeArray(int n, String[] tree) {
        // 数组列数
        int y = tree.length;

        String[][] mutex = new String[n][y];

        int s = 0;
        int middle = y / 2;
        // 先序遍历二叉树
        for (int i = 0; i < tree.length; i++) {
            if (s < n) {
                // 算出它的位置 ..
                if (i == 0) {
                    mutex[0][middle] = tree[0];
                    for (int i1 = 0; i1 < mutex[0].length; i1++) {
                        if (mutex[0][i1] == null) {
                            mutex[0][i1] = "";
                        }
                    }
                    s = 1;
                } else {
                    int total = (int) Math.pow(2, s);

                    // 相邻元素的 偏距值 ...(对应每一行的 相邻元素的 偏距值 )
                    int span = (int) Math.pow(2, (n - s));
                    // 表示最左边 ..
                    middle /= 2;
                    // 取出这一列的数 ...
                    for (int j = i, x = 0; j < i + total && middle + x < y; j++, x = x + span) {
                        // 位置
                        // 算出最左边的 元素位置 ..
                        mutex[s][middle + x] = tree[j] == null ? "" : tree[j];
                    }

                    for (int i1 = 0; i1 < mutex[s].length; i1++) {
                        if (mutex[s][i1] == null) {
                            mutex[s][i1] = "";
                        }
                    }

                    s++;
                    i = i + total - 1;
                }
            }
        }

        // 转换
        LinkedList<List<String>> result = new LinkedList<List<String>>();
        for (String[] objects : mutex) {
            LinkedList<String> strings = new LinkedList<>();
            for (String object : objects) {
                if(object == null) {
                    strings.add("");
                }
                else {
                    strings.add(object);
                }
            }
            result.add(strings);
        }
        return result;
    }
}