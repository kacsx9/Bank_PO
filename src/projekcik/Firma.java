package projekcik;

public abstract class Firma {
	//pola
	protected String nazwa, KRS;
	protected boolean status=true;

    //gettery i settery
    public String getNazwa()
    {
        return this.nazwa;
    }
    public String getKRS()
    {
        return this.KRS;
    }
    public void setNazwa(String n)
    {
        this.nazwa = n;
    }
    public void setKRS(String k)
    {
        this.KRS = k;
    }
    public boolean getStatus()
    {
    	return status;
    }
    public void setStatus(boolean status)
	{
		this.status = status;
	}
    
    //kontruktor
    public Firma (String krs, String nazwa)
    {
       this.KRS = krs;
       this.nazwa = nazwa;
    }

}


