package com.cleverson.backend.entity;

import java.math.BigDecimal;
import java.util.List;

public record TransacaoReport(
        BigDecimal total,
        String nome,
        List<Transacao> transacoes) {

    public TransacaoReport addtotal(BigDecimal valor) {
        return new TransacaoReport(this.total().add(valor), this.nome(), this.transacoes());
    }

    public TransacaoReport addTransacao(Transacao transacao) {
        transacoes.add(transacao);
        return new TransacaoReport(total, nome, transacoes);
    }

}
