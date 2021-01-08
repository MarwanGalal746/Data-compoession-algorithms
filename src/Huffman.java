import java.util.ArrayList;
import java.util.PriorityQueue;

public class Huffman {

    private static void huffmanCode(huffmanNode node, String str) {
        if (node.left == null && node.right == null && Character.isLetter(node.c)) {
            System.out.println(node.c + " " + str);
            return;
        }
        huffmanCode(node.left, str + "0");
        huffmanCode(node.right, str + "1");
    }

    public static void compress(ArrayList<Character> arr, ArrayList<Integer> freq) {
        PriorityQueue<huffmanNode> q = new PriorityQueue<huffmanNode>(arr.size(), new huffmanNodeComparator());
        for (int i = 0; i < arr.size(); i++) {
            huffmanNode temp = new huffmanNode();
            temp.c = arr.get(i);
            temp.data = freq.get(i);
            temp.left = null;
            temp.right = null;
            q.add(temp);
        }
        huffmanNode root = null;
        while (q.size() > 1) {
            huffmanNode least = q.peek();
            q.poll();
            huffmanNode least2 = q.peek();
            q.poll();
            huffmanNode f = new huffmanNode();
            f.data = least.data + least2.data;
            f.c = '^';
            f.left = least;
            f.right = least2;
            root = f;
            q.add(f);
        }
        huffmanCode(root, "");
    }
}
