
package warcaby;

import java.util.Vector;

public class Ruch {
    

   public static final int
             PUSTE = 0,
             NIEBIESKIE = 1,
             NIEBIESKIE_KROLOWA = 2,
             BIALE = 3,
             BIALE_KROLOWA = 4;

   private int[][] plansza; 
   

   public Ruch() {
      plansza = new int[8][8];
      przygotujPlansze();
   }
   
   public void przygotujPlansze() {

      for (int wiersz = 0; wiersz < 8; wiersz++) {
         for (int kolumna = 0; kolumna < 8; kolumna++) {
            if ( wiersz % 2 == kolumna % 2 ) {
               if (wiersz < 3)
                  plansza[wiersz][kolumna] = BIALE;
               else if (wiersz > 4)
                  plansza[wiersz][kolumna] = NIEBIESKIE;
               else
                  plansza[wiersz][kolumna] = PUSTE;
            }
            else {
               plansza[wiersz][kolumna] = PUSTE;
            }
         }
      }
   } 
   

   public int ktoryPionek(int wiersz, int kolumna) {
       return plansza[wiersz][kolumna];
   }
   

   public void wykonajRuch(DaneORuchu ruch) {
	   int zWiersza = ruch.zKtoregoWiersza;
	   int zKolumny = ruch.zKtorejKolumny;
	   int doWiersza = ruch.doKtoregoWiersza;
	   int doKolumny = ruch.doKtorejKolumny;
      plansza[doWiersza][doKolumny] = plansza[zWiersza][zKolumny];
      plansza[zWiersza][zKolumny] = PUSTE;
      if (zWiersza - doWiersza == 2 || zWiersza - doWiersza == -2) {
         int przezWiersz = (zWiersza + doWiersza) / 2;
         int przezKolumne = (zKolumny + doKolumny) / 2;
         plansza[przezWiersz][przezKolumne] = PUSTE;
      }
      if (doWiersza == 0 && plansza[doWiersza][doKolumny] == NIEBIESKIE)
         plansza[doWiersza][doKolumny] = NIEBIESKIE_KROLOWA;
      if (doWiersza == 7 && plansza[doWiersza][doKolumny] == BIALE)
         plansza[doWiersza][doKolumny] = BIALE_KROLOWA;
   }
   

   public DaneORuchu[] mozliweRuchy(int gracz) {

      if (gracz != NIEBIESKIE && gracz != BIALE)
         return null;

      int krolowa;
      if (gracz == NIEBIESKIE)
         krolowa = NIEBIESKIE_KROLOWA;
      else
         krolowa = BIALE_KROLOWA;

      Vector ruchy = new Vector();

      for (int wiersz = 0; wiersz < 8; wiersz++) {
         for (int kolumna = 0; kolumna < 8; kolumna++) {
            if (plansza[wiersz][kolumna] == gracz || plansza[wiersz][kolumna] == krolowa) {
               if (czyMozliweBicie(gracz, wiersz, kolumna, wiersz+1, kolumna+1, wiersz+2, kolumna+2))
                  ruchy.addElement(new DaneORuchu(wiersz, kolumna, wiersz+2, kolumna+2));
               if (czyMozliweBicie(gracz, wiersz, kolumna, wiersz-1, kolumna+1, wiersz-2, kolumna+2))
                  ruchy.addElement(new DaneORuchu(wiersz, kolumna, wiersz-2, kolumna+2));
               if (czyMozliweBicie(gracz, wiersz, kolumna, wiersz+1, kolumna-1, wiersz+2, kolumna-2))
                  ruchy.addElement(new DaneORuchu(wiersz, kolumna, wiersz+2, kolumna-2));
               if (czyMozliweBicie(gracz, wiersz, kolumna, wiersz-1, kolumna-1, wiersz-2, kolumna-2))
                  ruchy.addElement(new DaneORuchu(wiersz, kolumna, wiersz-2, kolumna-2));
            }
         }
      }
      
      if (ruchy.size() == 0) {
         for (int wiersz = 0; wiersz < 8; wiersz++) {
            for (int kolumna = 0; kolumna < 8; kolumna++) {
               if (plansza[wiersz][kolumna] == gracz || plansza[wiersz][kolumna] == krolowa) {
                  if (czyMozliwyRuch(gracz,wiersz,kolumna,wiersz+1,kolumna+1))
                     ruchy.addElement(new DaneORuchu(wiersz,kolumna,wiersz+1,kolumna+1));
                  if (czyMozliwyRuch(gracz,wiersz,kolumna,wiersz-1,kolumna+1))
                     ruchy.addElement(new DaneORuchu(wiersz,kolumna,wiersz-1,kolumna+1));
                  if (czyMozliwyRuch(gracz,wiersz,kolumna,wiersz+1,kolumna-1))
                     ruchy.addElement(new DaneORuchu(wiersz,kolumna,wiersz+1,kolumna-1));
                  if (czyMozliwyRuch(gracz,wiersz,kolumna,wiersz-1,kolumna-1))
                     ruchy.addElement(new DaneORuchu(wiersz,kolumna,wiersz-1,kolumna-1));
               }
            }
         }
      }
      
      if (ruchy.size() == 0)
         return null;
      else {
         DaneORuchu[] ruchyTab = new DaneORuchu[ruchy.size()];
         for (int i = 0; i < ruchy.size(); i++)
            ruchyTab[i] = (DaneORuchu)ruchy.elementAt(i);
         return ruchyTab;
      }

   } 
   

