package br.com.summitpcm.app.model;

public class Produto {
    public int ProdutoId ;

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getProdutoId() {
        return ProdutoId;
    }

    public void setProdutoId(int produtoId) {
        ProdutoId = produtoId;
    }

    public String Nome ;
    public String Image ;
}
