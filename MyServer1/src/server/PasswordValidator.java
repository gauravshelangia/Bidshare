package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class PasswordValidator {
	
	
	public boolean validate(String user, String pass)
	{
		FileReader file=null;
		try
		{
			file = new FileReader("passwords");
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		Scanner scn=new Scanner(file);
		while(scn.hasNext())
		{
			if(scn.next().compareTo(user)==0)
			{
				return (scn.next().compareTo(pass)==0);
			}
			else
			{
				scn.next();
			}
		}
		return false;
	}
	
}
