package _zadatak_vesalica_RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**6*/
/**Metode koje se izvrsavaju za klijenta (dopremaju mu podatke i komande sa servera)...*/
public interface Klijent extends Remote {
	
	public void primiPoruku(String poruka) throws RemoteException;
	
	public void start() throws RemoteException;
	
}
