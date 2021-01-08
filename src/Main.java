import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //FloatArithmeticCoding
        //AAABBCAAAAAAA
        //0.41400410346304545
        /*Scanner sc= new Scanner(System.in);
        String str= sc.nextLine(); //reads string.
        double val = FloatArithmeticCoding.compress(str);
        System.out.println("float code:   " + val);
        System.out.print("decompressed string:   ");
        System.out.println(FloatArithmeticCoding.deCompress(val, str.length(),
         FloatArithmeticCoding.generateRanges(str)));*/


        //Huffman
        //a 5 b 9 c 12 d 13 e 16 f 45
        //f 0 c 100 d 101 a 1100 b 1101 e 111
        /*Scanner sc = new Scanner(System.in);
        ArrayList<Character> arr = new ArrayList<>();
        ArrayList<Integer> freq = new ArrayList<>();
        System.out.println("How many charaters?");
        int n;
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            char c = sc.next().charAt(0);
            int x = sc.nextInt();
            arr.add(c);
            freq.add(x);
        }
        Huffman.compress(arr,freq);*/
    }
}
