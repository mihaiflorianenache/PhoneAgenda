package org.fasttrackit.Persistence;

import org.fasttrackit.Domain.Agenda;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaRepository {
    public void createContact(Agenda agenda) throws SQLException, IOException, ClassNotFoundException {
        try(Connection connection= DatabaseConfiguration.getConnection()){
            String insertContact="INSERT INTO agenda (`firstName`,`lastName`,`phoneNumber`) VALUES (?,?,?)"+ "ON DUPLICATE KEY UPDATE firstName=firstName+1;";

            PreparedStatement preparedStatement=connection.prepareStatement(insertContact);
            preparedStatement.setString(1,agenda.getFirstName());
            preparedStatement.setString(2,agenda.getLastName());
            preparedStatement.setString(3,agenda.getPhoneNumber());
            preparedStatement.executeUpdate();
        }
    }

    public List<Agenda> getAgendaRepository() throws SQLException, IOException, ClassNotFoundException {
        try(Connection connection= DatabaseConfiguration.getConnection()){
            String query="SELECT id,`firstName`,`lastName`,`phoneNumber` FROM agenda ORDER BY firstName desc";
            Statement statement=connection.createStatement();
            statement.execute(query);

            ResultSet resultSet=statement.executeQuery(query);
            List<Agenda> response=new ArrayList<>();

            while(resultSet.next()){
                Agenda agenda=new Agenda();
                agenda.setId(resultSet.getInt("id"));
                agenda.setFirstName(resultSet.getString("firstName"));
                agenda.setLastName(resultSet.getString("lastName"));
                agenda.setPhoneNumber(resultSet.getString("phoneNumber"));
                response.add(agenda);
            }
            return response;
        }
    }
 }
