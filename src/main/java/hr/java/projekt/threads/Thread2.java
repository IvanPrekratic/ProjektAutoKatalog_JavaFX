package hr.java.projekt.threads;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import hr.java.projekt.entiteti.CarPart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class Thread2 extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(Thread2.class);
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
            logger.info("Problem kod serijalizacije podataka");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        serijaliziraj();
    }
}
