package _zadatak_chatApp_RMI;
/**8*/
/**Kod koji se vrti za svakog klijenta...*/
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class KlijentImp extends UnicastRemoteObject implements Klijent {
	
	private static final long serialVersionUID = 1L;
	private final Prozor prozor;
	private final Server server;

	public KlijentImp(String nick, Pristup p) throws RemoteException {
		prozor = new Prozor(nick, this::posaljiPoruku);
		server = p.prijava(nick, this);
	}

	@Override
	public void primiPorukuk(String nick, String poruka) throws RemoteException {
		prozor.prikazi(nick, poruka);
	}
	
	private void posaljiPoruku(String poruka) {
		try {
			server.posaljiPoruku(poruka);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		System.out.println("prikazivanje prozora");
		prozor.setVisible(true);
		try {
			unexportObject(this, true);
		} catch (NoSuchObjectException e) {
			// Vec smo odjavljeni
		}
	}

}
