import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {



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






        //BinaryArithmeticCoding
        /*Map<Character,ArrayList<Double>> m = new HashMap<>();
        ArrayList<Double>a1=new ArrayList<>();
        a1.add(0.0);
        a1.add(0.8);
        m.put('A',a1);
        ArrayList<Double>a2=new ArrayList<>();
        a2.add(0.8);
        a2.add(0.82);
        m.put('B',a2);
        ArrayList<Double>a3=new ArrayList<>();
        a3.add(0.82);
        a3.add(1.00);
        m.put('C',a3);
        //0.110001100000
        String floatCode =BinaryArithmeticCoding.compress("ACBA",m);
        System.out.println(floatCode);
        //ACBA
        System.out.println(BinaryArithmeticCoding.decompress(floatCode,4,m));*/






        //vector quantization
        /*int numOfVectors = (img.h * img.w)/(16);
        int codeBlockSize = 32*4*4*5;
        if(  VectorQuantization.Compress(4,4,codeBlockSize,"/home/marwan/Desktop/photo2.jpg/"))
            System.out.println("photo is compressed");
        String photo = VectorQuantization.getCompressedPath("/home/marwan/Desktop/photo2.jpg/");
        if( VectorQuantization.Decompress(photo))
            System.out.println("photo is decompressed");*/
    }
}
