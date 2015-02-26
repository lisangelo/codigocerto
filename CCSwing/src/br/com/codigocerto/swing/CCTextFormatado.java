/*
 * CCTextFormatado.java
 *
 * Criado em 5 de Junho de 2007, 13:24
 *
 */

package br.com.codigocerto.swing;

import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFormattedTextField;

import br.com.codigocerto.swing.formularios.IPanelEntradaDados;

/**
 *
 * @author lis
 */
public class CCTextFormatado extends JFormattedTextField {
    
    private boolean _informarAlteracao = true;
    private String _mascaraEdicao = null;
    private boolean _alterado = false;
    private boolean _maiusculas = false;
    private String _textoAnterior = null;
    private int _limiteCaracteres = 0;
    
    /** Cria uma nova instância de CCTextFormatado */
    public CCTextFormatado() {
        configurar();
    }

    /**
     *Retorna mascara utilizada para edicao
     *@return string com mascara
     */
    public String getMascaraEdicao() {
        return _mascaraEdicao;
    }

    /**
     *Configura mascara para edicao
     * # - numeros decimais, 
     * A - letras, 
     * ? - letras ou numeros, 
     * * - qualquer caracter
     *@param string com mascara no formato "#A?*"
     */
    public void setMascaraEdicao(String mascaraEdicao) {
        _mascaraEdicao = mascaraEdicao;
        if (mascaraEdicao != null) {
            _limiteCaracteres = mascaraEdicao.length();
        }
    }
    
    /**
     *Configura tamanho da mascara para edicao
     *@param tamanho
     */
    public void setTamanho(int tamanho) {
        _limiteCaracteres = tamanho;
        _mascaraEdicao = gerarMascara(_limiteCaracteres);
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
    
    /**
     * Configura se entrada deve ser convertida para maiuscula
     * @param boolean verdadeiro para converter para maiuscula
     */
    public void setMaiusculas(boolean indicador) {
        _maiusculas = indicador;
    }
    
    /**
     * Informa o texto digitado sem caracteres de formatacao
     * @return string apenas com letras e digitos
     */
    public String getTextoPlano() {
        StringBuilder textoPlano = new StringBuilder("");
        char[] texto = getText().toCharArray();
        for (int i = 0; i < texto.length; i++) {
            if (Character.isLetterOrDigit(texto[i])) {
                textoPlano.append(texto[i]);
            }
        }
        return textoPlano.toString();
    }

    private void configurar() {
        
        // ao receber ou perder foco alterar cor de fundo
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                _alterado = false;
                _textoAnterior = getText();
                mudaCorControle(CoresSistema.CAMPO_SELECIONADO);
            }
            @Override
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
        @Override
        public void keyTyped(KeyEvent evt) {
            JFormattedTextField texto = (JFormattedTextField) evt.getSource();
            char c = evt.getKeyChar();
            if ( ! verificaEntrada(c, texto.getCaretPosition())) {
                evt.consume();
            }
            else {
                verificarMaiuscula(evt);
                int tamTexto = texto.getText().length();
                if (++tamTexto >= _mascaraEdicao.length()) {
                    texto.transferFocus();
                }
            }
        }
        
        /* verifica se tecla Enter foi acionada */
        @Override
        public void keyReleased(KeyEvent evt) {
            if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                evt.consume();
                transferFocus();
            }
        }
        
        private void verificarMaiuscula(KeyEvent evt) {
            if (_maiusculas) {
                char c = evt.getKeyChar();
                if (Character.isLetter(c)) {
                    evt.setKeyChar(Character.toUpperCase(c));
                }
            }
        }
 
        private boolean verificaEntrada(char c, int posicao) {
            boolean ok = true;
            char caracterMascara;
            
            try {
                caracterMascara = _mascaraEdicao.charAt(posicao);
            } catch (StringIndexOutOfBoundsException e) {
                caracterMascara = 0;
                ok = false;
            }
            
            switch (caracterMascara) {
                case '#':
                    if ( ! Character.isDigit(c)) {
                        ok = false;
                    }
                    break;
                case 'A':
                    if ( ! Character.isLetter(c)){
                        ok = false;
                    }
                    break;
                case '?':
                    if ( ! Character.isLetterOrDigit(c)){
                        ok = false;
                    }
                    break;
                case '*':
                    if ( ! Character.isDefined(c)){
                        ok = false;
                    }
                    break;
            }
            
            return ok;
        }
    }
    
    private String gerarMascara(int tamanho) {
        StringBuilder mascara = new StringBuilder("");
        for (int i = 0; i < tamanho; i++) {
            mascara.append("#");
        }
        
        return mascara.toString();
    }
    
} ///:~