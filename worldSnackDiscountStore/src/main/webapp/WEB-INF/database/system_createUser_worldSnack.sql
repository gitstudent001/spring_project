-- system ���� �������������Ʈ ���� ���� --
CREATE USER worldSnack IDENTIFIED BY 1234;

-- ���� ���� ���� �ο� --
GRANT CREATE SESSION TO worldSnack;

-- ���̺� ���� ���� �ο� --
GRANT CREATE TABLE TO worldSnack;

-- tablespace �Ҵ緮 ���� �ο� --
ALTER USER worldSnack DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;


/* �Ʒ� �ڵ� �߰� �߽��ϴ� �Ʒ� �ڵ常 SYSTEM�������� ������ �ּ��� */
-- �⺻ ����� ���� �ο� --
GRANT RESOURCE, CONNECT, DBA TO worldSnack;

-- ������, ���ν��� ���� ���� �ο� --
GRANT CREATE SEQUENCE, CREATE PROCEDURE TO worldSnack;