import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
public class MainClass {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		switch (2) {
		case 1: {
			 GetInput gi = new GetInput();
			//RandomInput gi = new RandomInput();
			try {
				gi.runTest();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			break;
		case 2: {
			//System.out.println("Enter recipes:");
			String line;
		    ArrayList<String> testinput=new ArrayList<String>();
			try{
		    /*while(sc.hasNextLine())
		        {
				 String input = sc.nextLine();
				 testinput.add(input);
		        }*/
			//String s=sc.nextLine();
		    //testinput.add(s);
BufferedReader in1 = new BufferedReader(new FileReader("testFile.txt"));

		while ((line = in1.readLine()) != null) {

			testinput.add(line);

		}
			GetTestInput gti = new GetTestInput();
			
				if (testinput.size() == 0) {
					System.out.println("End of input");
					break;
				} else
					gti.runTest(testinput);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			sc.close();

		}
	}

}
