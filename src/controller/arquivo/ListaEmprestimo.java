package controller.arquivo;

import model.book.Emprestimo;

import java.io.*;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ListaEmprestimo {

    private static ListaEmprestimo listaEmprestimo;
    private final String path = "./src/resources/fila.txt";
    private File file = new File(path);
    private ArquivoEmprestimo arquivoEmprestimo = ArquivoEmprestimo.getInstance();

    private ListaEmprestimo(){
        cria();
    }

    public static ListaEmprestimo getInstance() {
        if (listaEmprestimo == null)
            return listaEmprestimo = new ListaEmprestimo();
        return listaEmprestimo;
    }

    public void adiciona(int idEstudante, int idLivro) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))){
            bw.write(idEstudante + " " + idLivro + "\n");
        } catch (IOException e) {
            System.out.println(e + " ao adicionar a fila");
        }
    }

    public String[] retira(int idLivro)  {
        try (FileWriter fw =new FileWriter(path, false);
             FileReader fileReader = new FileReader(path);
             Scanner sc = new Scanner(fileReader)){
            String[] emprestimoDados = null;

            List<String> fila = new ArrayList<>();
            while (sc.hasNextLine()) {
                fila.add(sc.nextLine());
            }
            boolean retirado = false;
            System.out.println(fila.size());
            for (String dados : fila) {
                String[] dado = dados.split(" ");
                System.out.println(dado[1] + " " + idLivro);
                if(!retirado && dado[1].equals(String.valueOf(idLivro))) {
                    emprestimoDados = new String[3];
                    emprestimoDados[0] = dado[0];
                    emprestimoDados[1] = dado[1];
                    emprestimoDados[2] = new SimpleDateFormat("dd/MM/yy hh:mm").format(LocalDateTime.now());
                    System.out.println("Em lista emprestimo: "+ emprestimoDados);
                    retirado = true;
                } else {
                    fw.write(dado[0] + " " + dado[1] + "\n");
                }

            }
            if (retirado) {
                return emprestimoDados;
            }

        } catch(IOException e) {
            System.out.println(e + " ao adicionar a fila");
        }
        return null;
    }

    public void cria() {
        if (!file.exists()) {
            try{
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e+ " ao criar lista de emprestimo");
            }

        }
    }

}
