package com.olx.dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "BLACKLISTED_TOKEN")
public class BlacklistedToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "blacklisted_jwt")
    private String blacklistedJwt;

    public BlacklistedToken() {

    }

    public BlacklistedToken(String blacklistedJwt) {
        this.blacklistedJwt = blacklistedJwt;
    }

    public BlacklistedToken(int id, String blacklistedJwt) {
        this.id = id;
        this.blacklistedJwt = blacklistedJwt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlacklistedJwt() {
        return blacklistedJwt;
    }

    public void setBlacklistedJwt(String blacklistedJwt) {
        this.blacklistedJwt = blacklistedJwt;
    }

    @Override
    public String toString() {
        return "BlacklistedToken{" +
                "id=" + id +
                ", blacklistedJwt='" + blacklistedJwt + '\'' +
                '}';
    }
}
