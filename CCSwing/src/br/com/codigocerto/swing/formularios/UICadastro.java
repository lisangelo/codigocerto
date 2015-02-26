/*
 * UICadastro.java
 *
 * Criado em 5 de Junho de 2007, 15:33
 *
 */

package br.com.codigocerto.swing.formularios;

import br.com.codigocerto.swing.GerenciadorAcoesDesktop;
import javax.swing.JPanel;

/**
 *
 * @author lis
 */
public abstract class UICadastro extends UIEntradaDados {
    
    /** Cria uma nova instância de Cadastro */
    public UICadastro() {
    }
    
    @Override
    public void inicializar() {
        super.inicializar();
        focarCodigo();
    }
    
    /**
     *Adiciona painel
     *@param string com titulo do painel
     *@param JPanel painel para a area de abas
     */
    public void addPainel(String titulo, JPanel painel) {
        ((PanelEntradaCadastro)_panel).addPainel(titulo, painel);
        validate();
        pack();
    }
    
    /**
     *Insere texto no campo nome
     *@param string com nome
     */
    protected void setNome(String nome) {
        ((PanelEntradaCadastro)_panel).setNome(nome);
    }
    
    /**
     * Informa se os dados do cadastro estao prontos para serem salvos
     * @param novo - verdadeiro para inclusao
     * @return - verdadeiro para dados prontos para serem salvos
     */
    protected boolean verificarDados(boolean novo) {
        boolean ok = true;
        
        return ok;
    }
    
    /**
     *Salva os dados digitados no formulario
     */
    @Override
    public boolean salvarDados() {
        boolean ok = true;

        coletarDados();
        boolean novo = _panel.getNovo();
        if (verificarDados(novo)) {
            if (novo) {
                long id = ((PanelEntradaCadastro)_panel).getId();
                ok = _entrada.incluir();
                if (ok && (id != _entrada.getIdAtual())) { // houve inclusao deste id por outro usuario
                    GerenciadorAcoesDesktop.exibirAviso("Código " + id + " utilizado por outro usuário.\n" 
                                             + "Alterado para " + _entrada.getIdAtual() + ".");
                }
            }
            else {
                ok = _entrada.alterar();
            }
        } else {
            ok = false;
        }
        
        if ( ! ok) {
            if (_entrada.descricaoErro() != null) {
                Avisos.exibirAviso(this, _entrada.descricaoErro());
                String nomeCampo = _entrada.focarComponente();
                if (nomeCampo != null) {
                    _panel.focarCampo(nomeCampo);
                }
            }
        }
        else {
            exibirDados();
        }
        
        return ok;
    }
    
    public void efetuarPesquisaCadastro() {
        _pesquisar.visualizar();
        if (_pesquisar.isOk()) {
            ((PanelEntradaCadastro)_panel).setId(_pesquisar.getId());
            if (isCadastrado(_pesquisar.getId())) {
                exibirDados();
            }
        }
    }
    
    @Override
    protected void incluirComponentes() {
        _panel = new PanelEntradaCadastro();
        super.incluirComponentes();
        add((PanelEntradaCadastro)_panel);
    }
    
    private void focarCodigo() {
        ((PanelEntradaCadastro)_panel).focarCodigo();
    }
    
    @Override
    public void exibirDados() {
        ((PanelEntradaCadastro)_panel).setId(_entrada.getIdAtual());
        focarCodigo();
    }

    public void exibirDados(long id) {
        _entrada.setIdAtual(id);
        exibirDados();
    }

    @Override
    protected void criarNovo() {
        criarNovoRegistro(0L);
    }
    
    protected void criarNovoRegistro(long novoId) {
        _panel.setNovo(true);
        long novoCodigo = novoId;
        if (_entrada.ultimo()) {
            long ultimoId = _entrada.getIdAtual();
            if (ultimoId > novoId) {
                novoCodigo += _entrada.getIdAtual();
            }
        }
        novoCodigo++;
        ((PanelEntradaCadastro)_panel).setId(novoCodigo);
    }
    
    @Override
    protected void excluir() {
        if (((PanelEntradaCadastro)_panel).getId() > 0) { // esta efetivamente posicionado em um registro?
            if (Avisos.exibirPergunta(getParent(), "Excluir este cadastro?")) {
                if (_entrada.excluir()) {
                    _panel.limparDados();
                    irUltimo();
                }
                else {
                    Avisos.exibirAviso(getParent(), _entrada.descricaoErro());
                }
            }
        }
    }
    
    
    @Override
    protected void pesquisar() {
        efetuarPesquisaCadastro();
    }

} ///:~
