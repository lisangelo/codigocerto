/*
 * PanelOkCancelar.java
 *
 * Created on 23 de Maio de 2007, 16:15
 */

package br.com.codigocerto.swing;

import java.awt.event.KeyEvent;

/**
 *
 * @author  lis
 */
public class PanelSimNao extends javax.swing.JPanel {
    
    /** Creates new form PanelOkCancelar */
    public PanelSimNao() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonOk = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();

        jButtonOk.setText("Sim");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });
        jButtonOk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButtonOkKeyReleased(evt);
            }
        });

        jButtonCancelar.setText("Não");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonCancelar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonOk))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOkKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonOkKeyReleased
        int tecla = evt.getKeyCode();
        switch (tecla) {
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_S:
                jButtonOk.doClick();
                break;
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_N:    
                jButtonCancelar.doClick();
                break;
        }
    }//GEN-LAST:event_jButtonOkKeyReleased

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonOkActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonOk;
    // End of variables declaration//GEN-END:variables
    
    public javax.swing.JButton botaoCancelar() {
        return jButtonCancelar;
    }

    public javax.swing.JButton botaoOk() {
        return jButtonOk;
    }

}