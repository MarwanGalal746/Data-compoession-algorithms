import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BinaryArithmeticCoding {

    private static double getLeastProb(String str) {
        double Min = 123452267.0;
        //to store only one copy for each character in the string
        Set<Character> uniqueChars = new LinkedHashSet<Character>();

        //convert the string to arr to sort it
        char[] arr = str.toCharArray();
        Arrays.sort(arr);

        //add character to the set
        for (int i = 0; i < str.length(); i++)
            uniqueChars.add(arr[i]);
        double count;
        for (char info : uniqueChars) {
            count = str.chars().filter(c -> c == info).count();
            count /= str.length();
            if (count <= Min) Min = count;
        }
        return Min;
    }

    public static String compress(String str, Map<Character, ArrayList<Double>> ranges) {
        if (str.length() == 0) {
            return "";
        }
        StringBuilder res = new StringBuilder();
        res.append("0.");
        double low_range, high_range, x;
        double low_range_curr, high_range_curr;
        char ch;
        ch = str.charAt(0);
        low_range = ranges.get(ch).get(0);
        high_range = ranges.get(ch).get(1);
        while ((low_range > 0.5 && high_range > 0.5) || (low_range < 0.5 && high_range < 0.5)) {
            if ((low_range > 0.5 && high_range > 0.5)) {
                low_range = (low_range - .5) * 2;
                high_range = (high_range - .5) * 2;
                res.append('1');
            } else {
                low_range *= 2;
                high_range *= 2;
                res.append('0');
            }
        }
        for (int i = 1; i < str.length(); i++) {
            ch = str.charAt(i);
            low_range_curr = ranges.get(ch).get(0);
            high_range_curr = ranges.get(ch).get(1);
            x = low_range;
            low_range = low_range + (high_range - low_range) * low_range_curr;
            high_range = x + (high_range - x) * high_range_curr;
            while ((low_range > 0.5 && high_range > 0.5) || (low_range < 0.5 && high_range < 0.5)) {
                if ((low_range > 0.5 && high_range > 0.5)) {
                    low_range = (low_range - .5) * 2;
                    high_range = (high_range - .5) * 2;
                    res.append('1');
                } else {
                    low_range *= 2;
                    high_range *= 2;
                    res.append('0');
                }
            }
        }
        res.append('1');
        ArrayList<Double> probabilities = new ArrayList<>();
        for (Map.Entry<Character, ArrayList<Double>> entry : ranges.entrySet())
            probabilities.add(entry.getValue().get(1) - entry.getValue().get(0));
        double leastProb = Collections.min(probabilities);
        int k = 0;
        while (Math.pow(.5, k) > leastProb) k++;
        k--;
        while (k > 0) {
            res.append('0');
            k--;
        }
        return res.toString();
    }

    private static double binaryToDecimal(String binary, int len) {
        int point = binary.indexOf('.');
        if (point == -1)
            point = len;
        double intDecimal = 0,
                fracDecimal = 0,
                twos = 1;
        for (int i = point - 1; i >= 0; i--) {
            intDecimal += (binary.charAt(i) - '0') * twos;
            twos *= 2;
        }
        twos = 2;
        for (int i = point + 1; i < len; i++) {
            fracDecimal += (binary.charAt(i) - '0') / twos;
            twos *= 2.0;
        }
        return intDecimal + fracDecimal;
    }


    public static String decompress(String code, int len, Map<Character, ArrayList<Double>> ranges) {
        String res = "";
        ArrayList<Double> probabilities = new ArrayList<>();
        for (Map.Entry<Character, ArrayList<Double>> entry : ranges.entrySet())
            probabilities.add(entry.getValue().get(1) - entry.getValue().get(0));
        double leastProb = Collections.min(probabilities);
        int k = 0;
        while (Math.pow(.5, k) > leastProb) k++;
        String bin = "";
        for (int i = 0; i < k; i++) bin += code.charAt(i);
        double floatCode = binaryToDecimal(bin.toString(), bin.length());
        double low_range = 0, high_range = 0, temp, tempM, x, y, range;
        double low_rangeSym, high_rangeSym;
        int ind = 0;
        for (char ch : ranges.keySet()) {
            if (floatCode > ranges.get(ch).get(0) && floatCode < ranges.get(ch).get(1)) {
                res += ch;
                low_range = ranges.get(ch).get(0);
                high_range = ranges.get(ch).get(1);
                break;
            }
        }
        while (true) {
            if ((low_range < 0.5 && high_range < 0.5)) {
                low_range *= 2;
                high_range *= 2;
                ind++;
                continue;
            }
            if (low_range > 0.5 && high_range > 0.5) {
                low_range -= 0.5;
                low_range *= 2;
                high_range -= 0.5;
                high_range *= 2;
                ind++;
                continue;
            }
            break;
        }
        for (int r = 1; r < len; r++) {
            bin = "0.";
            for (int i = 2 + ind; i < k + ind + 2; i++) bin += code.charAt(i);
            floatCode = binaryToDecimal(bin.toString(), bin.length());
            floatCode = (floatCode - low_range) / (high_range - low_range);
            for (char ch : ranges.keySet()) {
                if (floatCode > ranges.get(ch).get(0) && floatCode < ranges.get(ch).get(1)) {
                    res += ch;
                    low_rangeSym = ranges.get(ch).get(0);
                    high_rangeSym = ranges.get(ch).get(1);
                    temp = low_range;
                    range = high_range - low_range;
                    tempM = range * low_rangeSym;
                    low_range += tempM;
                    tempM = range * high_rangeSym;
                    high_range = temp + tempM;
                    break;
                }
            }
            while (true) {
                if ((low_range < 0.5 && high_range < 0.5)) {
                    low_range *= 2;
                    high_range *= 2;
                    ind++;
                    continue;
                }
                if (low_range > 0.5 && high_range > 0.5) {
                    low_range -= 0.5;
                    low_range *= 2;
                    high_range -= 0.5;
                    high_range *= 2;
                    ind++;
                    continue;
                }
                break;
            }
        }
        return res;
    }


    //test compress
    /*public static String compress() {
        String str="ACBA";
        Map<Character, ArrayList<Double>> ranges = new HashMap<Character, ArrayList<Double>>();
        ArrayList<Double> arr1 = new ArrayList<>();
        arr1.add(0.0);
        arr1.add(0.8);
        ranges.put('A', arr1);
        ArrayList<Double> arr2 = new ArrayList<>();
        arr2.add(0.8);
        arr2.add(0.82);
        ranges.put('B', arr2);
        ArrayList<Double> arr3 = new ArrayList<>();
        arr3.add(0.82);
        arr3.add(1.0);
        ranges.put('C', arr3);
        StringBuilder res = new StringBuilder();
        double low_range, high_range, x;
        double low_range_curr, high_range_curr;
        char ch;
        ch = str.charAt(0);
        low_range = ranges.get(ch).get(0);
        high_range = ranges.get(ch).get(1);
        while ((low_range > 0.5 && high_range > 0.5) || (low_range < 0.5 && high_range < 0.5)) {
            if ((low_range > 0.5 && high_range > 0.5)) {
                low_range = (low_range - .5) * 2;
                high_range = (high_range - .5) * 2;
                res.append('1');
            } else {
                low_range *= 2;
                high_range *= 2;
                res.append('0');
            }
        }
        for (int i = 1; i < str.length(); i++) {
            ch = str.charAt(i);
            low_range_curr = ranges.get(ch).get(0);
            high_range_curr = ranges.get(ch).get(1);
            x = low_range;
            low_range = low_range + (high_range - low_range) * low_range_curr;
            high_range = x + (high_range - x) * high_range_curr;
            while ((low_range > 0.5 && high_range > 0.5) || (low_range < 0.5 && high_range < 0.5)) {
                if ((low_range > 0.5 && high_range > 0.5)) {
                    low_range = (low_range - .5) * 2;
                    high_range = (high_range - .5) * 2;
                    res.append('1');
                } else {
                    low_range *= 2;
                    high_range *= 2;
                    res.append('0');
                }
            }
        }
        res.append('1');
        double leastProb = 0.02;
        int k = 0;
        while (Math.pow(.5, k) > leastProb) k++;
        k--;
        while (k > 0){
            res.append('0');
            k--;
        }
        return res.toString();
    }*/
}
