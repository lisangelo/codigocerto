/*
 * Codigo Certo
 * CCTextNumerico.java
 * Criado em 25 de Junho de 2007, 13:30
 */

package br.com.codigocerto.swing;

import br.com.codigocerto.conversores.FormatoNumerico;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import javax.swing.JTextField;
import javax.swing.text.*;

import br.com.codigocerto.swing.formularios.IPanelEntradaDados;

/**
 * @author lis
 */
public class CCTextNumerico extends JTextField implements NumericPlainDocument.InsertErrorListener {

    private boolean _informarAlteracao = true;
    private boolean _alterado = false;
    private String _textoAnterior = null;
    private DecimalFormat _formato;
    private String _mascara;
    private BigDecimal _maiorValor = new BigDecimal(0);
    private BigDecimal _menorValor = new BigDecimal(0);
    
    /** Criar nova instancia de CCTextNumerico */
    public CCTextNumerico() {
        configurar();
    }
    
    /**
     * Configurar acao de informar alteracao
     * @param verdadeiro para informar toda vez que campo for alterado (default)
     */
    public void setInformarAlteracao(boolean informar) {
        _informarAlteracao = informar;
    }
    
    /**
     *Informa se campo teve seu conteudo alterado
     *@return boolean
     */
    public boolean isAlterado() {
        return _alterado;
    }

    public void setMaiorValor(BigDecimal maiorValor) {
        _maiorValor = maiorValor;
    }
    
    public void setMenorValor(BigDecimal menorValor) {
        _menorValor = menorValor;
    }

    public void setMascaraEdicao(String mascaraEdicao) {
        _mascara = mascaraEdicao;
        _formato = new DecimalFormat(_mascara);
        _formato.setGroupingUsed(true);
        _formato.setGroupingSize(3);
        _formato.setParseIntegerOnly(false);
        setFormat(_formato);
    }

