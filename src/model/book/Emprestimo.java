package model.book;

import controller.arquivo.ArquivoLivro;
import controller.arquivo.ArquivoStudents;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emprestimo implements Serializable, Cloneable {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
    private int borrowId;
    private int studentId;
    private int bookId;
    private Date takenDate;
    private Date broughtDate;


    public Emprestimo(int borrowId, int studentId, int bookId, String takenDate, String broughtDate) throws ParseException {
        this.borrowId = borrowId;
        this.studentId = studentId;
        this.bookId = bookId;
        this.takenDate = sdf.parse(takenDate);
        this.broughtDate = sdf.parse(broughtDate);
    }

    public Emprestimo(int borrowId, int studentId, int bookId, String takenDate) throws ParseException {
        this.borrowId = borrowId;
        this.studentId = studentId;
        this.bookId = bookId;
        this.takenDate = sdf.parse(takenDate);
    }

    public Emprestimo clone() {
        try {
            return (Emprestimo) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Clonagem não autorizada");
            return this;
        }
    }

    @Override
    public String toString() {
        ArquivoLivro arquivoLivro = ArquivoLivro.getInstance();
        ArquivoStudents arquivoStudents = ArquivoStudents.getInstance();
        StringBuilder sb = new StringBuilder();

        sb.append("ID = ").append(this.borrowId).append(", Livro = ").append(arquivoLivro.getNameById(this.bookId))
                .append(", Estudante = ").append(arquivoStudents.getNameById(this.studentId)).append(", Data empréstimo = ").append(sdf.format(takenDate));

        if (broughtDate != null) {
            sb.append(", Data devolução = ").append(sdf.format(broughtDate));
        } else {
            sb.append(" EM ABERTO");
        }
        return sb.toString();
    }

    public void setBroughtDate(Date broughtDate) {
        this.broughtDate = broughtDate;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public Date getTakenDate() {
        return takenDate;
    }

    public Date getBroughtDate() {
        return broughtDate;
    }
}
