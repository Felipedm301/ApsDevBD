package pontoDeVenda.Modelos;

import java.io.Serializable;
import java.util.Objects;

public class Localidade implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer codLocal;
    private String nome;
    private String endereco;
    private String telefone;

    public Localidade() {
    }

    public Localidade(Integer codLocal, String nome, String endereco, String telefone) {
        this.codLocal = codLocal;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public Integer ObterIdentificadorLocalidade() {
        return codLocal;
    }

    public void setCodLocal(Integer codLocal) {
        this.codLocal = codLocal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.codLocal);
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
        final Localidade other = (Localidade) obj;
        if (!Objects.equals(this.codLocal, other.codLocal)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Localidade " + nome + "\n"
                + "Código: " + codLocal + "\n"
                + "Endereço: " + endereco + "\n"
                + "Telefone: " + telefone + "\n";
    }

}
