/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

     private int GetLatestId(Connection connection){
        int latest = -1;
        try{ 
        String query = "SELECT MAX(id) FROM produtos";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                latest = resultSet.getInt(1);
            }
        }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return latest;
    }
   
    public void cadastrarProduto (ProdutosDTO produto, Component parent){
        try {
            conn = new conectaDAO().connectDB();
            String query = "INSERT INTO produtos (id, nome, valor, status) VALUES (?, ?, ?, ?)";
            try {
                prep = conn.prepareStatement(query);
                if (produto.getId() == null) prep.setInt(1, GetLatestId(conn) + 1);
                else prep.setInt(1, produto.getId());
                prep.setString(2, produto.getNome());
                prep.setInt(3, produto.getValor());
                prep.setString(4, produto.getStatus());
                prep.executeUpdate();
                JOptionPane.showMessageDialog(parent, "Arquivo salvo com sucesso!");
            }
            finally{
                conn.close();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        try {
            conn = new conectaDAO().connectDB();
            String query = "SELECT * FROM produtos";
            try{
                prep = conn.prepareStatement(query);
                resultset = prep.executeQuery();
                while (resultset.next()){
                    ProdutosDTO prod = new ProdutosDTO();
                    prod.setId(resultset.getInt(1));
                    prod.setNome(resultset.getString(2));
                    prod.setValor(resultset.getInt(3));
                    prod.setStatus(resultset.getString(4));
                    listagem.add(prod);
                }
            }
            finally{
                conn.close();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return listagem;
    }
}

