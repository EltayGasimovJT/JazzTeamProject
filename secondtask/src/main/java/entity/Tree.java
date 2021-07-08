package entity;

public class Tree {

    private Node root;

    {
        this.root = new Node();
    }

    public void setRoot(int root) {
        this.root.value = root;
    }

    public Node getRoot() {
        return root;
    }

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

        public void add(int value) {
            Node node = new Node();
            node.value = value;
            if (root == null) {
                root = node;
            } else {
                Node current = root;
                Node prev = null;
                while (true) {
                    prev = current;
                    if (value < prev.value) {
                        current = current.left;
                        if (current == null) {
                            prev.left = node;
                            return;
                        }
                    } else {
                        current = current.right;
                        if (current == null) {
                            prev.right = node;
                            return;
                        }
                    }
                }
            }
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
}