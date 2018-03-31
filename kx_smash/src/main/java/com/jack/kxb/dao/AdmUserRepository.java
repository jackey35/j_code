package com.jack.kxb.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.kxb.dao.custom.AdmUserRepositoryCustom;
import com.jack.kxb.model.AdmUser;
@Repository
public interface AdmUserRepository extends CrudRepository<AdmUser, Long> ,AdmUserRepositoryCustom{

}
