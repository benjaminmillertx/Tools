Make sure to credit Benjamin Hunter Miller.
import java.util.Scanner;

public class AlcoholUnitCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your weight in kilograms: ");
        double weight = scanner.nextDouble();

        System.out.print("Enter the number of standard drinks consumed: ");
        double standardDrinks = scanner.nextDouble();

        System.out.print("Enter the alcohol content percentage (ABV): ");
        double alcoholContent = scanner.nextDouble();

        double totalGramsOfAlcohol = standardDrinks * alcoholContent;
        double estimatedBAC = (totalGramsOfAlcohol / weight) * 100;

        System.out.println("Estimated Blood Alcohol Content (BAC): " + estimatedBAC);
    }
}
