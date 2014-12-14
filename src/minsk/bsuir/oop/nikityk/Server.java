package minsk.bsuir.oop.nikityk;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

class Server extends Thread {
    private static int count = 0;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;
    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8030);
        DataBaseClass.dialDB("jdbc:mysql://localhost:3306/pitdb","root", "Avatar123");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println(++count);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            new ThreadClass(objectInputStream, objectOutputStream,"admin","password").
                    start();
        }
    }
}