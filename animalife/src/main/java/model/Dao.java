package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



public class Dao {

    public Dao() {

    }

    // Parâmetros de conexão
    // Adaptar as configurações do seu banco
    private String driver = "com.mysql.cj.jdbc.Driver";

    private String url = "jdbc:mysql://localhost:3306/animalife?useTimezone=true&serverTimezone=UTC";

    private static final String USERNAME = "root";

    private static final String SENHA = "root@1012";

    // Método de conexão
    public Connection conectar() {
        Connection con = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, USERNAME, SENHA);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void testaConexao() {
        try {
            Connection con = conectar();
            System.out.print(con);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Create
    public void adicionar(Usuario usuario, String relativoPath) {
        String create = "insert into usuario (nome, email, senha, telefone, cidade, estado, nome_animal, descricao_animal, imagem_animal) values(?,?,MD5(?),?,?,?,?,?,?)";

        try {
            // Abrir a conexão
            Connection con = conectar();

            // Preparar a query para execução no banco
            PreparedStatement prepared = con.prepareStatement(create);

            // Inserir o conteúdo das variáveis do objeto Usuario
            prepared.setString(1, usuario.getNome());
            prepared.setString(2, usuario.getEmail());
            prepared.setString(3, usuario.getSenha());
            prepared.setString(4, usuario.getTelefone());
            prepared.setString(5, usuario.getCidade());
            prepared.setString(6, usuario.getEstado());
            prepared.setString(7, usuario.getNomeAnimal());
            prepared.setString(8, usuario.getDescricaoAnimal());
            prepared.setString(9, relativoPath);

            prepared.executeUpdate();

            // Encerrar a conexão com o banco
            con.close();
            prepared.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // SELECT login e Senha
    public boolean consultaLogin(Usuario usuario) {
        Usuario recebimento = new Usuario();
        recebimento.setEmail("");
        recebimento.setSenha("");
        String select = "SELECT * FROM usuario where email = ? && senha = MD5(?)";

        try {
            Connection con = conectar();
            PreparedStatement pst = con.prepareStatement(select);
            pst.setString(1, usuario.getEmail());
            pst.setString(2, usuario.getSenha());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                recebimento.setEmail(rs.getString("email"));
                recebimento.setSenha(rs.getString("senha"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (recebimento.getEmail().equals("") && recebimento.getSenha().equals("")) {
            return false;
        }
        return true;
    }

    // UPDATE da senha por meio da senha temporaria
    public void atualizarSenha(String email, String senhaTemporaria) {
        String update = "update usuario set senha = MD5(?) where email = ?";
        try {
            Connection con = conectar();
            PreparedStatement pst = con.prepareStatement(update);
            pst.setString(1, senhaTemporaria);
            pst.setString(2, email);
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SELECT por email
    public void consultarDados(String emailUsuario, Usuario usuario) {
        String select = "SELECT * FROM usuario where email = ?";
        try {
            Connection con = conectar();
            PreparedStatement pst = con.prepareStatement(select);
            pst.setString(1, emailUsuario);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                usuario.setId(rs.getString(1));
                usuario.setNome(rs.getString(2));
                usuario.setEmail(rs.getString(3));
                usuario.setSenha(rs.getString(4));
                usuario.setTelefone(rs.getString(5));
                usuario.setCidade(rs.getString(6));
                usuario.setEstado(rs.getString(7));
                usuario.setNomeAnimal(rs.getString(8));
                usuario.setDescricaoAnimal(rs.getString(9));
                usuario.setImagemAnimal(rs.getString(10));

            }
            con.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Atualização da Senha solicitada pelo usuário
    public boolean atualizarSenha(String senhaAtual, String novaSenha, String email) {
        //Verificação da senha atual

        Usuario recebimento = new Usuario();
        recebimento.setEmail("");
        recebimento.setSenha("");

        String select = "SELECT * FROM usuario where email = ? && senha = MD5(?)";

        try {
            Connection con = conectar();
            PreparedStatement pst = con.prepareStatement(select);
            pst.setString(1, email);
            pst.setString(2, senhaAtual);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                recebimento.setEmail(rs.getString("email"));
                recebimento.setSenha(rs.getString("senha"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (recebimento.getEmail().equals("") && recebimento.getSenha().equals("")) {
            return false;
        } 
            String update = "update usuario set senha = MD5(?) where email = ?";
            try {
                Connection con = conectar();
                PreparedStatement pst = con.prepareStatement(update);
                pst.setString(1, novaSenha);
                pst.setString(2, email);
                pst.executeUpdate();
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        
        return true;
    }

    // SELECT Animal
    public ArrayList<Usuario> dadosAnimais(String animal) {

        //Faz o select por nomes relacionados com a String, análogo ao método contains
        String select = "select nome, telefone, cidade, estado,nome_animal ,descricao_animal, imagem_animal from usuario where nome_animal like '%" + animal + "%'";

        ArrayList<Usuario> animais = new ArrayList<>();
        try {
            Connection con = conectar();
            PreparedStatement pst = con.prepareStatement(select);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String nome = (rs.getString(1));
                String telefone = (rs.getString(2));
                String cidade = (rs.getString(3));
                String estado = (rs.getString(4));
                String nomeAnimal = (rs.getString(5));
                String descricaoAnimal = (rs.getString(6));
                String imagemAnimal = (rs.getString(7));
                animais.add(new Usuario(nome, telefone, cidade, estado, nomeAnimal, descricaoAnimal, imagemAnimal));
            }
            con.close();
            return animais;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    
    //update das informações do animal
    public void updateAnimal(String id, String nomeAnimal, String descricao){
        String read = "update usuario set nome_animal = ?, descricao_animal = ? where id = ?";
          try {
            Connection con = conectar();
            PreparedStatement pst = con.prepareStatement(read);
            pst.setString(1, nomeAnimal);
            pst.setString(2, descricao);
            pst.setString(3, id);
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
    
    public void deletarConta(String id){
           String delete = "delete from usuario where id = ?";
        try {
            Connection con = conectar();
            PreparedStatement pst = con.prepareStatement(delete);
            pst.setString(1, id);
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
