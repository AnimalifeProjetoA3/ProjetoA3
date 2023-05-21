package model;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String cidade;
    private String estado;
    private String nomeAnimal;
    private String descricaoAnimal;
    private String imagemAnimal;

    public Usuario(String id, String nome, String email, String senha, String telefone, String cidade, String estado,
            String nomeAnimal, String descricaoAnimal, String imagemAnimal) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.cidade = cidade;
        this.estado = estado;
        this.nomeAnimal = nomeAnimal;
        this.descricaoAnimal = descricaoAnimal;
        this.imagemAnimal = imagemAnimal;
    }

    public Usuario(String nome, String telefone, String cidade, String estado,
            String nomeAnimal, String descricaoAnimal, String imagemAnimal) {

        this.nome = nome;
        this.telefone = telefone;
        this.cidade = cidade;
        this.estado = estado;
        this.nomeAnimal = nomeAnimal;
        this.descricaoAnimal = descricaoAnimal;
        this.imagemAnimal = imagemAnimal;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNomeAnimal() {
        return nomeAnimal;
    }

    public void setNomeAnimal(String nomeAnimal) {
        this.nomeAnimal = nomeAnimal;
    }

    public String getDescricaoAnimal() {
        return descricaoAnimal;
    }

    public void setDescricaoAnimal(String descricaoAnimal) {
        this.descricaoAnimal = descricaoAnimal;
    }

    public String getImagemAnimal() {
        return imagemAnimal;
    }

    public void setImagemAnimal(String imagemAnimal) {
        this.imagemAnimal = imagemAnimal;
    }
    
    
    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", telefone="
                + telefone + ", cidade=" + cidade + ", estado=" + estado + ", nomeAnimal=" + nomeAnimal
                + ", descricaoAnimal=" + descricaoAnimal + ", imagemAnimal=" + imagemAnimal + "]";
    }
}
