import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Enter \"exit\" to exit");
        System.out.println("Enter \"cheat\" to cheat");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter p: ");
        int p = Integer.parseInt(reader.readLine());
        System.out.print("Enter q: ");
        int q = Integer.parseInt(reader.readLine());
        System.out.print("Enter no. of mines: ");
        int mines = Integer.parseInt(reader.readLine());

        Game game = new Game(p, q, mines);

        while (true) {

            game.display();

            System.out.print("(p q F/C) > ");
            String input = reader.readLine();

            int inputP, inputQ;
            String operation;

            if (input.equals("cheat")) {
                game.displayLost();
                System.out.println();
                continue;
            } else if (input.equals("exit")) {
                System.exit(0);
            }

            try {
                inputP = Integer.parseInt(input.split(" ")[0]);
                inputQ = Integer.parseInt(input.split(" ")[1]);
                operation = input.split(" ")[2];
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Invalid input");
                continue;
            }

            switch (operation) {
                case "C":
                    int result = game.clickTile(inputP, inputQ);
                    switch (result) {
                        case Game.INVALID_POSITION -> System.out.println("Invalid position");
                        case Game.ALREADY_CLICKED -> System.out.println("Already clicked position");
                        case Game.PLAYER_WON -> {
                            game.display();
                            System.out.println("You have won!");
                            System.out.print("Press enter to exit...");
                            reader.readLine();
                            System.exit(0);
                        }
                        case Game.PLAYER_LOST -> {
                            game.displayLost();
                            System.out.println("You have lost!");
                            System.out.print("Press enter to exit...");
                            reader.readLine();
                            System.exit(0);
                        }
                        case Game.ALREADY_FLAGGED -> System.out.println("Tile has been flagged");
                    }
                    break;
                case "F":
                    result = game.flag(inputP, inputQ);
                    switch (result) {
                        case Game.ALREADY_CLICKED -> System.out.println("Already clicked");
                        case Game.INVALID_POSITION -> System.out.println("Invalid position");
                    }
            }
        }
    }
}
