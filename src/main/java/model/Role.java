package model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

public enum Role {
    USER,
    ADMIN;


    @OneToOne
    private Member member;
}
