package projekcik;

public abstract class Karta {
	//pola
	protected int numerKarty, numerKonta;
	protected String nazwaBanku;//nazwa banku, do którego przypisana jest karta;
	protected String pesel;//pesel wlasciciela karty
	
	public boolean czyTransakcja(double kwota, double saldo)
	{
		return false;
	}
	
	//gettery i settery
	public String getPesel()
	{
		return pesel;
	}
	public String getNazwaBanku()
	{
		return nazwaBanku;
	}
	public int getNumerKarty()
	{
		return numerKarty;
	}
	public int getNumerKonta()
	{
		return numerKonta;
	}
	
	//kontruktor
	public Karta(int numerKonta, String nazwaBanku, String pesel)
	{
		this.nazwaBanku = nazwaBanku;
		this.numerKonta = numerKonta;
		this.pesel = pesel;
		this.numerKarty = Centrum.getNumerKarty();
		Centrum.zwiekszNumerKarty();		
	}
	public Karta(int numerKarty, int numerKonta, String nazwaBanku, String pesel)
	{
		this.nazwaBanku = nazwaBanku;
		this.numerKonta = numerKonta;
		this.pesel = pesel;
		this.numerKarty = numerKarty;
	}
}
