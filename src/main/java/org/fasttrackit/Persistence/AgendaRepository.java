package org.fasttrackit.Persistence;

import org.fasttrackit.Domain.Agenda;
import org.fasttrackit.Domain.FirstNameFromDatabase;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

    public List<Agenda> getContact() throws SQLException, IOException, ClassNotFoundException {
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

    public List<Agenda> searchContact(String optionSearch) throws SQLException, IOException, ClassNotFoundException {
        try(Connection connection=DatabaseConfiguration.getConnection()) {
            String query = "SELECT id,`firstName`,`lastName`,`phoneNumber` FROM agenda WHERE "+optionSearch+"='mihai'";
            Statement statement = connection.createStatement();
            statement.execute(query);

            ResultSet resultSet=statement.executeQuery(query);
            List<Agenda> searchedContact=new ArrayList<>();
            while(resultSet.next()){
                Agenda agenda=new Agenda();
                agenda.setId(resultSet.getInt("id"));
                agenda.setFirstName(resultSet.getString("firstName"));
                agenda.setLastName(resultSet.getString("lastName"));
                agenda.setPhoneNumber(resultSet.getString("phoneNumber"));
                searchedContact.add(agenda);
            }
            return searchedContact;
        }
    }

    public Stack<FirstNameFromDatabase> searchFirstName()throws SQLException, IOException, ClassNotFoundException{
        try(Connection connection=DatabaseConfiguration.getConnection()){
            String query="SELECT DISTINCT firstName FROM agenda;";
            Statement statement=connection.createStatement();
            statement.execute(query);

            ResultSet resultSet=statement.executeQuery(query);
            Stack<FirstNameFromDatabase> allFirstNames=new Stack<>();
            while(resultSet.next()){
                FirstNameFromDatabase firstNameFromDatabase=new FirstNameFromDatabase();
                firstNameFromDatabase.setFirstName(resultSet.getString("firstName"));
                allFirstNames.push(firstNameFromDatabase);
            }
            return allFirstNames;
        }
    }

 }
