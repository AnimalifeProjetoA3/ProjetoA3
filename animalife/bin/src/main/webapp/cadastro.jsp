<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="ISO-8859-1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Styles/cadastro.css">
        <link rel="shortcut icon" href="imagensProjeto/dog-track.png"
              type="image/x-icon">
        <title>Cadastro</title>
    </head>
    <body>
        <input type="hidden" id="mensagem"
               value="<%=request.getAttribute("mensagem")%>">

        <div class="container">
            <div class="cabecalho">
                <img class="imagemVoltar" src="imagensProjeto/voltar.png" alt="imagem">
                <a href="index.jsp">Voltar</a>
                <p class="animalife">ANIMALIFE</p>
            </div>
            <div class="formulario">
                <p class="titulo">Criar conta</p>
                <form action="create" method="post" enctype="multipart/form-data" >
                    <label for="nome">Nome:</label>
                    <input type="text" name="nome" class="inputNome" required>
                    <br><br>
                    <label for="email">Email:</label>
                    <input type="email" name="email" class="inputEmail" required>
                    <br><br>
                    <label for="senha">Senha:</label>
                    <input type="password" name="senha" class="inputSenha" required>
                    <br><br>
                    <label for="telefone" class="phone">Telefone:</label>
                    <input type="tel" name="telefone" class="inputTel" required>
                    <br><br>
                    <label for="cidade">Cidade:</label>
                    <input type="text" name="cidade" required>
                    <br><br>
                    <label for="estado">Estado:</label>
                    <input type="text" name="estado"  required>
                    <br><br>
                    <label for="nomeAnimal">Animal:</label>
                    <input type="text" name="nomeAnimal" required>
                    <br><br>
                    <label for="descricaoAnimal">Dados do Animal:</label>
                    <br><br>
                    <textarea name="descricaoAnimal" cols="30" rows="10" maxlength="77" required></textarea>
                    <br><br>
                    <label for="imagem">Imagem:</label>
                    <input type="file" name="imagem" class="inputImagem" required>
                    <br><br>
                    <button class="botaoConfirmar" type="submit">Confirmar</button>
                </form>
            </div>
            <img class="obraprima" src="imagensProjeto/ImagemCriar.png" alt="text">
            <p class="assinatura">@AnimaLife</p>
        </div>


        <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>


        <script>
            var mensagem = document.getElementById("mensagem").value;

            console.log(mensagem);

            if (mensagem === "sucesso") {
                Swal.fire({
                    icon: 'success',
                    title: 'Conta Criada Com Sucesso!!',
                    text: 'Parabéns, sua conta foi criada e agora você pode voltar para a tela de login!',
                    confirmButtonColor: "rgb(5,167,40)"
                });
            }

        </script>
    </body>
</html>