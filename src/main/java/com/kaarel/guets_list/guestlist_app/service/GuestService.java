package com.kaarel.guets_list.guestlist_app.service;

import com.kaarel.guets_list.guestlist_app.model.Guest;
import com.kaarel.guets_list.guestlist_app.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    public Guest save(Guest guest) {
        return guestRepository.save(guest);
    }

    public Iterable<Guest> findAll() {
        return guestRepository.findAll();
    }

    public void delete(Long id) {
        guestRepository.deleteById(id);
    }

    public Guest findById(Long id) {
        return guestRepository.findById(id).orElse(null);
    }


}
