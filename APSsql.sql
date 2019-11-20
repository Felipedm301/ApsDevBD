/*Apagar caso exista*/
drop database if exists apsbd;
SET GLOBAL log_bin_trust_function_creators = 1;

/*Criando banco*/
create database apsbd;

/*Usando o banco*/
use apsbd;

/*Criando tabelas*/
create table CLIENTE (
	CODCLI		integer not null AUTO_INCREMENT,
	NOME		varchar(35),
	BONUS		numeric,
	PERFIL		varchar(1),
	STATU		varchar(1),
    primary key (CODCLI));

create table LOCALIDADE (
	CODLOCAL	integer not null auto_increment,
	NOME		varchar(35),
	ENDERECO	varchar(80),
	TELEFONE	varchar(40),
    primary key (CODLOCAL));


create table DESCONTO (
	ID_DESCONTO	integer not null AUTO_INCREMENT,
	CODPROD 	integer not null,
	PERCENTUAL	numeric,
	QTD_MIN		numeric,
	QTD_MAX		numeric,
    primary key (ID_DESCONTO));
	
create table PRODUTO (
	CODPROD		integer not null auto_increment,
	CODLOCAL	integer not null,
	DESCRICAO	varchar(35),
	QTD_ESTOQUE	numeric,
	PRECO_UNITARIO	decimal(10,2),
    primary key (CODPROD));
	
 
create table VENDA (
	CODCLI	integer not null,
	CODPROD	integer not null,
	CODLOCAL integer not null,
	QTD_VENDA	numeric,
	VALOR_TOTAL decimal(10,2),
	DATA_VENDA	date);


/*Inserir dados cliente*/
delimiter $$
create procedure apsbd.INSERTCLIENTE (IN nome varchar(35), bonus numeric, perfil varchar(1), status varchar(1))
begin
	insert into CLIENTE (nome,bonus,perfil,status) values (nome,bonus,perfil,status);
end $$
delimiter ;
	

/*Inserir PRODUTO*/
delimiter $$
create procedure apsbd.INSERTPRODUTO (IN CODLOCAL INTEGER, DESCRICAO VARCHAR(35), QTD_ESTTOQUE NUMERIC,PRECO_UNITARIO DECIMAL(10,2))	
BEGIN
	INSERT INTO PRODUTO (CODLOCAL,DESCRICAO,QTD_ESTOQUE,PRECO_UNITARIO) VALUES (CODLOCAL,DESCRICAO,QTD_ESTOQUE,PRECO_UNITARIO);
end $$
delimiter ;  

/*TRATAR ESTOQUE*/  
DELIMITER $$
CREATE FUNCTION apsbd.TRATARPRODUTO (TCODPROD INTEGER) RETURNS integer
BEGIN
	IF NOT EXISTS (SELECT * FROM PRODUTO WHERE CODPRO = TCODPROD) THEN
		RETURN 0;
    ELSE
		RETURN 1;
    END IF;
END $$
DELIMITER ;

/*CALCULAR PRECO*/
DELIMITER $$ 
CREATE FUNCTION apsbd.CALCULARPRODUTO (QUANTIDADE INTEGER,CODP INTEGER) RETURNS DECIMAL(10,2)
BEGIN
	RETURN (SELECT PRECO_UNITARIO * QUANTIDADE FROM PRODUTO WHERE CODPROD = CODP);
END $$
DELIMITER ;

   

/*Inserir VENDA*/
delimiter $$
create FUNCTION apsbd.INSERTVENDA (PCODPROD INTEGER,PCODLOCAL INTEGER,PQTD_VENDA NUMERIC,PVALOR_TOTAL DECIMAL(10,2),PDATA_VENDA DATE)	RETURNS VARCHAR(45)
BEGIN
	DECLARE PQ_ESTOQUE NUMERIC;
    
    SET PQ_ESTOQUE = (SELECT QTD_ESTOQUE FROM PRODUTO WHERE CODPROD = PCODPROD);
    
    IF PQ_ESTOQUE < PQTD_VENDA THEN
		RETURN 'QUANTIA DE ESTOQUE INSUFICIENTE';
    ELSE
		INSERT INTO VENDA (CODPROD,CODLOCAL,QTD_VENDA,VALOR_TOTAL,DATA_VENDA) VALUES (PCODPROD,PCODLOCAL,PQTD_VENDA,PVALOR_TOTAL,PDATA_VENDA);
        UPDATE PRODUTO SET QTD_ESTOQUE = (SELECT QTD_ESTOQUE - PQTD_VENDA FROM PRODUTO WHERE ID = PCODPROD) WHERE ID = PCODPROD;
        RETURN 'VENDA REGISTRADA';
    END IF;
