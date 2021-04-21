package rekisteri.test;
// Generated by ComTest BEGIN
import rekisteri.*;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.20 16:11:01 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class PaivatTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa62 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa62() throws SailoException {    // Paivat: 62
    Paivat paivat = new Paivat(); 
    Paiva maanantai = new Paiva(), tiistai = new Paiva(); 
    assertEquals("From: Paivat line: 66", 0, paivat.getLkm()); 
    paivat.lisaa(maanantai); assertEquals("From: Paivat line: 67", 1, paivat.getLkm()); 
    paivat.lisaa(tiistai); assertEquals("From: Paivat line: 68", 2, paivat.getLkm()); 
    paivat.lisaa(maanantai); assertEquals("From: Paivat line: 69", 3, paivat.getLkm()); 
    Iterator<Paiva> it = paivat.iterator(); 
    assertEquals("From: Paivat line: 72", maanantai, it.next()); 
    assertEquals("From: Paivat line: 73", tiistai, it.next()); 
    assertEquals("From: Paivat line: 74", maanantai, it.next()); 
    paivat.lisaa(maanantai); assertEquals("From: Paivat line: 75", 4, paivat.getLkm()); 
    paivat.lisaa(maanantai); assertEquals("From: Paivat line: 76", 5, paivat.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaLisaa92 
   * @throws SailoException when error
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaLisaa92() throws SailoException,CloneNotSupportedException {    // Paivat: 92
    Paivat paivat = new Paivat(); 
    Paiva maa1 = new Paiva(), tii1 = new Paiva(); 
    maa1.rekisteroi(); tii1.rekisteroi(); 
    assertEquals("From: Paivat line: 98", 0, paivat.getLkm()); 
    paivat.korvaaLisaa(maa1); assertEquals("From: Paivat line: 99", 1, paivat.getLkm()); 
    paivat.korvaaLisaa(tii1); assertEquals("From: Paivat line: 100", 2, paivat.getLkm()); 
    Paiva maa2 = maa1.clone(); 
    maa2.aseta(3,"kkk"); 
    Iterator<Paiva> it = paivat.iterator(); 
    assertEquals("From: Paivat line: 104", true, it.next() == maa1); 
    paivat.korvaaLisaa(maa2); assertEquals("From: Paivat line: 105", 2, paivat.getLkm()); 
    it = paivat.iterator(); 
    Paiva p0 = it.next(); 
    assertEquals("From: Paivat line: 108", maa2, p0); 
    assertEquals("From: Paivat line: 109", true, p0 == maa2); 
    assertEquals("From: Paivat line: 110", false, p0 == maa1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoista131 
   * @throws SailoException when error
   */
  @Test
  public void testPoista131() throws SailoException {    // Paivat: 131
    Paivat paivat = new Paivat(); 
    Paiva maa1 = new Paiva(), maa2 = new Paiva(), maa3 = new Paiva(); 
    maa1.rekisteroi(); maa2.rekisteroi(); maa3.rekisteroi(); 
    int id1 = maa1.getTunnusNro(); 
    paivat.lisaa(maa1); paivat.lisaa(maa2); paivat.lisaa(maa3); 
    assertEquals("From: Paivat line: 138", 1, paivat.poista(id1+1)); 
    assertEquals("From: Paivat line: 139", null, paivat.annaId(id1+1)); assertEquals("From: Paivat line: 139", 2, paivat.getLkm()); 
    assertEquals("From: Paivat line: 140", 1, paivat.poista(id1)); assertEquals("From: Paivat line: 140", 1, paivat.getLkm()); 
    assertEquals("From: Paivat line: 141", 0, paivat.poista(id1+3)); assertEquals("From: Paivat line: 141", 1, paivat.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta161 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta161() throws SailoException {    // Paivat: 161
    Paivat paivat = new Paivat(); 
    Paiva maanantai1 = new Paiva(), maanantai2 = new Paiva(); 
    maanantai1.paivanTiedot(); 
    maanantai2.paivanTiedot(); 
    String hakemisto = "testirekisteri"; 
    String tiedostoNimi = hakemisto+"/paivat"; 
    File ftied = new File(tiedostoNimi+".dat"); 
    File dir = new File(hakemisto); 
    dir.mkdir(); 
    ftied.delete(); 
    try {
    paivat.lueTiedostosta(tiedostoNimi); 
    fail("Paivat: 176 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    paivat.lisaa(maanantai1); 
    paivat.lisaa(maanantai2); 
    paivat.tallenna(); 
    paivat = new Paivat();  // Poistetaan vanhat luomalla uusi
    paivat.lueTiedostosta(tiedostoNimi);  // johon ladataan tiedot tiedostosta.
    Iterator<Paiva> i = paivat.iterator(); 
    assertEquals("From: Paivat line: 183", maanantai1, i.next()); 
    assertEquals("From: Paivat line: 184", maanantai2, i.next()); 
    assertEquals("From: Paivat line: 185", false, i.hasNext()); 
    paivat.lisaa(maanantai2); 
    paivat.tallenna(); 
    assertEquals("From: Paivat line: 188", true, ftied.delete()); 
    File fbak = new File(tiedostoNimi+".bak"); 
    assertEquals("From: Paivat line: 190", true, fbak.delete()); 
    assertEquals("From: Paivat line: 191", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEtsi376 */
  @Test
  public void testEtsi376() {    // Paivat: 376
    Paivat paivat = new Paivat(); 
    Paiva paiva1 = new Paiva(); paiva1.parse("1|Lempäälä|10:00|20 C|"); 
    Paiva paiva2 = new Paiva(); paiva2.parse("2|Vihti||11:00|"); 
    Paiva paiva3 = new Paiva(); paiva3.parse("3|Paltamo|12:00||131313|19 C"); 
    Paiva paiva4 = new Paiva(); paiva4.parse("4|Jyväskylä|13:00|5 C"); 
    Paiva paiva5 = new Paiva(); paiva5.parse("5|Puuhamaa|14:00|15 C"); 
    paivat.lisaa(paiva1); paivat.lisaa(paiva2); paivat.lisaa(paiva3); paivat.lisaa(paiva4); paivat.lisaa(paiva5); 
    List<Paiva> loytyneet; 
    loytyneet = (List<Paiva>)paivat.etsi("*l*",1); 
    assertEquals("From: Paivat line: 389", 3, loytyneet.size()); 
    assertEquals("From: Paivat line: 390", true, loytyneet.get(0) == paiva4); 
    assertEquals("From: Paivat line: 391", true, loytyneet.get(1) == paiva1); 
    assertEquals("From: Paivat line: 392", true, loytyneet.get(2) == paiva3); 
    loytyneet = (List<Paiva>)paivat.etsi("*Pal*",1); 
    assertEquals("From: Paivat line: 395", 1, loytyneet.size()); 
    assertEquals("From: Paivat line: 396", true, loytyneet.get(0) == paiva3); 
    loytyneet = (List<Paiva>)paivat.etsi(null,-1); 
    assertEquals("From: Paivat line: 400", 5, loytyneet.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnnaId420 
   * @throws SailoException when error
   */
  @Test
  public void testAnnaId420() throws SailoException {    // Paivat: 420
    Paivat paivat = new Paivat(); 
    Paiva maa1 = new Paiva(), maa2 = new Paiva(), maa3 = new Paiva(); 
    maa1.rekisteroi(); maa2.rekisteroi(); maa3.rekisteroi(); 
    int id1 = maa1.getTunnusNro(); 
    paivat.lisaa(maa1); paivat.lisaa(maa2); paivat.lisaa(maa3); 
    assertEquals("From: Paivat line: 427", true, paivat.annaId(id1  ) == maa1); 
    assertEquals("From: Paivat line: 428", true, paivat.annaId(id1+1) == maa2); 
    assertEquals("From: Paivat line: 429", true, paivat.annaId(id1+2) == maa3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsiId444 
   * @throws SailoException when error
   */
  @Test
  public void testEtsiId444() throws SailoException {    // Paivat: 444
    Paivat paivat = new Paivat(); 
    Paiva maa1 = new Paiva(), maa2 = new Paiva(), maa3 = new Paiva(); 
    maa1.rekisteroi(); maa2.rekisteroi(); maa3.rekisteroi(); 
    int id1 = maa1.getTunnusNro(); 
    paivat.lisaa(maa1); paivat.lisaa(maa2); paivat.lisaa(maa3); 
    assertEquals("From: Paivat line: 451", 1, paivat.etsiId(id1+1)); 
    assertEquals("From: Paivat line: 452", 2, paivat.etsiId(id1+2)); 
  } // Generated by ComTest END
}