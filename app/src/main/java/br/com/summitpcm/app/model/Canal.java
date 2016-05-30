package br.com.summitpcm.app.model;

import java.util.ArrayList;

public class Canal {

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    private int Id;
    private String Nome, Descricao,Site, thumbnailUrl;
    private Boolean Status;

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public Canal() {
    }

    public Canal(String name, String thumbnailUrl, String descricao, String site,
                 Boolean status, int id) {
        this.Id = id;
        this.Nome = name;
        this.thumbnailUrl = thumbnailUrl;
        this.Descricao = descricao;
        this.Site = site;
        this.Status = status;
    }


}