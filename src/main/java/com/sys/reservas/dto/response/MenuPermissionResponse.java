package com.sys.reservas.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuPermissionResponse {
    private Long id;
    private String description;
    private String link;
    private String icon;
    private Long  parentMenuId;
    private Long  order;
    private boolean canCreate;
    private boolean canRead;
    private boolean canUpdate;
    private boolean canDelete;
    private List<MenuPermissionResponse> items = new ArrayList<>();

    public MenuPermissionResponse(
            Long id,
            String description,
            String link,
            String icon,
            Long  parentMenuId,
            Long  order,
            boolean canCreate,
            boolean canRead,
            boolean canUpdate,
            boolean canDelete
    ) {
        this.id = id;
        this.description = description;
        this.link = link;
        this.icon = icon;
        this.parentMenuId = parentMenuId;
        this.order = order;
        this.canCreate =canCreate;
        this.canRead = canRead;
        this.canUpdate = canUpdate;
        this.canDelete = canDelete;
    }
}
