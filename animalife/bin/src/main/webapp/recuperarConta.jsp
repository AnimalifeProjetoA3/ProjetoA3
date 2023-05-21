<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="ISO-8859-1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Esqueci Minha Senha</title>
        <link rel="stylesheet" href="Styles/alterarSenha.css">
        <link rel="shortcut icon" href="imagensProjeto/dog-track.png"
              type="image/x-icon">
    </head>
    <body>
        <input type="hidden" id="email"
               value="<%=request.getAttribute("email")%>">



        <div class="container">
            <div class="cabecalho">
                <img class="imagemVoltar" src="imagensProjeto\voltar.png" alt="imagem">
                <a href="index.jsp">Voltar</a>
                <p class="animalife">ANIMALIFE</p>
            </div>
            <div class="formulario">
                <form action="updateSenha" name="frmUpdate" method="post">
                    <p class="titulo">Recuperação de senha</p>
                    <label for="email">E-mail:</label>
                    <input type="email" name="email">
                    <button type="submit" class="confirmacao">Confirmar e-mail</button>
                </form>
            </div>
            <img class="obraprima" src="imagensProjeto/recuperar.png" alt="text">
            <p class="assinatura">@AnimaLife</p>
        </div>   


        <!--Utilização da API sweetalert2 para criação de Alert personalizado-->
        <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>


        <script>
            var email = document.getElementById("email").value;

            console.log(email);

            if (email === "enviado") {
                Swal.fire({
                    icon: 'success',
                    title: 'E-mail enviado!!',
                    text: 'Um e-mail contendo uma senha provissória foi enviado para você. Agora, basta seguir o passo a passo para alterar sua senha!',
                    confirmButtonColor: "rgb(5,167,40)"
                });
            } if(email === "invalido"){
                Swal.fire({
                    icon: 'error',
                    title: 'E-mail não cadastrado!!',
                    confirmButtonColor: "rgb(5,167,40)"
                });
            }

        </script>


    </body>
</html>