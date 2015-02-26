/*
 * Codigo Certo
 * GeradorPesquisaCad.java
 * Criado em 27 de Junho de 2007, 11:20
 */

package br.com.codigocerto.controles.pesquisas;

import br.com.codigocerto.cadastros.PesquisarCadastro;
import br.com.codigocerto.controles.*;
import br.com.codigocerto.modelos.Terceiro;

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
            @Override
            public String getNomeCadastro() {
                return _nomeCadastro;
            }

            @Override
            public Registro getRegistro() {
                return _registro;
            }

            @Override
            public PesquisarCadastro getPesquisarCadastro() {
                return _pesquisarCadastro;
            }

            @Override
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

            @Override
            public int[] getLarguraColunas() {
                return _larguras;
            }

            @Override
            public int getCadastroId() {
                return _cadastro;
            }
            
        }
        
        return new ModeloPesquisaCad();
    } 
    
    private void configurar() {
        switch (_cadastro) {
            case CadastrosId.ORIGEM_MERCADORIA:
                configurarOrigemMercadoria();
                break;
            case CadastrosId.TRIBUTACAO_ICMS:
                configurarTributacaoICMS();
                break;
            case CadastrosId.USUARIO:
                configurarUsuario();
                break;
            case CadastrosId.GRUPO_TRIBUTARIO:
                configurarGrupoTributario();
                break;
            case CadastrosId.UNIDADE_MEDIDA:
                configurarUnidadeMedida();
                break;
            case CadastrosId.PRODUTO:
                configurarProduto();
                break;
            case CadastrosId.LISTA_PRECOS:
                configurarListaPrecos();
                break;
            case CadastrosId.ESTADO:
                configurarEstado();
                break;
            case CadastrosId.CIDADE:
                configurarCidade();
                break;
            case CadastrosId.BAIRRO:
                configurarBairro();
                break;
            case CadastrosId.CLIENTE:
                configurarCliente();
                break;
            case CadastrosId.FORNECEDOR:
                configurarFornecedor();
                break;
            case CadastrosId.TRANSPORTADORA:
                configurarTransportadora();
                break;
            case CadastrosId.TERCEIRO:
                configurarTerceiro();
                break;
            case CadastrosId.ORCAMENTO:
                configurarOrcamento();
                break;
            case CadastrosId.CONDICAO_PAGAMENTO:
                configurarCondicaoPagamento();
                break;
            case CadastrosId.FUNCIONARIO:
                configurarFuncionario();
                break;
            case CadastrosId.CFOP:
                configurarCFOP();
                break;
            case CadastrosId.OPERACAO_FISCAL:
                configurarOperacaoFiscal();
                break;
            case CadastrosId.TOTALIZADOR_NAOSUJEITO_ICMS:
                configurarTotalizadorNaoSujeitoIcms();
                break;
            case CadastrosId.MOEDA:
                configurarMoeda();
                break;
        }
    }
    
    private void configurarOrigemMercadoria() {
        _registro = new RegistroOrigemMercadoria();
        _campoDescricao = RegistroOrigemMercadoria.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroOrigemMercadoria.BD.ID, 
                RegistroOrigemMercadoria.BD.NOME, 
                RegistroOrigemMercadoria.BD.CODIGO
            });
        _pesquisarCadastro.setTabela(_registro.getTabela());
        _nomeCadastro = "Origem";
        _titulos = new String[] {
            "Código", 
            "Descrição", 
            "CST"
        };
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.CODIGO};
    }

    private void configurarTributacaoICMS() {
        _registro = new RegistroTributacaoIcms();
        _campoDescricao = RegistroTributacaoIcms.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroTributacaoIcms.BD.ID, 
                RegistroTributacaoIcms.BD.NOME, 
                RegistroTributacaoIcms.BD.CODIGO
            });
        _pesquisarCadastro.setTabela(_registro.getTabela());
        _nomeCadastro = "Tributação ICMS";
        _titulos = new String[] {
            "Código", 
            "Descrição", 
            "CST"
        };
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.CODIGO};
    }
    
    private void configurarUsuario() {
        _registro = new RegistroUsuario();
        _campoDescricao = RegistroUsuario.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroUsuario.BD.ID, 
                RegistroUsuario.BD.NOME, 
                RegistroUsuario.BD.LOGIN  
            });
        _pesquisarCadastro.setTabela(_registro.getTabela());
        _nomeCadastro = "Usuário";
        _titulos = new String[] {
            "Código", 
            "Nome", 
            "Login"
        };
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.LOGIN};
    }
    
    private void configurarGrupoTributario() {
        _registro = new RegistroGrupoTributario();
        _campoDescricao = RegistroGrupoTributario.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroGrupoTributario.BD.ID, 
                RegistroGrupoTributario.BD.NOME
            });
        _pesquisarCadastro.setTabela(_registro.getTabela());
        _nomeCadastro = "Grupo Tributário";
        _titulos = new String[] {
            "Código", 
            "Descrição" 
        };
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME};
    }

    private void configurarUnidadeMedida() {
        _registro = new RegistroUnidadeMedida();
        _campoDescricao = RegistroUnidadeMedida.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroUnidadeMedida.BD.ID, 
                RegistroUnidadeMedida.BD.NOME,
                RegistroUnidadeMedida.BD.SIGLA
            });
        _pesquisarCadastro.setTabela(_registro.getTabela());
        _nomeCadastro = "Unidades de Medida";
        _titulos = new String[] {
            "Código", 
            "Descrição",
            "Sigla"
        };
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.CODIGO};
    }

    private void configurarProduto() {
        _registro = new RegistroProduto();
        _campoDescricao = RegistroProduto.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroProduto.BD.ID, 
                RegistroProduto.BD.NOME,
                RegistroProduto.BD.CODIGO
            });
        _pesquisarCadastro.setTabela(_registro.getTabela());
        _nomeCadastro = "Produtos";
        _titulos = new String[] {
            "Código", 
            "Descrição",
            "Código Interno"
        };
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.DESCRICAO, Larguras.CODIGO};
    }
    
    private void configurarListaPrecos() {
        _registro = new RegistroListaPrecos();
        _campoDescricao = RegistroListaPrecos.BD.DATA_INICIAL;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroListaPrecos.BD.ID,
                RegistroListaPrecos.BD.DATA_INICIAL
            });
        _pesquisarCadastro.setTabela("cc_view_listaprecos");
        _nomeCadastro = "Lista de Preços";
        _titulos = new String[] {
            "Código",
            "Data"
        };
        _campoId = 0; //id
        _campoInicial = 1; // data
        _larguras = new int[] {Larguras.ID, Larguras.DATA};
    }

    private void configurarEstado() {
        _registro = new RegistroEstado();
        _campoDescricao = RegistroEstado.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroEstado.BD.ID, 
                RegistroEstado.BD.NOME,
                RegistroEstado.BD.SIGLA
            });
        _pesquisarCadastro.setTabela(_registro.getTabela());
        _nomeCadastro = "Estados";
        _titulos = new String[] {
            "Código", 
            "Nome",
            "Sigla"
        };
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.CODIGO};
    }
    
    private void configurarCidade() {
        _registro = new RegistroCidade();
        _campoDescricao = RegistroCidade.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroCidade.BD.ID, 
                RegistroCidade.BD.NOME,
                RegistroEstado.BD.SIGLA
            });
        _pesquisarCadastro.setTabela("cc_view_cidades");
        _nomeCadastro = "Cidades";
        _titulos = new String[] {
            "Código", 
            "Nome",
            "UF"
        };
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.CODIGO};
    }

    private void configurarBairro() {
        _registro = new RegistroBairro();
        _campoDescricao = RegistroBairro.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroBairro.BD.ID, 
                RegistroBairro.BD.NOME
            });
        _pesquisarCadastro.setTabela(_registro.getTabela());
        _nomeCadastro = "Bairros";
        _titulos = new String[] {
            "Código", 
            "Nome"
        };
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME};
    }

    private void configurarCliente() {
        _registro = new RegistroTerceiro();
        _campoDescricao = RegistroTerceiro.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroTerceiro.BD.ID, 
                RegistroTerceiro.BD.NOME,
                RegistroEstado.BD.SIGLA,
                "cc_cidades_nome",
                RegistroTerceiro.BD.NOME_FANTASIA,
                RegistroTerceiroDocumentos.BD.CNPJ,
                RegistroTerceiroDocumentos.BD.IDENTIDADE,
                RegistroTerceiroDocumentos.BD.CPF
            });
        _pesquisarCadastro.setTabela("cc_view_clientes");
        _nomeCadastro = "Clientes";
        _titulos = new String[] {
            "Código", "Nome", "UF", "Cidade", "Nome Fantasia", "CNPJ", "Identidade", "CPF"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.ID, Larguras.NOME, Larguras.NOME,
                                 Larguras.DOCUMENTO, Larguras.DOCUMENTO, Larguras.DOCUMENTO};
    }

    private void configurarFornecedor() {
        _registro = new RegistroTerceiro();
        _campoDescricao = RegistroTerceiro.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroTerceiro.BD.ID, 
                RegistroTerceiro.BD.NOME,
                RegistroEstado.BD.SIGLA,
                RegistroTerceiro.BD.NOME_FANTASIA,
                RegistroTerceiroDocumentos.BD.CNPJ,
                RegistroTerceiroDocumentos.BD.IDENTIDADE,
                RegistroTerceiroDocumentos.BD.CPF
            });
        _pesquisarCadastro.setTabela("cc_view_fornecedores");
        _nomeCadastro = "Fornecedores";
        _titulos = new String[] {
            "Código", "Nome", "UF", "Nome Fantasia", "CNPJ", "Identidade", "CPF"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.ID, Larguras.NOME,
                                 Larguras.DOCUMENTO, Larguras.DOCUMENTO, Larguras.DOCUMENTO};
    }

    private void configurarTransportadora() {
        _registro = new RegistroTerceiro();
        ((RegistroTerceiro)_registro).setTipoTerceiro(Terceiro.Tipo.TRANSPORTADORA);
        _campoDescricao = RegistroTerceiro.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroTerceiro.BD.ID, 
                RegistroTerceiro.BD.NOME,
                RegistroEstado.BD.SIGLA,
                RegistroTerceiro.BD.NOME_FANTASIA,
                RegistroTerceiroDocumentos.BD.CNPJ,
                RegistroTerceiroDocumentos.BD.IDENTIDADE,
                RegistroTerceiroDocumentos.BD.CPF
            });
        _pesquisarCadastro.setTabela("cc_view_transportadoras");
        _nomeCadastro = "Transportadoras";
        _titulos = new String[] {
            "Código", "Nome", "UF", "Nome Fantasia", "CNPJ", "Identidade", "CPF"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.ID, Larguras.NOME,
                                 Larguras.DOCUMENTO, Larguras.DOCUMENTO, Larguras.DOCUMENTO};
    }

    private void configurarTerceiro() {
        _registro = new RegistroTerceiro();
        _campoDescricao = RegistroTerceiro.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroTerceiro.BD.ID, 
                RegistroTerceiro.BD.NOME,
                RegistroEstado.BD.SIGLA,
                RegistroTerceiro.BD.NOME_FANTASIA,
                RegistroTerceiroDocumentos.BD.CNPJ,
                RegistroTerceiroDocumentos.BD.IDENTIDADE,
                RegistroTerceiroDocumentos.BD.CPF
            });
        _pesquisarCadastro.setTabela("cc_view_terceiros");
        _nomeCadastro = "Terceiros";
        _titulos = new String[] {
            "Código", "Nome", "UF", "Nome Fantasia", "CNPJ", "Identidade", "CPF"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.ID, Larguras.NOME,
                                 Larguras.DOCUMENTO, Larguras.DOCUMENTO, Larguras.DOCUMENTO};
    }

    private void configurarOrcamento() {
        _registro = new RegistroOrcamento();
        _campoDescricao = RegistroOrcamento.BD.NOME_CLIENTE;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroOrcamento.BD.ID, 
                RegistroOrcamento.BD.NOME_CLIENTE,
            });
        _pesquisarCadastro.setTabela("cc_orcamentos");
        _nomeCadastro = "Orçamentos";
        _titulos = new String[] {
            "Número", "Nome"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME};
    }

    private void configurarCondicaoPagamento() {
        _registro = new RegistroCondicaoPagamento();
        _campoDescricao = RegistroCondicaoPagamento.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroCondicaoPagamento.BD.ID, 
                RegistroCondicaoPagamento.BD.NOME,
            });
        _pesquisarCadastro.setTabela("cc_condicoespagamento");
        _nomeCadastro = "Condições de Pagamento";
        _titulos = new String[] {
            "Código", "Nome"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME};
    }

    private void configurarFuncionario() {
        _registro = new RegistroFuncionario();
        _campoDescricao = RegistroFuncionario.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroFuncionario.BD.ID, 
                RegistroFuncionario.BD.NOME,
            });
        _pesquisarCadastro.setTabela("cc_funcionarios");
        _nomeCadastro = "Funcionários";
        _titulos = new String[] {
            "Código", "Nome"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME};
    }

    private void configurarCFOP() {
        _registro = new RegistroCFOP();
        _campoDescricao = RegistroCFOP.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroCFOP.BD.ID, 
                RegistroCFOP.BD.NOME,
                RegistroCFOP.BD.OPERACAO,
                RegistroCFOP.BD.DETALHE
            });
        _pesquisarCadastro.setTabela("cc_cfop");
        _nomeCadastro = "CFOP";
        _titulos = new String[] {
            "Código", "Nome", "Operação", "Detalhe"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.CODIGO, Larguras.CODIGO};
    }

    private void configurarOperacaoFiscal() {
        _registro = new RegistroOperacaoFiscal();
        _campoDescricao = RegistroOperacaoFiscal.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroOperacaoFiscal.BD.ID, 
                RegistroOperacaoFiscal.BD.NOME,
                RegistroCFOP.BD.OPERACAO,
            });
        _pesquisarCadastro.setTabela("cc_view_operacoesfiscais");
        _nomeCadastro = "Operação Fiscal";
        _titulos = new String[] {
            "Código", "Nome", "Operação"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME, Larguras.CODIGO};
    }

    private void configurarTotalizadorNaoSujeitoIcms() {
        _registro = new RegistroTotalizadorNaoSujeitoIcms();
        _campoDescricao = RegistroTotalizadorNaoSujeitoIcms.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroTotalizadorNaoSujeitoIcms.BD.ID, 
                RegistroTotalizadorNaoSujeitoIcms.BD.NOME
            });
        _pesquisarCadastro.setTabela("cc_totalizadoresnaosujeitoicms");
        _nomeCadastro = "Totalizador Não-Sujeito ICMS";
        _titulos = new String[] {
            "Código", "Nome"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME};
    }

    private void configurarMoeda() {
        _registro = new RegistroMoeda();
        _campoDescricao = RegistroMoeda.BD.NOME;
        _pesquisarCadastro = new PesquisarCadastro();
        _pesquisarCadastro.setCampoPesquisa(_campoDescricao);
        _pesquisarCadastro.setColunas(new String[] {
                RegistroMoeda.BD.ID, 
                RegistroMoeda.BD.NOME
            });
        _pesquisarCadastro.setTabela("cc_moedas");
        _nomeCadastro = "Moeda";
        _titulos = new String[] {
            "Código", "Nome"};
        _campoId = 0; //id
        _campoInicial = 1; // nome
        _larguras = new int[] {Larguras.ID, Larguras.NOME};
    }
    
} ///:~
