import java.util.List;

public class Konto
{
	private String bezeichnung;
	private int geld;
	private List<Kunde> zeichnungsberechtigten;
	
	public int saldo()
	{
		return geld;
	}
	
	public void einzahlen( int geldBetrag )
	{
		geld = geldBetrag;
	}
}
