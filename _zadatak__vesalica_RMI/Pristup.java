package _zadatak_vesalica_RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**3*/
/**Uvek isto (povezuje sve - spaja klijente i server)*/
public interface Pristup extends Remote {

	static final String IME = "Vesalica";

	public Server prijava(String nick, Klijent klijent) throws RemoteException;
}