end $$
delimiter ;    


/*Inserir localidade*/
delimiter $$
create procedure apsbd.INSERTLOCALIDADE (IN NOME VARCHAR(35), ENDERECO VARCHAR(80), TELEFONE VARCHAR(40))	
BEGIN
	INSERT INTO LOCALIDADE (NOME,ENDERECO,TELEFONE) VALUES (NOME,ENDERECO,TELEFONE);
end $$
delimiter ;    


/*Inserir DESCONTO*/
delimiter $$
create procedure apsbd.INSERTDESCONTO (IN CODPROD INTEGER,PERCENTUAL NUMERIC,QTD_MIN NUMERIC,QTD_MAX NUMERIC)	
BEGIN
	INSERT INTO VENDA (CODPROD, PERCENTUAL, QTD_MIN, QTD_MAX) VALUES (CODPROD, PERCENTUAL, QTD_MIN, QTD_MAX);
end $$
delimiter ;
    
/*Atribuindo constraints*/
alter table PRODUTO add constraint CODLOCAL_PRODUTO_FK foreign key (CODLOCAL) references LOCALIDADE (CODLOCAL);

alter table VENDA add constraint CODCLI_VENDA_FK foreign key (CODCLI) references CLIENTE (CODCLI);
alter table VENDA add constraint CODPROD_VENDA_FK foreign key (CODPROD) references PRODUTO (CODPROD);
alter table VENDA add constraint CODLOCAL_VENDA_FK foreign key (CODLOCAL) references LOCALIDADE (CODLOCAL);

alter table DESCONTO add constraint CODPROD_DESCONTO_FK foreign key (CODPROD) references PRODUTO (CODPROD);

INSERT INTO cliente(nome, bonus, perfil, statu) VALUES ('Doria', 1000, 'G', 'A');
INSERT INTO cliente(nome, bonus, perfil, statu) VALUES ('Wagner', 150, 'G', 'A');
INSERT INTO cliente(nome, bonus, perfil, statu) VALUES ('Randolfo', 300, 'M', 'A');
INSERT INTO cliente(nome, bonus, perfil, statu) VALUES ('Joseval', 1000, 'P', 'A');
INSERT INTO cliente(nome, bonus, perfil, statu) VALUES ('Patrick', 250, 'G', 'A');


INSERT INTO localidade(nome, endereco, telefone) VALUES ('Maracana', 'Rua são francisco xavier', '20550-011');
INSERT INTO localidade(nome, endereco, telefone) VALUES ('Centro', 'Rua da Quitanda', '20091-000');
INSERT INTO localidade(nome, endereco, telefone) VALUES ('Méier', 'Rua Dias Cruz', '4109-0459');
INSERT INTO localidade(nome, endereco, telefone) VALUES ('Barra da Tijuca', 'Av. do Pepê', '22620-170');

/*Maracana*/
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (1, 'Smart TV 32 Samsung', 100, 1200.00);
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (1, 'Geladeira Brastemp duoFlex', 30, 2400.00);
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (1, 'Smart TV 50 4K LG', 50, 3000.00);

/*Centro*/
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (2, 'Caixa de som portátil Pulse', 300, 200.00);
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (2, 'Microondas eletrolux 20L', 150, 500.00);
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (2, 'IPhone X', 10, 4000.00);

/*Méier*/
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (3, 'Cafeteira', 60, 180.00);
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (3, 'Fogão 4 bocas mundial', 80, 450.00);
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (3, 'Ferro eletrico', 10, 60.00);

/*Barra da Tijuca*/
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (4, 'Notebook 8gb I7 500gb', 30, 1149.00);
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (4, 'Cama box solteiro premium', 50, 699.00);
INSERT INTO produto(codlocal, descricao, qtd_estoque, preco_unitario) VALUES (4, 'Guarda roupa 6 portas com espelho', 10, 1029.00);

/*Desconto*/

INSERT INTO desconto(codprod, percentual, qtd_min, qtd_max) VALUES (1, 5, 10, 50);
INSERT INTO desconto(codprod, percentual, qtd_min, qtd_max) VALUES (4, 20, 5, 50);
INSERT INTO desconto(codprod, percentual, qtd_min, qtd_max) VALUES (7, 20, 10, 80);
INSERT INTO desconto(codprod, percentual, qtd_min, qtd_max) VALUES (8, 20, 10, 70);
INSERT INTO desconto(codprod, percentual, qtd_min, qtd_max) VALUES (9, 8, 10, 90);
INSERT INTO desconto(codprod, percentual, qtd_min, qtd_max) VALUES (12, 10, 2, 80);