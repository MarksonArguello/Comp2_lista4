package controller.arquivo;

import model.book.Emprestimo;
import model.book.Estilo;
import model.book.Estilos;
import model.book.Livro;
import model.pessoas.Autor;
import model.pessoas.Estudante;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ArquivoEmprestimo {

    private int finalId;
    private static ArquivoEmprestimo arquivoEmprestimo;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
    private final String pathTsv = "./src/resources/borrows.tsv";
    private final String pathSer = "./src/resources/borrows.ser";
    private ArquivoLivro arquivoLivro = ArquivoLivro.getInstance();
    private ArquivoStudents arquivoStudents = ArquivoStudents.getInstance();


    //private static FileOutputStream fos;
    //private static ObjectOutputStream oos;


    private ArquivoEmprestimo() {
        this.inicializarOutputStream();
    }

    public static ArquivoEmprestimo getInstance() {
        if (arquivoEmprestimo == null)
            return  arquivoEmprestimo = new ArquivoEmprestimo();
        return arquivoEmprestimo;
    }

    public void estilosMaisPopulares() {

        List<Estilos> lista = new ArrayList<>();
        Map <Integer, Integer> map = new HashMap<>();
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Emprestimo aux, first = null;
            while (true) {
                aux = (Emprestimo) ois.readObject();
                if (first == null)
                    first = aux.clone();
                else if (first.equals(aux))
                    break;
                Livro livro = arquivoLivro.getBookById(aux.getBookId());
                int estilo = livro.getTypeId();

                if (map.containsKey(estilo))
                    map.put(estilo, map.get(estilo) + 1);
                else
                    map.put(estilo, 1);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println("Erro ao ler classe");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }
        for(Map.Entry<Integer,Integer> atual : map.entrySet()) {
            String estilo = String.valueOf(Estilo.getById(atual.getKey()));
            lista.add(new Estilos(estilo, atual.getValue()));
        }

        Collections.sort(lista, Estilos::compareTo);
        for (int indice = 0; indice < lista.size(); indice++) {
            System.out.println("Estilo: " + lista.get(indice).getEstilo() + " Quantidade: " + lista.get(indice).getQuantidade());

        }
    }

    public void consultaAutoresMaisPopulares(int quantidade) {
        List<Autor> lista = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Emprestimo aux, first = null;
            ArquivoAutor arquivoAutor = ArquivoAutor.getInstance();

            while (true) {
                aux = (Emprestimo) ois.readObject();
                if (first == null)
                    first = aux.clone();
                else if (first.equals(aux))
                    break;
                Livro livro = arquivoLivro.getBookById(aux.getBookId());
                String nomeAutor = arquivoAutor.getNameById(livro.getAuthorId());

                if (map.containsKey(nomeAutor))
                    map.put(nomeAutor, map.get(nomeAutor) + 1);
                else
                    map.put(nomeAutor, 1);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println("Erro ao ler classe");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }


        for(Map.Entry<String,Integer> atual : map.entrySet()) {
            if(quantidade-- == 0) break;
            lista.add(new Autor(atual.getKey(), atual.getValue()));
        }


        Collections.sort(lista, Autor::compareTo);
        for (int indice = 0; indice < lista.size(); indice++) {
            System.out.println("Nome: " + lista.get(indice).getName() + " Quantidade de livros: " + lista.get(indice).getQtdDeEmprestimos());

        }
    }

    public void consultaLivrosMaisEmprestados(int quantidade) {
        List<Livro> lista = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Emprestimo aux, first = null;
            while (true) {
                aux = (Emprestimo) ois.readObject();
                if (first == null)
                    first = aux.clone();
                else if (first.equals(aux))
                    break;
                if (map.containsKey(aux.getBookId()))
                    map.put(aux.getBookId(), map.get(aux.getBookId()) + 1);
                else
                    map.put(aux.getBookId(), 1);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println("Erro ao ler classe");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }

        for(Map.Entry<Integer,Integer> atual : map.entrySet()) {
            if(quantidade-- == 0) break;
            lista.add(new Livro(atual.getKey(), atual.getValue()));
        }


        Collections.sort(lista, Livro::compareTo);
        for (int indice = 0; indice < lista.size() ; indice++) {
            System.out.println("Livro: " + arquivoLivro.getNameById(lista.get(indice).getBookId()) + " Quantidade de livros: " + lista.get(indice).getQtdEmprestados());

        }
    }

    public void consultaEstudantesQueMaisPegaramLivros(int quantidade) {
        List<Estudante> lista = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Emprestimo aux, first = null;
            while (true) {
                aux = (Emprestimo) ois.readObject();
                if (first == null)
                    first = aux.clone();
                else if (first.equals(aux))
                    break;
                if (map.containsKey(aux.getStudentId()))
                    map.put(aux.getStudentId(), map.get(aux.getStudentId()) + 1);
                 else
                    map.put(aux.getStudentId(), 1);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println("Erro ao ler classe");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }


        for(Map.Entry<Integer,Integer> atual : map.entrySet()) {
            if(quantidade-- == 0) break;
            lista.add(new Estudante(atual.getKey(), atual.getValue()));
        }


        Collections.sort(lista, Estudante::compareTo);
        for (int indice = 0; indice < lista.size(); indice++) {
            System.out.println("Estudante: " + arquivoStudents.getNameById(lista.get(indice).getStudentId()) + " Quantidade de livros: " + lista.get(indice).getQtdBorrows());

        }
    }

    public void consultaEmprestimosDias(long quantidade) {
        Stack<Emprestimo> lista = new Stack<>();
        quantidade *= 24 * 60 * 60 * 1000;
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Emprestimo aux, first = null;
            while (true) {
                aux = (Emprestimo) ois.readObject();
                if (first == null)
                    first = aux.clone();
                else if (first.equals(aux))
                    break;
                if (System.currentTimeMillis() - aux.getTakenDate().getTime() > quantidade)
                    lista.add(aux.clone());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println("Erro ao ler classe");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }

        if(lista.empty()) {
            System.out.println("Não foram encontrados empréstimos");
        }
        while (!lista.empty()) {
            System.out.println(lista.pop().toString());
        }
    }

    public void consultaEmprestimos(int quantidade) {
        Stack<Emprestimo> lista = new Stack<>();
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Emprestimo aux, first = null;
            while (true) {
                aux = (Emprestimo) ois.readObject();
                if (first == null)
                    first = aux.clone();
                else if (first.equals(aux))
                    break;
                lista.add(aux.clone());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }
        while (!lista.empty() && quantidade > 0) {
            System.out.println(lista.pop().toString());
            quantidade--;
        }
    }

    public void registrarDevolucao(String[] dadosDevolucao) {
        ListaEmprestimo listaEmprestimo = ListaEmprestimo.getInstance();
        List<Emprestimo> lista = new ArrayList<>();
        String[] dadosEmprestimo = null;
        try(FileInputStream fis = new FileInputStream(pathSer);
         ObjectInputStream ois = new ObjectInputStream(fis)) {

            Emprestimo aux, first = null, fila = null;
            boolean registrado = false;
            while (true) {
                aux = (Emprestimo) ois.readObject();
                if (first == null)
                    first = aux.clone();
                else if (first.equals(aux))
                    break;
                if (!registrado && aux.getBorrowId() == Integer.parseInt(dadosDevolucao[0])) {
                    aux.setBroughtDate(sdf.parse(dadosDevolucao[1]));

                    dadosEmprestimo = listaEmprestimo.retira(aux.getBookId());
                    System.out.println(dadosEmprestimo);
                    registrado = true;
                }
                lista.add(aux.clone());
            }

        } catch (ParseException e) {
            System.out.println("Erro ao converter data");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {

        } catch (IOException e ) {
            System.out.println("Erro ao ler classe");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe emprestimo não encontrada");
        }
        try{
            ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(pathSer, false));

            //System.out.println(lista.size());
            for (Emprestimo emprestimo : lista)
                oos2.writeObject(emprestimo);
            oos2.close();
            if (dadosEmprestimo != null){
                System.out.println("1");
                this.registrarEmprestimo(dadosEmprestimo);
            }

        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo");
        }

    }

    public void registrarEmprestimo(String[] emprestimoDados) {

        int studentId = Integer.parseInt(emprestimoDados[0]);
        int bookId = Integer.parseInt(emprestimoDados[1]);
        String takenDate = emprestimoDados[2];
        System.out.println("2");

        Estudante estudante = arquivoStudents.getStudentById(studentId);
        Livro livro = arquivoLivro.getBookById(bookId);
        if (estudante.getPoint() > livro.getPoint()) {
            int novosPontos = estudante.getPoint() + (int)Math.floor(livro.getPoint() * 0.2);
            arquivoStudents.alteraPontos(studentId, novosPontos);
        } else {
            System.out.println("O estudante não tem pontos o suficiente");
            return;
        }

        /*List<Emprestimo> lista = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            Emprestimo aux, first = null;
            while (true) {
                aux = (Emprestimo) ois.readObject();
                if (first == null)
                    first = aux.clone();
                else if (first.equals(aux))
                    break;
                if (aux != null)
                    lista.add(aux.clone());
                else
                    break;
            }


        }catch (EOFException e) {

        } catch (ClassNotFoundException e) {
            System.out.println(e + " Classe emprestimo não encontrada");
        } catch (IOException e) {
            System.out.println(e + " ao inserir emprestimo");
        }
           */
        try
        {

           /* System.out.println(lista.size());
            for (Emprestimo emprestimo: lista) {
                oos.writeObject(emprestimo.clone());
            }
            */
            int id = ++finalId;
            System.out.println("3");
            Emprestimo emprestimo = new Emprestimo(id, studentId, bookId, takenDate);
            this.write(emprestimo);
            System.out.println("4");
        } catch (ParseException e) {
            System.out.println("Não foi possível converter data empréstimo");
        }
    }

    private void criarArquivo() {

        try (FileInputStream input = new FileInputStream(pathTsv);
             Scanner scanner = new Scanner(input))
        {
            scanner.nextLine();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathSer));
            while (scanner.hasNextLine()) {
                String[] linha = scanner.nextLine().split("\t");
                int id = Integer.parseInt(linha[0]);
                int studentId = Integer.parseInt(linha[1]);
                int bookId = Integer.parseInt(linha[2]);
                String takenDate = linha[3];
                String broughtDate = linha[4];
                oos.writeObject(new Emprestimo(id,studentId, bookId,takenDate,broughtDate));

                finalId = id;
            }
            oos.close();
        } catch (ParseException e) {
            System.out.println("Não foi possível converter data empréstimo");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo borrows.tsv não encontrado");
        } catch (IOException e) {
            System.out.println(e + " Não foi possível abrir o arquivo borrows.ser");
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
            //fos = new FileOutputStream(pathSer);
            //oos = new ObjectOutputStream(fos);
            criarArquivo();
        } else {
            try(FileInputStream fis = new FileInputStream(pathSer);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                Emprestimo aux;
                do {
                    aux = (Emprestimo) ois.readObject();
                    finalId = aux.getBorrowId();
                } while (aux != null);


            }catch (EOFException e) {

            } catch (ClassNotFoundException e) {
                System.out.println(e + " Classe emprestimo não encontrada");
            } catch (IOException e) {
                System.out.println(e + " ao registrar emprestimo");
            }
        }

    }

    public boolean verificaEmprestimos(int idEstudante, int idLivro) {
        int livrosEmprestados = 0;
        Map<Integer, Integer> exemplaresEmprestados =new HashMap<>();
        int totalExemplares = 0;
        try(FileInputStream fis = new FileInputStream(pathSer);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            Emprestimo aux;
            boolean loop = false;
            int n = 0;
            do {
                aux = (Emprestimo) ois.readObject();
                n++;
                if(aux.getBorrowId() == 1 && loop){
                    System.out.println(aux + " " + n);
                    break;
                }else{
                    loop = true;
                    //System.out.println(aux + " " + n);
                }

                if (aux.getStudentId() == idEstudante && aux.getBroughtDate() == null) {
                    livrosEmprestados++;

                    if (livrosEmprestados == 2) {
                        System.out.println("O estudante já tem 2 empréstimos em aberto");
                        return false;
                    }

                    if (aux.getBookId() == idLivro) {
                        System.out.println("O estudante já pegou esse livro");
                        return false;
                    }
                }
                if(aux.getBookId() == idLivro && aux.getBroughtDate() == null) {
                    if (!exemplaresEmprestados.containsKey(aux.getStudentId())){
                        exemplaresEmprestados.put(aux.getStudentId(), 1);
                        totalExemplares++;
                    }

                    //System.out.println(aux.getStudentId() + " " +exemplaresEmprestados + " " + aux.getBorrowId() + " " + aux.getBroughtDate());

                }

            } while (aux != null);

        }catch (EOFException e) {

        } catch (ClassNotFoundException e) {
            System.out.println(e + " Classe emprestimo não encontrada");
        } catch (IOException e) {
            System.out.println(e + " ao inserir emprestimo");
        }

        if (totalExemplares >= 2) {
            ListaEmprestimo listaEmprestimo = ListaEmprestimo.getInstance();
            System.out.println("Todos os exemplares foram emprestados, adionado a fila de espera");
            listaEmprestimo.adiciona(idEstudante, idLivro);
            return false;
        }

        return true;
    }

    private void write(Emprestimo emprestimo){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathSer, true)) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            oos.writeObject(emprestimo);

            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Algo de errado ao escrever no arquivo");
        } catch (IOException e) {
            System.out.println("Algo de errado ao escrever no arquivo");
        }
    }

    public int getFinalId() {
        return finalId;
    }
}
