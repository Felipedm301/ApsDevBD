/*Apagar caso exista*/
drop database if exists APSDevBancoDados;
SET GLOBAL log_bin_trust_function_creators = 1;

/*Criando banco*/
create database APSDevBancoDados;

/*Usando o banco*/
use APSDevBancoDados;

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
create procedure APSDevBancoDados.INSERTCLIENTE (IN nome varchar(35), bonus numeric, perfil varchar(1), status varchar(1))
begin
	insert into CLIENTE (nome,bonus,perfil,status) values (nome,bonus,perfil,status);
end $$
delimiter ;
	

/*Inserir PRODUTO*/
delimiter $$
create procedure APSDevBancoDados.INSERTPRODUTO (IN CODLOCAL INTEGER, DESCRICAO VARCHAR(35), QTD_ESTTOQUE NUMERIC,PRECO_UNITARIO DECIMAL(10,2))	
BEGIN
	INSERT INTO PRODUTO (CODLOCAL,DESCRICAO,QTD_ESTOQUE,PRECO_UNITARIO) VALUES (CODLOCAL,DESCRICAO,QTD_ESTOQUE,PRECO_UNITARIO);
end $$
delimiter ;  

/*TRATAR ESTOQUE*/  
DELIMITER $$
CREATE FUNCTION APSDevBancoDados.TRATARPRODUTO (TCODPROD INTEGER) RETURNS integer
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
CREATE FUNCTION APSDevBancoDados.CALCULARPRODUTO (QUANTIDADE INTEGER,CODP INTEGER) RETURNS DECIMAL(10,2)
BEGIN
	RETURN (SELECT PRECO_UNITARIO * QUANTIDADE FROM PRODUTO WHERE CODPROD = CODP);
END $$
DELIMITER ;

   

/*Inserir VENDA*/
delimiter $$
create FUNCTION APSDevBancoDados.INSERTVENDA (PCODPROD INTEGER,PCODLOCAL INTEGER,PQTD_VENDA NUMERIC,PVALOR_TOTAL DECIMAL(10,2),PDATA_VENDA DATE)	RETURNS VARCHAR(45)
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
create procedure APSDevBancoDados.INSERTLOCALIDADE (IN NOME VARCHAR(35), ENDERECO VARCHAR(80), TELEFONE VARCHAR(40))	
BEGIN
	INSERT INTO LOCALIDADE (NOME,ENDERECO,TELEFONE) VALUES (NOME,ENDERECO,TELEFONE);
end $$
delimiter ;    


/*Inserir DESCONTO*/
delimiter $$
create procedure APSDevBancoDados.INSERTDESCONTO (IN CODPROD INTEGER,PERCENTUAL NUMERIC,QTD_MIN NUMERIC,QTD_MAX NUMERIC)	
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