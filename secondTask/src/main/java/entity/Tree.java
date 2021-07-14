package entity;

public class Tree {
    private Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public Tree(int[] arrayForTreeCreate) {
        for (int i = 0; i < arrayForTreeCreate.length; i++) {
            this.add(new Node(), arrayForTreeCreate[i]);
        }
    }

    public Node getRoot() {
        return root;
    }

    public void insert(int value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
        } else {
            Node currentNode = root;
            Node parentNode;
            while (true) {
                parentNode = add(currentNode, value);
                if (value == currentNode.getValue()) {
                    return;
                } else if (value < currentNode.getValue()) {
                    currentNode = currentNode.getLeft();
                    if (currentNode == null) {
                        parentNode.setLeft(add(newNode, value));
                        return;
                    }
                } else {
                    currentNode = currentNode.getRight();
                    if (currentNode == null) {
                        parentNode.setRight(add(newNode, value));
                        return;
                    }
                }
            }
        }

    }

    public Node add(Node node, int key) {
        if (node == null) {
            return new Node(key);
        } else if (node.getValue() > key) {
            node.setLeft(add(node.getLeft(), key));
        } else if (node.getValue() < key) {
            node.setRight(add(node.getRight(), key));
        }
        return reBalanceTree(node);
    }

    void updateHeight(Node node) {
        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
    }

    int height(Node node) {
        return node == null ? -1 : node.getHeight();
    }

    int getBalance(Node node) {
        return (node == null) ? 0 : height(node.getRight()) - height(node.getLeft());
    }

    private Node rotateLeft(Node node) {
        Node x = node.getRight();
        Node z = x.getLeft();
        x.setLeft(node);
        node.setRight(z);
        updateHeight(node);
        updateHeight(x);
        return x;
    }

    private Node rotateRight(Node node) {
        Node x = node.getLeft();
        Node z = x.getRight();
        x.setRight(node);
        node.setLeft(z);
        updateHeight(node);
        updateHeight(x);
        return x;
    }

    public Node reBalanceTree(Node node) {
        updateHeight(node);
        int balance = getBalance(node);
        if (balance > 1) {
            if (height(node.getRight().getRight()) > height(node.getRight().getLeft())) {
                node = rotateLeft(node);
            } else {
                node.setRight(rotateRight(node.getRight()));
                node = rotateLeft(node);
            }
        } else if (balance < -1) {
            if (height(node.getLeft().getLeft()) > height(node.getLeft().getRight()))
                node = rotateRight(node);
            else {
                node.setLeft(rotateLeft(node.getLeft()));
                node = rotateRight(node);
            }
        }
        return node;

    }
}