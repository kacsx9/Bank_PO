//Projekt z PO - temat nr 4(Centrum obs³ugi kart p³atniczych)
//Autorzy: ¯ochowiec Weronika, Kozikowski Micha³, Siegieñczuk Kacper; PS8
package projekcik;

import java.util.*;
import java.io.*;

public class Centrum {
	private static int NumerKonta = 62538, NumerKarty = 725838, numerTransakcji=1; //Numer ostatnio utworzonego konta, transakcji i karty; 
	private HashMap<Integer, Transakcja> transakcje;
	private HashMap<String, Bank> banki; //mapa bankow: key - nazwa banku, value - bank;
	private HashMap<String, Firma> firmy; //mapa firm: key - numer KRS, value - firma;
	
	//metody		 
	 public boolean dodajTransakcje (Karta karta, Firma sprzedajacy, double kwota, String data)
     {
		 Bank bank = banki.get(karta.getNazwaBanku());
		 Osoba klient = bank.getOsoba(karta.getPesel());
		 Konto konto = klient.getKonto(karta.getNumerKonta());
		 double saldo = konto.getSaldo();
		 boolean status = karta.czyTransakcja(kwota, saldo);
		 if (status == true)
			 konto.wyplac(kwota);
		 Transakcja t = new Transakcja(sprzedajacy, kwota, karta, data, status);
		 transakcje.put(t.getID(), t);
		 return status;
     }

     public void usunTransakcje (int ID)
     {
    	 Transakcja t; 
    	 if(transakcje.containsKey(ID))
    	 {
    		 t = transakcje.get(ID);
    		 if (t.getStatus() == true)
             {
                 Karta k = t.getKarta();
                 String nazwabanku = k.getNazwaBanku();
                 String pesel = k.getPesel();
                 double kwota = t.getKwota();

                 Bank b = banki.get(nazwabanku);
                 Osoba klient = b.getOsoba(pesel);
                 int nrkonta = k.getNumerKonta();
                 Konto c = klient.getKonto(nrkonta);
                 c.wplac(kwota);
             }
    		 transakcje.remove(ID);
    	 }
     }

     public void dodajBank (String nazwa)
     {
         Bank bank = new Bank(nazwa);
         banki.put(nazwa, bank);
     }

     public void usunBank (String nazwa)
     {
    	 if(banki.containsKey(nazwa))
    		 banki.remove(nazwa);
     }

     public void dodajFirmeUslugowa (String krs, String nazwa)
     {
         Uslugowa firma = new Uslugowa(krs, nazwa);
         firmy.put(krs, firma);
     }
     
     public void dodajSklep (String krs, String nazwa)
     {
         Sklep firma = new Sklep(krs, nazwa);
         firmy.put(krs, firma);
     }
     
     public void dodajFirmeTransportowa (String krs, String nazwa)
     {
         Transportowa firma = new Transportowa(krs, nazwa);
         firmy.put(krs, firma);
     }

     public void UsunFirme (String krs)
     {
    	 if (firmy.containsKey(krs))
    		 firmy.remove(krs);
     }
     
     public int liczbaBankow()
     {
    	 return banki.size();
     }
     public Karta wyszukiwanieKarty (int nr)
     {
    	 Integer numerek = new Integer (nr);
    	 Set<String> nazwaBanku = banki.keySet();
    	 for(String n: nazwaBanku)
    	 {
    		 Bank bank = banki.get(n);
    		 HashMap<String, Osoba> osoby = bank.getOsoby();
    		 Set<String> pesel = osoby.keySet();
    		 for (String o: pesel)
    		 {
    			 Osoba osoba = osoby.get(o);
    			 HashMap<Integer, Konto> konta = osoba.getKonta();
    			 Set<Integer> nrKonta = konta.keySet();
    			 for (Integer k: nrKonta)
    			 {
    				 Konto konto = konta.get(k);
    				 HashMap<Integer, Karta> karty = konto.getKarty();
    				 Set<Integer> nrKarty = karty.keySet();
    				 for (Integer karta: nrKarty)
    				 {
    					 if (karta.equals(numerek)) return karty.get(karta);
    				 }
    			 }
    		 }
    	 }
    	 return null;
     }
	
	//gettery i settery
     public Transakcja getTransakcja(int ID)
     {
    	 if (transakcje.containsKey(ID))
    		 return transakcje.get(ID);
    	 else return null;
     }
     
     public HashMap<Integer, Transakcja> getTransakcje()
     {
    	 return transakcje;
     }
     
     public Bank getBank(String nazwa)
     {
    	 if (banki.containsKey(nazwa))
    		 return banki.get(nazwa);
    	 else return null;
     }
     
     public HashMap<String, Bank> getBanki()
     {
    	 return banki;
     }
     
     public Firma getFirma(String krs)
     {
    	 if (firmy.containsKey(krs))
    		 return firmy.get(krs);
    	 else return null;
     }
     
     public HashMap<String, Firma> getFirmy()
     {
    	 return firmy;
     }
     
     //Wczytywanie i zapisywanie danych do plików
     
