package controller;

import model.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import service.AddressService;
import java.net.URI;

@RestController
@RequestMapping("/mypages/addresses")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")

public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        Address address = service.findById(id);
        if(address == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(address);
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address created = service.create(address);
        URI location = URI.create("/mypages/addresses/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }
}
