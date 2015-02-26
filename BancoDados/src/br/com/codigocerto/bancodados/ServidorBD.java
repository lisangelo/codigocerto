/*
 * ServidorBD.java
 *
 * Criado em 26 de Agosto de 2006, 11:05
 *
 * CodigoCerto Sistemas
 */

package br.com.codigocerto.bancodados;

import java.io.IOException;
import java.sql.*;
import org.xml.sax.SAXException;

import br.com.codigocerto.xml.ParserXML;

/**
 *
 * @author Lisangelo
 */
public class ServidorBD {

    private static ServidorBD _instance; // singleton
    private static String _tipo = ""; // tipo do banco de dados
    private static String _endereco = ""; // endereco ip do servidor
    private static String _usuario = ""; // usuario do bd
    private static String _driver = ""; // driver jdbc para conexao
    private static String _senha = ""; // senha do bd
    private static String _nome = ""; // nome do bd
    private static String _url = "";
    private static ResultSet _resultado;
    private static Connection _conexao = null;
    private static Connection _transacao = null;
    private static boolean _suporteTransacao = false;
    private static Statement _consultaSql = null;
    
    private ServidorBD() {
    }

    /**
     * iniciar singleton
     */
    public synchronized static ServidorBD getInstance() 
    {
        if( _instance == null ) {
            _instance = new ServidorBD();
        }
        return _instance;
    }

    /**
     * Ler os dados do arquivo de configuracao, carregar o driver e conectar ao servidor
     * @return ok se os parâmetros foram inicializados com sucesso
     */
    public static boolean iniciar(String uri) {
        boolean ok = false;
        try
        {
            ParserXML parser = new ParserXML();
            parser.setNomeArquivoXml(uri);
            parser.Iniciar();
            parser.ProcessarArquivoXml();
            _tipo = parser.getValor("tipo");
            _usuario = parser.getValor("usuario");
            _driver = parser.getValor("driver");
            _endereco = parser.getValor("endereco");
            _senha = parser.getValor("senha");
            _nome = parser.getValor("nome");
            _suporteTransacao = verificarSuporteTransacao(parser.getValor("transacao"));
            ok = true;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (SAXException e)
        {
            System.out.println(e.getMessage());
        }
        try
        {
            Class.forName(_driver).newInstance();
            if (_tipo.contains("mysql")) {
                _url = "jdbc:" + _tipo + "://" + _endereco + "/" + _nome;
            } else {
                _url = "jdbc:" + _tipo + ":" + _nome + ";create=false";
            }
        }
        catch (Exception e)
        {
            System.out.println("Driver do banco de dados falhou!");
            System.exit(1);
        }
        finally
        {
            getConexao();
        }
        return ok;
    }
    
    /**
     * Encerra conexao ao banco de dados
     * @return ok se conexão foi encerrada com sucesso
     */
    public static boolean encerraConexao() 
    {
        boolean ok = false;
        if (_conexao != null)
        {
            try
            {
                _conexao.close();
                if (_transacao != null) {
                    _transacao.close();
                }
                ok = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return ok;
    }
    
    /**
     * Informa se existe suporte a transacao no banco de dados
     * @return boolean verdadeiro para suporte valido
     */
    public static boolean isSuporteTransacao() {
        return _suporteTransacao;
    }
    
    /**
     * Executa uma query no banco de dados
     * @param string contendo a query
     * @return resultset com od dados obtidos
     */
    public static TabelaBD executaQuery(String query)
    {
        TabelaBD resultado = new TabelaBD();
        try 
        {
            _consultaSql = _conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            resultado.setResultadoQuery(_consultaSql.executeQuery(query));
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
    public static ResultSet executaQueryRS(String query)
    {
        ResultSet resultado = null;
        try
        {
            _consultaSql = _conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            resultado = _consultaSql.executeQuery(query);
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
    public static int executaUpdate(String query)
    {
        int registrosAfetados = 0;
            
        try 
        {
            Statement comandoSql = _conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            registrosAfetados = comandoSql.executeUpdate(query);
            comandoSql.close();
        } 
        catch (SQLException ex) 
        {
            System.out.println(query);
            ex.printStackTrace();
            registrosAfetados = -1;
        }

        return registrosAfetados;
    }

    /**
     * Conecta ao banco de dados
     * utilizando os parâmetros obtidos através do arquivo xml
     * @return ok se a conexao foi estabelecida
     */
    private static boolean getConexao() 
    {
        boolean ok = false;
        try
        {
            _conexao = DriverManager.getConnection(_url, _usuario, _senha);
            if (_suporteTransacao) {
                _transacao = DriverManager.getConnection(_url, _usuario, _senha);
                _transacao.setAutoCommit(false);
                _transacao.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            }
            ok = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return ok;
    }

    /**
     * Solicita uma transação ao BD
     * @return Retorna transação
     */
    public static TransacaoBD getTransacao() {
        TransacaoBD transacao = new TransacaoBD();
        if (_suporteTransacao) {
            transacao.iniciar(_transacao);
        } else {
            transacao.iniciar(_conexao);
        }
        
        return transacao;
    }

    private static boolean verificarSuporteTransacao(String suporte) {
        boolean ok = false;

        if (suporte != null) {
            if (!suporte.isEmpty()) {
                if (suporte.equalsIgnoreCase("SIM")) {
                    ok = true;
                }
            }
        }

        return ok;
    }
    
}///:~