/*
 * UIConsultaJasper.java
 *
 * Criado em 5 de Junho de 2007, 15:33
 *
 */

package br.com.codigocerto.swing.formularios;

import java.awt.event.*;
import java.awt.Cursor;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperPrintManager;

import br.com.codigocerto.swing.CCInternalFrame;
import br.com.codigocerto.bancodados.ServidorBD;
import br.com.codigocerto.bancodados.TransacaoBD;
import br.com.codigocerto.conversores.DataHora;
import br.com.codigocerto.swing.GerenciadorAcoesDesktop;
import java.sql.ResultSet;
import net.sf.jasperreports.engine.JRResultSetDataSource;

/**
 *
 * @author lis
 */
public abstract class UIConsultaJasper extends CCInternalFrame {
    
    private final String _PASTA_TEMPLATES = "./report/templates/";
    private final String _EXTENSAO_ARQ_COMPILADO = ".jasper";
    private final boolean EXIBIR_DIALOGO_IMPRESSAO = true;
    
    private PanelConsultaJasper _panel = new PanelConsultaJasper();
    private JasperDesign _jasperDesign;
    private Map<String, Object> _params = new HashMap<String, Object>();
    private String _arquivoJasper;
    private boolean _utilizarQueryPronta = false;
    private String _queryPronta;
    
    /** Cria uma nova instância */
    public UIConsultaJasper() {
        incluirComponentes();
    }

    protected void setUtilizarQueryPronta(boolean indicador) {
        _utilizarQueryPronta = indicador;
    }
    
    protected void setQueryPronta(String query) {
        _queryPronta = query;
    }

    protected void incluirComponentes() {
        _panel.setAcaoFechar(new AcaoFechar());
        _panel.setAcaoExibirConsulta(new AcaoExibirConsulta());
        _panel.setAcaoImprimirConsulta(new AcaoImprimirConsulta());
        _panel.setOrigem(this);
        add(_panel);
        
        DataHora emissao = new DataHora();
        emissao.setData(new Date());
        adicionaParametro("relatorioEmissao", emissao.getDataFormatada());
    }
    
    protected void incluirPainelParametros(JPanel painel) {
        _panel.addPainelConsulta(painel);
        validate();
        pack();
    }
    
    /**
     * Limpa o conteudo dos parametros
     */
    public void limparParametros() {
        _params.clear();
    }
    
    /**
     * Adiciona parametro na lista
     * @param nomeParametro
     * @param parametro 
     */
    public void adicionaParametro(String nomeParametro, Object parametro) {
        _params.put(nomeParametro, parametro);
    }
    
    /**
     * Configura o nome do arquivo jasper
     * @param arquivo
     */
    public void setArquivoJasper(String arquivo) {
        _arquivoJasper = arquivo;
    }
    
    protected abstract void lerParametros();
     
    protected JasperPrint gerarConsulta() {
        lerParametros();
        TransacaoBD trans = ServidorBD.getTransacao();
        JasperPrint relatorio = null;
        
        try{
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if (!_utilizarQueryPronta) {
                relatorio =
                        JasperFillManager.fillReport(_PASTA_TEMPLATES
                                                    + _arquivoJasper + _EXTENSAO_ARQ_COMPILADO,
                                                    _params, trans.getConexao());
            } else {
                  ResultSet resultado = trans.executaQueryRS(_queryPronta);
                  JRResultSetDataSource jrRS = new JRResultSetDataSource(resultado);
                  relatorio =
                            JasperFillManager.fillReport(_PASTA_TEMPLATES
                                                        + _arquivoJasper + _EXTENSAO_ARQ_COMPILADO,
                                                        _params, jrRS);
            }
            
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            
        } catch(Exception ex){
            ex.printStackTrace();
        }   
        
        return relatorio;
    }

    protected void exibirConsulta() {
        try {
            JasperPrint relatorio = gerarConsulta();
            CCVisualizadorJasper vis = new CCVisualizadorJasper();
            vis.adicionarRelatorio(relatorio);
            GerenciadorAcoesDesktop.exibirExclusivo(vis);
            vis.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(UIConsultaJasper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void imprimirConsulta() {
        try {
            JasperPrint relatorio = gerarConsulta();
            JasperPrintManager.printReport(relatorio, EXIBIR_DIALOGO_IMPRESSAO);
        } catch (JRException ex) {
            Logger.getLogger(UIConsultaJasper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    class AcaoFechar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            fechar();
        }
    }
    
    class AcaoExibirConsulta implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            exibirConsulta();
        }
    }

    class AcaoImprimirConsulta implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            imprimirConsulta();
        }
    }

} ///:~
