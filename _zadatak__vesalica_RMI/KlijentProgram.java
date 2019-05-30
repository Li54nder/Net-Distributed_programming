package _zadatak_vesalica_RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
/**2*/
/**Glavni program za klijenta... (uvek isto)*/
public class KlijentProgram {
	
	static Scanner sc = new Scanner(System.in);

	/**Obicno bude uvek isto*/
	public static void main(String[] args) {
		
		String nick = ucitajNick();
		try {
			Registry reg = LocateRegistry.getRegistry(1234);
			Pristup pristup = (Pristup) reg.lookup(Pristup.IME);
			KlijentImp klijent = new KlijentImp(nick, pristup);
			System.err.println("Klijent " + nick + " povezan na server");
			
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
