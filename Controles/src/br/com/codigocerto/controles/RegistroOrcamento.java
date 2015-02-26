/*
 * Codigo Certo
 * RegistroOrcamento.java
 * Criado em 1 de Outubro de 2007, 10:19
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.modelos.CNPJ;
import java.math.BigDecimal;
import java.util.Date;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.controles.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.ItemOrcamento;
import br.com.codigocerto.modelos.Orcamento;
import br.com.codigocerto.modelos.Produto;
import br.com.codigocerto.modelos.Terceiro;

/**
 * @author lis
 */
public class RegistroOrcamento extends Registro {
    
    public interface BD {
        String  ID = "cc_orcamentos_id", 
                DATA = "cc_orcamentos_data",
                TERCEIRO = "cc_orcamentos_terceiros_id",
                CONDICAO_PAGAMENTO = "cc_orcamentos_condicoespagamento_id",
                FATURAMENTO_MINIMO = "cc_orcamentos_faturamentominimo",
                NOME_CONTATO = "cc_orcamentos_nomecontato",
                VALIDADE = "cc_orcamentos_validade",
                VALOR_PRODUTOS = "cc_orcamentos_valorprodutos",
                VALOR_DESCONTO = "cc_orcamentos_valordesconto",
                VALOR_FRETE = "cc_orcamentos_valorfrete",
                TIPO_FRETE = "cc_orcamentos_tipofrete",
                VALOR_TOTAL = "cc_orcamentos_valortotal",
                OBS = "cc_orcamentos_observacao",
                SITUACAO = "cc_orcamentos_situacao",
                ENTREGA = "cc_orcamentos_entrega",
                MOTIVO_CANCELAMENTO = "cc_orcamentos_motivocancelamento",
                NOME_CLIENTE = "cc_orcamentos_nomecliente",
                CNPJ_CLIENTE = "cc_orcamentos_cnpjcliente";
    }
    
    private RegistroOrcamentoItem regItem = new RegistroOrcamentoItem();
    private RegistroTerceiro regTerceiro = new RegistroTerceiro();
    private RegistroCondicaoPagamento regCondicao = new RegistroCondicaoPagamento();
    private long _id = 0;
    
