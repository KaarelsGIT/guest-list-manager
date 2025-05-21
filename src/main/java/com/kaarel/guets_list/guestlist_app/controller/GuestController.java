package com.kaarel.guets_list.guestlist_app.controller;

import com.kaarel.guets_list.guestlist_app.model.Guest;
import com.kaarel.guets_list.guestlist_app.service.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/guest")
@AllArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<Guest>> getAllGuests(
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false, defaultValue = "all") String status
    ) {
        try {
            Iterable<Guest> guests;
            if (status.equals("all")) {
                guests = guestService.findAll(direction);
            } else {
                guests = guestService.findByStatus(status, direction);
            }

            List<Guest> guestList =  new ArrayList<>();
            guests.forEach(guestList::add);
            return ResponseEntity.ok(guestList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Guest> addGuest(@RequestBody Guest guest) {
        try {
            return ResponseEntity.ok(guestService.save(guest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGuest(@PathVariable Long id) {
        try {
            Guest guest = guestService.findById(id);
            guestService.delete(id);
            String message = String.format("Guest %s has been deleted successfully.", guest.getName());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to deleting guest.");
        }
    }
}
