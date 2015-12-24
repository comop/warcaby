
package warcaby;

public class DaneORuchu {
    
   int zKtoregoWiersza, zKtorejKolumny;
   int doKtoregoWiersza, doKtorejKolumny;
   DaneORuchu(int r1, int c1, int r2, int c2) {
      zKtoregoWiersza = r1;
      zKtorejKolumny = c1;
      doKtoregoWiersza = r2;
      doKtorejKolumny = c2;
   }
   boolean czySkok() {
      return (zKtoregoWiersza - doKtoregoWiersza == 2 || zKtoregoWiersza - doKtoregoWiersza == -2);
   }
}
