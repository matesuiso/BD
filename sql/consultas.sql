-- a)Devolver las colonias con la cantidad de niños que poseen.
SELECT c.*, COUNT(n.dni) AS ninios_asistentes
FROM asiste a, nino n, persona p, colonia c
WHERE n.dni = a.dni AND n.dni = p.dni AND a.cod_colonia = c.cod_colonia
GROUP BY c.cod_colonia;


-- b)Listado de actividades con el promedio de antigüedad de los niños que participan en ellas.
SELECT a.*, AVG(TO_DAYS(now()) - TO_DAYS(r.fecha_inicio)) AS promedio_dias_ninios
FROM realiza r, actividad a
WHERE r.cod_actividad = a.cod_actividad
GROUP BY a.cod_actividad;


-- c)Apellido y Nombre de los líderes que trabajan en más de una colonia.
SELECT s.apellido, s.nombre 
FROM (	SELECT p.apellido, p.nombre, l.dni, ac.cod_colonia 
		FROM lider l JOIN persona p ON l.dni = p.dni JOIN ayuda ay ON ay.dni = l.dni JOIN actividad ac ON ac.cod_actividad = ay.cod_actividad UNION 
			SELECT p.apellido, p.nombre, l.dni, a.cod_colonia 
            FROM lider l JOIN persona p ON l.dni = p.dni JOIN actividad a ON a.dni_lider = l.dni) s 
GROUP BY s.dni 
HAVING COUNT(DISTINCT s.cod_colonia) > 1;

-- d)Definir consultas propias (no menos de tres), donde por lo menos una utilice subconsultas.

-- Certificados emitidos por una asociacion (con grado) y el lider que lo posee
SELECT p.apellido, p.nombre, c.cod_certificado, c.grado, a.nombre
FROM certificado c, lider l, asociacion a, persona p
WHERE l.cod_certificado = c.cod_certificado AND c.nombre_asoc = a.nombre AND p.dni = l.dni;

-- Lideres que solo ayudan, y que no son responsables de ninguna actividad 
SELECT l.*
FROM lider l, ayuda ay
WHERE l.dni = ay.dni  AND l.dni NOT IN (SELECT l.dni
										FROM lider l, actividad ac
                                        WHERE l.dni = ac.dni_lider)
;


-- Colonias que solo tienen una actividad
SELECT col.*
FROM colonia col, actividad act
WHERE col.cod_colonia = act.cod_colonia
GROUP BY col.cod_colonia
HAVING COUNT(act.cod_actividad) < 2;


-- Lideres que tienen mas de un telefono
SELECT p.*
FROM lider l, mtel, persona p
WHERE l.dni = mtel.dni AND p.dni = l.dni
GROUP BY l.dni
HAVING COUNT(mtel.telefono) > 1;