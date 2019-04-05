package org.fasttrackit.Service;

import org.fasttrackit.Domain.Agenda;
import org.fasttrackit.Persistence.AgendaRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AgendaService {
    AgendaRepository agendaRepository=new AgendaRepository();

    public void createContact(Agenda agenda) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Creating item: "+agenda);
        agendaRepository.createContact(agenda);
    }

    public List<Agenda> getContact() throws SQLException, IOException, ClassNotFoundException {
        return agendaRepository.getContact();
    }

    public void searchContact(String optionSearch) throws SQLException {
        agendaRepository.searchContact(optionSearch);
    }
}
