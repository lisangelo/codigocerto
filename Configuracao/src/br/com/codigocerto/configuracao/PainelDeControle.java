/*
 *
 * Codigo Certo
 *
 * PainelDeControle.java
 *
 * Criado em 6 de Abril de 2007, 09:14
 *
 */

package br.com.codigocerto.configuracao;

/**
 *
 * @author lis
 */
public class PainelDeControle {
    
    private static PainelDeControle instance; // singleton 
    
    public static PainelRegistros painelRegistros;
    
    /** Creates a new instance of PainelDeControle */
    private PainelDeControle() {
        painelRegistros = new PainelRegistros();
    }
    
    /**
     * iniciar singleton
     */
    public synchronized static PainelDeControle getInstance() 
    {
        if( instance == null ) {
            instance = new PainelDeControle();
        }
        return instance;
    }
    
}
