package controller.arquivo;

import model.book.Livro;
import model.pessoas.Autor;
import model.pessoas.Estudante;

import java.io.*;
import java.util.*;


public class ArquivoStudents {
    private int finalId;
    private static ArquivoStudents arquivoStudents;
    private final String pathCsv = "./src/resources/students.tsv";
    private final String pathSer = "./src/resources/students.ser";
    //private FileOutputStream fos;
    //private ObjectOutputStream oos;

    private ArquivoStudents() {
        this.inicializarOutputStream();
    }

    public static ArquivoStudents getInstance() {
        if (arquivoStudents == null)
            return arquivoStudents = new ArquivoStudents();
        return arquivoStudents;
    }

    public void consultaEstudante() {
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Estudante aux;

            do {
                aux = (Estudante) ois.readObject();

                System.out.println(aux);
                finalId = Math.max(finalId, aux.getStudentId());
            } while (aux != null);

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println(e + " Erro ao ler classe");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }

    }

    public void alteraPontos(int studentId, int novosPontos) {
        List<Estudante> lista = new ArrayList<>();


        try( FileInputStream fis = new FileInputStream(pathSer);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Estudante aux;
            while (true) {
                aux = (Estudante) ois.readObject();
                if (aux != null){
                    if (aux.getStudentId() == studentId)
                        aux.setPoint(novosPontos);
                    lista.add(aux.clone());
                }
                else
                    break;
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathSer));
            for (Estudante estudante: lista) {
                oos.writeObject(estudante.clone());
            }
            oos.close();

        }catch (EOFException e) {

        } catch (ClassNotFoundException e) {
            System.out.println(e + " Classe estudante não encontrada");
        } catch (IOException e) {
            System.out.println(e + " ao inserir estudante");
        }


    }

    public Estudante getStudentById(int id) {
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Estudante aux;
            while (true) {
                aux = (Estudante) ois.readObject();
                if (aux.getStudentId() == id)
                    return aux;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println(e+ " Erro ao ler classe");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }
        return null;
    }

    public String getNameById(int id) {
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Estudante aux;
            while (true) {
                aux = (Estudante) ois.readObject();
                if (aux.getStudentId() == id)
                    return aux.getName() + " " + aux.getSurname();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println(e + "Erro ao ler classe");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }
        return null;
    }

    public void cadastrarEstudante(String[] estudanteDados) {
        /*List<Estudante> lista = new ArrayList<>();
        try( FileInputStream fis = new FileInputStream(pathSer);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Estudante aux;
            while (true) {
                aux = (Estudante) ois.readObject();
                if (aux != null)
                    this.write(aux.clone());
                else
                    break;
            }


        }catch (EOFException e) {

        } catch (ClassNotFoundException e) {
            System.out.println(e + " Classe estudante não encontrada");
        } catch (IOException e) {
            System.out.println(e + " ao inserir estudante");
        }*/


            int id = ++finalId;
            String name = estudanteDados[0];
            String surname = estudanteDados[1];
            String bithDate = estudanteDados[2];
            String gender = estudanteDados[3];
            String classe = estudanteDados[4];
            int point = Integer.parseInt(estudanteDados[5]);
            Estudante estudante = new Estudante(name, surname, id, bithDate, gender, classe, point);
            this.write(estudante);


    }

    public void criarArquivo() {

        try (FileInputStream input = new FileInputStream(pathCsv);
             Scanner scanner = new Scanner(input))
        {
            scanner.nextLine();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathSer));
            while (scanner.hasNextLine()) {
                String[] linha = scanner.nextLine().split("\t");
                int id = Integer.parseInt(linha[0]);
                //System.out.println(id);
                String name = linha[1];
                String surname = linha[2];
                String bithDate = linha[3];
                String gender = linha[4];
                String classe = linha[5];
                int point = Integer.parseInt(linha[6]);
                Estudante estudante = new Estudante(name, surname, id, bithDate, gender, classe, point);
                oos.writeObject(estudante);

                finalId = id;
            }


            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo students.csv não encontrado");
        } catch (IOException e) {
            System.out.println("Não foi possível abrir o arquivo students.ser");
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
                Estudante aux;
                while (true) {
                    aux = (Estudante) ois.readObject();
                    finalId = aux.getStudentId();
                }


            }catch (EOFException e) {

            } catch (ClassNotFoundException e) {
                System.out.println(e + " Classe estudante não encontrada");
            } catch (IOException e) {
                System.out.println(e + " ao inserir estudante");
            }
        }

    }

    public int getFinalId() {
        return finalId;
    }

   private void write(Estudante estudante){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathSer, true)) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            oos.writeObject(estudante);

            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Algo de errado ao escrever no arquivo");
        } catch (IOException e) {
            System.out.println("Algo de errado ao escrever no arquivo");
        }
    }

}