    /**
     * Criar nova instancia de RegistroOrcamento
     */
    public RegistroOrcamento() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        Orcamento modelo = (Orcamento) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.DATA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getData(), 
                                                            TiposDadosQuery.DATA)
                                                            );

            sql.insereColuna(BD.VALOR_DESCONTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorDesconto().toString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_FRETE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorFrete().toString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.TIPO_FRETE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipoFrete(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_PRODUTOS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorProdutos().toString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_TOTAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorTotal().toString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            conv.setValorBase(modelo.getCondicao().getId());
            sql.insereColuna(BD.CONDICAO_PAGAMENTO, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.FATURAMENTO_MINIMO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getFaturamentoMinimo().toString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.NOME_CONTATO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getContato(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.VALIDADE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValidade(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.OBS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getObs(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.SITUACAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSituacao(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ENTREGA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEntrega(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.MOTIVO_CANCELAMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getMotivo(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            String nomeCliente;
            String cnpjCliente;
            if (modelo.getCliente() != null) {
                conv.setValorBase(modelo.getCliente().getId());
                sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
                nomeCliente = modelo.getCliente().getNome();
                cnpjCliente = modelo.getCliente().getDocumentos().getCNPJ().getDocumento();
            } else {
                nomeCliente = modelo.getNomeCliente();
                cnpjCliente = modelo.getCnpjCliente().getDocumento();
            }
            sql.insereColuna(BD.NOME_CLIENTE, FormatacaoSQL.getDadoFormatado(
                                                            nomeCliente, 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CNPJ_CLIENTE, FormatacaoSQL.getDadoFormatado(
                                                            cnpjCliente, 
                                                            TiposDadosQuery.DATA)
                                                            );

            TransacaoBD trans = ServidorBD.getTransacao();
            
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                long idOrcamento = 0;
                if (modelo.getCliente() != null) {
                    idOrcamento = getId(trans, modelo.getCliente().getId());
                } else {
                    idOrcamento = getId(trans);
                }
                if (idOrcamento > 0) {
                    ok = insereProdutos(idOrcamento, modelo, trans);
                }
            }
            if (ok) {
                trans.commit();
            }
            else {
                trans.rollback();
            }
            trans.fechar();
        }
        
        return ok;
    }

    @Override
    public boolean setDadosAlteracao(Object dados) {
        boolean ok = false;
        Orcamento modelo = (Orcamento) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            BigDecimal valor;
            String valorFormatado;
            
            sql.insereColuna(BD.DATA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getData(), 
                                                            TiposDadosQuery.DATA)
                                                            );

            valor = modelo.getValorDesconto();
            if (valor != null) {
                valorFormatado = modelo.getValorDesconto().toString();
            } else {
                valorFormatado = "null";
            }
            sql.insereColuna(BD.VALOR_DESCONTO, FormatacaoSQL.getDadoFormatado(
                                                            valorFormatado, 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            valor = modelo.getValorFrete();
            if (valor != null) {
                valorFormatado = modelo.getValorFrete().toString();
            } else {
                valorFormatado = "null";
            }
            sql.insereColuna(BD.VALOR_FRETE, FormatacaoSQL.getDadoFormatado(
                                                            valorFormatado, 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.TIPO_FRETE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipoFrete(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_PRODUTOS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorProdutos().toString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.VALOR_TOTAL, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValorTotal().toString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            conv.setValorBase(modelo.getCondicao().getId());
            sql.insereColuna(BD.CONDICAO_PAGAMENTO, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.FATURAMENTO_MINIMO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getFaturamentoMinimo().toString(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.NOME_CONTATO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getContato(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.VALIDADE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getValidade(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.OBS, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getObs(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.SITUACAO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSituacao(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.ENTREGA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getEntrega(), 
                                                            TiposDadosQuery.DATA)
                                                            );
            sql.insereColuna(BD.MOTIVO_CANCELAMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getMotivo(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            String nomeCliente;
            String cnpjCliente;
            if (modelo.getCliente() != null) {
                conv.setValorBase(modelo.getCliente().getId());
                sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                                conv.getString(), 
                                                                TiposDadosQuery.NUMERO)
                                                                );
                nomeCliente = modelo.getCliente().getNome();
                cnpjCliente = modelo.getCliente().getDocumentos().getCNPJ().getDocumento();
            } else {
                nomeCliente = modelo.getNomeCliente();
                cnpjCliente = modelo.getCnpjCliente().getDocumento();
            }
            sql.insereColuna(BD.NOME_CLIENTE, FormatacaoSQL.getDadoFormatado(
                                                            nomeCliente, 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CNPJ_CLIENTE, FormatacaoSQL.getDadoFormatado(
                                                            cnpjCliente, 
                                                            TiposDadosQuery.DATA)
                                                            );

            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());

            TransacaoBD trans = ServidorBD.getTransacao();
            
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                ok = eliminaProdutos(modelo.getId(), trans);
                if (ok) {
                    ok = insereProdutos(modelo.getId(), modelo, trans);
                }
            }
            if (ok) {
                trans.commit();
            }
            else {
                trans.rollback();
            }
            trans.fechar();
        }
        
        return ok;
    }

    @Override
    public boolean excluir(long id) {
         boolean ok = false;
         
         TransacaoBD trans = ServidorBD.getTransacao();
         
         eliminaProdutos(id, trans);
         DeleteSqlBD sql = new DeleteSqlBD();
         sql.setTabela(ligacao.getTabela());
         sql.insereCondicao(ligacao.getCampoId() + " = " + id);
         if (trans.executaUpdate(sql.getQuery()) == 1) {
             ok = true;
         }
         
         return ok;
    }
    
    @Override
    public Orcamento getDados(long id) {
        Orcamento modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        regTerceiro.setTipoTerceiro(Terceiro.Tipo.CLIENTE);
        if (tabela.primeiro()) {
            ConversorTipos conv = new ConversorTipos();
            modelo = new Orcamento();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setData((Date) tabela.getColuna(BD.DATA));
            if (tabela.getColuna(BD.TERCEIRO) != null) {
                conv.setValorBase(tabela.getColuna(BD.TERCEIRO).toString());
                modelo.setTerceiro(regTerceiro.getDados(conv.getLong()));
            }
            if (tabela.getColuna(BD.CONDICAO_PAGAMENTO) != null) {
                conv.setValorBase(tabela.getColuna(BD.CONDICAO_PAGAMENTO).toString());
                modelo.setCondicao(regCondicao.getDados(conv.getLong()));
            }
            modelo.setContato(tabela.getColuna(BD.NOME_CONTATO).toString());
            modelo.setFaturamentoMinimo((BigDecimal)tabela.getColuna(BD.FATURAMENTO_MINIMO));
            conv.setValorBase(tabela.getColuna(BD.VALIDADE).toString());
            modelo.setValidade(conv.getInteger());
            modelo.setValorDesconto((BigDecimal)tabela.getColuna(BD.VALOR_DESCONTO));
            modelo.setValorFrete((BigDecimal)tabela.getColuna(BD.VALOR_FRETE));
            if (tabela.getColuna(BD.TIPO_FRETE) != null) {
                conv.setValorBase(tabela.getColuna(BD.TIPO_FRETE).toString());
                modelo.setTipoFrete(conv.getInteger());
            }
            modelo.setObs(tabela.getColuna(BD.OBS).toString());
            modelo.setEntrega((Date)tabela.getColuna(BD.ENTREGA));
            modelo.setSituacao(LerCampo.getInt(tabela.getColuna(BD.SITUACAO)));
            if (tabela.getColuna(BD.MOTIVO_CANCELAMENTO) != null) {
                modelo.setMotivo(tabela.getColuna(BD.MOTIVO_CANCELAMENTO).toString());
            }
            if (tabela.getColuna(BD.NOME_CLIENTE) != null) {
                modelo.setNomeCliente(tabela.getColuna(BD.NOME_CLIENTE).toString());
            }
            if (tabela.getColuna(BD.CNPJ_CLIENTE) != null) {
                CNPJ cnpj = new CNPJ(tabela.getColuna(BD.CNPJ_CLIENTE).toString());
                modelo.setCnpjCliente(cnpj);
            }
            leItens(modelo);
        }
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_orcamentos");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(Orcamento.Campos.ID);
        campo.setIU(Orcamento.Dicas.ID);
        estabelecerCampo(campo);
        /* Terceiro */
        campo = new Campo();
        campo.setBanco(BD.TERCEIRO);
        campo.setModelo(Orcamento.Campos.TERCEIRO);
        campo.setIU(Orcamento.Dicas.TERCEIRO);
        estabelecerCampo(campo);
        /* Condicao de pagto */
        campo = new Campo();
        campo.setBanco(BD.CONDICAO_PAGAMENTO);
        campo.setModelo(Orcamento.Campos.CONDICAO_PAGAMENTO);
        campo.setIU(Orcamento.Dicas.CONDICAO_PAGAMENTO);
        estabelecerCampo(campo);
        /* Contato */
        campo = new Campo();
        campo.setBanco(BD.NOME_CONTATO);
        campo.setModelo(Orcamento.Campos.NOME_CONTATO);
        campo.setIU(Orcamento.Dicas.NOME_CONTATO);
        estabelecerCampo(campo);
        /* Data */
        campo = new Campo();
        campo.setBanco(BD.DATA);
        campo.setModelo(Orcamento.Campos.DATA);
        campo.setIU(Orcamento.Dicas.DATA);
        estabelecerCampo(campo);
        /* Valor frete */
        campo = new Campo();
        campo.setBanco(BD.VALOR_FRETE);
        campo.setModelo(Orcamento.Campos.VALOR_FRETE);
        campo.setIU(Orcamento.Dicas.VALOR_FRETE);
        estabelecerCampo(campo);
        /* Tipo frete */
        campo = new Campo();
        campo.setBanco(BD.TIPO_FRETE);
        campo.setModelo(Orcamento.Campos.TIPO_FRETE);
        campo.setIU(Orcamento.Dicas.TIPO_FRETE);
        estabelecerCampo(campo);
        /* Valor de desconto */
        campo = new Campo();
        campo.setBanco(BD.VALOR_DESCONTO);
        campo.setModelo(Orcamento.Campos.VALOR_DESCONTO);
        campo.setIU(Orcamento.Dicas.VALOR_DESCONTO);
        estabelecerCampo(campo);
        /* Valor dos produtos */
        campo = new Campo();
        campo.setBanco(BD.VALOR_PRODUTOS);
        campo.setModelo(Orcamento.Campos.VALOR_PRODUTOS);
        campo.setIU(Orcamento.Dicas.VALOR_PRODUTOS);
        estabelecerCampo(campo);
        /* Valor total */
        campo = new Campo();
        campo.setBanco(BD.VALOR_TOTAL);
        campo.setModelo(Orcamento.Campos.VALOR_TOTAL);
        campo.setIU(Orcamento.Dicas.VALOR_TOTAL);
        estabelecerCampo(campo);
        /* Validade */
        campo = new Campo();
        campo.setBanco(BD.VALIDADE);
        campo.setModelo(Orcamento.Campos.VALIDADE);
        campo.setIU(Orcamento.Dicas.VALIDADE);
        estabelecerCampo(campo);
        /* Faturamento Minimo */
        campo = new Campo();
        campo.setBanco(BD.FATURAMENTO_MINIMO);
        campo.setModelo(Orcamento.Campos.FATURAMENTO_MINIMO);
        campo.setIU(Orcamento.Dicas.FATURAMENTO_MINIMO);
        estabelecerCampo(campo);
        /* Observacao */
        campo = new Campo();
        campo.setBanco(BD.OBS);
        campo.setModelo(Orcamento.Campos.OBS);
        campo.setIU(Orcamento.Dicas.OBS);
        estabelecerCampo(campo);
        /* Entrega */
        campo = new Campo();
        campo.setBanco(BD.ENTREGA);
        campo.setModelo(Orcamento.Campos.ENTREGA);
        campo.setIU(Orcamento.Dicas.ENTREGA);
        estabelecerCampo(campo);
        /* Situacao */
        campo = new Campo();
        campo.setBanco(BD.SITUACAO);
        campo.setModelo(Orcamento.Campos.SITUACAO);
        campo.setIU(Orcamento.Dicas.SITUACAO);
        estabelecerCampo(campo);
        /* Motivo */
        campo = new Campo();
        campo.setBanco(BD.MOTIVO_CANCELAMENTO);
        campo.setModelo(Orcamento.Campos.MOTIVO_CANCELAMENTO);
        campo.setIU(Orcamento.Dicas.MOTIVO_CANCELAMENTO);
        estabelecerCampo(campo);
        /* Nome do cliente */
        campo = new Campo();
        campo.setBanco(BD.NOME_CLIENTE);
        campo.setModelo(Orcamento.Campos.NOME_CLIENTE);
        campo.setIU(Orcamento.Dicas.NOME_CLIENTE);
        estabelecerCampo(campo);
        /* Cnpj do cliente */
        campo = new Campo();
        campo.setBanco(BD.CNPJ_CLIENTE);
        campo.setModelo(Orcamento.Campos.CNPJ_CLIENTE);
        campo.setIU(Orcamento.Dicas.CNPJ_CLIENTE);
        estabelecerCampo(campo);
   }
    
   private long getId(TransacaoBD trans, long idCliente) {
       long id = 0;
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereColuna(BD.ID, "id", "MAX");
       sql.insereCondicao(BD.TERCEIRO + " = " + idCliente);
       TabelaBD tabela = trans.executaQuery(sql.getQuery());
       if (tabela.primeiro()) {
           id = LerCampo.getLong(tabela.getColuna("id"));
       }
       
       return id;
   }
   
   private long getId(TransacaoBD trans) {
       long id = 0;
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereColuna(BD.ID, "id", "MAX");
       sql.insereCondicao("isnull(" + BD.TERCEIRO + ")");
       TabelaBD tabela = trans.executaQuery(sql.getQuery());
       if (tabela.primeiro()) {
           id = LerCampo.getLong(tabela.getColuna("id"));
       }
       
       return id;
   }

   private boolean insereProdutos(long idOrcamento, Orcamento orca, TransacaoBD trans) {
       boolean ok = true;
       InsertSqlBD sql;
       regItem.setTransacao(trans);
       regItem.setIdOrcamento(idOrcamento);
       int num = 0;
       
       while (ok && num < orca.getNumeroItens()) {
           ok = regItem.setDadosInsercao(orca.getItem(num));
           num++;
       }
       
       return ok;
   }

   private boolean eliminaProdutos(long idOrcamento, TransacaoBD trans) {
       boolean ok = false;
       
       regItem.setIdOrcamento(idOrcamento);
       regItem.setTransacao(trans);
       if (regItem.eliminaItensOrcamento() > 0) {
           ok = true;
       }
       
       return ok;
   }
   
   private void leItens(Orcamento orca) {
       
       regItem.setIdOrcamento(orca.getId());
       ItemOrcamento[] itens = regItem.getItensOrcamento();
       for (int i = 0; i < itens.length; i++) {
           orca.adicionaItem(itens[i]);
       }
       
   }
   
} ///~
