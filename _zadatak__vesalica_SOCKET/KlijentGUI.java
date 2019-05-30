package _zadatak_vesalica_SOCKET;

import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
/**Glavna klasa za klijentsku stranu...*/
public class KlijentGUI {
	
	private String nick;
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	private String trazenaRec;
	

	private JFrame frame;
	private JTextField tfPokusaj;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KlijentGUI window = new KlijentGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public KlijentGUI() {
		initialize();
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 265);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblKonekcija = new JLabel("");
		lblKonekcija.setBounds(10, 75, 414, 14);
		frame.getContentPane().add(lblKonekcija);
		
		JLabel lbl1 = new JLabel("Broj zivota:");
		lbl1.setBounds(10, 100, 100, 14);
		frame.getContentPane().add(lbl1);
		
		JLabel lbl2 = new JLabel("Trazena rec:");
		lbl2.setBounds(10, 125, 100, 14);
		frame.getContentPane().add(lbl2);
		
		JLabel lblSlovoZaPokusaj = new JLabel("Slovo za pokusaj:");
		lblSlovoZaPokusaj.setBounds(10, 150, 159, 14);
		frame.getContentPane().add(lblSlovoZaPokusaj);
		
		tfPokusaj = new JTextField();
		tfPokusaj.setBounds(176, 147, 149, 20);
		frame.getContentPane().add(tfPokusaj);
		tfPokusaj.setColumns(10);
		
		JLabel lblTrazenaRec = new JLabel("");
		lblTrazenaRec.setBounds(176, 125, 46, 14);
		frame.getContentPane().add(lblTrazenaRec);
		
		JLabel lblBrojZivota = new JLabel("");
		lblBrojZivota.setBounds(176, 100, 46, 14);
		frame.getContentPane().add(lblBrojZivota);

		/**Uspostavlja konekciju sa serverom (Postavlja 'socket' i kroz njega 'reader' i 'writer')*/
		JButton btnPoveziSe = new JButton("Povezi se");
		btnPoveziSe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					nick = JOptionPane.showInputDialog("Unesite korisnicko ime: ");
					s = new Socket("localhost", 1234);
					in= new BufferedReader(new InputStreamReader(s.getInputStream()));
					out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);//TRUE obavezno!
					lblKonekcija.setText("Klijent je uspesno zakacen na server");
					out.println(nick);
					trazenaRec = in.readLine();
					System.out.println(trazenaRec.toUpperCase().replaceAll("(.)", "$1 "));
					lblBrojZivota.setText("5");
					lblTrazenaRec.setText(trazenaRec.replaceAll(".", "-"));
				} catch (HeadlessException | IOException e) {
					e.printStackTrace();
				}
				frame.setTitle(nick);
			}
		});
		btnPoveziSe.setBounds(10, 11, 414, 51);
		frame.getContentPane().add(btnPoveziSe);
		
		/**Uzimanje korisnikovog inputa, formatiranje i slanje na server kroz 'socket'(out.println) i cekanje odgovora (in.readLine)*/
		JButton btnPokusaj = new JButton("Pokusaj");
		btnPokusaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = tfPokusaj.getText().toUpperCase();
				char slovo = str.charAt(0);
				String lbl = lblTrazenaRec.getText();
				String br = lblBrojZivota.getText();
				String data = slovo + ":" + lbl + ":" + br;
				out.println(data);
				try {//newLbl + ":" + (resenaRec? "K" : "N") + ":" + (izgubio? "K" : "N") + ":" + newBr
					data = in.readLine();
					String tmp[] = data.split(":");
					lblTrazenaRec.setText(tmp[0]);
					lblBrojZivota.setText(tmp[3]);
					boolean resenaRec = tmp[1].equals("K")? true : false;
					boolean izgubio = tmp[2].equals("K")? true : false;
					
					if(resenaRec) {
						JOptionPane.showMessageDialog(null, "Pogodili ste rec!", "Info", JOptionPane.INFORMATION_MESSAGE);
						try {
							s.close();
						} catch (IOException e) {
							System.err.println("Konekcija nije ni otvorena...");
						}
						frame.dispose();
					} else if(izgubio) {
						JOptionPane.showMessageDialog(null, "Izgubili ste!", "Info", JOptionPane.INFORMATION_MESSAGE);
						try {
							s.close();
						} catch (IOException e) {
							System.err.println("Konekcija nije ni otvorena...");
						}
						frame.dispose();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				tfPokusaj.setText("");
			}
		});
		btnPokusaj.setBounds(335, 146, 89, 23);
		frame.getContentPane().add(btnPokusaj);

		JButton btnKraj = new JButton("Zavrsi igru");
		btnKraj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					s.close();
				} catch (IOException e) {
					System.err.println("Konekcija nije ni otvorena...");
				}
				frame.dispose();
			}
		});
		btnKraj.setBounds(10, 180, 414, 35);
		frame.getContentPane().add(btnKraj);
	}
}
