package _zadatak_vesalica_RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**4*/
/**Metode kojima server isporucuje podatke klijentu...*/
public interface Server extends Remote{

	public void posaljiKlijentu(String poruka) throws RemoteException;
}
