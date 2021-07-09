package creator;

import entity.Tree;

public class TreeCreator {
    public static Tree createTree(int[] arrayForTreeCreate) {
        Tree tree = new Tree();

        for (int i = 0; i < arrayForTreeCreate.length; i++) {
            tree.add(arrayForTreeCreate[i]);
        }

        return tree;
    }
}