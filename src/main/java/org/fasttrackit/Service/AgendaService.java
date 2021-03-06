package org.fasttrackit.Service;

import org.fasttrackit.Domain.Agenda;
import org.fasttrackit.Domain.FirstNameFromDatabase;
import org.fasttrackit.Domain.LastNameFromDatabase;
import org.fasttrackit.Persistence.AgendaRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Stack;

public class AgendaService {
    AgendaRepository agendaRepository=new AgendaRepository();

    public void createContact(Agenda agenda) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Creating item: "+agenda);
        agendaRepository.createContact(agenda);
    }

    public List<Agenda> getContact() throws SQLException, IOException, ClassNotFoundException {
        return agendaRepository.getContact();
    }

    public List<Agenda> searchContactAfterFirstNameOrLastName(String firstNameOrLastName,String optionSearch) throws SQLException, IOException, ClassNotFoundException{
        return agendaRepository.searchContact(firstNameOrLastName,optionSearch);
    }

    public Stack<FirstNameFromDatabase> getFirstName()throws SQLException, IOException, ClassNotFoundException{
        return agendaRepository.searchFirstName();
    }

    public Stack<LastNameFromDatabase> getLastName()throws SQLException, IOException, ClassNotFoundException{
        return agendaRepository.searchLastName();
    }
}
