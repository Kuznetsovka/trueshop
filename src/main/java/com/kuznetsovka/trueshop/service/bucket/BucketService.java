package com.kuznetsovka.trueshop.service.bucket;



import com.kuznetsovka.trueshop.domain.Bucket;
import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.domain.User;
import com.kuznetsovka.trueshop.dto.BucketDto;
import com.kuznetsovka.trueshop.dto.ProductDto;

import javax.transaction.Transactional;
import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);

    void addProducts(Bucket bucket, List<Long> productIds);

    BucketDto getBucketByUser(String name);

    void commitBucketToOrder(String name);

    @Transactional
    void deleteProductFromBucket(String username, Product product);
}
