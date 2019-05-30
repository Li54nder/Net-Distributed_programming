package _zadatak_chatApp_SOCKET;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
/**2*/
public class Server {
	
	static Map<String, ServerTmp> korisnici;

	public static void main(String[] args) {
		
		try {
			
			ServerSocket ss = new ServerSocket(1234);
			korisnici = new HashMap<String, ServerTmp>();
			while(true) {
				Socket s = ss.accept();
				System.err.println("Zakacen novi klijent");
				
				ServerTmp tmp = new ServerTmp(s, korisnici);
				
				/**Za svakog novonakacenog korisnika odvaja se novi tred koji ce da se bavi tekucim korisnikom
				 * oslobadjajuci MAIN tred da prihvata nove korisnike...*/
				new Thread(tmp::run).start();
			}		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
