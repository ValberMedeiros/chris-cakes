
/* SEED DE DADOS NA TABELA DE USUÁRIOS */
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Alex', 'Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Maria', 'Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

/* SEED DE DADOS NA TABELA DE PERMISSÕES */
INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');

INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

/* SEED DE DADOS NA TABELA DE ASSOCIAÇÃO DE USUÁRIOS E PERMISSÕES */
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);

INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

/* SEED DE DADOS NA TABELA DE INGREDIENTES */
INSERT INTO tb_ingredientes (nome, unidade_medida, quantidade_medida, quantidade_em_estoque) VALUES ('Leite condensado', 0, 5, 100);

INSERT INTO tb_ingredientes (nome, unidade_medida, quantidade_medida, quantidade_em_estoque) VALUES ('Creme de leite', 0, 1, 100);

INSERT INTO tb_ingredientes (nome, unidade_medida, quantidade_medida, quantidade_em_estoque) VALUES ('Óleo', 3, 900, 100);

INSERT INTO tb_ingredientes (nome, unidade_medida, quantidade_medida, quantidade_em_estoque) VALUES ('Leite em pó', 4, 380, 100);