    private void configurar() {
        setHorizontalAlignment(JTextField.RIGHT);
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                _alterado = false;
                _textoAnterior = getText().trim();
                selecionarTexto();
                mudaCorControle(CoresSistema.CAMPO_SELECIONADO);
            }
            @Override
            public void focusLost(FocusEvent evt) {
                setSelectionEnd(0);
                normalize();
                mudaCorControle(CoresSistema.CAMPO_NORMAL);
                if (!verificaLimite()) {
                    requestFocusInWindow();
                    Toolkit.getDefaultToolkit().beep();
                }
                else {
                    if (!getText().trim().equalsIgnoreCase(_textoAnterior)) {
                        _alterado = true;
                        if (_informarAlteracao) {
                            informarAlteracao();
                        }
                    }
                }
            }
        });
        addKeyListener(new MyKeyListener());
    }
    
    private void selecionarTexto() {
        String texto = getText();
        if (texto.length() > 0) {
            setSelectionStart(0);
            setSelectionEnd(texto.length());
        }
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
    
    private boolean verificaLimite() {
        boolean ok = false;
        BigDecimal valorDigitado = BigDecimal.ZERO;
        Number valor = getNumberValue();
        try {
            if (valor instanceof Long) {
                valorDigitado = BigDecimal.valueOf((Long) valor);
            }
            else {
                if (valor instanceof Double) {
                    valorDigitado = BigDecimal.valueOf((Double) valor);
                }
            }
        } catch (Exception e) {
            ControleExcecoes.capturarExcecao(e);
        }
        
        if (valorDigitado.compareTo(_menorValor) >= 0 
                && valorDigitado.compareTo(_maiorValor) <= 0) {
            ok = true;
        }
        
        return ok;
    }
    
    private void mudaCorControle(int padrao) {
        setBackground(CoresSistema.corPadrao(padrao));
    }
    
    public void setFormat(DecimalFormat format) {
        ((NumericPlainDocument) getDocument()).setFormat(format);
    }

    public DecimalFormat getFormat() {
        return ((NumericPlainDocument) getDocument()).getFormat();
    }

    public void formatChanged() {
        // Notify change of format attributes.
        setFormat(getFormat());
    }

    // Methods to get the field value
    public Long getLongValue() throws ParseException {
        return ((NumericPlainDocument) getDocument()).getLongValue();
    }

    public Double getDoubleValue() {
        Double valor = 0D;
        try {
            valor = ((NumericPlainDocument) getDocument()).getDoubleValue();
        } catch (ParseException e) {
            ControleExcecoes.capturarExcecao(e);
        }
        
        return valor;
    }

    public Number getNumberValue() {
        Number valor = null;
        try {
            valor = ((NumericPlainDocument) getDocument()).getNumberValue();
        } catch (ParseException e) {
            ControleExcecoes.capturarExcecao(e);
        }
        return valor;
    }

    /**
     * Retorna valor digitado em formato decimal
     * @return valor em decimal
     */
    public BigDecimal getDecimalValue() {

        BigDecimal valorDecimal = null;
        Number valorDigitado = getNumberValue();
        if (valorDigitado != null) {
            try {
                if (valorDigitado instanceof Long) {
                    valorDecimal = BigDecimal.valueOf((Long) valorDigitado);
                }
                else {
                    if (valorDigitado instanceof Double) {
                        valorDecimal = BigDecimal.valueOf((Double) valorDigitado);
                    }
                }
            } catch (Exception e) {
                ControleExcecoes.capturarExcecao(e);
            }
        }

        return valorDecimal;
    }
    
    
    // Methods to install numeric values
    public void setValue(Number number) {
        if (number != null) {
            try {
                setText(verificarDecimais(getFormat().format(number)));
            } catch (IllegalArgumentException e) {
                ControleExcecoes.capturarExcecao(e);
            }
        }
        else {
            setText("");
        }
    }

    public void setValue(long l) {
        try {
            setText(getFormat().format(l));
        } catch (IllegalArgumentException e) {
            ControleExcecoes.capturarExcecao(e);
        }
    }

    public void setValue(double d) {
        try {
            setText(getFormat().format(d));
        } catch (IllegalArgumentException e) {
            ControleExcecoes.capturarExcecao(e);
        }
    }

    public void normalize() {
        // format the value according to the format string
        String numeroFormatado = null;
        try {
            setText(FormatoNumerico.aplicar(getNumberValue().toString(), _mascara));
        } catch (Exception e) {
            ControleExcecoes.capturarExcecao(e);
        }
            
    }
    
    private String verificarDecimais(String numero) {
        final String PONTO_DECIMAL = ".";
        final String SIMBOLO_DECIMAL = ",";
        
        StringBuilder numeroFormatado = new StringBuilder(numero);
        int posPontoMascara = _mascara.indexOf(PONTO_DECIMAL);
        if (posPontoMascara != -1) { // mascara possue ponto decimal, e casas apos a virgula
            int posPontoNumero = numeroFormatado.indexOf(SIMBOLO_DECIMAL);
            if (posPontoNumero == -1) { //  numero formatado nao possue virgula
                numeroFormatado.append(SIMBOLO_DECIMAL);
                posPontoNumero = numeroFormatado.length() - 1;
            }
            int numCasas = _mascara.substring(posPontoMascara + 1).length();
            int numCasasNumero = 0;
            if ((posPontoMascara + 1) < numeroFormatado.length()) {
                numCasasNumero = numeroFormatado.substring(posPontoMascara + 1).length();
            }
            
            for (int i = numCasasNumero; i < numCasas; i++) {
                numeroFormatado.append('0');
            }
        }
        
        return numeroFormatado.toString();
    }

    // Override to handle insertion error
    @Override
    public void insertFailed(NumericPlainDocument doc, int offset, String str, AttributeSet a) {
        // By default, just beep
        Toolkit.getDefaultToolkit().beep();
    }

    // Method to create default model
    @Override
    protected Document createDefaultModel() {
        return new NumericPlainDocument();
    }

    class MyKeyListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent evt) {
            _alterado = true;
        }

        /* verifica se tecla Enter foi acionada */
        @Override
        public void keyReleased(KeyEvent evt) {
            if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                evt.consume();
                transferFocus();
            }
        }
    }

}

