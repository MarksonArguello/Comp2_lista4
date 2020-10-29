package controller.arquivo;

import view.Main;

import java.util.Scanner;

public class Mediator {
    public Scanner sc = new Scanner(System.in);
    private static Mediator mediator;
    private ArquivoStudents arquivoStudents;
    private ArquivoLivro arquivoLivro;
    private ArquivoAutor arquivoAutor;
    private ArquivoEmprestimo arquivoEmprestimo;

    private Mediator(){
        inicializa();
    }

    public static Mediator getInstance() {
        if (mediator == null)
            return new Mediator();
        return mediator;
    }

    private void inicializa() {
        this.arquivoStudents = ArquivoStudents.getInstance();
        this.arquivoLivro = ArquivoLivro.getInstance();
        this.arquivoAutor = ArquivoAutor.getInstance();
        this.arquivoEmprestimo = ArquivoEmprestimo.getInstance();
    }

    public void consultaEmprestimos(int quantidadeConsulta) {
        arquivoEmprestimo.consultaEmprestimos(quantidadeConsulta);
    }

    public void consultaEmprestimosDias(int quantidadeConsulta) {
        arquivoEmprestimo.consultaEmprestimosDias(quantidadeConsulta);
    }

    public void consultaEstudantesQueMaisPegaramLivros(int quantidadeConsulta) {
        arquivoEmprestimo.consultaEstudantesQueMaisPegaramLivros(quantidadeConsulta);
    }

    public void consultaLivrosMaisEmprestados(int quantidadeConsulta) {
        arquivoEmprestimo.consultaLivrosMaisEmprestados(quantidadeConsulta);
    }

    public void consultaAutoresMaisPopulares(int quantidadeConsulta) {
        arquivoEmprestimo.consultaAutoresMaisPopulares(quantidadeConsulta);
    }

    public void estilosMaisPopulares() {
        arquivoEmprestimo.estilosMaisPopulares();
    }

