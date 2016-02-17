import java.util.Scanner;
public class testin {
	public static void main(String[] args)
    {   
        String line;
        Scanner stdin = new Scanner(System.in);
        while(stdin.hasNextLine() && !( line = stdin.nextLine() ).equals( "" ))
        {
            String[] tokens = line.split(" ");
            System.out.println(Integer.parseInt(tokens[1]));
        }
        stdin.close();
    }
}


