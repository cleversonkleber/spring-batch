package com.cleverson.backend.entity;

import java.math.BigDecimal;
import java.util.List;

public record TransacaoReport(
        BigDecimal total,
        String nome,
        List<Transacao> transacoes) {

  public TransacaoReport addTotal(BigDecimal valor) {
    return new TransacaoReport(this.total().add(valor), this.nome(), this.transacoes);
  }

  public TransacaoReport addTransacao(Transacao transacao) {
    var transacoes = this.transacoes();
    transacoes.add(transacao);
    return new TransacaoReport(this.total(), this.nome(), transacoes);
  }

  public TransacaoReport withNomeDaLoja(String nomeDaLoja) {
    return new TransacaoReport(this.total(), nomeDaLoja, this.transacoes);
  }

}
