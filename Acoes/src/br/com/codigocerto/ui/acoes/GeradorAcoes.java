/*
 * Codigo Certo
 * GeradorAcoes.java
 * Criado em 29 de Junho de 2007, 14:45
 */

package br.com.codigocerto.ui.acoes;

import br.com.codigocerto.acesso.UsuarioAtivo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.codigocerto.swing.*;
import br.com.codigocerto.ui.swing.cadastros.*;
import br.com.codigocerto.ui.swing.financeiro.cadastros.*;
import br.com.codigocerto.controles.*;
import br.com.codigocerto.modelos.Formulario;

/**
 * @author lis
 */
public class GeradorAcoes {
    
    private int _form;

    /** Criar nova instancia de GeradorAcoes */
    public GeradorAcoes() {
    }
    
    /**
     * Ajusta form que ira ser visualizado
     * @param form int com identificacao do form
     */
    public void setForm(int form) {
        _form = form;
    }
    
    /**
     * Retorna uma acao que ira exibir o form selecionado
     * @return ActionListener
     */
    public ActionListener getAcaoExibirForm() {
        class AcaoForm implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (UsuarioAtivo.getPermissao(Formulario.Tipo.CADASTRO, _form, false)) {
                    CCInternalFrame frameInterno = configurar();
                    GerenciadorAcoesDesktop.exibir(frameInterno);
                } else {
                    GerenciadorAcoesDesktop.exibirAviso("Acesso restrito!");
                }
            }
        }
        
        return new AcaoForm();
    } 
    
    private CCInternalFrame configurar() {
        switch (_form) {
            case CadastrosId.ORIGEM_MERCADORIA:
                return new UICadastroOrigemMercadoria();
            case CadastrosId.TRIBUTACAO_ICMS:
                return new UICadastroTributacoesIcms();
            case CadastrosId.USUARIO:
                return new UICadastroUsuario();
            case CadastrosId.GRUPO_TRIBUTARIO:
                return new UICadastroGrupoTributario();
            case CadastrosId.UNIDADE_MEDIDA:
                return new UICadastroUnidadeMedida();
            case CadastrosId.PRODUTO:
                return new UICadastroProduto();
            case CadastrosId.ESTADO:
                return new UICadastroEstado();
            case CadastrosId.CIDADE:
                return new UICadastroCidade();
            case CadastrosId.BAIRRO:
                return new UICadastroBairro();
            case CadastrosId.TERCEIRO:
                return new UICadastroTerceiro();
            case CadastrosId.CLIENTE:
                return new UICadastroCliente();
            case CadastrosId.FORNECEDOR:
                return new UICadastroFornecedor();
            case CadastrosId.TRANSPORTADORA:
                return new UICadastroTransportadora();
            case CadastrosId.CONDICAO_PAGAMENTO:
                return new UICadastroCondicaoPagamento();
            case CadastrosId.FUNCIONARIO:
                return new UICadastroFuncionario();
            case CadastrosId.CFOP:
                return new UICadastroCFOP();
            case CadastrosId.OPERACAO_FISCAL:
                return new UICadastroOperacaoFiscal();
            case CadastrosId.TOTALIZADOR_NAOSUJEITO_ICMS:
                return new UICadastroTotalizadorNaoSujeitoIcms();
            case CadastrosId.MOEDA:
                return new UICadastroMoeda();
            case CadastrosId.BANCO:
                return new UICadastroBanco();
            case CadastrosId.PORTADOR:
                return new UICadastroPortador();
        }
        
        return null;
    }
    
} ///:~
