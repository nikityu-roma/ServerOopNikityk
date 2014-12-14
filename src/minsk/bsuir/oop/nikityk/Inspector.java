package minsk.bsuir.oop.nikityk;

import javax.swing.*;
import java.io.Serializable;
import java.util.LinkedList;

public class Inspector extends AppUser implements Serializable {
	protected LinkedList<Pit> PitList;
    private String udnum;
	public Inspector(String NM, String SNM, String PST, String num, String UDNUM) {
		super(NM, SNM, PST,num);
        PitList = new LinkedList<Pit>();
        udnum=UDNUM;
	}
    public void setUdnum(String UDNUM)
    {
        udnum=UDNUM;
    }
    public String getUdnum()
    {
        return udnum;
    }
    public void addPit(Pit A)
    {
        PitList.add(A);
    }
    public Pit getPit(int ind)
    {
        if(ind<0 || ind>PitList.size())
        {
            JOptionPane.showMessageDialog(null,"Нет запрошенного элемента");
            return null;
        }
        return PitList.get(ind);
    }
    public int getSize()
    {
        return PitList.size();
    }
    public void delPit(int ind)
    {
        PitList.remove(ind);
    }
    public void setPit(int ind, Pit A)
    {
        PitList.set(ind,A);
    }
    public String toString()
    {
        return (Surname + "|" + Name + "|" + Passport + "|" + TelNum + "|" + udnum);
    }
    public static Inspector fromString(String TEXT){
        String A[]=TEXT.split("\\|");
        return new Inspector(A[1],A[0],A[2],A[3],A[4]);
    }
}
