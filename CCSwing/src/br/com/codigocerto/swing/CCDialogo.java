/*
 * CCDialogo.java
 *
 * Criado em 23 de Maio de 2007, 11:35
 *
 */

package br.com.codigocerto.swing;

import java.awt.Window;
import javax.swing.*;

/**
 *
 * @author lis
 */
public abstract class CCDialogo extends CCDialog {
    
    private boolean _ok = false;
    private StringBuilder _descricaoErro = new StringBuilder();
    
    /** Cria uma nova instância de CCDialogo */
    public CCDialogo() {
        setAlwaysOnTop(true);
        setModal(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setResizable(false);
    }
    public CCDialogo(Window owner, String titulo, ModalityType tipo) {
        super(owner, titulo, tipo);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setResizable(false);
    }
    
    /**
     * Informa se usuario confirmou operacao
     * @return verdadeiro para confirmado
     */
    public boolean isOk() {
        return _ok;
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
    
    /**
     * Verifica consistencia de dados informados
     * @return verdadeiro para dados ok
     */
    protected abstract boolean verificarOk() ;

    private void fechar() {
        this.dispose();
    }
    
} ///:~
