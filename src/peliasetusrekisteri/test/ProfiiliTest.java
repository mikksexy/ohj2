package peliasetusrekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import peliasetusrekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.06.29 21:45:36 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class ProfiiliTest {


  // Generated by ComTest BEGIN
  /** testRekisteroi54 */
  @Test
  public void testRekisteroi54() {    // Profiili: 54
    Profiili allu1 = new Profiili(); 
    assertEquals("From: Profiili line: 56", 0, allu1.getTunnusNro()); 
    allu1.rekisteroi(); 
    Profiili allu2 = new Profiili(); 
    allu2.rekisteroi(); 
    int n1 = allu1.getTunnusNro(); 
    int n2 = allu2.getTunnusNro(); 
    assertEquals("From: Profiili line: 62", n2-1, n1); 
  } // Generated by ComTest END
}