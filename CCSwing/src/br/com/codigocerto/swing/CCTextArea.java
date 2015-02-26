/*
 * Codigo Certo 
 * CCTextArea.java
 * Criado em 18 de Junho de 2007, 11:14
 */

package br.com.codigocerto.swing;

import br.com.codigocerto.swing.formularios.IPanelEntradaDados;
import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextArea;

/**
 * @author lis
 */
public class CCTextArea  extends JTextArea {
    
    private boolean _informarAlteracao = true;
    private boolean _alterado = false;
    private String _textoAnterior = null;

    /** Criar nova instancia de CCTextArea */
    public CCTextArea() {
        configurar();
    }

    /**
     *Informa se campo teve seu conteudo alterado
     *@return boolean
     */
    public boolean isAlterado() {
        return _alterado;
    }
    
    /**
     * Configurar acao de informar alteracao
     * @param verdadeiro para informar toda vez que campo for alterado (default)
     */
    public void setInformarAlteracao(boolean informar) {
        _informarAlteracao = informar;
    }

    private void configurar() {
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                _alterado = false;
                _textoAnterior = getText();
                mudaCorControle(CoresSistema.CAMPO_SELECIONADO);
            }
            public void focusLost(FocusEvent evt) {
                mudaCorControle(CoresSistema.CAMPO_NORMAL);
                if (!getText().equals(_textoAnterior)) {
                    _alterado = true;
                    if (_informarAlteracao) {
                        informarAlteracao();
                    }
                }
            }
        });
    }
    
    private void mudaCorControle(int padrao) {
        setBackground(CoresSistema.corPadrao(padrao));
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
    
    
} ///~
