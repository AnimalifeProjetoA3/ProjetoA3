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
        <link rel="stylesheet" href="Styles/alterarInfo.css">
        <title>Alterar Informações</title>
    </head>
    <body>
        <%
            
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
            
        %>

        <input type="hidden" id="retorno"
               value="<%=request.getAttribute("retorno")%>">

        <input type="hidden" name="email"
               value="<%=usuarioLogado.getEmail()%>">


        <div class="container">
            <div class="cabecalho">
                <img class="imagemVoltar" src="imagensProjeto\voltar.png" alt="imagem">
                <a href="animalife.jsp">Voltar</a>
                <p class="animalife">ANIMALIFE</p>
            </div>
            <div class="formulario">
                <form action="updateAnimal">
                    <p class="titulo">Alterar informações do animal</p>
                    <input type="hidden" name="id"
                           value="<%=usuarioLogado.getId()%>">
                    <br><br>
                    <br><br>
                    <label for="especie" class="labelNome">Nome:</label>
                    <input type="text" name="nomeAnimal" value="<%=usuarioLogado.getNomeAnimal()%>">
                    <br><br>
                    <br><br>
                    <label for="descricao" class="labelDescricao">Descrição:</label>
                    <textarea name="descricaoAnimal" class="descricao" cols="30" rows="10" maxlength="77"><%=usuarioLogado.getDescricaoAnimal()%></textarea>
                    <button type="submit" class="confirmacao">Confirmar</button>
                </form>
            </div>
            <img class="obraprima" src="imagensProjeto\lapis.png" alt="text">
            <p class="assinatura">@AnimaLife</p>
        </div>

        <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>


        <script>
            var resultado = document.getElementById("retorno").value;
            if (resultado === "true") {
                Swal.fire({
                    icon: 'success',
                    title: 'Atualização realizada com sucesso!!',
                    confirmButtonColor: "rgb(5,167,40)"
                });
            }
        </script>
    </body>
</html>
