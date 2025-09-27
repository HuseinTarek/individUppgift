package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.Address;
import model.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressDao {

    @PersistenceContext
    private EntityManager em;

    public AddressDao() {
    }

//    public List<Address> findAll() {
//
//    }

    public Address findById(Long id) {
        return em.find(Address.class, id);
    }

    public Address create(Address address) {
        return em.merge(address);
    }

    public List<Member> findMembersByAddressId(Long addressId) {
        return em.createQuery(
                        "SELECT m FROM Member m " +
                                "LEFT JOIN FETCH m.address a " +
                                "WHERE m.address.id = :addressId " +
                                "ORDER BY m.id", Member.class)
                .setParameter("addressId", addressId)
                .getResultList();
    }
}
