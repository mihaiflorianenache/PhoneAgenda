package org.fasttrackit.Domain;

import org.fasttrackit.Service.AgendaService;

import java.io.IOException;
import java.sql.SQLException;
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
        callSettingsFirstName();
        callSettingsLastName();
        callSettingsPhoneNumber();

        AgendaService agendaService=new AgendaService();
        agendaService.createContact(agenda);
    }
}
