package com.xiaowu.security.rbac.repository;



import com.xiaowu.security.rbac.domain.Resource;
import org.springframework.stereotype.Repository;

/**
 * @author TiHom
 *
 */
@Repository
public interface ResourceRepository extends ImoocRepository<Resource> {

	Resource findByName(String name);

}
