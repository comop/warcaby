package warcaby;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Przyciski implements ActionListener {
	private Plansza pl = new Plansza();
	private String zasady = "Jako pierwszy ruch wykonuje graj¹cy pionami niebieskimi, po czym gracze wykonuj¹ na zmianê kolejne ruchy.\n"
			+ "Celem gry jest zbicie wszystkich pionów przeciwnika albo zablokowanie wszystkich, które pozostaj¹ na planszy, pozbawiaj¹c przeciwnika mo¿liwoœci wykonania ruchu.\n"
			+ "Piony mog¹ poruszaæ siê o jedno pole do przodu po przek¹tnej (na ukos) na wolne pola.\n"
			+ "Bicie pionem nastêpuje przez przeskoczenie s¹siedniego pionu (lub damki) przeciwnika na pole znajduj¹ce siê tu¿ za nim po przek¹tnej (pole to musi byæ wolne). Zbite piony s¹ usuwane z planszy po zakoñczeniu ruchu.\n"
			+ "Piony mog¹ biæ zarówno do przodu, jak i do ty³u.\n"
			+ "W jednym ruchu wolno wykonaæ wiêcej ni¿ jedno bicie tym samym pionem, przeskakuj¹c przez kolejne piony (damki) przeciwnika.\n"
			+ "Bicia s¹ obowi¹zkowe.\n"
			+ "Pion, który dojdzie do ostatniego rzêdu planszy, staje siê damk¹, przy czym jeœli znajdzie siê tam w wyniku bicia i bêdzie móg³ wykonaæ kolejne bicie (do ty³u), to bêdzie musia³ je wykonaæ i nie staje siê wtedy damk¹.\n"
			+ "Kiedy pion staje siê damk¹, kolej ruchu przypada dla przeciwnika.\n"
			+ "Damki mog¹ poruszaæ siê w jednym ruchu o jedno pole do przodu lub do ty³u po przek¹tnej.\n"
			+ "Kiedy istnieje kilka mo¿liwych biæ, gracz musi wykonaæ maksymalne (tzn. takie, w którym zbije najwiêksz¹ liczbê pionów lub damek przeciwnika).\n"
			+ "Podczas bicia nie mo¿na przeskakiwaæ wiêcej ni¿ jeden raz przez ten sam pion (damkê).";
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
				pl.koniecGry("Gra zosta³a przerwana");
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