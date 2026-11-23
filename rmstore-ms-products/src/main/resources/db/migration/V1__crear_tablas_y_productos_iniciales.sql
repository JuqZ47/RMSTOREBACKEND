-- Migración versionada: se ejecuta UNA vez por base de datos mediante Flyway.
CREATE TABLE productos (
  id VARCHAR(32) NOT NULL PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  categoria VARCHAR(60) NOT NULL,
  precio INT NOT NULL,
  stock INT NOT NULL,
  stock_critico INT NOT NULL,
  descripcion VARCHAR(2000),
  imagen VARCHAR(2048),
  INDEX idx_productos_categoria (categoria),
  CONSTRAINT chk_precio CHECK (precio >= 0),
  CONSTRAINT chk_stock CHECK (stock >= 0),
  CONSTRAINT chk_stock_critico CHECK (stock_critico >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE producto_imagenes (
  producto_id VARCHAR(32) NOT NULL,
  orden INT NOT NULL,
  imagen_url VARCHAR(2048) NOT NULL,
  PRIMARY KEY (producto_id, orden),
  CONSTRAINT fk_imagen_producto FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO productos (id, nombre, categoria, precio, stock, stock_critico, descripcion, imagen) VALUES ('LEG-001', 'Lego Star Wars - Millennium Falcon', 'Star Wars', 169990, 12, 3, 'Set coleccionable de la icónica nave con mini figuras de Chewbacca, Han Solo y más.', 'https://i5.walmartimages.com/asr/625df3e7-f408-4b69-a52a-4392886a5291.ca5a8df751327ab30c7bb0996141f533.jpeg');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-001', 0, 'https://images-na.ssl-images-amazon.com/images/I/91FoS63ClXL.jpg');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-001', 1, 'https://www.tiendalego.cl/cdn/shop/products/75192_prod14_851x851.jpg?v=1745255859');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-001', 2, 'https://www.tiendalego.cl/cdn/shop/products/75192_prod_1000x1000.jpg?v=1773427616');

INSERT INTO productos (id, nombre, categoria, precio, stock, stock_critico, descripcion, imagen) VALUES ('LEG-002', 'Lego Technic - Ferrari Daytona SP3', 'Technic', 349990, 5, 2, 'Modelo a escala 1:8 con caja de cambios secuencial de 8 velocidades y motor V12.', 'https://www.tiendalego.cl/cdn/shop/products/42143_box_1000x1000.jpg?v=1773694256');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-002', 0, 'https://www.tiendalego.cl/cdn/shop/products/42143_boxprod_1000x1000.jpg?v=1773694256');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-002', 1, 'https://www.tiendalego.cl/cdn/shop/products/42143_prod_1000x1000.jpg?v=1773694256');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-002', 2, 'https://www.tiendalego.cl/cdn/shop/products/42143_back_03_1000x1000.jpg?v=1773694256');

INSERT INTO productos (id, nombre, categoria, precio, stock, stock_critico, descripcion, imagen) VALUES ('LEG-003', 'Lego Harry Potter - Castillo de Hogwarts', 'Harry Potter', 399990, 8, 2, 'Microescala del legendario castillo con aulas, torres y el Gran Comedor.', 'https://www.tiendalego.cl/cdn/shop/products/71043_box_1000x1000.jpg?v=1745256791');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-003', 0, 'https://www.tiendalego.cl/cdn/shop/products/71043_boxprod_851x851.jpg?v=1745256791');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-003', 1, 'https://www.tiendalego.cl/cdn/shop/products/71043_prod_851x851.jpg?v=1745256791');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-003', 2, 'https://www.tiendalego.cl/cdn/shop/products/71043_back_01_851x851.jpg?v=1745256791');

INSERT INTO productos (id, nombre, categoria, precio, stock, stock_critico, descripcion, imagen) VALUES ('LEG-004', 'Lego City - Estación de Bomberos', 'City', 49990, 20, 5, 'Cuartel de bomberos de 3 niveles con camión, helicóptero y 5 minifiguras.', 'https://m.media-amazon.com/images/I/81HYayz4mrL.jpg');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-004', 0, 'https://m.media-amazon.com/images/I/81HYayz4mrL.jpg');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-004', 1, 'https://cdn.toypro.com/media/cache/tp_product_detail/uploads/images/product/10/19006.webp');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-004', 2, 'https://images-na.ssl-images-amazon.com/images/I/81fm8ea5j6L.jpg');

INSERT INTO productos (id, nombre, categoria, precio, stock, stock_critico, descripcion, imagen) VALUES ('LEG-005', 'Lego Icons - Ramo de Flores Silvestres', 'Icons', 59990, 15, 4, 'Arreglo floral realista construido completamente con piezas LEGO.', 'https://http2.mlstatic.com/D_NQ_NP_2X_702394-MLA99937500525_112025-F.webp');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-005', 0, 'https://http2.mlstatic.com/D_NQ_NP_2X_702394-MLA99937500525_112025-F.webp');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-005', 1, 'https://http2.mlstatic.com/D_NQ_NP_2X_777335-MLA84548544358_052025-F.webp');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-005', 2, 'https://http2.mlstatic.com/D_NQ_NP_2X_842437-MLA84846916913_052025-F.webp');

INSERT INTO productos (id, nombre, categoria, precio, stock, stock_critico, descripcion, imagen) VALUES ('LEG-006', 'Lego Marvel - Guantelete del Infinito', 'Marvel', 79990, 10, 3, 'Recreación dorada del Guantelete de Thanos con las 6 Gemas del Infinito.', 'https://http2.mlstatic.com/D_NQ_NP_963272-CBT110839248748_052026-O-lego-marvel-super-heroes-76191-guantelete-del-infinito.webp');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-006', 0, 'https://http2.mlstatic.com/D_NQ_NP_695847-CBT109845875685_032026-O.webp');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-006', 1, 'https://http2.mlstatic.com/D_NQ_NP_953428-CBT110838674122_052026-O-lego-marvel-super-heroes-76191-guantelete-del-infinito.webp');
INSERT INTO producto_imagenes (producto_id, orden, imagen_url) VALUES ('LEG-006', 2, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzt0ozPMPC83d1vs3MXR7qXIDHOeBNAq3Fk_358gyPBWWqHTAiAVrprGsW&s=10');

