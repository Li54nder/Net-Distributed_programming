package _zadatak_chatApp_RMI;
/**4*/
/**Metode neophodne za rad servera...*/
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
	
	//Kad se dodaju metode...
	public void posaljiPoruku(String poruka) throws RemoteException;
	
	public void odjava() throws RemoteException;

}
