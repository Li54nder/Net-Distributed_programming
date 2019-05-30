package _zadatak_vesalica_SOCKET;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
/**Glavna klasa za serversku stranu...*/
public class Server {
	
	private static Map<String, Integer> rezultati;
	
	public static void main(String[] args) {
		
		try {
			/**Podesavanje 'ServerSocketa' na neki port*/
			ServerSocket ss = new ServerSocket(1234);
			rezultati = new TreeMap<>();
			System.err.println("Server pokrenut");
			
			/**Petlja koja omogucava serveru da konstantno radi i da prihvata nove klijente (kraz ss.accept())*/
			while (!Thread.interrupted()) {
				Socket s = ss.accept();
				System.err.println("Konenktovan novi korisnik");
				
				String pom;
				Tmp tmp = new Tmp(pom = getRandomString(5), s, rezultati);
				System.out.println(pom);
				
				/**Za svakog klijenta ovde se odvaja poseban tred 
				 * koji ce komunicirati sa njim da bi ostali nesmetano mogli da se nakace na server*/
				new Thread(tmp::run).start();
				
			}
			ss.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getRandomString(int n) { 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        StringBuilder sb = new StringBuilder(n); 
        for (int i = 0; i < n; i++) { 
            int index = (int)(AlphaNumericString.length() * Math.random());  
            sb.append(AlphaNumericString.charAt(index)); 
        }   
        return sb.toString(); 
    }

}
