package com.example.copytable.listener;

import com.example.copytable.model.postgre.Department;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;


public class CustomItemReaderListener implements ItemReadListener<Department> {
    @Override
    public void beforeRead() {
        System.out.println("before read ");
    }

    @Override
    public void afterRead(Department item) {
        System.out.println("after read "+ item);
    }

    @Override
    public void onReadError(Exception ex) {
        System.out.println(" exception");
    }
}
