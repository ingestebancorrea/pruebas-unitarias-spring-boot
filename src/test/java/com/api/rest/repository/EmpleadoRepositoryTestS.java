package com.api.rest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.api.rest.model.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmpleadoRepositoryTestS {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    @BeforeEach // Se va a ejecutar antes de cada método
    void setup(){
        empleado = Empleado.builder()
                .nombre("Emily")
                .apellido("Correa")
                .email("emily@hotmail.com")
                .build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado(){
        // Para definir los casos BDD para una UH se deben definir bajo el patrón:

        // . Given: dado o condición previa o configuración - ejemplo (Dado que el usuario no ha introducido ningún dato en el formulario)
        Empleado empleado1 = Empleado.builder() // Patrón builder permite construir objetos
                .nombre("Erisson")
                .apellido("Correa")
                .email("erisson@hotmail.com")
                .build();

        // . When: acción o el comportamiento que vamos a probar - ejemplo (Cuando se hace click en el botón enviar)
        Empleado empleadoGuardado = empleadoRepository.save(empleado1);

        // . Then: verificar la salida - ejemplo(Se deben mostrar los mensajes de validación apropiados)
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void testListarEmpleados(){
        // given: Dado estos empleado guardados
        Empleado empleado1 = Empleado.builder() // Patrón builder permite construir objetos
                .nombre("Esteban")
                .apellido("Correa")
                .email("esteban@hotmail.com")
                .build();
        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado);

        // when: Cuando liste a todas los empleados
        List<Empleado> listaEmpleados = empleadoRepository.findAll();

        // then: Se debe mostrar que la lista de empleado no sea nula
        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener un empleado por id")
    @Test
    void testObtenerEmpleadoPorId(){
        empleadoRepository.save(empleado);

        // when - comportamiento o acción que vamos a probar
        Empleado empleadoBD = empleadoRepository.findById(empleado.getId()).get();

        // then
        assertThat(empleadoBD).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){
        empleadoRepository.save(empleado);

        // when
        Empleado empleadoGuardado = empleadoRepository.findById(empleado.getId()).get();
        empleadoGuardado.setEmail("c232@gmail.com");
        empleadoGuardado.setNombre("Cristian");
        empleadoGuardado.setApellido("Ramirez");
        Empleado empleadoActualizado = empleadoRepository.save(empleadoGuardado);

        // then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("c232@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Cristian");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){
        empleadoRepository.save(empleado);

        // when
        empleadoRepository.deleteById(empleado.getId());
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleado.getId());

        // then
        assertThat(empleadoOptional).isEmpty();
    }
}
