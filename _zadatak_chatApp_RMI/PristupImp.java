package _zadatak_chatApp_RMI;
/**6*/
/**Pomoucu pristupa spajaju se klijent i server...*/
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

//Obratiti paznju na one koje treba da poseduju UnicastRemote...
public class PristupImp extends UnicastRemoteObject implements Pristup {

	private static final long serialVersionUID = 1L;

	protected PristupImp() throws RemoteException {
		super();
	}

	//kako znati da treba mapa bas ovde...
	Map<String, Klijent> klijenti = new HashMap<String, Klijent>();

	@Override
	public Server prijava(String nick, Klijent klijent) throws RemoteException {
		Server server = new ServerImp(nick, klijenti);
		synchronized (klijenti) {
			klijenti.put(nick, klijent);
		}
		return server;
	}

}
