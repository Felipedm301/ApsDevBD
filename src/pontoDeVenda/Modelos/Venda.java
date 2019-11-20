package pontoDeVenda.Modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Objects;

public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    private Cliente cliente;
    private Localidade local;
    private Produto produto;
    private Long qteVenda;
    private Double valorTotal;
    private LocalDate dataVenda;

    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyy");

    @Override
    public String toString() {
        return "Dados da venda: \n"
                + "Cliente: " + cliente + "\n"
                + "Localidade: " + local + "\n"
                + "Produto: " + produto + "\n"
                + "Quantidade de venda: " + qteVenda + "\n"
                + "Total: " + valorTotal + "\n"
                + "Data de venda: " + dataVenda.format(formatador);
    }

    public Venda() {
    }

    public Venda(Cliente cliente, Localidade local, Produto produto, Long qteVenda, Double valorTotal, LocalDate dataVenda) {
        this.cliente = cliente;
        this.local = local;
        this.produto = produto;
        this.qteVenda = qteVenda;
        this.valorTotal = valorTotal;
        this.dataVenda = dataVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Localidade getLocal() {
        return local;
    }

    public void setLocal(Localidade local) {
        this.local = local;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Long getQteVenda() {
        return qteVenda;
    }

    public void setQteVenda(Long qteVenda) {
        this.qteVenda = qteVenda;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.cliente);
        hash = 61 * hash + Objects.hashCode(this.local);
        hash = 61 * hash + Objects.hashCode(this.produto);
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
        final Venda other = (Venda) obj;
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.local, other.local)) {
            return false;
        }
        if (!Objects.equals(this.produto, other.produto)) {
            return false;
        }
        return true;
    }

}
