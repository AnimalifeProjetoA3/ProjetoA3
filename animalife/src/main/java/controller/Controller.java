package controller;

import java.io.IOException;
//import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Dao;
import model.Usuario;

//Mapeamento dos Actions 
@WebServlet(urlPatterns = { "/Controller", "/main", "/create", "/select", "/updateSenha", "/exit", "/selectName",
        "/delete", "/updateAnimal", "/alterarSenha" })

// Configurações de Files
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 50)

public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    Dao dao = new Dao();
    Usuario usuario = new Usuario();

    public Controller() {
        super();
    }

    // Nome da pasta que será salvo as imagens enviadas.
    public static final String UPLOAD_DIR = "Imagens";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        // Mostra no terminal a requisição feita
        // System.out.println(action);

        switch (action) {
            case "/create":
                doPost(request, response);
                break;
            case "/select":
                doPost(request, response);
                break;
            case "/updateSenha":
                doPost(request, response);
                break;
            case "/exit":
                doPost(request, response);
                break;
            case "/selectName":
                selecionarAnimal(request, response);
                break;
            case "/alterarSenha":
                doPost(request, response);
                break;
            case "/updateAnimal":
                alterarInfoAnimal(request, response);
                break;
            case "/delete":
                deletarCadastro(request, response);
                break;
        }

        /*
         * Teste de conexão com o banco de dados
         * dao.testaConexao();
         */
    }

    // Método Post
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getServletPath();

        switch (action) {
            case "/create":
                create(request, response);
                break;
            case "/select":
                select(request, response);
                break;
            case "/updateSenha":
                modificarSenha(request, response);
                break;
            case "/exit":
                deslogar(request, response);
                break;
            case "/alterarSenha":
                atualizarSenha(request, response);
                break;

        }

    }

    // Cadastrar usuario
    protected void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        // PrintWriter out = response.getWriter();

        // Resgatando os dados digitados no formulário
        usuario.setNome(request.getParameter("nome"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setSenha(request.getParameter("senha"));
        usuario.setTelefone(request.getParameter("telefone"));
        usuario.setCidade(request.getParameter("cidade"));
        usuario.setEstado(request.getParameter("estado"));
        usuario.setNomeAnimal(request.getParameter("nomeAnimal"));
        usuario.setDescricaoAnimal(request.getParameter("descricaoAnimal"));
        String img = request.getParameter("imagem");
        // Salva o caminho da imagem
        // Part part = request.getPart("imagem");
        // String filename = extractFileName(part);

        // Caminho absoluto da pasta Imagens
        // IMPORTANTE
        // ADAPTAR PARA SUA MAQUINA
        // String salvarPath = "\\src\\main\\resources\\imagens" + File.separator +
        // filename;
        // File file = new File(salvarPath);

        // part.write(salvarPath + File.separator);

        // System.out.println(salvarPath);
        // String sRootPath = new File(salvarPath).getAbsolutePath();
        // System.out.println(sRootPath);

        // part.write(salvarPath + File.separator);
        // // File fileSaveDir = new File(salvarPath);

        // //String dbFileName = UPLOAD_DIR + File.separator + filename;
        // part.write(salvarPath + File.separator);

        // String relativoPath = "Imagens/" + filename;

        // Método para cadastrar o usuário e o nome da imagem no banco de dados
        dao.adicionar(usuario, img);

        // Cria a variável mensagem que recebe "sucesso" como atributo
        request.setAttribute("mensagem", "sucesso");

        RequestDispatcher rd = request.getRequestDispatcher("cadastro.jsp");
        rd.forward(request, response);
    }

    // Select do Email e Senha
    protected void select(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        usuario.setEmail(request.getParameter("email"));
        usuario.setSenha(request.getParameter("senha"));

        boolean consulta = dao.consultaLogin(usuario);

        if (consulta) {

            // Insere todos os dados no objeto Usuário por meio de um SELECT no login
            dao.consultarDados(request.getParameter("email"), usuario);

            // Cria uma seção para o usuário logado
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario);

            // Envia o objeto Usuario para a pagina animalife.js
            RequestDispatcher rd = request.getRequestDispatcher("animalife.jsp");
            rd.forward(request, response);

        } else {

            // Teste de verificação do login
            // System.out.println("Email e/ou Senha Incorretos");
            // Envia para o index.jsp uma variável
            request.setAttribute("mensagem", "erro");

            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
    }

    // Update da senha por meio da classe Random e o envio de e-mail com a senha
    // temporária
    protected void modificarSenha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        usuario.setId("");
        usuario.setNome("");
        usuario.setCidade("");
        usuario.setTelefone("");
        usuario.setEmail(request.getParameter("email"));
        usuario.setDescricaoAnimal("");
        usuario.setImagemAnimal("");

        // System.out.println(request.getParameter("email"));
        dao.consultarDados(request.getParameter("email"), usuario);

        if (!(usuario.getId().isEmpty())) {

            // Geração da senha
            String senha = "";
            String letrasUppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String letras = "abcdefghijklmnopqrstuvwxyz";
            String numeros = "123456789";
            String caractereEspecial = "@#$&";
            String combinacao = letrasUppercase + letras + numeros + caractereEspecial;

            int tamanho = 7;
            char[] senhaTemp = new char[tamanho];
            Random random = new Random();

            for (int i = 0; i < tamanho; i++) {
                senhaTemp[i] = combinacao.charAt(random.nextInt(combinacao.length()));
                senha += senhaTemp[i];
            }

            // Recebe o e-mail digitado e a senha criada para realizar o UPDATE no banco
            dao.atualizarSenha(request.getParameter("email"), senha);

            Properties props = new Properties();

            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");

            Session s = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // Email e senha do Remetente
                    return new PasswordAuthentication("animalifea3@gmail.com", "pxueduqrwzdzwcjj");
                }
            });

            try {
                MimeMessage mensagem = new MimeMessage(s);
                mensagem.setFrom(new InternetAddress("animalifea3@gmail.com"));

                mensagem.addRecipient(Message.RecipientType.TO, new InternetAddress(request.getParameter("email")));

                mensagem.setSubject("Solicitação de nova senha");

                mensagem.setContent("<html>Olá, " + "" + usuario.getNome() + "," + "<br><br>"
                        + "Conforme sua solicitação, segue abaixo sua nova senha. Esta senha é temporária e precisa ser alterada.<br><br>"
                        + "Para alterá-la, entre com a nova senha, siga para a engrenagem no canto superior esquerdo da página principal e selecione a opção de alterar senha.<br>"
                        + "Nova Senha: " + "" + senha + "<br><br>" + "\n"
                        + "Atenciosamente,<br>" + "Animalife.</html>", "text/html;charset=utf-8");

                Transport.send(mensagem);

                usuario.setNome("");

                request.setAttribute("email", "enviado");

                RequestDispatcher rd = request.getRequestDispatcher("recuperarConta.jsp");
                rd.forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            request.setAttribute("email", "invalido");

            RequestDispatcher rd = request.getRequestDispatcher("recuperarConta.jsp");
            rd.forward(request, response);
        }

    }

    protected void deslogar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        usuario.setId("");
        usuario.setNome("");
        usuario.setCidade("");
        usuario.setTelefone("");
        usuario.setEmail("");
        usuario.setDescricaoAnimal("");
        usuario.setImagemAnimal("");

        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("index.jsp");
    }

    protected void selecionarAnimal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nomeAnimal = request.getParameter("nomeAnimal");
        // System.out.println(nomeAnimal);

        ArrayList<Usuario> animais;
        animais = dao.dadosAnimais(nomeAnimal);

        if (animais.isEmpty()) {
            request.setAttribute("retorno", "Animal não localizado.");

            RequestDispatcher rd = request.getRequestDispatcher("animalifeResultado.jsp");
            rd.forward(request, response);
        } else {
            request.setAttribute("retorno", "");
            request.setAttribute("dados", animais);

            RequestDispatcher rd = request.getRequestDispatcher("animalifeResultado.jsp");
            rd.forward(request, response);
        }

    }

    protected void alterarInfoAnimal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String nomeAnimal = request.getParameter("nomeAnimal");
        String descricao = request.getParameter("descricaoAnimal");
        // String email = request.getParameter("email");

        dao.updateAnimal(id, nomeAnimal, descricao);

        usuario.setNomeAnimal(nomeAnimal);
        usuario.setDescricaoAnimal(descricao);

        request.setAttribute("retorno", "true");

        RequestDispatcher rd = request.getRequestDispatcher("alterarInformacoes.jsp");
        rd.forward(request, response);

    }

    protected void atualizarSenha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean resultado = false;

        String email = request.getParameter("email");
        String senhaAtual = request.getParameter("senhaAtual");
        String novaSenha = request.getParameter("novaSenha");

        resultado = dao.atualizarSenha(senhaAtual, novaSenha, email);

        if (resultado) {
            request.setAttribute("retorno", "true");

            RequestDispatcher rd = request.getRequestDispatcher("modificarSenha.jsp");
            rd.forward(request, response);
        } else {

            request.setAttribute("retorno", "false");

            RequestDispatcher rd = request.getRequestDispatcher("modificarSenha.jsp");
            rd.forward(request, response);
        }

    }

    protected void deletarCadastro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        usuario.setId(id);
        dao.deletarConta(id);

        request.setAttribute("retorno", "true");
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

}
