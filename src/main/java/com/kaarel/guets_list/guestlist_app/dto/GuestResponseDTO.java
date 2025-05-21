package com.kaarel.guets_list.guestlist_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestResponseDTO {

    private Long id;
    private String name;
    private String status;
}
