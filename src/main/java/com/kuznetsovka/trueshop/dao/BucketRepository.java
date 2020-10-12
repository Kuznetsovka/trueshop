package com.kuznetsovka.trueshop.dao;

import com.kuznetsovka.trueshop.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
