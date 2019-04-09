package org.fasttrackit.Persistence;

import org.fasttrackit.Domain.Agenda;
import org.fasttrackit.Domain.FirstNameFromDatabase;
import org.fasttrackit.Domain.LastNameFromDatabase;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AgendaRepository {

    public void createContact(Agenda agenda) throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = DatabaseConfiguration.getConnection()) {
            String insertContact = "INSERT INTO agenda (`firstName`,`lastName`,`phoneNumber`) VALUES (?,?,?)" + "ON DUPLICATE KEY UPDATE firstName=firstName+1;";

            PreparedStatement preparedStatement = connection.prepareStatement(insertContact);
            preparedStatement.setString(1, agenda.getFirstName());
            preparedStatement.setString(2, agenda.getLastName());
            preparedStatement.setString(3, agenda.getPhoneNumber());
            preparedStatement.executeUpdate();
        }
    }

    public List<Agenda> getContact() throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = DatabaseConfiguration.getConnection()) {
            String query = "SELECT id,`firstName`,`lastName`,`phoneNumber` FROM agenda ORDER BY firstName desc";
            Statement statement = connection.createStatement();
            statement.execute(query);

            ResultSet resultSet = statement.executeQuery(query);
            List<Agenda> response = new ArrayList<>();

            while (resultSet.next()) {
                Agenda agenda = new Agenda();
                agenda.setId(resultSet.getInt("id"));
                agenda.setFirstName(resultSet.getString("firstName"));
                agenda.setLastName(resultSet.getString("lastName"));
                agenda.setPhoneNumber(resultSet.getString("phoneNumber"));
                response.add(agenda);
            }
            return response;
        }
    }

    public List<Agenda> searchContact(String optionSearch, String firstNameOrLastName) throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = DatabaseConfiguration.getConnection()) {
            String query = "SELECT id,`firstName`,`lastName`,`phoneNumber` FROM agenda WHERE " + optionSearch + "=" + "'" + firstNameOrLastName + "'";
            Statement statement = connection.createStatement();
            statement.execute(query);

            ResultSet resultSet = statement.executeQuery(query);
            List<Agenda> searchedContact = new ArrayList<>();
            while (resultSet.next()) {
                Agenda agenda = new Agenda();
                agenda.setId(resultSet.getInt("id"));
                agenda.setFirstName(resultSet.getString("firstName"));
                agenda.setLastName(resultSet.getString("lastName"));
                agenda.setPhoneNumber(resultSet.getString("phoneNumber"));
                searchedContact.add(agenda);
            }
            return searchedContact;
        }
    }

    public Stack<FirstNameFromDatabase> searchFirstName() throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = DatabaseConfiguration.getConnection()) {

            /*String query = "SELECT DISTINCT firstName FROM agenda;";

            Statement statement = connection.createStatement();
            statement.execute(query);

            ResultSet resultSet = statement.executeQuery(query);
            Stack<FirstNameFromDatabase> allFirstNames = new Stack<>();
            while (resultSet.next()) {
                FirstNameFromDatabase firstNameFromDatabase = new FirstNameFromDatabase();
                firstNameFromDatabase.setFirstName(resultSet.getString("firstName"));
                allFirstNames.push(firstNameFromDatabase);
            }
            return allFirstNames;*/

            /****************************************************************************************************************/

            Stack<FirstNameFromDatabase> allFirstNames = new Stack<>();
            for (String browseStackFirstName : callDistinctMethodFirstName()) {
                FirstNameFromDatabase firstNameFromDatabase = new FirstNameFromDatabase();
                firstNameFromDatabase.setFirstName(browseStackFirstName);
                allFirstNames.push(firstNameFromDatabase);
            }
            return allFirstNames;
        }
    }

    private Stack<String> callDistinctMethodFirstName() throws SQLException, IOException, ClassNotFoundException {
        Stack<String> distinctFirstName = new Stack<>();
        String search;
        int i, j, lengthFirstName, lengthSearch;
        for (String browseStackFirstName : distinctMethodFirstName()) {
            lengthSearch = 0;
            lengthFirstName = browseStackFirstName.trim().length();

            if(distinctFirstName.size()==0) {
                distinctFirstName.push(browseStackFirstName);
            }

            System.out.println("distinctFirstName.size()= "+distinctFirstName.size());

            for (i = 0; i < distinctFirstName.size(); i++) {
                search = distinctFirstName.get(i).trim();
                if(lengthFirstName==search.length()) {
                    for (j = 0; j < search.length(); j++) {
                        if (search.charAt(j) == browseStackFirstName.trim().charAt(j) || search.charAt(j) == browseStackFirstName.trim().charAt(j) + 32 || search.charAt(j) == browseStackFirstName.trim().charAt(j) - 32)
                            lengthSearch++;
                    }
                    //if distinctFirstName doesn't contain string(first name) this will be adding in stack
                    if (lengthSearch != lengthFirstName)
                        distinctFirstName.push(browseStackFirstName);
                    break;
                }
                else{
                    distinctFirstName.push(browseStackFirstName);
                    break;
                }
            }
        }
        return distinctFirstName;
    }

    private Stack<String> distinctMethodFirstName() throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = DatabaseConfiguration.getConnection()) {
            String query = "SELECT firstName FROM agenda";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);

            ResultSet resultSet = statement.executeQuery(query);
            Stack<String> solelyFirstName = new Stack<>();
            while (resultSet.next()) {
                String onlyFirstName = new String();
                onlyFirstName = resultSet.getString("firstName");
                solelyFirstName.push(onlyFirstName);
            }
            return solelyFirstName;
        }
    }

    public Stack<LastNameFromDatabase> searchLastName() throws SQLException, IOException, ClassNotFoundException {
        try (Connection connection = DatabaseConfiguration.getConnection()) {
            String query = "SELECT DISTINCT lastName FROM agenda;";
            Statement statement = connection.createStatement();
            statement.execute(query);

            ResultSet resultSet = statement.executeQuery(query);
            Stack<LastNameFromDatabase> allLastNames = new Stack<>();
            while (resultSet.next()) {
                LastNameFromDatabase lastNameFromDatabase = new LastNameFromDatabase();
                lastNameFromDatabase.setLastName(resultSet.getString("lastName"));
                allLastNames.push(lastNameFromDatabase);
            }
            return allLastNames;
        }
    }

}
