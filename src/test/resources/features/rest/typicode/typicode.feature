Feature: Consumo typicode
  Como administrador de la pagina
  necesito lanzar pruebas en la pagina
  para garantizar su correcto funcionamiento

  Scenario: Crear un usuario dados sus datos personales
    Given que se esta ordenando la base de datos del servicio
    When se ingresan los datos personales de un usuario en el servicio
    Then se retornara un codigo de aceptacion y el usuario creado

  Scenario: Eliminar un usuario del registro del sitio
    Given que se esta probando la funcionalidad de borrado de usuarios del sitio
    When se ingresan los datos del usuario a borrar del servicio
    Then se retornara un codigo de aceptacion de borrado