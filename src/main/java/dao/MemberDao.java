package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.Address;
import model.Member;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDao {

    @PersistenceContext
    private EntityManager em;

    public List<Member> findAllWithAddresses() {
        return em.createQuery("SELECT m FROM Member m Left JOIN FETCH m.address ORDER BY m.id", Member.class)
                .getResultList();
    }

    public List<Member> findByAddress(Address address) {
        return em.createQuery("SELECT m FROM Member m WHERE m.address = :address", Member.class)
                .setParameter("address", address)
                .getResultList();
    }

    public Member create(Member m) {
        em.persist(m);
        return m;
    }

    public Member update(Long id, Member updatedMember) {
        Member existing = em.find(Member.class, id);
        if (existing == null) return null;

        if (updatedMember.getFirstName() != null) {
            existing.setFirstName(updatedMember.getFirstName());

            if (updatedMember.getEmail() != null) {
                existing.setEmail(updatedMember.getEmail());

                if (updatedMember.getAddress() != null) {
                    existing.setAddress(updatedMember.getAddress());
                }
            }
        }
        return existing;
    }


                public Member findById (Long id,Member member){
                    return em.find(Member.class, id );
                }

                public Member patch (Long id, Map < String, String > fields){
                    Member member = em.find(Member.class, id);
                    if (member == null) return null;
                    member.setFirstName(fields.get("firstName"));
                    member.setLastName(fields.get("lastName"));
                    member.setEmail(fields.get("email"));
                    member.setPhone(fields.get("phone"));
                    return member;

                }

                public boolean delete (Long id){
                Member member = em.find(Member.class, id);
                    if (member == null) return false;
                    em.remove(member);
                    return true;
                }
            }
