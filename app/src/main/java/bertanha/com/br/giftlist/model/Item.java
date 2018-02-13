package bertanha.com.br.giftlist.model;

import java.io.Serializable;

/**
 * Created by berta on 2/4/2018.
 */

public class Item implements Serializable{
    private String codigo;
    private String descricao;
    private Double valor;
    private String link;
    private Boolean ativo;

    public Item() {

    }

    public Item(String descricao) {
        this.descricao = descricao;
    }

    public Item(String descricao, String link) {
        this.descricao = descricao;
        this.link = link;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
