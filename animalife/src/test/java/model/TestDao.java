package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// import org.junit.FixMethodOrder;
// import org.junit.runners.MethodSorters;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestDao {

    public String idAtual() throws SQLException {
        Dao dao = new Dao();
        Connection con = dao.conectar();
        PreparedStatement pst = con.prepareStatement(
                "SELECT * FROM  animalife.usuario where id = (SELECT MAX(ID) FROM animalife.usuario) ");

        ResultSet rs = pst.executeQuery();
        rs.next();
        return (rs.getString("id"));

    }

    @Test
    @Order(1)
    @DisplayName("Teste de cadastrar um usuário")
    public void test1() throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setNomeAnimal("Hamster");
        usuario.setDescricaoAnimal("Altura: 5 cm\nIdade: 2 anos");
        usuario.setEmail("testeAdicionar@mail.com");
        usuario.setCidade("São Paulo");
        usuario.setEstado("SP");
        usuario.setSenha("senha");
        usuario.setTelefone("(11) 9999-9999");

        String relativoPath = "Imagens" + File.separator + "hamster-bochecha.jpg";
        Dao dao = new Dao();

        dao.adicionar(usuario, relativoPath);

        Connection con = dao.conectar();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM USUARIO WHERE EMAIL = ? ");
        pst.setString(1, usuario.getEmail());

        ResultSet rs = pst.executeQuery();

        assertTrue(rs.next()); // Se localizou no banco, então retorna verdadeiro

    }

    @Test
    @Order(2)
    @DisplayName("Teste de consultar e-mail e senha")
    public void test2() {
        /* Utilizando o cadastro criado acima */
        Usuario usuario = new Usuario();
        usuario.setEmail("testeAdicionar@mail.com");
        usuario.setSenha("senha");

        Dao dao = new Dao();
        boolean resultadoEsperado = true; // Precisa retornar verdadeiro para o usuário conseguir acessar o sistema
        boolean resultadoReal = dao.consultaLogin(usuario);
        assertEquals(resultadoEsperado, resultadoReal);

    }

    @Test
    @Order(3)
    @DisplayName("1º Teste de alterar a senha")
    public void test3() throws SQLException {
        /* Utilizando o cadastro criado acima */
        String email = "testeAdicionar@mail.com";
        String senhaTemp = "senhaTemp02";
        Dao dao = new Dao();
        dao.atualizarSenha(email, senhaTemp);

        Connection con = dao.conectar();
        PreparedStatement pst = con
                .prepareStatement("SELECT * FROM animalife.usuario WHERE email = ?  AND senha = MD5(?)  ");
        pst.setString(1, email);
        pst.setString(2, senhaTemp);

        ResultSet rs = pst.executeQuery();

        assertTrue(rs.next());
    }

    @Test
    @Order(4)
    @DisplayName("Teste de consultar o usuário por meio do e-mail")
    public void test4() {
        String emailUsuario = "testeAdicionar@mail.com";
        Usuario usuario = new Usuario();
        Dao dao = new Dao();
        dao.consultarDados(emailUsuario, usuario);

        assertEquals(emailUsuario, usuario.getEmail());

    }

    @Test
    @Order(5)
    @DisplayName("2º Teste de alterar a senha ")
    public void test5() {
        String senhaAtual = "senhaTemp02";
        String novaSenha = "senha";
        String email = "testeAdicionar@mail.com";
        Dao dao = new Dao();
        boolean resultadoEsperado = true;
        boolean resultadoReal = dao.atualizarSenha(senhaAtual, novaSenha, email);
        assertEquals(resultadoEsperado, resultadoReal);

    }

    @Test
    @Order(6)
    @DisplayName("Teste de consultar o animal cadastrado")
    public void test6() {
        // Busca com base no animal cadastrado acima.
        String animal = "Hamster";
        Dao dao = new Dao();

        ArrayList<Usuario> animais = new ArrayList<>();
        animais = dao.dadosAnimais(animal);

        /*
         * Como a busca é feita no banco por meio do LIKE, caso exista algo relacionado
         * ou próximo do nome passado
         * será retornado verdadeiro
         */
        assertTrue(animais.get(0).getNomeAnimal().contains(animal));

    }

    @Test
    @Order(7)
    @DisplayName("Teste de atualizar os dados do animal")
    public void test7() throws SQLException {
        TestDao td = new TestDao();
        String id = td.idAtual(); // ID é auto increment, sendo este do cadastro criado acima.
        String nomeAnimal = "Hamster Roborovski";
        String descricao = "O hamster mais veloz existente!";
        Dao dao = new Dao();
        dao.updateAnimal(id, nomeAnimal, descricao);

        Connection con = dao.conectar();
        PreparedStatement pst = con
                .prepareStatement("SELECT * FROM animalife.usuario WHERE nome_animal = ?  AND descricao_animal = ?  ");
        pst.setString(1, nomeAnimal);
        pst.setString(2, descricao);

        ResultSet rs = pst.executeQuery();

        assertTrue(rs.next());

    }

    @Test
    @Order(8)
    @DisplayName("Teste de deletar o cadastro")
    public void test8() throws SQLException {
        TestDao td = new TestDao();
        String id = td.idAtual();
        Dao dao = new Dao();
        dao.deletarConta(id);

        Connection con = dao.conectar();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM animalife.usuario WHERE id = ?   ");
        pst.setString(1, id);

        ResultSet rs = pst.executeQuery();

        assertTrue(!(rs.next()));

    }

}
