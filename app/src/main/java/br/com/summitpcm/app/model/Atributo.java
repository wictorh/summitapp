package br.com.summitpcm.app.model;

public class Atributo {
    public int AtributoId ;
    public String Descricao ;

    public int getAtributoId() {
        return AtributoId;
    }

    public void setAtributoId(int atributoId) {
        AtributoId = atributoId;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String Tipo ;
}
