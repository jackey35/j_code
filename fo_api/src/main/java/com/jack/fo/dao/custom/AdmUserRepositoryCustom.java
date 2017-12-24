package com.jack.fo.dao.custom;



import com.jack.fo.model.AdmUser;

public interface AdmUserRepositoryCustom  {
	public AdmUser getAdmUserByUserNamePass(String userName,String pass);
}
