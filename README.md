@ Autor: David Israel Gutiérrez Núñez

Este proyecto implementa los métodos CRUD para una API REST empleada para administrar un conjunto de servidores.

Métodos y Endpoints:

GET:
    servidores/         --> Regresa la base de datos entera de pares Identificador : Servidor
    servidores/{id}     --> Regresa el servidor asociado al id especificado.
    servidores/creditos --> Devuelve información sobre el autor del proyecto.

POST:
    servidores/         --> Inserta el servidor contenido en el cuerpo de la solicitud en la base de datos.

PATCH:
    servidores/{id}     --> Actualiza el servidor asociado al id especificado con el objeto servidor contenido en el cuerpo de la solicitud.

DELETE:
    servidores/{id}     --> Elimina el servidore asociado al id especificado de la base de datos.