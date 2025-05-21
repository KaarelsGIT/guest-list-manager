package com.kaarel.guets_list.guestlist_app.controller;

import com.kaarel.guets_list.guestlist_app.dto.GuestListDTO;
import com.kaarel.guets_list.guestlist_app.dto.GuestRequestDTO;
import com.kaarel.guets_list.guestlist_app.dto.GuestResponseDTO;
import com.kaarel.guets_list.guestlist_app.model.Guest;
import com.kaarel.guets_list.guestlist_app.service.GuestService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/guest")
@AllArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping("/add")
    public ResponseEntity<GuestResponseDTO> addGuest(@Valid @RequestBody GuestRequestDTO guestDTO) {
        try {
            Guest newGuest = new Guest();
            newGuest.setName(guestDTO.getName());
            newGuest.setTransportation(guestDTO.isTransportation());
            newGuest.setStatus(guestDTO.getStatus());

            Guest savedGuest = guestService.save(newGuest);

            GuestResponseDTO responseDTO = new GuestResponseDTO(
                    savedGuest.getId(),
                    savedGuest.getName(),
                    savedGuest.isTransportation(),
                    savedGuest.getStatus() != null ? savedGuest.getStatus().name() : null
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<GuestListDTO> getAllGuests(
            @RequestParam(required = false, defaultValue = "asc") String sort,
            @RequestParam(required = false, defaultValue = "all") String filter
    ) {
        try {
            Iterable<Guest> guests;
            if (filter.equals("all")) {
                guests = guestService.findAll(sort);
            } else {
                guests = guestService.findByStatus(filter, sort);
            }

            List<Guest> guestList = new ArrayList<>();
            guests.forEach(guestList::add);

            int acceptedCount = (int) guestList.stream()
                    .filter(g -> g.getStatus() != null && g.getStatus().name().equalsIgnoreCase("ACCEPTED"))
                    .count();

            GuestListDTO guestDTO = new GuestListDTO();
            guestDTO.setGuests(guestList);
            guestDTO.setAccepted(acceptedCount);

            return ResponseEntity.ok(guestDTO);
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestResponseDTO> getGuestById(@PathVariable Long id) {
        try {
            Guest guest = guestService.findById(id);

            GuestResponseDTO guestResponseDTO = new GuestResponseDTO(
                    guest.getId(),
                    guest.getName(),
                    guest.isTransportation(),
                    guest.getStatus() != null ? guest.getStatus().name() : null
            );

            return ResponseEntity.ok(guestResponseDTO);
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GuestResponseDTO> updateGuest(
            @PathVariable Long id,
            @RequestBody GuestRequestDTO guestDTO
    ) {
        try {
            Guest existingGuest = guestService.findById(id);

            existingGuest.setName(guestDTO.getName());
            existingGuest.setTransportation(guestDTO.isTransportation());
            existingGuest.setStatus(guestDTO.getStatus());

            Guest savedGuest = guestService.save(existingGuest);

            GuestResponseDTO responseDTO = new GuestResponseDTO(
                    savedGuest.getId(),
                    savedGuest.getName(),
                    savedGuest.isTransportation(),
                    savedGuest.getStatus() != null ? savedGuest.getStatus().name() : null
            );

            return ResponseEntity.ok(responseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
