package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m",Member.class).getResultList();
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

    public Member update(Member member) {
        // First merge the member to ensure it's managed
        Member mergedMember = em.merge(member);
        
        // If the member has an address, make sure it's also managed
        if (mergedMember.getAddress() != null && mergedMember.getAddress().getId() == null) {
            // If the address is new, persist it first
            em.persist(mergedMember.getAddress());
        } else if (mergedMember.getAddress() != null) {
            // If the address exists, merge it
            em.merge(mergedMember.getAddress());
        }
        
        return mergedMember;
    }


                public Member findById (Long id){
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
