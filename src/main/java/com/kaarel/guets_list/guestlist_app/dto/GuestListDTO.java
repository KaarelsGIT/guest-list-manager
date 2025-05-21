package com.kaarel.guets_list.guestlist_app.dto;

import com.kaarel.guets_list.guestlist_app.model.Guest;
import lombok.Data;

import java.util.List;

@Data
public class GuestListDTO {

    private List<Guest> guests;
    private int accepted;
}
