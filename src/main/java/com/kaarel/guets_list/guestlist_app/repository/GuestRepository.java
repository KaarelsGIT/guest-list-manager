package com.kaarel.guets_list.guestlist_app.repository;

import com.kaarel.guets_list.guestlist_app.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
