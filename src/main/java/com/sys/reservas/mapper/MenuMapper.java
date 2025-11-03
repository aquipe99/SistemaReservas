package com.sys.reservas.mapper;

import com.sys.reservas.dto.response.MenuResponse;
import com.sys.reservas.entity.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {
    public MenuResponse toDTO(Menu menu){
        MenuResponse dto = new MenuResponse();
        dto.setId(menu.getId());
        dto.setDescription(menu.getDescription());
        dto.setLink(menu.getLink());
        dto.setIcon(menu.getIcon());
        return  dto;
    }

}
