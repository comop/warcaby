
package warcaby;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Plansza extends JPanel implements ActionListener, MouseListener {

   private JButton nowaGra = new JButton("Nowa Gra");
   private JButton pvp = new JButton("Gracz vs Gracz");
   private JButton pvc = new JButton("Gracz vs komputer");
   private JButton cvc = new JButton("Komp. vs komp?");
   private String wiadomosc; 
   private Ruch inf; 
   private boolean czyWTrakcie;
   private int obecnyGracz;
   private int wybranyWiersz, wybranaKolumna; 
   private DaneORuchu[] dostepneRuchy; 
   private boolean czyPvP = false;
   private boolean czyPvC = false;
   private boolean czyCVC = false;
   
   
   public Plansza() {
      addMouseListener(this);
      inf = new Ruch();
      stworzNowaGre();
   }
   
   void stworzNowaGre() {
      if (czyWTrakcie == true) {

         wiadomosc="Najpierw dokoncz biezaca rozgrywke!";
         return;
      }
      inf.przygotujPlansze();
      obecnyGracz = Ruch.NIEBIESKIE;
      dostepneRuchy = inf.mozliweRuchy(Ruch.NIEBIESKIE); 
      wybranyWiersz = -1; 
      wiadomosc="Niebieski: Teraz Twoja kolej";
      czyWTrakcie = false;
      nowaGra.setEnabled(false);
   }
   

   void koniecGry(String str) {
      wiadomosc= str;
      nowaGra.setEnabled(true);
      czyWTrakcie = false;
      czyCVC = false;
      czyPvC = false;
      czyPvP = false;
   }
      

   void przygotujRuch(int wiersz, int kolumna) {

      for (int i = 0; i < dostepneRuchy.length; i++)
         if (dostepneRuchy[i].zKtoregoWiersza == wiersz && dostepneRuchy[i].zKtorejKolumny == kolumna) {
            wybranyWiersz = wiersz;
            wybranaKolumna = kolumna;
            if (obecnyGracz == Ruch.NIEBIESKIE)
               wiadomosc="Niebieski: Wykonaj ruch";
            else
               wiadomosc="Bialy: Wykonaj ruch.";
            return;
         }


      if (wybranyWiersz < 0) {
          wiadomosc="Kliknij na pionek, ktory chcesz przesunac.";
          return;
      }
      for (int i = 0; i < dostepneRuchy.length; i++)
         if (dostepneRuchy[i].zKtoregoWiersza == wybranyWiersz && dostepneRuchy[i].zKtorejKolumny == wybranaKolumna
                 && dostepneRuchy[i].doKtoregoWiersza == wiersz && dostepneRuchy[i].doKtorejKolumny == kolumna) {
        		 wykonajRuch(dostepneRuchy[i]);
            return;
         }
         

      wiadomosc="Wybierz pole, na ktorym chesz umiescic pionek.";

   }
   void przygotujRuchKomp(int wiersz, int kolumna) {
	   if(obecnyGracz==Ruch.NIEBIESKIE){
	      for (int i = 0; i < dostepneRuchy.length; i++)
	         if (dostepneRuchy[i].zKtoregoWiersza == wiersz && dostepneRuchy[i].zKtorejKolumny == kolumna) {
	            wybranyWiersz = wiersz;
	            wybranaKolumna = kolumna;
	            if (obecnyGracz == Ruch.NIEBIESKIE)
	               wiadomosc="Niebieski: Wykonaj ruch";
	            else
	               wiadomosc="Bialy: Wykonaj ruch.";
	            return;
	         }


	      if (wybranyWiersz < 0) {
	          wiadomosc="Kliknij na pionek, ktory chcesz przesunac.";
	          return;
	      }
	      for (int i = 0; i < dostepneRuchy.length; i++)
	         if (dostepneRuchy[i].zKtoregoWiersza == wybranyWiersz && dostepneRuchy[i].zKtorejKolumny == wybranaKolumna
	                 && dostepneRuchy[i].doKtoregoWiersza == wiersz && dostepneRuchy[i].doKtorejKolumny == kolumna) {
	        		 wykonajRuch(dostepneRuchy[i]);
	            return;
	         }
		   /*Random rand = new Random();
		   wykonajRuch(dostepneRuchy[rand.nextInt(dostepneRuchy.length)]);*/
	   }else{
		   try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		   Random rand = new Random();
		   wykonajRuch(dostepneRuchy[rand.nextInt(dostepneRuchy.length)]);
	   }

	      wiadomosc="Wybierz pole, na ktorym chesz umiescic pionek.";

	   }
   void kompVsKomp() {
	   if(obecnyGracz==Ruch.NIEBIESKIE){
		   Random rand = new Random();
		   wykonajRuch(dostepneRuchy[rand.nextInt(dostepneRuchy.length)]);
	   }else{
		   Random rand = new Random();
		   wykonajRuch(dostepneRuchy[rand.nextInt(dostepneRuchy.length)]);
	   }

	   }
   void wykonajRuch(DaneORuchu ruch) {

      inf.wykonajRuch(ruch);

      
      if (ruch.czySkok()) {
         dostepneRuchy = inf.mozliweBicia(obecnyGracz,ruch.doKtoregoWiersza,ruch.doKtorejKolumny);
         if (dostepneRuchy != null) {
            if (obecnyGracz == Ruch.NIEBIESKIE)
               wiadomosc="Niebieski: Musisz kontunuowac bicie!";
            else
               wiadomosc="Bialy: musisz kontynuowac bicie";
            wybranyWiersz = ruch.doKtoregoWiersza;
            wybranaKolumna = ruch.doKtorejKolumny;
            return;
         }
      }
      
      if (obecnyGracz == Ruch.NIEBIESKIE) {
         obecnyGracz = Ruch.BIALE;
         dostepneRuchy = inf.mozliweRuchy(obecnyGracz);
         if (dostepneRuchy == null)
            koniecGry("Bialy nie ma juz ruchow, wygrywa niebieski.");
         else if (dostepneRuchy[0].czySkok())
            wiadomosc="Bialy: Musisz zbic pionek przeciwnika.";
         else
            wiadomosc="Bialy: Wykonaj ruch.";
      }else {
         obecnyGracz = Ruch.NIEBIESKIE;
         dostepneRuchy = inf.mozliweRuchy(obecnyGracz);
         if (dostepneRuchy == null)
            koniecGry("Niebieski nie ma juz ruchow, wygrywa bialy.");
         else if (dostepneRuchy[0].czySkok())
            wiadomosc="Niebieski: Musisz zbic pionek przeciwnika.";
         else
            wiadomosc="Niebieski: Wykonaj ruch.";
      }
      
      wybranyWiersz = -1;  
      
   }

   public void paint(Graphics g) {
	   Graphics2D g2 = (Graphics2D) g;
       super.paint(g2);
      for (int wiersz = 0; wiersz < 8; wiersz++) {
         for (int kolumna = 0; kolumna < 8; kolumna++) {
             if ( wiersz % 2 == kolumna % 2 )
                g2.setColor(Color.black);
             else
                g2.setColor(Color.white);
             g2.fillRect(2 + kolumna*70, 2 + wiersz*70, 70, 70);
             switch (inf.ktoryPionek(wiersz,kolumna)) {
                case Ruch.NIEBIESKIE:
                   g2.setColor(Color.blue);
                   g2.fillOval(10 + kolumna*70, 10 + wiersz*70, 50, 50);
                   break;
                case Ruch.BIALE:
                   g2.setColor(Color.white);
                   g2.fillOval(10 + kolumna*70, 10 + wiersz*70, 50, 50);
                   break;
                case Ruch.NIEBIESKIE_KROLOWA:
                   g2.setColor(Color.blue);
                   g2.fillOval(10 + kolumna*70, 10 + wiersz*70, 50, 50);
                   g2.setColor(Color.white);
                   g2.drawString("K", 7 + kolumna*70, 16 + wiersz*70);
                   break;
                case Ruch.BIALE_KROLOWA:
                   g2.setColor(Color.white);
                   g2.fillOval(10 + kolumna*70, 10 + wiersz*70, 50, 50);
                   g2.setColor(Color.white);
                   g2.drawString("K", 7 + kolumna*70, 16 + wiersz*70);
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
            g2.drawRect(2 + dostepneRuchy[i].zKtorejKolumny*70, 2 + dostepneRuchy[i].zKtoregoWiersza*70, 69, 69);
         }

         if (wybranyWiersz >= 0) {
            g2.setColor(Color.white);
            g2.drawRect(2 + wybranaKolumna*70, 2 + wybranyWiersz*70, 69, 69);
            g2.drawRect(3 + wybranaKolumna*70, 3 + wybranyWiersz*70, 67, 67);
            g2.setColor(Color.green);
            for (int i = 0; i < dostepneRuchy.length; i++) {
               if (dostepneRuchy[i].zKtorejKolumny == wybranaKolumna && dostepneRuchy[i].zKtoregoWiersza == wybranyWiersz)
                  g2.drawRect(2 + dostepneRuchy[i].doKtorejKolumny*70, 2 + dostepneRuchy[i].doKtoregoWiersza*70, 69, 69);
            }
         }
      }
      g2.setStroke(oldStroke);
      g2.setColor(Color.red);
      g2.drawString(wiadomosc, 300, 580);
   }

   public void mousePressed(MouseEvent evt) {

      if (czyWTrakcie == false)
         wiadomosc="Kliknij \"Nowa gra\" aby rozpoczac nowa gre.";
      else {
    	  if(czyCVC){
    		 kompVsKomp();
    	  }else if(czyPvP){
	         int kolumna = (evt.getX() - 2) / 70;
	         int wiersz = (evt.getY() - 2) / 70;
	         if (kolumna >= 0 && kolumna < 8 && wiersz >= 0 && wiersz < 8)
	            przygotujRuch(wiersz,kolumna);
    	  }else if(czyPvC){
    		  	int kolumna = (evt.getX() - 2) / 70;
 	         	int wiersz = (evt.getY() - 2) / 70;
 	         	if (kolumna >= 0 && kolumna < 8 && wiersz >= 0 && wiersz < 8)
 	         		przygotujRuchKomp(wiersz,kolumna);
    	  }
      }
   }
   
   public static void start(){
       JFrame okno = new JFrame("WARCABY");
        final Plansza pl = new Plansza();
        okno.setVisible(true);
        okno.setSize(1024, 640);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setResizable(false);
        okno.setLayout(null);
        pl.setBounds(0, 0, 600, 620);
        okno.add(pl);
        pl.nowaGra.setBounds(600, 60, 145, 30);
        pl.pvp.setBounds(600, 100, 145, 30);
        pl.pvc.setBounds(600, 140, 145, 30);
        pl.cvc.setBounds(600, 180, 145, 30);
        pl.nowaGra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			         pl.stworzNowaGre();
			         pl.pvp.setEnabled(true);
			         pl.pvc.setEnabled(true);
			         pl.cvc.setEnabled(true);
			}
		});
        pl.pvp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			         pl.czyWTrakcie = true;
			         pl.czyPvP = true;
			         pl.pvp.setEnabled(false);
			         pl.pvc.setEnabled(false);
			         pl.cvc.setEnabled(false);
			}
		});
        pl.pvc.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
		         pl.czyWTrakcie = true;
		         pl.czyPvC = true;
		         pl.pvp.setEnabled(false);
		         pl.pvc.setEnabled(false);
		         pl.cvc.setEnabled(false);
			}
		});
        pl.cvc.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
		         pl.czyWTrakcie = true;
		         pl.czyCVC = true;
		         pl.pvp.setEnabled(false);
		         pl.pvc.setEnabled(false);
		         pl.cvc.setEnabled(false);
			}
		});
        okno.add(pl.nowaGra);
        okno.add(pl.pvp);
        okno.add(pl.pvc);
        okno.add(pl.cvc);
        while(true){
            pl.repaint();
        }
        

   }
   public void mouseReleased(MouseEvent evt) { }
   public void mouseClicked(MouseEvent evt) { }
   public void mouseEntered(MouseEvent evt) { }
   public void mouseExited(MouseEvent evt) { }
   
   public static void main(String[] args) {
        start();
    }

   @Override
	public void actionPerformed(ActionEvent e) {
	}
}
