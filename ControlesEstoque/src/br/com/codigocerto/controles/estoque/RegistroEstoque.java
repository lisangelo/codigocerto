/*
 * Codigo Certo
 * RegistroMovimento.java
 * Criado em 16 de Julho de 2007, 14:51
 */

package br.com.codigocerto.controles.estoque;

import br.com.codigocerto.bancodados.ServidorBD;
import br.com.codigocerto.bancodados.TransacaoBD;
import br.com.codigocerto.controles.Ligacao;

/**
 * @author lis
 */
public abstract class RegistroEstoque {
    
    protected Ligacao ligacao;
    private TransacaoBD _transacao;

    public RegistroEstoque() {
        _transacao = ServidorBD.getTransacao();
        estabelecerLigacao();
    }
    public RegistroEstoque(TransacaoBD transacao) {
        _transacao = transacao;
        estabelecerLigacao();
    }

    /**
     * Obtem a transacao utilizada pelo estoque
     * @return transacao do banco de dados
     */
    public TransacaoBD getTransacao() {
        return _transacao;
    }
    
    /*
     *Envia objeto para insercao na base de dados
     *@param objeto a ser inserido na base
     *@return verdadeiro para sucesso na insercao
     */
    abstract public boolean setDadosInsercao(Object dados);

    /*
     *Metodo padrao para estabelecer as ligacoes entre o modelo e a tabela da base de dados
     */
    abstract protected void estabelecerLigacao();
    
    
    public String getTabela() {
        return ligacao.getTabela();
    }
 
    public String getCampoId() {
        return ligacao.getCampoId();
    }

} ///:~