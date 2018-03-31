package com.jack.kxb.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.kxb.dao.custom.KxWinningRepositoryCustom;
import com.jack.kxb.model.KxWinning;

@Repository
public interface KxWinningRepository extends CrudRepository<KxWinning, Long>,KxWinningRepositoryCustom {

}
