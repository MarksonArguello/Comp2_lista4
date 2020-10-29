package model.pessoas;

import com.sun.source.doctree.AuthorTree;

import java.io.Serializable;

public class Autor extends Pessoa implements Serializable, Cloneable, Comparable<Autor> {
    private int id;
    private int qtdDeEmprestimos;

    public Autor(int id, String name, String surname) {
        this.id = id;
        super.name = name;
        super.surname = surname;
    }



    public Autor(String name, int qtdDeEmprestimos) {
        this.qtdDeEmprestimos = qtdDeEmprestimos;
        super.name = name;
    }

    public int getQtdDeEmprestimos() {
        return qtdDeEmprestimos;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID = ").append(this.id).append(", Nome = ").append(this.name).append(" ").append(this.surname);
        return sb.toString();
    }
    public Autor clone() {
        try {
            return (Autor) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Clonagem não autorizada");
            return this;
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Autor other) {
        return other.getQtdDeEmprestimos() - this.getQtdDeEmprestimos();
    }
}
