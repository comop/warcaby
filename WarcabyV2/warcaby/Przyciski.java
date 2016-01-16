package warcaby;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Przyciski implements ActionListener {
	private Plansza pl = new Plansza();
	private String zasady = "Jako pierwszy ruch wykonuje graj�cy pionami niebieskimi, po czym gracze wykonuj� na zmian� kolejne ruchy.\n"
			+ "Celem gry jest zbicie wszystkich pion�w przeciwnika albo zablokowanie wszystkich, kt�re pozostaj� na planszy, pozbawiaj�c przeciwnika mo�liwo�ci wykonania ruchu.\n"
			+ "Piony mog� porusza� si� o jedno pole do przodu po przek�tnej (na ukos) na wolne pola.\n"
			+ "Bicie pionem nast�puje przez przeskoczenie s�siedniego pionu (lub damki) przeciwnika na pole znajduj�ce si� tu� za nim po przek�tnej (pole to musi by� wolne). Zbite piony s� usuwane z planszy po zako�czeniu ruchu.\n"
			+ "Piony mog� bi� zar�wno do przodu, jak i do ty�u.\n"
			+ "W jednym ruchu wolno wykona� wi�cej ni� jedno bicie tym samym pionem, przeskakuj�c przez kolejne piony (damki) przeciwnika.\n"
			+ "Bicia s� obowi�zkowe.\n"
			+ "Pion, kt�ry dojdzie do ostatniego rz�du planszy, staje si� damk�, przy czym je�li znajdzie si� tam w wyniku bicia i b�dzie m�g� wykona� kolejne bicie (do ty�u), to b�dzie musia� je wykona� i nie staje si� wtedy damk�.\n"
			+ "Kiedy pion staje si� damk�, kolej ruchu przypada dla przeciwnika.\n"
			+ "Damki mog� porusza� si� w jednym ruchu o jedno pole do przodu lub do ty�u po przek�tnej.\n"
			+ "Kiedy istnieje kilka mo�liwych bi�, gracz musi wykona� maksymalne (tzn. takie, w kt�rym zbije najwi�ksz� liczb� pion�w lub damek przeciwnika).\n"
			+ "Podczas bicia nie mo�na przeskakiwa� wi�cej ni� jeden raz przez ten sam pion (damk�).";
	public Przyciski(Plansza pl) {
		this.pl = pl;
		akcje();
	}

	public void akcje() {
		pl.nowaGra.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pl.stworzNowaGre();
				pl.pvp.setEnabled(true);
				pl.pvc.setEnabled(true);
				pl.lista.setEnabled(true);
				pl.dodaj.setEnabled(true);
				pl.usun.setEnabled(true);
			}
		});
		pl.pvp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pl.czyWTrakcie = true;
				pl.czyPvP = true;
				pl.pvp.setEnabled(false);
				pl.pvc.setEnabled(false);
				pl.lista.setEnabled(false);
				pl.dodaj.setEnabled(false);
				pl.usun.setEnabled(false);
				pl.repaint();
			}
		});
		pl.pvc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pl.lista.getSelectedIndex() > -1) {
					pl.czyWTrakcie = true;
					pl.czyPvC = true;
					pl.pvp.setEnabled(false);
					pl.pvc.setEnabled(false);
					pl.lista.setEnabled(false);
					pl.dodaj.setEnabled(false);
					pl.usun.setEnabled(false);
					pl.ktoryGracz = pl.lista.getSelectedIndex();
				} else {
					JOptionPane.showMessageDialog(null, "Wybierz gracza");
				}
				pl.repaint();
			}
		});
		pl.dodaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nazwa = JOptionPane.showInputDialog("Dodaj nowego gracza.");
				if (nazwa != null && nazwa.length() >0) {
					Gracz nowyGracz = new Gracz(nazwa);
					pl.gracze.add(nowyGracz);
					pl.lista.add(nazwa);
				}
			}
		});
		pl.usun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pl.lista.getSelectedIndex() > -1) {
					pl.gracze.remove(pl.lista.getSelectedIndex());
					pl.lista.remove(pl.lista.getSelectedIndex());
				}
			}
		});
		pl.historia.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pl.poleTekstowe.setText("");
				for (int i = 0; i < pl.gracze.size(); i++) {
					pl.poleTekstowe.append("Gracz: " + pl.gracze.get(i).getNazwa() + " wygranych: "
							+ pl.gracze.get(i).getIleWygranych() + " przegranych: "
							+ pl.gracze.get(i).getIlePrzegranych() + "\n");
				}
				pl.poleTekstowe.setVisible(!pl.poleTekstowe.isVisible());
			}
		});
		pl.rezygnacja.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pl.koniecGry("Gra zosta�a przerwana");
			}
		});
		pl.zasady.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, zasady, "ZASADY",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		pl.wyjscie.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        System.exit(0);
		    }
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {}
}