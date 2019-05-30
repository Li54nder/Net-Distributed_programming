package _zadatak_chatApp_SOCKET;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
/**1*/
public class KlijentGui {
	
	/*
	 * Napisati cet aplikaciju u Javi koja komunicira pomocu soketa.
	 *
	 * Serverski deo aplikacije implementirati tako da server prihvata vise klijentskih
	 * konekcija. Priilkom uspostave konekcije, prvo se serveru salje string KORISNICKO IME,
	 * zatim vaka nova string linija poslata serveru predstavlja novu poruku koju je korisnik napisao.
	 * Prilikom primanja poruke od nekog korisnika, server je prosedjuje sveim trenutno prijavljenim
	 * korisnicima tako sto savakom salje liniju stringa oblika KORISNICKO IME: PORUKA
	 * 
	 * Klijentski deo aplikacije usopstavlja konekciju sa serverom na odredjenoj adresi i portu,
	 * a prvo sto salje na server jeste KORISNICKO IME, Po uspesnoj uspostavi konekcije, 
	 * klijent moze koristeci implementiran gui unositi nove poruke u odredjeno tekst polje
	 * i pritiskom na ENTER te poruke slati preko servera drugim korisnicima
	 */

	private JFrame frmCetAplikacija;
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	private String nick;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KlijentGui window = new KlijentGui();
					window.frmCetAplikacija.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public KlijentGui() {
		initialize();
	}

	@SuppressWarnings("serial")
	private void initialize() {
		frmCetAplikacija = new JFrame() {
			/**Ne nuzno overrajdovanje metode za zatvaranje prozora tako da pogasi konekcije...*/
			@Override
			public void dispose() {
				try {
					in.close();
					out.close();
					s.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			    super.dispose();
			}
		};
		frmCetAplikacija.setTitle("Cet aplikacija");
		frmCetAplikacija.setBounds(100, 100, 450, 300);
		frmCetAplikacija.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCetAplikacija.getContentPane().setLayout(null);
		
		JScrollPane textArea = new JScrollPane();
		textArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textArea.setBounds(10, 45, 414, 171);
		frmCetAplikacija.getContentPane().add(textArea);
		
		JTextArea text = new JTextArea();
		textArea.setViewportView(text);
		

		JButton btnKonektujSeNa = new JButton("Konektuj se na server");
		btnKonektujSeNa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nick = JOptionPane.showInputDialog("Unesite ime: ");
				try {
					s = new Socket("localhost", 1234);
					btnKonektujSeNa.setEnabled(false);
					btnKonektujSeNa.setBackground(Color.BLACK);
					btnKonektujSeNa.setText(nick + " konektovan na server");
					in = new BufferedReader(new InputStreamReader(s.getInputStream()));
					out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
					out.println(nick);
					Thread t = new Thread() { //mozda bez treda...
						@Override
						public void run() {
							String linija;
							while(true) {
								try {
									linija = in.readLine();
									text.append(linija + "\n");
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
					};
					t.start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnKonektujSeNa.setBounds(10, 11, 414, 23);
		frmCetAplikacija.getContentPane().add(btnKonektujSeNa);
		
		/**Implemetiranje akcije na dugme ENTER koje se desi nakon uete poruke...*/
		JTextField line = new JTextField();
		line.setBounds(10, 227, 414, 23);
		frmCetAplikacija.getContentPane().add(line);
		line.addActionListener(e -> {
			String message = line.getText();
			if (!"".equals(message)) {
				out.println(nick + ": " + message); //u zavisnosti od udaljeno implementiranog servera mozda treba maci sve pre msg
				line.setText("");
			}
		});
	}
	
	
}