     public void wczytaj_z_plikow() throws Exception
     {
    	 Scanner plikBanki = new Scanner(new File("banki.txt"));
    	 Scanner plikKlienci = new Scanner(new File("klienci.txt"));
    	 Scanner plikKonta = new Scanner(new File("konta.txt"));
    	 Scanner plikKarty = new Scanner(new File("karty.txt"));
    	 plikBanki.useLocale(Locale.US);
    	 plikKlienci.useLocale(Locale.US);
    	 plikKonta.useLocale(Locale.US);
    	 plikKarty.useLocale(Locale.US);
    	 String nazwa;
    	 int lKlientow, lKont, lKart;
    	 char typ;
    	 Bank bank;
    	 Osoba klient;
    	 Konto konto;
    	 Kredytowa kredytowa;
    	 Debetowa debetowa;
    	 Bankomatowa bankomatowa;
    	 String pesel;
    	 while(plikBanki.hasNext()) {
    		 try
    		 {
    			 nazwa = plikBanki.next(); lKlientow = plikBanki.nextInt();
	    		 bank = new Bank(nazwa);
	    		 banki.put(nazwa, bank);
	        	 for (int i=0; i<lKlientow; i++)
	       		 {
	       			 klient = new Osoba( plikKlienci.next(), plikKlienci.next(), plikKlienci.next() );
	       			 bank.dodajOsobe(klient);
	       			 lKont = plikKlienci.nextInt();
	       			 for (int j=0; j<lKont; j++)
	       			 {
	       				 konto = new Konto( plikKonta.nextInt(), plikKonta.nextDouble() );
	       				 klient.dodajKonto(konto);
	       				 lKart = plikKonta.nextInt();
	       				 for (int k=0; k<lKart; k++)
	       				 {
	       					 typ = plikKarty.next().charAt(0);
	       					 switch(typ)
	       					 {
	       					 case 'K':
	       						 kredytowa = new Kredytowa( plikKarty.nextInt(), plikKarty.nextInt(), plikKarty.next(), plikKarty.next(), plikKarty.nextDouble() );
	       						 konto.dodajKarte(kredytowa);
	       						 break;
	       					 case 'D':
	       						 debetowa = new Debetowa( plikKarty.nextInt(), plikKarty.nextInt(), plikKarty.next(), plikKarty.next() );
	       						 konto.dodajKarte(debetowa);
	       						 break;
	       					 case 'B':
	       						 bankomatowa = new Bankomatowa( plikKarty.nextInt(), plikKarty.nextInt(), plikKarty.next(), plikKarty.next() );
	       						 konto.dodajKarte(bankomatowa);
	       						 break;
	       					 }
	       				 }       				 
	        		}
	        	 } 
    		 }
    		 catch (NullPointerException npe) { }
    	 }
    	 plikBanki.close();
    	 plikKlienci.close();
    	 plikKonta.close();
    	 plikKarty.close();
    	 
    	 Scanner plikTransportowe = new Scanner(new File("transportowe.txt"));
    	 plikTransportowe.useLocale(Locale.US);
    	 Transportowa transportowa;
    	 while(plikTransportowe.hasNext()) {
    		 try
    		 {
    			 transportowa = new Transportowa( plikTransportowe.next(), plikTransportowe.next() );
    			 firmy.put(transportowa.getKRS(), transportowa);
    		 }
    		 catch (NullPointerException npe) { }
    		 
    	 }
    	 plikTransportowe.close();
    	 
    	 Scanner plikUslugowe = new Scanner(new File("uslugowe.txt"));
    	 plikUslugowe.useLocale(Locale.US);
    	 Uslugowa uslugowa;
    	 while(plikUslugowe.hasNext()) {
    		 try
    		 {
    			 uslugowa = new Uslugowa( plikUslugowe.next(), plikUslugowe.next() );
    			 firmy.put(uslugowa.getKRS(), uslugowa);
    		 }
    		 catch (NullPointerException npe) { }
    		 
    	 }
    	 plikUslugowe.close();
    	 
    	 Scanner plikSklepy = new Scanner(new File("sklepy.txt"));
    	 plikSklepy.useLocale(Locale.US);
    	 Sklep sklep;
    	 while(plikSklepy.hasNext()) {
    		 try
    		 {
    			 sklep = new Sklep( plikSklepy.next(), plikSklepy.next() );
    			 firmy.put(sklep.getKRS(), sklep);
    		 }
    		 catch (NullPointerException npe) { }
    		 
    	 }
    	 plikSklepy.close();
    	 
    	 Scanner plikArchiwum = new Scanner(new File("archiwum.txt"));
    	 plikArchiwum.useLocale(Locale.US);
    	 Transakcja transakcja;
    	 int id, numerKarty, nrKonta;
    	 String krs_firmy, data, nazwaBanku;
    	 double kwota;
    	 boolean status;
    	 Karta karta;
    	 Firma firma;
    	 while(plikArchiwum.hasNext())
    	 {
    		try
    		{
    			id = plikArchiwum.nextInt(); 
    			krs_firmy = plikArchiwum.next(); 
    			data = plikArchiwum.next();
        		kwota = plikArchiwum.nextDouble(); 
        		numerKarty = plikArchiwum.nextInt(); 
        		nrKonta = plikArchiwum.nextInt();
        		pesel = plikArchiwum.next();  
        		nazwaBanku = plikArchiwum.next(); 
        		if (plikArchiwum.next().charAt(0) == 'W') status = true;
        		else status = false;
        		bank = banki.get(nazwaBanku);
        		klient = bank.getOsoba(pesel);
        		konto = klient.getKonto(nrKonta);
        		karta = konto.getKarta(numerKarty);
        		firma = firmy.get(krs_firmy);
        		transakcja = new Transakcja(firma, kwota, karta, data, status);
        		transakcje.put(id, transakcja);
    		}
    		catch (NullPointerException npe) { }
    	 }
    	 plikArchiwum.close();
    	 
    	 Scanner plikNumery = new Scanner(new File("numery.txt"));
    	 plikNumery.useLocale(Locale.US);
    	 NumerKonta = plikNumery.nextInt();
    	 NumerKarty = plikNumery.nextInt();
    	 numerTransakcji = plikNumery.nextInt();
    	 plikNumery.close();
     }
     
