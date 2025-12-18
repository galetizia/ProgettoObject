package model;

public abstract class User {

    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String password;
    private Integer hackathonID;

    protected User(String nome, String cognome, String email, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public String getNome() {return nome;}
    public String getCognome() {return cognome;}
    public String getEmail() {return email;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public Integer getHackathonID() {return hackathonID;}

    public void setNome(String nome) {this.nome = nome;}
    public void setCognome(String cognome) {this.cognome = cognome;}
    public void setEmail(String email) {this.email = email;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setHackathonID(Integer hackathonID) {this.hackathonID = hackathonID;}
}
