package poo.gamed;

import poo.gamed.exception.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Mediateca {
        static ArrayList<Utente> utentes = new ArrayList<>(); //arrayist que armazena os utentes da mediateca
        static ArrayList<Obra> obras = new ArrayList<>(); //Arralist que armazena as obras
        static ArrayList<Categoria> categorias = new ArrayList<>();
        static ArrayList<Requisicao> requisicoes = new ArrayList<>();
        static String arquivoAtual = "mediateca.dat";// Adicionar variável para arquivo atual
        static Scanner teclado = new Scanner(System.in);


        static void inicializarCategorias() {
        categorias.add(new Categoria("FICÇÃO"));
        categorias.add(new Categoria("REFERÊNCIA"));
        categorias.add(new Categoria("TÉCNICAS E CIENTÍFICAS"));
    }
        //adicionar um novo utente na biblioteca
        static void addUtente() throws UserRegistrationFailedException {
            try {
                String nome = requestUserName();
                String email = requestUserEMail();
                utentes.add(new Utente(nome, email));
                System.out.println(userRegistrationSuccessful());
            } catch (Exception e) {
                throw new UserRegistrationFailedException("Falha ao Registra utente");
            }
        }
        // adicionar uma obra do tipo livro na biblioteca
        static void addLivros() throws WorkRegistrationFailedException {
            try {
                String titulo = requestTitle();
                float preco = requestPrice();
                int totalExemplares = requestNumCopies();
                int exemplaresdisponiveis = totalExemplares;
                Categoria categoria = requestCategory();
                System.out.println("Nome do autor: ");
                String autores =teclado.nextLine();
                String isbn = requestISBN();
                obras.add(new Livro(titulo, preco, totalExemplares, exemplaresdisponiveis, categoria, autores, isbn));
                System.out.println(workRegistrationSuccessful());
            }catch (WorkRegistrationFailedException e){
                throw new WorkRegistrationFailedException("Falha no registo da obra");
            }
        }
        static void addDvd() throws WorkRegistrationFailedException{
            try {
                String titulo = requestTitle();
                float preco = requestPrice();
                int totalExemplares = requestNumCopies();
                int exemplaresdisponiveis = totalExemplares;
                Categoria categoria = requestCategory();
                System.out.print("Nome do Realizador: ");
                String realizador = teclado.nextLine().trim();
                while (realizador.isEmpty()) {
                    System.out.print("Realizador não pode estar vazio. Digite novamente: ");
                    realizador = teclado.nextLine().trim();
                }
                String DNDAC = requestDNDAC();
                obras.add(new DVD(titulo, preco, totalExemplares, exemplaresdisponiveis, categoria, realizador, DNDAC));
                System.out.println(workRegistrationSuccessful());
            }catch (Exception e){
                throw new WorkRegistrationFailedException("Falha no registo da Obra");
            }
        }
        //mostar dados de um utente
        static void showUtente() {
            if (utentes.isEmpty()) {
                System.out.println("Não tem nenhum utente cadastrado. ");
            } else {
                System.out.println("Digite o Nome do Utente: ");
                String Procurado = teclado.nextLine();

                for (Utente u : utentes) {
                    if (Procurado.equalsIgnoreCase(u.getNome())) {
                        System.out.println(u.toString());
                    }
                }

            }
        }
        //imprimir os utentes da mediateca
        static void showUtentes() {
            if (utentes.isEmpty()) {
                System.out.println("Não tem nenhum utente cadastrado. ");
            } else {
                System.out.println("\n=== LISTA DE UTENTES ===\n");
                for (Utente u : utentes) {
                    System.out.println(u.toString());
                }
            }
        }
        //mostra os dados de um dvd
        static void showDvd() {
            showObra();
        }
        //imprimir dvs da mediateca
        static void showDvds() {
            if (obras.isEmpty()) {
                System.out.println("Não tem nenhum DVD cadastrado. ");
            } else {
                System.out.println("\n=== LISTA DE DVDS===\n");
                for (Obra o : obras) {
                    if (o instanceof DVD) {
                        System.out.println(o.toString());
                    }
                }


            }
        }
        //mostrar os dados de um livros
        static void showLivro() {
            showObra();
        }
        //imprir os livros da mediateca
        static void showLivros() {
            if (obras.isEmpty()) {
                System.out.println("Não tem nenhum DVD cadastrado. ");
            } else {
                System.out.println("\n=== LISTA DE DVDS===\n");
                for (Obra o : obras) {
                    if (o instanceof Livro) {
                        System.out.println(o.toString());
                    }
                }
            }
        }
        static void showObra() throws NoSuchWorkException {
            String id = requestWorkId();
            Obra o = findObraById(id);
            System.out.println(o);
        }
        static void showObras() {
        List<Obra> sorted = new ArrayList<>(obras);
        sorted.sort(Comparator.comparing(Obra::getId));//comparador met da bibioteca util
        for (Obra o : sorted) {
            System.out.println(o);
        }
    }
        static void efectuarPesquisa() {
        String term = requestSearchTerm().toLowerCase();
        List<Obra> results = new ArrayList<>();
        for (Obra o : obras) {
            boolean match = false;
            if (o.getTitulo().toLowerCase().contains(term)) match = true;
            if (o instanceof Livro) {
                Livro l = (Livro) o; //typecasting de Obara para livro
                if (l.getAutores().toLowerCase().contains(term)) match = true;
            } else if (o instanceof DVD) {
                DVD d = (DVD) o;
                if (d.getRealizador().toLowerCase().contains(term)) match = true;
            }
            if (match) results.add(o);
        }
        results.sort(Comparator.comparing(Obra::getId));
        for (Obra o : results) {
            System.out.println(o);
        }
    }

         //requisitar
        static void requisitarObra() throws NoSuchUserException, NoSuchWorkException, RuleFailedException {
                //recebe o id e valida o usuario
                int userId = requestUserId();
                Utente u = findUtenteById(userId);
                // recece o id da obra e valaida
                String workId = requestWorkId();
                Obra o = findObraById(workId);
                // 3. Aplicacap das regras de validacao
                validateRequisicaoRules(u, o, userId, workId);
                // 4. Processa a requisicao
                processRequisition(u, o);
        }
        private static void validateRequisicaoRules(Utente u, Obra o, int userId, String workId)
            throws RuleFailedException {

        // nao pode requisitar obras de referencia
        if ("REFERÊNCIA".equalsIgnoreCase(o.getCategoria().getNome())) {
            throw new RuleFailedException("Não pode requisitar obras de referência.");
        }

        // nao pode requisitar obras com preco maior de 10000
        if (o.getPreco() > 10000) {
            throw new RuleFailedException("Não pode requisitar obras com preço superior a 10.000 Kz.");
        }

        // verificar se o usuario requisitou a obra
        int numReqUser = 0;
        boolean obraAlreadyRequested = false;

        for (Requisicao r : requisicoes) {
            if (r.getUtente().getId() == userId) {
                numReqUser++;
                if (r.getObra().getId().equals(workId)) {
                    obraAlreadyRequested = true;
                }
            }
        }

        // nao pode requisitar mesma obra duas veses
        if (obraAlreadyRequested) {
            throw new RuleFailedException("Não pode requisitar a mesma obra duas vezes.");
        }

        // nao pode ter duas requisicoes activas
        if (numReqUser >= 2) {
            throw new RuleFailedException("Não pode ter mais de 2 obras requisitadas.");
        }

        // verifica se há numeros de exemplares sufucientes para requisica
        if (o.getExemplaresdisponiveis() == 0) {
            boolean pref = requestReturnNotificationPreference();
            if (pref) {
                registerNotificationRequest(u, o);
            }
            throw new RuleFailedException("Sem exemplares disponíveis.");
        }
    }
        private static void processRequisition(Utente u, Obra o) {
        // Decrementa o numero de copias disponiveis
        o.setExemplaresdisponiveis(o.getExemplaresdisponiveis() - 1);

        // Instancia a requisica e armazena
        Requisicao req = new Requisicao(u, o);
        requisicoes.add(req);
        System.out.println(workRequestSuccessful());

            // retorna a data
        LocalDate dataDevolucao = req.getDataDeDevolucao();
        if (dataDevolucao!= null) {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataDevolucao.format(formato);
            System.out.println("Data de devolução: " + dataFormatada );
        }else {
            System.out.println("Data de devolução não definida.");
        }


    }
        private static void registerNotificationRequest(Utente u, Obra o) {
            System.out.println("Você será notificado quando " + o.getTitulo() + " estiver disponível.");
        }
        //devolver
        static void devolverObra() throws NoSuchUserException, NoSuchWorkException, WorkNotBorrowedByUserException {
            int userId = requestUserId();
            String workId = (String)requestWorkId();
            Requisicao toRemove = null;
            for (Requisicao r : requisicoes) {
                if (r.getUtente().getId() == userId && r.getObra().getId().equals(workId)) {
                    toRemove = r;
                    break;
                }
            }
            if (toRemove == null) {
                throw new WorkNotBorrowedByUserException("Obra não requisitada pelo utente.");
            }
            toRemove.getObra().setExemplaresdisponiveis(toRemove.getObra().getExemplaresdisponiveis()+1);
            requisicoes.remove(toRemove);
            System.out.println("Devolução bem sucedida.");
        }


        // Menu principal
        static void menu_principal() {
            int op;
            while (true){
                System.out.println("\n=======================================");
                System.out.println("======| (#) MEDIATECA GAMED (#) |======");
                System.out.println("=======================================");

                System.out.println("\n[1] Abrir");
                System.out.println("[2] Guardar");
                System.out.println("[3] Menu Gestão de Utentes ");
                System.out.println("[4] Menu Gestão de Obras");
                System.out.println("[5] Menu Gestão de Requisições");
                System.out.println("[0] Sair\n");
                System.out.print("Escolha uma opção: ");
                try {
                    op = teclado.nextInt();
                    teclado.nextLine(); // Limpar buffer
                    switch (op) {
                        case 1: carregarEstado();break;
                        case 2: guardarEstado();break;
                        case 3: menu_utentes();break;
                        case 4: menu_obra();break;
                        case 5: menu_requisicao();break;
                        case 0: return;
                        default: System.out.println("Opção invalida!!!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Erro: Digite um número!");
                    teclado.nextLine(); // Limpar buffer
                    op = -1;
                }
            }
        }
        // Menu de obras
        static void menu_obra() {
            int op;
            while (true){
                System.out.println("\n----------------------------");
                System.out.println("----Menu Gestão de Obras----");
                System.out.println("----------------------------");

                System.out.println("\n[1] Registrar Obras");
                System.out.println("[2] Mostrar Obras");
                System.out.println("[3] Pesquisar Obras");
                System.out.println("[4] Criar Categoria");
                System.out.println("[0] Sair\n");
                System.out.print("Escolha uma opção: ");

                try {
                    op = teclado.nextInt();
                    teclado.nextLine();
                    switch (op) {
                        case 1: menu_registrar();break;
                        case 2: menu_mostrar();break;
                        case 3: menu_pesquisar();break;
                        case 4: criarCategoria();break;
                        case 0: return;
                        default: System.out.println("Opção invalida!!!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Erro: Digite um número!");
                    teclado.nextLine();
                    op = -1;
                }

            }
        }
        // menu de requisição
        static void menu_requisicao() {
            int op;
            while (true){
                System.out.println("\n------------------------------");
                System.out.println("--Menu Gestão de Requisições--");
                System.out.println("------------------------------");

                System.out.println("\n[1] Requisitar");
                System.out.println("[2] Devolver Obra");
                System.out.println("[0] Sair\n");
                System.out.print("Escolha uma opção: ");
                try {
                    op = teclado.nextInt();
                    teclado.nextLine();
                    switch (op) {
                        case 1:
                            requisitarObra();
                            break;
                        case 2: devolverObra();break;
                        case 0: return;
                        default: System.out.println("Opção invalida!!!");
                    }
                }catch(RuleFailedException e) {
                    System.out.println("Aviso: " + e.getMessage());
                }catch (NoSuchUserException | NoSuchWorkException e) {
                    System.out.println("Erro: " + e.getMessage());
                }catch (WorkNotBorrowedByUserException e){
                    System.out.println("Aviso: " + e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Erro! Digite um número...");
                    teclado.nextLine();
                    op = -1;
                }catch (Exception e){
                    System.out.println("Erro inesperado: " + e.getMessage());
                }
            }


        }
        // Menu de utentes
        static void menu_utentes() {
            int op;
           while (true){
                System.out.println("\n----------------------------");
                System.out.println("---Menu Gestão de Utentes---");
                System.out.println("----------------------------");

                System.out.println("\n[1] Registrar utentes");
                System.out.println("[2] Mostrar Utentes");
                System.out.println("[0] Sair\n");
                System.out.print("Escolha uma opção: ");

                try {
                    op = teclado.nextInt();
                    teclado.nextLine();
                    switch (op) {
                        case 1: addUtente();break;
                        case 2: showUtentes();break;
                        case 0: return;
                        default: System.out.println("Opção invalida!!!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Erro: Digite um número!");
                    teclado.nextLine();
                    op = -1;
                }
            }


        }
        static void menu_registrar() {
            int op;
            while (true){
                System.out.println("\n----------------------------");
                System.out.println("----Menu Gestão de Obras----");
                System.out.println("----------------------------");
                System.out.println("\n[1] Registrar livro");
                System.out.println("[2] Registrar DVD");
                System.out.println("[0] Sair\n");
                System.out.print("Escolha uma opção: ");
                try {
                    op = teclado.nextInt();
                    teclado.nextLine();
                    switch (op) {
                        case 1: addLivros();break;
                        case 2: addDvd();break;
                        case 0: return;
                        default: System.out.println("Opção invalida!!!");
                    }
                }catch (InputMismatchException e){
                    System.out.println("Erro: Digite um número!");
                    teclado.nextLine();
                    op = -1;
                }

            }
        }
        static void menu_mostrar() {
            int op;
            while (true){
                System.out.println("\n----------------------------");
                System.out.println("----Menu Gestão de Obras----");
                System.out.println("----------------------------");
                System.out.println("\n[1] Mostrar livro");
                System.out.println("[2] Mostrar DVD");
                System.out.println("[3] Mostrar todas Obras");
                System.out.println("[0] Sair\n");
                System.out.print("Escolha uma opção: ");
                op = teclado.nextInt();
                teclado.nextLine();
                try {
                    switch (op) {
                        case 1: showLivro();break;
                        case 2: showDvd(); break;
                        case 3: showObras();break;
                        case 0: return;
                        default: System.out.println("Opção invalida!!!");
                    }
                }catch (Exception e){
                    System.out.println("Erro: "+ e.getMessage());
                }
            }
        }
        static void menu_pesquisar() {
            int op;
            while (true){
                System.out.println("\n----------------------------");
                System.out.println("----Menu Gestão de Obras----");
                System.out.println("----------------------------");
                System.out.println("\n[1] Pesquisar Obra");
                System.out.println("[0] Sair\n");
                System.out.print("Escolha uma opção: ");
                try {
                    op = teclado.nextInt();
                    teclado.nextLine();
                    switch (op) {
                        case 1:
                            efectuarPesquisa();
                            break;
                        case 0:
                            return;
                        default:
                            System.out.println("Opção invalida!!!");
                    }
                }catch (WorkNotBorrowedByUserException e) {
                    System.out.println("Erro: " + e.getMessage());
                }catch (NoSuchUserException e) {
                    System.out.println("Erro: " + e.getMessage());
                }catch (NoSuchWorkException e) {
                    System.out.println("Erro: "+ e.getMessage());
                }catch (RuleFailedException e){
                    System.out.println("Erro: "+ e.getMessage());
                }catch (InputMismatchException e ){
                    System.out.println("Erro: " + e.getMessage());
                    teclado.nextLine();
                    op = -1;
                }
            }
        }
        //metodos da categoria
        private static void criarCategoria() {
            try {
                System.out.println("\n=== CRIAR CATEGORIA ===\n");

                // 1. Pedir nome da nova categoria
                System.out.print("Digite o nome da nova categoria: ");
                String nomeCategoria = teclado.nextLine().trim();

                // 2. Validar entrada
                if (nomeCategoria.isEmpty()) {
                    System.out.println("Erro: O nome não pode estar vazio!");
                    return;
                }

                if (nomeCategoria.length() < 2) {
                    System.out.println("Erro: Nome muito curto! Mínimo 2 caracteres.");
                    return;
                }

                // 3. Verificar se categoria já existe
                for (Categoria cat : categorias) {
                    if (cat.getNome().equalsIgnoreCase(nomeCategoria)) {
                        System.out.println("Erro: Categoria '" + nomeCategoria + "' já existe!");
                        return;
                    }
                }

                // 4. Criar e adicionar nova categoria
                Categoria novaCategoria = new Categoria(nomeCategoria);
                categorias.add(novaCategoria);

                System.out.println("Categoria criada com sucesso: " + novaCategoria.getNome());

            } catch (Exception e) {
                System.out.println("Erro ao criar categoria: " + e.getMessage());
                e.printStackTrace(); // Para debug
            }
        }
        // ====================== VALIDAÇÕES ======================
        // Valida se uma string contém apenas letras e espaços
        private static boolean validarLetrasEspacos(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }

        String textoTrim = texto.trim();
        for (int i = 0; i < textoTrim.length(); i++) {
            char c = textoTrim.charAt(i);
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }
        // Valida email (versão simples)
        private static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String emailTrim = email.trim();
        // Deve ter @ e .
        if (!emailTrim.contains("@") || !emailTrim.contains(".")) {
            return false;
        }

        // @ não pode ser primeiro nem último caractere
        if (emailTrim.indexOf('@') == 0 || emailTrim.indexOf('@') == emailTrim.length() - 1) {
            return false;
        }

        // Deve ter pelo menos um caractere depois do ponto
        int pontoIndex = emailTrim.lastIndexOf('.');
        if (pontoIndex < emailTrim.indexOf('@') || pontoIndex == emailTrim.length() - 1) {
            return false;
        }

        return true;
    }
        // Valida se é um número inteiro positivo
        private static boolean validarInteiroPositivo(String texto) {
        try {
            int numero = Integer.parseInt(texto);
            return numero > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
        // Valida se é um número float positivo
        private static boolean validarFloatPositivo(String texto) {
        try {
            float numero = Float.parseFloat(texto);
            return numero > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
        // Valida ISBN (10 dígitos)
        private static boolean validarISBN(String isbn) {
        if (isbn == null || isbn.length() != 10) {
            return false;
        }

        for (int i = 0; i < isbn.length(); i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }
        }
        return true;
    }
        // Valida DNDAC (6 dígitos)
        private static boolean validarDNDAC(String dndac) {
        if (dndac == null || dndac.length() != 6) {
            return false;
        }

        for (int i = 0; i < dndac.length(); i++) {
            if (!Character.isDigit(dndac.charAt(i))) {
                return false;
            }
        }
        return true;
    }

        //Metodos auxiliares de leitura, saida e busca
        static private String requestUserName() {
            String nome;
            while (true) {
                System.out.print("Nome do utente: ");
                nome = teclado.nextLine().trim();

                if (nome.length() < 2) {
                    System.out.println("Erro: Nome muito curto! Mínimo 2 caracteres.");
                } else if (!validarLetrasEspacos(nome)) {
                    System.out.println("Erro: Nome deve conter apenas letras e espaços.");
                } else {
                    return nome;
                }
            }
        }
        static private String requestUserEMail() {
            String email;
            while (true) {
                System.out.print("E-mail do utente: ");
                email = teclado.nextLine().trim();

                if (!validarEmail(email)) {
                    System.out.println("Erro: Email inválido! Formato: exemplo@dominio.com");
                } else {
                    return email;
                }
            }
        }
        static private String requestTitle() {
            String titulo;
            while (true) {
                System.out.print("Título da obra: ");
                titulo = teclado.nextLine().trim();

                if (titulo.length() < 2) {
                    System.out.println("Erro: Título muito curto! Mínimo 2 caracteres.");
                } else {
                    return titulo;
                }
            }
        }
        static private float requestPrice() {
            String precoStr;
            while (true) {
                System.out.print("Preço da obra (ex: 100.00): ");
                precoStr = teclado.nextLine().trim();

                if (!validarFloatPositivo(precoStr)) {
                    System.out.println("Erro: Preço inválido! Deve ser um número positivo (ex: 100)");
                } else {
                    return Float.parseFloat(precoStr);
                }
            }
        }
        static private int requestNumCopies() {
            String numStr;
            while (true) {
                System.out.print("Número de exemplares: ");
                numStr = teclado.nextLine().trim();

                if (!validarInteiroPositivo(numStr)) {
                    System.out.println("Erro: Número inválido! Deve ser um número inteiro positivo.");
                } else {
                    return Integer.parseInt(numStr);
                }
            }
        }
    static private Categoria requestCategory() {
        // Inicializar categorias se estiver vazia
        if (categorias.isEmpty()) {
            inicializarCategorias();
        }

        while (true) {
            System.out.println("\nCategorias disponíveis:");
            for (int i = 0; i < categorias.size(); i++) {
                System.out.println((i + 1) + ". " + categorias.get(i).getNome());
            }

            System.out.println("\n[Digite 0 para criar uma nova categoria]");
            System.out.print("Escolha uma categoria (número ou nome): ");

            String input = teclado.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Erro: Digite algo!");
                continue;
            }

            // Opção para criar nova categoria
            if (input.equals("0")) {
                criarCategoria(); // Chama o método que corrigimos
                continue;
            }

            // Tentar como número
            if (input.matches("\\d+")) {
                int numero = Integer.parseInt(input);
                if (numero >= 1 && numero <= categorias.size()) {
                    return categorias.get(numero - 1);
                } else {
                    System.out.println("Erro: Número inválido! Escolha entre 1 e " + categorias.size());
                    continue;
                }
            }

            // É um nome de categoria
            // Verificar se já existe
            for (Categoria cat : categorias) {
                if (cat.getNome().equalsIgnoreCase(input)) {
                    return cat;
                }
            }

            // Perguntar se quer criar nova categoria
            System.out.print("Categoria '" + input + "' não existe. Deseja criá-la? (s/n): ");
            String resposta = teclado.nextLine().trim().toLowerCase();

            if (resposta.equals("s") || resposta.equals("sim")) {
                Categoria novaCat = new Categoria(input.toUpperCase());
                categorias.add(novaCat);
                System.out.println("Nova categoria criada: " + novaCat.getNome());
                return novaCat;
            } else {
                System.out.println("Operação cancelada. Escolha outra categoria.");
            }
        }
    }
        static String requestISBN() {
        String isbn;
        while (true) {
            System.out.print("ISBN (10 dígitos): ");
            isbn = teclado.nextLine().trim();

            if (!validarISBN(isbn)) {
                System.out.println("Erro: ISBN inválido! Deve ter exatamente 10 dígitos.");
            } else {
                return isbn;
            }
        }
    }
        static String requestDNDAC() {
        String dndac;
        while (true) {
            System.out.print("Registro DNDAC (6 dígitos): ");
            dndac = teclado.nextLine().trim();

            if (!validarDNDAC(dndac)) {
                System.out.println("Erro: DNDAC inválido! Deve ter exatamente 6 dígitos.");
            } else {
                return dndac;
            }
        }
    }
        static private int requestUserId() throws NoSuchUserException {
           while (true){
               try {
                   System.out.print("ID do utente: ");
                   int id = teclado.nextInt();
                   teclado.nextLine();
                   if (findUtenteById(id) == null) {
                       throw new NoSuchUserException("Utente não existe.");
                   }
                   return id;
               }catch (NoSuchUserException e){
                   System.out.println("Erro: "+ e.getMessage() + "Tente novamente.");
               }
           }
        }
        static private String requestWorkId() {
            while (true){
                try {
                    System.out.print("ID da obra: ");
                    String id = teclado.nextLine();
                    if (findObraById(id) == null) {
                        throw new NoSuchWorkException("Obra não existe.");
                    }
                    return id;
                }catch (NoSuchWorkException e){
                    System.out.println("Erro: " + e.getMessage() + "Tente novamente");
                }
            }
        }
        static private String requestSearchTerm() {
            System.out.print("Termo de pesquisa: ");
            return teclado.nextLine();
        }
        static private boolean requestReturnNotificationPreference() {
            System.out.print("Deseja ser notificado na devolução? (s/n): ");
            String resp = teclado.nextLine();
            return "s".equalsIgnoreCase(resp);
        }
        static private String openFile() {
            System.out.print("Nome do ficheiro a abrir: ");
            return teclado.nextLine();
        }
        static private String newSaveAs() {
            System.out.print("Nome do ficheiro para guardar: ");
            return teclado.nextLine();
        }
        static private String workRegistrationSuccessful() {
            return "Registo de obra bem sucedido.";
        }
        static private String workReturnDay() {
            return "Prazo de devolução: ";
        }
        static private String userRegistrationSuccessful() {
            return "Registo de utente bem sucedido.";
        }
        static private String workRequestSuccessful() {
            return "Requisicao de obra bem sucedido.";
        }
        // metodos de pesquisa
        static private Utente findUtenteById(int id) {
            for (Utente u : utentes) {
                if (u.getId() == id) return u;
            }
            return null;
        }
        static private Obra findObraById(String id) {
            for (Obra o : obras) {
                if (o.getId().equalsIgnoreCase(id)) return o;
            }
            return null;
        }

        // carregando e salvamento de dados
// Metodo para salvar tudo
        static void guardarEstado() {
            try {
                Serializador.salvarEstado(arquivoAtual, obras, utentes, categorias, requisicoes);
                System.out.println("Dados guardados com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao guardar: " + e.getMessage());
            }
        }
        // Metodo para carregar tudo
        static void carregarEstado() {
            System.out.print("Nome do arquivo (deixe vazio para " + arquivoAtual + "): ");
            String arquivo = teclado.nextLine();

            if (!arquivo.isEmpty()) {
                arquivoAtual = arquivo;
            }

            try {
                Object[] estado = Serializador.carregarEstado(arquivoAtual);

                // Restaurar as listas
                utentes = (ArrayList<Utente>) estado[0];
                obras = (ArrayList<Obra>) estado[1];
                categorias = (ArrayList<Categoria>) estado[2];
                requisicoes = (ArrayList<Requisicao>) estado[3];

        //===================Sincronizacao de ids ==========================
                //Sincronizar utentes
                int maxUtenteId = 0;
                for (Utente u: utentes){
                    if(u.getId() > maxUtenteId) maxUtenteId = u.getId();
                }
                Utente.actualizarContador(maxUtenteId);
                //Sincronizar Obras
                int maxObraId =0;
                for (Obra o: obras){
                    String parteNumerica = o.getId().split("/")[0];
                    int idActual = Integer.parseInt(parteNumerica);
                    if (idActual > maxObraId) maxObraId = idActual;
                }
                Obra.actualizarContador(maxObraId);
        //=================================================================

                System.out.println("Dados carregados com sucesso!");
                System.out.println("Obras: " + obras.size());
                System.out.println("Utentes: " + utentes.size());
                System.out.println("Requisicoes: " + requisicoes.size());

            } catch (FileNotFoundException e) {
                System.out.println("Arquivo não encontrado. Criando nova mediateca.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Erro ao carregar: " + e.getMessage());
            }
        }
    }

