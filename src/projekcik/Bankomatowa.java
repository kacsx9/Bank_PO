package projekcik;

public class Bankomatowa extends Karta {
	//metody
	public boolean czyTransakcja(double kwota)
	{
		return false;
	}
	
	//konstruktory
	public Bankomatowa(int numerKonta, String nazwaBanku, String pesel)
	{
		super(numerKonta, nazwaBanku, pesel);
	}
	public Bankomatowa(int numerKarty, int numerKonta, String nazwaBanku, String pesel)
	{
		super(numerKarty, numerKonta, nazwaBanku, pesel);
	}
}
