package com.server.backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "student_application", schema = "public")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class student_application {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_registration_id_seq")
    @SequenceGenerator(name = "application_registration_id_seq", sequenceName = "application_Registration_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    @Column(name = "regid", nullable = false, unique = true, updatable = false)
    private Integer regid;//here

}
