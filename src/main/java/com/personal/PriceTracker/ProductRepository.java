package com.personal.PriceTracker;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {

    Optional<List<ProductEntity>> findAllByFlagEquals(String flag);

    Optional<List<ProductEntity>> findAllByUseridIs(Long id);

    Optional<ProductEntity> findByIdAndUserid(int id, long charId);

    void deleteByIdAndUserid(int id, long chatId);
}
