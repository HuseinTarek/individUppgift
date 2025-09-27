package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import model.Address;
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
}
