
import java.io.*;
import java.util.*;

public class VectorQuantization {

    static boolean Compress(int vecH, int vecW, int codeBookSize, String Path) throws IOException {
        int[][] image = img.readImage(Path);
        int originalH = img.h;
        int originalW = img.w;
        int scaledH = originalH % vecH == 0 ? originalH : ((originalH / vecH) + 1) * vecH;
        int scaledW = originalW % vecW == 0 ? originalW : ((originalW / vecW) + 1) * vecW;
        int[][] scaledImg = new int[scaledH][scaledW];
        for (int i = 0; i < scaledH; i++) {
            int x = i >= originalH ? originalH - 1 : i;
            for (int j = 0; j < scaledW; j++) {
                int y = j >= originalW ? originalW - 1 : j;
                assert image != null;
                scaledImg[i][j] = image[x][y];
            }
        }
        Vector<Vector<Integer>> Vecs = new Vector<>();
        for (int i = 0; i < scaledH; i += vecH) {
            for (int j = 0; j < scaledW; j += vecW) {
                Vecs.add(new Vector<>());
                for (int x = i; x < i + vecH; x++) {
                    for (int y = j; y < j + vecW; y++) {
                        Vecs.lastElement().add(scaledImg[x][y]);
                    }
                }
            }
        }
        Vector<Vector<Integer>> Quant = new Vector<>();
        Quantize(codeBookSize, Vecs, Quant);
        Vector<Integer> VecsToQuantIndices = Optimize(Vecs, Quant);
        FileOutputStream fileOutputStream = new FileOutputStream(getCompressedPath(Path));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(originalW);
        objectOutputStream.writeObject(originalH);
        objectOutputStream.writeObject(scaledW);
        objectOutputStream.writeObject(scaledH);
        objectOutputStream.writeObject(vecW);
        objectOutputStream.writeObject(vecH);
        objectOutputStream.writeObject(VecsToQuantIndices);
        objectOutputStream.writeObject(Quant);
        objectOutputStream.close();
        return true;
    }

    static boolean Decompress(String Path) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(Path);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        int width = (int) input.readObject();
        int height = (int) input.readObject();
        int scaledW = (int) input.readObject();
        int scaledH = (int) input.readObject();
        int vecW = (int) input.readObject();
        int vecH = (int) input.readObject();
        Vector<Integer> VecsToOptimizeIndices = (Vector<Integer>) input.readObject();
        Vector<Vector<Integer>> Quant = (Vector<Vector<Integer>>) input.readObject();
        int[][] newImg = new int[scaledH][scaledW];
        for (int i = 0; i < VecsToOptimizeIndices.size(); i++) {
            int x = i / (scaledW / vecW);
            int y = i % (scaledW / vecW);
            x *= vecH;
            y *= vecW;
            int v = 0;
            for (int j = x; j < x + vecH; j++) {
                for (int k = y; k < y + vecW; k++) {
                    newImg[j][k] = Quant.get(VecsToOptimizeIndices.get(i)).get(v++);
                }
            }
        }
        img.writeImage(newImg, width, height, getDecompressedPath(Path));
        return true;
    }

    private static Vector<Integer> vectorAverage(Vector<Vector<Integer>> Vecs) {
        int[] summation = new int[Vecs.get(0).size()];
        for (Vector<Integer> vector : Vecs)
            for (int i = 0; i < vector.size(); i++)
                summation[i] += vector.get(i);
        Vector<Integer> returnVector = new Vector<>();
        for (int i = 0; i < summation.length; i++)
            returnVector.add(summation[i] / Vecs.size());
        return returnVector;
    }

    private static int EuclidDis(Vector<Integer> x, Vector<Integer> y, int incrementFactor) {
        int distance = 0;
        for (int i = 0; i < x.size(); i++)
            distance += Math.pow(x.get(i) - y.get(i) + incrementFactor, 2);
        return (int) Math.sqrt(distance);
    }

    private static void Quantize(int Level, Vector<Vector<Integer>> Vecs, Vector<Vector<Integer>> Quant) {
        if (Level == 1 || Vecs.size() == 0) {
            if (Vecs.size() > 0)
                Quant.add(vectorAverage(Vecs));
            return;
        }
        Vector<Vector<Integer>> leftVecs = new Vector<>();
        Vector<Vector<Integer>> rightVecs = new Vector<>();
        Vector<Integer> mean = vectorAverage(Vecs);
        for (Vector<Integer> vec : Vecs) {
            int eDistance1 = EuclidDis(vec, mean, 1);
            int eDistance2 = EuclidDis(vec, mean, -1);
            if (eDistance1 >= eDistance2)
                leftVecs.add(vec);
            else
                rightVecs.add(vec);
        }
        Quantize(Level / 2, leftVecs, Quant);
        Quantize(Level / 2, rightVecs, Quant);
    }

    private static Vector<Integer> Optimize(Vector<Vector<Integer>> Vecs, Vector<Vector<Integer>> Quant) {
        Vector<Integer> VecsToQuantIndices = new Vector<>();
        for (Vector<Integer> vector : Vecs) {
            int smallestDistance = EuclidDis(vector, Quant.get(0));
            int smallestIndex = 0;
            for (int i = 1; i < Quant.size(); i++) {
                int tempDistance = EuclidDis(vector, Quant.get(i));
                if (tempDistance < smallestDistance) {
                    smallestDistance = tempDistance;
                    smallestIndex = i;
                }
            }
            VecsToQuantIndices.add(smallestIndex);
        }
        return VecsToQuantIndices;
    }

    static String getCompressedPath(String path) {

        return path.substring(0, path.lastIndexOf('.')) + ".VQ";
    }

    static String getDecompressedPath(String path) {
        return path.substring(0, path.lastIndexOf('.')) + "***compressed version.jpg";
    }

    private static int EuclidDis(Vector<Integer> x, Vector<Integer> y) {
        return EuclidDis(x, y, 0);
    }
}