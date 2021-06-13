package projekcik;

public class Transakcja {
	//pola
	private int id;
	private String data;
	private double kwota;
	private boolean status;	
	private Karta kupujacy;
	private Firma sprzedajacy;
	
	//metody		
	
	
	//gettery
	public int getID()
	{
		return id;
	}
	public Firma getSprzedajacy()
	{
		return sprzedajacy;
	}
	public String getData()
	{
		return data;
	}
	public double getKwota()
	{
		return kwota;
	}
	public boolean getStatus()
	{
		return status;
	}
	public Karta getKarta()
	{
		return kupujacy;
	}
	
	//konstruktory
	public Transakcja(Firma sprzedajacy, double kwota, Karta kupujacy, String data, boolean status)
	{
		this.id = Centrum.getNumerTransakcji();
		Centrum.zwiekszIdTransakcji();
		this.sprzedajacy = sprzedajacy;
		this.data = data;
		this.kwota = kwota;
		this.kupujacy = kupujacy;
		this.status = status;
	}
	public Transakcja(int id, Firma sprzedajacy, double kwota, Karta kupujacy, String data, boolean status)
	{
		this.id = id;
		this.sprzedajacy = sprzedajacy;
		this.data = data;
		this.kwota = kwota;
		this.kupujacy = kupujacy;
		this.status = status;
	}
}
