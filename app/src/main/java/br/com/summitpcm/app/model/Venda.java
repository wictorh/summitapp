package br.com.summitpcm.app.model;

import java.util.Date;
import java.util.List;

public class Venda {
    public int VendaId;
    public int CanalId;
    public double ValorTotal;
    public String Usuario;

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public int getVendaId() {
        return VendaId;
    }

    public void setVendaId(int vendaId) {
        VendaId = vendaId;
    }

    public int getCanalId() {
        return CanalId;
    }

    public void setCanalId(int canalId) {
        CanalId = canalId;
    }

    public double getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(double valorTotal) {
        ValorTotal = valorTotal;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public List<Produto> getProdutos() {
        return Produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        Produtos = produtos;
    }

    public String Descricao;
    public Boolean Status;
    public int UsuarioId;
    public String Data;
    public List<Produto> Produtos;
}
