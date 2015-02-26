/*
 * PanelCadastro.java
 *
 * Created on 31 de Maio de 2007, 16:13
 */

package br.com.codigocerto.swing.formularios;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import br.com.codigocerto.swing.ICCPanel;
import br.com.codigocerto.swing.CCPesquisaCadastro;
import br.com.codigocerto.swing.CCDataField;
import br.com.codigocerto.swing.CCTextNumerico;
import br.com.codigocerto.swing.CCModeloTabela;
import br.com.codigocerto.swing.CCTabela;
import br.com.codigocerto.swing.CCTextoFixo;

/**
 *
 * @author  lis
 */
public class PanelEntradaDados extends javax.swing.JPanel implements ICCPanel, IPanelEntradaDados {

    private boolean _alterado = false;
    private boolean _novo = false;
    
    /** Creates new form PanelEntradaDados */
    public PanelEntradaDados() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        barra = new br.com.codigocerto.swing.barras.BarraCadastro();
        jPanelPrincipal = new javax.swing.JPanel();
        jPanelRodape = new javax.swing.JPanel();
        jButtonCancelar = new javax.swing.JButton();
        jButtonSalvar = new javax.swing.JButton();
        jPanelMovimento = new javax.swing.JPanel();

        barra.setFloatable(false);

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setEnabled(false);
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jButtonSalvar.setText("Salvar");
        jButtonSalvar.setEnabled(false);
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRodapeLayout = new javax.swing.GroupLayout(jPanelRodape);
        jPanelRodape.setLayout(jPanelRodapeLayout);
        jPanelRodapeLayout.setHorizontalGroup(
            jPanelRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRodapeLayout.createSequentialGroup()
                .addContainerGap(402, Short.MAX_VALUE)
                .addComponent(jButtonSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancelar))
        );
        jPanelRodapeLayout.setVerticalGroup(
            jPanelRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButtonCancelar)
                .addComponent(jButtonSalvar))
        );

        javax.swing.GroupLayout jPanelMovimentoLayout = new javax.swing.GroupLayout(jPanelMovimento);
        jPanelMovimento.setLayout(jPanelMovimentoLayout);
        jPanelMovimentoLayout.setHorizontalGroup(
            jPanelMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );
        jPanelMovimentoLayout.setVerticalGroup(
            jPanelMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelPrincipalLayout = new javax.swing.GroupLayout(jPanelPrincipal);
        jPanelPrincipal.setLayout(jPanelPrincipalLayout);
        jPanelPrincipalLayout.setHorizontalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelRodape, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanelMovimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelPrincipalLayout.setVerticalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrincipalLayout.createSequentialGroup()
                .addComponent(jPanelMovimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelRodape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(barra, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
            .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(barra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed

        UIEntradaDados uiEntrada = getOrigem();    
        if (uiEntrada != null) {
            if (uiEntrada.salvarDados()) {
                setAlterado(false);
            }
        }
    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
            
        UIEntradaDados uiEntrada = getOrigem();    
        if (uiEntrada != null) {
            uiEntrada.limparDados();
            if (_novo) {
                setNovo(false);
                eliminarConteudo(getComponents());
            }
            else {
                uiEntrada.exibirDados();                
                setAlterado(false);
            }
        }
    }//GEN-LAST:event_jButtonCancelarActionPerformed
    
    protected UIEntradaDados getOrigem() {
        final int NIVEIS_CONTAINERS = 10;
        UIEntradaDados uiEntrada = null;

        Container cont = getParent();
        int nivel = NIVEIS_CONTAINERS;
        while ( ! (cont instanceof UIEntradaDados) && nivel > 0) {
            cont = cont.getParent();
            nivel--;
        }
        if (nivel > 0) {
            uiEntrada = (UIEntradaDados) cont;
        }
        
        return uiEntrada;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.codigocerto.swing.barras.BarraCadastro barra;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JPanel jPanelMovimento;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JPanel jPanelRodape;
    // End of variables declaration//GEN-END:variables
    
    /**
     *Configura acao para botao fechar
     *@param acao para fechar formulario
     */
    @Override
    public void setAcaoFechar(ActionListener acao) {
        barra.setAcaoFechar(acao);
    }

    /**
     *Configura acao para botao proximo
     *@param acao para proximo registro
     */
    @Override
    public void setAcaoProximo(ActionListener acao) {
        barra.setAcaoProximo(acao);
    }

    /**
     *Configura acao para botao anterior
     *@param acao para registro anterior
     */
    public void setAcaoAnterior(ActionListener acao) {
        barra.setAcaoAnterior(acao);
    }
    
    /**
     *Configura acao para botao primeiro
     *@param acao para primeiro registro
     */
    public void setAcaoPrimeiro(ActionListener acao) {
        barra.setAcaoPrimeiro(acao);
    }
    
    /**
     *Configura acao para botao ultimo
     *@param acao para ultimo registro
     */
    public void setAcaoUltimo(ActionListener acao) {
        barra.setAcaoUltimo(acao);
    }

    /**
     *Configura acao para botao novo
     *@param acao para novo registro
     */
    public void setAcaoNovo(ActionListener acao) {
        barra.setAcaoNovo(acao);
    }

    /**
     *Configura acao para botao excluir
     *@param acao para excluir registro
     */
    public void setAcaoExcluir(ActionListener acao) {
        barra.setAcaoExcluir(acao);
    }

    /**
     *Configura acao para botao pesquisar
     *@param acao para pesquisar cadastro
     */
    public void setAcaoPesquisar(ActionListener acao) {
        barra.setAcaoPesquisar(acao);
    }
    
    /**
     *Configura estado de alteracao
     *@param boolean com estado
     */
    public void setAlterado(boolean alterado) {
        _alterado = alterado;
        barra.setAlterado(_alterado);
        jButtonCancelar.setEnabled(_alterado);
        jButtonSalvar.setEnabled(_alterado);
        if ( ! _alterado) {
            _novo = false;
        }
    }
    
    /**
     *Informa estado de alteracao de dados no painel
     *@return boolean com estado
     */
    public boolean getAlterado() {
        return _alterado;
    }

    /**
     *Configura estado de Novo 
     *@param boolean com estado
     */
    public void setNovo(boolean novo) {
        _novo = novo;
        _alterado = _novo;
        barra.setAlterado(_alterado);
        jButtonCancelar.setEnabled(_alterado);
        jButtonSalvar.setEnabled(_alterado);
        eliminarConteudo(jPanelPrincipal.getComponents());
    }
    
    /**
     *Informa estado de alteracao de dados no painel
     *@return boolean com estado
     */
    public boolean getNovo() {
        return _novo;
    }

    /**
     * Configura dica em determinado componente
     *@param nomeComponente string com nome do componente
     *@param dica string com dica
     */
    public void setDica(String nomeComponente, String dica) {
        
        for (Component comp : jPanelPrincipal.getComponents()) {
            
            if (comp.getName() != null && comp.getName().equals(nomeComponente)) {
                ((JComponent)comp).setToolTipText(dica);
            }
            else {
                if (comp instanceof JComponent) {
                    JComponent jComp = (JComponent) comp;
                    if (jComp.getComponentCount() > 0) {
                        setDica(jComp, nomeComponente, dica);
                    }
                }
            }
        }
    }

    private void setDica(JComponent componente, String nomeComponente, String dica) {
        
        for (Component comp : componente.getComponents()) {
            
            if (comp.getName() != null && comp.getName().equals(nomeComponente)) {
                ((JComponent)comp).setToolTipText(dica);
            }
            else {
                if (comp instanceof JComponent) {
                    JComponent jComp = (JComponent) comp;
                    if (jComp.getComponentCount() > 0) {
                        setDica(jComp, nomeComponente, dica);
                    }
                }
            }
        }
    }
    
    /**
     *Informa valor setado em determinado componente
     *@return Object valor inserido
     */
    public Object getValor(String nomeComponente) {
        Object valor = null;
        
        for (Component comp : jPanelPrincipal.getComponents()) {
            
            if (comp.getName() != null && comp.getName().equals(nomeComponente)) {
                return getValor(comp);
            }
            else {
                if (comp instanceof JComponent) {
                    JComponent jComp = (JComponent) comp;
                    if (jComp.getComponentCount() > 0) {
                        valor = getValor(jComp, nomeComponente);
                        if (valor != null) {
                            return valor;
                        }
                    }
                }
            }
        }
        
        return valor;
    }
    
    /**
     * Adiciona painel com objetos da movimentacao
     * @param painel 
     */
    public void addPainelMovimento(JPanel painel) {
        GroupLayout layout = (GroupLayout) jPanelMovimento.getLayout();
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );
    }

    private Object getValor(JComponent componente, String nomeComponente) {
        Object valor = null;
        
        for (Component comp : componente.getComponents()) {
            
            if (comp.getName() != null && comp.getName().equals(nomeComponente)) {
                return getValor(comp);
            }
            else {
                if (comp instanceof JComponent) {
                    JComponent jComp = (JComponent) comp;
                    if (jComp.getComponentCount() > 0) {
                        valor = getValor(jComp, nomeComponente);
                        if (valor != null) {
                            return valor;
                        }
                    }
                }
            }
        }
        
        return valor;
    }

    private Object getValor(Component comp) {
        Object valor = null;
        if (comp instanceof CCDataField) {
            valor = ((CCDataField)comp).getDate();
        }
            else {
            if (comp instanceof CCTextNumerico) {
                valor = ((CCTextNumerico)comp).getDecimalValue();
            }
            else {
                if (comp instanceof CCPesquisaCadastro) {
                    valor = new Long(((CCPesquisaCadastro)comp).getId());
                }
                else {
                    if (comp instanceof JTextComponent) {
                        valor = ((JTextComponent)comp).getText();
                    }
                    else {
                        if (comp instanceof JCheckBox) {
                            valor = new Boolean(((JCheckBox) comp).isSelected());
                        }
                    }
                }
            }
        }
        return valor;
    }
    
    /**
     *Focar componente 
     *@param String nome do campo associado ao componente
     */
    public void focarCampo(String nomeCampo) {
        
        for (Component comp : jPanelPrincipal.getComponents()) {
            
            if (comp.getName() != null && comp.getName().equals(nomeCampo)) {
                comp.requestFocusInWindow();
            }
            else {
                if (comp instanceof JComponent) {
                    JComponent jComp = (JComponent) comp;
                    if (jComp.getComponentCount() > 0) {
                        focarCampo(jComp, nomeCampo);
                    }
                }
            }
        }
    }
    
    private void focarCampo(JComponent componente, String nomeComponente) {
        
        for (Component comp : componente.getComponents()) {
            
            if (comp.getName() != null && comp.getName().equals(nomeComponente)) {
                 comp.requestFocusInWindow();
            }
            else {
                if (comp instanceof JComponent) {
                    JComponent jComp = (JComponent) comp;
                    if (jComp.getComponentCount() > 0) {
                        focarCampo(jComp, nomeComponente);
                    }
                }
            }
        }
    }
    
    /**
     *Elimina conteudo dos campos
     */
    @Override
    public void limparDados() {
        eliminarConteudo(jPanelPrincipal.getComponents());
    }
    
    private void eliminarConteudo(Component[] componentes) {
        for (Component comp : componentes) {
            if (comp instanceof CCTabela) {
                TableModel modelo = ((CCTabela)comp).getModel();
                if (modelo instanceof CCModeloTabela) {
                    ((CCModeloTabela)modelo).setConteudo(null);
                }
            } else {
                if (comp instanceof CCTextoFixo) {
                    ((CCTextoFixo)comp).setText(" ");
                } else {
                    if (comp instanceof CCPesquisaCadastro) {
                        ((CCPesquisaCadastro)comp).setId(0L);
                    } else {
                        if (comp instanceof CCDataField) {
                            ((CCDataField) comp).setDate(null);
                        } else {
                            if (comp instanceof JTextComponent) {
                                ((JTextComponent) comp).setText("");
                            } else {
                                if (comp instanceof JCheckBox) {
                                    ((JCheckBox) comp).setSelected(false);
                                } else {
                                    if (comp instanceof JComponent) {
                                        JComponent jComp = (JComponent) comp;
                                        if (jComp.getComponentCount() > 0) {
                                            eliminarConteudo(jComp.getComponents());
                                        } 
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    protected void configurar() {
        setAlterado(_alterado);
        configurarAtalhos();
    }
    
    private void configurarAtalhos() {
        jButtonSalvar.setMnemonic(jButtonSalvar.getText().charAt(0));
        jButtonSalvar.setToolTipText("Salvar <Alt+S>");
        jButtonCancelar.setMnemonic(jButtonCancelar.getText().charAt(0));
        jButtonCancelar.setToolTipText("Cancelar alterações <Alt+C>");
    }

    public void exibirDados() {
        UIEntradaDados uiEntrada = getOrigem();    
        if (uiEntrada != null) {
            if (!_novo) {
                uiEntrada.exibirDados();                
            }
        }
    }
    
}
