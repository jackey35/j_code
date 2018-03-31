package com.jack.kxb.dao.custom;

import com.jack.kxb.model.AdmUser;

public interface AdmUserRepositoryCustom  {
	public AdmUser getAdmUserByUserNamePass(String userName,String pass);
}
