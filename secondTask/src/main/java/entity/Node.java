package entity;

import java.util.Objects;

public class Node {
    private int height;
    private int value;
    private Node left;
    private Node right;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node() {
    }

    public Node(int value) {
        this.left = null;
        this.right = null;
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

    public int getCountOfLeaves() {
        int result = 0;
        if (this.left == null && this.right == null)
            result += 1;
        if (this.left != null)
            result += left.getCountOfLeaves();
        if (this.right != null)
            result += right.getCountOfLeaves();
        return result;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return height == node.height &&
                value == node.value &&
                Objects.equals(left, node.left) &&
                Objects.equals(right, node.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, value, left, right);
    }

}
