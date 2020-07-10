package pkgfinal.project;
import java.util.Scanner;

public class FinalProject {
    //runs the program
    public static void run(User user1){
        //defines scanners and variables
        Scanner scnr = new Scanner(System.in);
        boolean running = true;
        int attempts;
        
        //runs the program until 3 invalid login attempts, or the user chooses to quit.
        while(running){
            attempts = 0;           
            //gives the user 3 attempts to login
            while(attempts < 3){
                //asks for username and checks if the input is q to "Quit"
                System.out.println("Please enter username or enter q to quit the program:");
                user1.setUserName(scnr.next());
                if (user1.getUserName().equals("q")){
                    System.out.println("Quitting now....");
                    running = false;
                    break;
                }
                scnr.nextLine();
                //asks for password and saves the md5 encryption of the entered password to user1
                System.out.println("Please enter password:");
                user1.setPassword(scnr.nextLine());
                System.out.println("");

                //if correct credentials are inputted logs the user in and displays the proper message based on role
                if (user1.isAllowed()){
                    user1.loggedIn();
                    break;
                }        
                //if unknown credentials are entered itterates attempts and prints remaining attempts 
                //if 3 attempts have already been made notifies user and exits the program.
                else{
                    attempts++;
                    if (attempts == 3){
                        System.out.println("You have reached the maximum login attempts, Please try again later...");
                        running = false;
                    }
                    else{
                        System.out.println((3 - attempts) + " attempts remaining");
                    }
                }
            }
        }
    }
    //main method defines user1 and begins running the program
    public static void main(String[] args) {
        User user1 = new User();
        run(user1);
    }
}
