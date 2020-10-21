
    create table
        bucket_products (
            bucket_id bigint not null,
            product_id bigint not null)
        engine=InnoDB;
    create table bucket_seq (next_val bigint) engine=InnoDB;
    insert into bucket_seq values ( 1 );
    create table buckets (
            id bigint not null,
            user_id bigint,
            primary key (id))
        engine=InnoDB;
--  Category
    create table
        categories (
            id bigint not null,
            title varchar(255),
            primary key (id))
        engine=InnoDB;
    create table category_seq (next_val bigint) engine=InnoDB;
    insert into category_seq values ( 1 );
--  Comparison
    create table
        comparison_products (
            comparison_id bigint not null,
            product_id bigint not null)
        engine=InnoDB;
    create table comparison_seq (next_val bigint) engine=InnoDB;
    insert into comparison_seq values ( 1 );
    create table
        comparisons (
            id bigint not null,
            user_id bigint,
            primary key (id))
        engine=InnoDB;
--  Favorite
    create table
        favourite_products (
            favourite_id bigint not null,
            product_id bigint not null)
        engine=InnoDB;
    create table favourite_seq (next_val bigint) engine=InnoDB;
    insert into favourite_seq values ( 1 );
    create table
        favourites (
            id bigint not null,
            user_id bigint,
            primary key (id))
        engine=InnoDB;
--  Order
    create table order_details_seq (next_val bigint) engine=InnoDB;
    insert into order_details_seq values ( 1 );
    create table order_seq (next_val bigint) engine=InnoDB;
    insert into order_seq values ( 1 );
    create table
        orders (
            id bigint not null,
            address varchar(255),
            changed datetime(6),
            created datetime(6),
            status varchar(255),
            sum decimal(19,2),
            user_id bigint,
            primary key (id))
        engine=InnoDB;
    create table
        orders_details (
            id bigint not null,
            amount decimal(19,2),
            price decimal(19,2),
            order_id bigint,
            product_id bigint,
            primary key (id))
        engine=InnoDB;
--  Place in Stock
    create table
        place_in_stock (
            id bigint not null,
            place_fld integer not null,
            row_fld integer not null,
            shelf_fld integer not null,
            span_fld integer not null,
            primary key (id))
        engine=InnoDB;
    create table
        place_in_stock_products (
            place_in_stock_id bigint not null,
            product_id bigint not null)
        engine=InnoDB;
    create table place_seq (next_val bigint) engine=InnoDB;
    insert into place_seq values ( 1 );
--  Product
    create table product_seq (next_val bigint) engine=InnoDB;
    insert into product_seq values ( 1 );
    create table
        products (
            id bigint not null,
            price double precision,
            title varchar(255),
            primary key (id))
        engine=InnoDB;
    create table
        products_categories (
            product_id bigint not null,
            category_id bigint not null)
        engine=InnoDB;
    create table
        products_place_in_stock (
            product_id bigint not null,
            place_in_stock_id bigint not null)
        engine=InnoDB;
    create table
        products_suppliers (
            product_id bigint not null,
            supplier_id bigint not null)
        engine=InnoDB;
--  Supplier
    create table supplier_seq (next_val bigint) engine=InnoDB;
    insert into supplier_seq values ( 1 );
    create table
        suppliers (
            id bigint not null,
            title varchar(255),
            primary key (id))
        engine=InnoDB;
    create table
        suppliers_products (
            supplier_id bigint not null,
            product_id bigint not null)
        engine=InnoDB;
--  User
    create table user_seq (next_val bigint) engine=InnoDB;
    insert into user_seq values ( 1 );
    create table
        users (
            id bigint not null,
            archive bit not null,
            email varchar(255),
            name varchar(255),
            password varchar(255),
            role varchar(255),
            bucket_id bigint,
            comparison_id bigint,
            favourite_id bigint,
            primary key (id))
        engine=InnoDB;
-- KEYS
alter table bucket_products add constraint FKBucketToProducts foreign key (product_id) references products (id);
alter table bucket_products add constraint FKProductToBuckets foreign key (bucket_id) references buckets (id);
alter table buckets add constraint FKBucketToUsers foreign key (user_id) references users (id);
alter table comparison_products add constraint FKComparisonToProducts foreign key (product_id) references products (id);
alter table comparison_products add constraint FKProductToComparisons foreign key (comparison_id) references comparisons (id);
alter table comparisons add constraint FKComparisonToUsers foreign key (user_id) references users (id);
alter table favourite_products add constraint FKFavouriteToProducts foreign key (product_id) references products (id);
alter table favourite_products add constraint FKProductToFavourites foreign key (favourite_id) references favourites (id);
alter table favourites add constraint FKFavouriteToUsers foreign key (user_id) references users (id);
alter table orders add constraint FKOrderToUsers foreign key (user_id) references users (id);
alter table orders_details add constraint FKOrderDelailsToOrders foreign key (order_id) references orders (id);
alter table orders_details add constraint FKOrderDelailsToProducts foreign key (product_id) references products (id);
alter table place_in_stock_products add constraint FKPlaceInStockToProducts foreign key (product_id) references products (id);
alter table place_in_stock_products add constraint FKProductToPlaceInStocks foreign key (place_in_stock_id) references place_in_stock (id);
alter table products_categories add constraint FKProductToCategories foreign key (category_id) references categories (id);
alter table products_categories add constraint FKCategoryToProducts foreign key (product_id) references products (id);
alter table products_place_in_stock add constraint FKPlaceInStocksToProducts foreign key (place_in_stock_id) references place_in_stock (id);
alter table products_place_in_stock add constraint FKProductsToPlaceInStocks foreign key (product_id) references products (id);
alter table products_suppliers add constraint FKProductToSuppliers foreign key (supplier_id) references suppliers (id);
alter table products_suppliers add constraint FKSupplierToProducts foreign key (product_id) references products (id);
alter table suppliers_products add constraint FKSuppliersToProducts foreign key (product_id) references products (id);
alter table suppliers_products add constraint FKProductsToSuppliers foreign key (supplier_id) references suppliers (id);
alter table users add constraint FKUsersToBuckets foreign key (bucket_id) references buckets (id);
alter table users add constraint FKUsersToComparisons foreign key (comparison_id) references comparisons (id);
alter table users add constraint FKUsersToFavourites foreign key (favourite_id) references favourites (id);