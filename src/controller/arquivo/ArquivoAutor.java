package controller.arquivo;

import model.book.Emprestimo;
import model.pessoas.Autor;
import model.pessoas.Pessoa;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ArquivoAutor {
    private int finalId;
    private static ArquivoAutor arquivoAutor;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
    private final String pathTsv = "./src/resources/authorsFull.tsv";
    private final String pathSer = "./src/resources/authorsFull.ser";
    //private FileOutputStream fos;
    //private ObjectOutputStream oos;

    private ArquivoAutor() {
        this.inicializarOutputStream();
    }

    public static ArquivoAutor getInstance() {
        if (ArquivoAutor.arquivoAutor == null)
            return arquivoAutor = new ArquivoAutor();

            return arquivoAutor;
    }

    public String getNameById(int id) {
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Autor aux;
            while (true) {
                aux = (Autor) ois.readObject();
                finalId = Math.max(aux.getId(),finalId);
                if (aux.getId() == id)
                    return aux.getName() + " " + aux.getSurname();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println("Erro ao ler classe");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }
        return null;
    }

    public boolean consultaAutor(String nome) {
        Stack<Autor> lista = new Stack<>();
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Autor aux;
            List<String> nomeCompleto = Arrays.asList(nome.split(" "));
            while (true) {
                aux = (Autor) ois.readObject();
                if(aux.getName().equals(nomeCompleto.get(0)) || aux.getSurname().equals(nomeCompleto.get(0)) ||
                (nomeCompleto.size() == 2 && (aux.getName().equals(nomeCompleto.get(1)) || aux.getSurname().equals(nomeCompleto.get(1))))) {
                    lista.add(aux.clone());
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println("Erro ao ler classe");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }

        if (lista.empty()){
            return false;
        }
        while (!lista.empty()) {
            System.out.println(lista.pop().toString());
        }
        return true;
    }

    public void registrarAutor(String[] autorNome) {
        /*List<Autor> lista = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            Autor aux;
            while (true) {
                aux = (Autor) ois.readObject();
                if (aux != null)
                    lista.add(aux.clone());
                else
                    break;
            }
            for (Autor autor: lista) {
                this.write(autor.clone());
            }

        }catch (EOFException e) {

        } catch (ClassNotFoundException e) {
            System.out.println(e + " Classe emprestimo não encontrada");
        } catch (IOException e) {
            System.out.println(e + " ao inserir emprestimo");
        }


            for (Autor autor: lista) {
                this.write(autor.clone());
            }
            */
            int id = ++finalId;

            String name = autorNome[0];
            String surName = autorNome[1];

            Pessoa autor = new Autor(id, name, surName);
            this.write((Autor)autor);
            System.out.println("ID do autor = " + finalId);


    }

    private void criarArquivo() {

        try (FileInputStream input = new FileInputStream(pathTsv);
             Scanner scanner = new Scanner(input))
        {
            scanner.nextLine();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(pathSer));
            while (scanner.hasNextLine()) {
                String[] linha = scanner.nextLine().split("\t");
                int id = Integer.parseInt(linha[0]);
                String name = linha[1];
                String surName = linha[2];
                objectOutputStream.writeObject(new Autor(id, name, surName));

                finalId = id;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo borrows.tsv não encontrado");
        } catch (IOException e) {
            System.out.println("Não foi possível abrir o arquivo borrows.ser");
        }
    }

    private void write(Autor autor){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathSer, true)) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            oos.writeObject(autor);

            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Algo de errado ao escrever no arquivo");
        } catch (IOException e) {
            System.out.println("Algo de errado ao escrever no arquivo");
        }
    }

    public void inicializarOutputStream() {
        //try {
            criaFos();
        /*} catch (IOException e) {
            System.out.println(e + " ao criar arquivos");
        }*/
    }

    private void criaFos() {

        File file = new File(pathSer);
        if (!file.exists()){
            criarArquivo();
        } else {
            try(FileInputStream fis = new FileInputStream(pathSer);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                Autor aux;
                while (true) {
                    aux = (Autor) ois.readObject();
                    finalId = aux.getId();
                }
            }catch (EOFException e) {

            } catch (ClassNotFoundException e) {
                System.out.println(e + " Classe emprestimo não encontrada");
            } catch (IOException e) {
                System.out.println(e + " ao registrar emprestimo");
            }
        }

    }

    public int getFinalId() {
        return finalId;
    }
}
