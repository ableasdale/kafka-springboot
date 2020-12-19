package com.alexbleasdale.kafkademo;

import java.util.Objects;

public class Model {

    public Model() {
    }

    public Model(String firstname, String surname) {
        this.firstname = firstname;
        this.surname = surname;
    }

    private String firstname;
    private String surname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return firstname.equals(model.firstname) &&
                surname.equals(model.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, surname);
    }

    @Override
    public String toString() {
        return "Model{" +
                "firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
