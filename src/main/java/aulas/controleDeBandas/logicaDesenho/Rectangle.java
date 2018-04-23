package aulas.controleDeBandas.logicaDesenho;

public class Rectangle {

    public static void main(String[] args) {
        int n = 10;

        for (int i = 0; i <= n+2; i++) {
            System.out.print(". ");
        }
        System.out.println();
        for (int i = 0; i <= n; i++) {
            System.out.print(". ");
            for (int i2 = 0; i2 <= n; i2++) {
                System.out.print("* ");
            }
            System.out.print(". ");

            System.out.println();
        }
        for (int i = 0; i <= n+2; i++) {
            System.out.print(". ");
        }
    }
}