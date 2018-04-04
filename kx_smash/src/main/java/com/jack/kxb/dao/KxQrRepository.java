package com.jack.kxb.dao;

import org.springframework.data.repository.CrudRepository;

import com.jack.kxb.dao.custom.KxQrRepositoryCustom;
import com.jack.kxb.model.KxQr;

public interface KxQrRepository extends CrudRepository<KxQr, Long>, KxQrRepositoryCustom {

}
