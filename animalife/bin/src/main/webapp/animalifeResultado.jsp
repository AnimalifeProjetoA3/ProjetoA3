<%@page import="java.util.ArrayList"%>
<%@ page import="controller.Controller"%>
<%@ page import="model.Dao"%>
<%@ page import="model.Usuario"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Styles/animalife.css">
        <link rel="shortcut icon" href="imagensProjeto/dog-track.png"
              type="image/x-icon">
        <title>Animalife</title>
    </head>

    <body>

        <%

            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

            Dao dao = new Dao();

            ArrayList<Usuario> animais = new ArrayList<>();

            animais = dao.dadosAnimais(request.getParameter("nomeAnimal"));


        %>


        <div class="cabecalho">
            <div class="dropdown">
                <img src="imagensProjeto/configuracao.png" alt="imagem" class="imgConfig">
                <div class="dropdown-content">
                    <a href="modificarSenha.jsp" class="options">Alterar senha</a>
                    <a href="alterarInformacoes.jsp" class="options">Alterar informações do animal</a>
                    <button class="options" onclick="confirmacao()">Excluir conta</button>
                    <a href="exit" class="options">Sair</a>
                </div>    
            </div>
            <p class="animalife">ANIMALIFE</p>
        </div>


        <div class="cabecalhoSecundario">
            <form action="selectName" method="get">
                <label class="labelInput">Procurar animal:</label>
                <input class="inputProcurar" type="text" placeholder="Digite aqui o nome do animal" name="nomeAnimal">
                <button class="botaoProcurar" type="submit">Procurar</button>
            </form>
        </div>



        <input type="hidden" id="identificador"
               value="<%=usuarioLogado.getId()%>">



        <%
            try {
                for (int i = 0; i < animais.size(); i++) {
        %>
        <div class="modalAnimal">

            <img src="<%=animais.get(i).getImagemAnimal()%>" class="imagemAnimal">

            <br>
            <p class="nomeAnimal"><%=animais.get(i).getNomeAnimal()%></p>
            <div class="descricao">
                <label class="dados">Nome: <%=animais.get(i).getNome()%></label>
                <br>
                <label class="dados">Telefone:<%=animais.get(i).getTelefone()%></label>
                <br>
                <label class="dados">Cidade: <%=animais.get(i).getCidade()%></label>
                <br>
                <label class="dados">Estado: <%=animais.get(i).getEstado()%></label>
                <br><br>
                <label class="labelDados">Dados do Animal:</label>
                <br>
                <textarea class="dadosAnimal" disabled><%=animais.get(i).getDescricaoAnimal()%></textarea> 
            </div>
        </div>


    <%
            }
        } catch (Exception e) {
            e.printStackTrace();
        }%>

    <p class="mensagemRetorno"><%=request.getAttribute("retorno")%></p>
    <p class="assinatura">@AnimaLife</p>


    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
                      
                       

                        function confirmacao() {
                            var usuario = document.getElementById("identificador").value;
                            Swal.fire({
                                title: 'Deseja excluir a conta? ',
                                showDenyButton: true,
                                showCancelButton: false,
                                confirmButtonText: "Sim",
                                denyButtonText: 'Não',
                                confirmButtonColor: "rgb(5,167,40)",
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    // Envia para o servelet 
                                    window.location.href = "delete?id=" + usuario;
                                }
                            })


                        }
    </script>
</body>

</html>