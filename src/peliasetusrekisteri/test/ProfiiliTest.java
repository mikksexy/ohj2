package peliasetusrekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import peliasetusrekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.07.24 15:13:16 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class ProfiiliTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi84 */
  @Test
  public void testRekisteroi84() {    // Profiili: 84
    Profiili allu1 = new Profiili(); 
    assertEquals("From: Profiili line: 86", 0, allu1.getTunnusNro()); 
    allu1.rekisteroi(); 
    Profiili allu2 = new Profiili(); 
    allu2.rekisteroi(); 
    int n1 = allu1.getTunnusNro(); 
    int n2 = allu2.getTunnusNro(); 
    assertEquals("From: Profiili line: 92", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString154 */
  @Test
  public void testToString154() {    // Profiili: 154
    Profiili profiili = new Profiili(); 
    profiili.parse("   2  |  allu | 2"); 
    assertEquals("From: Profiili line: 157", true, profiili.toString().startsWith("2|allu|2|")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse180 */
  @Test
  public void testParse180() {    // Profiili: 180
    Profiili profiili = new Profiili(); 
    profiili.parse("   2  |  allu | 2"); 
    assertEquals("From: Profiili line: 183", 2, profiili.getTunnusNro()); 
    assertEquals("From: Profiili line: 184", true, profiili.toString().startsWith("2|allu|2|")); 
    profiili.rekisteroi(); 
    int n = profiili.getTunnusNro(); 
    profiili.parse(""+(n+20)); 
    profiili.rekisteroi(); 
    assertEquals("From: Profiili line: 190", n+20+1, profiili.getTunnusNro()); 
  } // Generated by ComTest END
}