   public DaneORuchu[] mozliweBicia(int gracz, int wiersz, int kolumna) {
      if (gracz != NIEBIESKIE && gracz != BIALE)
         return null;
      int krolowa;
      if (gracz == NIEBIESKIE)
         krolowa = NIEBIESKIE_KROLOWA;
      else
         krolowa = BIALE_KROLOWA;
      Vector ruchy = new Vector();
      if (plansza[wiersz][kolumna] == gracz || plansza[wiersz][kolumna] == krolowa) {
         if (czyMozliweBicie(gracz, wiersz, kolumna, wiersz+1, kolumna+1, wiersz+2, kolumna+2))
            ruchy.addElement(new DaneORuchu(wiersz, kolumna, wiersz+2, kolumna+2));
         if (czyMozliweBicie(gracz, wiersz, kolumna, wiersz-1, kolumna+1, wiersz-2, kolumna+2))
            ruchy.addElement(new DaneORuchu(wiersz, kolumna, wiersz-2, kolumna+2));
         if (czyMozliweBicie(gracz, wiersz, kolumna, wiersz+1, kolumna-1, wiersz+2, kolumna-2))
            ruchy.addElement(new DaneORuchu(wiersz, kolumna, wiersz+2, kolumna-2));
         if (czyMozliweBicie(gracz, wiersz, kolumna, wiersz-1, kolumna-1, wiersz-2, kolumna-2))
            ruchy.addElement(new DaneORuchu(wiersz, kolumna, wiersz-2, kolumna-2));
      }
      if (ruchy.size() == 0)
         return null;
      else {
         DaneORuchu[] ruchyTab = new DaneORuchu[ruchy.size()];
         for (int i = 0; i < ruchy.size(); i++)
            ruchyTab[i] = (DaneORuchu)ruchy.elementAt(i);
         return ruchyTab;
      }
   }
   

   private boolean czyMozliweBicie(int gracz, int w1, int k1, int w2, int k2, int w3, int k3) {
      if (w3 < 0 || w3 >= 8 || k3 < 0 || k3 >= 8)
         return false;
         
      if (plansza[w3][k3] != PUSTE)
         return false;
         
      if (gracz == NIEBIESKIE) {
         if (plansza[w2][k1] == NIEBIESKIE && w3 > w2)
            return false;
         if (plansza[w2][k2] != BIALE && plansza[w2][k2] != BIALE_KROLOWA)
            return false;
         return true;
      }
      else {
         if (plansza[w2][k1] == BIALE && w3 < w2)
            return false;
         if (plansza[w2][k2] != NIEBIESKIE && plansza[w2][k2] != NIEBIESKIE_KROLOWA)
            return false;
         return true;
      }

   } 
   

   private boolean czyMozliwyRuch(int gracz, int w1, int k1, int w2, int k2) {       
      if (w2 < 0 || w2 >= 8 || k2 < 0 || k2 >= 8)
         return false;
         
      if (plansza[w2][k2] != PUSTE)
         return false;

      if (gracz == NIEBIESKIE) {
         if (plansza[w1][k1] == NIEBIESKIE && w2 > w1)
             return false;
          return true;
      }
      else {
         if (plansza[w1][k1] == BIALE && w2 < w1)
             return false;
          return true;
      }
      
   }

}
