package controller;

import model.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.MemberService;

@RestController
@RequestMapping("/mypages/members")
public class MemberController {
private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        Member member = service.findById(id);
        if(member == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(member);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        Member updated = service.update(id,member);
        if(updated == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }







}
