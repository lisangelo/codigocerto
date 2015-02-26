/*
 * Codigo Certo
 * RegistroTerceiroEndereco.java
 * Criado em 24 de Outubro de 2007, 14:43
 */

package br.com.codigocerto.controles;

import br.com.codigocerto.bancodados.*;
import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.modelos.Endereco;

/**
 * @author lis
 */
public class RegistroTerceiroEndereco extends RegistroFilho {
    
    public interface BD {
        String  ID = "cc_terceirosenderecos_id", 
                LOGRADOURO = "cc_terceirosenderecos_logradouro",
                NUMERO = "cc_terceirosenderecos_numero",
                COMPLEMENTO = "cc_terceirosenderecos_complemento",
                BAIRRO = "cc_terceirosenderecos_bairros_id",
                CEP = "cc_terceirosenderecos_cep",
                CIDADE = "cc_terceirosenderecos_cidades_id",
                UF = "cc_terceirosenderecos_estados_id",
                TIPO = "cc_terceirosenderecos_tipo_id",
                TERCEIRO = "cc_terceirosenderecos_terceiros_id";
    }
    
    private RegistroBairro _regBairro;
    private RegistroCidade _regCidade;
    private RegistroEstado _regEstado;

    /** Criar nova instancia de RegistroTerceiroEndereco */
    public RegistroTerceiroEndereco() {
        configurar();
    }

    @Override
    public int eliminaFilhos() {
        return eliminaFilhos(BD.TERCEIRO);
    }

    public Endereco[] getFilhos() {
       Endereco[] filhos = null;
        
       SelectSqlBD sql = new SelectSqlBD();
       sql.setTabela(ligacao.getTabela());
       sql.insereCondicao(BD.TERCEIRO + " = " + _idPai);
       TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
       Endereco filho;
       int num = 0;
       if (tabela.primeiro()) {
           filhos = new Endereco[tabela.getTotalRegistros()];
           do {
                filho = new Endereco();
                filho.setId(LerCampo.getLong(tabela.getColuna(BD.ID)));
                filho.setLogradouro(tabela.getColuna(BD.LOGRADOURO).toString());
                filho.setNumero(tabela.getColuna(BD.NUMERO).toString());
                filho.setComplemento(tabela.getColuna(BD.COMPLEMENTO).toString());
                filho.setCEP(tabela.getColuna(BD.CEP).toString());
                filho.setBairro(_regBairro.getDados(LerCampo.getLong(tabela.getColuna(BD.BAIRRO))));
                filho.setCidade(_regCidade.getDados(LerCampo.getLong(tabela.getColuna(BD.CIDADE))));
                filho.setUF(_regEstado.getDados(LerCampo.getLong(tabela.getColuna(BD.UF))));
                filho.setTipo(LerCampo.getInt(tabela.getColuna(BD.TIPO)));
                filhos[num] = filho;
                num++;
           }
           while (tabela.proximo());
       }
       tabela.fechar();
        
       return filhos;
    }

