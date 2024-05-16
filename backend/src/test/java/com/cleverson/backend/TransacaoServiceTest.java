package com.cleverson.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleverson.backend.entity.Transacao;
import com.cleverson.backend.repositories.TransacaoRepository;
import com.cleverson.backend.service.TransacoesService;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

        @InjectMocks
        private TransacoesService transacaoService;

        @Mock
        private TransacaoRepository transacaoRepository;

        /**
         * 
         */
        @Test
        public void testGetTotaisTransacoesByNomeDaLoja() {
                final String lojaA = "Loja A", lojaB = "Loja B";

                var transacao1 = new Transacao(
                                1L, 1, new Date(System.currentTimeMillis()),
                                BigDecimal.valueOf(100.00),
                                123456789L, "1234-5678-9012-3456",
                                new Time(System.currentTimeMillis()), "Dono Loja A", lojaA);

                var transacao2 = new Transacao(
                                2L, 1, new Date(System.currentTimeMillis()),
                                BigDecimal.valueOf(50.00),
                                987654321L, "9876-5432-1098-7654",
                                new Time(System.currentTimeMillis()), "Dono Loja B", lojaB);
                var transacao3 = new Transacao(
                                3L, 1, new Date(System.currentTimeMillis()),
                                BigDecimal.valueOf(75.00),
                                111222333L, "1111-2222-3333-4444",
                                new Time(System.currentTimeMillis()), "Dono Loja A", lojaA);

                var mockTransacoes = List.of(transacao1, transacao2, transacao3);

                when(transacaoRepository.findAllByOrderByNomeDaLojaAscIdDesc()).thenReturn(mockTransacoes);

                var reports = transacaoService.getTotaisTransacoesByNomeDaLoja();

                assertEquals(2, reports.size());

                reports.forEach(report -> {
                        if (report.nome().equals(lojaA)) {
                                assertEquals(2, report.transacoes().size());
                                assertEquals(BigDecimal.valueOf(175.00), report.total());
                                assertTrue(report.transacoes().contains(transacao1));
                                assertTrue(report.transacoes().contains(transacao3));
                        } else if (report.nome().equals(lojaB)) {
                                assertEquals(1, report.transacoes().size());
                                assertEquals(BigDecimal.valueOf(50.00), report.total());
                                assertTrue(report.transacoes().contains(transacao2));
                        }
                });

        }

}
