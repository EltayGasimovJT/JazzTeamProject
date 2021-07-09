package entity;

public class Node {

    private int value;
    private Node left;
    private Node right;

    public Node(){}

    public Node(int value) {
        this.value = value;
    }

    public Node(int value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getValue() {
        return value;
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
