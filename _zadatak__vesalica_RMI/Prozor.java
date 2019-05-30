package _zadatak_vesalica_RMI;

import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**1*/
/**Prozor za svakog novog klijenta*/
public class Prozor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfPokusaj;
	
	JLabel lblBrZivota;
	JLabel lblRec;


	public Prozor(String naslov, Consumer<String> akcija) {
		setTitle(naslov);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl1 = new JLabel("Broj Zivota:");
		lbl1.setBounds(10, 27, 214, 14);
		contentPane.add(lbl1);
		
		lblBrZivota = new JLabel("5");
		lblBrZivota.setBounds(27, 52, 197, 14);
		contentPane.add(lblBrZivota);
		
		JLabel lbl2 = new JLabel("Rec za pogadjanje:");
		lbl2.setBounds(10, 102, 214, 14);
		contentPane.add(lbl2);
		
		lblRec = new JLabel("");
		lblRec.setBounds(27, 127, 197, 14);
		contentPane.add(lblRec);
		
		JLabel lbl3 = new JLabel("Pokusaj:");
		lbl3.setBounds(10, 182, 214, 14);
		contentPane.add(lbl3);
		
		tfPokusaj = new JTextField();
		tfPokusaj.setBounds(58, 207, 122, 20);
		contentPane.add(tfPokusaj);
		tfPokusaj.setColumns(10);
		
		/**Klijent salje podatke serveru*/
		JButton btnPokusaj = new JButton("Pokusaj");
		btnPokusaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = tfPokusaj.getText().toUpperCase();
				char slovo = str.charAt(0);
				String lbl = lblRec.getText();
				String br = lblBrZivota.getText();
				String data = slovo + ":" + lbl + ":" + br;
				tfPokusaj.setText("");
				akcija.accept(data);
			}
		});
		btnPokusaj.setBounds(68, 238, 99, 47);
		contentPane.add(btnPokusaj);
	}
	
	/**Server pravi izmene na klijentu...*/
	public void prikazi(String poruka) {//PORUKA = newLbl + ":" + (resenaRec? "K" : "N") + ":" + (izgubio? "K" : "N") + ":" + newBr
		SwingUtilities.invokeLater(() -> {
			String podaci[] = poruka.split(":");
			String newLbl = podaci[0];
			String newBr = podaci[3];
			boolean pobedio = podaci[1].equals("K")? true : false;
			boolean izgubio = podaci[2].equals("K")? true : false;
			
			lblBrZivota.setText(newBr);
			lblRec.setText(newLbl);

			if(pobedio) {
				JOptionPane.showMessageDialog(null, "Pogodili ste rec!", "Info", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			} else if(izgubio) {
				JOptionPane.showMessageDialog(null, "Izgubili ste!", "Info", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			}
		});
	}
}
