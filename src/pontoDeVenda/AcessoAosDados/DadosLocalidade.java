package pontoDeVenda.AcessoAosDados;

import pontoDeVenda.BaseDeDados.Factory;
import pontoDeVenda.Modelos.Localidade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DadosLocalidade implements ObjetoAcessoAosDados<Localidade> {

    private Connection conexao;

    @Override
    public Localidade ObterPeloIdentificador(Integer id) throws SQLException {
        try {
            String consulta = "SELECT * FROM localidade WHERE codlocal = ?";
            conexao = Factory.obterConexao();
            conexao.setAutoCommit(false);
            PreparedStatement ps = conexao.prepareStatement(consulta);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.first();
            Localidade localidade = new Localidade(
                    rs.getInt("codlocal"), 
                    rs.getString("nome"), 
                    rs.getString("endereco"), 
                    rs.getString("telefone")
            );
            conexao.commit();
            return localidade;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "localidade não encontrado ! \n" + ex.getMessage(), "localidade inválido", JOptionPane.ERROR_MESSAGE);
            conexao.rollback();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            conexao.rollback();
        }
        return null;
    }

    @Override
    public List<Localidade> ObterTodosItens() throws SQLException {
        try {
            String consulta = "SELECT * FROM localidade";
            List<Localidade> localidades = new ArrayList<>();
            conexao = Factory.obterConexao();
            conexao.setAutoCommit(false);
            PreparedStatement ps = conexao.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                localidades.add(new Localidade(
                        rs.getInt("codlocal"), 
                        rs.getString("nome"), 
                        rs.getString("endereco"), 
                        rs.getString("telefone")
                ));
            }
            conexao.commit();
            return localidades;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DadosLocalidade.class.getName()).log(Level.SEVERE, null, ex);
            conexao.rollback();
        }
        return new ArrayList<>();
    }

}
