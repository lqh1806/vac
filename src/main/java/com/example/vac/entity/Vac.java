package com.example.vac.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(
        name = "vac",
        indexes = {
                @Index(
                        name = "uk_vac",
                        columnList = "vac",
                        unique = true
                )
        }
)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vac {

    @Id
    private String vac;
    private String email;
    private String vav;
    private String cid;
    private String crexpdt;
    private String custcod;
    private String custnam;
    private String dbexpdt;
    private String debmax;
    private String debmin;
    private String eftdt;
    private String expdt;
    private String lstdt;
    private String pnrflg;
    private String prodid;
    private String prodnam;
    private String regdt;
    private String regtm;
    private String sid;
    private String uid;
    private String vaind;
    private String vanam;
    private String vastat;
    private String vavtb;
}

