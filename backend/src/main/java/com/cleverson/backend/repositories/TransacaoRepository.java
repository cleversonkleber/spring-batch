package com.cleverson.backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cleverson.backend.entity.Transacao;

public interface TransacaoRepository extends CrudRepository<Transacao, Long> {

    // select * from transacao order by nome_oja asc abd id desc
    List<Transacao> findAllByOrderByNomeDaLojaAscIdDesc();

}
