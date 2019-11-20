package pontoDeVenda.AcessoAosDados;

import pontoDeVenda.BaseDeDados.Factory;
import pontoDeVenda.Modelos.Localidade;
import pontoDeVenda.Modelos.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DadosProduto implements ObjetoAcessoAosDados<Produto> {

    private Connection conexao;

    @Override
    public Produto ObterPeloIdentificador(Integer id) throws SQLException {

        try {
            String consulta = "SELECT * FROM produto WHERE codprod = ?";
            conexao = Factory.obterConexao();
            conexao.setAutoCommit(false);
            PreparedStatement ps = conexao.prepareStatement(consulta);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.first();
            Produto produto = new Produto(
                    rs.getInt("codprod"),
                    null,
                    rs.getString("descricao"),
                    rs.getLong("qte_estoque"),
                    rs.getDouble("preco_unitario")
            );

            consulta = "SELECT * FROM localidade WHERE codlocal = ?";
            ps = conexao.prepareStatement(consulta);
            ps.setInt(1, rs.getInt("codlocal"));
            rs = ps.executeQuery();
            rs.first();
            Localidade localidade = new Localidade(rs.getInt("codlocal"), rs.getString("nome"), rs.getString("endereco"), rs.getString("telefone"));
            produto.setLocal(localidade);
            conexao.commit();
            return produto;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi encontrado produto com esse código!", "Produto inválido", JOptionPane.ERROR_MESSAGE);
            conexao.rollback();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            conexao.rollback();
        }
        return null;
    }

    @Override
    public List<Produto> ObterTodosItens() throws SQLException {
        try {
            String consulta = "SELECT * FROM localidade";
            List<Produto> produtos = new ArrayList<>();
            List<Localidade> localidades = new ArrayList<>();
            conexao = Factory.obterConexao();
            conexao.setAutoCommit(false);
            PreparedStatement ps = conexao.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                localidades.add(new Localidade(rs.getInt("codlocal"), rs.getString("nome"), rs.getString("endereco"), rs.getString("telefone")));
            }

            consulta = "SELECT * FROM produto";
            ps = conexao.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                produtos.add(new Produto(rs.getInt("codprod"), setLocal(localidades, rs.getInt("codlocal")), rs.getString("descricao"),
                        rs.getLong("qte_estoque"), rs.getDouble("preco_unitario")));
            }
            conexao.commit();
            return produtos;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DadosCliente.class.getName()).log(Level.SEVERE, null, ex);
            conexao.rollback();
        }
        return null;
    }

    private Localidade setLocal(List<Localidade> localidades, Integer IdentificadorLocalidade) {
        Localidade local = new Localidade();
        localidades.stream().forEach(localidade -> {
            if (Objects.equals(localidade.ObterIdentificadorLocalidade(), IdentificadorLocalidade)) {
                localidade.setCodLocal(localidade.ObterIdentificadorLocalidade());
                localidade.setEndereco(localidade.getEndereco());
                localidade.setNome(localidade.getNome());
                localidade.setTelefone(localidade.getTelefone());
                return;
            }
        });

        return local;
    }

}
