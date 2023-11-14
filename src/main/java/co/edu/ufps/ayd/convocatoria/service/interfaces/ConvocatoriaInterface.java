package co.edu.ufps.ayd.convocatoria.service.interfaces;


import java.util.List;

import co.edu.ufps.ayd.convocatoria.model.entity.ConvocatoriaEntity;

public interface ConvocatoriaInterface {

    public void abrirConvocatoria(ConvocatoriaEntity convocatoriaEntity);

    public void cerrarConvocatoria();

    public ConvocatoriaEntity buscarConvocatoriaHabilitada();

    public void modificarConvocatoria(ConvocatoriaEntity convocatoriaEntity);

    public List<ConvocatoriaEntity> listarConvocatoriasPasadas();
}
