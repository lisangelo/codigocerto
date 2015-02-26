/*
 * Codigo Certo
 * RegistroListaPrecos.java
 * Criado em 13 de Julho de 2007, 09:38
 */

package br.com.codigocerto.controles;

import java.math.BigDecimal;
import java.util.Date;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.ListaPrecos;

/**
 * @author lis
 */
public class RegistroListaPrecos extends Registro {

    private final BigDecimal ZERO = new BigDecimal(0);
    private final String VIEW = "cc_view_listaprecos";
    
    public interface BD {
        String  ID = "cc_listaprecos_id",
                DATA_INICIAL = "cc_listaprecos_datainicio", 
                HORA_INICIAL = "cc_listaprecos_horainicio",
                DATA_FINAL = "cc_listaprecos_datafim",
                HORA_FINAL = "cc_listaprecos_horafim",
                PRODUTO = "cc_listaprecos_produtos_id",
                VALOR = "cc_listaprecos_valor"; 
    }
    
    /** Criar nova instancia de RegistroListaPrecos */
    public RegistroListaPrecos() {
        estabelecerLigacao();
    }

    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        ListaPrecos modelo = (ListaPrecos) dados;

        if (modelo != null) {
            verificarProdutos(modelo);
            InsertSqlBD sql;
            ConversorTipos conv = new ConversorTipos();
            
            String dataInicial = 
                    FormatacaoSQL.getDadoFormatado(modelo.getDataInicial(), TiposDadosQuery.DATA);
            String dataFinal = 
                    FormatacaoSQL.getDadoFormatado(modelo.getDataFinal(), TiposDadosQuery.DATA);
            int horaInicial = modelo.getHoraInicial(); 
            int horaFinal = modelo.getHoraFinal(); 
            TransacaoBD trans = ServidorBD.getTransacao();
            
            for (int i = 0; i < modelo.getTotalProdutos(); i++) {
                if (modelo.getPreco(i).compareTo(ZERO) > 0) {
                    sql = new InsertSqlBD();
                    sql.setTabela(ligacao.getTabela());
                    sql.insereColuna(BD.DATA_INICIAL, dataInicial);
                    sql.insereColuna(BD.DATA_FINAL, dataFinal);
                    sql.insereColuna(BD.HORA_INICIAL, FormatacaoSQL.getDadoFormatado(
                                                                    horaInicial, 
                                                                    TiposDadosQuery.NUMERO)
                                                                    );
                    sql.insereColuna(BD.HORA_FINAL, FormatacaoSQL.getDadoFormatado(
                                                                    horaFinal, 
                                                                    TiposDadosQuery.NUMERO)
                                                                    );
                    conv.setValorBase(modelo.getProduto(i).getId());
                    sql.insereColuna(BD.PRODUTO, FormatacaoSQL.getDadoFormatado(
                                                               conv.getString(), 
                                                               TiposDadosQuery.NUMERO)
                                                               );
                    sql.insereColuna(BD.VALOR, FormatacaoSQL.getDadoFormatado(
                                                               modelo.getPreco(i), 
                                                               TiposDadosQuery.NUMERO)
                                                               );
                    if (trans.executaUpdate(sql.getQuery()) == 1) {
                        ok = true;
                    } else {
                        ok = false;
                    }
                }
                
            }
            if (ok) {
                trans.commit();
                modelo.setId(getId(modelo.getDataInicial()));
            } else {
                trans.rollback();
            }
            trans.fechar();

        }
        else {
            ok = false;
        }