class NumericPlainDocument extends PlainDocument {

    protected InsertErrorListener errorListener;
    protected DecimalFormat format;
    protected char decimalSeparator;
    protected char groupingSeparator;
    protected String positivePrefix;
    protected String negativePrefix;
    protected int positivePrefixLen;
    protected int negativePrefixLen;
    protected String positiveSuffix;
    protected String negativeSuffix;
    protected int positiveSuffixLen;
    protected int negativeSuffixLen;
    protected ParsePosition parsePos = new ParsePosition(0);
    protected static DecimalFormat defaultFormat = new DecimalFormat();    

    public NumericPlainDocument() {
        setFormat(null);
    }

    public NumericPlainDocument(DecimalFormat format) {
        setFormat(format);
    }

    public NumericPlainDocument(AbstractDocument.Content content, DecimalFormat format) {
        super(content);
        setFormat(format);

        try {
            format.parseObject(content.getString(0, content.length()), parsePos);
        } 
        catch (Exception e) {
          throw new IllegalArgumentException(
              "Valor inicial não é um número válido");
        }

        if (parsePos.getIndex() != content.length() - 1) {
            throw new IllegalArgumentException( "Valor inicial não é um número válido");
        }
    }

    public void setFormat(DecimalFormat fmt) {
        this.format = fmt != null ? fmt : (DecimalFormat) defaultFormat.clone();

        decimalSeparator = format.getDecimalFormatSymbols().getDecimalSeparator();
        groupingSeparator = format.getDecimalFormatSymbols().getGroupingSeparator();
        positivePrefix = format.getPositivePrefix();
        positivePrefixLen = positivePrefix.length();
        negativePrefix = format.getNegativePrefix();
        negativePrefixLen = negativePrefix.length();
        positiveSuffix = format.getPositiveSuffix();
        positiveSuffixLen = positiveSuffix.length();
        negativeSuffix = format.getNegativeSuffix();
        negativeSuffixLen = negativeSuffix.length();
    }

    public DecimalFormat getFormat() {
        return format;
    }

    public Number getNumberValue() throws ParseException {
        try {
            String content = getText(0, getLength());
            parsePos.setIndex(0);
            Number result = format.parse(content, parsePos);
            if (parsePos.getIndex() != getLength()) {
                throw new ParseException("Não é um número válido: " + content, 0);
            }

            return result;
        } 
        catch (BadLocationException e) {
            throw new ParseException("Não é um número válido", 0);
        }
    }

    public Long getLongValue() throws ParseException {
        Number result = getNumberValue();
        if ((result instanceof Long) == false) {
            throw new ParseException("Não é long", 0);
        }

        return (Long) result;
    }