    public boolean setDadosInsercao(Object dados) {
        boolean ok = false;
        Endereco modelo = (Endereco) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            InsertSqlBD sql = new InsertSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.LOGRADOURO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getLogradouro(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NUMERO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumero(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CEP, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCEP(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.COMPLEMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getComplemento(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            conv.setValorBase(modelo.getBairro().getId());
            sql.insereColuna(BD.BAIRRO, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(),
                                                            TiposDadosQuery.NUMERO));
            conv.setValorBase(modelo.getCidade().getId());
            sql.insereColuna(BD.CIDADE, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(),
                                                            TiposDadosQuery.NUMERO));
            conv.setValorBase(modelo.getEstado().getId());
            sql.insereColuna(BD.UF, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(),
                                                            TiposDadosQuery.NUMERO));

            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    public boolean setDadosAlteracao(Object dados) {
        boolean ok = false;
        Endereco modelo = (Endereco) dados;
        
        if (modelo != null) {
            ConversorTipos conv = new ConversorTipos();
            UpdateSqlBD sql = new UpdateSqlBD();
            sql.setTabela(ligacao.getTabela());
            
            sql.insereColuna(BD.TERCEIRO, FormatacaoSQL.getDadoFormatado(
                                                            String.valueOf(_idPai), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            sql.insereColuna(BD.LOGRADOURO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getLogradouro(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.NUMERO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getNumero(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.CEP, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getCEP(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.COMPLEMENTO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getComplemento(), 
                                                            TiposDadosQuery.TEXTO)
                                                            );
            sql.insereColuna(BD.TIPO, FormatacaoSQL.getDadoFormatado(
                                                            modelo.getTipo(), 
                                                            TiposDadosQuery.NUMERO)
                                                            );
            conv.setValorBase(modelo.getBairro().getId());
            sql.insereColuna(BD.BAIRRO, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(),
                                                            TiposDadosQuery.NUMERO));
            conv.setValorBase(modelo.getCidade().getId());
            sql.insereColuna(BD.CIDADE, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(),
                                                            TiposDadosQuery.NUMERO));
            conv.setValorBase(modelo.getEstado().getId());
            sql.insereColuna(BD.UF, FormatacaoSQL.getDadoFormatado(
                                                            conv.getString(),
                                                            TiposDadosQuery.NUMERO));

            sql.insereCondicao(ligacao.getCampoId() + " = " + modelo.getId());
                                                                
            if (_trans.executaUpdate(sql.getQuery()) == 1) {
                ok = true;
            }
        }
        
        return ok;
    }

    public Endereco getDados(long id) {
        Endereco modelo = null;
        SelectSqlBD sql = new SelectSqlBD();
        sql.setTabela(ligacao.getTabela());
        sql.insereCondicao(ligacao.getCampoId() + " = " + id);
        TabelaBD tabela = ServidorBD.executaQuery(sql.getQuery());
        if (tabela.primeiro()) {
            modelo = new Endereco();
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(tabela.getColuna(BD.ID).toString());
            modelo.setId(conversor.getLong());
            modelo.setLogradouro(tabela.getColuna(BD.LOGRADOURO).toString());
            modelo.setNumero(tabela.getColuna(BD.NUMERO).toString());
            modelo.setComplemento(tabela.getColuna(BD.COMPLEMENTO).toString());
            modelo.setCEP(tabela.getColuna(BD.CEP).toString());
            modelo.setBairro(_regBairro.getDados(LerCampo.getLong(tabela.getColuna(BD.BAIRRO))));
            modelo.setCidade(_regCidade.getDados(LerCampo.getLong(tabela.getColuna(BD.CIDADE))));
            modelo.setUF(_regEstado.getDados(LerCampo.getLong(tabela.getColuna(BD.UF))));
            modelo.setTipo(LerCampo.getInt(tabela.getColuna(BD.TIPO)));
        }
        tabela.fechar();
        
        return modelo;
    }

    @Override
    protected void estabelecerLigacao() {
        if (this.ligacao == null) {
            ligacao = new Ligacao();
        }
        ligacao.setTabela("cc_terceirosenderecos");
        ligacao.setCampoId(BD.ID);
        
        estabelecerCampos();
    }

    protected void estabelecerCampos() {
        Campo campo;
        
        /* Id */
        campo = new Campo();
        campo.setBanco(BD.ID);
        campo.setModelo(Endereco.Campos.ID);
        campo.setIU(Endereco.Dicas.ID);
        estabelecerCampo(campo);
        /* Logradouro */
        campo = new Campo();
        campo.setBanco(BD.LOGRADOURO);
        campo.setModelo(Endereco.Campos.LOGRADOURO);
        campo.setIU(Endereco.Dicas.LOGRADOURO);
        estabelecerCampo(campo);
        /* Numero */
        campo = new Campo();
        campo.setBanco(BD.NUMERO);
        campo.setModelo(Endereco.Campos.NUMERO);
        campo.setIU(Endereco.Dicas.NUMERO);
        estabelecerCampo(campo);
        /* CEP */
        campo = new Campo();
        campo.setBanco(BD.CEP);
        campo.setModelo(Endereco.Campos.CEP);
        campo.setIU(Endereco.Dicas.CEP);
        estabelecerCampo(campo);
        /* Bairro */
        campo = new Campo();
        campo.setBanco(BD.BAIRRO);
        campo.setModelo(Endereco.Campos.BAIRRO);
        campo.setIU(Endereco.Dicas.BAIRRO);
        estabelecerCampo(campo);
        /* Cidade */
        campo = new Campo();
        campo.setBanco(BD.CIDADE);
        campo.setModelo(Endereco.Campos.CIDADE);
        campo.setIU(Endereco.Dicas.CIDADE);
        estabelecerCampo(campo);
        /* Uf */
        campo = new Campo();
        campo.setBanco(BD.UF);
        campo.setModelo(Endereco.Campos.UF);
        campo.setIU(Endereco.Dicas.UF);
        estabelecerCampo(campo);
        /* Tipo */
        campo = new Campo();
        campo.setBanco(BD.TIPO);
        campo.setModelo(Endereco.Campos.TIPO);
        campo.setIU(Endereco.Dicas.TIPO);
        estabelecerCampo(campo);
    }
    
    private void configurar() {
        _regCidade = new RegistroCidade();
        _regBairro = new RegistroBairro();
        _regEstado = new RegistroEstado();
    }
    
} ///~
