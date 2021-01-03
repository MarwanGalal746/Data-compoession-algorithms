import java.util.ArrayList;
import java.util.Vector;

public class LZ78 {
    public static Vector<Vector<Integer>> compress(String str) {
        Vector<Vector<Integer>> vec = new Vector<Vector<Integer>>();
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(null);
        String collector = "";
        for (int i = 0; i < str.length(); i++) {
            collector += str.charAt(i);
            Vector<Integer> x;
            if (!temp.contains(collector)) {
                x = new Vector<>();
                String sub = collector.substring(0, collector.length() - 1);
                if (temp.contains(sub))
                    x.add(temp.indexOf(sub));
                else x.add(0);
                x.add((int) collector.charAt(collector.length() - 1));
                vec.add(x);
                temp.add(collector);
                collector = "";
                System.out.println(x.get(0) + " " + x.get(1));
            }
            if (i == str.length() - 1 && collector.length() > 0) {
                x = new Vector<>();
                if (temp.contains(collector)) {
                    x.add(0);
                    x.add(null);
                } else {
                    String sub = collector.substring(0, collector.length() - 1);
                    x.add(temp.indexOf(sub));
                    x.add((int) collector.charAt(collector.length() - 1));
                }
                vec.add(x);
            }
        }
        return vec;
    }

    public static String decompress(Vector<Vector<Integer>> vec) {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(null);
        String res = "";
        for (int i = 0; i < vec.size(); i++) {
            if (vec.get(i).get(0) == 0) {
                res += Character.toString(vec.get(i).get(1));
                temp.add(Character.toString(vec.get(i).get(1)));
            } else {
                res += temp.get(vec.get(i).get(0));
                String x = temp.get(vec.get(i).get(0));
                if (!vec.get(i).get(0).equals(null)) {
                    res += Character.toString(vec.get(i).get(1));
                    x += Character.toString(vec.get(i).get(1));
                }
                temp.add(x);
            }
        }
        return res;
    }
}
