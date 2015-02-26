/*
 * Registro.java
 *
 * Created on 26 de Março de 2007, 14:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.com.codigocerto.controles;

import java.util.ArrayList;

import br.com.codigocerto.bancodados.DeleteSqlBD;
import br.com.codigocerto.bancodados.SelectSqlBD;
import br.com.codigocerto.bancodados.ServidorBD;
import br.com.codigocerto.bancodados.TabelaBD;
import br.com.codigocerto.bancodados.TransacaoBD;
import br.com.codigocerto.conversores.ConversorTipos;

/**
 *
 * @author lis
 */
public abstract class Registro implements IRegistro {
    
    protected Ligacao ligacao;
    private ArrayList<Campo> listaCampos = new ArrayList<Campo>();
    private TransacaoBD _trans;

    public Registro() {
        estabelecerLigacao();
    }
    public Registro(TransacaoBD trans) {
        _trans = trans;
        estabelecerLigacao();
    }

    public void setTransacao(TransacaoBD trans) {
        _trans = trans;
    }
    
    /*
     *Envia objeto para insercao na base de dados
     *@param objeto a ser inserido na base
     *@return verdadeiro para sucesso na insercao
     */
    @Override
    abstract public boolean setDadosInsercao(Object dados);

    /*
     *Envia objeto para alteracao na base de dados
     *@param objeto a ser alterado na base
     *@return verdadeiro para sucesso na alteracao
     */
    @Override
    abstract public boolean setDadosAlteracao(Object dados);

    /*
     *Obtem dados da base
     *@param long com Id do modelo
     *@return objeto com dados retornados da base
     */ 
    @Override
    abstract public Object getDados(long id);
    
    /*
     *Metodo padrao para estabelecer as ligacoes entre o modelo e a tabela da base de dados
     */
    abstract protected void estabelecerLigacao();
    
    /*
     *Metodo para estabelecer vinculo entre campo da tabela e propriedade da classe
     *@param Campo objeto com propriedades do vinculo
     */
    public void estabelecerCampo(Campo campo) {
        listaCampos.add(campo);
    }
    
    /**
     * Recupera informacoes dos campos do registro e cadastro
     * @return lista de campos
     */
    public ArrayList getCampos() {
        return listaCampos;
    }
    
    /*
     *Buscar posicao do primeiro registro
     *@return id para primeiro registro encontrado
     */
    @Override
    public long buscarPrimeiro() {
        
        long primeiroId = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(ligacao.getCampoId(), "id", "min");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        
        if (reg.primeiro() && reg.getColuna(0) != null) {
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(reg.getColuna(0).toString());
            primeiroId = conversor.getLong();
        }
        reg.fechar();
        
        return primeiroId;
    }
    
    /*
     *Buscar posicao do ultimo registro
     *@return id para ultimo registro encontrado
     */
    @Override
    public long buscarUltimo() {
        long ultimoId = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(ligacao.getCampoId(), "id", "max");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        
        if (reg.primeiro() && reg.getColuna(0) != null) {
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(reg.getColuna(0).toString());
            ultimoId = conversor.getLong();
        }
        reg.fechar();
        
        return ultimoId;
    }
    
    /*
     *Buscar posicao do proximo registro
     *@param Id do registro referencia
     *@return id para proximo registro encontrado
     */
    @Override
    public long buscarProximo(long id) {
        long proximoId = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(ligacao.getCampoId(), "id", "min");
        sql.insereCondicao(ligacao.getCampoId() + " > " + id);
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        
        if (reg.primeiro() && reg.getColuna(0) != null) {
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(reg.getColuna(0).toString());
            proximoId = conversor.getLong();
        }

        reg.fechar();
        
        return proximoId;
    }
    
    /*
     *Buscar posicao do registro anterior
     *@param Id do registro referencia
     *@return id para registro anterior encontrado
     */
    @Override
    public long buscarAnterior(long id) {
        long anteriorId = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(ligacao.getCampoId(), "id", "max");
        sql.insereCondicao(ligacao.getCampoId() + " < " + id);
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        
        if (reg.primeiro() && reg.getColuna(0) != null) {
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(reg.getColuna(0).toString());
            anteriorId = conversor.getLong();
        }

        reg.fechar();
        
        return anteriorId;
    }

