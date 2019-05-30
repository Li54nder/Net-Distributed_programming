package _zadatak_vesalica_RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**7*/
/**Izvrsavanje klijenta (Svaki klijent ima po jednu instancu za sebe)*/
public class KlijentImp extends UnicastRemoteObject implements Klijent {
	
	private static final long serialVersionUID = 1L;
	private  Server server;
	private  Prozor prozor;

	public KlijentImp(String nick, Pristup pristup) throws RemoteException{
		prozor = new Prozor(nick, this::postaServeru);
		this.server = pristup.prijava(nick, this);
	}

	public void start() {
		prozor.setVisible(true);
		postaServeru("s");
	}

	/**Otpremanje podataka na server*/
	public void postaServeru(String s) {
		try {
			if(s==null || s.length()==0) s = ":::";//vrv suvisno...
			server.posaljiKlijentu(s);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**Dopremanje podataka sa servera*/
	@Override
	public void primiPoruku(String poruka) throws RemoteException {
		prozor.prikazi(poruka);
	}

}
