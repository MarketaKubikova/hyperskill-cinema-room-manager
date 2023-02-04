import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String shape = scanner.next();
        double area = 0.0;

        switch (shape) {
            case "triangle":
                area = getTriangleArea(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
                break;
            case "rectangle":
                area = getRectangleArea(scanner.nextDouble(), scanner.nextDouble());
                break;
            case "circle":
                area = getCircleArea(scanner.nextDouble());
                break;
        }

        System.out.print(area);
    }

    private static double getTriangleArea(double a, double b, double c) {
        double s = (a + b + c) / 2;

        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    private static double getRectangleArea(double a, double b) {
        return a * b;
    }

    private static double getCircleArea(double a) {
        final double pi = 3.14;

        return pi * Math.pow(a, 2);
    }
}
