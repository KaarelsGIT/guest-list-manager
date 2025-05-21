package com.kaarel.guets_list.guestlist_app.service;

import com.kaarel.guets_list.guestlist_app.model.Guest;
import com.kaarel.guets_list.guestlist_app.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    public Guest save(Guest guest) {
        return guestRepository.save(guest);
    }

    public Iterable<Guest> findAll(String direction) {
        Sort sort = Sort.by("name");
        if (direction.equals("desc")) {
            sort = sort.descending();
        } else if (direction.equals("asc")) {
            sort = sort.ascending();
        }

        return guestRepository.findAll(sort);
    }

    public void delete(Long id) {
        guestRepository.deleteById(id);
    }

    public Guest findById(Long id) {
        return guestRepository.findById(id).orElse(null);
    }

    public Guest findByName(String name) {
        return guestRepository.findByName(name);
    }

    public Iterable<Guest> findByStatus(String status, String direction) {
        Sort sort = Sort.by("name");
        if (direction.equals("desc")) {
            sort = sort.descending();
        } else if (direction.equals("asc")) {
            sort = sort.ascending();
        }

        return guestRepository.findByStatus(status, sort);
    }

}
