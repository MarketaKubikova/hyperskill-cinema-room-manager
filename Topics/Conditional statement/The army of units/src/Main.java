import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfUnits = scanner.nextInt();
        String classification;

        if (numberOfUnits < 1) {
            classification = "no army";
        } else if (numberOfUnits <= 19) {
            classification = "pack";
        } else if (numberOfUnits <= 249) {
            classification = "throng";
        } else if (numberOfUnits <= 999) {
            classification = "zounds";
        } else {
            classification = "legion";
        }

        System.out.println(classification);
    }
}
