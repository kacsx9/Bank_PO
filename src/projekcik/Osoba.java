package projekcik;

import java.util.*;

public class Osoba {
	//pola
	private String imie, nazwisko, pesel;
	private HashMap<Integer, Konto> mapa_kont; // mapa kont klienta: key - numer konta, value - konto;
	
	//metody
	public void dodajKonto(Konto konto)
	{
		mapa_kont.put(konto.getNumerKonta(), konto);
	}
	public void usunKonto(Konto konto)
	{
		if(mapa_kont.containsKey(konto.getNumerKonta()));
			mapa_kont.remove(konto.getNumerKonta());
	}
	public int liczbaKont()
	{
		return mapa_kont.size();
	}
	public void wypiszKonta()
	{
		System.out.format("%-10s %-10s", "Nr Konta", "Saldo" );
		System.out.println();
		mapa_kont.forEach( (key, value) -> {
			System.out.format("%-10d %-10.2f", key, value.getSaldo() );
			System.out.println();
		});
	}
	
	//gettery i settery
	public String getImie()
	{
		return imie;
	}
	public String getNazwisko()
	{
		return nazwisko;
	}
	public String getPesel()
	{
		return pesel;
	}
	public HashMap<Integer, Konto> getKonta()
	{
		return mapa_kont;
	}
	public Konto getKonto(int nrKonta)
	{
		if(mapa_kont.containsKey(nrKonta))
		{
			return mapa_kont.get(nrKonta);
		}
		else return null;
	}
	
	//konstruktory
	public Osoba(String _pesel, String _imie, String _nazwisko)
	{
		this.imie = _imie;
		this.nazwisko = _nazwisko;
		this.pesel = _pesel;
		mapa_kont = new HashMap<Integer, Konto>();
	}
	
}
