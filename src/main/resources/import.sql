INSERT INTO salas (id, nombre, aforo, observaciones) VALUES(1, 'Ordenadores', 23, 'Esta libre');
INSERT INTO salas (id, nombre, aforo, observaciones) VALUES(2, 'G3', 887, 'Falta luz');
INSERT INTO salas (id, nombre, aforo, observaciones) VALUES(3, 'G4', 34, 'Hay que limpiarla');

INSERT INTO productos (id, nombre, cantidad, foto, observaciones) VALUES(1, 'Pelota', 34, '', 'Esta pinchada');
INSERT INTO productos (id, nombre, cantidad, foto, observaciones) VALUES(2, 'Burro', 1, '', 'Buen estado');

INSERT INTO roles (id,rol,nombre) VALUES (1, 'ADMIN', 'ADMINISTRADOR');
INSERT INTO roles (id,rol,nombre) VALUES (2, 'USER', 'USUARIO');

INSERT INTO usuarios (id, usuario, rol_id, email, password, nombrecompleto) VALUES(1, 'ldiazcas', 1 , 'ldiaz@gmail.com', '1234', 'Luis Diaz Casas');
INSERT INTO usuarios (id, usuario, rol_id, email, password, nombrecompleto) VALUES(2, 'mmdiaz', 2 , 'mmdiaz@gmail.com', '3210', 'Luis Casas Diaz');
