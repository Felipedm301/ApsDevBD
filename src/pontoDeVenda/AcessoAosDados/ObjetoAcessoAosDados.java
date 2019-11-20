
package pontoDeVenda.AcessoAosDados;

import java.util.List;

public interface ObjetoAcessoAosDados<T>{
    
    public abstract T ObterPeloIdentificador(Integer id) throws Throwable;

    public abstract List<T> ObterTodosItens() throws Throwable;
    
    
}
