import java.io.*;

public class Nuke2 {
	
	public static void main(String[] args) throws Exception{
		BufferedReader keyboard;
		keyboard = new BufferedReader (new InputStreamReader (System.in));
		String inputline = keyboard.readLine();
		System.out.print(inputline.charAt(0));
		for(int i =2; i<inputline.length(); i++) {
			System.out.print(inputline.charAt(i));
		}
		
	}

}
