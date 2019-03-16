/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelDAO;

import connection.connectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Aluno;
import model.Curso;
import model.Matricula;

/**
 *
 * @author Alex
 */
public class MatriculaDAO {
    
    public void insere (Curso c, Aluno a){
        
        Connection con = connectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        
        try {
        stmt = (PreparedStatement) con.prepareStatement("INSERT INTO CURSO_ALUNO (CODIGO_CURSO, CODIGO_ALUNO) VALUES (?,?)");
        stmt.setInt(1, c.getCodigo());
        stmt.setInt(2, a.getCodigo());
        stmt.executeUpdate();
        
        JOptionPane.showMessageDialog(null, "Matrícula realizada com sucesso!");
        
        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao realizar matricula: ", ex);
        } finally {
            connectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    public List<Matricula> read(Aluno a){
        
        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null; 
        List<Matricula> matriculas = new ArrayList<>();
        
        try {           
            stmt = (PreparedStatement) con.prepareStatement(
            "SELECT M.CODIGO, C.DESCRICAO, C.EMENTA "
            +" FROM CURSO C, ALUNO A, CURSO_ALUNO M "
            +"WHERE M.CODIGO_ALUNO = A.CODIGO "
            +"  AND M.CODIGO_CURSO = C.CODIGO "
            +"  AND A.CODIGO = ?");
            
            stmt.setInt(1, a.getCodigo());
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                
                Matricula m = new Matricula(rs.getInt("CODIGO"),
                                            rs.getString("DESCRICAO"),
                                            rs.getString("EMENTA"));         
                matriculas.add(m);  
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar os dados: ", ex);
        }finally{
            connectionFactory.closeConnection(con, stmt);
        }
        return matriculas;
    }
    
    public void deleta (Matricula m) {
        
        Connection con = connectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            
            stmt = (PreparedStatement) con.prepareStatement("DELETE FROM curso_aluno WHERE CODIGO = ?");
            stmt.setInt(1, m.getCodigo());
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso!");
        } catch (SQLException ex) {      
            throw new RuntimeException("Problemas ao realizar exclusão: ", ex);
        } finally {
            connectionFactory.closeConnection(con, stmt);
        }
    }
    
}
