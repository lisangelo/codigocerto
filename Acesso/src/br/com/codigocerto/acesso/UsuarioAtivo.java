/*
 *
 * Codigo Certo
 *
 * Usuario.java
 *
 * Criado em 30 de Março de 2007, 08:58
 *
 */

package br.com.codigocerto.acesso;


import br.com.codigocerto.controles.RegistroPermissao;
import br.com.codigocerto.controles.RegistroUsuario;
import br.com.codigocerto.modelos.Usuario;
import br.com.codigocerto.registro.*;
import br.com.codigocerto.seguranca.Permissao;

/**
 *
 * @author lis
 */
public class UsuarioAtivo {
    
    private static UsuarioAtivo instance; // singleton 
    private static final String _LOGIN_CC = "codigo";
    private static final String _SENHA_CC = "a22e9c1beddf8b89fb4a9a0a089085c5";
    private static boolean logado = false;
    private static Usuario _usuario;
    
    /**
     * iniciar singleton
     */
    public synchronized static UsuarioAtivo getInstance() 
    {
        if( instance == null ) {
            instance = new UsuarioAtivo();
        }
        return instance;
    }
    
    /*
     *Inicializa todos os dados
     */
    public static void reset() {
        logado = false;
        _usuario = null;
    }
    
    /*
     *Informa nome do usuario ativo
     *@return String com nome do usuario
     */
    public static String getNome() {
        StringBuilder nome = new StringBuilder("");
        if (logado) {
            nome.append(_usuario.getNome());
        }
        return nome.toString();
    }

    /*
     *Informa usuario ativo
     *@return String com nome do usuario
     */
    public static Usuario getUsuarioAtivo() {
        Usuario usuarioAtivo = null;
        if (logado) {
            usuarioAtivo = _usuario;
        }
        return usuarioAtivo;
    }

    /*
     *Configura usuario ativo
     *@param usuario
     */
    public static void setUsuarioAtivo(Usuario usuario) {
        _usuario = usuario;
    }

    /*
     *Informa se usuario esta logado
     *@return verdadeiro para usuario logado
     */
    public static boolean isLogado() {
        return logado;
    }
    
    /*
     *Efetua o login no sistema
     *@param nomeLogin - string com o nome de login
     *@param senha - string com senha de login
     *@return verdadeiro para login efetuado com exito
     */
    public static boolean login(String nomeLogin, String senha) {
        boolean ok = false;
        
        RegistroUsuario registro = new RegistroUsuario();
        _usuario = registro.getDados(nomeLogin, senha);
        if (_usuario != null) {
            ok = true;
            logado = true;
            OcorrenciaLog ocorrencia = new OcorrenciaLog();
            ocorrencia.setUsuario(_usuario);
            ocorrencia.setComponente("LOGIN");
            ocorrencia.setAcao("ENTRADA NO SISTEMA");
            ocorrencia.gerarRegistro();
        }
        
        return ok;
    }

    /*
     *Obter permissao do usuario atual para tarefa
     *@param nome do componente
     *@param sigla da acao
     *@return verdadeiro para permissao concedida
     */
    public static boolean getPermissao(String componente, String acao) {
        boolean ok = true;
        
        if (logado) {
            if (_usuario.getLogin().equals(_LOGIN_CC)) {
                ok = true;
            }
            else {
                Permissao permissao = new Permissao();
                permissao.setUsuario(_usuario.getId());
                permissao.setComponente(componente);
                permissao.setAcao(acao);
                RegistroPermissao registro = new RegistroPermissao();
                ok = registro.getOk(permissao);
            }
        }
        
        return ok;
    }

    /*
     *Obterm permissao do usuario atual para tarefa
     *@param tipo - tipo do formulario
     *@param codigo do form
     *@param verdadeiro para acao local
     *@return verdadeiro para permissao concedida
     */
    public static boolean getPermissao(int tipo, int form, boolean isLocal) {
        boolean ok = false;

        if (logado) {
            if (_usuario.getLogin().equals(_LOGIN_CC)) {
                ok = true;
            }
            else {
                ok = _usuario.getAcesso() == Usuario.Acesso.LIBERADO;
                if (_usuario.getNumeroExcecoes() > 0) {
                    boolean excecaoEncontrada = false;
                    if (isLocal) {
                        excecaoEncontrada = _usuario.isExcecaoLocal(tipo, form);
                    } else {
                        excecaoEncontrada = _usuario.isExcecao(tipo, form);
                    }
                    if (excecaoEncontrada) {
                        ok = !ok;
                    }
                }
            }
        }

        return ok;
    }


}///:~
