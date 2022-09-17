package com.example.extractcsvfile.repo;

import com.example.extractcsvfile.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
@Repository
public class ProductRepository<T> {

    final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    Map<String,String> map= Map.ofEntries(Map.entry("product","select id, name, price\n" +
            "from product"));


    public <U> void extractRecords(String table, Function<T, U> processor, Consumer<List<U>> consumer) {

        var sqlQuery = map.get(table);


        queryAndProcessRecords(sqlQuery,null,resultSet -> {
            try {
                return createRecord(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        },processor,consumer,2);

    }

    private T  createRecord(ResultSet resultSet) throws SQLException {

        return (T) Product.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .price(resultSet.getInt("price"))
                .build();
    }

    public <U> void queryAndProcessRecords(String sqlQuery, Map<String, ?> params, Function<ResultSet, T> rowMapper
            , Function<T, U> processor, Consumer<List<U>> consumer, int batchSize) {
        var resultList = new ArrayList<U>();

       var rp= new RowCallbackHandler(){

            @Override
            public void processRow(ResultSet rs) throws SQLException {

            }
        };

       Consumer<ResultSet> c= dd -> customRowCallbackHandler(dd,rowMapper, processor, consumer, batchSize,resultList);

        namedParameterJdbcTemplate
                .query(sqlQuery, params, rs -> {
                    customRowCallbackHandler(rs, rowMapper, processor, consumer, batchSize,resultList);
                });

    }

    private <U> void customRowCallbackHandler(ResultSet rs, Function<ResultSet, T> rowMapper, Function<T, U> processor, Consumer<List<U>> consumer, int batchSize,List<U> resultList) {

        var record = rowMapper.apply(rs);
        var result = processor.apply(record);

        resultList.add(result);

        if (resultList.size() >= batchSize) {
            consumer.accept(resultList);
            resultList.clear();
        }
//        if (resultList.size() > 0) {
//            consumer.accept(resultList);
//            resultList.clear();
//        }
    }

}

