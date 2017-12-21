package com.jack.fo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.fo.dao.custom.AppUserRepositoryCustom;
import com.jack.fo.model.AppUser;


@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long>, AppUserRepositoryCustom {

}
