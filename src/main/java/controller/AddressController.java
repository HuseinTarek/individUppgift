package controller;

import model.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AddressService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/mypages/members")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        Address address = service.findById(id);
        if(address == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(address);
    }

//    @GetMapping
//    public ResponseEntity<List<Address>> addresses() {
//        List<Address> addresses = service.findAll();
//        return ResponseEntity.ok(addresses);
//    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address created = service.create(address);
        URI location = URI.create("/mypages/members/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }
}
