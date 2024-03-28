
package com.example.interfata;

public class Curs {
    public Curs() {
    };

    public Curs(int id, String nume, String descriere, int nr_max) {
        super();
        this.id = id;
        this.nume = nume;
        this.descriere = descriere;
        this.nr_max = nr_max;
    }

    private int id;
    private String nume;
    private String descriere;
    private int nr_max;

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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getNr_max() {
        return nr_max;
    }

    public void setNr_max(int nr_max) {
        this.nr_max = nr_max;
    }

    @Override
    public String toString() {
        return this.nume + this.descriere;
    }

}

