package controller;

import model.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/admin/members")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> allMembers= service.findAllWithAddresses();
        return ResponseEntity.ok(allMembers);
    }




}
