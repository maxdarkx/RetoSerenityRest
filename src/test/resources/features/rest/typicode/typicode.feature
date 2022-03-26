Feature: Consumo typicode
  Como administrador de la pagina
  necesito lanzar pruebas en la pagina
  para garantizar su correcto funcionamiento

  Scenario: Crear un usuario dados sus datos personales
    Given que se esta ordenando la base de datos del servicio
    When se ingresan los datos personales de un usuario en el servicio
    Then se retornara un codigo de aceptacion y el usuario creado