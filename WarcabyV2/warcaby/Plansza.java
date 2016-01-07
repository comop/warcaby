
package warcaby;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Plansza extends JPanel implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List lista = new List();
	public JButton nowaGra = new JButton("Nowa Gra");
	public JButton pvp = new JButton("Gracz vs Gracz");
	public JButton pvc = new JButton("Gracz vs komputer");
	public JButton dodaj = new JButton("Dodaj");
	public JButton usun = new JButton("Usun");
	public JButton historia = new JButton("Historia");
	public JButton rezygnacja = new JButton("Przerwij gre");
	public TextArea poleTekstowe = new TextArea();
	public JButton zasady = new JButton("Zasady");
	private JMenuBar menu = new JMenuBar();
	private JMenu zakoncz = new JMenu("Zakoncz");
	public JMenuItem wyjscie = new JMenuItem("Wyjscie");
	private String wiadomosc;
	private Ruch inf;
	public boolean czyWTrakcie;
	private int obecnyGracz;
	private int wybranyWiersz, wybranaKolumna;
	private DaneORuchu[] dostepneRuchy;
	public boolean czyPvP = false;
	public boolean czyPvC = false;
	public Vector<Gracz> gracze = new Vector<Gracz>();
	public int ktoryGracz;

	public Plansza() {
		addMouseListener(this);
		inf = new Ruch();
		stworzNowaGre();
	}

	public void stworzNowaGre() {
		if (czyWTrakcie == true) {

			wiadomosc = "Najpierw dokoncz biezaca rozgrywke!";
			return;
		}
		inf.przygotujPlansze();
		obecnyGracz = Ruch.NIEBIESKIE;
		dostepneRuchy = inf.mozliweRuchy(Ruch.NIEBIESKIE);
		wybranyWiersz = -1;
		wiadomosc = "Niebieski: Teraz Twoja kolej";
		czyWTrakcie = false;
		nowaGra.setEnabled(false);
		repaint();
	}

	void koniecGry(String str) {
		wiadomosc = str;
		nowaGra.setEnabled(true);
		czyWTrakcie = false;
		czyPvC = false;
		czyPvP = false;
	}

	void przygotujRuch(int wiersz, int kolumna) {

		for (int i = 0; i < dostepneRuchy.length; i++)
			if (dostepneRuchy[i].zKtoregoWiersza == wiersz && dostepneRuchy[i].zKtorejKolumny == kolumna) {
				wybranyWiersz = wiersz;
				wybranaKolumna = kolumna;
				if (obecnyGracz == Ruch.NIEBIESKIE)
					wiadomosc = "Niebieski: Wykonaj ruch";
				else
					wiadomosc = "Bialy: Wykonaj ruch.";
				repaint();
				return;
			}

		if (wybranyWiersz < 0) {
			wiadomosc = "Kliknij na pionek, ktory chcesz przesunac.";
			return;
		}
		for (int i = 0; i < dostepneRuchy.length; i++)
			if (dostepneRuchy[i].zKtoregoWiersza == wybranyWiersz && dostepneRuchy[i].zKtorejKolumny == wybranaKolumna
					&& dostepneRuchy[i].doKtoregoWiersza == wiersz && dostepneRuchy[i].doKtorejKolumny == kolumna) {
				wykonajRuch(dostepneRuchy[i]);
				return;
			}

		wiadomosc = "Wybierz pole, na ktorym chesz umiescic pionek.";

	}

	void przygotujRuchKomp(int wiersz, int kolumna) {
		if (obecnyGracz == Ruch.NIEBIESKIE) {
			for (int i = 0; i < dostepneRuchy.length; i++)
				if (dostepneRuchy[i].zKtoregoWiersza == wiersz && dostepneRuchy[i].zKtorejKolumny == kolumna) {
					wybranyWiersz = wiersz;
					wybranaKolumna = kolumna;
					if (obecnyGracz == Ruch.NIEBIESKIE)
						wiadomosc = "Niebieski: Wykonaj ruch";
					else
						wiadomosc = "Bialy: Wykonaj ruch.";
					repaint();
					return;
				}

			if (wybranyWiersz < 0) {
				wiadomosc = "Kliknij na pionek, ktory chcesz przesunac.";
				return;
			}
			for (int i = 0; i < dostepneRuchy.length; i++)
				if (dostepneRuchy[i].zKtoregoWiersza == wybranyWiersz
						&& dostepneRuchy[i].zKtorejKolumny == wybranaKolumna
						&& dostepneRuchy[i].doKtoregoWiersza == wiersz && dostepneRuchy[i].doKtorejKolumny == kolumna) {
					wykonajRuch(dostepneRuchy[i]);
					return;
				}
		} else {
			int max = 0;
			int maxi = 0;
			for (int i = 0; i < dostepneRuchy.length; i++) {
				if (oplacalnosc(dostepneRuchy[i].doKtoregoWiersza, dostepneRuchy[i].doKtorejKolumny) >= max) {
					max = oplacalnosc(dostepneRuchy[i].doKtoregoWiersza, dostepneRuchy[i].doKtorejKolumny);
					maxi = i;
				}
			}

			wykonajRuch(dostepneRuchy[maxi]);
		}

		wiadomosc = "Wybierz pole, na ktorym chesz umiescic pionek.";

	}

	private int oplacalnosc(int w, int k) {
		int wynik = 0;
		if (w - 1 >= 0 && w + 1 <= 7 && k - 1 >= 0 && k + 1 <= 7) {
			if (inf.ktoryPionek(w + 1, k - 1) != Ruch.NIEBIESKIE
					&& inf.ktoryPionek(w + 1, k - 1) != Ruch.NIEBIESKIE_KROLOWA
					&& inf.ktoryPionek(w + 1, k + 1) != Ruch.NIEBIESKIE
					&& inf.ktoryPionek(w + 1, k + 1) != Ruch.NIEBIESKIE_KROLOWA) {
				wynik += 1;
			}
		}
		return wynik;
	}

	void wykonajRuch(DaneORuchu ruch) {

		inf.wykonajRuch(ruch);

		if (ruch.czySkok()) {
			dostepneRuchy = inf.mozliweBicia(obecnyGracz, ruch.doKtoregoWiersza, ruch.doKtorejKolumny);
			if (dostepneRuchy != null) {
				if (obecnyGracz == Ruch.NIEBIESKIE)
					wiadomosc = "Niebieski: Musisz kontunuowac bicie!";
				else
					wiadomosc = "Bialy: musisz kontynuowac bicie";
				wybranyWiersz = ruch.doKtoregoWiersza;
				wybranaKolumna = ruch.doKtorejKolumny;
				repaint();
				return;
			}
		}

		if (obecnyGracz == Ruch.NIEBIESKIE) {
			obecnyGracz = Ruch.BIALE;
			dostepneRuchy = inf.mozliweRuchy(obecnyGracz);
			if (dostepneRuchy == null) {
				koniecGry("Bialy nie ma juz ruchow, wygrywa niebieski.");
				
					if (!czyPvP && gracze.get(ktoryGracz) != null)
					gracze.get(ktoryGracz).setIleWygranych();
			} else if (dostepneRuchy[0].czySkok())
				wiadomosc = "Bialy: Musisz zbic pionek przeciwnika.";
			else
				wiadomosc = "Bialy: Wykonaj ruch.";
		} else {
			obecnyGracz = Ruch.NIEBIESKIE;
			dostepneRuchy = inf.mozliweRuchy(obecnyGracz);
			if (dostepneRuchy == null) {
				koniecGry("Niebieski nie ma juz ruchow, wygrywa bialy.");
				
					if (!czyPvP && gracze.get(ktoryGracz) != null)
						gracze.get(ktoryGracz).setIlePrzegranych();
			} else if (dostepneRuchy[0].czySkok())
				wiadomosc = "Niebieski: Musisz zbic pionek przeciwnika.";
			else
				wiadomosc = "Niebieski: Wykonaj ruch.";
		}
		repaint();
		wybranyWiersz = -1;

	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paint(g2);
		for (int wiersz = 0; wiersz < 8; wiersz++) {
			for (int kolumna = 0; kolumna < 8; kolumna++) {
				if (wiersz % 2 == kolumna % 2)
					g2.setColor(Color.black);
				else
					g2.setColor(Color.white);
				g2.fillRect(5 + kolumna * 70, wiersz * 70, 70, 70);
				switch (inf.ktoryPionek(wiersz, kolumna)) {
				case Ruch.NIEBIESKIE:
					g2.setColor(Color.blue);
					g2.fillOval(15 + kolumna * 70, 10 + wiersz * 70, 50, 50);
					break;
				case Ruch.BIALE:
					g2.setColor(Color.white);
					g2.fillOval(15 + kolumna * 70, 10 + wiersz * 70, 50, 50);
					break;
				case Ruch.NIEBIESKIE_KROLOWA:
					g2.setColor(Color.blue);
					g2.fillOval(15 + kolumna * 70, 10 + wiersz * 70, 50, 50);
					g2.setColor(Color.white);
					g2.drawString("D", 15 + kolumna * 70, 10 + wiersz * 70);
					break;
				case Ruch.BIALE_KROLOWA:
					g2.setColor(Color.white);
					g2.fillOval(15 + kolumna * 70, 10 + wiersz * 70, 50, 50);
					g2.setColor(Color.white);
					g2.drawString("D", 15 + kolumna * 70, 10 + wiersz * 70);
					break;
				}
			}
		}

		float grubosc = 2.5f;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(grubosc));
		if (czyWTrakcie) {
			g2.setColor(Color.red);
			for (int i = 0; i < dostepneRuchy.length; i++) {
				g2.drawRect(5 + dostepneRuchy[i].zKtorejKolumny * 70,  dostepneRuchy[i].zKtoregoWiersza * 70, 69,
						69);
			}

			if (wybranyWiersz >= 0) {
				g2.setColor(Color.white);
				g2.drawRect(5 + wybranaKolumna * 70,  wybranyWiersz * 70, 69, 69);
				g2.drawRect(5 + wybranaKolumna * 70,  wybranyWiersz * 70, 67, 67);
				g2.setColor(Color.green);
				for (int i = 0; i < dostepneRuchy.length; i++) {
					if (dostepneRuchy[i].zKtorejKolumny == wybranaKolumna
							&& dostepneRuchy[i].zKtoregoWiersza == wybranyWiersz)
						g2.drawRect(5 + dostepneRuchy[i].doKtorejKolumny * 70,  dostepneRuchy[i].doKtoregoWiersza * 70, 69, 69);
				}
			}
		}
		g2.setStroke(oldStroke);
		g2.setColor(Color.red);
		g2.drawString(wiadomosc, 300, 590);
	}

	public void mousePressed(MouseEvent evt) {

		if (czyWTrakcie == false)
			wiadomosc = "Kliknij \"Nowa gra\" aby rozpoczac nowa gre.";
		else {
			if (czyPvP) {
				int kolumna = (evt.getX() ) / 70;
				int wiersz = (evt.getY() ) / 70;
				if (kolumna >= 0 && kolumna < 8 && wiersz >= 0 && wiersz < 8)
					przygotujRuch(wiersz, kolumna);
			} else if (czyPvC) {
				int kolumna = (evt.getX() ) / 70;
				int wiersz = (evt.getY() ) / 70;
				if (kolumna >= 0 && kolumna < 8 && wiersz >= 0 && wiersz < 8)
					przygotujRuchKomp(wiersz, kolumna);
			}
		}
	}

	public static void start() {
		
		JFrame okno = new JFrame("WARCABY");
		final Plansza pl = new Plansza();
		Przyciski przy = new Przyciski(pl);
		okno.setVisible(true);
		okno.setSize(1024, 640);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setResizable(false);
		okno.setLayout(null);
		pl.setBounds(0,20, 600, 620);
		pl.lista.setBounds(760, 30, 140, 150);
		pl.dodaj.setBounds(910, 30, 90, 25);
		pl.usun.setBounds(910, 70, 90, 25);
		pl.nowaGra.setBounds(600, 30, 145, 30);
		pl.rezygnacja.setBounds(600, 70, 145, 30);
		pl.pvp.setBounds(600, 110, 145, 30);
		pl.pvc.setBounds(600, 150, 145, 30);
		pl.historia.setBounds(700, 210, 120, 30);
		pl.poleTekstowe.setBounds(600, 260, 400, 312);
		pl.zasady.setBounds(600, 590, 80, 20);
		pl.poleTekstowe.setVisible(false);
		pl.poleTekstowe.setEditable(false);
		pl.nowaGra.addActionListener(przy);
		pl.pvp.addActionListener(przy);
		pl.pvc.addActionListener(przy);
		pl.dodaj.addActionListener(przy);
		pl.usun.addActionListener(przy);
		pl.historia.addActionListener(przy);
		pl.rezygnacja.addActionListener(przy);
		pl.zasady.addActionListener(przy);
		pl.menu.setBounds(0, 1, 1100, 18);
		pl.zakoncz.add(pl.wyjscie);
		pl.menu.add(pl.zakoncz);
		okno.add(pl);
		okno.add(pl.nowaGra);
		okno.add(pl.pvp);
		okno.add(pl.pvc);
		okno.add(pl.lista);
		okno.add(pl.dodaj);
		okno.add(pl.usun);
		okno.add(pl.historia);
		okno.add(pl.poleTekstowe);
		okno.add(pl.rezygnacja);
		okno.add(pl.zasady);
		okno.add(pl.menu);
		}

	public void mouseReleased(MouseEvent evt) {
	}

	public void mouseClicked(MouseEvent evt) {
	}

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseExited(MouseEvent evt) {
	}

	public void actionPerformed(ActionEvent e) {
	}

	public static void main(String[] args) {
		start();
	}

}
