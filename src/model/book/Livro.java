package model.book;

import model.pessoas.Estudante;

import java.io.Serializable;

public class Livro implements Serializable, Cloneable, Comparable<Livro> {
    private int bookId;
    private int authorId;
    private int typeId;
    private String name;
    private int pageCount;
    private int point;
    private int qtdEmprestados;

    public Livro(int bookId, int authorId, int typeId, String name, int pageCount, int point) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.typeId = typeId;
        this.name = name;
        this.pageCount = pageCount;
        this.point = point;
    }

    public Livro(int bookId, int qtdEmprestados) {
        this.bookId = bookId;
        this.qtdEmprestados = qtdEmprestados;
    }

    public int getQtdEmprestados() {
        return qtdEmprestados;
    }

    public Livro clone(){
        try {
            return (Livro) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Clonagem não autorizada");
            return this;
        }
    }

    @Override
    public int compareTo(Livro other) {
        return other.getQtdEmprestados() - this.getQtdEmprestados();
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
