package bertanha.com.br.giftlist.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by berta on 2/4/2018.
 */

public class Item implements Serializable{
    private String codigo;
    private String nome;
    private BigDecimal valor;
    private String link;
    private Boolean ativo;

    public Item() {
    }

    public Item(String nome) {
        this.nome = nome;
    }

    public Item(String nome, String link) {
        this.nome = nome;
        this.link = link;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
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
