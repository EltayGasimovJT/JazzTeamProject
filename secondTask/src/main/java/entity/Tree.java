package entity;

public class Tree {
    private Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public Tree(int[] arrayForTreeCreate){
        for (int i = 0; i < arrayForTreeCreate.length; i++) {
            this.add(arrayForTreeCreate[i]);
        }
    }

    public Node getRoot() {
        return root;
    }

    public void add(int value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
        }
        else {
            Node currentNode = root;
            Node parentNode;
            while (true)
            {
                parentNode = currentNode;
                if(value == currentNode.getValue()) {
                    return;
                }
                else  if (value < currentNode.getValue()) {
                    currentNode = currentNode.getLeft();
                    if (currentNode == null){
                        parentNode.setLeft(newNode);
                        return;
                    }
                }
                else {
                    currentNode = currentNode.getRight();
                    if (currentNode == null) {
                        parentNode.setRight(newNode);
                        return;
                    }
                }
            }
        }
    }
}