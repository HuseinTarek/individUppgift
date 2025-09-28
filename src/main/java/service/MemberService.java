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
        // First, load the existing member with its address
        Member existing = dao.findById(id);
        if (existing == null) return null;

        // Update the fields from the incoming member object
        existing.setFirstName(member.getFirstName());
        existing.setLastName(member.getLastName());
        existing.setEmail(member.getEmail());
        existing.setPhone(member.getPhone());
        existing.setDateOfBirth(member.getDateOfBirth());
        existing.setRole(member.getRole());
        
        // Handle address update
        if (member.getAddress() != null) {
            // If the member already has an address, update its fields
            if (existing.getAddress() != null && member.getAddress().getId() != null) {
                Address existingAddress = existing.getAddress();
                Address newAddress = member.getAddress();
                existingAddress.setStreet(newAddress.getStreet());
                existingAddress.setPostalCode(newAddress.getPostalCode());
                existingAddress.setCity(newAddress.getCity());
            } else {
                // If no existing address or new address has no ID, set the new address
                existing.setAddress(member.getAddress());
            }
        }
        
        // Save and return the updated member
        return dao.update(existing);
    }

    @Transactional
    public Member patch(Long id, Map<String, String> fields) {
        Member updated = dao.findById(id);
        if (updated == null) return null;

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
            }
        });
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

