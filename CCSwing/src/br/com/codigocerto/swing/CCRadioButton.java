/*
 * Codigo Certo
 * CCRadioButton.java
 * @author lis
 * Criado em 29 de Outubro de 2007, 09:12
 */

package br.com.codigocerto.swing;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JRadioButton;

import br.com.codigocerto.swing.formularios.IPanelEntradaDados;

public class CCRadioButton extends JRadioButton {
    
    private boolean _alterado = false;
    private boolean _informarAlteracao = true;
    
    /**
     * Criar nova instancia de CCRadioButton
     */
    public CCRadioButton() {
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
            @Override
            public void focusGained(FocusEvent evt) {
                _alterado = false;
                mudaCorControle(CoresSistema.CHECK_SELECIONADO);
            }
            @Override
            public void focusLost(FocusEvent evt) {
                mudaCorControle(CoresSistema.CHECK_NORMAL);
            }
        });
        
        addActionListener(new AcaoClicar());
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
    
    class AcaoClicar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            _alterado = true;
            if (_informarAlteracao) {
                informarAlteracao();
            }
        }
    }

} ///~

