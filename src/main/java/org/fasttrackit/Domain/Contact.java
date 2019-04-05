package org.fasttrackit.Domain;

import org.fasttrackit.Service.AgendaService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Contact {

    Agenda agenda=new Agenda();

    private void settingFirstName() throws MyException{
        System.out.println("Write your first name");
        Scanner scanner=new Scanner(System.in);
        String firstName=scanner.nextLine();
        int i;
        for(i=0;i<firstName.length();i++){
            if((firstName.charAt(i)<(int)'a' || firstName.charAt(i)>'z') && (firstName.charAt(i)<(int)'A' || firstName.charAt(i)>'Z') && firstName.charAt(i)!=' ' && firstName.charAt(i)!='\t')
                throw new MyException("First name can contains only letters");
        }

        int numberSpace=0;
        for(i=0;i<firstName.length();i++){
            if(firstName.charAt(i)==' ' || firstName.charAt(i)=='\t')
                numberSpace++;
        }
        if(numberSpace==firstName.length())
            throw new MyException("This is not a first name");

        if(firstName.trim().charAt(0)<(int)'A' || firstName.trim().charAt(0)>(int)'Z'){
            throw new MyException("First name must begin with uppercase character");
        }
        agenda.setFirstName(firstName.trim());
    }

    private void callSettingsFirstName(){
        try{
            settingFirstName();
        }catch(MyException exception){
            System.out.println(exception);
            callSettingsFirstName();
        }
    }

    private void settingLastName() throws MyException{
        System.out.println("Write your last name");
        Scanner scanner=new Scanner(System.in);
        String lastName=scanner.nextLine();
        int i;
        for(i=0;i<lastName.length();i++){
            if((lastName.charAt(i)<(int)'a' || lastName.charAt(i)>'z') && (lastName.charAt(i)<(int)'A' || lastName.charAt(i)>'Z') && lastName.charAt(i)!=' ' && lastName.charAt(i)!='\t')
                throw new MyException("Last name can contains only letters");
        }

        if(lastName.trim().charAt(0)<(int)'A' || lastName.trim().charAt(0)>(int)'Z'){
            throw new MyException("Last name must begin with uppercase character");
        }
        agenda.setLastName(lastName.trim());
    }

    private void callSettingsLastName(){
        try{
            settingLastName();
        }catch(MyException exception){
            System.out.println(exception);
            callSettingsLastName();
        }
    }

    private void settingPhoneNumber() throws MyException{
        System.out.println("Write your phone number");
        Scanner scanner=new Scanner(System.in);
        String phoneNumber=scanner.nextLine();
        int i;
        for(i=0;i<phoneNumber.trim().length();i++){
            if((phoneNumber.trim().charAt(i)<(int)'0' || phoneNumber.trim().charAt(i)>(int)'9') && phoneNumber.trim().charAt(i)!='+' && phoneNumber.trim().charAt(i)!='-')
                throw new MyException("The phone number must contains only digits, + and/or - sign");
        }
        agenda.setPhoneNumber(phoneNumber);
    }

    private void callSettingsPhoneNumber(){
        try{
            settingPhoneNumber();
        }catch(MyException exception){
            System.out.println(exception);
            callSettingsPhoneNumber();
        }
    }

    public void createContact() throws SQLException, IOException, ClassNotFoundException {
        /*callSettingsFirstName();
        callSettingsLastName();
        callSettingsPhoneNumber();

        agendaService.createContact(agenda);*/
        AgendaService agendaService=new AgendaService();
        System.out.println("The agend contains follow contacts:");
        for(Agenda phoneList:agendaService.getContact()){
            System.out.println("Id: "+phoneList.getId()+",First name: "+phoneList.getFirstName()+",Last name: "+phoneList.getLastName()+",Phone number: "+phoneList.getPhoneNumber());
        }

        searchContact(agendaService);
    }

    private int searchContact(AgendaService agendaService) throws SQLException {
        System.out.println("How do you want to search a contact after first name and last name ? 1-first name, 2-last name");
        try{
            Scanner scanner=new Scanner(System.in);
            int chooseMethodSearchContact=scanner.nextInt();
            if(chooseMethodSearchContact<1 || chooseMethodSearchContact>2)  return searchContact(agendaService);
            else if(chooseMethodSearchContact==1){
                System.out.println("You choose after first name");
                searchPhisicallyContact("first name",agendaService);
            }
            else if(chooseMethodSearchContact==2){
                System.out.println("You choose after last name");
                searchPhisicallyContact("last name",agendaService);
            }
        }catch(InputMismatchException exception){
            System.out.println("You must to choose a contact according to options who are show above");
            return searchContact(agendaService);
        }
        return 0;
    }

    private void searchPhisicallyContact(String optionSearch,AgendaService agendaService) throws SQLException {
        agendaService.searchContact(optionSearch);
    }
}
