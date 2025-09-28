package controller;


import model.Address;
import model.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import service.MemberService;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/members")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")

public class AdminController {

    private final MemberService service;

    public AdminController(MemberService service) {
        this.service = service;
    }

    //getting all members
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = service.findAll();
        return ResponseEntity.ok(members);
    }

    // get all members in the same address
    @GetMapping("/address/{addressId}")
    public ResponseEntity<List<Member>> getMembersByAddress(@PathVariable Long addressId) {
        List<Member> members = service.findAllWithAddresses();
        if (members.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(members);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id, @RequestParam(required = false) boolean withAddress) {
        Member member;
        if(withAddress) {
            member = service.findById(id);
        }
        else  {
            member = service.findById(id);
        }
        if(member==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(member);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        Member updated = service.update(id,member);
        if(updated == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Member> patchMember(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            // Flatten the nested address object if present
            Map<String, String> flatUpdates = new java.util.HashMap<>();
            updates.forEach((key, value) -> {
                if (value instanceof Map) {
                    // Handle nested address object
                    if ("address".equals(key)) {
                        Map<?, ?> addressMap = (Map<?, ?>) value;
                        addressMap.forEach((addressKey, addressValue) -> {
                            if (addressValue != null) {
                                flatUpdates.put("address." + addressKey, addressValue.toString());
                            }
                        });
                    }
                } else if (value != null) {
                    flatUpdates.put(key, value.toString());
                }
            });
            
            Member updated = service.patch(id, flatUpdates);
            if(updated == null) 
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member created = service.create(member);
        URI location = URI.create("/admin/members/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
