package bertanha.com.br.giftlist.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by berta on 2/4/2018.
 */

public class Lista implements Serializable{
    private String codigo;
    private String nome;
    private Date dataDeCriacao;
    private List<Item> itens;
    private Double total;

    public Lista() {
    }

    public Lista(String nome, Date dataDeCriacao, List<Item> itens, Double total) {
        this.nome = nome;
        this.dataDeCriacao = dataDeCriacao;
        this.itens = itens;
        this.total = total;
    }

    public Lista(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Lista(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
