package pkgfinal.project;
    
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class User {
    //set and get methods for user class
    private String userName;
    private String role;
    private String password;
    
    public User(){
        password = "";
        userName = "";
        role = "";
    }    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setPassword(String password){
        MD5(password);
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    public String getRole(){
        return role;
    }
    
    //encrypts the entered password with an md5 hash algorithm then saves the hashed password
    public void MD5(String a) {
        String original = a;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
             StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            password = sb.toString();
        }
        catch (NoSuchAlgorithmException ex){
            System.out.println("ERROR..." + ex);
        }
    }
    
    // checks the inputted credentials against the credentials stored in credentials .txt 
    //returns a boolean that indicates if the credentials match or not.
    public boolean isAllowed(){
        //Creates variables for isAllowed()
        String[] currentLine = new String[4];
        String temp = "";
        int wordCount = 0;
        int y = 0;
        boolean correctUser = false;
        boolean correctPass = false;
        try{
            //attempts to open the file
            FileInputStream file = new FileInputStream("credentials.txt");
            Scanner credentials = new Scanner(file);
            while(credentials.hasNext()){
                String b = credentials.next();
                //checks if the current string read by scanner contains a " if it does 
                //it saves it to a temp string to be combined with the last "
                if(b.contains("\"")){
                    if (wordCount == 0 && b.endsWith("\"")){
                        temp = b;
                        currentLine[y] = temp;
                        wordCount++;
                        y++;
                        continue;
                    }
                    else if(wordCount == 0){
                        temp = b;
                        wordCount++;
                        continue;
                    }
                    else{
                        temp = temp + " " + b;
                        wordCount++;
                        currentLine[y] = temp;
                        y++;
                        continue;
                    }
                }
                //if the string does not contain " then it saves the value to an array
                else{
                    temp = "";
                    wordCount = 0;
                    currentLine[y] = b;
                    y++;
                }
                //if y = 4 then it sets y to 0 and checks if the inputted username and password match the current line
                if(y == 4){
                    y = 0;
                    if(getUserName().equals(currentLine[0]) && getPassword().equals(currentLine[1])){
                        correctUser = true;
                        correctPass = true;
                    }
                    else{
                        correctUser = false;
                        correctPass = false;
                    }
                    
                }
                //if username and password match sets the correct role of the user and returns true
                if(correctUser && correctPass){
                    setRole(currentLine[3]);
                    return true;
                }
            }  
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        return false;
        
    }
    
    //if the user is logged in displays the proper message based on user role and stays there until 
    //the user presses x to logout
    public void loggedIn(){
        try{
            Scanner scnr = new Scanner(System.in);
            FileInputStream roleFile = new FileInputStream(role + ".txt");
            Scanner rolePrint = new Scanner(roleFile);
            System.out.println("Successfully logged in as: " + getUserName());
            System.out.println("");
            while(rolePrint.hasNextLine()){
                System.out.println(rolePrint.nextLine());
            }
            System.out.println("");
            System.out.println("enter 'x' to log out");
            while(true){
                String enteredVal = scnr.next();
                if(enteredVal.equals("x")){
                    System.out.println("Logging out...\n");
                    break;
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e);    
        }
    }
    
}