    public Double getDoubleValue() throws ParseException {
        Number result = getNumberValue();
        if ((result instanceof Long) == false
            && (result instanceof Double) == false) {
            throw new ParseException("Não é double", 0);
        }

        if (result instanceof Long) {
            result = new Double(result.doubleValue());
        }

        return (Double) result;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet a)
                                            throws BadLocationException {
        if (str == null || str.length() == 0) {
            return;
        }

        Content content = getContent();
        int length = content.length();
        int originalLength = length;

        parsePos.setIndex(0);

        // Create the result of inserting the new data,
        // but ignore the trailing newline
        String targetString = content.getString(0, offset) + str
                        + content.getString(offset, length - offset - 1);

        // Parse the input string and check for errors
        do {
            boolean gotPositive = targetString.startsWith(positivePrefix);
            boolean gotNegative = targetString.startsWith(negativePrefix);

            length = targetString.length();

            // If we have a valid prefix, the parse fails if the
            // suffix is not present and the error is reported
            // at index 0. So, we need to add the appropriate
            // suffix if it is not present at this point.
            if (gotPositive == true || gotNegative == true) {
                String suffix;
                int suffixLength;
                int prefixLength;

                if (gotPositive == true && gotNegative == true) {
                    // This happens if one is the leading part of
                    // the other - e.g. if one is "(" and the other "(("
                    if (positivePrefixLen > negativePrefixLen) {
                        gotNegative = false;
                    } else {
                        gotPositive = false;
                    }
                }

                if (gotPositive == true) {
                    suffix = positiveSuffix;
                    suffixLength = positiveSuffixLen;
                    prefixLength = positivePrefixLen;
                } 
                else {
                    // Must have the negative prefix
                    suffix = negativeSuffix;
                    suffixLength = negativeSuffixLen;
                    prefixLength = negativePrefixLen;
                }

                // If the string consists of the prefix alone,
                // do nothing, or the result won't parse.
                if (length == prefixLength) {
                    break;
                }

                // We can't just add the suffix, because part of it
                // may already be there. For example, suppose the
                // negative prefix is "(" and the negative suffix is
                // "$)". If the user has typed "(345$", then it is not
                // correct to add "$)". Instead, only the missing part
                // should be added, in this case ")".
                if (targetString.endsWith(suffix) == false) {
                    int i;
                    for (i = suffixLength - 1; i > 0; i--) {
                        if (targetString.regionMatches(length - i, suffix, 0, i)) {
                            targetString += suffix.substring(i);
                            break;
                        }
                    }

                    if (i == 0) {
                        // None of the suffix was present
                        targetString += suffix;
                    }

                    length = targetString.length();
                }
            }

            format.parse(targetString, parsePos);

            int endIndex = parsePos.getIndex();
            if (endIndex == length) {
                break; // Number is acceptable
            }

            // Parse ended early
            // Since incomplete numbers don't always parse, try
            // to work out what went wrong.
            // First check for an incomplete positive prefix
            if (positivePrefixLen > 0 && endIndex < positivePrefixLen
                        && length <= positivePrefixLen
                        && targetString.regionMatches(0, positivePrefix, 0, length)) {
                break; // Accept for now
            }

            // Next check for an incomplete negative prefix
            if (negativePrefixLen > 0 && endIndex < negativePrefixLen
                        && length <= negativePrefixLen
                        && targetString.regionMatches(0, negativePrefix, 0, length)) {
                break; // Accept for now
            }

            // Allow a number that ends with the group
            // or decimal separator, if these are in use
            char lastChar = targetString.charAt(originalLength - 1);
            int decimalIndex = targetString.indexOf(decimalSeparator);
            if (format.isGroupingUsed() && lastChar == groupingSeparator
                        && decimalIndex == -1) {
                // Allow a "," but only in integer part
                break;
            }

            if (format.isParseIntegerOnly() == false
                        && lastChar == decimalSeparator
                        && decimalIndex == originalLength - 1) {
                // Allow a ".", but only one
                break;
            }

            // No more corrections to make: must be an error
            if (errorListener != null) {
                errorListener.insertFailed(this, offset, str, a);
            }
            return;
        } while (true == false);

        // Finally, add to the model
        super.insertString(offset, str, a);
    }

    public void addInsertErrorListener(InsertErrorListener l) {
        if (errorListener == null) {
            errorListener = l;
            return;
        }
        throw new IllegalArgumentException("InsertErrorListener já registrado");
    }

    public void removeInsertErrorListener(InsertErrorListener l) {
        if (errorListener == l) {
            errorListener = null;
        }
    }

    public interface InsertErrorListener {
        public abstract void insertFailed(NumericPlainDocument doc, int offset,
                                                        String str, AttributeSet a);
    }
    
} ///~
