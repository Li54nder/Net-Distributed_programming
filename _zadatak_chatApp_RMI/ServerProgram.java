package _zadatak_chatApp_RMI;
/**9*/
/**Uvek isto...*/
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerProgram {

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
