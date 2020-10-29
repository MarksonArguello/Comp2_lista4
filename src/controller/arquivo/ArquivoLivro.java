package controller.arquivo;

import model.book.Emprestimo;
import model.book.Livro;
import model.pessoas.Estudante;

import java.io.*;
import java.util.*;

public class ArquivoLivro {
    private int finalId;
    private static ArquivoLivro arquivoLivro;
    private final String pathCsv = "./src/resources/books.tsv";
    private final String pathSer = "./src/resources/books.ser";
    //private FileOutputStream fos;
    //private ObjectOutputStream oos;


    private void write(Livro livro){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathSer, true)) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            oos.writeObject(livro);

            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Algo de errado ao escrever no arquivo");
        } catch (IOException e) {
            System.out.println("Algo de errado ao escrever no arquivo");
        }
    }

    private ArquivoLivro() {
        this.inicializarOutputStream();
    }

    public static ArquivoLivro getInstance() {
        if (arquivoLivro == null)
            return arquivoLivro = new ArquivoLivro();
        return arquivoLivro;
    }

    public boolean consultaLivro(String nome) {
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Livro aux;

            while (true) {
                aux = (Livro) ois.readObject();
                finalId = Math.max(aux.getBookId(), finalId);
                if(aux.getName().equals(nome)) {
                    System.out.println(aux.getBookId() + " " +  aux.getName());
                    return true;
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


        return false;
    }

    public String getNameById(int id) {
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Livro aux;
            while (true) {
                aux = (Livro) ois.readObject();
                finalId = Math.max(aux.getBookId(), finalId);
                if (aux.getBookId() == id)
                    return aux.getName();
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

    public Livro getBookById(int id) {
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            Livro aux;
            while (true) {
                aux = (Livro) ois.readObject();
                finalId = Math.max(aux.getBookId(), finalId);
                if (aux.getBookId() == id)
                    return aux;
            }
        }catch (EOFException e) {

        } catch (ClassNotFoundException e) {
            System.out.println(e + " Classe livro não encontrada");
        } catch (IOException e) {
            System.out.println(e + " ao inserir livro");
        }
        return null;
    }

    public void cadastrarLivro(String[] livroDados) {
        /*List<Livro> lista = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            Livro aux;
            while (true) {
                aux = (Livro) ois.readObject();
                if (aux != null)
                    lista.add(aux.clone());
                else
                    break;
            }
            for (Livro livro: lista) {
                this.write(livro.clone());
            }

        }catch (EOFException e) {

        } catch (ClassNotFoundException e) {
            System.out.println(e + " Classe livro não encontrada");
        } catch (IOException e) {
            System.out.println(e + " ao inserir livro");
        }
*/
            int id = ++finalId;

            int authorId = Integer.parseInt(livroDados[0]);
            int typeId = Integer.parseInt(livroDados[1]);
            String name = livroDados[2];
            int pageCount = Integer.parseInt(livroDados[3]);
            int point = Integer.parseInt(livroDados[4]);
            Livro livro = new Livro(id, authorId, typeId, name, pageCount, point);
            this.write(livro);
    }

    private void criarArquivo() {

        try (FileInputStream input = new FileInputStream(pathCsv);
             Scanner scanner = new Scanner(input))
        {
            scanner.nextLine();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathSer));
            while (scanner.hasNextLine()) {
                String[] linha = scanner.nextLine().split("\t");
                int id = Integer.parseInt(linha[0]);
                String name = linha[1];
                int pageCount = Integer.parseInt(linha[2]);
                int point = Integer.parseInt(linha[3]);
                int authorId = Integer.parseInt(linha[4]);
                int typeId = Integer.parseInt(linha[5]);

                oos.writeObject(new Livro(id, authorId, typeId, name, pageCount, point));

                finalId = id;
            }
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo books.tsv não encontrado");
        } catch (IOException e) {
            System.out.println("Não foi possível abrir o arquivo books.ser");
        }
    }

    public void inicializarOutputStream() {
        criaFos();
    }

    private void criaFos() {
        File file = new File(pathSer);
        if (!file.exists()){
            criarArquivo();
        } else {
            try(FileInputStream fis = new FileInputStream(pathSer);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                Livro aux;
                while (true) {
                    aux = (Livro) ois.readObject();
                    finalId = aux.getBookId();
                }
            }catch (EOFException e) {

            } catch (ClassNotFoundException e) {
                System.out.println(e + " Classe livro não encontrada");
            } catch (IOException e) {
                System.out.println(e + " ao inserir livro");
            }
        }

    }

    public int getFinalId() {
        return finalId;
    }

}
