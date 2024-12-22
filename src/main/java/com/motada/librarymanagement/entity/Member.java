
package com.motada.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "MEMBERS")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "MEMBERSHIPDATE")
    private Timestamp membershipDate ;

    @Column(name = "CREATEDON")
    private Timestamp createdOn;

    @Column(name = "UPDATEDON")
    private Timestamp updatedOn;

    @Column(name = "DELETED")
    private boolean deleted;

}
