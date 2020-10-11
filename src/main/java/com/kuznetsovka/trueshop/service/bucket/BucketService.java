package com.kuznetsovka.trueshop.service.bucket;



import com.kuznetsovka.trueshop.domain.Bucket;
import com.kuznetsovka.trueshop.domain.User;
import com.kuznetsovka.trueshop.dto.BucketDto;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);

    void addProducts(Bucket bucket, List<Long> productIds);

    BucketDto getBucketByUser(String name);
}
