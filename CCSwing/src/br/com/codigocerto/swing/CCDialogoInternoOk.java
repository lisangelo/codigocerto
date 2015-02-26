/*
 * CCDialogoInternoOk.java
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
public abstract class CCDialogoInternoOk extends CCInternalFrame {
    
    private JPanel _panelDialogo;
    private boolean _ok = false;
    private StringBuilder _descricaoErro = new StringBuilder();
    private Recursos _recursos = new Recursos();
    private CCInternalFrame _origem;

    /**
     * Gerar nova instancia
     * @param origem - formulario pai do dialgo
     */
    public CCDialogoInternoOk(CCInternalFrame origem) {
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
     * Torna visivel o dialogo
     */
    public void visualizar() {
        setVisible(true);
    }

    /**
     * Inicia processo de encerramento verificando informacoes
     */
    public void sair() {
        fechar();
    }
    
    @Override
    protected void fechar() {
        _origem.removeFillho(this);
        super.fechar();
    }
    
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
        PanelOk panelOk = new PanelOk();
        panelBotoes.add(panelOk, BorderLayout.EAST);
        add(panelBotoes, BorderLayout.SOUTH);
        
        panelOk.botaoOk().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sair();
            }
        });
        
    }
    
} ///:~