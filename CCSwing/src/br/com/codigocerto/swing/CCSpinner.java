/*
 * Codigo Certo
 * CCSpinner.java
 * Criado em 2 de Agosto de 2007, 10:11
 */

package br.com.codigocerto.swing;

import br.com.codigocerto.swing.formularios.IPanelEntradaDados;
import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSpinner;

/**
 * @author lis
 */
public class CCSpinner extends JSpinner {
    
    private boolean _alterado = false;
    private String _textoAnterior = null;
    
    /** Criar nova instancia de CCSpinner */
    public CCSpinner() {
        configurar();
    }
    
    /**
     *Informa se campo teve seu conteudo alterado
     *@return boolean
     */
    public boolean isAlterado() {
        return _alterado;
    }
    
    private void configurar() {
        
        // ao receber ou perder foco alterar cor de fundo
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                _alterado = false;
                _textoAnterior = getValue().toString();
                mudaCorControle(CoresSistema.CAMPO_SELECIONADO);
            }
            public void focusLost(FocusEvent evt) {
                mudaCorControle(CoresSistema.CAMPO_NORMAL);
                if (!getValue().toString().equals(_textoAnterior)) {
                    _alterado = true;
                    informarAlteracao();
                }
            }

        });

        // verificar teclas digitadas de acordo com mascara
        addKeyListener(new MyKeyListener());
    }

    private void informarAlteracao() {

        final int NIVEIS_CONTAINERS = 10;

        Container painel = getParent();
        int nivel = NIVEIS_CONTAINERS;
        while ( ! (painel instanceof IPanelEntradaDados) && nivel > 0) {
            painel = painel.getParent();
            nivel--;
        }
        if (nivel > 0) {
            IPanelEntradaDados panelCadastro = (IPanelEntradaDados) painel;
            if ( ! panelCadastro.getAlterado()) {
                // Se form nao estiver em estado de alteracao, ativar estado
                panelCadastro.setAlterado(true);
            }
        }
    }
    
    private void mudaCorControle(int padrao) {
        setBackground(CoresSistema.corPadrao(padrao));
    }
    
    class MyKeyListener extends KeyAdapter {
        /* verifica se tecla Enter foi acionada */
        public void keyReleased(KeyEvent evt) {
            if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                evt.consume();
                transferFocus();
            }
        }
 
    }
    
} ///~
