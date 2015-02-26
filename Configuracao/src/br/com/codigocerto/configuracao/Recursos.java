/*
 * Recursos.java
 *
 * Criado em 31 de Maio de 2007, 13:56
 *
 */

package br.com.codigocerto.configuracao;

import java.net.URL;

/**
 *
 * @author lis
 */
public class Recursos {
    
    public interface Icones {
            int PRINCIPAL = 0, 
                DIALOGO = 1, 
                FRAME_INTERNO = 2;
    }
    public interface IconesBotoes {
            int NOVO = 0, 
                PESQUISAR = 1, 
                EXCLUIR = 2,
                PRIMEIRO = 3,
                ANTERIOR = 4,
                PROXIMO = 5,
                ULTIMO = 6,
                FECHAR = 7,
                EXIBIR_CONSULTA = 8,
                IMPRIMIR_CONSULTA = 9;
    }
    public interface IconesCadastros {
            int CLIENTES = 0, 
                USUARIOS = 1,
                PRODUTOS = 2,
                SERVICOS = 3;
    }
    public interface IconesMovimentos {
            int CAIXA = 0,
                PEDIDO = 1,
                ORCAMENTO = 2,
                CREDITO = 3,
                EXPEDICAO = 4,
                FATURAMENTO = 5;
    }
    public interface IconesAvisos {
            int AVISO = 0,
                PERGUNTA = 1;
    }
    public interface IconesAcoes {
            int SAIR = 0;
    }
    public interface Logotipos {
            int PEQUENO = 0;
    }
    
    private final String _caminhoRecursos = "/";
    
    /** Cria uma nova instância de Recursos */
    public Recursos() {
    }

    /**
     *Retorna URL para formulario 
     *@param indiceIcone identificacao do icone de formulario
     *@return URL imagem do icone selecionado
     */
    public URL getIcone(int indiceIcone) {
        StringBuilder caminhoIcone = new StringBuilder(_caminhoRecursos + "Imagens/");
        switch (indiceIcone) {
            case Icones.PRINCIPAL: 
                caminhoIcone.append("logo_cc_32.png");
                break;
            case Icones.DIALOGO: 
                caminhoIcone.append("logo_cc_32.png");
                break;
            case Icones.FRAME_INTERNO: 
                caminhoIcone.append("logo_cc_16.png");
                break;
        }
        
        return getClass().getResource(caminhoIcone.toString());
    }

    /**
     *Retorna URL para botao de ferramentas 
     *@param indiceIcone identificacao do icone de botao
     *@return URL imagem do icone selecionado
     */
    public URL getIconeBotao(int indiceIcone) {
        StringBuilder caminhoIcone = new StringBuilder(_caminhoRecursos + "Imagens/");
        switch (indiceIcone) {
            case IconesBotoes.NOVO: 
                caminhoIcone.append("bt_novo.gif");
                break;
            case IconesBotoes.PESQUISAR: 
                caminhoIcone.append("bt_pesquisar.gif");
                break;
            case IconesBotoes.EXCLUIR: 
                caminhoIcone.append("bt_excluir.gif");
                break;
            case IconesBotoes.PRIMEIRO: 
                caminhoIcone.append("bt_primeiro.gif");
                break;
            case IconesBotoes.ANTERIOR: 
                caminhoIcone.append("bt_anterior.gif");
                break;
            case IconesBotoes.PROXIMO: 
                caminhoIcone.append("bt_proximo.gif");
                break;
            case IconesBotoes.ULTIMO: 
                caminhoIcone.append("bt_ultimo.gif");
                break;
            case IconesBotoes.FECHAR: 
                caminhoIcone.append("bt_fechar.gif");
                break;
            case IconesBotoes.EXIBIR_CONSULTA: 
                caminhoIcone.append("bt_exibirconsulta.gif");
                break;
            case IconesBotoes.IMPRIMIR_CONSULTA: 
                caminhoIcone.append("bt_imprimirconsulta.gif");
                break;
        }
        
        return getClass().getResource(caminhoIcone.toString());
    }
    
    /**
     *Retorna URL para formulario de cadastro
     *@param indiceIcone identificacao do icone de formulario
     *@return URL imagem do icone selecionado
     */
    public URL getIconeCadastro(int indiceIcone) {
        StringBuilder caminhoIcone = new StringBuilder(_caminhoRecursos + "Imagens/");
        switch (indiceIcone) {
            case IconesCadastros.CLIENTES: 
                caminhoIcone.append("cad_cliente.gif");
                break;
            case IconesCadastros.USUARIOS: 
                caminhoIcone.append("cad_usuario.gif");
                break;
            case IconesCadastros.PRODUTOS: 
                caminhoIcone.append("cad_produto.gif");
                break;
            case IconesCadastros.SERVICOS: 
                caminhoIcone.append("cad_servico.gif");
                break;
        }

        return getClass().getResource(caminhoIcone.toString());
    }

    /**
     *Retorna URL para formulario de movimento
     *@param indiceIcone identificacao do icone de formulario
     *@return URL imagem do icone selecionado
     */
    public URL getIconeMovimento(int indiceIcone) {
        StringBuilder caminhoIcone = new StringBuilder(_caminhoRecursos + "Imagens/");
        switch (indiceIcone) {
            case IconesMovimentos.CAIXA: 
                caminhoIcone.append("caixa.gif");
                break;
            case IconesMovimentos.PEDIDO: 
                caminhoIcone.append("pedido.gif");
                break;
            case IconesMovimentos.ORCAMENTO: 
                caminhoIcone.append("orcamento.gif");
                break;
            case IconesMovimentos.CREDITO:
                caminhoIcone.append("bt_credito.gif");
                break;
            case IconesMovimentos.EXPEDICAO:
                caminhoIcone.append("bt_expedicao.gif");
                break;
            case IconesMovimentos.FATURAMENTO:
                caminhoIcone.append("bt_faturamento.gif");
                break;
        }
        return getClass().getResource(caminhoIcone.toString());
    }

    /**
     *Retorna URL para dialogo de aviso
     *@param indiceIcone identificacao do icone da mensagem
     *@return URL imagem do icone selecionado
     */
    public URL getIconeAviso(int indiceIcone) {
        StringBuilder caminhoIcone = new StringBuilder(_caminhoRecursos + "Imagens/");
        switch (indiceIcone) {
            case IconesAvisos.AVISO: 
                caminhoIcone.append("alerta.png");
                break;
            case IconesAvisos.PERGUNTA: 
                caminhoIcone.append("pergunta.png");
                break;
        }
        
        return getClass().getResource(caminhoIcone.toString());
    }

    /**
     *Retorna URL para logotipo
     *@param indice tipo do logotipo
     *@return URL imagem do logotipo
     */
    public URL getLogotipo(int indice) {
        StringBuilder caminho = new StringBuilder(_caminhoRecursos + "Imagens/");
        switch (indice) {
            case Logotipos.PEQUENO: 
                caminho.append("logo_pq_codigocerto.jpg");
                break;
        }
        
        return getClass().getResource(caminho.toString());
    }

    /**
     *Retorna URL para acoes
     *@param indiceIcone identificacao do icone de acao
     *@return URL imagem do icone selecionado
     */
    public URL getIconeAcao(int indiceIcone) {
        StringBuilder caminhoIcone = new StringBuilder(_caminhoRecursos + "Imagens/");
        switch (indiceIcone) {
            case IconesAcoes.SAIR: 
                caminhoIcone.append("sair.gif");
                break;
        }
        
        return getClass().getResource(caminhoIcone.toString());
    }
    
    
} ///:~
