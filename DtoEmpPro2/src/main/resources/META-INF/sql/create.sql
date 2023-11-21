CREATE TABLE hib_departamento (id UUID PRIMARY KEY,nombre VARCHAR(255) NOT NULL,jefe_id UUID);
CREATE TABLE hib_empleado (id UUID PRIMARY KEY,nombre VARCHAR(255) NOT NULL, salario DOUBLE NOT NULL,fecha_nacimiento DATE,departamento_id UUID);
CREATE TABLE hib_proyecto (id UUID PRIMARY KEY, nombre VARCHAR(255) NOT NULL);
CREATE TABLE hib_empleado_hib_proyecto (empleado_id UUID,proyecto_id UUID,PRIMARY KEY (empleado_id, proyecto_id));
ALTER TABLE hib_departamento ADD CONSTRAINT fk_departamento_jefe FOREIGN KEY (jefe_id) REFERENCES hib_empleado(id);
ALTER TABLE hib_empleado ADD CONSTRAINT fk_empleado_departamento FOREIGN KEY (departamento_id) REFERENCES hib_departamento(id);
ALTER TABLE hib_empleado_hib_proyecto ADD CONSTRAINT fk_empleado_proyecto_empleado FOREIGN KEY (empleado_id) REFERENCES hib_empleado(id);
ALTER TABLE hib_empleado_hib_proyecto ADD CONSTRAINT fk_empleado_proyecto_proyecto FOREIGN KEY (proyecto_id) REFERENCES hib_proyecto(id);