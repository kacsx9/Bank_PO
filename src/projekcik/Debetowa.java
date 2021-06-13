package projekcik;

public class Debetowa extends Karta {
	//metody
	public boolean czyTransakcja(double kwota, double saldo)
	{
		if (kwota > saldo) return false;
        else return true;
	}
	
	//konstruktory
	public Debetowa(int numerKonta, String nazwaBanku, String pesel)
	{
		super(numerKonta, nazwaBanku, pesel);
	}
	public Debetowa(int numerKarty, int numerKonta, String nazwaBanku, String pesel)
	{
		super(numerKarty, numerKonta, nazwaBanku, pesel);
	}
}
