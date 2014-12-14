package minsk.bsuir.oop.nikityk;

/**
 * Created by Роман on 12.12.2014.
 */
import com.sun.org.apache.xpath.internal.SourceTree;

import java.sql.*;
import java.util.LinkedList;
import java.util.logging.*;

public class DataBaseClass {
    private static Connection conn ;
    private static Statement stmt;
    public static void dialDB(String URL, String Login, String Pass)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL,Login,Pass);
            Statement stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static String fEnabling(String stlName, String param)
    {
        if (param.compareTo("")!=0)
            return stlName+"="+param;
        else
            return "";
    }
    public static LinkedList<Pit> selectAllPit()
    {
        LinkedList<Pit> lst = new LinkedList<Pit>();
        try {
            Pit tmp = null;
            ResultSet rs = null;
            PreparedStatement ps = conn.prepareStatement( "SELECT * FROM pitdb.pit");
            rs = ps.executeQuery();
            if(rs!=null) {
                while (rs.next()) {
                    tmp = new Pit(new AppUser(rs.getString("name"), rs.getString("surname"), rs.getString("passport"), rs.getString("telnum")), rs.getString("street"), rs.getString("nearhome"), rs.getString("deep"), rs.getString("diam"));
                    tmp.setInspector(new Inspector(rs.getString("INSPname"), rs.getString("INSPsurname"), rs.getString("INSPnum"), rs.getString("INSPtelnum"), rs.getString("INSPpass")));
                    tmp.setCreationDate(rs.getString("date"));
                    tmp.setID(rs.getString("id"));
                    System.out.println("ID="+tmp.getID());
                    System.out.println(tmp.toString());
                    lst.add(tmp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }
    public static LinkedList<Pit> selectAllPit(Pit filter)
    {
        LinkedList<Pit> lst = new LinkedList<Pit>();
        try {
            Pit tmp = null;
            ResultSet rs = null;
            if(filter!=null) {
                System.out.println("Работа с фильтром");
                if (filter.getID() != -1) {
                    rs = stmt.executeQuery("SELECT * FROM pitdb.pit WHERE id =" + filter.getID());
                } else {
                    String Query = "SELECT * FROM pitdb.pit WHERE id>=-1 ";
                    Query += fEnabling("passport", filter.getUser().getPassport());
                    Query += fEnabling("name", filter.getUser().getName());
                    Query += fEnabling("surname", filter.getUser().getSurname());
                    Query += fEnabling("telnum", filter.getUser().getTelNum());
                    Query += fEnabling("street", filter.getLocationStreet());
                    Query += fEnabling("nearhome", filter.getLocationNum());
                    Query += fEnabling("deep", filter.getDeep());
                    Query += fEnabling("diam", filter.getDiam());
                    Query += fEnabling("INSPname", filter.getInspector().getName());
                    Query += fEnabling("INSPsurname", filter.getInspector().getSurname());
                    Query += fEnabling("INSPnum", filter.getInspector().getUdnum());
                    Query += fEnabling("INSPtelnum", filter.getInspector().getTelNum());
                    Query += fEnabling("INSPpass", filter.getInspector().getPassport());
                    Query += fEnabling("date", filter.getCreationDate());
                    rs = stmt.executeQuery(Query);
                }
            }
            else
            {
                System.out.println("Работа без фильтра");
                PreparedStatement ps = conn.prepareStatement( "SELECT * FROM pitdb.pit");
                rs = ps.executeQuery();
            }
            if(rs!=null) {
                while (rs.next()) {
                    tmp = new Pit(new AppUser(rs.getString("name"), rs.getString("surname"), rs.getString("passport"), rs.getString("telnum")), rs.getString("street"), rs.getString("nearhome"), rs.getString("deep"), rs.getString("diam"));
                    tmp.setInspector(new Inspector(rs.getString("INSPname"), rs.getString("INSPsurname"), rs.getString("INSPnum"), rs.getString("INSPtelnum"), rs.getString("INSPpass")));
                    tmp.setCreationDate(rs.getString("date"));
                    tmp.setID(rs.getString("id"));
                    lst.add(tmp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }
    public static void insertPit(Pit A) {
        try {
            System.out.println("Добавление записи");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO pitdb.pit(passport, surname, name, telnum, street, nearhome, deep, diam, date, INSPsurname, INSPname, INSPnum, INSPtelnum, INSPpass)"+
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1,A.getUser().getPassport());
            pstmt.setString(2,A.getUser().getName());
            pstmt.setString(3,A.getUser().getSurname());
            pstmt.setString(5,A.getLocationStreet());
            pstmt.setString(9,A.getCreationDate());
            pstmt.setInt(4,Integer.parseInt(A.getUser().getTelNum()));
            pstmt.setInt(6,Integer.parseInt(A.getLocationNum()));
            pstmt.setInt(7,Integer.parseInt(A.getDeep()));
            pstmt.setInt(8,Integer.parseInt(A.getDiam()));
            if(A.getInspector()!=null) {
                pstmt.setInt(12, Integer.parseInt(A.getInspector().getUdnum()));
                pstmt.setInt(13, Integer.parseInt(A.getInspector().getTelNum()));
                pstmt.setString(10, A.getInspector().getSurname());
                pstmt.setString(11, A.getInspector().getName());
                pstmt.setString(14, A.getInspector().getPassport());
            }
            else
            {
                pstmt.setInt(12, 0);
                pstmt.setInt(13, 0);
                pstmt.setString(10, null);
                pstmt.setString(11, null);
                pstmt.setString(14, null);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updatePit(Pit A) {
        try {
            System.out.println("Обновление записи");
            System.out.println(A.toString());
            PreparedStatement pstmt = conn.prepareStatement
   ("UPDATE pitdb.pit SET passport=?, name=?, surname=?, telnum=?, street=?, nearhome=?, deep=?, diam=?, date=?, INSPsurname=?, INSPname=?, INSPnum=?, INSPtelnum=?, INSPpass=? WHERE id=?");

            pstmt.setString(1,A.getUser().getPassport());
            pstmt.setString(2,A.getUser().getName());
            pstmt.setString(3,A.getUser().getSurname());
            pstmt.setInt(4, Integer.parseInt(A.getUser().getTelNum()));
            pstmt.setString(5,A.getLocationStreet());
            pstmt.setInt(6, Integer.parseInt(A.getLocationNum()));
            pstmt.setInt(7, Integer.parseInt(A.getDeep()));
            pstmt.setInt(8, Integer.parseInt(A.getDiam()));
            pstmt.setString(9, A.getCreationDate());
            pstmt.setString(10, A.getInspector().getSurname());
            pstmt.setString(11, A.getInspector().getName());
            pstmt.setInt(12, Integer.parseInt(A.getInspector().getUdnum()));
            pstmt.setInt(13, Integer.parseInt(A.getInspector().getTelNum()));
            pstmt.setString(14, A.getInspector().getPassport());
            pstmt.setInt(15,A.getID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deletePit(int id) {
        try {
            System.out.println("Удаление записи");
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM pitdb.pit WHERE id=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
