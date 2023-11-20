CREATE TABLE hib_departamento (id UUID NOT NULL PRIMARY KEY, nombre varchar(50) NOT NULL UNIQUE, jefe UUID DEFAULT NULL);
CREATE TABLE hib_empleado (id UUID NOT NULL PRIMARY KEY, nombre varchar(100) NOT NULL, salario decimal(10,2) DEFAULT NULL, departamento UUID DEFAULT NULL);
CREATE TABLE hib_proyecto (id UUID NOT NULL PRIMARY KEY, nombre varchar(100) NOT NULL);