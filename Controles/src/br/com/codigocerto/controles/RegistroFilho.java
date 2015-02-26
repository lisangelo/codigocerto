/*
 * Codigo Certo
 * RegistroFilho.java
 * Criado em 24 de Outubro de 2007, 10:57
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;

/**
 * @author lis
 */
public abstract class RegistroFilho extends Registro {
    
    protected long _idPai = 0;
    protected TransacaoBD _trans = null;

    /**
     * Configura o id do registro pai
     * @param id do registro pai
     */
    public void setIdPai(long id) {
        _idPai = id;
    }
    
    /**
     * Configura a transacao do bd a ser utilizada
     * @param transacao
     */
    @Override
    public void setTransacao(TransacaoBD trans) {
        _trans = trans;
    }
    
    /**
     * Elimina todos os registros filhos de um registro pai
     * @return numero de registros eliminados
     */
    abstract public int eliminaFilhos();

    /**
     * Recupera todos os registros filhos relativos ao registro pai
     * @return array com registro filhos
     */
    abstract public Object[] getFilhos();

    protected int eliminaFilhos(String campoPai) {
       DeleteSqlBD sql = new DeleteSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(campoPai + " = " + _idPai);

       return _trans.executaUpdate(sql.getQuery());
    }
    
} ///:~
