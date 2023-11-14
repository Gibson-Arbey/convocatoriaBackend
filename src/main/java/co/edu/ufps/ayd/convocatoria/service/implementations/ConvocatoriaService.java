package co.edu.ufps.ayd.convocatoria.service.implementations;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.ayd.convocatoria.exception.ConvocatoriaException;
import co.edu.ufps.ayd.convocatoria.model.entity.ConvocatoriaEntity;
import co.edu.ufps.ayd.convocatoria.repository.ConvocatoriaRepository;
import co.edu.ufps.ayd.convocatoria.service.interfaces.ConvocatoriaInterface;

@Service
public class ConvocatoriaService implements ConvocatoriaInterface{

    @Autowired 
    ConvocatoriaRepository convocatoriaRepository;

    @Override
    public void abrirConvocatoria(ConvocatoriaEntity convocatoriaEntity) {
        validacionesDeFechas(convocatoriaEntity);
        Optional<ConvocatoriaEntity> convocatoriaHabilitada = convocatoriaRepository.findByEstado(true);
        
        if(convocatoriaHabilitada.isPresent()) {
            throw new ConvocatoriaException("Ya hay una convocatoria abierta.");
        }

        convocatoriaEntity.setEstado(true);
        String nombre = crearNombre();
        convocatoriaEntity.setNombre(nombre);
        convocatoriaRepository.save(convocatoriaEntity);
    }

    @Override
    public void cerrarConvocatoria() {
        Optional<ConvocatoriaEntity> convocatoriaHabilitada = convocatoriaRepository.findByEstado(true);
        if(!convocatoriaHabilitada.isPresent()) {
            throw new ConvocatoriaException("No hay una convocatoria abierta.");
        }
        ConvocatoriaEntity convocatoriaEntity = convocatoriaHabilitada.get();
        convocatoriaEntity.setEstado(false);
        convocatoriaRepository.save(convocatoriaEntity);
    }

    @Override
    public ConvocatoriaEntity buscarConvocatoriaHabilitada() {
        Optional<ConvocatoriaEntity> convocatoriaHabilitada = convocatoriaRepository.findByEstado(true);
        if(!convocatoriaHabilitada.isPresent()) {
            throw new ConvocatoriaException("No hay una convocatoria abierta.");
        }
        return convocatoriaHabilitada.get();
    }

    @Override
    public void modificarConvocatoria(ConvocatoriaEntity convocatoriaEntity) {
        Optional<ConvocatoriaEntity> convocatoriaHabilitada = convocatoriaRepository.findByEstado(true);
        if(!convocatoriaHabilitada.isPresent()) {
            throw new ConvocatoriaException("No hay una convocatoria abierta.");
        }
        validacionesDeFechas(convocatoriaEntity);
        ConvocatoriaEntity convocatoria = convocatoriaHabilitada.get();
        convocatoria.setFechaInicio(convocatoriaEntity.getFechaInicio());
        convocatoria.setFechaFin(convocatoriaEntity.getFechaFin());
        convocatoria.setFechaResultados(convocatoriaEntity.getFechaResultados());
        convocatoriaRepository.save(convocatoria);
    }

    @Override
    public List<ConvocatoriaEntity> listarConvocatoriasPasadas() {
        List<ConvocatoriaEntity> convocatorias = convocatoriaRepository.findAllByEstado(false);
        if (convocatorias.isEmpty()) {
            throw new ConvocatoriaException("No hay registro de convocatorias pasadas.");
        }
        return convocatorias;
    }

    private String crearNombre() {
        Date fechaActual = new Date();
        Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);

        calendario.setTime(fechaActual);
        int month = calendario.get(Calendar.MONTH) + 1;
        int semestre = (month <= 6) ? 1 : 2;

        return String.format("Convocatoria %d - Semestre %d", anio, semestre);
    }

    private void validacionesDeFechas(ConvocatoriaEntity convocatoriaEntity) {
        Date fechaInicio = convocatoriaEntity.getFechaInicio();
        Date fechaFin = convocatoriaEntity.getFechaFin();
        Date fechaResultados = convocatoriaEntity.getFechaResultados();
        if (fechaFin.before(fechaInicio)) {
            throw new ConvocatoriaException("La fecha de fin de inscripciones no puede ser anterior a la fecha de inicio.");
        }
        
        if (fechaFin.before(new Date())) {
            throw new ConvocatoriaException("La fecha de fin de inscripciones no puede ser anterior a la fecha actual.");
        }
        
        if (fechaResultados.before(fechaInicio)) {
            throw new ConvocatoriaException("La fecha de presentación de resultados no puede ser anterior a la fecha de inicio de inscripciones.");
        }
        
        if (fechaResultados.before(fechaFin)) {
            throw new ConvocatoriaException("La fecha de presentación de resultados no puede ser anterior a la fecha de fin de inscripciones.");
        }
        
        if (fechaResultados.before(new Date())) {
            throw new ConvocatoriaException("La fecha de presentación de resultados no puede ser anterior a la fecha actual.");
        }
    }
    
}
