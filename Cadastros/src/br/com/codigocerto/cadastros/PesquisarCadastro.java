/*
 * PesquisarCadastro.java
 *
 * Criado em 8 de Junho de 2007, 14:47
 *
 */

package br.com.codigocerto.cadastros;

import br.com.codigocerto.bancodados.SelectSqlBD;
import br.com.codigocerto.bancodados.ServidorBD;
import br.com.codigocerto.bancodados.TabelaBD;

/**
 *
 * @author lis
 */
public class PesquisarCadastro {
    
    interface NumeroRegistros {
        int INICIAL = 100,
            MINIMO = 1,
            MAXIMO = 1000,
            TOTAL = 10000;
    }
    
    private String _tabela;
    private int _numeroRegistrosPorConsulta = NumeroRegistros.INICIAL; 
    private String _chavePesquisa; // sequencia a ser pesquisada
    private String[] _colunas;
    private String _campoPesquisa; // campo do bd a ser examinado
    private boolean _resultadoValido = false;
    private int _posicaoAtual = 0;
    private TabelaBD _tabelaResultados;
    private boolean _qualquerPosicao = false; 
    
    /** Cria uma nova instância de PesquisarCadastro */
    public PesquisarCadastro() {
    }

    /**
     *Configura tabela de cadastro a ser pesquisada
     *@param string nome da tabela no bd
     */
    public void setTabela(String tabela) {
        _tabela = tabela;
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
     *Configura sequencia de caracteres a ser pesquisada
     *@param String com sequencia
     */
    public void setChavePesquisa(String chavePesquisa) {
        if (chavePesquisa != null && chavePesquisa.length() > 0) {
            _chavePesquisa = chavePesquisa.trim();
        }
    }

    /**
     *Configura as colunas a serem apresentadas apos consulta
     *@param String[] com nome dos campos associados as colunas do BD
     */
    public void setColunas(String[] colunas) {
        if (colunas.length > 0) {
            _colunas = colunas;
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
     *Configura o nome do campo do bd a ser examinado
     *@param String com nome do campo no bd
     */
    public void setCampoPesquisa(String campoPesquisa) {
        if (campoPesquisa != null && campoPesquisa.length() > 0) {
            _campoPesquisa = campoPesquisa;
        }
    }

    /**
     *Configura o nome do campo do bd a ser examinado
     *@param int com indice do campo no bd
     */
    public void setCampoPesquisa(int indiceCampo) {
        if (indiceCampo >= 0 && indiceCampo < _colunas.length) {
            _campoPesquisa = _colunas[indiceCampo];
        }
    }
    
    /**
     *Informa se a pesquisa encontrou registros
     *@return boolean verdadeiro para registro(s) encontrado(s)
     */
    public boolean isResultadoValido() {
        return _resultadoValido;
    }
    
    /**
     *Configura se pesquisa deve ser somente a partir do inicio do campo ou em qualquer posicao
     *@param boolean verdadeiro para qualquer posicao
     */
    public void setQualquerPosicao(boolean qualquerPosicao) {
        _qualquerPosicao = qualquerPosicao;
    }
    
    /**
     *Efetuar pesquisa conforme parametros
     */
    public void efetuarPesquisa() {
        _resultadoValido = false;
        _posicaoAtual = 0;
        
        if (_tabela != null && _tabela.length() > 0) {
            if (_colunas != null && _colunas.length > 0) {
                if (_campoPesquisa != null && _campoPesquisa.length() > 0) {
                    if (_chavePesquisa != null && _chavePesquisa.length() > 0) {
                        pesquisar();
                    }
                }
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

    private void pesquisar() {

        SelectSqlBD queryConsulta = new SelectSqlBD();
        queryConsulta.setTabela(_tabela);
        for (String coluna : _colunas) {
            queryConsulta.insereColuna(coluna);
        }
        StringBuilder comandoPesquisa = new StringBuilder();
        comandoPesquisa.append(_campoPesquisa + " LIKE '");
        if (_qualquerPosicao) {
            comandoPesquisa.append('%');
        }
        comandoPesquisa.append(_chavePesquisa.toUpperCase() + "%'");
        queryConsulta.insereCondicao(comandoPesquisa.toString());
        queryConsulta.insereOrdem(_campoPesquisa);
        if (ServidorBD.isSuporteTransacao()) {
            queryConsulta.setLimite(NumeroRegistros.TOTAL);
        }
        
        _tabelaResultados = ServidorBD.executaQuery(queryConsulta.getQuery());
        if (_tabelaResultados.getTotalRegistros() > 0) {
            _resultadoValido = true;
        }
        
    }
    
} ///~
