/*
 * Codigo Certo
 * RegistroTerceiro.java
 * @author lis
 * Criado em 25 de Outubro de 2007, 09:55
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.Contato;
import br.com.codigocerto.modelos.DocumentosTerceiro;
import br.com.codigocerto.modelos.Email;
import br.com.codigocerto.modelos.Endereco;
import br.com.codigocerto.modelos.Telefone;
import br.com.codigocerto.modelos.Terceiro;

public class RegistroTerceiro extends Registro {
    
    public interface BD {
        String  ID = "cc_terceiros_id",
                NOME = "cc_terceiros_nome",
                NOME_FANTASIA = "cc_terceiros_fantasia",
                SITE = "cc_terceiros_site",
                TIPO = "cc_terceiros_tipo_id";
    }
    
    private String _view;
    private int _tipoTerceiro = Terceiro.Tipo.CLIENTE;

    private RegistroTerceiroContato regContato = new RegistroTerceiroContato();
    private RegistroTerceiroDocumentos regDocumentos = new RegistroTerceiroDocumentos();
    private RegistroTerceiroEmail regEmail = new RegistroTerceiroEmail();
    private RegistroTerceiroEndereco regEndereco = new RegistroTerceiroEndereco();
    private RegistroTerceiroTelefone regFone = new RegistroTerceiroTelefone();
    private long _id = 0;
    
    /** Criar nova instancia de RegistroTerceiro */
    public RegistroTerceiro() {
    }

    @Override
    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        Terceiro modelo = (Terceiro) dados;
        
        if (modelo != null) {
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.ID, String.valueOf(modelo.getId()));
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NOME_FANTASIA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNomeFantasia(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.SITE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSite(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );

            TransacaoBD trans = ServidorBD.getTransacao();
            
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                    ok = true;
                    insereDocumentos(modelo, trans);
                    insereEnderecos(modelo, trans);
                    insereFones(modelo, trans);
                    insereEmails(modelo, trans);
                    insereContatos(modelo, trans);
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
        boolean ok = true;
        Terceiro modelo = (Terceiro) dados;
        
        if (modelo != null) {
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.NOME, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNome(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NOME_FANTASIA, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNomeFantasia(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.SITE, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getSite(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );

            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());

            TransacaoBD trans = ServidorBD.getTransacao();
            
            if (trans.executaUpdate(sql.getQuery()) == 1) {
                eliminaDocumentos(modelo.getId(), trans);
                insereDocumentos(modelo, trans);
                if (ok) {
                    eliminaEnderecos(modelo.getId(), trans);
                    insereEnderecos(modelo, trans);
                    eliminaFones(modelo.getId(), trans);
                    insereFones(modelo, trans);
                    eliminaEmails(modelo.getId(), trans);
                    insereEmails(modelo, trans);
                    eliminaContatos(modelo.getId(), trans);
                    insereContatos(modelo, trans);
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

         if (!existeMovimentacao(id)) {
             TransacaoBD trans = ServidorBD.getTransacao();

             eliminaEnderecos(id, trans);
             eliminaDocumentos(id, trans);
             eliminaFones(id, trans);
             eliminaEmails(id, trans);
             eliminaContatos(id, trans);
             DeleteSqlBD sql = new DeleteSqlBD();
             sql.setTabela(ligacao.getTabela());
             sql.insereCondicao(ligacao.getCampoId() + " = " + id);
             if (trans.executaUpdate(sql.getQuery()) == 1) {
                 ok = true;
                 trans.commit();
             } else {
                 trans.rollback();
             }
         }
         
         return ok;
    }

    protected  boolean existeMovimentacao(long id) {
        boolean existe = false;

        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela("cc_notasfiscais");
        sql.insereCondicao("cc_notasfiscais_terceiros_id = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            existe = true;
        }
        tabela.fechar();

        return existe;
    }

    @Override
    public Terceiro getDados(long id) {
        Terceiro modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        if (_tipoTerceiro > 0) {
            sql.insereCondicao(BD.TIPO + " = " + _tipoTerceiro, "AND");
        }
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new Terceiro();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setNome(tabela.getColuna(BD.NOME).toString());
            modelo.setNomeFantasia(tabela.getColuna(BD.NOME_FANTASIA).toString());
            if (tabela.getColuna(BD.SITE) != null) {
                modelo.setSite(tabela.getColuna(BD.SITE).toString());
            }
            modelo.setTipo(LerCampo.getInt(tabela.getColuna(BD.TIPO)));

            leEnderecos(modelo);
            leDocumentos(modelo);
            leFones(modelo);
            leEmails(modelo);
            leContatos(modelo);
        }
        tabela.fechar();
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_terceiros");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(Terceiro.Campos.ID);
        campo.setIU(Terceiro.Dicas.ID);
        estabelecerCampo(campo);
        /* Nome */
        campo = new Campo();
        campo.setBanco(BD.NOME);
        campo.setModelo(Terceiro.Campos.NOME);
        campo.setIU(Terceiro.Dicas.NOME);
        estabelecerCampo(campo);
        /* Fantasia */
        campo = new Campo();
        campo.setBanco(BD.NOME_FANTASIA);
        campo.setModelo(Terceiro.Campos.NOME_FANTASIA);
        campo.setIU(Terceiro.Dicas.NOME_FANTASIA);
        estabelecerCampo(campo);
        /* Site */
        campo = new Campo();
        campo.setBanco(BD.SITE);
        campo.setModelo(Terceiro.Campos.SITE);
        campo.setIU(Terceiro.Dicas.SITE);
        estabelecerCampo(campo);
        /* Tipo */
        campo = new Campo();
        campo.setBanco(BD.TIPO);
        campo.setModelo(Terceiro.Campos.TIPO);
        campo.setIU(Terceiro.Dicas.TIPO);
        estabelecerCampo(campo);
    }
    
    /**
     * Configurar tipo de terceiro a ser utilizado
     * @param int com tipo de terceiro
     */
    public void setTipoTerceiro(int tipo) {
        _tipoTerceiro = tipo;
    }
    
    /**
     * Configurar view a ser utilizado
     * @param string com nome da tabela de view
     */
    public void setView(String view) {
        _view = view;
    }

    /*
     *Buscar posicao do ultimo registro
     *@return long id
     */
    @Override
    public long buscarUltimo() {
        long ultimoId = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(_view);
        sql.insereColuna(BD.ID, "id", "max");
        sql.insereCondicao(BD.TIPO + " = " + _tipoTerceiro);
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
     *Buscar posicao do ultimo registro sem considerar tipos
     *@return long id
     */
    public long buscarUltimoGeral() {
        long ultimoId = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereColuna(BD.ID, "id", "max");
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
    @Override
    public long buscarPrimeiro() {
        
        long primeiroId = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(_view);
        sql.insereColuna(BD.ID, "id", "min");
        sql.insereCondicao(BD.TIPO + " = " + _tipoTerceiro);
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
    @Override
    public long buscarProximo(long id) {
        long proximoId = 0L;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(_view);
        sql.insereColuna(BD.ID);
        sql.insereCondicao(BD.ID + " > " + id);
        sql.insereCondicao(BD.TIPO + " = " + _tipoTerceiro, "AND");
        sql.insereOrdem(BD.ID + " limit 1");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());

        if (reg.primeiro() && reg.getColuna(0) != null) {
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(reg.getColuna(0).toString());
            proximoId = conversor.getLong();
        }
        
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
        sql.setTabela("cc_view_terceiros");
        sql.insereColuna(BD.ID);
        sql.insereCondicao(BD.ID + " < " + id);
        sql.insereCondicao(BD.TIPO + " = " + _tipoTerceiro, "AND");
        sql.insereOrdem("-" + BD.ID + " limit 1");
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());

        if (reg.primeiro() && reg.getColuna(0) != null) {
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(reg.getColuna(0).toString());
            anteriorId = conversor.getLong();
        }
        
        return anteriorId;
    }
  
    /**
     * Busca dados de um terceiro a partir do CNPJ
     * @param documento - CNPJ sem formatacaoe
     * @return
     */
    public Terceiro buscarCNPJ(String documento) {
        Terceiro terceiro = null;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela("cc_view_terceiros");
        sql.insereColuna(BD.ID);
        sql.insereCondicao(RegistroTerceiroDocumentos.BD.CNPJ + " = " + documento);
        System.out.println("Localizando CNPJ " + documento);
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());

        if (reg.primeiro() && reg.getColuna(0) != null) {
            terceiro = getDados(LerCampo.getLong(reg.getColuna(0)));
            if (terceiro != null) {
                System.out.println(terceiro.getNome());
            }
        }
        
        return terceiro;
    }
    
    /**
     * Busca dados de um terceiro a partir do CPF
     * @param documento - CPF sem formatacao
     * @return
     */
    public Terceiro buscarCPF(String documento) {
        Terceiro terceiro = null;
        
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela("cc_view_terceiros");
        sql.insereColuna(BD.ID);
        sql.insereCondicao(RegistroTerceiroDocumentos.BD.CPF + " = " + documento);
        System.out.println("Localizando CPF " + documento);
        TabelaBD reg = ServidorBD.executaQuery(sql.getQuery());

        if (reg.primeiro() && reg.getColuna(0) != null) {
            terceiro = getDados(LerCampo.getLong(reg.getColuna(0)));
            if (terceiro != null) {
                System.out.println(terceiro.getNome());
            }
        }
        
        return terceiro;
    }

    protected boolean insereDocumentos(Terceiro terceiro, TransacaoBD trans) {
       boolean ok = true;
       InsertSqlBD sql;
       regDocumentos.setTransacao(trans);
       regDocumentos.setIdPai(terceiro.getId());
       
       ok = regDocumentos.setDadosInsercao(terceiro.getDocumentos());
       
       return ok;
   }
    
   protected boolean insereEnderecos(Terceiro terceiro, TransacaoBD trans) {
       boolean ok = true;
       InsertSqlBD sql;
       regEndereco.setTransacao(trans);
       regEndereco.setIdPai(terceiro.getId());
       int num = 0;
       
       while (ok && num < terceiro.getNumeroEnderecos()) {
           ok = regEndereco.setDadosInsercao(terceiro.getEndereco(num));
           num++;
       }
       
       return ok;
   }
   
   protected boolean insereFones(Terceiro terceiro, TransacaoBD trans) {
       boolean ok = true;
       InsertSqlBD sql;
       regFone.setTransacao(trans);
       regFone.setIdPai(terceiro.getId());
       int num = 0;
       
       while (ok && num < terceiro.getNumeroFones()) {
           ok = regFone.setDadosInsercao(terceiro.getFone(num));
           num++;
       }
       
       return ok;
   }

   protected boolean insereEmails(Terceiro terceiro, TransacaoBD trans) {
       boolean ok = true;
       InsertSqlBD sql;
       regEmail.setTransacao(trans);
       regEmail.setIdPai(terceiro.getId());
       int num = 0;
       
       while (ok && num < terceiro.getNumeroEmails()) {
           ok = regEmail.setDadosInsercao(terceiro.getEmail(num));
           num++;
       }
       
       return ok;
   }

   protected boolean insereContatos(Terceiro terceiro, TransacaoBD trans) {
       boolean ok = true;
       InsertSqlBD sql;
       regContato.setTransacao(trans);
       regContato.setIdPai(terceiro.getId());
       int num = 0;
       
       while (ok && num < terceiro.getNumeroContatos()) {
           ok = regContato.setDadosInsercao(terceiro.getContato(num));
           num++;
       }
       
       return ok;
   }
   
   protected boolean eliminaDocumentos(long id, TransacaoBD trans) {
       boolean ok = false;
       
       regDocumentos.setIdPai(id);
       regDocumentos.setTransacao(trans);
       if (regDocumentos.eliminaFilhos() > 0) {
           ok = true;
       }
       
       return ok;
   }
   
   protected boolean eliminaEnderecos(long id, TransacaoBD trans) {
       boolean ok = false;
       
       regEndereco.setIdPai(id);
       regEndereco.setTransacao(trans);
       if (regEndereco.eliminaFilhos() > 0) {
           ok = true;
       }
       
       return ok;
   }
   
   protected boolean eliminaFones(long id, TransacaoBD trans) {
       boolean ok = false;
       
       regFone.setIdPai(id);
       regFone.setTransacao(trans);
       if (regFone.eliminaFilhos() > 0) {
           ok = true;
       }
       
       return ok;
   }
   
   protected boolean eliminaEmails(long id, TransacaoBD trans) {
       boolean ok = false;
       
       regEmail.setIdPai(id);
       regEmail.setTransacao(trans);
       if (regEmail.eliminaFilhos() > 0) {
           ok = true;
       }
       
       return ok;
   }
   
   protected boolean eliminaContatos(long id, TransacaoBD trans) {
       boolean ok = false;
       
       regContato.setIdPai(id);
       regContato.setTransacao(trans);
       if (regContato.eliminaFilhos() > 0) {
           ok = true;
       }
       
       return ok;
   }
   
   protected void leEnderecos(Terceiro terceiro) {
       
       regEndereco.setIdPai(terceiro.getId());
       Endereco[] itens = regEndereco.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               terceiro.adicionaEndereco(itens[i]);
           }
       }
   }
   
   protected void leEmails(Terceiro terceiro) {
       
       regEmail.setIdPai(terceiro.getId());
       Email[] itens = regEmail.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               terceiro.adicionaEmail(itens[i]);
           }
       }
   }
   
   protected void leFones(Terceiro terceiro) {
       
       regFone.setIdPai(terceiro.getId());
       Telefone[] itens = regFone.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               terceiro.adicionaFone(itens[i]);
           }
       }
   }
   
   protected void leContatos(Terceiro terceiro) {
       
       regContato.setIdPai(terceiro.getId());
       Contato[] itens = regContato.getFilhos();
       if (itens != null) {
           for (int i = 0; i < itens.length; i++) {
               terceiro.adicionaContato(itens[i]);
           }
       }
   }
   
   protected void leDocumentos(Terceiro terceiro) {
       
       regDocumentos.setIdPai(terceiro.getId());
       DocumentosTerceiro[] itens = regDocumentos.getFilhos();
       
       /* o documento apesar de poder receber mais de um registro
        * vai trabalhar inicialmente com apenas um
        */
       if (itens != null) {
            terceiro.setDocumentos(itens[0]);
       }
   }

} ///:~
