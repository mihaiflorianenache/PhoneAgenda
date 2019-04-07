package org.fasttrackit.Domain;

import org.fasttrackit.Service.AgendaService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Contact {

    private Agenda agenda = new Agenda();
    private Stack<String> searchAfterName = new Stack<>();
    private String firstNameAfterYouChoose;
    private String firstName="firstName";

    private void settingFirstName() throws MyException {
        System.out.println("Write your first name");
        Scanner scanner = new Scanner(System.in);
        String firstName = scanner.nextLine();
        int i;
        for (i = 0; i < firstName.length(); i++) {
            if ((firstName.charAt(i) < (int) 'a' || firstName.charAt(i) > 'z') && (firstName.charAt(i) < (int) 'A' || firstName.charAt(i) > 'Z') && firstName.charAt(i) != ' ' && firstName.charAt(i) != '\t')
                throw new MyException("First name can contains only letters");
        }

        int numberSpace = 0;
        for (i = 0; i < firstName.length(); i++) {
            if (firstName.charAt(i) == ' ' || firstName.charAt(i) == '\t')
                numberSpace++;
        }
        if (numberSpace == firstName.length())
            throw new MyException("This is not a first name");

        if (firstName.trim().charAt(0) < (int) 'A' || firstName.trim().charAt(0) > (int) 'Z') {
            throw new MyException("First name must begin with uppercase character");
        }
        agenda.setFirstName(firstName.trim());
    }

    private void callSettingsFirstName() {
        try {
            settingFirstName();
        } catch (MyException exception) {
            System.out.println(exception);
            callSettingsFirstName();
        }
    }

    private void settingLastName() throws MyException {
        System.out.println("Write your last name");
        Scanner scanner = new Scanner(System.in);
        String lastName = scanner.nextLine();
        int i;
        for (i = 0; i < lastName.length(); i++) {
            if ((lastName.charAt(i) < (int) 'a' || lastName.charAt(i) > 'z') && (lastName.charAt(i) < (int) 'A' || lastName.charAt(i) > 'Z') && lastName.charAt(i) != ' ' && lastName.charAt(i) != '\t')
                throw new MyException("Last name can contains only letters");
        }

        if (lastName.trim().charAt(0) < (int) 'A' || lastName.trim().charAt(0) > (int) 'Z') {
            throw new MyException("Last name must begin with uppercase character");
        }
        agenda.setLastName(lastName.trim());
    }

    private void callSettingsLastName() {
        try {
            settingLastName();
        } catch (MyException exception) {
            System.out.println(exception);
            callSettingsLastName();
        }
    }

    private void settingPhoneNumber() throws MyException {
        System.out.println("Write your phone number");
        Scanner scanner = new Scanner(System.in);
        String phoneNumber = scanner.nextLine();
        int i;
        for (i = 0; i < phoneNumber.trim().length(); i++) {
            if ((phoneNumber.trim().charAt(i) < (int) '0' || phoneNumber.trim().charAt(i) > (int) '9') && phoneNumber.trim().charAt(i) != '+' && phoneNumber.trim().charAt(i) != '-')
                throw new MyException("The phone number must contains only digits, + and/or - sign");
        }
        agenda.setPhoneNumber(phoneNumber);
    }

    private void callSettingsPhoneNumber() {
        try {
            settingPhoneNumber();
        } catch (MyException exception) {
            System.out.println(exception);
            callSettingsPhoneNumber();
        }
    }

    public void createContact() throws SQLException, IOException, ClassNotFoundException {
        /*callSettingsFirstName();
        callSettingsLastName();
        callSettingsPhoneNumber();

        agendaService.createContact(agenda);*/
        AgendaService agendaService = new AgendaService();
        System.out.println("The agend contains follow contacts:");
        for (Agenda phoneList : agendaService.getContact()) {
            System.out.println("Id: " + phoneList.getId() + ",First name: " + phoneList.getFirstName() + ",Last name: " + phoneList.getLastName() + ",Phone number: " + phoneList.getPhoneNumber());
        }

        searchContact(agendaService);
    }

    private int searchContact(AgendaService agendaService) throws SQLException, IOException, ClassNotFoundException {

        System.out.println("How do you want to search a contact between first name and last name ? 1-first name, 2-last name");
        try {
            Scanner scanner = new Scanner(System.in);
            int chooseMethodSearchContact = scanner.nextInt();
            if (chooseMethodSearchContact < 1 || chooseMethodSearchContact > 2) return searchContact(agendaService);
            else if (chooseMethodSearchContact == 1) {
                System.out.println("Choose a contact after one of the follow first names");
                for (FirstNameFromDatabase firstNameFromDatabase : agendaService.getFirstName()) {
                    System.out.println(firstNameFromDatabase.getFirstName());
                    searchAfterName.add(firstNameFromDatabase.getFirstName());
                }
                firstNameAfterYouChoose=chooseContactAfterFirstName(searchAfterName);
                System.out.println("The contacts are");
                searchPhisicallyContact(firstNameAfterYouChoose,agendaService,firstName);
            } else if (chooseMethodSearchContact == 2) {
                System.out.println("Choose a contact after follow last names");
                for(LastNameFromDatabase lastNameFromDatabase:agendaService.getLastName()){
                    System.out.println(lastNameFromDatabase.getLastName());
                    searchAfterName.add(lastNameFromDatabase.getLastName());
                }
                lastNameAfterYouChoose=chooseContactAfterLastName();
                System.out.println("You choose after last name");
                System.out.println("The contacts are");
                //searchPhisicallyContact("lastName", agendaService);
            }
        } catch (InputMismatchException exception) {
            System.out.println("You must to choose a contact according to options who are show above");
            return searchContact(agendaService);
        }
        return 0;
    }

    private String chooseContactAfterFirstName(List<String> searchAfterName) {
        int i;
        try {
            System.out.println("Choose a first name after you want to search between: ");
            for (i = 0; i < searchAfterName.size(); i++) {
                if (i != searchAfterName.size() - 1) System.out.print((i + 1) + "-" + searchAfterName.get(i) + ", ");
                else System.out.print((i + 1) + "-" + searchAfterName.get(i));
            }
            Scanner scanner = new Scanner(System.in);
            int optionFirstName = scanner.nextInt();
            if (optionFirstName < 1 || optionFirstName > searchAfterName.size()) return chooseContactAfterFirstName(searchAfterName);
            else return searchAfterName.get(optionFirstName-1);
        }catch(InputMismatchException exception){
            System.out.println("You must select a option from above");
            return chooseContactAfterFirstName(searchAfterName);
        }
    }

    private void searchPhisicallyContact(String searchAfterFirstNameOrLastName,AgendaService agendaService,String firstNameOrLastName) throws SQLException, IOException, ClassNotFoundException{
        for(Agenda agenda:agendaService.searchContactAfterFirstNameOrLastName(firstNameOrLastName,searchAfterFirstNameOrLastName)){
            System.out.println("Id: " + agenda.getId() + ",First name: " + agenda.getFirstName() + ",Last name: " + agenda.getLastName() + ",Phone number: " + agenda.getPhoneNumber());
        }
    }
}
