import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        int x;
        System.out.println("Welcome to Fed Simulator!");
        System.out.println("______________________________");
        do {
            x = menu();
            if (x ==1){
                System.out.println("Start Game!");
                game();
            }
            else if (x == 2){
                rules();
            }
            else if (x == 3){
                credits();
            }
        }
            while (x != 0);

            System.out.println("Thanks for playing!");
        }

    public static int menu(){
        int x;
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Select Next Choice!");
            System.out.println("______________________________");
            System.out.println("[1]: Start a new game?");
            System.out.println("[2]: Rules");
            System.out.println("[3]: Credits");
            System.out.println("[0]: Quit");
            System.out.println("______________________________");
            System.out.print("Enter number: ");
            x = in.nextInt();
            if (x < 0 || x > 3){
                throw new RuntimeException();
            }
            return x;

        }
        catch (Exception e){
            System.out.println("Enter proper input please!");
            System.out.println();
            x = menu();
            return x;

        }
     }
    public static void rules(){
        System.out.println("Rules of Fed Sim");
        System.out.println("Take the role of the Fed in the US economy!");
        System.out.println("Get a scenario every fiscal quarter that effects the stability of the country.");
        System.out.println("You have a chance to react to each scenario with one of nine options!");
        System.out.println("Make it to 2033 or get fired if stability reaches below 0!");



    }
    public static void credits(){
        System.out.println("Credits");
        System.out.println("______________________________");
        System.out.println("Created by: Karan Rawat");
    }

    public static void game(){
        Environment g = new Environment();
        boolean running = true;
        while (running){
            running = g.nextQuarter(); //goes to next quarter, where one scenario happens
        }
        System.out.println("Game Over!");
        if (g.getStability().get() > 0 && g.getCurrentYear() > g.getEndYear()){
            System.out.println("______________________________");
            System.out.println("You win!");
            System.out.println("Congrats on making it to 2033!");
            System.out.println("______________________________");

            System.out.println();


        }
        else {
            System.out.println("______________________________");
            System.out.println("You have been fired for ruining the economy!");
            System.out.println("At least you made it to " +g.getCurrentYear() + "!");
            System.out.println("Hopefully someone else takes over...");
            System.out.println("______________________________");
            System.out.println();


        }
    }





}
