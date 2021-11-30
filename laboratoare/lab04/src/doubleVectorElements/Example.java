package doubleVectorElements;

public class Example {
    public static void main(String[] args) {
        int[] v = new int[10];

        for (int i = 0; i < 10; i++) {
            v[i] = i;
        }

        modify_vector(v);

        String aux = "";
        for (int i = 0; i < 10; i++) {
            aux = aux + "" + i + " ";
        }
        aux += '\n';
        System.out.println(aux);
    }

    private static void modify_vector(int[] v) {
        for (int i = 0; i < 10; i++) {
            v[i] *= 2;
        }
    }
}
