# Sistema de Gestión de Biblioteca Genérica

Aplicación de escritorio en Java para administrar una colección de publicaciones. El modelo define la clase base `Trabajo` (título, autor y año de publicación) y las subclases `Libro`, `Revista` y `Articulo`, que añaden atributos propios y reutilizan el comportamiento común mediante herencia, sobrescritura de métodos y polimorfismo.

El almacenamiento se apoya en `BibliotecaGenericaArreglo<T extends Trabajo>`, un contenedor genérico respaldado por un arreglo que ofrece operaciones de alta en una posición, consulta por índice, listado completo, eliminación y vaciado, con verificación de tipos en tiempo de compilación y validación de límites. La interfaz, construida con Java Swing, permite elegir el tipo de publicación, habilitar los campos correspondientes, capturar y validar los datos, y visualizar los registros y mensajes de estado de cada operación.
