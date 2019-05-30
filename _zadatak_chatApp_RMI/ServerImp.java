package _zadatak_chatApp_RMI;
/**5*/
/**Svaki klijent ima po jedan ovaj server koji se izvrsava za njega...*/
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class ServerImp extends UnicastRemoteObject implements Server {
	
	private static final long serialVersionUID = 1L;
	private final String nick;
	private final Map<String, Klijent> klijenti;
	
	public ServerImp(String nick, Map<String, Klijent> klijenti) throws RemoteException{
		this.nick = nick;
		this.klijenti = klijenti;
	}

	@Override
	public void posaljiPoruku(String poruka) throws RemoteException {
		synchronized (klijenti) {
			klijenti.values().forEach(x -> {
				try {
					x.primiPorukuk(nick, poruka);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			});
		}
	}

	@Override
	public void odjava() throws RemoteException {
		synchronized (klijenti) {
			klijenti.remove(nick);
		}
	}

}
