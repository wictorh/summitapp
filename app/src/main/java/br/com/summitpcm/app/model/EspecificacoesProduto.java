package br.com.summitpcm.app.model;


public class EspecificacoesProduto {
    public int AtributoProdutoId;

    public int getProdutoId() {
        return ProdutoId;
    }

    public void setProdutoId(int produtoId) {
        ProdutoId = produtoId;
    }

    public int getAtributoId() {
        return AtributoId;
    }

    public void setAtributoId(int atributoId) {
        AtributoId = atributoId;
    }

    public int getAtributoProdutoId() {
        return AtributoProdutoId;
    }

    public void setAtributoProdutoId(int atributoProdutoId) {
        AtributoProdutoId = atributoProdutoId;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public int ProdutoId;
    public int AtributoId;

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String Descricao;
    public String Valor;
    public String Tipo;
}
