package minsk.bsuir.oop.nikityk;


import java.io.*;

public class Pit implements Serializable,Comparable<Pit> {
    private  int id=-1;
	protected String LocationStreet;
	protected String Deep;
    protected String LocationNum;
    protected String Diam;
	protected AppUser User=null;
	protected Inspector Insp=null;
    protected String CreationDate;
	public Pit() {

	}

	public Pit(AppUser USER, String LS, String LN, String DP, String DM)
    {
        User = new AppUser(USER);
		LocationStreet = LS;
        LocationNum = LN;
        Deep = DP;
        Diam = DM;
	}
	public Pit(Pit OBJ) {
		LocationStreet = new String(OBJ.LocationStreet);
        LocationNum = new String(OBJ.LocationNum);
        User = new AppUser(OBJ.User);
		Deep = new String(OBJ.Deep);
        Diam = new String(OBJ.Diam);
        CreationDate = new String(OBJ.CreationDate);
	}

	public void setLocationStreet(String STR) {
		LocationStreet = new String(STR);
	}

	public String getLocationStreet() {
		return LocationStreet;
	}

    public void setLocationNum(String STR) {
        LocationNum = new String(STR);
    }

    public String getLocationNum() {
        return LocationNum;
    }


    public void setInspector(Inspector STR) {
		Insp = STR;
	}

	public Inspector getInspector() {
		return Insp;
	}

	public void setUser(AppUser STR) {
		User = new AppUser(STR);
	}

	public AppUser getUser() {
		return User;
	}

	public void setDeep(String STR) {
		Deep = new String(STR);
	}

	public String getDeep() {
		return Deep;
	}

    public void setDiam(String STR) {
        Diam = new String(STR);
    }

    public String getDiam() {
        return Deep;
    }

    public String toString() {
        if(Insp!=null)
            return (id + "|" + LocationStreet + "|" + LocationNum + "|" + Deep + "|" + Diam + "|" + CreationDate + "|" + User.toString() + "|" + Insp.toString());
        return (id + "|" + LocationStreet + "|" + LocationNum + "|" + Deep + "|" + Diam + "|" + CreationDate + "|" + User.toString()+"|" + 0 + "|" + 0 + "|" + 0 + "|" + 0 + "|" + 0);
    }

    public static Pit fromString(String TEXT) {
        String A[] = TEXT.split("\\|");
        Pit tmp = new Pit(new AppUser(A[7], A[6], A[8], A[9]), A[1], A[2], A[3], A[4]);
        tmp.setID(Integer.parseInt(A[0]));
        tmp.setCreationDate(A[5]);
        tmp.setInspector(new Inspector(A[11], A[10], A[12], A[13], A[14]));
        return tmp;
    }

        public void Set(AppUser USER, String LS, String LN, String DP, String DM) {
        User = new AppUser(USER);
        LocationStreet = new String(LS);
        LocationNum = new String(LN);
        Deep = new String(DP);
        Diam = new String(DM);
    }

	public static void writeToFile(Pit obj, String File) throws IOException {
		FileOutputStream fos = new FileOutputStream(File);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}

	public static Pit readFromFile(String File) throws IOException,
			ClassNotFoundException {
		FileInputStream fis = new FileInputStream(File);
		ObjectInputStream oin = new ObjectInputStream(fis);
		Pit ReadObject = (Pit) oin.readObject();
		return ReadObject;
	}

	public int compareTo(Pit B){
        if(id!=-1 && B.id!=-1)
        {
            if(id<B.id)
                return -1;
            else if(id>B.id)
                return 1;
            else
                return 0;
        }
        else
			if(LocationNum==B.LocationNum && LocationStreet==B.LocationStreet)
						return 0;
		return User.compareTo(B.User);
	}
    public void setID(int num)
    {
        id=num;
    }
    public void setID(String num)
    {
        id=Integer.parseInt(num);
    }
    public int getID()
    {
        return id;
    }
    public void setCreationDate(String str)
    {
        CreationDate=str;
    }
    public String getCreationDate()
    {
        return CreationDate;
    }
}
