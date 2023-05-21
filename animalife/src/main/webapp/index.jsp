<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="ISO-8859-1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>AnimaLife</title>
        <link rel="stylesheet" href="Styles/index.css">
        <link rel="shortcut icon" href="imagensProjeto/dog-track.png"
              type="image/x-icon">
    </head>
    <body>
        <input type="hidden" id="mensagem"
               value="<%=request.getAttribute("mensagem")%>">

        <input type="hidden" id="retorno"
               value="<%=request.getAttribute("retorno")%>">

        <div class="container">
            <div class="fundo"></div>
            <img src="imagensProjeto/life.jpg" alt="imagem" id="imgPrincial">
            <form class="login" action="select" method="post"
                  enctype="multipart/form-data" name="formLogin">
                <h1 class="titleLogin">Login</h1>
                <label class="labelEmail" for="email">E-mail:</label> <input
                    class="inputEmail" name="email" type="email"> <br> <br>
                <label class="labelSenha" for="senha">Senha:</label> <input
                    class="inputSenha" name="senha" type="password"><br> <br>
                <p class="esqueciSenha">
                    <a href="recuperarConta.jsp" class="linkLogin">Esqueci minha senha.</a>
                </p>
                <button class="entrar" type="submit">Entrar</button>
                <p class="criarConta">
                    Ainda não tem uma conta? <a href="cadastro.jsp" class="linkLogin"> Cadastre-se.</a>
                </p>
            </form>
            <div class="logo">
                <p class="animalife">ANIMALIFE</p>
            </div>
            <p class="titulo">Ajudando a procriar animais em extinção!</p>
        </div>

        <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script>
            var erro = document.getElementById("mensagem").value;
            console.log(mensagem);
            if (erro === "erro") {
                Swal.fire({
                    icon: 'error',
                    title: 'Email e/ou Senha Inválidos!',
                    confirmButtonColor: "rgb(5,167,40)"
                });
            }

            var retorno = document.getElementById("retorno").value;
            if (retorno === "true") {
                Swal.fire({
                    title: 'Conta excluída com sucesso.',
                    confirmButtonColor: "rgb(5,167,40)"
                });
            }
        </script>

    </body>
</html>