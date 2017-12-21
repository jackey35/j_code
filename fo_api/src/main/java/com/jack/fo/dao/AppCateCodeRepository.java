package com.jack.fo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jack.fo.model.AppCateCode;

@Repository  
public interface AppCateCodeRepository extends CrudRepository<AppCateCode, Long> {

}
