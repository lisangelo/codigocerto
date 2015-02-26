/*
 * PesquisarCadastro.java
 *
 * Criado em 8 de Junho de 2007, 14:47
 *
 */

package br.com.codigocerto.controles.pesquisas;

import br.com.codigocerto.bancodados.*;

/**
 *
 * @author lis
 */
public class PesquisarMovimento {
    
    interface NumeroRegistros {
        int INICIAL = 100,
            MINIMO = 1,
            MAXIMO = 1000,
            TOTAL = 10000;
    }
    
    private String _tabela;
    private int _numeroRegistrosPorConsulta = NumeroRegistros.INICIAL; 
    private String[] _colunas;
    private boolean _resultadoValido = false;
    private int _posicaoAtual = 0;
    private TabelaBD _tabelaResultados;
    SelectSqlBD _queryConsulta;
    
    /** Cria uma nova instância de PesquisarMovimento */
    public PesquisarMovimento() {
        configurar();
    }

    /**
     *Configura tabela de cadastro a ser pesquisada
     *@param string nome da tabela no bd
     */
    public void setTabela(String tabela) {
        _tabela = tabela;
        _queryConsulta.setTabela(_tabela);
    }

    /**
     *Configura numero de registros a serem retornados a cada consulta
     *@param int com numero de registros
     */
    public void setNumeroRegistrosPorConsulta(int numeroRegistrosPorConsulta) {
        if (numeroRegistrosPorConsulta >= NumeroRegistros.MINIMO 
                && numeroRegistrosPorConsulta <= NumeroRegistros.MAXIMO) {
            _numeroRegistrosPorConsulta = numeroRegistrosPorConsulta;
        }
    }

    /**
     *Configura as colunas a serem apresentadas apos consulta
     *@param String[] com nome dos campos associados as colunas do BD
     */
    public void setColunas(String[] colunas) {
        if (colunas.length > 0) {
            _colunas = colunas;
            for (String coluna : _colunas) {
                _queryConsulta.insereColuna(coluna);
            }
        }
    }
    
    /**
     * Informa as colunas a serem apresentadas apos consulta
     * @return String[] com nome dos campos associados as colunas do BD
     */
    public String[] getColunas() {
        return _colunas;
    }

    /**
     *Informa se a pesquisa encontrou registros
     *@return boolean verdadeiro para registro(s) encontrado(s)
     */
    public boolean isResultadoValido() {
        return _resultadoValido;
    }
    
    /**
     *Efetuar pesquisa conforme parametros
     */
    public void efetuarPesquisa() {
        _resultadoValido = false;
        _posicaoAtual = 0;
        
        if (_tabela != null && _tabela.length() > 0) {
            if (_colunas != null && _colunas.length > 0) {
                pesquisar();
            }
        }
    }
    
    /**
     *Retornar primeiro bloco de resultados da pesquisa
     *@return String[][] com registros
     */
    public String[][] primeiro() {
        
        _posicaoAtual = 0;
        int totalRegistros = _tabelaResultados.getTotalRegistros();
        int numeroRegistros = _numeroRegistrosPorConsulta;
        if (totalRegistros < numeroRegistros) {
            numeroRegistros = totalRegistros;
        }
        String resultados[][] = blocoRegistros(_posicaoAtual, numeroRegistros);
        _posicaoAtual += numeroRegistros;

        return resultados;
    }

    /**
     *Retornar ultimo bloco de resultados da pesquisa
     *@return String[][] com registros
     */
    public String[][] ultimo() {
        
        int totalRegistros = _tabelaResultados.getTotalRegistros();
        int numeroRegistros = _numeroRegistrosPorConsulta;
        if (totalRegistros < numeroRegistros) {
            numeroRegistros = totalRegistros;
        }
        _posicaoAtual = totalRegistros - numeroRegistros;
        String resultados[][] = blocoRegistros(_posicaoAtual, numeroRegistros);
        _posicaoAtual += numeroRegistros;

        return resultados;
    }
    
    /**
     *Retornar proximo bloco de resultados da pesquisa
     *@return String[][] com registros
     */
    public String[][] proximo() {
        
        int totalRegistros = _tabelaResultados.getTotalRegistros();
        int numeroRegistros = _numeroRegistrosPorConsulta;
        if (totalRegistros < numeroRegistros) {
            numeroRegistros = totalRegistros;
        }
        if (_posicaoAtual >= totalRegistros) {
            _posicaoAtual = totalRegistros - numeroRegistros;
        }
        String resultados[][] = blocoRegistros(_posicaoAtual, numeroRegistros);
        if (resultados != null) {
            _posicaoAtual += numeroRegistros;
        }

        return resultados;
    }

    /**
     *Retornar bloco anterior de resultados da pesquisa
     *@return String[][] com registros
     */
    public String[][] anterior() {
        
        int totalRegistros = _tabelaResultados.getTotalRegistros();
        int numeroRegistros = _numeroRegistrosPorConsulta;
        if (totalRegistros < numeroRegistros) {
            numeroRegistros = totalRegistros;
        }
        _posicaoAtual -= (numeroRegistros * 2);
        if (_posicaoAtual < 0) {
            _posicaoAtual = 0;
        }
        String resultados[][] = blocoRegistros(_posicaoAtual, numeroRegistros);
        if (resultados != null) {
            _posicaoAtual += numeroRegistros;
        }

        return resultados;
    }
    
    /**
     * Insere filtro de pesquisa (where, like) na query
     * @param filtro
     */
    public void insereFiltroPesquisa(String filtro) {
        _queryConsulta.limpaCondicoes();
        if (filtro != null) {
            _queryConsulta.insereCondicao(filtro);
        }
    }
    
    /**
     * Insere ordenacao na query
     * @param ordem
     */
    public void insereOrdemPesquisa(String ordem) {
        _queryConsulta.insereOrdem(ordem);
    }

    private String[][] blocoRegistros(int posicaoInicial, int numeroRegistros) {
        String resultados[][] = null;
        if (posicaoInicial >= 0) { 
            resultados = new String [numeroRegistros][_colunas.length];
            if (_tabelaResultados.irPara(posicaoInicial)) { 
                for (int i = 0; i < numeroRegistros; i++) {
                    for (int j = 0; j < _colunas.length; j++) {
                        if (_tabelaResultados.getColuna(j) != null) {
                            resultados[i][j] = _tabelaResultados.getColuna(j).toString();
                        } else {
                            resultados[i][j] = "";
                        }
                            
                    }
                    _tabelaResultados.proximo();
                }
            }
        }
        
        return resultados;
    }

    private void configurar() {
        _queryConsulta = new SelectSqlBD();        
        _queryConsulta.setLimite(NumeroRegistros.TOTAL);
    }
    
    private void pesquisar() {
        _tabelaResultados = ServidorBD.executaQuery(_queryConsulta.getQuery());
        if (_tabelaResultados.getTotalRegistros() > 0) {
            _resultadoValido = true;
        }
    }
    
} ///:~
