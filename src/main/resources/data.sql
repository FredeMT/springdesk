--INSERT INTO tecnico (imagem, nome, perfil, senha, email) VALUES (NULL, 'Administrador', 'ADMIN', '$2a$12$VDRdIBPlfL9teP8RdvwGp.PAIMXb7qvC.YkViIMiq/j64nfjWw6PO', 'admin@admin.com');
INSERT INTO tecnico (imagem, nome, perfil, senha, email) SELECT 'face0.jpg', 'Administrador', 'ADMIN', '$2a$12$VDRdIBPlfL9teP8RdvwGp.PAIMXb7qvC.YkViIMiq/j64nfjWw6PO', 'admin@admin.com' WHERE NOT EXISTS (SELECT 1 FROM tecnico WHERE email='admin@admin.com');
--INSERT INTO tecnico (imagem, nome, perfil, senha, email) VALUES (NULL, 'Paulo', 'TECNICO', '$2a$12$VzxGJrnSGgRdNo3MbVu/a.4pqiU7x0MfSr2q6ode.A6CGIuG9z3Q2', 'paulo@paulo.com');
INSERT INTO tecnico (imagem, nome, perfil, senha, email) SELECT 'face3.jpg', 'Paulo', 'TECNICO', '$2a$12$VzxGJrnSGgRdNo3MbVu/a.4pqiU7x0MfSr2q6ode.A6CGIuG9z3Q2', 'paulo@paulo.com' WHERE NOT EXISTS (SELECT 1 FROM tecnico WHERE email='paulo@paulo.com');
--INSERT INTO cliente (imagem, nome, perfil, senha, email) VALUES (NULL, 'Maria', 'CLIENTE', '$2a$12$/ceB45SsGvqaLBrkN7SUjuFeU6LiotXA2W1qNN60qr/058bwxI976', 'maria@maria.com');
INSERT INTO cliente (imagem, nome, perfil, senha, email) SELECT 'face2.jpg', 'Maria', 'CLIENTE', '$2a$12$/ceB45SsGvqaLBrkN7SUjuFeU6LiotXA2W1qNN60qr/058bwxI976', 'maria@maria.com' WHERE NOT EXISTS (SELECT 1 FROM cliente WHERE email = 'maria@maria.com');
--INSERT INTO cliente (imagem, nome, perfil, senha, email) VALUES (NULL, 'Carlos', 'CLIENTE', '$2a$12$FwiPVgfx6rN4l93VCNH/u.Zo4vl.VDG56UziIP0V8t5USw0SjpWJK', 'carlos@carlos.com');
INSERT INTO cliente (imagem, nome, perfil, senha, email) SELECT 'face1.jpg', 'Carlos', 'CLIENTE', '$2a$12$FwiPVgfx6rN4l93VCNH/u.Zo4vl.VDG56UziIP0V8t5USw0SjpWJK', 'carlos@carlos.com' WHERE NOT EXISTS (SELECT 1 FROM cliente WHERE email='carlos@carlos.com');

--INSERT INTO chamado (data_abertura, data_fechamento, observacao, prioridade, status, titulo, cliente_id_fk, tecnico_id_fk) VALUES('2023-03-30', '2023-04-01', 'Chamado teste de baixa prioridade.', 'BAIXA', 'FECHADO', 'Teclado falhando', 1, 1);
INSERT INTO chamado (data_abertura, data_fechamento, observacao, prioridade, status, titulo, cliente_id_fk, tecnico_id_fk) SELECT '2023-03-30', '2023-04-01', 'Chamado teste de baixa prioridade.', 'BAIXA', 'FECHADO', 'Teclado falhando', 1, 1 WHERE NOT EXISTS (SELECT 1 FROM chamado WHERE id=1);

--INSERT INTO chamado (data_abertura, data_fechamento, observacao, prioridade, status, titulo, cliente_id_fk, tecnico_id_fk) VALUES('2023-04-15', NULL, 'Chamado teste andamento.', 'ALTA', 'ANDAMENTO', 'Tela azul windows.', 2, 2);
INSERT INTO chamado (data_abertura, data_fechamento, observacao, prioridade, status, titulo, cliente_id_fk, tecnico_id_fk) SELECT '2023-04-15', NULL, 'Chamado teste andamento.', 'ALTA', 'ANDAMENTO', 'Tela azul windows.', 2, 2 WHERE NOT EXISTS (SELECT 1 FROM chamado WHERE id=2);

--INSERT INTO chamado (data_abertura, data_fechamento, observacao, prioridade, status, titulo, cliente_id_fk, tecnico_id_fk) VALUES('2023-04-20', NULL, 'Chamado teste aberto.', 'MEDIA', 'ABERTO', 'Rede  travando.', 2, 1);

INSERT INTO chamado (data_abertura, data_fechamento, observacao, prioridade, status, titulo, cliente_id_fk, tecnico_id_fk) SELECT '2023-04-20', NULL, 'Chamado teste aberto.', 'MEDIA', 'ABERTO', 'Rede  travando.', 2, 1 WHERE NOT EXISTS (SELECT 1 FROM chamado WHERE id=3);


