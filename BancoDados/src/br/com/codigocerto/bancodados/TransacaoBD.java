/*
 * TransacaoBD.java
 *
 * Criado em 28 de Agosto de 2006, 21:17
 *
 * CodigoCerto Sistemas
 */

package br.com.codigocerto.bancodados;

import java.sql.*;

/**
 *
 * @author Lis
 */
public class TransacaoBD {
    
    private Connection _conexao;
    
    /** Creates a new instance of TransacaoBD */
    public TransacaoBD() {
    }

    /**
     * Fornece acesso direto a conexao do BD
     * @return Connection
     */
    public Connection getConexao() {
        return _conexao;
    }
    
    /**
     * Inicia a conexão com o bd e seta propriedades para transação
     * @param conexao connection a ser utilizada pela transacao
     * @return ok se conexão for iniciada com sucesso
     */
    public boolean iniciar(Connection conexao) {
        boolean ok = true;
        _conexao = conexao;
        
        return ok;
    }
    
    /**
     * Grava os dados pendentes da transação
     * @return ok se os dados foram atualizados com sucesso
     */
    public boolean commit() {
        boolean ok = false;
        try {
            if (ServidorBD.isSuporteTransacao()) {
                _conexao.commit();
            }
            ok = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return ok;
    }

    /**
     * Desfaz os comandos emitidos durante a transação
     * @return ok se operação for bem sucedida
     */
    public boolean rollback() {
        boolean ok = false;
        try {
            if (ServidorBD.isSuporteTransacao()) {
                _conexao.rollback();
            }
            ok = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return ok;
    }
    
    /**
     * Encerra o processo de transação
     * @return ok se fechamento foi realizado com sucesso
     */
    public boolean fechar() {
        boolean ok = true;
        
        return ok;
    }
    
    /**
     * Executa uma query no banco de dados
     * @param string contendo a query
     * @return resultset com od dados obtidos
     */
    public TabelaBD executaQuery(String query)
    {
        TabelaBD resultado = new TabelaBD();
        try 
        {
            Statement consultaSql = this._conexao.createStatement();
            resultado.setResultadoQuery(consultaSql.executeQuery(query));
        } 
        catch (SQLException ex) 
        {
            System.out.println(query);
            ex.printStackTrace();
        }
        
        return resultado;
    }

    /**
     * Executa uma query no banco de dados
     * @param string contendo a query
     * @return resultset com od dados obtidos
     */
    public ResultSet executaQueryRS(String query)
    {
        ResultSet resultado = null;
        try
        {
            Statement consultaSql = this._conexao.createStatement();
            resultado = consultaSql.executeQuery(query);
        }
        catch (SQLException ex)
        {
            System.out.println(query);
            ex.printStackTrace();
        }

        return resultado;
    }

    /**
     * Executa um update no banco de dados
     * @param query string contendo comando 
     * @return int com numero de registro afetados pelo update
     */
    public int executaUpdate(String query)
    {
        int registrosAfetados = 0;
            
        try 
        {
            Statement comandoSql = this._conexao.createStatement();
            registrosAfetados = comandoSql.executeUpdate(query);
        } 
        catch (SQLException ex) 
        {
            System.out.println(query);
            ex.printStackTrace();
        }

        return registrosAfetados;
    }
    
}
