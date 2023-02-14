package hr.java.projekt.threads;

import hr.java.projekt.entiteti.CarPart;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

public class Thread2 extends Thread{
    private List<CarPart> lista;

    public Thread2(List<CarPart> lista) {
        this.lista = lista;
    }

    private synchronized void serijaliziraj(){
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("zadnjaPromjena.dat"));
            out.writeObject(lista.get(lista.size()-1));
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        serijaliziraj();
    }
}
