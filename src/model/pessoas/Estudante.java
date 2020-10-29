package model.pessoas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Estudante extends Pessoa implements Serializable, Cloneable, Comparable<Estudante> {
    private int studentId;
    private String birthDate;
    private String gender;
    private String classe;
    private int point;
    private List borrowBooks;
    private int qtdBorrows;

    public Estudante(String name, String surname, int studentId, String birthDate, String gender, String classe, int point, List borrowBooks) {
        super.name = name;
        super.surname = surname;
        this.studentId = studentId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.classe = classe;
        this.point = point;
        this.borrowBooks = borrowBooks;
    }

    public Estudante(String name, String surname,int studentId, String birthDate, String gender, String classe, int point) {
        super.name = name;
        super.surname = surname;
        this.studentId = studentId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.classe = classe;
        this.point = point;
    }

    public Estudante(int studentId, int qtdBorrows) {
        this.studentId = studentId;
        this.qtdBorrows = qtdBorrows;
    }

    public Estudante clone(){
        try {
            return (Estudante) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Clonagem não autorizada");
            return this;
        }

    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getClasse() {
        return classe;
    }

    public int getPoint() {
        return point;
    }

    public List getBorrowBooks() {
        return borrowBooks;
    }

    public int getQtdBorrows() {
        return qtdBorrows;
    }


    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.studentId).append(" ").append(this.name).
                append(" ").append(this.surname).append(" ").append(this.birthDate).
                append(" ").append(this.classe).append(" ").append(this.gender).append(" ").
                append(this.point);
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Estudante other) {
        return other.getQtdBorrows() - this.getQtdBorrows();
    }
}