    /*
     *Excluir registro da base de dados
     *@param Id do registro a ser excluido
     *@return verdadeiro para exclusao bem sucedida
     */
    @Override
     public boolean excluir(long id) {
         boolean ok = false;

         if (! isUtilizado(id)) { // se registro nao estiver em uso
         
             DeleteSqlBD sql = new DeleteSqlBD();
             sql.setTabela(ligacao.getTabela());
             sql.insereCondicao(ligacao.getCampoId() + " = " + id);

             if (ServidorBD.executaUpdate(sql.getQuery()) == 1) {
                 ok = true;
             }

         }
         
         return ok;
    }
    
  /**
    * Obtem registros de uma tabela atraves da consulta a um campo
    * @param nomeCampo - nome do campo na tabela a ser utilizado como pesquisa
    * @param chave - conteudo a ser pesquisado
    * @param campoDescricao - segundo campo alem do id a ser recuperado
    * @return array de objetos com colunas da tabela
   */
    @Override
    public Object[][] procurarCampo(String nomeCampo, String chave, String campoDescricao) {
        Object[][] resultado = null;
        boolean ok = true;

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(ligacao.getCampoId());
        sql.insereColuna(campoDescricao);
        if (nomeCampo.endsWith("_id")) {
            sql.insereCondicao(nomeCampo + " = " + chave);
            if (!chave.matches("0*[1-9][0-9]*")) {
                ok = false;
            }
        } else {
            sql.insereCondicao(nomeCampo + " LIKE " + chave);
        }
        if (ok) {
            TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
            int numRegistros = reg.getTotalRegistros();

            if (numRegistros > 0) {
                resultado = new Object[numRegistros][2];
                reg.primeiro();
                ConversorTipos conv = new ConversorTipos();
                for (int i = 0; i < numRegistros; i++) {
                    conv.setValorBase(reg.getColuna(0).toString());
                    resultado[i][0] = new Long(conv.getLong());
                    resultado[i][1] = reg.getColuna(1);
                    reg.proximo();
                }
            }

            reg.fechar();
        }

        return resultado;
    }
    
  /**
    * Obtem registros de uma tabela atraves da consulta a um campo
    * @param nomeCampo - nome do campo na tabela a ser utilizado como pesquisa
    * @param chave - conteudo a ser pesquisado
    * @return verdadeiro para encontrado
   */
    public boolean procurarId(String nomeCampo, long id) {
        boolean encontrado = false;

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(nomeCampo);
        sql.insereCondicao(nomeCampo + " = " + id);
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        int numRegistros = reg.getTotalRegistros();

        if (numRegistros > 0) {
            encontrado = true;
        }

        reg.fechar();

        return encontrado;
    }

    @Override
    public String getTabela() {
        return ligacao.getTabela();
    }
 
    @Override
    public String getCampoId() {
        return ligacao.getCampoId();
    }

    public TransacaoBD getTransacao() {
        return _trans;
    }

    protected String getModelo(String iu) {
        String modelo = "";
        Campo campo;
        int tamanho = listaCampos.size();
        int pos = 0;
        
        while (pos < tamanho) {
            campo = listaCampos.get(pos);
            if (campo.getIU().equals(iu)) {
                modelo = campo.getModelo();
                pos = tamanho;
            }
            pos++;
        }
        
        return modelo;
    }
    
    protected String getBanco(String modelo) {
        String banco = "";
        Campo campo;
        int tamanho = listaCampos.size();
        int pos = 0;
        
        while (pos < tamanho) {
            campo = listaCampos.get(pos);
            if (campo.getModelo().equals(modelo)) {
                banco = campo.getBanco();
                pos = tamanho;
            }
            pos++;
        }
        
        return banco;
    }
    
    /**
     * Evitar sobrecarga no banco de dados controlando o numero de transacoes efetuadas em bloco
     * @param numero sequencial da transacao
     */
    protected void controlarTransacao(int numeroDaTransacao) {
       final int NUMERO_MAX_TRANSACOES_BLOCO = 30;
       final int ESPERA = 1;
       
       if (numeroDaTransacao % NUMERO_MAX_TRANSACOES_BLOCO == 0) {
            try {
                Thread.sleep(ESPERA * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
       }
       
    }
    
    /**
     * Informa se registro esta sendo utilizado em outra tabela
     * @param id do modelo
     * @return verdadeiro para utilizacao em outra tabela
     */
    protected boolean isUtilizado(long id) {
        boolean utilizado = false;
        return utilizado;

    }
}
