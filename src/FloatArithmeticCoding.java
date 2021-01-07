import java.util.*;

public class FloatArithmeticCoding {

    public static Map<Character, ArrayList<Double>> generateRanges(String str){
        //to sore high range and low range for each character
        Map<Character, ArrayList<Double>> ranges = new HashMap<Character, ArrayList<Double>>();

        //to store only one copy for each character in the string
        Set<Character> uniqueChars = new LinkedHashSet<Character>();

        //convert the string to arr to sort it
        char[] arr = str.toCharArray();
        Arrays.sort(arr);

        //add character to the set
        for (int i = 0; i < str.length(); i++)
            uniqueChars.add(arr[i]);

        //add range of each character to the map
        double low = 0, high, count;
        for (char info : uniqueChars) {
            count = str.chars().filter(c -> c == info).count();
            high = (count / str.length()) + low;
            ArrayList<Double> pair = new ArrayList<>();
            pair.add(low);
            pair.add(high);
            ranges.put(info, pair);
            low = high;
        }
        return ranges;
    }
    public static double compress(String str) {
        if (str.length() == 0) {
            return 0;
        }
        Map<Character, ArrayList<Double>> ranges = generateRanges(str);

        double low_range, high_range, x;
        double low_range_curr, high_range_curr;
        char ch;
        ch = str.charAt(0);
        low_range = ranges.get(ch).get(0);
        high_range = ranges.get(ch).get(1);
        for (int i = 1; i < str.length(); i++) {
            ch = str.charAt(i);
            low_range_curr = ranges.get(ch).get(0);
            high_range_curr = ranges.get(ch).get(1);
            x = low_range;
            low_range = low_range + (high_range - low_range) * low_range_curr;
            high_range = x + (high_range - x) * high_range_curr;
        }
        return (low_range + high_range) / 2.0;
    }

    public static String deCompress(double floatCode, int len, Map<Character, ArrayList<Double>> ranges) {
        if (ranges.size() == 0) {
            return "";
        }
        double low_range = 0, high_range = 0, x;
        double low_range_curr, high_range_curr;
        double temCode;
        StringBuilder res = new StringBuilder();
        for (char info : ranges.keySet()) {
            if (floatCode >= ranges.get(info).get(0) && floatCode < ranges.get(info).get(1)) {
                res.append(info);
                low_range = ranges.get(info).get(0);
                high_range = ranges.get(info).get(1);
            }
        }
        for (int i = 1; i < len; i++) {
            temCode = (floatCode - low_range) / (high_range - low_range);
            for (char info : ranges.keySet()) {
                if (temCode >= ranges.get(info).get(0) && temCode < ranges.get(info).get(1)) {
                    res.append(info);
                    low_range_curr = ranges.get(info).get(0);
                    high_range_curr = ranges.get(info).get(1);
                    x = low_range;
                    low_range = low_range + (high_range - low_range) * low_range_curr;
                    high_range = x + (high_range - x) * high_range_curr;
                }
            }
        }
        return res.toString();
    }
}