package minsk.bsuir.oop.nikityk;

public class AppUser implements Comparable<AppUser> {
	protected String Name;
	protected String Surname;
    protected String Passport;
    protected String TelNum;

    public AppUser() {
    }

    public AppUser(AppUser A) {
        Name = new String(A.Name);
        Surname = new String(A.Surname);
        Passport = new String(A.Passport);
        TelNum = new String(A.TelNum);
    }

	public AppUser(String NM, String SNM, String PST, String NUM) {
		Name = NM;
		Surname = SNM;
        Passport = PST;
        TelNum = NUM;
	}

    public int compareTo(AppUser B){
        if(Name==B.Name)
            if(Surname ==B.Surname)
                if(Passport ==B.Passport)
                    if(TelNum==B.TelNum)
                       return 0;
        return Passport.compareTo(B.Passport);
    }
    public void setName(String Str)
    {
        Name=Str;
    }
    public String getName()
    {
        return Name;
    }
    public void setSurame(String Str)
    {
        Surname=Str;
    }
    public String getSurname()
    {
        return Surname;
    }
    public void setPassport(String Str)
    {
        Passport =Str;
    }
    public String getPassport()
    {
        return Passport;
    }
    public void setTelNum(String Str)
    {
        TelNum=Str;
    }
    public String getTelNum()
    {
        return TelNum;
    }
    public String toString() {
        return (Surname + "|" + Name + "|" + Passport + "|" + TelNum);
    }
    public static AppUser fromString(String TEXT){
        String A[]=TEXT.split("\\|");
        return new AppUser(A[1],A[0],A[2],A[3]);
    }
}
