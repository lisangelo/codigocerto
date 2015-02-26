/*
 * Codigo Certo
 * IPanelConsultaJasper.java
 * Criado em 27 de Julho de 2007, 10:29
 */

package br.com.codigocerto.swing.formularios;

import java.awt.event.ActionListener;

/**
 * @author lis
 */
public interface IPanelConsultaJasper {
   
    /**
     *Configura acao para botao fechar
     *@param acao para fechar formulario
     */
    public void setAcaoFechar(ActionListener acao);

    /**
     *Configura acao para botao gerar
     *@param acao para gerar consulta
     */
    public void setAcaoGerarConsulta(ActionListener acao);
    
    /**
     * Configurar consulta de origem do painel
     * @param consulta de origem
     */
    public void setOrigem(UIConsultaJasper consulta);
    
} ///~
