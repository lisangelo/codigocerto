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
import br.com.codigocerto.ui.swing.movimentos.*;
import br.com.codigocerto.controles.*;
import br.com.codigocerto.modelos.Formulario;

/**
 * @author lis
 */
public class GeradorAcoesMov {
    
    private int _form;

    /** Criar nova instancia de GeradorAcoes */
    public GeradorAcoesMov() {
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
                if (UsuarioAtivo.getPermissao(Formulario.Tipo.MOVIMENTO, _form, false)) {
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
            case MovimentosId.LISTA_PRECOS:
                return new UIMovimentoListaPrecos();
            case MovimentosId.PEDIDO:
                return new UIMovimentoPedido();
            case MovimentosId.MOEDA_COTACOES:
                return new UIMoedaCotacoes();
        }
        
        return null;
    }
    
} ///:~
