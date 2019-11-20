package pontoDeVenda.Modelos;

import java.io.Serializable;

public class Detalhamento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String descricao;
    private Long qteVenda;
    private Double precoUnitario;
    private Double valorTotal;

    @Override
    public String toString() {
        return descricao + " / " + qteVenda + " / " + precoUnitario + " / " + valorTotal + "\n";
    }
    
    

    public Detalhamento() {
    }

    public Detalhamento(String descricao, Long qteVenda, Double precoUnitario, Double valorTotal) {
        this.descricao = descricao;
        this.qteVenda = qteVenda;
        this.precoUnitario = precoUnitario;
        this.valorTotal = valorTotal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getQteVenda() {
        return qteVenda;
    }

    public void setQteVenda(Long qteVenda) {
        this.qteVenda = qteVenda;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
    

}
