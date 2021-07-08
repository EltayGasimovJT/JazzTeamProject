package entity;


public class Tree {

    private int value;
    private Tree left;
    private Tree right;

    public Tree(int value) {
        this.value = value;
    }

    public Tree(int value, Tree left, Tree right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public int getCountOfLeaf() {
        int result = 0;
        if (this.left == null && this.right == null)
            result += 1;
        if (this.left != null)
            result += left.getCountOfLeaf();
        if (this.right != null)
            result += right.getCountOfLeaf();
        return result;

    }
}