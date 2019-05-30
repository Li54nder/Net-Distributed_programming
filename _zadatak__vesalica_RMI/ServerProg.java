package _zadatak_vesalica_RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**9*/
/**Glavni program servera... (uvek isto)*/
public class ServerProg {

	public static void main(String[] args) {
		try {
			PristupImp pristup = new PristupImp();
			Registry reg = LocateRegistry.createRegistry(1234);
			reg.rebind(Pristup.IME, pristup);
			System.err.println("Server pokrenut");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
