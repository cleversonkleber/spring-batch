package com.cleverson.backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

    public List<TransacaoReport> listTotaisTransacoesPorNOmeDaLoja() {
        var transacoes = repository.findAllByOrderByNomeDaLojaAscIdDesc();
        var reportMap = new LinkedHashMap<String, TransacaoReport>();
        transacoes.forEach(transacao -> extracted(reportMap, transacao));

        return new ArrayList<>(reportMap.values());
    }

    private void extracted(LinkedHashMap<String, TransacaoReport> reportMap, Transacao transacao) {
        String nomeDaLoja = transacao.nomeDaLoja();
        var tipoTransacao = TipoTransacao.findByTipo(transacao.tipo());
        BigDecimal valor = transacao.valor().multiply(
                tipoTransacao.getSinal());
        reportMap.compute(nomeDaLoja, (Key, existingReport) -> {
            var report = (existingReport != null) ? existingReport
                    : new TransacaoReport(BigDecimal.ZERO, Key, new ArrayList<>());

            return report.addtotal(valor).addTransacao(transacao.withValor(valor));
        });
    }
}