    public void lerDadosDevolucao() {
        String[] dadosDevolucao = new String[2];
        boolean ok = false;
        do{
            System.out.println("Digite o Id do empréstimo:");
            dadosDevolucao[0] = sc.nextLine();
            try {
                int id = Integer.parseInt(dadosDevolucao[0]);
                if (id > arquivoEmprestimo.getFinalId() || id <= 0) {
                    System.out.println("Id inexistente");
                } else {
                    ok = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números");
            }
        } while (!ok);

        System.out.println("Digite a data e hora da devolução (dd/MM/aa hh:mm):");
        dadosDevolucao[1] = sc.nextLine();

        this.arquivoEmprestimo.registrarDevolucao(dadosDevolucao);
    }

    public void lerDadosEmprestimo() {
        String[] dadosEmprestimo = new String[3];
        boolean ok = false;
        do{
            arquivoStudents.consultaEstudante();
            System.out.println("Digite o Id do estudante:");
            dadosEmprestimo[0] = sc.nextLine();
            try {
                int id = Integer.parseInt(dadosEmprestimo[0]);
                if (id > arquivoStudents.getFinalId() || id <= 0) {
                    System.out.println("Id inexistente");
                }else {
                    ok = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números");
            }
        } while (!ok);
        ok = false;

        do{
            System.out.println("Digite o nome do livro: ");
            String nome = sc.nextLine();
            if (!arquivoLivro.consultaLivro(nome)) {
                System.out.println("Livro não encontrado");
                continue;
            }
            System.out.println("Digite o Id do livro:");
            dadosEmprestimo[1] = sc.nextLine();
            try {
                int id = Integer.parseInt(dadosEmprestimo[1]);
                if (id > arquivoLivro.getFinalId() || id <= 0) {
                    System.out.println("Id inexistente");
                }else {
                    ok = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números");
            }
        } while (!ok);

        if(!arquivoEmprestimo.verificaEmprestimos(Integer.parseInt(dadosEmprestimo[0]), Integer.parseInt(dadosEmprestimo[1])))
            return;

        System.out.println("Digite a data e hora do empréstimo (dd/MM/aa hh:mm):");
        dadosEmprestimo[2] = sc.nextLine();

        arquivoEmprestimo.registrarEmprestimo(dadosEmprestimo);
    }

    public void lerDadosEstudante() {
        String[] dadosEstudante = new String[7];
        boolean ok = false;

        System.out.println("Digite o nome: ");
        dadosEstudante[0] = sc.nextLine();

        System.out.println("Digite o sobrenome: ");
        dadosEstudante[1] = sc.nextLine();

        do {
            System.out.println("Digite a data de nascimento (dd/MM/aa):");
            dadosEstudante[2] = sc.nextLine();
            try{
                String[] data =  dadosEstudante[2].split("/");
                if (data.length == 3) {
                    int ano = Integer.parseInt(data[2]);
                    int mes = Integer.parseInt(data[1]);
                    int dia = Integer.parseInt(data[0]);
                    ok = (ano <= 99 && ano >= 0 && mes <= 12 && mes >= 1 && dia <= 31 && dia >= 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números");
            }
        } while(!ok);

        ok = false;
        do{
            System.out.println("Digite o gênero (M ou F):");
            dadosEstudante[3] = String.valueOf(sc.nextLine().charAt(0));
            ok = dadosEstudante[3].equals("M") || dadosEstudante[3].equals("F");
        } while (!ok);


        System.out.println("Digite a classe:");
        dadosEstudante[4] = sc.nextLine();

        ok = false;
        do{
            System.out.println("Digite os pontos:");
            dadosEstudante[5] = sc.nextLine();
            try {
                Integer.parseInt(dadosEstudante[5]);
                ok = true;
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números");
            }
        } while (!ok);


        arquivoStudents.cadastrarEstudante(dadosEstudante);
    }

    public void lerDadosLivro() {
        String[] dadosLivro = new String[5];
        boolean ok = false;
        do{
            System.out.println("Digite o nome ou sobrenome do autor: ");
            String nome = sc.nextLine();
            if (!arquivoAutor.consultaAutor(nome)) {
                System.out.println("Autor não encontrado");
                System.out.println("Deseja cadastrá-lo?");
                System.out.println("1 - Sim");
                System.out.println("2 - Não");
                boolean cont = false;
                do{
                    String op = sc.nextLine();
                    if(op.equals("1")) {
                        System.out.println("Digite o nome e sobrenome do autor");
                        nome = sc.nextLine();
                        arquivoAutor.registrarAutor(nome.split(" "));
                        cont = true;
                    } else if (!op.equals("2")){
                        System.out.println("Opção inválida");
                    } else {
                        cont = true;
                    }
                } while (!cont);

            }
            System.out.println("Digite o ID do autor: ");
            dadosLivro[0] = sc.nextLine();
            try {
                int id = Integer.parseInt(dadosLivro[0]);
                if (id <= 0 || id > arquivoAutor.getFinalId()) {
                    System.out.println("Id inexistente");
                } else {
                    ok = true;
                }
            } catch (NumberFormatException e ) {
                System.out.println("Digite apenas números");
            }
        } while (!ok);

        ok = false;
        do{
            System.out.println("Digite o ID do tipo: ");
            dadosLivro[1] = sc.nextLine();
            try {
                int id = Integer.parseInt(dadosLivro[1]);
                if (id > 18 || id <= 0) {
                    System.out.println("ID inexistente");
                }
                ok = true;
            } catch (NumberFormatException e ) {
                System.out.println("Digite apenas números");
            }
        } while (!ok);


        ok = false;
        System.out.println("Digite o nome do livro");
        dadosLivro[2] = sc.nextLine();

        do{
            System.out.println("Digite a quantidade de paginas: ");
            dadosLivro[3] = sc.nextLine();
            try {
                Integer.parseInt(dadosLivro[3]);
                ok = true;
            } catch (NumberFormatException e ) {
                System.out.println("Digite apenas números");
            }
        } while (!ok);
        ok = false;

        do{
            System.out.println("Digite a quantidade de pontos: ");
            dadosLivro[4] = sc.nextLine();
            try {
                Integer.parseInt(dadosLivro[4]);
                ok = true;
            } catch (NumberFormatException e ) {
                System.out.println("Digite apenas números");
            }
        } while (!ok);

        arquivoLivro.cadastrarLivro(dadosLivro);
    }
}
