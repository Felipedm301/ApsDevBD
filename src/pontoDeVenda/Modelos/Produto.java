package pontoDeVenda.Modelos;

import java.io.Serializable;
import java.util.Objects;

public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer codProd;
    private Localidade local;
    private String descricao;
    private Long qteEstoque;
    private Double precoUnitario;

    @Override
    public String toString() {
        return "Produto " + descricao + " - código: " + codProd + "\n"
                + "Quantidade em estoque: " + qteEstoque + "\n"
                + "Valor: R$" + precoUnitario + "\n"
                + local + "\n";
    }

    public Produto() {
    }

    public Produto(Integer codProd, Localidade local, String descricao, Long qteEstoque, Double precoUnitario) {
        this.codProd = codProd;
        this.local = local;
        this.descricao = descricao;
        this.qteEstoque = qteEstoque;
        this.precoUnitario = precoUnitario;
    }

    public Integer getCodProd() {
        return codProd;
    }

    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
    }

    public Localidade getLocal() {
        return local;
    }

    public void setLocal(Localidade local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getQteEstoque() {
        return qteEstoque;
    }

    public void setQteEstoque(Long qteEstoque) {
        this.qteEstoque = qteEstoque;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.codProd);
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
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.codProd, other.codProd)) {
            return false;
        }
        return true;
    }

}
