package server.model;

public class UserInformation {
    private String firstname;
    private String lastname;
    private String username;
    private String pesel;
    private String accountNumber;

    public UserInformation(String firstname, String lastname, String username, String pesel, String accountNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.pesel = pesel;
        this.accountNumber = accountNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPesel() {
        return pesel;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
