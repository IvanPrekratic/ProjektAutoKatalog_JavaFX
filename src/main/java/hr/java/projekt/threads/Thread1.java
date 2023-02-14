package hr.java.projekt.threads;

import hr.java.projekt.entiteti.CarPart;
import hr.java.projekt.glavna.controllers.PromjeneController;

import java.util.List;

public class Thread1 extends Thread{
    private List<CarPart> lista;
    private PromjeneController controller;

    public Thread1(List<CarPart> lista, PromjeneController controller) {
        this.lista = lista;
        this.controller = controller;
    }

    private synchronized void upisiPodatke(){
        CarPart cp = lista.get(lista.size()-1);
        controller.cijenaLabel.setText(cp.getPartPrice().toString());
        controller.kataloskiLabel.setText(cp.getPartNumber());
        controller.markaLabel.setText(cp.getCar().getMake());
        controller.modelLabel.setText(cp.getCar().getModel());
        controller.kategorijaLabel.setText(cp.getCategory());
        controller.nazivLabel.setText(cp.getName());
        controller.proizvodacLabel.setText(cp.getPartManufactor());
        controller.skladisteLabel.setText(cp.getPartStock().toString());
    }

    @Override
    public void run() {
        upisiPodatke();
    }
}
