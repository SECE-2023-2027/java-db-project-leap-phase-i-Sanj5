
import java.util.*;

public class voting {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = sc.next();
        System.out.println("Enter age");
        int age = sc.nextInt();

        if(age >= 18)
        {
            System.out.println(name + "!You are eligible to vote");
            if(age > 50){
                System.out.println(name + ", You are a senior citizen");
            }
        }
        else{
            System.out.println(name + "!You are not eligible to vote");
        }
    }
}
