/*
 * PanelMensagem.java
 *
 * Created on 3 de Maio de 2008, 17:13
 */

package br.com.codigocerto.swing;

/**
 *
 * @author  lis
 */
public class PanelMensagem extends javax.swing.JPanel {
    
    /** Creates new form PanelMensagem */
    public PanelMensagem() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelMensagem = new javax.swing.JLabel();

        jLabelMensagem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelMensagem.setText("Mensagem");
        jLabelMensagem.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelMensagem;
    // End of variables declaration//GEN-END:variables
    
    public void setTexto(String texto) {
        jLabelMensagem.setText(texto);
    }
           
    
    
}