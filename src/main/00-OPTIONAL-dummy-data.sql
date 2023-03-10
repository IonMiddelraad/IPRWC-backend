USE iprwc_test;

-- Clear all the tables
SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM orders;
DELETE FROM order_products;
DELETE FROM product;


INSERT INTO product (id, name, description, price) VALUES
    (1, "TestProduct 1", "De erg mooie beschrijving van het product met id nummero 1" , 3.99),
    (2, "TestProduct 2", "De erg lelijke beschrijving van het product met id nummer 2" , 13.99);

INSERT INTO user (id, name, email, password) VALUES
    (1, "root", "root@gmail.com", "$2y$10$KQ./DcmX7u376BPZeWBu.ODkioyq7dOOA6nQV21vAXL1B5FHjU1X2"),
    (2, "Ben", "ben@gmail.com", "$2y$10$NYV5ynpiyHQuquqFhWhiIuSKnUfww5n0rzXvJXnnk92/tOO1eSrhS"),
    (3, "Karel", "karel@gmail.com", "$2y$10$Pk5dKcYHbksfWwYXYzuFc.BZzYkIBLlpn4Wm/KrKjrsHpmZ.iOhwC"),
    (4, "Nick", "nick@gmail.com", "$2y$10$0976M/AIxQNgVdCIvMw4l.gdHOkGQoIaCJQNujJ45mDMXB59M1idi"),
    (5, "Jack", "jack@gmail.com", "$2y$10$UmTqktT/kO9KW19./WIIT.Vwm.B1CJodsN3UixTX5e5FqwZLo89s.");

INSERT INTO employee_roles (employee_id, role_id) VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2);

INSERT INTO orders (id, user_id) VALUES
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 3);

INSERT INTO order_products (order_id, product_id) VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 2),
    (4, 2),
    (4, 1);