package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    @PersistenceContext
    private EntityManager em;

    public List<Member> findAllWithAddresses() {
        return em.createQuery("SELECT m FROM Member m Left JOIN FETCH m.address ORDER BY m.id", Member.class)
                .getResultList();
    }

}
