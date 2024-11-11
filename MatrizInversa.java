import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
public class MatrizInversa {

    public static BufferedReader bufer = new BufferedReader(new InputStreamReader(System.in));

    // Método para leer un número entero desde la consola
    public static int leerEntero() throws IOException {
        return Integer.parseInt(bufer.readLine());
    }

    // Método para leer una matriz cuadrada desde la consola
    public static double[][] leerMatriz(int filas, int columnas) throws IOException {
        double[][] matriz = new double[filas][columnas];
        System.out.println("Introduce los valores de la matriz:");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("Elemento [" + i + "][" + j + "]: ");
                matriz[i][j] = Double.parseDouble(bufer.readLine());
            }
        }
        return matriz;
    }

    // Método para calcular el determinante de una matriz
    public static double determinante(double[][] matriz) {
        int n = matriz.length;

        if (n == 1) {
            return matriz[0][0];
        }

        double det = 0;
        for (int j = 0; j < n; j++) {
            det += matriz[0][j] * cofactor(matriz, 0, j);
        }

        return det;
    }

    // Método para calcular el cofactor de un elemento
    public static double cofactor(double[][] matriz, int fila, int col) {
        double[][] submatriz = obtenerSubmatriz(matriz, fila, col);
        return Math.pow(-1, fila + col) * determinante(submatriz);
    }

    // Método para obtener la submatriz al eliminar una fila y una columna
    public static double[][] obtenerSubmatriz(double[][] matriz, int fila, int col) {
        int n = matriz.length;
        double[][] submatriz = new double[n - 1][n - 1];

        int sub_i = 0;
        for (int i = 0; i < n; i++) {
            if (i == fila) continue;
            int sub_j = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue;
                submatriz[sub_i][sub_j] = matriz[i][j];
                sub_j++;
            }
            sub_i++;
        }
        return submatriz;
    }

    // Método para calcular la matriz de cofactores de una matriz
    public static double[][] matrizDeCofactores(double[][] matriz) {
        int n = matriz.length;
        double[][] cofactores = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cofactores[i][j] = cofactor(matriz, i, j);
            }
        }

        return cofactores;
    }

    // Método para transponer una matriz
    public static double[][] transponerMatriz(double[][] matriz) {
        int n = matriz.length;
        double[][] transpuesta = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transpuesta[i][j] = matriz[j][i];
            }
        }

        return transpuesta;
    }

    // Método para calcular la inversa de una matriz
    public static double[][] inversaMatriz(double[][] matriz) {
        double determinante = determinante(matriz);

        if (determinante == 0) {
            throw new IllegalArgumentException("La matriz no tiene inversa (determinante = 0).");
        }

        // Matriz de cofactores
        double[][] cofactores = matrizDeCofactores(matriz);

        // Matriz adjunta (transpuesta de la matriz de cofactores)
        double[][] adjunta = transponerMatriz(cofactores);

        // Dividir cada elemento de la matriz adjunta por el determinante
        int n = matriz.length;
        double[][] inversa = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inversa[i][j] = adjunta[i][j] / determinante;
            }
        }

        return inversa;
    }

    // Método para imprimir una matriz
    public static void imprimirMatriz(double[][] matriz) {
        for (double[] fila : matriz) {
            for (double elemento : fila) {
                System.out.printf("%.2f ", elemento);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {

        // Leer dimensiones de la matriz
        System.out.print("Introduce el número de filas de la matriz: ");
        int filas = leerEntero();
        System.out.print("Introduce el número de columnas de la matriz: ");
        int columnas = leerEntero();

        // Verificar si la matriz es cuadrada
        if (filas != columnas) {
            System.out.println("Error: La matriz debe ser cuadrada para calcular su inversa.");
            return;
        }

        // Leer los valores de la matriz cuadrada
        double[][] matriz = leerMatriz(filas, columnas);

        // Imprimir la matriz original
        System.out.println("Matriz original:");
        imprimirMatriz(matriz);

        try {
            // Calcular la matriz inversa
            double[][] inversa = inversaMatriz(matriz);

            // Imprimir la matriz inversa
            System.out.println("\nMatriz inversa:");
            imprimirMatriz(inversa);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
