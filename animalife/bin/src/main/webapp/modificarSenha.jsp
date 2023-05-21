<%@ page import="controller.Controller"%>
<%@ page import="model.Dao"%>
<%@ page import="model.Usuario"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="ISO-8859-1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Styles/atualizarSenha.css">
        <link rel="shortcut icon" href="imagensProjeto/dog-track.png"
              type="image/x-icon">
        <title>Atualizar senha</title>
    </head>
    <body>

        <%

            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        %>

        <input type="hidden" id="retorno"
               value="<%=request.getAttribute("retorno")%>">

        <div class="container">
            <div class="cabecalho">
                <img class="imagemVoltar" src="imagensProjeto\voltar.png" alt="imagem">
                <a href="animalife.jsp">Voltar</a>
                <p class="animalife">ANIMALIFE</p>
            </div>
            <div class="formulario">
                <form action="alterarSenha" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="email"
                           value="<%=usuarioLogado.getEmail()%>">
                    <p class="titulo">Alterar senha</p>
                    <label for="email">Senha atual:</label>
                    <input type="password" name="senhaAtual">
                    <br><br>
                    <br><br>
                    <label for="novasenha">Nova senha:</label>
                    <input type="password" name="novaSenha">
                    <button  type="submit" class="confirmacao">Confirmar</button>
                </form>
            </div>
            <img class="obraprima" src="imagensProjeto\recuperar.png" alt="text">
            <p class="assinatura">@AnimaLife</p>
        </div>

        <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            var resultado = document.getElementById("retorno").value;
            if (resultado === "true") {
                Swal.fire({
                    icon: 'success',
                    title: 'Senha atualizada com sucesso!!',
                    confirmButtonColor: "rgb(5,167,40)"
                });
            }

            if (resultado === "false") {
                Swal.fire({
                    icon: 'error',
                    title: 'Senha Inválida',
                    confirmButtonColor: "rgb(5,167,40)"
                });
            }
        </script>
    </body>
</html>