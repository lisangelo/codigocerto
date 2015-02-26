/*
 * UICadastro.java
 *
 * Criado em 5 de Junho de 2007, 15:33
 *
 */

package br.com.codigocerto.swing.formularios;

import br.com.codigocerto.swing.GerenciadorAcoesDesktop;
import javax.swing.JPanel;

/**
 *
 * @author lis
 */
public abstract class UIMovimento extends UIEntradaDados {
    
    /** Cria uma nova instância de Cadastro */
    public UIMovimento() {
        incluirComponentes();
        configurarPesquisa();
    }
    
    /**
     *Adiciona painel
     *@param subPainel painel complementar para a area de dados do movimento
     */
    public void addPainel(JPanel subPainel) {
        PanelMovimento panelMovimento = new PanelMovimento();
        panelMovimento.addPainel(subPainel);
        ((PanelEntradaDados)_panel).addPainelMovimento(panelMovimento);
        validate();
        pack();
    }

    @Override
    protected void excluir() {
        if (Avisos.exibirPergunta(getParent(), "Excluir este movimento?")) {
            UIMensagem mensagem = new UIMensagem();
            mensagem.setMensagem("Excluindo movimento...");
            mensagem.visualizar();
            if (_entrada.excluir()) {
                irUltimo();
            }
            else {
                GerenciadorAcoesDesktop.exibirAviso(_entrada.descricaoErro());
            }
            mensagem.sair();
        }
    }
    
    @Override
    protected void incluirComponentes() {
        _panel = new PanelEntradaDados();
        super.incluirComponentes();
        add((PanelEntradaDados)_panel);
    }
    
} ///:~
