/*
 *
 * Codigo Certo
 *
 * PainelRegistros.java
 *
 * Criado em 6 de Abril de 2007, 09:26
 *
 */

package br.com.codigocerto.configuracao;

/**
 *
 * @author lis
 */
public class PainelRegistros {
    
    private boolean logAtivado = false;
    
    /** Creates a new instance of PainelRegistros */
    public PainelRegistros() {
    }
    
    /*
     *Configura o estado do log do sistema
     *@param verdadeiro para ativar o log
     */
    public void setLogAtivado(boolean estadoLog) {
        logAtivado = estadoLog;
    }
    
    /*
     *Informa o estado do log do sistema
     *@return verdadeiro para ativado
     */
    public boolean getLogAtivado() {
        return logAtivado;
    }
    
}
