/*
 * UIEntradaDados.java
 *
 * Criado em 5 de Junho de 2007, 15:33
 *
 */

package br.com.codigocerto.swing.formularios;

import java.awt.event.*;

import br.com.codigocerto.entradadados.*;
import br.com.codigocerto.controles.Registro;
import br.com.codigocerto.modelos.*;
import br.com.codigocerto.swing.CCInternalFrame;
import br.com.codigocerto.controles.Campo;
import br.com.codigocerto.swing.GerenciadorAcoesDesktop;

/**
 *
 * @author lis
 */
public abstract class UIEntradaDados extends CCInternalFrame {
    
    IPanelEntradaDados _panel;
    EntradaDados _entrada;
    Registro _registro;
    IModelo _modelo;
    protected UIPesquisarCadastro _pesquisar = new UIPesquisarCadastro();
    
    /** Cria uma nova instância de Cadastro */
    public UIEntradaDados() {
        incluirComponentes();
        _entrada = new EntradaDados();
        configurarPesquisa();
    }
    
    /**
     *Configura objeto modelo de entrada de dados
     *@param modelo de entrada
     */
    public void setModelo(IModelo modelo) {
        _entrada.setModelo(modelo);
    }

    /**
     * Recupera objeto modelo de entrada de dados
     * @return modelo de entrada
     */
    public IModelo getModelo() {
        return _modelo;
    }

    /**
     *Configura registro de controle
     *@param registro de controle
     */
    public void setRegistro(Registro registro) {
        _registro = registro;
        _entrada.setRegistro(_registro);
    }
    
    /**
     * Informa registro de controle
     * @return registro de controle
     */
    public Registro getRegistro() {
        return _registro;
    }
 
    /**
     * Informa se eh inclusao de dados
     * @return verdadeiro para inclusao
     */
    protected boolean isNovo() {
        return _panel.getNovo();
    }

    /**
     *Reune os dados existentes no formulario e insere na classe modelo
     */
    protected abstract void coletarDados();
    
    /**
     *Configura parametros de pesquisa da tabela do cadastro
     */
    protected abstract void configurarPesquisa();
    
    /**
     *Salva os dados digitados no formulario
     */
    public boolean salvarDados() {
        boolean ok = true;
        
        coletarDados();
        if (ok) {
            if (_panel.getNovo()) {
                ok = _entrada.incluir();
            }
            else {
                ok = _entrada.alterar();
            }

            if ( ! ok) {
                GerenciadorAcoesDesktop.exibirAviso(_entrada.descricaoErro());
                _panel.focarCampo(_entrada.focarComponente());
            }
            else {
                exibirDados();
            }
        }
        
        return ok;
    }
    
    /**
     *Informa se id esta ou nao cadastrado no bd
     *@param long com id
     *@return boolean verdadeiro se esta cadastrado no bd
     */
    public boolean isCadastrado(long id) {
        return _entrada.buscar(id);
    }
    
    /**
     *Informa entrada esta incluindo um registro
     *@return boolean verdadeiro se esta incluindo
     */
    protected boolean isInclusao() {
        return _panel.getNovo();
    }
    
    /**
     * Configura dicas para os componentes do cadastro
     */
    protected void configurarDicas() {
        for (Object item : _registro.getCampos()) {
            Campo campo = (Campo) item;
            _panel.setDica(campo.getModelo(), campo.getIU());
        }
    }

    protected void incluirComponentes() {
        _panel.setAcaoFechar(new AcaoFechar());
        _panel.setAcaoPrimeiro(new AcaoPrimeiro());
        _panel.setAcaoUltimo(new AcaoUltimo());
        _panel.setAcaoAnterior(new AcaoAnterior());
        _panel.setAcaoProximo(new AcaoProximo());
        _panel.setAcaoNovo(new AcaoNovo());
        _panel.setAcaoExcluir(new AcaoExcluir());
        _panel.setAcaoPesquisar(new AcaoPesquisar());
    }
    
    /**
     * Configura pesquisa personalizada
     * @param acao
     */
    public void setAcaoPesquisarMovimento(ActionListener acao) {
        _panel.setAcaoPesquisar(acao);
    }

    public void limparDados() {

    }

    public abstract void exibirDados();
    
    protected IModelo getDados() {
        return _entrada.getModeloAtual();
    }
    
    protected Object getValor(String nomeCampo) {
        return _panel.getValor(nomeCampo);
    }
    
    private void irProximo() {
        if (_entrada.proximo(_entrada.getIdAtual())) {
            exibirDados();
        }
    }

    protected void irUltimo() {
        if (_entrada.ultimo()) {
            exibirDados();
        } else {
            _panel.limparDados();
        }
    }
    
    protected void criarNovo() {
        _panel.setNovo(true);
    }
    
    protected abstract void excluir();

    protected void pesquisar() {
        if (_pesquisar.getCampos() != null) {
            _pesquisar.visualizar();
            if (_pesquisar.isOk()) {
                if (isCadastrado(_pesquisar.getId())) {
                    exibirDados();
                }
            }
        }
    }
    
    class AcaoFechar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            boolean ok = true;
            
            if (_panel.getAlterado()) {  // se houve alteracao
                if ( ! Avisos.exibirPergunta(getParent(), "Deseja sair sem salvar alterações?")) {
                    ok = false;
                }
            }
            if (ok) {
                fechar();
            }
        }
    }
    
    class AcaoPrimeiro implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (_entrada.primeiro()) {
                exibirDados();
            }
        }
    }

    class AcaoUltimo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (_entrada.ultimo()) {
                exibirDados();
            }
        }
    }
    
    class AcaoProximo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            irProximo();
        }
    }

    class AcaoAnterior implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            if (_entrada.anterior(_entrada.getIdAtual())) {
                exibirDados();
            }
        }
    }

    class AcaoNovo implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            criarNovo();
        }
    }
    
    class AcaoExcluir implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            excluir();
        }
    }

    class AcaoPesquisar implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            pesquisar();
        }
    }

} ///:~