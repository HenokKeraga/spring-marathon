package com.example.copydatatwo.batch;

import com.example.copydatatwo.model.postgre.Department;
import org.springframework.batch.item.ItemProcessor;

public class DepartmentIemProcessor implements ItemProcessor<Department, com.example.copydatatwo.model.sql.Department> {
    @Override
    public com.example.copydatatwo.model.sql.Department process(Department item) throws Exception {
        com.example.copydatatwo.model.sql.Department department= new com.example.copydatatwo.model.sql.Department(){{
            setId(item.getId());
            setDeptName(item.getDeptName());
        }};

        return department;
    }
}
