package _zadatak_chatApp_RMI;
/**1*/
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.Consumer;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/*
 * Napisati cet aplikaciju u Javi koja komunicira pomocu Java RMI.
 *
 * Serverski deo aplikacije implementirati tako da korisnici mogu da salju poruke
 * samo ako se prethodno prijave sa svojim nadimkom. Prilikom primanja poruke od
 * nekog korisnika, server prosledjuje tu poruku svim trenutno prijavljenim
 * korisnicima.
 * 
 * Klijentski deo aplikacije uspostavlja konekciju sa serverom i prikazuje
 * prozor sa cet porukama. Klasa Prozor implementira sve potrebne delove
 * grafickog interfejsa i prilikom kreiranja joj se prosledjuje lambda izraz
 * koji ce se izvrsiti svaki put kada korisnik unese novu cet poruku i pritisne
 * ENTER. Takodje, za prikaz nove cet poruke potrebno je pozvati metod prikazi().
 * 
 * Prozor se prikazuje pomocu prozor.setVisible(true). Ovaj poziv ce blokirati
 * proces dok se prozor ne zatvori, posle cega je potrebno zatvoriti konekciju
 * sa serverom.
 */
public class Prozor extends JDialog {

	private static final long serialVersionUID = -5208832710391379961L;

	private final JTextArea text;

	public Prozor(String naslov, Consumer<String> akcija) {

		Font font = new Font(Font.MONOSPACED, Font.BOLD, 14);

		text = new JTextArea(5, 20);
		text.setEditable(false);
		text.setFont(font);
		JScrollPane scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JTextField line = new JTextField();
		line.setFont(font);
		line.addActionListener(e -> {
			String message = line.getText();
			if (!"".equals(message)) {
				akcija.accept(message);
				line.setText("");
			}
		});

		add(scroll, BorderLayout.CENTER);
		add(line, BorderLayout.SOUTH);

		setTitle(naslov);
		setMinimumSize(new Dimension(400, 300));
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	public void prikazi(String nadimak, String poruka) {
		SwingUtilities.invokeLater(() -> {
			String linija = String.format("%10s: %s%n", nadimak, poruka);
			text.append(linija);
		});
	}
}
