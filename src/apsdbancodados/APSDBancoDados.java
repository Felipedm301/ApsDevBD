/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsdbancodados;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe
 */
public class APSDBancoDados {
    
    public String verificarExistencia (int codp){
        
        try {
            Class.forName("");
            Connection c = DriverManager.getConnection("");
            PreparedStatement pre = c.prepareStatement("SELECT TRATARPRODUTO(?);");
            pre.setInt(1, codp);
            ResultSet r = pre.executeQuery();
            String saida = r.getNString(1);
            pre.close();
            r.close();
            return saida;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(APSDBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(APSDBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    
    
    /**
     * @param args the command line arguments
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    */
}
