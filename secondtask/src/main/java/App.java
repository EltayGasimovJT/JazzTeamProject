import entity.Tree;

public class App {


    public static void main(String[] args) {
        Tree tree = new Tree(10, new Tree(8, new Tree(1), new Tree(9)), new Tree(11));
        int countOfLeaf = tree.getCountOfLeaf();
        System.out.println(countOfLeaf);
    }
}
