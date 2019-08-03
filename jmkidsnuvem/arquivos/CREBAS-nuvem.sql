
CREATE DATABASE jmkidsnuvem
  WITH OWNER = jmkids
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Portuguese_Brazil.1252'
       LC_CTYPE = 'Portuguese_Brazil.1252'
       CONNECTION LIMIT = -1;
       
CREATE SCHEMA nuvem AUTHORIZATION jmkids;

CREATE SCHEMA quiosque AUTHORIZATION jmkids;




Hibernate: create table nuvem.Brinquedo (id int8 not null, chave varchar(60), dataAquisicao date, dataHoraDesabilitado timestamp, descricao varchar(60), foto BYTEA, infTecnicas text, motivoDesabilitado text, tempoMinimo int4, valorAquisicao float8, versao int4, primary key (id))
Hibernate: create table nuvem.BrinquedoNoQuiosque (id int8 not null, contratado boolean, dataHoraAtivacao timestamp, dataHoraInativacao timestamp, emPausa boolean, emUso boolean, versao int4, brinquedo_id int8, quiosque_id int8, primary key (id))
Hibernate: create table nuvem.Configuracao (id int8 not null, versao int4, primary key (id))
Hibernate: create table nuvem.FormaPagamento (id int8 not null, descricao varchar(60), versao int4, primary key (id))
Hibernate: create table nuvem.Funcionario (id int8 not null, bairro varchar(50), cep varchar(10), complemento varchar(50), cpf varchar(14), ctpsNumero varchar(10), ctpsSerie varchar(10), dataAdmissao date, dataDemissao date, email varchar(150) not null, endereco varchar(100), foneDois varchar(20), foneUm varchar(20), foto BYTEA, nome varchar(255), nomeCidade varchar(50), numero varchar(20), rg varchar(15), senha varchar(64), ufCidade varchar(20), versao int4, primary key (id))
Hibernate: create table nuvem.FuncionarioQuiosque (id int8 not null, versao int4, funcionario_id int8 not null, quiosque_id int8 not null, primary key (id))
Hibernate: create table nuvem.GrupoCliente (id int8 not null, descricao varchar(60), versao int4, primary key (id))
Hibernate: create table nuvem.Promocao (id int8 not null, cnpj varchar(18), cpf varchar(14), dataHoraFim timestamp, dataHoraInicio timestamp, descricao varchar(60), diaDaSemana int4, geral boolean, percentualDesconto float4, versao int4, grupoCliente_id int8, primary key (id))
Hibernate: create table nuvem.PromocaoQuiosque (id int8 not null, versao int4, promocao_id int8, quiosque_id int8, primary key (id))
Hibernate: create table nuvem.Quiosque (id int8 not null, bairro varchar(60), cep varchar(10), chave varchar(60), complemento varchar(80), descricao varchar(60), endereco varchar(100), local varchar(60), nomeCidade varchar(60), numero varchar(20), ufCidade varchar(2), versao int4, primary key (id))
Hibernate: create table nuvem.TabelaPreco (id int8 not null, dataHoraFim timestamp, dataHoraInicio timestamp, descricao varchar(60), geral boolean, tempoFlexibilizacao int4, valorMinutoAdicional float4, versao int4, quiosque_id int8, primary key (id))
Hibernate: create table nuvem.TabelaPrecoTempo (id int8 not null, minutos int4, valor float8, versao int4, tabelaPreco_id int8 not null, primary key (id))
Hibernate: create table nuvem.Usuario (id int8 not null, login varchar(20) not null, nome varchar(60) not null, senha varchar(64) not null, primary key (id))
Hibernate: alter table if exists nuvem.Funcionario drop constraint if exists UK_iq7jks9kmtnmm74tjfp3b80ha
jun 05, 2019 3:43:38 PM org.hibernate.engine.jdbc.spi.SqlExceptionHelper$StandardWarningHandler logWarning
WARN: SQL Warning Code: 0, SQLState: 00000
Hibernate: alter table if exists nuvem.Funcionario add constraint UK_iq7jks9kmtnmm74tjfp3b80ha unique (cpf)
jun 05, 2019 3:43:38 PM org.hibernate.engine.jdbc.spi.SqlExceptionHelper$StandardWarningHandler logWarning
WARN: constraint "uk_iq7jks9kmtnmm74tjfp3b80ha" of relation "funcionario" does not exist, skipping
Hibernate: alter table if exists nuvem.Funcionario drop constraint if exists UK_hujfe9giwd0dwuktb8toq7op7
jun 05, 2019 3:43:38 PM org.hibernate.engine.jdbc.spi.SqlExceptionHelper$StandardWarningHandler logWarning
WARN: SQL Warning Code: 0, SQLState: 00000
jun 05, 2019 3:43:38 PM org.hibernate.engine.jdbc.spi.SqlExceptionHelper$StandardWarningHandler logWarning
WARN: constraint "uk_hujfe9giwd0dwuktb8toq7op7" of relation "funcionario" does not exist, skipping
Hibernate: alter table if exists nuvem.Funcionario add constraint UK_hujfe9giwd0dwuktb8toq7op7 unique (email)
Hibernate: create sequence nuvem.BrinquedoID start 1 increment 1
Hibernate: create sequence nuvem.BrinquedoNoQuiosqueID start 1 increment 1
Hibernate: create sequence nuvem.FormaPagamentoID start 1 increment 1
Hibernate: create sequence nuvem.FuncionarioID start 1 increment 1
Hibernate: create sequence nuvem.FuncionarioQuiosqueID start 1 increment 1
Hibernate: create sequence nuvem.GrupoClienteID start 1 increment 1
Hibernate: create sequence nuvem.PromocaoID start 1 increment 1
Hibernate: create sequence nuvem.PromocaoQuiosqueID start 1 increment 1
Hibernate: create sequence nuvem.QuiosqueID start 1 increment 1
Hibernate: create sequence nuvem.TabelaPrecoID start 1 increment 1
Hibernate: create sequence nuvem.TabelaPrecoTempoID start 1 increment 1
Hibernate: create sequence nuvem.UsuarioId start 1 increment 1
Hibernate: create table quiosque.BrinquedoOperacao (id int8 not null, dadosSemCadastro varchar(150), dataHoraAtivacao timestamp, dataHoraCancelamento timestamp, dataHoraContratado timestamp, dataHoraEmPausa timestamp, dataHoraInativacao timestamp, justificativaCancelamento varchar(150), justificativaCredito varchar(150), justificativaDesconto varchar(150), log text, minutosContratados int4, minutosDecorridos int4, minutosDiferenca int4, tempoEmPausa int4, tempoFlexibilizacao int4, valorCreditoConcedido float8, valorDesconto float8, valorPosPago float8, valorPrePago float8, versao int4, caixa_id int8 not null, quiosque_id int8 not null, brinquedo_id int8 not null, cliente_cpfOuCnpj varchar(20), formaPosPago_id int8, formaPrePago_id int8, funcionarioAtivou_id int8, funcionarioCancelou_id int8, funcionarioInativou_id int8, funcionarioPausou_id int8, tabelaPrecoTempo_id int8, primary key (caixa_id, quiosque_id, id))
Hibernate: create table quiosque.Caixa (id int8 not null, dataHoraAbertura timestamp, dataHoraFechamento timestamp, observacao text, valorAbertura float8, valorContagem float8, valorDiferenca float8, valorFechamento float8, versao int4, quiosque_id int8 not null, funcionarioAbriu_id int8, funcionarioFechou_id int8, primary key (id, quiosque_id))
Hibernate: create table quiosque.CaixaMovimento (id int8 not null, dataHora timestamp, descricao varchar(100), operacao varchar(30), valor float8, versao int4, caixa_id int8 not null, quiosque_id int8 not null, brinquedoOperacao_id int8 not null, formaPagamento_id int8 not null, funcionario_id int8, primary key (caixa_id, quiosque_id, brinquedoOperacao_id, id))
Hibernate: create table quiosque.CaixaMovimentoDois (id int8 not null, dataHora timestamp, descricao varchar(100), operacao varchar(30), valor float8, versao int4, caixa_id int8 not null, quiosque_id int8 not null, formaPagamento_id int8 not null, funcionario_id int8, primary key (caixa_id, quiosque_id, id))
Hibernate: create table quiosque.Cliente (cpfOuCnpj varchar(20) not null, bairro varchar(50), celular varchar(20), cep varchar(10), complemento varchar(50), dataHoraModificado timestamp, email varchar(120), endereco varchar(120), nascimento date, nome varchar(80), nomeCidade varchar(60), numLocacoes int4, numero varchar(20), pfOuPj varchar(2), telefoneFixo varchar(20), ufCidade varchar(2), versao int4, grupoCliente_id int8, primary key (cpfOuCnpj))
Hibernate: create table quiosque.ClienteQuiosque (dataHoraModificado timestamp, cliente_cpfOuCnpj varchar(20) not null, quiosque_id int8 not null, primary key (cliente_cpfOuCnpj, quiosque_id))
Hibernate: create table quiosque.ConfiguracaoQuiosque (id int8 not null, chaveQuiosque varchar(60), idQuiosque int8, tempoParaSincronizacao int4, urlIntegracao varchar(200), versao int4, primary key (id))
Hibernate: create table quiosque.InativacaoForcada (id int8 not null, dataHora timestamp, observacao varchar(150), versao int4, caixa_id int8 not null, quiosque_id int8 not null, brinquedoOperacao_id int8 not null, funcionario_id int8, primary key (caixa_id, quiosque_id, brinquedoOperacao_id, id))
Hibernate: alter table if exists nuvem.BrinquedoNoQuiosque add constraint FKh06fkybmoxn64ph3o7mopfuar foreign key (brinquedo_id) references nuvem.Brinquedo
Hibernate: alter table if exists nuvem.BrinquedoNoQuiosque add constraint FK74222r5njmav1g2p3kibypk5a foreign key (quiosque_id) references nuvem.Quiosque
Hibernate: alter table if exists nuvem.FuncionarioQuiosque add constraint FKcw26ykdol8d5kptnti4q8i7ye foreign key (funcionario_id) references nuvem.Funcionario
Hibernate: alter table if exists nuvem.FuncionarioQuiosque add constraint FKd97iam0ont9m3gpawfbeu1tjx foreign key (quiosque_id) references nuvem.Quiosque
Hibernate: alter table if exists nuvem.Promocao add constraint FKn9sj9iulobo73f3eikyy5n48x foreign key (grupoCliente_id) references nuvem.GrupoCliente
Hibernate: alter table if exists nuvem.PromocaoQuiosque add constraint FK49xq4ems28swmg4vvn8qw2hgk foreign key (promocao_id) references nuvem.Promocao
Hibernate: alter table if exists nuvem.PromocaoQuiosque add constraint FKslbjr53o5rkmdake5pvjrmq9g foreign key (quiosque_id) references nuvem.Quiosque
Hibernate: alter table if exists nuvem.TabelaPreco add constraint FKemxr7twvvh524ebfp9pls6u81 foreign key (quiosque_id) references nuvem.Quiosque
Hibernate: alter table if exists nuvem.TabelaPrecoTempo add constraint FK8fifhajkjcliqit32f4l3ngrx foreign key (tabelaPreco_id) references nuvem.TabelaPreco
Hibernate: alter table if exists quiosque.BrinquedoOperacao add constraint FK8pde40tjdsryxoi41mlit0unv foreign key (caixa_id, quiosque_id) references quiosque.Caixa
Hibernate: alter table if exists quiosque.BrinquedoOperacao add constraint FK2n4se5i0onb5dul9v5bf98s73 foreign key (brinquedo_id) references nuvem.Brinquedo
Hibernate: alter table if exists quiosque.BrinquedoOperacao add constraint FK79jxrnd6sod7dbyp6m0rc1ujt foreign key (cliente_cpfOuCnpj) references quiosque.Cliente
Hibernate: alter table if exists quiosque.BrinquedoOperacao add constraint FKpfm0tt1cm6dynmhjjpite6t56 foreign key (formaPosPago_id) references nuvem.FormaPagamento
Hibernate: alter table if exists quiosque.BrinquedoOperacao add constraint FKaqcqql0sxeq5to2b17n08betb foreign key (formaPrePago_id) references nuvem.FormaPagamento
Hibernate: alter table if exists quiosque.BrinquedoOperacao add constraint FK3k3up0bda0c3aa2kfucrho2i foreign key (funcionarioAtivou_id) references nuvem.Funcionario
Hibernate: alter table if exists quiosque.BrinquedoOperacao add constraint FKeclag9y5jm8ccw29sgdqrw2m2 foreign key (funcionarioCancelou_id) references nuvem.Funcionario
Hibernate: alter table if exists quiosque.BrinquedoOperacao add constraint FKk99p7v155mpk3f8m4km6rljpw foreign key (funcionarioInativou_id) references nuvem.Funcionario
Hibernate: alter table if exists quiosque.BrinquedoOperacao add constraint FKcsiopidp84wd32ji01tljxb6v foreign key (funcionarioPausou_id) references nuvem.Funcionario
Hibernate: alter table if exists quiosque.BrinquedoOperacao add constraint FKjrqh97ttpsh3aw4r6u230j07n foreign key (tabelaPrecoTempo_id) references nuvem.TabelaPrecoTempo
Hibernate: alter table if exists quiosque.Caixa add constraint FK3qlh3shml9xfbww6a0xdgw20w foreign key (quiosque_id) references nuvem.Quiosque
Hibernate: alter table if exists quiosque.Caixa add constraint FKlfv8dast8d8rlrbcm7xu2at5x foreign key (funcionarioAbriu_id) references nuvem.Funcionario
Hibernate: alter table if exists quiosque.Caixa add constraint FKbabr3aj8pulj3fwuyriky9i2d foreign key (funcionarioFechou_id) references nuvem.Funcionario
Hibernate: alter table if exists quiosque.CaixaMovimento add constraint FKevptr74ejxikvrfpsfinc1j5 foreign key (caixa_id, quiosque_id, brinquedoOperacao_id) references quiosque.BrinquedoOperacao
Hibernate: alter table if exists quiosque.CaixaMovimento add constraint FKe26683ni2d0t8emqm5uq5iwbg foreign key (caixa_id, quiosque_id) references quiosque.Caixa
Hibernate: alter table if exists quiosque.CaixaMovimento add constraint FKi1wm4gh6q5ouyh8d9hrgqk4cd foreign key (formaPagamento_id) references nuvem.FormaPagamento
Hibernate: alter table if exists quiosque.CaixaMovimento add constraint FK61is3sg9g2k65jrhvt5um25cj foreign key (funcionario_id) references nuvem.Funcionario
Hibernate: alter table if exists quiosque.CaixaMovimentoDois add constraint FKoh3lu6a17uqkmhdn765ccva5s foreign key (caixa_id, quiosque_id) references quiosque.Caixa
Hibernate: alter table if exists quiosque.CaixaMovimentoDois add constraint FKfmmc1oh6qetjhqle3rejcgn4n foreign key (formaPagamento_id) references nuvem.FormaPagamento
Hibernate: alter table if exists quiosque.CaixaMovimentoDois add constraint FKm4j8m4e0ybjgvowgya9f4yq8b foreign key (funcionario_id) references nuvem.Funcionario
Hibernate: alter table if exists quiosque.Cliente add constraint FK12d4ihpbtjpf72lsqxgskco0l foreign key (grupoCliente_id) references nuvem.GrupoCliente
Hibernate: alter table if exists quiosque.ClienteQuiosque add constraint FKggbl2imcv31bsw3o8f5t6n6rp foreign key (cliente_cpfOuCnpj) references quiosque.Cliente
Hibernate: alter table if exists quiosque.ClienteQuiosque add constraint FKqr3wn12i939el013s1692hwfx foreign key (quiosque_id) references nuvem.Quiosque
Hibernate: alter table if exists quiosque.InativacaoForcada add constraint FKt1rxmvuia4cqejcowr4nuw7wo foreign key (caixa_id, quiosque_id, brinquedoOperacao_id) references quiosque.BrinquedoOperacao
Hibernate: alter table if exists quiosque.InativacaoForcada add constraint FKtkrvo73n7bu0gh56di2esddsq foreign key (funcionario_id) references nuvem.Funcionario
jun 05, 2019 3:43:38 PM org.hibernate.engine.transaction.jta.platform.internal.JtaPlatformInitiator initiateService
INFO: HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
Hibernate: select nextval ('nuvem.UsuarioId')
Hibernate: select nextval ('nuvem.FuncionarioID')
Hibernate: insert into nuvem.Usuario (login, nome, senha, id) values (?, ?, ?, ?)
Hibernate: insert into nuvem.Funcionario (bairro, cep, complemento, cpf, ctpsNumero, ctpsSerie, dataAdmissao, dataDemissao, email, endereco, foneDois, foneUm, foto, nome, nomeCidade, numero, rg, senha, ufCidade, versao, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