     public void zapisz_do_plikow() throws Exception
     {
    	 PrintWriter plikBanki = new PrintWriter("banki.txt");
    	 PrintWriter plikKlienci = new PrintWriter("klienci.txt");
    	 PrintWriter plikKonta = new PrintWriter("konta.txt");
    	 PrintWriter plikKarty = new PrintWriter("karty.txt");
    		 banki.forEach((key, value) -> {
    	   	    plikBanki.println( key+" "+value.liczbaKlientow() );
    	 	    HashMap<String, Osoba> klienci = value.getOsoby();
    	 	    klienci.forEach( (key1, value1) -> {
    	  		   	plikKlienci.println( key1+" "+value1.getImie()+" "+value1.getNazwisko()+" "+value1.liczbaKont() );
    	   		   	HashMap<Integer, Konto> konta = value1.getKonta();
    	   		   	konta.forEach( (key2, value2) -> {
    	   		   		plikKonta.println( key2+" "+value2.getSaldo()+" "+value2.liczbaKart() );
    	   		   		HashMap<Integer, Karta> karty = value2.getKarty();
    	   		   		karty.forEach( (key3, value3) -> {
    	   		   			if( value3 instanceof Kredytowa) 
    	   		   			{
    	   		   				Kredytowa value4 = (Kredytowa)value3;
    	   		   				plikKarty.println( "K "+key3+" "+value2.getNumerKonta()+" "+value.getNazwa()+" "+value1.getPesel()+" "+value4.getLimit() );   		    					
    	   		   			}
    	   		   			else if ( value3 instanceof Debetowa)
    	   		   			{
    	   		   				plikKarty.println( "D "+key3+" "+value2.getNumerKonta()+" "+value.getNazwa()+" "+value1.getPesel() );  
    	   		   			}
    	   		   			else
    	   		   			{
    	   		   				plikKarty.println( "B "+key3+" "+value2.getNumerKonta()+" "+value.getNazwa()+" "+value1.getPesel() );  
    	   		   			} 			
    	   		    	});
    	   		    });
    	 	    });    		     
    		 });  	 
    	 plikBanki.close();
    	 plikKlienci.close();
    	 plikKonta.close();
    	 plikKarty.close();
    	 
    	 PrintWriter plikUslugowe = new PrintWriter("uslugowe.txt");
    	 PrintWriter plikSklepy = new PrintWriter("sklepy.txt");
    	 PrintWriter plikTransportowe = new PrintWriter("transportowe.txt");
    	 firmy.forEach((k, v) -> {
    		 if (v instanceof Uslugowa)
    			 plikUslugowe.println(k+" "+v.getNazwa() );
    		 else if (v instanceof Sklep)
    			 plikSklepy.println(k+" "+v.getNazwa());
    		 else if(v instanceof Transportowa)
    			 plikTransportowe.println(k+" "+v.getNazwa());    		 
    	 });
    	 plikUslugowe.close();
    	 plikSklepy.close();
    	 plikTransportowe.close();
    	 
    	 PrintWriter archiwum = new PrintWriter("archiwum.txt");
    	 transakcje.forEach((k, v) -> {
    		 char x;
    		 if (v.getStatus() == true) x = 'W';
    		 else x = 'N';
    		 archiwum.println(k+" "+v.getSprzedajacy().getKRS()+" "+v.getData()+" "+v.getKwota()+" "+v.getKarta().getNumerKarty()+" "+ +v.getKarta().getNumerKonta()+" "+v.getKarta().getPesel()+" "+v.getKarta().getNazwaBanku()+" "+x);
    	 });
    	 archiwum.close();
    	 
    	 PrintWriter plikNumery = new PrintWriter("numery.txt");
    	 plikNumery.println(NumerKonta);
    	 plikNumery.println(NumerKarty);
    	 plikNumery.println(numerTransakcji);
    	 plikNumery.close();
     }
     
     
     //Przeszukiwanie archiwum
     public HashMap<Integer, Transakcja> wyszukajTransakcje (HashMap <Integer, Transakcja> mapka)
     {
    	 Scanner skaner = new Scanner(System.in);
    	 skaner.useLocale(Locale.US);
    	 int wybor;
         HashMap<Integer, Transakcja> wyszukane = new HashMap<Integer, Transakcja>();
         System.out.print("Za pomoc¹ czego chcesz wyszukaæ? \n\n");
         System.out.print("[1] - Firmy sprzedajacej \n");
         System.out.print("[2] - Numer Karty kupujacego \n");
         System.out.print("[3] - Pesel kupuj¹cego \n");
         System.out.print("[4] - Nazwa banku \n");
         System.out.print("[5] - Kwota transakcji \n\n");
         System.out.print("Wybor: ");
         wybor = skaner.nextInt();
         String kryterium;
         int kryterium1;
         double kryterium2;
         switch(wybor)
         {
             case 1:
            	 System.out.print("Podaj KRS firmy: ");
                 kryterium = skaner.next();
                 mapka.forEach( (key, value) -> {
                	 if ( value.getSprzedajacy().getKRS().equals(kryterium) )
                		 wyszukane.put(key, value);
                 });
                 break;
             case 2:
            	 System.out.print("Podaj numer karty: ");
                 kryterium1 = skaner.nextInt();
                 mapka.forEach( (key, value) -> {
                	 if ( value.getKarta().getNumerKarty() == kryterium1 )
                		 wyszukane.put(key, value);
                 });
                 break;
             case 3:
                 System.out.print("Podaj pesel kupujacego: ");
                 kryterium = skaner.next();
                 mapka.forEach( (key, value) -> {
                	 if ( value.getKarta().getPesel().equals(kryterium) )
                		 wyszukane.put(key, value);
                 });
                 break;
             case 4:
                 System.out.print("Podaj nazwe banku: ");
                 kryterium = skaner.next();
                 mapka.forEach( (key, value) -> {
                	 if ( value.getKarta().getNazwaBanku().equals(kryterium) )
                		 wyszukane.put(key, value);
                 });    
                 break; 
             case 5:
                System.out.print("Podaj kwote transakcji: ");
                kryterium2 = skaner.nextDouble();
                mapka.forEach( (key, value) -> {
               	 if ( value.getKwota() == kryterium2 )
               		 wyszukane.put(key, value);
                });
             default: System.out.println("Brak podanej opcji w menu");
                 break;
         }
         //skaner.close();
         return wyszukane;
     }
	
	
	//metody do generowania numerow karty i konta
	public static int getNumerKonta()
	{
		return NumerKonta;
	}
	public static int getNumerKarty()
	{
		return NumerKarty;
	}
	public static int getNumerTransakcji()
    {
        return numerTransakcji;
    }
	public static void zwiekszNumerKonta()
	{
		NumerKonta++;
	}
	public static void zwiekszNumerKarty()
	{
		NumerKarty++;
	}
	public static void zwiekszIdTransakcji()
    {
        numerTransakcji++;
    }
	
