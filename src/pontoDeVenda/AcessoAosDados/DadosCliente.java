package pontoDeVenda.AcessoAosDados;

import pontoDeVenda.BaseDeDados.Factory;
import pontoDeVenda.Modelos.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DadosCliente implements ObjetoAcessoAosDados<Cliente> {

    private Connection conexao;

    @Override
    public Cliente ObterPeloIdentificador(Integer id) throws SQLException {
        try {
            String consulta = "SELECT * FROM cliente WHERE codcli = ?";
            conexao = Factory.obterConexao();
            conexao.setAutoCommit(false);
            PreparedStatement ps = conexao.prepareStatement(consulta);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.first();
            Cliente cliente = new Cliente(
                    rs.getInt("codcli"), 
                    rs.getString("nome"), 
                    rs.getLong("bonus"),
                    rs.getString("perfil").charAt(0),
                    rs.getString("condicao").charAt(0)
            );
            conexao.commit();
            return cliente;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado! " + ex.getMessage(), "Cliente inválido", JOptionPane.ERROR_MESSAGE);
            conexao.rollback();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            conexao.rollback();
        }
        return null;
    }

    @Override
    public List<Cliente> ObterTodosItens() throws SQLException {

        try {
            String consulta = "SELECT * FROM cliente";
            List<Cliente> clientes = new ArrayList<>();
            conexao = Factory.obterConexao();
            conexao.setAutoCommit(false);
            PreparedStatement ps = conexao.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("codcli"), 
                        rs.getString("nome"), 
                        rs.getLong("bonus"),
                        rs.getString("perfil").charAt(0), 
                        rs.getString("condicao").charAt(0)
                ));
            }
            conexao.commit();
            return clientes;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DadosCliente.class.getName()).log(Level.SEVERE, null, ex);
            conexao.rollback();
        }
        return new ArrayList<>();
    }

}
