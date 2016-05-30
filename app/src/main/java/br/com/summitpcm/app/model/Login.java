package br.com.summitpcm.app.model;

/**
 * Created by wictor.huggo on 29/05/2016.
 */
public class Login
{
    public Boolean isLogged;
    public String mensagem;

    public Boolean getLogged() {
        return isLogged;
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario usuario;
}
