public class Main {

    public static void main(String[] args) {
        String word = "AAABBCAAAAAAA";
        double val = FloatArithmeticCoding.compress(word);
        System.out.println("float code:   " + val);
        System.out.print("decompressed string:   ");
        System.out.println(FloatArithmeticCoding.deCompress(val, word.length(), FloatArithmeticCoding.generateRanges(word)));
    }
}
