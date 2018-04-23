package aulas.controleDeBandas.logicaDesenho;

public class Triangle {

    public static void main(String[] args) {
        int n = 10;

        // loop n times, one for each row
        for (int i = 0; i < n; i++) {

            // print j periods
            for (int j = 0; j < i; j++)
                System.out.print(". ");

            // print n-i asterisks
            for (int j = 0; j < n - i; j++)
                System.out.print("* ");

            // print a new line
            System.out.println();
        }
    }
}