package com.project.pin.session;

import com.project.pin.entity.Livro;
import com.project.pin.entity.ItemCompra;
import com.project.pin.repository.LivroRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarrinhoSession {
    @Autowired
    private LivroRepository livroRepository;
    private final List<ItemCompra> itens = new ArrayList<>();

    public void adicionarItem(ItemCompra itemCompra) {
        itens.add(itemCompra);
    }

    public void limparCarrrinho() {
        itens.clear();
    }

    public double calcularItensCarrinho(){
        return itens.stream()
                .mapToDouble(item -> item.getQuantidade() * item.getPrecoUnit())
                .sum();

    }


}