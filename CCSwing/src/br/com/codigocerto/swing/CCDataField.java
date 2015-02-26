/*
 * Codigo Certo
 * CCDataField.java
 * Criado em 2 de Julho de 2007, 16:07
 */

package br.com.codigocerto.swing;

import br.com.codigocerto.conversores.ConversorTipos;
import br.com.codigocerto.conversores.Verificar;
import br.com.codigocerto.swing.formularios.IPanelEntradaDados;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import org.jdesktop.swingx.JXDatePicker;


/**
 * @author lis
 */
public class CCDataField extends JXDatePicker {
    
    private Date _maiorValor = null;
    private Date _menorValor = null;
    private boolean _alterado = false;
    private String _textoAnterior = null;
    private Date _dataAnterior = null;
    private boolean _informarAlteracao = true;
    private JButton _botaoAcionar;
    

    /** Criar nova instancia de CCDataField */
    public CCDataField() {
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

    /**
     * Adiciona um listener para produto encontrado e selecionado
     * @param acao
     */
    public void adicionarAcao(ActionListener acao) {
        _botaoAcionar.addActionListener(acao);
    }

    private void configurar() {

        _botaoAcionar = new JButton();
        _botaoAcionar.setVisible(false);
        DateFormat[] formatosData = new DateFormat[1];
        formatosData[0] = new SimpleDateFormat("dd/MM/yyyy");
        setFormats(formatosData);
        
        final JFormattedTextField editor = getEditor();
        CCTextFormatado textoPadrao = new CCTextFormatado();
        editor.setFont(textoPadrao.getFont());
        editor.setSize(textoPadrao.getSize());
        
        // ao receber ou perder foco alterar cor de fundo
        editor.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                _alterado = false;
                mudaCorControle(CoresSistema.CAMPO_SELECIONADO);
                selecionarTexto(editor);
            }
            @Override
            public void focusLost(FocusEvent evt) {
                mudaCorControle(CoresSistema.CAMPO_NORMAL);
                corrigirTexto(editor);
                if (!verificaLimite()) {
                    requestFocusInWindow();
                    Toolkit.getDefaultToolkit().beep();
                }
                else {
                    if (!editor.getText().equals(_textoAnterior)) {
                        _alterado = true;
                        _botaoAcionar.doClick();
                        if (_informarAlteracao) {
                            informarAlteracao();
                        }
                    }
                }
            }

        });
        
        // verificar teclas digitadas de acordo com mascara
        editor.addKeyListener(new MyKeyListener());
        

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt) {
                if (!verificaLimite()) {
                    requestFocusInWindow();
                    Toolkit.getDefaultToolkit().beep();
                }
                else {
                    if (_dataAnterior != null) {
                        if (getDate().compareTo(_dataAnterior) != 0) {
                            _alterado = true;
                            _botaoAcionar.doClick();
                            informarAlteracao();
                        }
                    }
                    else {
                        _alterado = true;
                        _botaoAcionar.doClick();
                        if (_informarAlteracao) {
                            informarAlteracao();
                        }
                    }
                }
            }

        });
        
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                _alterado = true;
                if (_informarAlteracao) {
                    informarAlteracao();
                }
            }
        });
        
        
    }
    
    private void selecionarTexto(JFormattedTextField editor) {
        StringBuilder textoOriginal = new StringBuilder(editor.getText());
        do {
            int pos = textoOriginal.indexOf("/");
            if (pos >= 0) {
                textoOriginal.deleteCharAt(pos);
            }
        }
        while (textoOriginal.indexOf("/") >= 0);
        
        String texto = textoOriginal.toString();
        if (texto.length() > 0) {
            editor.setSelectionStart(0);
            editor.setSelectionEnd(texto.length());
        }
        editor.setText(texto);
    }
    
    private void corrigirTexto(JFormattedTextField editor) {
        StringBuilder textoCorrigido = new StringBuilder("");
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String hoje = formato.format(new Date());
        String texto = editor.getText();
        int tam = texto.length();
        
        switch (tam) {
            case 1:
                break;
            case 2:
                textoCorrigido.append(texto);
                textoCorrigido.append(hoje.substring(2));
                break;
            case 3:
                break;
            case 4:
                textoCorrigido.append(texto.substring(0, 2));
                textoCorrigido.append("/");
                textoCorrigido.append(texto.substring(2, 4));
                textoCorrigido.append(hoje.substring(5));
                break;
            case 5:
                break;
            case 6:
                textoCorrigido.append(texto.substring(0, 2));
                textoCorrigido.append("/");
                textoCorrigido.append(texto.substring(2, 4));
                textoCorrigido.append("/");
                ConversorTipos conv = new ConversorTipos();
                conv.setValorBase(texto.substring(4));
                int ano = conv.getInteger();
                if (ano < 40) {
                    textoCorrigido.append("20" + texto.substring(4));
                }
                else {
                    textoCorrigido.append("19" + texto.substring(4));
                }
                break;
            case 7:
                break;
            case 8:
                textoCorrigido.append(texto.substring(0, 2));
                textoCorrigido.append("/");
                textoCorrigido.append(texto.substring(2, 4));
                textoCorrigido.append("/");
                textoCorrigido.append(texto.substring(4));
        }
        
        editor.setText(textoCorrigido.toString());
    }

    private boolean verificaLimite() {
        boolean ok = true;
        Date data = getDate();

        if (!Verificar.isNuloOuVazio(data)) {
            if (!Verificar.isNuloOuVazio(_menorValor)) {
                if (data.compareTo(_menorValor) < 0) {
                    ok = false;
                }
            }
            if (!Verificar.isNuloOuVazio(_maiorValor)) {
                if (data.compareTo(_maiorValor) > 0) {
                    ok = false;
                }
            }
        }
        
        return ok;
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
        getEditor().setBackground(CoresSistema.corPadrao(padrao));
    }

    class MyKeyListener extends KeyAdapter {
        
        final String _mascaraEdicao = "########";
        
        @Override
        public void keyTyped(KeyEvent evt) {
            JFormattedTextField texto = (JFormattedTextField) evt.getSource();
            char c = evt.getKeyChar();
            if ( ! verificaEntrada(c, texto.getCaretPosition())) {
                evt.consume();
            }
            else {
                int tamTexto = texto.getText().length();
                if (++tamTexto == _mascaraEdicao.length()) {
                    getEditor().transferFocus();
                }
            }
                
        }

        /* verifica se tecla Enter foi acionada */
        @Override
        public void keyReleased(KeyEvent evt) {
            if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                evt.consume();
                getEditor().transferFocus();
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
    
} ///~
