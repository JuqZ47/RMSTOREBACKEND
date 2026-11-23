-- Insertar Categorías principales
INSERT INTO categoria (id, nombre) VALUES (1, 'Star Wars');
INSERT INTO categoria (id, nombre) VALUES (2, 'Technic');
INSERT INTO categoria (id, nombre) VALUES (3, 'Harry Potter');
INSERT INTO categoria (id, nombre) VALUES (4, 'City');
INSERT INTO categoria (id, nombre) VALUES (5, 'Icons');
INSERT INTO categoria (id, nombre) VALUES (6, 'Marvel');

-- Insertar Productos iniciales para alimentar el Frontend
INSERT INTO producto (nombre, descripcion, precio, stock, imagen_url, categoria_id)
VALUES ('Lego Star Wars - Millennium Falcon', 'Set coleccionable de la icónica nave con mini figuras de Chewbacca, Han Solo y más.', 169990, 12, 'https://i5.walmartimages.com/asr/625df3e7-f408-4b69-a52a-4392886a5291.ca5a8df751327ab30c7bb0996141f533.jpeg', 1);

INSERT INTO producto (nombre, descripcion, precio, stock, imagen_url, categoria_id)
VALUES ('Lego Technic - Ferrari Daytona SP3', 'Modelo a escala 1:8 con caja de cambios secuencial de 8 velocidades y motor V12.', 349990, 5, 'https://www.tiendalego.cl/cdn/shop/products/42143_box_1000x1000.jpg?v=1773694256', 2);

INSERT INTO producto (nombre, descripcion, precio, stock, imagen_url, categoria_id)
VALUES ('Lego Harry Potter - Castillo de Hogwarts', 'Microescala del legendario castillo con aulas, torres y el Gran Comedor.', 399990, 8, 'https://www.tiendalego.cl/cdn/shop/products/71043_box_1000x1000.jpg?v=1745256791', 3);

INSERT INTO producto (nombre, descripcion, precio, stock, imagen_url, categoria_id)
VALUES ('Lego City - Estación de Bomberos', 'Cuartel de bomberos de 3 niveles con camión, helicóptero y 5 minifiguras.', 49990, 20, 'https://m.media-amazon.com/images/I/81HYayz4mrL.jpg', 4);

INSERT INTO producto (nombre, descripcion, precio, stock, imagen_url, categoria_id)
VALUES ('Lego Icons - Ramo de Flores Silvestres', 'Arreglo floral realista construido completamente con piezas LEGO.', 59990, 15, 'https://http2.mlstatic.com/D_NQ_NP_2X_702394-MLA99937500525_112025-F.webp', 5);

INSERT INTO producto (nombre, descripcion, precio, stock, imagen_url, categoria_id)
VALUES ('Lego Marvel - Guantelete del Infinito', 'Recreación dorada del Guantelete de Thanos con las 6 Gemas del Infinito.', 79990, 10, 'https://http2.mlstatic.com/D_NQ_NP_963272-CBT110839248748_052026-O-lego-marvel-super-heroes-76191-guantelete-del-infinito.webp', 6);