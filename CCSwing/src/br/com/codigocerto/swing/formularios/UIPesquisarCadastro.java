/*
 * UIPesquisarCadastro.java
 *
 * Criado em 11 de Junho de 2007, 16:06
 *
 */

package br.com.codigocerto.swing.formularios;

import br.com.codigocerto.controles.pesquisas.IModeloPesquisaCad;
import br.com.codigocerto.conversores.ConversorTipos;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.JTextComponent;

import br.com.codigocerto.cadastros.PesquisarCadastro;
import br.com.codigocerto.swing.*;

/**
 *
 * @author lis
 */
public class UIPesquisarCadastro extends CCDialogoOkCancelar {

    public interface Criterio {
        int A_PARTIR_INICIO = 0,
            QUALQUER_POSICAO = 1;
    }
    
    interface TiposAvanco {
        int PRIMEIRO = 0,
            ANTERIOR = 1, 
            PROXIMO = 2,
            ULTIMO = 3;
    }
    
    private final int NUMERO_MINIMO_CARACTERES = 2;

    PesquisarCadastro _pesquisarCadastro;
    PanelPesquisarCadastro _panelPesquisarCadastro = new PanelPesquisarCadastro();
    ModeloTabela _modeloTabela = new ModeloTabela();
    private int _campoId = 0;
    private long _idEscolhido = 0L;
    private IModeloPesquisaCad _modeloPesquisa;
    
    /** Cria uma nova instância de UIPesquisarCadastro */
    public UIPesquisarCadastro() {
        incluirComponentes();
        setLocationRelativeTo(null); // centraliza 
    }

    /**
     * Configura modelo de pesquisa
     * @param modelopesquisa
     */
    public void setModeloPesquisa(IModeloPesquisaCad modeloPesquisa) {
        _modeloPesquisa = modeloPesquisa;
        setTitle("Pesquisar " + _modeloPesquisa.getNomeCadastro());
        _pesquisarCadastro = _modeloPesquisa.getPesquisarCadastro();
        _modeloTabela.setTitulos(_modeloPesquisa.getTitulosColunas());
        _panelPesquisarCadastro.setNomeCampos(_modeloPesquisa.getTitulosColunas());
        _panelPesquisarCadastro.setModeloTabela(_modeloTabela);
        _panelPesquisarCadastro.setCampoInicial(_modeloPesquisa.getCampoInicial());
        _campoId = _modeloPesquisa.getCampoId();
        _panelPesquisarCadastro.setLarguraColunas(_modeloPesquisa.getLarguraColunas());
        if (modeloPesquisa.getCampoDescricao().contains("terceiros")) {
            _panelPesquisarCadastro.setExibirDadosAdicionais(true);
        }
    }
    
    /**
     *Informa id escolhido na pesquisa
     *@return long id
     */
    public long getId() {
        return _idEscolhido;
    }

    /**
     * Informa todos os campos escolhidos na pesquisa
     *@return Object[]
     */
    public Object[] getCampos() {
        return _panelPesquisarCadastro.getSelecao();
    }

    /**
     * Estabelece criterio para pesquisa
     * @param criterio int 
     */
    public void setCriterio(int criterio) {
        _panelPesquisarCadastro.setCriterio(criterio);
    }
    
    /**
     * Informa chave de pesquisa inicial
     * @param string com chave de pesquisa
     */
    public void setChave(String chave) {
        PanelPesquisarCadastro panel = (PanelPesquisarCadastro) getPanelDialogo();
        panel.setChave(chave);
        efetuarPesquisa(chave);
    }
    
    private void efetuarPesquisa(String chave) {
        if (chave.length() > NUMERO_MINIMO_CARACTERES) {
            _pesquisarCadastro.setChavePesquisa(chave);
            _pesquisarCadastro.setCampoPesquisa(_panelPesquisarCadastro.getIndiceCampoChave());
            _pesquisarCadastro.efetuarPesquisa();
            if (_pesquisarCadastro.isResultadoValido()) {
                _modeloTabela.setConteudo(_pesquisarCadastro.primeiro());
            }
            else {
                _modeloTabela.setConteudo(null);
                
            }
        }
        else {
            _modeloTabela.setConteudo(null);
        }
        _panelPesquisarCadastro.focarChave();
    }
    
    @Override
    protected boolean verificarOk() {
        
        boolean ok = true;
        PanelPesquisarCadastro panel = (PanelPesquisarCadastro) getPanelDialogo();
        String id = (String) panel.getSelecao(_campoId);
        
        if (id == null) {
            ok = false;
            _idEscolhido = 0L;
        }
        else {
            ConversorTipos conversor = new ConversorTipos();
            conversor.setValorBase(id);
            _idEscolhido = conversor.getLong();
        }
        
        return ok;
    }
    
