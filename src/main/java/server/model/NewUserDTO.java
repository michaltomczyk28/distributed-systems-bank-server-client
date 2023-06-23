package server.model;

public class NewUserDTO {
    private String firstname;
    private String lastname;
    private String pesel;
    private String username;
    private String password;
    private String accountNumber;

    public NewUserDTO(String firstname, String lastname, String pesel, String username, String password) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.pesel = pesel;
        this.username = username;
        this.password = password;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getPesel() {
        return this.pesel;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
