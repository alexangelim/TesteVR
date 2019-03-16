/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelDAO;

import com.mysql.jdbc.PreparedStatement;
import connection.connectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Aluno;
import model.Curso;

/**
 *
 * @author Alex
 */
public class CursoDAO {
    
    public void insere (Curso c){
        
        Connection con = connectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        
        try {
        stmt = (PreparedStatement) con.prepareStatement("INSERT INTO CURSO (DESCRICAO, EMENTA) VALUES (?, ?)");
        stmt.setString(1, c.getDescricao());
        stmt.setString(2, c.getEmenta());
        stmt.executeUpdate();
        
        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao inserir dados", ex);
        } finally {
            
            connectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public List<Curso> read() {
        
        Connection con = connectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        
        ResultSet rs = null;
        
        List<Curso> cursos = new ArrayList<>();
        
        try {
            
            stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM CURSO");
            rs = stmt.executeQuery();
            
            while(rs.next()) {               
                Curso c = new Curso(rs.getInt("CODIGO"), rs.getString("DESCRICAO"), rs.getString("EMENTA"));
                cursos.add(c);    
            }
            
        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao listar os dados: ", ex);
        } finally {
            connectionFactory.closeConnection(con, stmt);
        }        
        return cursos;
    }
    
    public List<Curso> readNotIn(Aluno a) {
        
        Connection con = connectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        
        ResultSet rs = null;
        
        List<Curso> cursos = new ArrayList<>();
        
        try {
            
            stmt = (PreparedStatement) con.prepareStatement(
                     "SELECT * "
                    +" FROM CURSO C "
                    +"WHERE C.CODIGO NOT IN "
                    +"      (SELECT CODIGO_CURSO "
                    +"         FROM CURSO_ALUNO CA "
                    +"    WHERE CA.CODIGO_ALUNO = ?)");
            stmt.setInt(1, a.getCodigo());
            rs = stmt.executeQuery();
            
            while(rs.next()) {               
                Curso c = new Curso(rs.getInt("CODIGO"), rs.getString("DESCRICAO"), rs.getString("EMENTA"));
                cursos.add(c);    
            }
            
        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao listar os dados: ", ex);
        } finally {
            connectionFactory.closeConnection(con, stmt);
        }        
        return cursos;
    }
    
    public void atualiza (Curso c) {
        
        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = (PreparedStatement) con.prepareStatement("UPDATE CURSO SET DESCRICAO = ?, EMENTA = ? WHERE CODIGO = ?");
            stmt.setString(1, c.getDescricao());
            stmt.setString(2, c.getEmenta());
            stmt.setInt(3, c.getCodigo());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao atualizar os dados: ", ex);
        } finally {
            connectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void deleta (Curso c) {
        
        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            
            stmt = (PreparedStatement) con.prepareStatement("DELETE FROM CURSO WHERE CODIGO = ?");
            stmt.setInt(1, c.getCodigo());
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso!");
        } catch (SQLException ex) {
            
            throw new RuntimeException("Problemas ao realizar exclusão: ", ex);
        } finally {
            connectionFactory.closeConnection(con, stmt);
        }
    }
    
}
