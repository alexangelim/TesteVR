/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelDAO;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import model.Aluno;
import connection.connectionFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
public class AlunoDAO {
    
    public void insere (Aluno a) {
        
        Connection con = connectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        
        try {
        stmt = (PreparedStatement) con.prepareStatement("INSERT INTO ALUNO (NOME) VALUES (?)");
        stmt.setString(1, a.getNome());
        stmt.executeUpdate();
        
        JOptionPane.showMessageDialog(null,"Aluno Cadastrado com sucesso!");
        
        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao inserir dados", ex);
        } finally {
            
            connectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public List<Aluno> read() {
        
        Connection con = connectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        
        ResultSet rs = null;
        
        List<Aluno> alunos = new ArrayList<>();
        
        try {
            
            stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM ALUNO ORDER BY NOME");
            rs = stmt.executeQuery();
            
            while(rs.next()) {               
                Aluno aluno = new Aluno(rs.getInt("CODIGO"), rs.getString("NOME"));
                alunos.add(aluno);    
            }
            
        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao listar os dados: ", ex);
        } finally {
            connectionFactory.closeConnection(con, stmt);
        }        
        return alunos;
    }
    
    public void atualiza (Aluno a) {
        
        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = (PreparedStatement) con.prepareStatement("UPDATE ALUNO SET NOME = ? WHERE CODIGO = ?");
            stmt.setString(1, a.getNome());
            stmt.setInt(2, a.getCodigo());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao atualizar os dados: ", ex);
        } finally {
            connectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void deleta (Aluno a) {
        
        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            
            stmt = (PreparedStatement) con.prepareStatement("DELETE FROM ALUNO WHERE CODIGO = ?");
            stmt.setInt(1, a.getCodigo());
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"Exclusão realizada com sucesso!");
        } catch (SQLException ex) {
            
            throw new RuntimeException("Problemas ao realizar exclusão: ", ex);
        } finally {
            connectionFactory.closeConnection(con, stmt);
        }
    }
    
}
