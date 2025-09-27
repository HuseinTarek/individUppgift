package service;

import dao.MemberDao;
import model.Address;
import model.Member;
import model.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {
    private final MemberDao dao;

    public MemberService(MemberDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public List<Member> findAllWithAddresses() {
        return dao.findAllWithAddresses();
    }




    @Transactional(readOnly=true)
    public List<Member> findByAddress(Address address) {
        return dao.findByAddress(address);
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return dao.findById(id);
    }

    @Transactional
    public Member create(Member m){
        return dao.create(m);
    }

    @Transactional
    public Member update(Long id, Member member) {
        Member updated = dao.findById(id);
        if (updated == null) return null;

        // ✅ Update the fields from the incoming member object
        updated.setFirstName(member.getFirstName());
        updated.setLastName(member.getLastName());
        updated.setEmail(member.getEmail());
        updated.setPhone(member.getPhone());
        updated.setDateOfBirth(member.getDateOfBirth());
        updated.setRole(member.getRole());
        return updated;
    }

    @Transactional
    public Member patch(Long id, Map<String, String> fields) {
        // Find the existing member
        Member updated = dao.findById(id);
        if (updated == null) return null;

        // Apply only the fields that were provided
        fields.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    updated.setFirstName(value);
                    break;
                case "lastName":
                    updated.setLastName(value);
                    break;
                case "email":
                    updated.setEmail(value);
                    break;
                case "phone":
                    updated.setPhone(value);
                    break;
                case "role":
                    updated.setRole(Role.valueOf(value));
                    break;
                case "dateOfBirth":
                    if (value != null) {
                        updated.setDateOfBirth(LocalDate.parse(value));
                    }
                    break;
                // Add other fields as needed
            }
        });

        // No need to call dao.save() - automatic persistence with @Transactional
        return updated;
    }


    @Transactional
    public boolean delete(Long id) {
        return dao.delete(id);
    }

    @Transactional
    public List<Member> findAll() {
        return dao.findAll();
    }
}

