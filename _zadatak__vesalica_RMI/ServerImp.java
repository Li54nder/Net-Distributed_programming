package _zadatak_vesalica_RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**5*/
/**Izvrsavanje servera (Svaki klijent ima server za sebe)*/
public class ServerImp extends UnicastRemoteObject implements Server {
	
	/**Cuva nick za svakog klijenta i mapu ostalih korisnika*/
	private static final long serialVersionUID = 1L;
	private final String nick;
	private final String rec;
	private final Map<String, Klijent> klijenti;

	protected ServerImp(String nick, Map<String, Klijent> klijenti) throws RemoteException {
		super();
		this.nick = nick;
		this.klijenti = klijenti;
		this.rec = generateRec();
	}

	private String generateRec() throws RemoteException {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        StringBuilder sb = new StringBuilder(5); 
        for (int i = 0; i < 5; i++) { 
            int index = (int)(AlphaNumericString.length() * Math.random());  
            sb.append(AlphaNumericString.charAt(index)); 
        }   
        System.out.println(sb);
        return sb.toString(); 
    }

	/**Podaci za slanje klijentu(obrada 'poruke' i slanje nove 'klijent.primiPoruku(newPoruka)')...*/
	@Override
	public void posaljiKlijentu(String poruka) throws RemoteException {
		if("s".equals(poruka)) {
			poruka = rec.replaceAll("(.)", "-") + ":" + "N" + ":" + "N" + ":" + 6;
			synchronized (klijenti) {
				for(Entry<String, Klijent> e : klijenti.entrySet()) {
					if(e.getKey().equals(nick))
						e.getValue().primiPoruku(poruka);
				}
			}
			return;
		}
			
		String data = poruka;
		String tmp[] = data.split(":");
		String slovo = tmp[0];
		String oldLbl = tmp[1];
		String oldBr = tmp[2];
		String newLbl = oldLbl;
		String newBr = oldBr;
		boolean resenaRec = false;
		boolean izgubio = false;
		
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
		poruka = newLbl + ":" + (resenaRec? "K" : "N") + ":" + (izgubio? "K" : "N") + ":" + newBr;
		synchronized (klijenti) {
			for(Entry<String, Klijent> e : klijenti.entrySet()) {
				if(e.getKey().equals(nick))
					e.getValue().primiPoruku(poruka);
			}
		}
	}

	
}
