import java.util.Comparator;

public class huffmanNodeComparator implements Comparator<huffmanNode> {
    public int compare(huffmanNode x, huffmanNode y) {

        return x.data - y.data;
    }
}
