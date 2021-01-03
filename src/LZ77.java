import java.util.Vector;

public class LZ77 {
    public static Vector<Vector<Integer>> commpress(String str) {
        String searched = "";
        String temp = "";
        int ind = 0;
        Vector<Vector<Integer>> vec = new Vector<Vector<Integer>>();
        for (int i = 0; i < str.length(); i++) {
            temp += str.charAt(i);
            if (searched.contains(temp))
                ind = searched.indexOf(temp);
            else {
                Vector<Integer> inner = new Vector<Integer>();
                if (temp.length() > 1)
                    inner.add(searched.length() - ind);
                else inner.add(0);
                inner.add(temp.length() - 1);
                inner.add((int) str.charAt(i));
                vec.add(inner);
                searched += temp;
                temp = "";
                ind = 0;
            }
        }
        if (temp.length() > 0) {
            Vector<Integer> inner = new Vector<Integer>();
            inner.add(temp.length() - 1);
            inner.add(temp.length() - 1);
            inner.add((int) temp.charAt(temp.length() - 1));
            vec.add(inner);
        }
        return vec;
    }

    public static String decompress(Vector<Vector<Integer>> vec) {
        String str = "";
        for (int i = 0; i < vec.size(); i++) {
            if (vec.get(i).get(0) == 0)
                str += Character.toString(vec.get(i).get(2));
            else {
                str += str.substring(str.length() - vec.get(i).get(0), (str.length() - vec.get(i).get(0)) + vec.get(i).get(1));
                str += Character.toString(vec.get(i).get(2));
            }
        }
        return str;
    }
}