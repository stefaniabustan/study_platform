package com.example.interfata;



public class Persoana {

    public Persoana() {
        this.functie = -1;
    };

    public Persoana(int id, String cnp, String nume, String prenume, String adresa, String telefon, String mail,
                    String parola, String contIban, int nrContract, int functie) {

        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.adresa = adresa;
        this.telefon = telefon;
        this.mail = mail;
        this.parola = parola;
        this.contIban = contIban;
        this.nrContract = nrContract;
        this.functie = functie;
        this.group = group;
    }

    private int id;
    private String nume;
    private String prenume;
    private String cnp;
    private String adresa;
    private String telefon;
    private String mail;
    private String parola;
    private String contIban;
    private int nrContract;
    private int functie;

    private static String[] group = { "Admin", "Student", "Profesor"};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getContIban() {
        return contIban;
    }

    public void setContIban(String contIban) {
        this.contIban = contIban;
    }

    public int getNrContract() {
        return nrContract;
    }

    public void setNrContract(int nrContract) {
        this.nrContract = nrContract;
    }

    public int getFunctie() {
        return functie;
    }

    public void setFunctie(int functie) {
        this.functie = functie;
    }

    public String toString() {
        return this.nume + " " + this.prenume;
    }

}