	//konstruktory
	public Centrum()
	{
		transakcje = new HashMap<Integer, Transakcja>();
		banki = new HashMap<String, Bank>();
		firmy = new HashMap<String, Firma>();
	}	
	
	//wypisywanie menu glownego
	public void wypiszMenu()
	{
		System.out.println("__________________________________________");
		System.out.print("MENU GLOWNE\n\n");
		System.out.print("[1] - Przegladaj spis bankow \n");
		System.out.print("[2] - Przegladaj spis firm \n");
		System.out.print("[3] - Szukaj w archiwum transakcji \n");
		System.out.print("[4] - Dodaj \n");
		System.out.print("[5] - Usun \n");
		System.out.print("[6] - Dokonaj wplaty/wyplaty \n");
		System.out.print("[7] - Wykonaj Transakcje \n");
		System.out.print("[8] - Zapisz dane do plikow \n");
		System.out.print("[0] - Zakoncz program \n \n");
		System.out.print("Wybor:");
	}
	
	//wypisywanie tabeli(tabel?)
	public void wypiszBanki()
    {
		System.out.format("%-4s %-15s %-11s", "Lp.", "Nazwa banku", "Status");
		System.out.println();
		int[] i= {1};
        banki.forEach( (key, value) -> {
        	System.out.format("%-4d %-15s", i[0], key);
        	if (value.getStatus() == true)
        		System.out.format("%-11s", "Aktywny");
        	else
        		System.out.format("%-11s", "Nieaktywny");
        	System.out.println();
        	i[0]++;
        });
    }
	public void wypiszTransakcje()
	{
		System.out.format("%-4s %-11s %-8s %-10s %-12s %-11s ", "ID", "KRS firmy", "Kwota", "Nr Karty", "Data", "Status" );
		System.out.println();
		transakcje.forEach( (key, value) -> {
			System.out.format("%-4d %-11s %-8.2f %-10d %-12s ", value.getID(), value.getSprzedajacy().getKRS(), value.getKwota(), value.getKarta().getNumerKarty(), value.getData() );
			if (value.getStatus() == true)
				System.out.format("%-11s", "Wykonana");
			else
				System.out.format("%-11s", "Niewykonana");
			System.out.println();
		});
	}
	public void wypiszPrzefiltrowane(HashMap<Integer, Transakcja> mapka)
	{
		if (mapka != null)
		{
			System.out.format("%-4s %-11s %-8s %-10s %-12s %-11s", "ID", "KRS firmy", "Kwota", "Nr Karty", "Data", "Status" );
			System.out.println();
			mapka.forEach( (key, value) -> {
				System.out.format("%-4d %-11s %-8.2f %-10d %-12s ", value.getID(), value.getSprzedajacy().getKRS(), value.getKwota(), value.getKarta().getNumerKarty(), value.getData() );
				if (value.getStatus() == true)
					System.out.format("%-11s", "Wykonana");
				else
					System.out.format("%-11s", "Niewykonana");
				System.out.println();
			});
		}
		else
			System.out.println("Nie znalezniono wyników dla podanych kryteriów");
		
	}
	public void wypiszFirmy()
	{
		System.out.format("%-11s %-20s %-15s", "KRS firmy", "Nazwa firmy", "Typ firmy" );
		System.out.println();
		firmy.forEach( (key, value) -> {
			System.out.format("%-11s %-20s", value.getKRS(), value.getNazwa() );
			if (value instanceof Transportowa)
				System.out.format("%-15s", "Transportowa");
			else if (value instanceof Sklep)
				System.out.format("%-15s", "Sklep");
			else
				System.out.format("%-15s", "Uslugowa");
			System.out.println();
		});
	}

	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		scan.useLocale(Locale.US);
		Centrum centrum = new Centrum();
		centrum.wczytaj_z_plikow();
		while( true )
		{
			centrum.wypiszMenu();
			switch( scan.nextInt() )
			{
				case 1: //wypisywanie bankow i ich komponentow
					centrum.wypiszBanki();
					System.out.println("Chcesz wyswietlic klientow danego banku?");
					System.out.println("[1] - TAK.");
					System.out.println("[0] - NIE (Wroc do menu).");
					if( scan.nextInt() == 1 )
					{
						System.out.println("Podaj nazwe banku:");
						String nazwa_banku;
						nazwa_banku = scan.next();
						Bank bank = centrum.getBank(nazwa_banku);
						if( bank == null )
						{
							System.out.println("Nie ma takiego banku!");
							System.out.println("Wracasz do menu glownego, jak nie umiesz pisac.");
							break;
						}	
						bank.wypiszKlientow();
						System.out.println("Chcesz wyswietlic konta danego klienta?");
						System.out.println("[1] - TAK.");
						System.out.println("[0] - NIE (Wroc do menu).");
						if( scan.nextInt() == 1 )
						{
							System.out.println("Podaj PESEL klienta:");
							String pesel_klienta;
							pesel_klienta = scan.next();
							Osoba osoba = bank.getOsoba(pesel_klienta);
							if( osoba == null )
							{
								System.out.println("Nie ma takiej osoby!");
								System.out.println("Wracasz do menu glownego, jak nie umiesz pisac.");
								break;
							}
							osoba.wypiszKonta();
							System.out.println("Chcesz wyswietlic karty danego konta?");
							System.out.println("[1] - TAK.");
							System.out.println("[0] - NIE (Wroc do menu).");
							if( scan.nextInt() == 1 )
							{
								System.out.println("Podaj nr konta:");
								int nr_konta;
								nr_konta = scan.nextInt();
								Konto konto = osoba.getKonto(nr_konta);
								if( konto == null )
								{
									System.out.println("Nie ma takiego konta!");
									System.out.println("Wracasz do menu glownego, jak nie umiesz pisac.");
									break;
								}
								konto.wypiszKarty();
								System.out.println("[0] - Wroc do menu");
								if( scan.nextInt() == 0 )
									break;
								else
								{
									System.out.println("Przeciez bylo napisane, zeby wcisnac zero...");
									break;
								}	
							}
							else 
								break;
						}
						else
							break;
					}
					else 
						break;
				case 2: //wypisywanie firm
					centrum.wypiszFirmy();
					break;
				case 3: //filtrowanie
				{
					HashMap<Integer, Transakcja> filtrowanie = new HashMap<Integer, Transakcja>();
					HashMap<Integer, Transakcja> tmp = new HashMap<Integer, Transakcja>();
					centrum.wypiszTransakcje();
					int i=1, liczba;
					System.out.println();
					System.out.println("Jak i po jakiej liczbie warunków chcesz przeszukaæ archiwum transakcji?");
					System.out.println("[1] - Jeden warunek");
					System.out.println("[2] - Koniunkcja kilku warunków ");
					System.out.println("[3] - Alternatywa kilku warunków ");
					System.out.println("[0] - Powrót do menu ");
					System.out.print("Podaj numer: ");
					switch ( scan.nextInt() )
					{
					case 1:
						filtrowanie = centrum.wyszukajTransakcje(centrum.getTransakcje());
						if (filtrowanie.size() > 0) centrum.wypiszPrzefiltrowane(filtrowanie);
						else System.out.println("Brak transakcji o podanych kryteriach");
						break;
					case 2: //AND
						filtrowanie = centrum.wyszukajTransakcje(centrum.getTransakcje());
						do {
							System.out.println("Czy chcesz dodaæ jeszcze jeden warunek?");
							System.out.println("[1] - Tak");
							System.out.println("[2] - Nie");
							System.out.print("Wybor: ");
							liczba = scan.nextInt();
							if (liczba == 1)
							{
								filtrowanie = centrum.wyszukajTransakcje(filtrowanie);			
							}
							else break;
							i++;
						} while(i<5);
						if (filtrowanie.size() > 0) centrum.wypiszPrzefiltrowane(filtrowanie);
						else System.out.println("Brak transakcji o podanych kryteriach");
						break;
					case 3://OR
						filtrowanie = centrum.wyszukajTransakcje(centrum.getTransakcje());
						do {
							System.out.println("Czy chcesz dodaæ jeszcze jeden warunek?");
							System.out.println("[1] - Tak");
							System.out.println("[2] - Nie");
							System.out.print("Wybor: ");
							liczba = scan.nextInt();
							if (liczba == 1)
							{
								tmp = centrum.wyszukajTransakcje(centrum.getTransakcje());
								Set<Integer> keys = tmp.keySet();
								for(int k: keys)
								{
									if (filtrowanie.containsKey(k)) continue;
									else
										filtrowanie.put(k, tmp.get(k));
								}
								tmp = null;
							}
							else break;
							i++;
						} while(i<5);
						if (filtrowanie.size() > 0) centrum.wypiszPrzefiltrowane(filtrowanie);
						else System.out.println("Brak transakcji o podanych kryteriach");
						break;
					case 0:
						break;
					default: 
						System.out.println("B³êdny numer poda³eœ");
						break;
					}
					filtrowanie = null;
					tmp = null;
					break;
				}
				case 4: //dodawanie
				{
		            System.out.print("Co chcesz dodaæ?\n\n");
		            System.out.print("[1] - Bank \n");
		            System.out.print("[2] - Osobe \n");
		            System.out.print("[3] - Konto \n");
		            System.out.print("[4] - Karte \n");
		            System.out.print("[5] - Firme \n");
		            System.out.print("Wybor: ");
		            String nazwa, pesel;
		            Bank b;
		            Osoba o;
		            Konto k;
		            switch (scan.nextInt())
		            {
		                case 1:
		                	System.out.print("Podaj nazwe banku ktory chcesz dodac: ");
		                	nazwa = scan.next();
		                	if ( centrum.getBanki().containsKey(nazwa) == true)
		                	{
		                		System.out.print("Bank o podanej nazwie juz istnieje.\n ");
		                		break;
		                	}
		                	else
		                	{
		                		centrum.dodajBank(nazwa);
		                		System.out.println("Bank zosta³ pomyœlnie dodany");
		                	}
		                		
		                    break;
		                case 2:
		                    centrum.wypiszBanki();
		                    System.out.print("Podaj nazwe banku do ktorego chcesz dodac osobe: ");
		                        nazwa = scan.next();
		                        while (centrum.getBanki().containsKey(nazwa) == false)
		                        {
		                        	System.out.print("Nie ma takiego banku, podaj nazwe ponownie: ");
		                            nazwa = scan.next();
		                        }
		                        if (centrum.getBanki().containsKey(nazwa))
		                        {
		                            b = centrum.getBank(nazwa);
		                            System.out.print("Podaj pesel osoby ktora chcesz dodac do banku: ");
		                            pesel = scan.next();
		                            if (b.getOsoby().containsKey(pesel) == true)
		                            {
		                            	System.out.println("Osoba o podanym peselu juz jest klientem tego banku");
		                            	break;
		                            }
		                            else
		                            {
		                            	System.out.print("Podaj imie osoby ktora chcesz dodac: ");
			                            String imie = scan.next();			 
			                            System.out.print("Podaj nazwisko osoby ktora chcesz dodac: ");
			                            String nazwisko = scan.next();
			                            o = new Osoba(pesel, imie, nazwisko);
			                            b.dodajOsobe(o);
			                            System.out.println("Osoba zosta³a pomyœlnie dodana ");
		                            }
		                        }
		                        
		                        break;
		                case 3:
		                	centrum.wypiszBanki();
		                	System.out.print("Podaj nazwe banku do ktorego chcesz dodac konto: ");
		                    nazwa = scan.next();
		                    while (centrum.getBanki().containsKey(nazwa) == false)
		                    {
		                    	System.out.print("Nie ma takiego banku, podaj nazwe ponownie: ");
		                    	nazwa = scan.next();
		                    }
		                    b = centrum.getBank(nazwa);
		                    b.wypiszKlientow();
		                    System.out.print("Podaj pesel osoby do ktorej chcesz dodac konto: ");
		                    pesel = scan.next();
		                    while (b.getOsoby().containsKey(pesel) == false)
		                    {
		                    	System.out.print("Nie ma takiego klienta, podaj pesel ponownie: ");
		                    	pesel = scan.next();
		                    }
		                    o = b.getOsoba(pesel);
		                    k = new Konto();
		                    o.dodajKonto(k);
		                    System.out.println("Konto zosta³o pomyœlnie dodane");
		                    break;
		                case 4:
		                    {
		                    	centrum.wypiszBanki();
		                        System.out.print("Podaj nazwe banku do ktorego chcesz dodac karte: ");
		                        nazwa = scan.next();
		                        while (centrum.getBanki().containsKey(nazwa) == false)
		                        {
		                        	System.out.print("Nie ma takiego banku, podaj nazwe ponownie: ");
		                            nazwa = scan.next();
		                        }
		                        b = centrum.getBank(nazwa);
		                        b.wypiszKlientow();
		                        System.out.print("Podaj pesel osoby do ktorej chcesz dodac karte: ");
		                        pesel = scan.next();
		                        while (b.getOsoby().containsKey(pesel) == false)
		                        {
		                        	System.out.print("Nie ma takiego klienta, podaj pesel ponownie: ");
		                            pesel = scan.next();
		                        }
		                        o = b.getOsoba(pesel);
		                        o.wypiszKonta();
		                        System.out.print("Podaj numer konta do ktorego chcesz dodac karte: ");
		                        int numer = scan.nextInt();
		                        while (o.getKonta().containsKey(numer) == false)
		                        {
		                        	System.out.print("Nie istnieje konto o podanym numerze, podaj numer konta ponownie: ");
		                            numer = scan.nextInt();
		                        }
		                        k = o.getKonto(numer);
		                        System.out.print("Jaka karte chcesz dodac?\n");
		                        System.out.print("[1] - Debetowa\n");
		                        System.out.print("[2] - Bankomatowa\n");
		                        System.out.print("[3] - Kredytowa\n");
		                        System.out.print("Wybor: ");
		                        switch (scan.nextInt())
		                        {
		                            case 1:
		                                {
		                                    Debetowa c = new Debetowa(k.getNumerKonta(), b.getNazwa(), o.getPesel());
		                                    k.dodajKarte(c);
		                                    System.out.println("Karta zosta³a pomyœlnie dodana");
		                                }
		                                break;
		                            case 2:
		                                {
		                                    Bankomatowa c = new Bankomatowa(k.getNumerKonta(), b.getNazwa(), o.getPesel());
		                                    k.dodajKarte(c);
		                                    System.out.println("Karta zosta³a pomyœlnie dodana");
		                                }
		                                break;
		                            case 3:
		                                {
		                                	System.out.print("Podaj limit karty: ");
		                                    double limit = scan.nextDouble();
		                                    Kredytowa c = new Kredytowa(k.getNumerKonta(), b.getNazwa(), o.getPesel(), limit);
		                                    k.dodajKarte(c);
		                                    System.out.println("Karta zosta³a pomyœlnie dodana");
		                                }
		                                break;
		                            default: System.out.print("Brak podanej opcji. Wracamy sie do menu \n");
		                                break;
		                       }
		                    }
		                    break;
		                case 5:
		                    {
		                    	System.out.print("Podaj nazwe firmy ktora chcesz dodac:");
		                        nazwa = scan.next();
		                        System.out.print("Podaj krs firmy ktora chcesz dodac: ");
		                        String krs = scan.next();
		                        System.out.print("Wybierz rodzaj dodawanej firmy: \n");
		                        System.out.print("[1] - Sklep\n");
		                        System.out.print("[2] - Firma Transportowa\n");
		                        System.out.print("[3] - Firma Uslugowa\n");
		                        System.out.print("Wybor: ");
		                        switch (scan.nextInt())
		                        {
		                            case 1:
		                            	centrum.dodajSklep(krs, nazwa);
		                            	System.out.println("Firma zosta³a pomyœlnie dodana");
		                                break;
		                            case 2:
		                                centrum.dodajFirmeTransportowa(krs, nazwa);
		                                System.out.println("Firma zosta³a pomyœlnie dodana");
		                                break;
		                            case 3:
		                                centrum.dodajFirmeTransportowa(krs, nazwa);
		                                System.out.println("Firma zosta³a pomyœlnie dodana");
		                                break;
		                            default: System.out.print("\n Brak podanej opcji. Wracamy sie do menu \n");
		                                break;
		                        }
		                        
		                    }
		                    break;
		                default: System.out.print("Brak podanej opcji. Wracamy sie do menu \n");
		                    break;
		            }
		                 
				}
					break;
				case 5://usuwanie
		            System.out.print("Co chcesz usunac?\n");
		            System.out.print("[1] - Bank \n");
		            System.out.print("[2] - Firme\n");
		            System.out.print("Wybor: ");
		            switch (scan.nextInt())
		            {
		                case 1:
		                    {
		                    	System.out.print("Podaj nazwe banku ktory chcesz usunac: ");
		                        String nazwa = scan.next();
		                        Bank b = centrum.getBank(nazwa);
		                        b.setStatus(false);
		                    }
		                    break;
		                case 2:
		                    {
		                    	System.out.print("Podaj krs firmy ktora chcesz usunac: ");
		                        String krs = scan.next();
		                        Firma f = centrum.getFirma(krs);
		                        f.setStatus(false);
		                    }
		                    break;
		                default:
		                	System.out.print("Brak podanej opcji");
		                    break;
		            }
		            break;
				case 6:
					//dokonywanie wplaty i wyplat
                    System.out.println("Co chcesz zrobic?");
                    System.out.println("[1] - wplac pieniadze");
                    System.out.println("[2] - wyplac pieniadze");
                    switch( scan.nextInt() )
                    {
                        case 1: //wplacanie pieniedzy
                            boolean[] sukces_wplaty = {false};
                            System.out.println("Podaj numer konta:");
                            int numer_konta = scan.nextInt();
                            System.out.println("Podaj kwote, jaka chcesz wplacic");
                            double kwota = scan.nextDouble();
                            centrum.getBanki().forEach( (nazwa_banku, bank) -> {
                                bank.getOsoby().forEach( (pesel, klient) -> {
                                    klient.getKonta().forEach( (nr_konta, konto) -> {
                                        if( nr_konta == numer_konta )
                                        {
                                            if( kwota > 0 )
                                            {
                                                konto.wplac(kwota);
                                                sukces_wplaty[0] = true;
                                            }
                                            else
                                                System.out.println("Podano ujemna kwote!");
                                        }
                                    });
                                });
                            });
                            if( sukces_wplaty[0] == false )
                                System.out.println("Nie znaleziono konta lub ujemna kwota!");
                            else
                                System.out.println("Sukces");
                            break;
                        case 2: //wyplacanie pieniedzy
                            boolean[] sukces_wyplaty = {false};
                            System.out.println("Podaj numer konta:");
                            numer_konta = scan.nextInt();
                            System.out.println("Podaj kwote, jaka chcesz wyplacic");
                            kwota = scan.nextDouble();
                            centrum.getBanki().forEach( (nazwa_banku, bank) -> {
                                bank.getOsoby().forEach( (pesel, klient) -> {
                                    klient.getKonta().forEach( (nr_konta, konto) -> {
                                        if( nr_konta == numer_konta )
                                        {
                                            double saldo = konto.getSaldo();
                                            if( saldo-kwota > 0 && kwota > 0 )
                                            {
                                                konto.wyplac(kwota);
                                                sukces_wyplaty[0] = true;
                                            }
                                            else
                                                System.out.println("Ujmena kwota lub brak srodkow!");
                                        }
                                    });
                                });
                            });
                            if( sukces_wyplaty[0] == false )
                                System.out.println("Nie znaleziono konta, ujemna kwota lub brak srodkow!");
                            else
                                System.out.println("Sukces.");
                            break;
                        default:
                            System.out.println("Nieprawidlowy wybor.");
                    }
					break;
				case 7: //wykonaj transakcje
					centrum.wypiszFirmy();
					System.out.print("Podaj kwote transakcji:");
				    double kwota = scan.nextDouble();
				    System.out.print("Podaj date transakcji [RRRR-MM-DD]:");
				    String data = scan.next();
				    System.out.print("Wybierz sklep z listy i podaj jego numer KRS: ");
				    String krs = scan.next();
				    Firma firma = centrum.getFirma(krs);
				    if (firma.getStatus() == false) 
				    {
				    	System.out.println("Blad, firma juz dawno zawiesila swoja dzialanosc");
				    	break;
				    }
				    System.out.print("Podaj numer karty, z ktorej chcesz dokonac transakcji: ");
				    int numer = scan.nextInt();
				    Karta card = centrum.wyszukiwanieKarty(numer);
				    while( card == null )
				    {
				        System.out.println("Brak podanego numeru karty w systemie, sprobuj ponownie");
				        numer = scan.nextInt();
				        card = centrum.wyszukiwanieKarty(numer);
				    }
				    Bank bank = centrum.getBank( card.getNazwaBanku() );
				    if (bank.getStatus() == false)
				    {
				    	System.out.println("Blad, bank do ktorego nalezy karta zbankrutowal");
				    	break;
				    }
				    boolean status = centrum.dodajTransakcje(card, firma, kwota, data);
				    if (status == true)				    
				    	System.out.println("Transakcja zosta³a pomyœlnie zrealizowana");
				    else
				    	System.out.println("Transakcja zosta³a odrzucona");
					break;
				case 8:
					centrum.zapisz_do_plikow();
					System.out.println("Zmiany zapisano pomyœlnie do plików systemowych");
					break;
				case 0:
					centrum.zapisz_do_plikow();
					scan.close();
					System.exit(0);
					break;
				default: continue;
			}
			System.out.print("\n \n \n ");
		}
	}
}
