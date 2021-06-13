package projekcik;

public class Kredytowa extends Karta {
	//pola
	private double limit;
	
	//metody
	public boolean czyTransakcja(double kwota, double saldo)
	{
		if(kwota > (saldo + limit)) return false;
		else return true;
	}
	
	//gettery i settery
	public double getLimit()
	{
		return limit;
	}
	public void setLimit(double _limit)
	{
		limit = _limit;
	}
	
	//konstruktory
	public Kredytowa(int numerKonta, String nazwaBanku, String pesel, double _limit)
	{
		super(numerKonta, nazwaBanku, pesel);
		this.limit = _limit;
	}
	public Kredytowa(int numerKarty, int numerKonta, String nazwaBanku, String pesel, double limit)
	{
		super(numerKarty, numerKonta, nazwaBanku, pesel);
		this.limit = limit;
	}
}
