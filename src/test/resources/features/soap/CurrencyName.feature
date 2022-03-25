Feature: Nombre de moneda
  Como financiero
  necesito convertir codigos de moneda ISO a lenguaje formal
  para agilizar el proceso de comprension con clientes

  Scenario: Convertir de moneda ISO a nombre formal
    Given dado que el usuario esta en el recurso web indicando el nombre de moneda ISO "COP"
    When el usuario genera la consulta
    Then visualizara el nombre formal de la moneda como "Pesos"