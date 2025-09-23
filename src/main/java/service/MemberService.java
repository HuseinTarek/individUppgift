package service;

import dao.MemberDao;
import org.springframework.transaction.annotation.Transactional;
import model.Member;

import java.util.List;

public class MemberService {
    private final MemberDao dao;

    public MemberService(MemberDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public List<Member> findAllWithAddresses() {
        return dao.findAllWithAddresses();
    }
}
