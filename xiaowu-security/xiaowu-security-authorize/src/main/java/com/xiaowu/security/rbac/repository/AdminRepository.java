package com.xiaowu.security.rbac.repository;


import com.xiaowu.security.rbac.domain.Admin;
import org.springframework.stereotype.Repository;

/**
 * @author TiHom
 *
 */
@Repository
public interface AdminRepository extends ImoocRepository<Admin> {

	Admin findByUsername(String username);

}
