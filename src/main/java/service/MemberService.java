package service;

import dao.MemberDao;
import model.Address;
import model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Member update(Long id,Member member) {
        Member updated = dao.findById(id);
        if (updated == null) return null;
        return updated;
    }

    @Transactional
    public Member patch(Long id, Map<String, String> fields) {
        Member updated=dao.patch(id,fields);
        if(updated == null) return null;
        return updated;
}

    @Transactional
    public boolean delete(Long id) {
        return dao.delete(id);
    }
    }

