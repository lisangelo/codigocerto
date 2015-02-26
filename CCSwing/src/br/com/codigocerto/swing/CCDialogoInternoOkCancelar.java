/*
 * CCDialogoOkCancelar.java
 *
 * Criado em 23 de Maio de 2007, 18:50
 *
 */

package br.com.codigocerto.swing;

import br.com.codigocerto.configuracao.Recursos;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author lis
 */
public abstract class CCDialogoInternoOkCancelar extends CCInternalFrame {
    
    private JPanel _panelDialogo;
    private boolean _ok = false;
    private StringBuilder _descricaoErro = new StringBuilder();
    private Recursos _recursos = new Recursos();
    private CCInternalFrame _origem;

    /**
     * Gerar nova instancia
     * @param origem - formulario pai do dialgo
     */
    public CCDialogoInternoOkCancelar(CCInternalFrame origem) {
        _origem = origem;
        _origem.adicionaFilho(this);
        incluirComponentes();
    }
    
    /**
     * Informa o formulario pai do dialogo
     * @return formulario pai
     */
    protected CCInternalFrame getOrigem() {
        return _origem;
    }

    /**
     *Insere painel acima dos botoes para edicao
     */
    protected void inserirPanelDialogo(JPanel panel) {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        _panelDialogo = panel;
        panelSuperior.add(_panelDialogo, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);
    }
    
    /**
     * Fornece uma referencia ao objeto panel utilizado
     * @return JPanel utilizado
     */
    protected JPanel getPanelDialogo() {
        return _panelDialogo;
    }
    
    /**
     * Informa se usuario confirmou operacao
     * @return verdadeiro para confirmado
     */
    public boolean isOk() {
        return _ok;
    }
    
    /**
     * Torna visivel o dialogo
     */
    public void visualizar() {
        setVisible(true);
    }

    /**
     * Informa o que houve de errado ao confirmar operacao
     * @return string com descricao do erro
     */
    public String getDescricaoErro() {
        String descricao = _descricaoErro.toString();
        _descricaoErro.delete(0, _descricaoErro.length());
        return descricao;
    }

    /**
     * Registra o que houve de errado ao confirmar operacao
     * @param string com descricao do erro
     */
    protected void setDescricaoErro(String descricao) {
        _descricaoErro.append(descricao);
    }
    
    /**
     * Inicia processo de encerramento verificando informacoes
     */
    public void sair() {
        if (verificarOk()) {
            _ok = true;
            fechar();
        }
    }
    
    /**
     * Cancela operacao 
     */
    protected void cancelar() {
        fechar();
    }
    
    protected void fechar() {
        _origem.removeFillho(this);
        super.fechar();
    }
    
    /**
     * Verifica consistencia de dados informados
     * @return verdadeiro para dados ok
     */
    protected abstract boolean verificarOk() ;

    protected void alterarFoco(String nomeComponente) {
        try {
            Component comp;
            int i = 0;
            int numComponentes = _panelDialogo.getComponentCount();
            do {
                comp = _panelDialogo.getComponent(i);
                if (comp.getName() != null && comp.getName().equals(nomeComponente)) {
                    i = numComponentes;
                    comp.requestFocusInWindow();
                }
                i++;
            }
            while (i < numComponentes);
        } 
        catch (ArrayIndexOutOfBoundsException e) {
            ControleExcecoes.capturarExcecao(e);
        }
    }
    
    private void incluirComponentes() {
        JPanel panelBotoes = new JPanel(new BorderLayout());
        PanelOkCancelar panelOkCancelar = new PanelOkCancelar();
        panelBotoes.add(panelOkCancelar, BorderLayout.EAST);
        add(panelBotoes, BorderLayout.SOUTH);
        
        panelOkCancelar.botaoCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cancelar();
            }
        });
        
        panelOkCancelar.botaoOk().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                sair();
            }
        });
        
    }
    
} ///:~