package minsk.bsuir.oop.nikityk;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * Created by Роман on 11.12.2014.
 */
public class ThreadClass extends Thread {
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;
    private static String login, pass;
    public ThreadClass(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }
    public ThreadClass(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, String login, String pass) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.login=login;
        this.pass=pass;
    }

    @Override
    public void run() {
            try {
                String command = ((String) objectInputStream.readObject());
                if(command.compareTo("gPL|")==0)
                {
                    LinkedList<Pit> tmp = DataBaseClass.selectAllPit();
                    objectOutputStream.writeObject((String)Integer.toString(tmp.size()));
                    for(int i=0 ;i<tmp.size();i++) {
                        objectOutputStream.writeObject(tmp.get(i).toString());
                    }
                    System.out.println("Список отправлен");
                }
                else if(command.compareTo("lp|")==0)
                {
                    String log = ((String) objectInputStream.readObject());
                    String pas = ((String) objectInputStream.readObject());
                    if(log.compareTo(login)==0 && pas.compareTo(pass)==0)
                        objectOutputStream.writeObject("true");
                    else
                        objectOutputStream.writeObject("false");
                    System.out.println("Проверка входа");
                }
                else if(command.compareTo("gPLF|")==0)
                {
                    LinkedList<Pit> tmp = DataBaseClass.selectAllPit(Pit.fromString((String)objectInputStream.readObject()));
                    objectOutputStream.writeObject(tmp.size());
                    for(int i=0 ;i<tmp.size();i++) {
                        System.out.println(tmp.get(i).toString());
                        objectOutputStream.writeObject(tmp.get(i).toString());
                    }
                    System.out.println("Отфильтрованный список отправлен");
                }
                else if(command.compareTo("sPL|")==0)
                {
                    int count = Integer.parseInt((String) objectInputStream.readObject());
                    Pit tmp=null;
                    for(int i=0; i<count;  i++)
                    {
                        tmp = Pit.fromString((String) objectInputStream.readObject());
                        DataBaseClass.updatePit(tmp);
                    }
                    System.out.println("Обновлённый список получен");
                }
                else if(command.compareTo("sP|")==0)
                {
                    System.out.println("Добавленно!");
                    Pit tmp = Pit.fromString((String) objectInputStream.readObject());
                    System.out.println("азаза "+ tmp.toString());
                    DataBaseClass.insertPit(tmp);
                    System.out.println("Объект получен");
                }
                else if(command.compareTo("uP|")==0)
                {
                    System.out.println("Изменено!");
                    Pit tmp = Pit.fromString((String) objectInputStream.readObject());
                    System.out.println(tmp.toString());
                    DataBaseClass.updatePit(tmp);
                    System.out.println("Изменения внесены");
                }
                else if(command.compareTo("del|")==0)
                {
                    System.out.println("Удалено!");
                    DataBaseClass.deletePit(Integer.parseInt((String) objectInputStream.readObject()));
                    System.out.println("Объект удалён из базы данных");
                }

                objectOutputStream.writeObject("Prinyal");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

    }
}
