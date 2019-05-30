package _zadatak_vesalica_SOCKET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
/**Za svakog klijenta ovde se odvaja poseban tred 
 * koji ce komunicirati sa njim da bi ostali nesmetano mogli da se nakace na server*/
public class Tmp {
	
	private Map<String, Integer> rezultati;
	
	private Socket s;
	private String rec;
	private BufferedReader in;
	private PrintWriter out;
	private String nick;
	
	/**Konstruktor koji kroz prosledjen 'socket' pravi 'reader' i 'writer'*/
	public Tmp(String rec, Socket s, Map<String, Integer> r) throws IOException {
		this.rec = rec;
		this.s = s;
		rezultati = r;
		
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
		
		out.println(String.valueOf(rec));
	}
	
	/**Kod ovog metoda se izvrsava kada se nad tredom pozove '.start()'*/
	public void run() { //metod obradjuje dobijene podatke i vraca rezultat
		try {
			nick = in.readLine();
			String newLbl;
			String newBr;
			boolean resenaRec = false;
			boolean izgubio = false;
			do {
				String data = in.readLine();
				String tmp[] = data.split(":");
				String slovo = tmp[0];
				String oldLbl = tmp[1];
				String oldBr = tmp[2];
				newLbl = oldLbl;
				newBr = oldBr;
				
				if(rec.contains(slovo)) {
					List<Integer> pozicije = new ArrayList<>();
					for(int i = 0; i < rec.length(); i++) {
						if(rec.charAt(i) == slovo.charAt(0)) {
							pozicije.add(i);
						}
					}
					StringBuilder sb = new StringBuilder();
					sb.append(oldLbl);
					for(int i = 0; i < rec.length(); i++) {
						if(pozicije.contains(i)) {
							sb.setCharAt(i, slovo.charAt(0));
						}
					}
					newLbl = sb.toString();
					if(rec.toLowerCase().equals(newLbl.toLowerCase())) {
						resenaRec = true;
					}
				} else {
					int br = Integer.parseInt(oldBr);
					if(br == 1) {
						izgubio = true;
					} else {
						br--;
						newBr = br+"";
					}
				}
				out.println(newLbl + ":" + (resenaRec? "K" : "N") + ":" + (izgubio? "K" : "N") + ":" + newBr);
				
			} while(!resenaRec && !izgubio);
			
			int bodovi = newLbl.length() * Integer.parseInt(newBr);
			
			rezultati.put(nick, bodovi);
			System.out.println(nick +" ("+ bodovi+ "bodova) je resio rec!");
		} catch (IOException e) {
			e.printStackTrace();			
		} finally {
			try {
				out.close();
				in.close();
				s.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
