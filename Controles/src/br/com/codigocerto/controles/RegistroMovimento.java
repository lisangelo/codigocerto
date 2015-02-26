/*
 * Codigo Certo
 * RegistroMovimento.java
 * Criado em 16 de Julho de 2007, 14:51
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import java.util.ArrayList;

/**
 * @author lis
 */
public abstract class RegistroMovimento implements IRegistro {
    
    protected Ligacao ligacao;
    private ArrayList<Campo> listaCampos = new ArrayList<Campo>();

    public RegistroMovimento() {
        estabelecerLigacao();
    }
    
    /*
     *Envia objeto para insercao na base de dados
     *@param objeto a ser inserido na base
     *@return verdadeiro para sucesso na insercao
     */
    abstract public boolean setDadosInsercao(Object dados);

    /*
     *Envia objeto para alteracao na base de dados
     *@param objeto a ser alterado na base
     *@return verdadeiro para sucesso na alteracao
     */
    abstract public boolean setDadosAlteracao(Object dados);

    /*
     *Obtem dados da base
     *@param object com Id do modelo
     *@return objeto com dados retornados da base
     */ 
    abstract public Object getDados(Object id);
    
    /*
     *Metodo padrao para estabelecer as ligacoes entre o modelo e a tabela da base de dados
     */
    abstract protected void estabelecerLigacao();
    
    /*
     *Metodo padrao para estabelecer vinculos entre campos da tabela e propriedades da classe
     */
    abstract protected void estabelecerCampos();

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
     public boolean excluir(long id) {
         boolean ok = false;
         
         DeleteSqlBD sql = new DeleteSqlBD();
         sql.setTabela(ligacao.getTabela());
         sql.insereCondicao(ligacao.getCampoId() + " = " + id);
         
         if (ServidorBD.executaUpdate(sql.getQuery()) == 1) {
             ok = true;
         }
         
         return ok;
    }
     
    /**
     * Encontrar id de um registro baseado em uma chave de procura
     * @param chave string com chave de pesquisa
     * @return long id do registro encontrado, 0L para nao encontrado
     */
    protected long getId(String chave) {
        long id = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(ligacao.getCampoId(), "id");
        sql.insereCondicao(chave);
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        
        if (reg.primeiro() && reg.getColuna(0) != null) {
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(reg.getColuna(0).toString());
            id = conversor.getLong();
        }

        reg.fechar();
        
        return id;
    }
     
    public Object[][] procurarCampo(String nomeCampo, String chave, String campoDescricao) {
        Object[][] resultado = null;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(ligacao.getCampoId());
        sql.insereColuna(campoDescricao);
        sql.insereCondicao(nomeCampo + " LIKE " + chave);
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

        return resultado;
    }
    
    public String getTabela() {
        return ligacao.getTabela();
    }
 
    public String getCampoId() {
        return ligacao.getCampoId();
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
    
} ///~
