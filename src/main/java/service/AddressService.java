package service;

import dao.AddressDao;
import model.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {
    private final AddressDao dao;

    public AddressService(AddressDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Address findById(Long id) {
       return dao.findById(id);

    }

//    // jag antar att bara chef kan få göra detta men det krävs inte på uppgiften
//    @Transactional(readOnly = true)
//    public List<Address> findAll() {
//        return dao.findAll();
//    }

    @Transactional
    public Address create(Address address) {
        return dao.create(address);
    }
}