        return ok;
   }

    public boolean setDadosAlteracao(Object dados) {
        boolean ok = false;
        ListaPrecos modelo = (ListaPrecos) dados;

        if (modelo != null) {
            verificarProdutos(modelo);
            InsertSqlBD sql;
            ConversorTipos conv = new ConversorTipos();
            
            String dataInicial = 
                    FormatacaoSQL.getDadoFormatado(modelo.getDataInicial(), TiposDadosQuery.DATA);
            String dataFinal = 
                    FormatacaoSQL.getDadoFormatado(modelo.getDataFinal(), TiposDadosQuery.DATA);
            int horaInicial = modelo.getHoraInicial(); 
            int horaFinal = modelo.getHoraFinal(); 
            TransacaoBD trans = ServidorBD.getTransacao();
            
            DeleteSqlBD delete = new DeleteSqlBD();
            delete.setTabela(ligacao.getTabela());
            delete.insereCondicao(BD.DATA_INICIAL + " = " + dataInicial);
            trans.executaUpdate(delete.getQuery());
            
            for (int i = 0; i < modelo.getTotalProdutos(); i++) {
                if (modelo.getPreco(i).compareTo(ZERO) > 0) {
                    sql = new InsertSqlBD();
                    sql.setTabela(ligacao.getTabela());
                    sql.insereColuna(BD.DATA_INICIAL, dataInicial);
                    sql.insereColuna(BD.DATA_FINAL, dataFinal);
                    sql.insereColuna(BD.HORA_INICIAL, FormatacaoSQL.getDadoFormatado(
                                                                    horaInicial, 
                                                                    TiposDadosQuery.NUMERO)
                                                                    );
                    sql.insereColuna(BD.HORA_FINAL, FormatacaoSQL.getDadoFormatado(
                                                                    horaFinal, 
                                                                    TiposDadosQuery.NUMERO)
                                                                    );
                    conv.setValorBase(modelo.getProduto(i).getId());
                    sql.insereColuna(BD.PRODUTO, FormatacaoSQL.getDadoFormatado(
                                                               conv.getString(), 
                                                               TiposDadosQuery.NUMERO)
                                                               );
                    sql.insereColuna(BD.VALOR, FormatacaoSQL.getDadoFormatado(
                                                               modelo.getPreco(i), 
                                                               TiposDadosQuery.NUMERO)
                                                               );
                    if (trans.executaUpdate(sql.getQuery()) == 1) {
                        ok = true;
                    } else {
                        ok = false;
                    }
                }
                
            }
            if (ok) {
                trans.commit();
                modelo.setId(getId(modelo.getDataInicial()));
            } else {
                trans.rollback();
            }
            trans.fechar();
        }
        else {
            ok = false;
        }

        return ok;
    }

    public ListaPrecos getDados(long id) {
        
        ListaPrecos modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.getTotalRegistros() > 0) {
            modelo = getDados(tabela.getColuna(BD.DATA_INICIAL));
        }
        
        return modelo;
    }        
    
    public ListaPrecos getDados(Object id) {
        ListaPrecos modelo = null;
        Date inicio = (Date) id;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(BD.DATA_INICIAL + " = " 
                + FormatacaoSQL.getDadoFormatado(inicio, TiposDadosQuery.DATA));
        sql.insereOrdem(BD.PRODUTO);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.getTotalRegistros() > 0) {
            tabela.primeiro();
            modelo = new ListaPrecos();
            modelo.setDataInicial(inicio);
            modelo.setHoraInicial((Integer)tabela.getColuna(BD.HORA_INICIAL));
            modelo.setDataFinal((Date) tabela.getColuna(BD.DATA_FINAL));
            modelo.setHoraFinal((Integer)tabela.getColuna(BD.HORA_FINAL));
            long idProduto = 0L;
            RegistroProduto regProd = new RegistroProduto();
            do {
                idProduto = LerCampo.getLong(tabela.getColuna(BD.PRODUTO));
                if (idProduto > 0) {
                    modelo.addPreco(regProd.getDados(idProduto), 
                                    (BigDecimal)tabela.getColuna(BD.VALOR));
                }
            } while(tabela.proximo());
            
        }
        verificarProdutos(modelo);
        
        return modelo;
    }

    /**
     * Retorna lista de produtos em condicao valida para serem incluidos em lista de precos
     * @return matriz de string com valor, nome do produto, unidade e codigo
     */
    public ListaPrecos getProdutos() {

        ListaPrecos listaVazia = new ListaPrecos();
        verificarProdutos(listaVazia);
        
        return listaVazia;
    }
    
    
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_listaprecos");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(ListaPrecos.Campos.ID);
        campo.setIU(ListaPrecos.Dicas.ID);
        estabelecerCampo(campo);
        /* Data inicial */
        campo = new Campo();
        campo.setBanco(BD.DATA_INICIAL);
        campo.setModelo(ListaPrecos.Campos.DATA_INICIAL);
        campo.setIU(ListaPrecos.Dicas.DATA_INICIAL);
        estabelecerCampo(campo);
        /* Data final */
        campo = new Campo();
        campo.setBanco(BD.DATA_FINAL);
        campo.setModelo(ListaPrecos.Campos.DATA_FINAL);
        campo.setIU(ListaPrecos.Dicas.DATA_FINAL);
        estabelecerCampo(campo);
        /* Hora inicial */
        campo = new Campo();
        campo.setBanco(BD.HORA_INICIAL);
        campo.setModelo(ListaPrecos.Campos.HORA_INICIAL);
        campo.setIU(ListaPrecos.Dicas.HORA_INICIAL);
        estabelecerCampo(campo);
        /* Hora final */
        campo = new Campo();
        campo.setBanco(BD.HORA_FINAL);
        campo.setModelo(ListaPrecos.Campos.HORA_FINAL);
        campo.setIU(ListaPrecos.Dicas.HORA_FINAL);
        estabelecerCampo(campo);
        /* Produto */
        campo = new Campo();
        campo.setBanco(BD.PRODUTO);
        campo.setModelo(ListaPrecos.Campos.PRODUTO);
        campo.setIU(ListaPrecos.Dicas.PRODUTO);
        estabelecerCampo(campo);
        /* Valor */
        campo = new Campo();
        campo.setBanco(BD.VALOR);
        campo.setModelo(ListaPrecos.Campos.VALOR);
        campo.setIU(ListaPrecos.Dicas.VALOR);
        estabelecerCampo(campo);
    }
    
    /*
     *Buscar posicao do ultimo registro
     *@return String[] com dados para ultimo registro encontrado
     */
    public long buscarUltimo() {
        long ultimoId = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(VIEW);
        sql.insereColuna(BD.ID, "id");
        sql.insereOrdem("-" + BD.DATA_INICIAL + " limit 1");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        
        if (reg.primeiro() && reg.getColuna(0) != null) {
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(reg.getColuna(0).toString());
            ultimoId = conversor.getLong();
        }
        
        return ultimoId;
    }    
    
    /*
     *Buscar posicao do primeiro registro
     *@return id para primeiro registro encontrado
     */
    public long buscarPrimeiro() {
        
        long primeiroId = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(VIEW);
        sql.insereColuna(BD.ID, "id");
        sql.insereOrdem(BD.DATA_INICIAL + " limit 1");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        
        if (reg.primeiro() && reg.getColuna(0) != null) {
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(reg.getColuna(0).toString());
            primeiroId = conversor.getLong();
        }
        
        return primeiroId;
    }
   
    /*
     *Buscar posicao do proximo registro
     *@param Id do registro referencia
     *@return id para proximo registro encontrado
     */
    public long buscarProximo(long id) {
        long proximoId = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(VIEW);
        sql.insereColuna(BD.DATA_INICIAL, "data");
        sql.insereCondicao(BD.ID + " = " + id);
        sql.insereOrdem(BD.DATA_INICIAL + " limit 1");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        if (reg.primeiro() && reg.getColuna(0) != null) {
            sql = new SelectSqlBD();
            sql.setTabela(VIEW);
            sql.insereColuna(BD.ID);
            Date data = (Date) reg.getColuna(0);
            sql.insereCondicao(BD.DATA_INICIAL + " > " + FormatacaoSQL.getDadoFormatado(data, 
                                                            TiposDadosQuery.DATA));
            sql.insereOrdem(BD.DATA_INICIAL + " limit 1");
            reg = ServidorBD.executaQuery(sql.getQuery());

            if (reg.primeiro() && reg.getColuna(0) != null) {
                ConversorTipos conversor = new ConversorTipos();
                conversor.setValorBase(reg.getColuna(0).toString());
                proximoId = conversor.getLong();
            }
        }        
        
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
        sql.setTabela(VIEW);
        sql.insereColuna(BD.DATA_INICIAL, "data");
        sql.insereCondicao(BD.ID + " = " + id);
        sql.insereOrdem("-" + BD.DATA_INICIAL + " limit 1");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        if (reg.primeiro() && reg.getColuna(0) != null) {
            sql = new SelectSqlBD();
            sql.setTabela(VIEW);
            sql.insereColuna(BD.ID);
            sql.insereColuna(BD.DATA_INICIAL);
            Date data = (Date) reg.getColuna(0);
            sql.insereCondicao(BD.DATA_INICIAL + " < " + FormatacaoSQL.getDadoFormatado(data, 
                                                            TiposDadosQuery.DATA));
            sql.insereOrdem("-" + BD.DATA_INICIAL + " limit 1");
            reg = ServidorBD.executaQuery(sql.getQuery());

            if (reg.primeiro() && reg.getColuna(0) != null) {
                ConversorTipos conversor = new ConversorTipos();
                conversor.setValorBase(reg.getColuna(0).toString());
                anteriorId = conversor.getLong();
            }
        }        
        
        return anteriorId;
    }
    
    /**
     * Informa id atual apos ultima insercao ou alteracao
     * @param Date data da ultima movimentacao
     * @return long id
     */
    public long getId(Date data) {
        long id = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(VIEW);
        sql.insereColuna(BD.ID);
        sql.insereCondicao(BD.DATA_INICIAL + " = " + FormatacaoSQL.getDadoFormatado(data, 
                                                        TiposDadosQuery.DATA));
        sql.insereOrdem(BD.DATA_INICIAL + " limit 1");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());

        if (reg.primeiro() && reg.getColuna(0) != null) {
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(reg.getColuna(0).toString());
            id = conversor.getLong();
        }
        
        return id;
    }

     public boolean excluir(long id) {
         boolean ok = false;
         
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(VIEW);
        sql.insereColuna(BD.DATA_INICIAL, "data");
        sql.insereCondicao(BD.ID + " = " + id);
        sql.insereOrdem("-" + BD.DATA_INICIAL + " limit 1");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());
        if (reg.primeiro() && reg.getColuna(0) != null) {
            DeleteSqlBD sqlDelete = new DeleteSqlBD();
            sqlDelete.setTabela(ligacao.getTabela());
            Date data = (Date) reg.getColuna(0);
            sqlDelete.insereCondicao(BD.DATA_INICIAL + " = " + FormatacaoSQL.getDadoFormatado(data, 
                                                            TiposDadosQuery.DATA));
             if (ServidorBD.executaUpdate(sqlDelete.getQuery()) >= 1) {
                 ok = true;
             }
        }        
         
         return ok;
    }
     
    
    private void verificarProdutos(ListaPrecos modelo) {
        RegistroProduto regProduto = new RegistroProduto();
        String diaHoje = FormatacaoSQL.getDadoFormatado(new Date(), TiposDadosQuery.DATA);
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(regProduto.ligacao.getTabela());
        sql.insereColuna(RegistroProduto.BD.ID, "id");
        sql.insereCondicao(RegistroProduto.BD.VALIDADE_INICIO + " <= " + diaHoje);
        sql.insereCondicao("(" + RegistroProduto.BD.VALIDADE_FIM 
                  + " <= " + diaHoje + " OR " + RegistroProduto.BD.VALIDADE_FIM + " is null)", "AND");
        
        TabelaBD tabProdutos = ServidorBD.executaQuery(sql.getQuery());
        if (tabProdutos.primeiro()) {
            ConversorTipos conv = new ConversorTipos();
            do {
                conv.setValorBase(tabProdutos.getColuna("id").toString());
                long id = conv.getLong();
                if (!modelo.isIncluido(id)) {
                    BigDecimal preco = precoVigente(id);
                    incluirProduto(modelo, id, preco);
                }
            }
            while (tabProdutos.proximo());
        }
    }
    
    private void incluirProduto(ListaPrecos modelo, long id, BigDecimal preco) {
        RegistroProduto regProd = new RegistroProduto();
        modelo.addPreco(regProd.getDados(id), preco);
    }
    
    private BigDecimal precoVigente(long idProduto) {
        BigDecimal preco = ZERO;
        ConversorTipos conv = new ConversorTipos();
        conv.setValorBase(idProduto);
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.VALOR, "valor");
        sql.insereCondicao(BD.PRODUTO + " = " + 
                FormatacaoSQL.getDadoFormatado(conv.getString(), TiposDadosQuery.NUMERO));
        sql.insereOrdem("-" + BD.DATA_INICIAL + " limit 1");
        
        TabelaBD tabPrecos = ServidorBD.executaQuery(sql.getQuery());
        if (tabPrecos.primeiro()) {
            preco = (BigDecimal) tabPrecos.getColuna("valor");
            if (preco == null) {
                preco = ZERO;
            }
        }
        
        return preco;
    }
    
} ///~
