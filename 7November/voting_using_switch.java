
import java.util.*;
public class voting_using_switch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = sc.next();
        System.out.println("Enter age");
        int age = sc.nextInt();
        int ch;
        if(age >= 18)
        {
            ch = 1;
        }
        else if(age > 50)
        {
            ch = 2;
        }
        switch(age)
        {
            
            case 1:
            System.out.println(name + "! You are eligible to vote");
            break;

            case 2:
            System.out.println(name + "! You are eligible to vote");
            System.out.println(name + "! You are a senior citizen");
            break;

            default:
            System.out.println("Not eligible to vote");
        }
    }
}
