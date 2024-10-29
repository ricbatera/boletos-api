insert ignore into tb_permissoes (permissao_id, permissao) values (1, 'admin');
insert ignore into tb_permissoes (permissao_id, permissao) values (2, 'usuario');
insert ignore into tb_permissoes (permissao_id, permissao) values (3, 'suporte_n2');
insert ignore into tb_permissoes (permissao_id, permissao) values (4, 'suporte_n3');

insert ignore into tb_tipo_boleto (tipo_boleto_id, descricao, ativo, value) values(1, "Boleto de Servicos", true, 0);
insert ignore into tb_tipo_boleto (tipo_boleto_id, descricao, ativo, value) values(2, "Boleto Comum com Itens", true, 2);
insert ignore into tb_tipo_boleto (tipo_boleto_id, descricao, ativo, value) values(3, "Boleto Comum sem Itens", false, 1);