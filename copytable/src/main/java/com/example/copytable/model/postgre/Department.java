package com.example.copytable.model.postgre;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "department")
@Data
public class Department {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name="dept_name")
    private String deptName;
}
