package controller;


import model.Address;
import model.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.MemberService;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/members")
public class AdminController {

    private final MemberService service;

    public AdminController(MemberService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Member>> members(Address address) {
        List<Member> members = service.findByAddress(address);
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
    public ResponseEntity<Member> patchMember(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Member updated = service.patch(id, body);
        if(updated == null) 
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
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
