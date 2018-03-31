package com.jack.kxb.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.kxb.dao.custom.KxUserRepositoryCustom;
import com.jack.kxb.model.KxUser;

@Repository
public interface KxUserRepository extends KxUserRepositoryCustom, CrudRepository<KxUser, Long> {

}
