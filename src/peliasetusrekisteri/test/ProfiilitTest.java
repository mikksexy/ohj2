package peliasetusrekisteri.test;
// Generated by ComTest BEGIN
import java.io.File;
import peliasetusrekisteri.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.07.24 14:54:34 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class ProfiilitTest {



  // Generated by ComTest BEGIN
  /** testLisaa34 */
  @Test
  public void testLisaa34() {    // Profiilit: 34
    Profiilit profiilit = new Profiilit(); 
    Profiili allu1 = new Profiili(), allu2 = new Profiili(); 
    assertEquals("From: Profiilit line: 37", 0, profiilit.getLkm()); 
    profiilit.lisaa(allu1); assertEquals("From: Profiilit line: 38", 1, profiilit.getLkm()); 
    profiilit.lisaa(allu2); assertEquals("From: Profiilit line: 39", 2, profiilit.getLkm()); 
    profiilit.lisaa(allu1); assertEquals("From: Profiilit line: 40", 3, profiilit.getLkm()); 
    assertEquals("From: Profiilit line: 41", allu1, profiilit.anna(0)); 
    assertEquals("From: Profiilit line: 42", allu2, profiilit.anna(1)); 
    assertEquals("From: Profiilit line: 43", allu1, profiilit.anna(2)); 
    assertEquals("From: Profiilit line: 44", false, profiilit.anna(1) == allu1); 
    assertEquals("From: Profiilit line: 45", true, profiilit.anna(1) == allu2); 
    try {
    assertEquals("From: Profiilit line: 46", allu1, profiilit.anna(3)); 
    fail("Profiilit: 46 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    profiilit.lisaa(allu1); assertEquals("From: Profiilit line: 47", 4, profiilit.getLkm()); 
    profiilit.lisaa(allu1); assertEquals("From: Profiilit line: 48", 5, profiilit.getLkm()); 
    profiilit.lisaa(allu1); assertEquals("From: Profiilit line: 49", 6, profiilit.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta82 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta82() throws SailoException {    // Profiilit: 82
    Profiilit profiilit = new Profiilit(); 
    Profiili allu1 = new Profiili(), allu2 = new Profiili(); 
    allu1.taytaAlluTiedoilla(); 
    allu2.taytaAlluTiedoilla(); 
    File tied = new File("profiilit.dat"); 
    tied.delete(); 
    try {
    profiilit.lueTiedostosta(); 
    fail("Profiilit: 92 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    profiilit.lisaa(allu1); 
    profiilit.lisaa(allu2); 
    profiilit.tallenna(); 
    profiilit = new Profiilit();  // Poistetaan vanhat luomalla uusi
    profiilit.lueTiedostosta();  // johon ladataan tiedot tiedostosta.
    Iterator<Profiili> i = profiilit.iterator(); 
    assertEquals("From: Profiilit line: 99", allu1, i.next()); 
    assertEquals("From: Profiilit line: 100", allu2, i.next()); 
    assertEquals("From: Profiilit line: 101", false, i.hasNext()); 
    profiilit.lisaa(allu2); 
    profiilit.tallenna(); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testProfiilitIterator189 
   * @throws SailoException when error
   */
  @Test
  public void testProfiilitIterator189() throws SailoException {    // Profiilit: 189
    Profiilit profiilit = new Profiilit(); 
    Profiili allu1 = new Profiili(), allu2 = new Profiili(); 
    allu1.rekisteroi(); allu2.rekisteroi(); 
    profiilit.lisaa(allu1); 
    profiilit.lisaa(allu2); 
    profiilit.lisaa(allu1); 
    StringBuilder ids = new StringBuilder(30); 
    for (Profiili profiili:profiilit) // Kokeillaan for-silmukan toimintaa
    ids.append(" "+profiili.getTunnusNro()); 
    String tulos = " " + allu1.getTunnusNro() + " " + allu2.getTunnusNro() + " " + allu1.getTunnusNro(); 
    assertEquals("From: Profiilit line: 208", tulos, ids.toString()); 
    ids = new StringBuilder(30); 
    for (Iterator<Profiili>  i=profiilit.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
    Profiili profiili = i.next(); 
    ids.append(" "+profiili.getTunnusNro()); 
    }
    assertEquals("From: Profiilit line: 216", tulos, ids.toString()); 
    Iterator<Profiili>  i=profiilit.iterator(); 
    assertEquals("From: Profiilit line: 219", true, i.next() == allu1); 
    assertEquals("From: Profiilit line: 220", true, i.next() == allu2); 
    assertEquals("From: Profiilit line: 221", true, i.next() == allu1); 
    try {
    i.next(); 
    fail("Profiilit: 223 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi283 
   * @throws SailoException when error
   */
  @Test
  public void testEtsi283() throws SailoException {    // Profiilit: 283
    Profiilit profiilit = new Profiilit(); 
    Profiili pro1= new Profiili(); pro1.parse("1|s1mple|1|3.09"); 
    Profiili pro2 = new Profiili(); pro2.parse("2|allu|2|3.3"); 
    Profiili pro4 = new Profiili(); pro4.parse("4|ropz|3|1.77"); 
    Profiili pro5 = new Profiili(); pro5.parse("5|Jamppi|2|1.25"); 
    profiilit.lisaa(pro1); profiilit.lisaa(pro2); profiilit.lisaa(pro4); profiilit.lisaa(pro5); 
  } // Generated by ComTest END
}