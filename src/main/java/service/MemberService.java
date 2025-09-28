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
    public Member patch(Long id, Map<String, String> updates) {
        Member member = dao.findById(id);
        if (member == null) {
            return null;
        }

        // Update member fields
        updates.forEach((key, value) -> {
            if (value == null) return;
            
            switch (key.toLowerCase()) {
                case "firstname":
                    member.setFirstName(value);
                    break;
                case "lastname":
                    member.setLastName(value);
                    break;
                case "email":
                    member.setEmail(value);
                    break;
                case "phonenumber":
                    member.setPhone(value);
                    break;
                // Handle nested address fields
                case "address.street":
                    if (member.getAddress() == null) {
                        member.setAddress(new Address());
                    }
                    member.getAddress().setStreet(value);
                    break;
                case "address.city":
                    if (member.getAddress() == null) {
                        member.setAddress(new Address());
                    }
                    member.getAddress().setCity(value);
                    break;
                case "address.postalcode":
                    if (member.getAddress() == null) {
                        member.setAddress(new Address());
                    }
                    member.getAddress().setPostalCode(value);
                    break;
                case "role":
                    member.setRole(Role.valueOf(value.toUpperCase()));
                    break;
                case "dateofbirth":
                    member.setDateOfBirth(LocalDate.parse(value));
                    break;
            }
        });

        return dao.update(member);
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

