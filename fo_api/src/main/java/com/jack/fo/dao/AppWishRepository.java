package com.jack.fo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.fo.dao.custom.AppWishRepositoryCustom;
import com.jack.fo.model.AppWish;

@Repository
public interface AppWishRepository extends CrudRepository<AppWish, Long>, AppWishRepositoryCustom {

}
