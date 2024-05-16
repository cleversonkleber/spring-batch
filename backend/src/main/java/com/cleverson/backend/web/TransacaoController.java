package com.cleverson.backend.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleverson.backend.entity.TransacaoReport;
import com.cleverson.backend.service.TransacoesService;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("transacoes")
public class TransacaoController {

    private final TransacoesService service;

    public TransacaoController(TransacoesService service) {
        this.service = service;
    }

    @CrossOrigin(origins = {"http://localhost:9090", "https://frontend-pagnet-caf6.onrender.com"})
    @GetMapping
    List<TransacaoReport> listAll() {
        return service.getTotaisTransacoesByNomeDaLoja();
    }

}
