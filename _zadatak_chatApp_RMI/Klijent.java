package _zadatak_chatApp_RMI;
/**7*/
/**Metode neophodne za klijenta*/
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Klijent extends Remote {
	
	public void primiPorukuk(String nick, String poruka) throws RemoteException;

}
