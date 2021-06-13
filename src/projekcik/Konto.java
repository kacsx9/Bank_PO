package projekcik;

import java.util.*;

public class Konto {
	//pola
	private int numerKonta;
	private double saldo;
	private HashMap<Integer, Karta> mapa_kart; //mapa kart przypisanych do konta: key - nr karty, value - karta;
	
	//metody
	public void dodajKarte(Karta karta)
	{
		mapa_kart.put(karta.getNumerKarty(), karta);
	}
	public void usunKarte(Karta karta)
	{
		if( mapa_kart.containsKey(karta.getNumerKarty()) )
			mapa_kart.remove(karta.getNumerKarty());
	}
	public void wplac(double kwota)
	{
		saldo += kwota;
	}
	public void wyplac(double kwota)
	{
		saldo -= kwota;
	}
	public void usunWszystkieKarty()
	{
		mapa_kart.clear();
	}
	public void wypiszKarty()
	{
		System.out.format("%-10s %-10s", "Nr Karty", "Debet" );
		System.out.println();
		double[] debet = {0};
		mapa_kart.forEach( (key, value) -> {
			if (value instanceof Kredytowa) 
				debet[0] = ((Kredytowa) value).getLimit();
			else debet[0] = 0;
			System.out.format("%-10d %-10f", key, debet[0] );
			System.out.println();
		});
	}
	
	//gettery i settery
	public int getNumerKonta()
	{
		return numerKonta;
	}
	public HashMap<Integer, Karta> getKarty()
	{
		return mapa_kart;
	}
	public double getSaldo()
	{
		return saldo;
	}
	public int liczbaKart()
	{
		return mapa_kart.size();
	}
	public Karta getKarta(int nrKarty)
	{
		return mapa_kart.get(nrKarty);
	}
	//konstruktory
	public Konto()
	{
		this.saldo = 0;
		numerKonta = Centrum.getNumerKonta();
		Centrum.zwiekszNumerKonta();
		mapa_kart = new HashMap<Integer, Karta>();
	}
	public Konto(int numerKonta, double saldo)
	{
		this.saldo = saldo;
		this.numerKonta = numerKonta;
		mapa_kart = new HashMap<Integer, Karta>();
	}
}
