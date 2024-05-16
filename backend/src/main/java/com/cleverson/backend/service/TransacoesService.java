package com.cleverson.backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cleverson.backend.entity.TipoTransacao;
import com.cleverson.backend.entity.Transacao;
import com.cleverson.backend.entity.TransacaoReport;
import com.cleverson.backend.repositories.TransacaoRepository;

@Service
public class TransacoesService {

    private final TransacaoRepository repository;

    public TransacoesService(TransacaoRepository repository) {
        this.repository = repository;
    }


  public List<TransacaoReport> getTotaisTransacoesByNomeDaLoja() {
    List<Transacao> transacoes = repository.findAllByOrderByNomeDaLojaAscIdDesc();

    // preserves order
    Map<String, TransacaoReport> reportMap = new LinkedHashMap<>();

    transacoes.forEach(transacao -> {
      var nomeDaLoja = transacao.nomeDaLoja();
      var valor = transacao.valor();

      reportMap.compute(nomeDaLoja, (key, existingReport) -> {
        TransacaoReport report = (existingReport != null) ? existingReport
            : new TransacaoReport(BigDecimal.ZERO, key, new ArrayList<>());
        return report.addTotal(valor).addTransacao(transacao);
      });
    });

    return new ArrayList<>(reportMap.values());
  }
}
