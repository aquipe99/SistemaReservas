package com.sys.reservas.repository;

import com.sys.reservas.dto.response.MenuPermissionDTO;
import com.sys.reservas.entity.Menu;
import com.sys.reservas.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Long> {
    @Query("SELECT rm.menu FROM RoleMenu rm WHERE rm.role.id = :roleId")
    List<Menu> findMenusByRoleId(@Param("roleId") Long roleId);

    @Query("""
    SELECT new com.sys.reservas.dto.response.MenuPermissionDTO(
        m.id, m.description, m.link, m.icon, rm.canCreate, rm.canRead, rm.canUpdate, rm.canDelete
    )
    FROM RoleMenu rm
    JOIN rm.menu m
    WHERE rm.role.id = :roleId
    """)
    List<MenuPermissionDTO> findMenusWithPermissionsByRoleId(@Param("roleId") Long roleId);

    @Query("""
    SELECT CASE WHEN COUNT(rm) > 0 THEN TRUE ELSE FALSE END
    FROM RoleMenu rm
    JOIN rm.role r
    JOIN rm.menu m
    WHERE r.name = :roleName
      AND m.link = :menu
      AND (
          (:action = 'CREATE' AND rm.canCreate = true) OR
          (:action = 'READ' AND rm.canRead = true) OR
          (:action = 'UPDATE' AND rm.canUpdate = true) OR
          (:action = 'DELETE' AND rm.canDelete = true)
      )
""")
    boolean existsByRoleNameAndMenuAndAction(@Param("roleName") String roleName,
                                             @Param("menu") String menu,
                                             @Param("action") String action);
}
