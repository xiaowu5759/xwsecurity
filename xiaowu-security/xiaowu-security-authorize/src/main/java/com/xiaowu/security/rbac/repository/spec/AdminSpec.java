package com.xiaowu.security.rbac.repository.spec;


import com.xiaowu.security.rbac.domain.Admin;
import com.xiaowu.security.rbac.dto.AdminCondition;
import com.xiaowu.security.rbac.repository.support.ImoocSpecification;
import com.xiaowu.security.rbac.repository.support.QueryWraper;

/**
 * @author TiHom
 *
 */
public class AdminSpec extends ImoocSpecification<Admin, AdminCondition> {

	public AdminSpec(AdminCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Admin> queryWraper) {
		addLikeCondition(queryWraper, "username");
	}

}
