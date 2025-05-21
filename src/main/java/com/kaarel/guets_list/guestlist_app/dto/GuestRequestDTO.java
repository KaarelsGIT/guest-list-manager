package com.kaarel.guets_list.guestlist_app.dto;

import com.kaarel.guets_list.guestlist_app.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestRequestDTO {

    private Long id;
    private String name;
    private boolean transportation;
    private Status status;
}
