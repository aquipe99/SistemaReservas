package com.sys.reservas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourtResponse {
    private Long id;
    private String name;
    private String description;
    private Boolean status;
}
