package _zadatak_chatApp_RMI;
/**3*/
/**Pomoucu pristupa spajaju se klijent i server...( 'IME' )*/
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Pristup extends Remote {
	
	public static final String IME = "chat";
	
	//kako da znam da treba prijava...
	public Server prijava(String nick, Klijent klijent) throws RemoteException;
	
}
