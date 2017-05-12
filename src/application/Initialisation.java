package application;

import java.io.IOException;

public class Initialisation extends Thread{
	
	public void run() {
		try {
			Launcher.initDocuments();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  } 
}
