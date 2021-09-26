package GOL;

import java.util.*;

/**
 * Driver class that allows the user to choose between the square or hexagonal GOL
 */
public class MainGOL
{
	/**
	 * Allows the user to choose between the square or hexagonal GOL
	 */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The Game of Life continues indefinitely");
        System.out.println("To stop it, the program can be terminated at any time");
        System.out.println();
        System.out.println("Enter 1 for square game or enter 2 for hexagon game");
        int input = scanner.nextInt();
        while (!(input ==1 || input ==2))
        {
            System.out.println("Please choose 1 or 2");
            input = scanner.nextInt();
        }
        if (input == 1)
        {
            System.out.println("Square game selected");
            SquareGOL.main(null);
        }
        if (input == 2)
        {
            System.out.println("Hexagon game selected");
            HexagonGOL.main(null);
        }
    }
}



