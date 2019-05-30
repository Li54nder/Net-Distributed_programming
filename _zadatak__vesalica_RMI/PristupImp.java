package _zadatak_vesalica_RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
/**8*/
/**Moze uvek skoro isto (povezuje sve - spaja klijente i server)*/
public class PristupImp extends UnicastRemoteObject implements Pristup {
	
	private static final long serialVersionUID = 1L;
	static final String IME = "Vesalica";
	private Map<String, Klijent> klijenti = new HashMap<String, Klijent>();
	
	protected PristupImp() throws RemoteException {
		super();
	}

	
	@Override
	public Server prijava(String nick, Klijent klijent) throws RemoteException {
		Server s = new ServerImp(nick, klijenti);
		synchronized (klijenti) {
			klijenti.put(nick, klijent);
		}
		return s;
	}

}