    private void incluirComponentes() {
        inserirPanelDialogo(_panelPesquisarCadastro);
        _panelPesquisarCadastro.setAcaoEfetuarPesquisa(new ControlaCampoChave());
        _panelPesquisarCadastro.setAcaoCriterio(new ControlaCriterio());
        _panelPesquisarCadastro.setAcaoCampo(new ControlaCampo());
        _panelPesquisarCadastro.setAcaoPrimeiro(new ControlaAvanco(TiposAvanco.PRIMEIRO));
        _panelPesquisarCadastro.setAcaoAnterior(new ControlaAvanco(TiposAvanco.ANTERIOR));
        _panelPesquisarCadastro.setAcaoProximo(new ControlaAvanco(TiposAvanco.PROXIMO));
        _panelPesquisarCadastro.setAcaoUltimo(new ControlaAvanco(TiposAvanco.ULTIMO));
        pack();
    }
    
    class ModeloTabela extends AbstractTableModel {
        
        private String[] _titulos;
        private String[][] _celulas;
        
        public void setTitulos(String[] titulos) {
            _titulos = titulos;
        }
        
        public void setConteudo(String[][] celulas) {
            _celulas = celulas;
            fireTableDataChanged();
        }
        
        @Override
        public int getColumnCount() {
            return _titulos.length;
        }
        
        @Override
        public int getRowCount() {
            int numeroCelulas = 0;
            if (_celulas != null) {
                numeroCelulas = _celulas.length;
            }
            return numeroCelulas;
        }
        
        public String getColumnName(int col) {
            return _titulos[col];
        }
        
        public Object getValueAt(int row, int col) {
            StringBuilder conteudo = new StringBuilder("");
            if (_celulas != null) {
                conteudo.append(_celulas[row][col]);
            }
            return conteudo.toString();
        }
        
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        
        public boolean isCellEditable(int row, int col) {
            return false;
        }
        
        public void setValueAt(Object value, int row, int col) {
            _celulas[row][col] = (String) value;
            fireTableCellUpdated(row, col);
        }   
        
    }
    
    class ControlaCampoChave extends KeyAdapter {
        public void keyTyped(KeyEvent evt) {
            if (evt.getKeyCode() == KeyEvent.VK_TAB) {
                evt.consume();
                _panelPesquisarCadastro.focarResultados();
            }
            else {
                JTextComponent comp = (JTextComponent) evt.getSource();
                StringBuilder texto = new StringBuilder(comp.getText());
                texto.append(evt.getKeyChar());
                efetuarPesquisa(texto.toString());
            }
        }
    }
    
    class ControlaCriterio implements ItemListener {
        
        public void itemStateChanged(ItemEvent evt) {
            JComboBox combo = (JComboBox) evt.getSource();
            if (combo.getSelectedIndex() == Criterio.A_PARTIR_INICIO) {
                _pesquisarCadastro.setQualquerPosicao(false);
            }
            else {
                _pesquisarCadastro.setQualquerPosicao(true);
            }
            efetuarPesquisa(_panelPesquisarCadastro.getChave());
        }
        
    }

    class ControlaCampo implements ItemListener {
        
        public void itemStateChanged(ItemEvent evt) {
            efetuarPesquisa(_panelPesquisarCadastro.getChave());
        }
    }
    
    class ControlaAvanco implements ActionListener {
        
        private int _tipoAvanco = 0;
        
        public ControlaAvanco(int tipoAvanco) {
            _tipoAvanco = tipoAvanco;
        }
        
        public void actionPerformed(ActionEvent evt) {
            if (_pesquisarCadastro.isResultadoValido()) {
                String[][] resultados;
                switch (_tipoAvanco) {
                    case UIPesquisarCadastro.TiposAvanco.PRIMEIRO:
                        resultados = _pesquisarCadastro.primeiro();
                        break;
                    case UIPesquisarCadastro.TiposAvanco.ANTERIOR:
                        resultados = _pesquisarCadastro.anterior();
                        break;
                    case UIPesquisarCadastro.TiposAvanco.PROXIMO:
                        resultados = _pesquisarCadastro.proximo();
                        break;
                    case UIPesquisarCadastro.TiposAvanco.ULTIMO:
                        resultados = _pesquisarCadastro.ultimo();
                        break;
                    default:
                        resultados = _pesquisarCadastro.primeiro();
                        break;
                }
                _modeloTabela.setConteudo(resultados);
            }
            _panelPesquisarCadastro.focarChave();
        }
    }
    
    
} ///:~
