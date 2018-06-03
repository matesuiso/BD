-- a)Devolver las colonias con la cantidad de niños que poseen.
SELECT p.nombre, p.apellido, n.dni, a.cod_colonia, c.nombre AS asociacion 
FROM asiste a, nino n, persona p, colonia c
WHERE n.dni = a.dni AND n.dni = p.dni AND a.cod_colonia = c.cod_colonia;


-- b)Listado de actividades con el promedio de antigüedad de los niños que participan en ellas.
SELECT a.*, AVG(TO_DAYS(now()) - TO_DAYS(r.fecha_inicio)) AS promedio_dias_ninios
FROM realiza r, actividad a
WHERE r.cod_actividad = a.cod_actividad
GROUP BY a.cod_actividad;


-- c)Apellido y Nombre de los líderes que trabajan en más de una colonia.
SELECT p.apellido, p.nombre
FROM actividad a, lider l, persona p
WHERE a.dni_lider = l.dni AND p.dni = l.dni
GROUP BY l.dni
HAVING COUNT(l.dni) > 1;

-- d)Definir consultas propias (no menos de tres), donde por lo menos una utilice subconsultas.
