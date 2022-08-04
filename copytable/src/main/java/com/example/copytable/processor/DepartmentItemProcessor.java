package com.example.copytable.processor;

import com.example.copytable.model.postgre.Department;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component

public class DepartmentItemProcessor implements ItemProcessor<Department, com.example.copytable.model.sql.Department> {

    @Override
    public com.example.copytable.model.sql.Department process(Department item) throws Exception {



        System.out.println("processor =" +item.getId());
        com.example.copytable.model.sql.Department department= new com.example.copytable.model.sql.Department();

        department.setId(item.getId());
        department.setDeptName(item.getDeptName());

        return department;
    }
}
