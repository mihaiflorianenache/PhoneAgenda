package org.fasttrackit.Service;

import org.fasttrackit.Domain.Agenda;
import org.fasttrackit.Persistence.AgendaRepository;

import java.io.IOException;
import java.sql.SQLException;

public class AgendaService {
    AgendaRepository agendaRepository=new AgendaRepository();

    public void createContact(Agenda agenda) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Creating item: "+agenda);
        agendaRepository.createContact(agenda);
    }

    /*public List<Animal> getAnimalRescuer() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Getting to do items: ");
        return animalRescuerRepository.getAnimalRescuer();
    }*/
}
