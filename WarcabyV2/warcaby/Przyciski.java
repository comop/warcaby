package warcaby;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;

import javax.swing.JOptionPane;

public class Przyciski implements ActionListener,WindowListener {
	    private Plansza pl = new Plansza();
    private String zasady = "Jako pierwszy ruch wykonuje grajacy pionami niebieskimi, po czym gracze wykonuja na zmiane kolejne ruchy.\n"
            + "Celem gry jest zbicie wszystkich pionow przeciwnika albo zablokowanie wszystkich, ktore pozostajo na planszy, pozbawiajac przeciwnika mozliwosci wykonania ruchu.\n"
            + "Piony moga poruszac sie o jedno pole do przodu po przekatnej (na ukos) na wolne pola.\n"
            + "Bicie pionem nastepuje przez przeskoczenie sasiedniego pionu (lub damki) przeciwnika na pole znajdujace sie tuz za nim po przekatnej (pole to musi byc wolne). Zbite piony sa usuwane z planszy po zakonczeniu ruchu.\n"
            + "Piony moga bic zarowno do przodu, jak i do tylu.\n"
            + "W jednym ruchu wolno wykonac wiecej niz jedno bicie tym samym pionem, przeskakujac przez kolejne piony (damki) przeciwnika.\n"
            + "Bicia sa obowiazkowe.\n"
            + "Pion, ktory dojdzie do ostatniego rzedu planszy, staje sie damka, przy czym jezli znajdzie sie tam w wyniku bicia i bedzie mogl wykonac kolejne bicie (do tylu), to bedzie musial je wykonac i nie staje sie wtedy damka.\n"
            + "Kiedy pion staje sie damka, kolej ruchu przypada dla przeciwnika.\n"
            + "Damki moga poruszac sie w jednym ruchu o jedno pole do przodu lub do tylu po przekatnej.\n"
            + "Kiedy istnieje kilka mozliwych bic, gracz musi wykonac maksymalne (tzn. takie, w ktorym zbije najwieksza liczbe pionow lub damek przeciwnika).\n"
            + "Podczas bicia nie mozna przeskakiwac wiecej niz jeden raz przez ten sam pion (damka).";

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
                pl.ktoryGracz = -1;
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
                boolean czyJest = false;
                String nazwa = JOptionPane.showInputDialog("Dodaj nowego gracza.");
                if (nazwa != null && nazwa.length() > 0) {
                    for (int i = 0; i < pl.gracze.size(); i++) {
                        if (nazwa.equals(pl.gracze.get(i).getNazwa())) {
                            czyJest = true;
                        }
                    }
                    if (!czyJest) {
                        Gracz nowyGracz = new Gracz(nazwa);
                        pl.gracze.add(nowyGracz);
                        pl.lista.add(nazwa);
                    } else {
                        JOptionPane.showMessageDialog(null, "Gracz o takiej nazwie istnieje.");
                    }
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
                pl.koniecGry("Gra zostala przerwana");
            }
        });
        pl.zasady.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, zasady, "ZASADY", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        pl.wyjscie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	String[] opcje = new String[] {"Zapisz", "Nie zapisuj", "Anuluj"};
            	int confirm = JOptionPane.showOptionDialog(
        	             null, "Czy chcesz zapisac historie gry?", 
        	             "Wyjscie", JOptionPane.WARNING_MESSAGE, 
        	             JOptionPane.QUESTION_MESSAGE, null, opcje, opcje[1]);
            	if(confirm==1){
            		 System.exit(0);
            	}else if(confirm==0){
            		 JFileChooser fc = new JFileChooser();
                     File file = null;
                     if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                         file = fc.getSelectedFile();
                     }
                     try {
                         PrintWriter pw = new PrintWriter(file);
                         for (int i = 0; i < pl.gracze.size(); i++) {
                             pw.println("Gracz: " + pl.gracze.get(i).getNazwa() + " wygranych: "
                                     + pl.gracze.get(i).getIleWygranych() + " przegranych: "
                                     + pl.gracze.get(i).getIlePrzegranych() + "\n");
                         }
                         pw.close();
                         System.exit(0);
                     } catch (Exception e) {
                         JOptionPane.showMessageDialog(null, "Nie ma takiego pliku.");
                     }
                     
            	}
            		
            }
        });
        pl.zapisz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JFileChooser fc = new JFileChooser();
                File file = null;
                if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                }
                try {
                    PrintWriter pw = new PrintWriter(file);
                    for (int i = 0; i < pl.gracze.size(); i++) {
                        pw.println("Gracz: " + pl.gracze.get(i).getNazwa() + " wygranych: "
                                + pl.gracze.get(i).getIleWygranych() + " przegranych: "
                                + pl.gracze.get(i).getIlePrzegranych() + "\n");
                    }
                    pw.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Nie ma takiego pliku.");
                }
            }
        });
        pl.wczytaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNext()) {
                            String linia = scanner.nextLine();
                            String[] czesci = linia.split(" ");
                            Gracz nowyGracz = new Gracz(czesci[1]);
                            nowyGracz.setIlePrzegranych(Integer.parseInt(czesci[5]));
                            nowyGracz.setIleWygranych(Integer.parseInt(czesci[3]));
                            pl.gracze.add(nowyGracz);
                            pl.lista.add(czesci[1]);
                        }
                        scanner.close();
                    }catch(FileNotFoundException fnfe ){
                        JOptionPane.showMessageDialog(null, "Nie ma takiego pliku.");
                    }catch(Exception e){
                    	JOptionPane.showMessageDialog(null, "Wczytano zly plik.");
                    }
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {}
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		String[] opcje = new String[] {"Zapisz", "Nie zapisuj", "Anuluj"};
    	int confirm = JOptionPane.showOptionDialog(
	             null, "Czy chcesz zapisac historie gry?", 
	             "Wyjscie", JOptionPane.WARNING_MESSAGE, 
	             JOptionPane.QUESTION_MESSAGE, null, opcje, opcje[0]);
    	if(confirm==1){
    		 System.exit(0);
    	}else if(confirm==0){
    		 JFileChooser fc = new JFileChooser();
             File file = null;
             if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                 file = fc.getSelectedFile();
             }
             try {
                 PrintWriter pw = new PrintWriter(file);
                 for (int i = 0; i < pl.gracze.size(); i++) {
                     pw.println("Gracz: " + pl.gracze.get(i).getNazwa() + " wygranych: "
                             + pl.gracze.get(i).getIleWygranych() + " przegranych: "
                             + pl.gracze.get(i).getIlePrzegranych() + "\n");
                 }
                 pw.close();
                 System.exit(0);
             } catch (Exception ex) {
                 JOptionPane.showMessageDialog(null, "Nie ma takiego pliku.");
             }
    	}
	}

	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}
