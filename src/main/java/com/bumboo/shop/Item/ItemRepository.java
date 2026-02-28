package com.bumboo.shop.Item;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findPageBy(Pageable page);
    List<Item> findAllByTitleContains(String keyword);

    @Query(value = "SELECT * FROM item WHERE MATCH(title) AGAINST(:keyword IN NATURAL LANGUAGE MODE)",
            countQuery = "SELECT count(*) FROM item WHERE MATCH(title) AGAINST(:keyword IN NATURAL LANGUAGE MODE)",
            nativeQuery = true)
    Page<Item> fullTextSearch(@Param("keyword") String keyword, Pageable pageable);
}
