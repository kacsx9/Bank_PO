package projekcik;
import java.util.*;

public class Bank {
	//pola
		private String nazwa;
		private HashMap<String, Osoba> mapa_osob; //mapa klientow banku: key - pesel, value - klient;
		private boolean status = true;
		
		//metody
		public void dodajOsobe(Osoba osoba)
		{
			mapa_osob.put(osoba.getPesel(), osoba);
		}
		public void usunOsobe(Osoba osoba)
		{
			if(mapa_osob.containsKey(osoba.getPesel()));
				mapa_osob.remove(osoba.getPesel());
		}
		public int liczbaKlientow()
		{
			return mapa_osob.size();
		}
		public void wypiszKlientow ()
        {
			System.out.format("%-20s %-10s %-15s", "Pesel", "Imie", "Nazwisko" );
			System.out.println();
            mapa_osob.forEach( (key, value) -> {
            	System.out.format("%-20s %-10s %-15s", key, value.getImie(), value.getNazwisko() );
            	System.out.println();
            });
        }
		
		//gettery i settery
		public String getNazwa()
		{
			return nazwa;
		}
		public HashMap<String, Osoba> getOsoby()
		{
			return mapa_osob;
		}
		public Osoba getOsoba(String _pesel)
		{
			if(mapa_osob.containsKey(_pesel))
			{
				Osoba o = mapa_osob.get(_pesel);
                return o;
			}
			else return null;
		}
		public void setNazwa(String _nazwa)
		{
			this.nazwa = _nazwa;
		}
		public boolean getStatus()
		{
			return status;
		}
		public void setStatus(boolean status)
		{
			this.status = status;
		}
		
		//konstruktory
		public Bank(String nazwa)
		{
			this.nazwa = nazwa;
			this.mapa_osob = new HashMap<String, Osoba>();
		}
}
