package view;

import controller.arquivo.*;

public class Main {



    public static void main(String[] args) {
        Mediator mediator = Mediator.getInstance();

        int quantidadeConsulta;
        boolean ok = true;
        do {
            System.out.println("====================MENU====================");
            System.out.println("1 - Realizar cadastro (estudante ou livro).");
            System.out.println("2 - Realizar registro (empréstimo ou devolução).");
            System.out.println("3 - Consulta dos últimos empréstimos.");
            System.out.println("4 - Consulta aos empréstimos com mais de N dias.");
            System.out.println("5 - Consulta aos N estudantes que pegaram mais livros emprestados.");
            System.out.println("6 - Consulta aos N livros mais emprestados.");
            System.out.println("7 - Consulta aos N autores mais populares.");
            System.out.println("8 - Consulta aos estilos literários mais populares.");
            System.out.println("9 - Sair");
            String option = mediator.sc.nextLine();
            System.out.println();
            boolean cont = false;
            String op;
            switch (option) {
                case "1":
                    do {
                        System.out.println("====Cadastro====");
                        System.out.println("1 - Cadastrar estudante");
                        System.out.println("2 - Cadastrar livro");
                        op = mediator.sc.nextLine();

                        switch (op) {
                            case "1":
                                mediator.lerDadosEstudante();
                                cont = true;
                                break;
                            case "2":
                                mediator.lerDadosLivro();
                                cont = true;
                                break;
                            default:
                                System.out.println("Opção inválida");
                        }
                    } while (!cont);
                    break;

                case "2":
                    cont = false;
                    do {
                        System.out.println("=======Registro=======");
                        System.out.println("1 - Registrar empréstimo");
                        System.out.println("2 - Registrar devolução");
                        op = mediator.sc.nextLine();
                        switch (op) {
                            case "1":
                                mediator.lerDadosEmprestimo();
                                cont = true;
                                break;
                            case "2":
                                mediator.lerDadosDevolucao();
                                cont = true;
                                break;
                            default:
                                System.out.println("Opção inválida");
                        }
                    } while (!cont);
                    break;
                case "3":
                    System.out.println("Quantos empréstimos? ");
                    quantidadeConsulta = Integer.parseInt(mediator.sc.nextLine());
                    mediator.consultaEmprestimos(quantidadeConsulta);
                    break;
                case "4":
                    System.out.println("Com mais de quantos dias? ");
                    quantidadeConsulta = Integer.parseInt(mediator.sc.nextLine());
                    mediator.consultaEmprestimosDias(quantidadeConsulta);
                    break;
                case "5":
                    System.out.println("Quantos estudantes? ");
                    quantidadeConsulta = Integer.parseInt(mediator.sc.nextLine());
                    mediator.consultaEstudantesQueMaisPegaramLivros(quantidadeConsulta);
                    break;
                case "6":
                    System.out.println("Quantos livros? ");
                    quantidadeConsulta = Integer.parseInt(mediator.sc.nextLine());
                    mediator.consultaLivrosMaisEmprestados(quantidadeConsulta);
                    break;
                case "7":
                    System.out.println("Quantos autores? ");
                    quantidadeConsulta = Integer.parseInt(mediator.sc.nextLine());
                    mediator.consultaAutoresMaisPopulares(quantidadeConsulta);
                    break;
                case "8":
                    mediator.estilosMaisPopulares();
                    break;
                case "9":
                    ok = false;
                    break;
                default:
                    System.out.println("Opção inválida\n");
            }

        } while (ok);
    }
}
