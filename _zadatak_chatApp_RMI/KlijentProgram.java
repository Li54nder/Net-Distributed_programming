package _zadatak_chatApp_RMI;
/**2*/
/**Glavni program koji se izvrsava na klijentovoj strani...*/
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class KlijentProgram {
	
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		String nick = ucitajNick();
		
		try {
			System.err.println("Uspostavljanje veze");
			Registry reg = LocateRegistry.getRegistry("localhost", 1234);
			Pristup pristup = (Pristup) reg.lookup(Pristup.IME);
			KlijentImp klijent = new KlijentImp(nick, pristup);
			System.err.println("Veza sa serverom je uspostavljena");
			klijent.start();
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private static String ucitajNick() {
		System.out.print("NICK: ");
		return sc.nextLine();
	}
}
