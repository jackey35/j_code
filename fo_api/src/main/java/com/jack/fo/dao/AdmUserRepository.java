package com.jack.fo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.fo.dao.custom.AdmUserRepositoryCustom;
import com.jack.fo.model.AdmUser;
@Repository
public interface AdmUserRepository extends CrudRepository<AdmUser, Long> ,AdmUserRepositoryCustom{

}
