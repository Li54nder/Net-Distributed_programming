package _zadatak_chatApp_SOCKET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
/**3*/
public class ServerTmp {
	
	private BufferedReader in;
	private PrintWriter out;
	private String nick;
	
	String zaSlanje = "";
	
	private Map<String, ServerTmp> korisnici;
	
	public ServerTmp(Socket s, Map<String, ServerTmp> korisnici) throws IOException {
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
		this.korisnici = korisnici;
	}
	
	/**Kod metode koji se izvrsava pokretanjem naredbe .start() nad svakim novim tredom...
	 * (Citanje novih poruka i njihovo slanje)*/
	public void run() {
		try {
			nick = in.readLine();
			synchronized (korisnici) {
				korisnici.put(nick, this);
			}
			System.err.println(nick);
			
			while(true) {
				String poruka;
				try {
					poruka = in.readLine();
					zaSlanje = poruka;
					
					korisnici.values().stream().forEach(x -> x.out.println(zaSlanje));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
