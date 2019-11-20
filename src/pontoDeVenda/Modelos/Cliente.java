package pontoDeVenda.Modelos;

import java.io.Serializable;
import java.util.Objects;

public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer codigoCliente;
    private String nome;
    private Long bonus;
    private Character perfil;
    private Character condicao;

    public Cliente() {
    }

    public Cliente(Integer codCli, String nome, Long bonus, Character perfil, Character condicao) {
        this.codigoCliente = codCli;
        this.nome = nome;
        this.bonus = bonus;
        this.perfil = perfil;
        this.condicao = condicao;
    }

    public Integer obterCodigoCliente() {
        return codigoCliente;
    }

    public void configurarCodigoCliente(Integer codCli) {
        this.codigoCliente = codCli;
    }

    public String obterNome() {
        return nome;
    }

    public void configurarNome(String nome) {
        this.nome = nome;
    }

    public Long obterBonus() {
        return bonus;
    }

    public void configurarBonus(Long bonus) {
        this.bonus = bonus;
    }

    public Character obterPerfil() {
        return perfil;
    }

    public void configurarPerfil(Character perfil) {
        this.perfil = perfil;
    }

    public Character obterCondicao() {
        return condicao;
    }

    public void configurarCondicao(Character condicao) {
        this.condicao = condicao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.codigoCliente);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        return Objects.equals(this.codigoCliente, other.codigoCliente);
    }

    @Override
    public String toString() {
        return "Cliente " + nome + "\n"
                + "Código: " + codigoCliente + "\n"
                + "Bónus atual: " + bonus + "\n"
                + "Perfil: " + perfil + "\n"
                + "Condição: " + (condicao == 'A' ? "Ativo" : "Inativo") + "\n";
    }

}
