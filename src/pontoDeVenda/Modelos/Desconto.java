package pontoDeVenda.Modelos;

import java.io.Serializable;
import java.util.Objects;

public class Desconto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idDesconto;
    private Produto produto;
    private Integer percentual;
    private Long qtdMin;
    private Long qtdMax;

    public Desconto() {
    }

    public Desconto(Integer idDesconto, Produto produto, Integer percentual, Long qtdMin, Long qtdMax) {
        this.idDesconto = idDesconto;
        this.produto = produto;
        this.percentual = percentual;
        this.qtdMin = qtdMin;
        this.qtdMax = qtdMax;
    }

    public Integer getIdDesconto() {
        return idDesconto;
    }

    public void setIdDesconto(Integer idDesconto) {
        this.idDesconto = idDesconto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getPercentual() {
        return percentual;
    }

    public void setPercentual(Integer percentual) {
        this.percentual = percentual;
    }

    public Long getQtdMin() {
        return qtdMin;
    }

    public void setQtdMin(Long qtdMin) {
        this.qtdMin = qtdMin;
    }

    public Long getQtdMax() {
        return qtdMax;
    }

    public void setQtdMax(Long qtdMax) {
        this.qtdMax = qtdMax;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.idDesconto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Desconto other = (Desconto) obj;
        if (!Objects.equals(this.idDesconto, other.idDesconto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Desconto de " + percentual + "% no produto " + produto.getDescricao() + "\n";
    }

}
