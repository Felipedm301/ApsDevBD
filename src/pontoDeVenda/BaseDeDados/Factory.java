package pontoDeVenda.BaseDeDados;

import java.sql.*;
import javax.swing.JOptionPane;

public class Factory {

    private static Connection conexão = null;
    private static final String baseDeDados = "PontoDeVenda";
    private static final String urlDeConexao = "jdbc:mysql://localhost:3306/" + baseDeDados;
    private static final String usuario = "root";
    private static final String senha = "root";
    private static final String recurso = "com.mysql.jdbc.Driver";

    public static Connection obterConexao() throws SQLException, ClassNotFoundException {
        try {
            if (conexão == null) {
                Class.forName(recurso);
                conexão = DriverManager.getConnection(urlDeConexao, usuario, senha);
            }
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException("Não foi possível carregar o recurso do banco");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage() + "\n Motivo: " + ex.getCause().getMessage(), "ERRO NO BANCO DE DADOS", JOptionPane.ERROR_MESSAGE);
            throw new SQLException("Erro ao conectar: " + ex.getMessage());
        }

        return conexão;
    }

    public static void FecharConexao() throws SQLException {
        try {
            conexão.close();
        } catch (SQLException ex) {
            throw new SQLException("Falha ao desconectar:" + ex.getMessage());
        }
    }
}
