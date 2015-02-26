/*
 * ColunaSqlBD.java
 *
 * Criado em 31 de Agosto de 2006, 20:52
 *
 * CodigoCerto Sistemas
 */

package br.com.codigocerto.bancodados;

/**
 *
 * @author lis
 */
public class ColunaSqlBD {
    
    private String apelido;     // nome amigavel a ser usado para referência do campo
    private String campo;      // nome do campo que irá originar a coluna
    private String funcao;      // funcao a ser aplicada no campo
    
    
    /** Creates a new instance of ColunaSqlBD */
    public ColunaSqlBD() {
    }
    
    /**
     * Insere o nome do campo da tabela
     * @param nome do campo
     */
    public void setCampo(String nome) {
        campo = nome;
    }
    
    /**
     * Insere o apelido para esta coluna
     * @param apelido
     */
    public void setApelido(String nome) {
        apelido = nome;
    }

    /**
     * Insere o nome de uma função a ser aplicada no campo
     * @param nome da função
     */
    public void setFuncao(String nome) {
        funcao = nome;
    }
    
    /**
     * Retorna o apelido amigável para esta coluna
     * @return string com o apelido
     */
    public String getApelido() {
         return (apelido == null ? campo : apelido);
    }
    
    /**
     * Retorna a descrição completa da coluna a ser inserida em um comando SQL
     * @return string com a descrição da coluna
     */
    public String getColuna() {

        final String ABRE_FUNCAO = "(";
        final String FECHA_FUNCAO = ")";
        final String COMO = " AS ";
        final int INICIO_STRING = 0;
        
        StringBuilder retorno = new StringBuilder("");
        
        if (campo != null) {
            retorno.append(campo);
            if (funcao != null) {
                retorno.insert(INICIO_STRING, funcao.toUpperCase() + ABRE_FUNCAO);
                retorno.append(FECHA_FUNCAO);
            }
            if (apelido != null) {
                retorno.append(COMO + apelido);
            }
        }
        
        return retorno.toString();
    }
    
}
