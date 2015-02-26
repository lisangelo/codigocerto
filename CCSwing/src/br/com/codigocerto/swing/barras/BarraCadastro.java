/*
 * BarraCadastro.java
 *
 * Criado em 31 de Maio de 2007, 11:00
 *
 */

package br.com.codigocerto.swing.barras;

import java.awt.event.*;
import javax.swing.*;

import br.com.codigocerto.swing.botoes.*;

/**
 *
 * @author lis
 */
public class BarraCadastro extends Barra {
    
    private BotaoNovo _botaoNovo = new BotaoNovo();
    private BotaoPesquisar _botaoPesquisar = new BotaoPesquisar();
    private BotaoExcluir _botaoExcluir = new BotaoExcluir();
    private BotaoPrimeiro _botaoPrimeiro = new BotaoPrimeiro();
    private BotaoAnterior _botaoAnterior = new BotaoAnterior();
    private BotaoProximo _botaoProximo = new BotaoProximo(); 
    private BotaoUltimo _botaoUltimo = new BotaoUltimo();
    private BotaoFechar _botaoFechar = new BotaoFechar();
    
    /** Cria uma nova instância de BarraCadastro */
    public BarraCadastro() {
        incluirComponentes();
    }
    
    /**
     * Configura acao para botao Fechar
     * @param acao 
     */
    public void setAcaoFechar(ActionListener acao) {
        _botaoFechar.addActionListener(acao);
    }

    /**
     * Configura acao para botao Proximo
     * @param acao
     */
    public void setAcaoProximo(ActionListener acao) {
        _botaoProximo.addActionListener(acao);
    }

    /**
     * Configura acao para botao Anterior
     * @param acao
     */
    public void setAcaoAnterior(ActionListener acao) {
        _botaoAnterior.addActionListener(acao);
    }

    /**
     * Configura acao para botao Primeiro
     * @param acao
     */
    public void setAcaoPrimeiro(ActionListener acao) {
        _botaoPrimeiro.addActionListener(acao);
    }
    
    /**
     * Configura acao para botao Ultimo
     * @param acao
     */
    public void setAcaoUltimo(ActionListener acao) {
        _botaoUltimo.addActionListener(acao);
    }

    /**
     * Configura acao para botao Novo
     * @param acao
     */
    public void setAcaoNovo(ActionListener acao) {
        _botaoNovo.addActionListener(acao);
    }

    /**
     * Configura acao para botao Excluir
     * @param acao
     */
    public void setAcaoExcluir(ActionListener acao) {
        _botaoExcluir.addActionListener(acao);
    }

    /**
     * Configura acao para botao Pesquisar
     * @param acao
     */
    public void setAcaoPesquisar(ActionListener acao) {
        _botaoPesquisar.addActionListener(acao);
    }

    /**
     * Dispara acao associada ao botao Pesquisar
     */
    public void acionaPesquisa() {
        _botaoPesquisar.doClick();
    }
    
    /**
     *Configura estado de alteracao
     *@param boolean com estado
     */
    public void setAlterado(boolean alterado) {
        _botaoNovo.setEnabled(!alterado);
        _botaoPesquisar.setEnabled(!alterado);
        _botaoExcluir.setEnabled(!alterado);
        _botaoPrimeiro.setEnabled(!alterado);
        _botaoAnterior.setEnabled(!alterado);
        _botaoProximo.setEnabled(!alterado);
        _botaoUltimo.setEnabled(!alterado);
    }

    private void incluirComponentes() {
        add(_botaoNovo);
        add(_botaoPesquisar);
        add(_botaoExcluir);
        add(new Separador());
        add(_botaoPrimeiro);
        add(_botaoAnterior);
        add(_botaoProximo);
        add(_botaoUltimo);
        add(new Separador());
        add(_botaoFechar);
    }
    
} ///:~
