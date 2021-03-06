Feature: consumo reqres
  Como un administrador de la pagina
  necesito realizar pruebas de funcionamiento de la pagina
  para verificar su correcto funcionamiento

  Scenario: Obtener un error por usuario no encontrado
    Given que se esta probando la base de datos de usuario
    When se ingresa un id inexistente en el servicio
    Then se retornara un codigo de error

  Scenario:  Obtener un usuario dado un identificador
    Given  que se esta probando la base de datos de usuario existente
    When se ingresa un id presente en el servicio
    Then se mostraran los datos del usuario

  Scenario:  crear un usuario dado su nombre y trabajo
    Given  que se esta creando la base de datos del servicio
    When se ingresan los datos de un usuario en el servicio
    Then se retornara un codigo de aceptacion y el usuario creado