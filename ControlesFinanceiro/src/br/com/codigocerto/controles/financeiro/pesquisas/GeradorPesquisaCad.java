/*
 * Codigo Certo
 * GeradorPesquisaCad.java
 * Criado em 27 de Junho de 2007, 11:20
 */

package br.com.codigocerto.controles.financeiro.pesquisas;

import br.com.codigocerto.controles.pesquisas.*;
import br.com.codigocerto.cadastros.PesquisarCadastro;
import br.com.codigocerto.controles.*;
import br.com.codigocerto.controles.financeiro.RegistroBanco;
import br.com.codigocerto.controles.financeiro.RegistroPortador;

/**
 * @author lis
 */
public class GeradorPesquisaCad {
    
    interface Larguras {
        int ID = 10, 
            NOME = 299,
            CODIGO = 10,
            LOGIN = 12,
            DATA = 36,
            DOCUMENTO = 40,
            DESCRICAO = 600;
    }
    
    private int _cadastro = 0;
    private Registro _registro;
    private String _nomeCadastro;
    private PesquisarCadastro _pesquisarCadastro;
    private String _campoDescricao;
    private int _campoId;
    private int _campoInicial;
    private String[] _titulos;
    private int[] _larguras;
    
    /** Criar nova instancia de GeradorPesquisaCad */
    public GeradorPesquisaCad() {
    }

    /**
     * Criar nova instancia de GeradorPesquisaCad
     * @param int com identificacao do cadastro
     */
    public GeradorPesquisaCad(int cadastro) {
        _cadastro = cadastro;
        configurar();
    }
    
    /**
     * Ajusta cadastro que ira ser pesquisado
     * @param int com identificacao do cadastro
     */
    public void setCadastro(int cadastro) {
        _cadastro = cadastro;
        configurar();
    }
    
    /**
     * Retorna um modelo de pesquisa baseado no cadastro informado
     * @return ModeloPesquisaCad
     */
    public IModeloPesquisaCad getModeloPesquisa() {
        class ModeloPesquisaCad implements IModeloPesquisaCad {
            public String getNomeCadastro() {
                return _nomeCadastro;
            }

            public Registro getRegistro() {
                return _registro;
            }

            public PesquisarCadastro getPesquisarCadastro() {
                return _pesquisarCadastro;
            }

            public String getCampoDescricao() {
                return _campoDescricao;
            }

            public int getCampoId() {
                return _campoId;
            }

            public int getCampoInicial() {
                return _campoInicial;
            }

            public String[] getTitulosColunas() {
                return _titulos;
            }

            public int[] getLarguraColunas() {
                return _larguras;
            }

            public int getCadastroId() {
                return _cadastro;
            }
            
        }
        
        return new ModeloPesquisaCad();
    } 
    
    private void configurar() {
        switch (_cadastro) {
            case CadastrosId.PORTADOR:
                configurarPortador();
                break;
            case CadastrosId.BANCO:
                configurarBanco();
                break;
        }
    }
    
    private void configurarPortador() {
        _registro = new RegistroPortador();
        _campoDescricao = RegistroPortador.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroPortador.BD.ID,
                RegistroPortador.BD.NOME
            });
        _pesquisarCadastro.setTabela("cc_portadores");
        _nomeCadastro = "Portador";
        _titulos = new String[] {
            "Código", "Nome"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME};
    }

    private void configurarBanco() {
        _registro = new RegistroBanco();
        _campoDescricao = RegistroBanco.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroBanco.BD.ID, 
                RegistroBanco.BD.NOME,
                RegistroBanco.BD.CODIGO
            });
        _pesquisarCadastro.setTabela("cc_bancos");
        _nomeCadastro = "Bancos";
        _titulos = new String[] {
            "Código", "Nome", "Código"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.ID};
    }

} ///~
