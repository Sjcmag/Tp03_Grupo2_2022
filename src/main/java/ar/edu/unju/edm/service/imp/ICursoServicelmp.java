package ar.edu.unju.edm.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.edm.controller.CursoController;
import ar.edu.unju.edm.model.Curso;
import ar.edu.unju.edm.repository.CursoRepository;
import ar.edu.unju.edm.service.ICursoService;
import ar.edu.unju.edm.until.ListadoCursos;

@Service

public class ICursoServicelmp implements ICursoService{

	private static final Log SANTI = LogFactory.getLog(CursoController.class);

    @Autowired
    ListadoCursos listCourses;

    @Autowired
    CursoRepository cursoRepository;

    @Override
    public void guardarCurso(Curso curso) {

        curso.setEstado(true);
        cursoRepository.save(curso);
        
    }

    @Override
    public void eliminarCurso(Integer id) throws Exception{

        Curso auxiliar = new Curso();
        auxiliar = buscarCurso(id);
        auxiliar.setEstado(false);
        cursoRepository.save(auxiliar);
        
    }

    @Override
    public void modficarCurso(Curso curso) {
    	SANTI.info("El curso con id: "+curso.getId()+"sera modificado proximamente");
        cursoRepository.save(curso);
        SANTI.info("El curso con id: "+curso.getId()+"se modifico");
        
    }

    @Override
    public List<Curso> listarCursos() {
        List<Curso> auxiliar = new ArrayList<>();
        List<Curso> auxiliar2 = new ArrayList<>();
        auxiliar = (List<Curso>) cursoRepository.findAll();
        for (int i = 0; i < auxiliar.size(); i++) {

            if (auxiliar.get(i).getEstado() == true) {
                auxiliar2.add(auxiliar.get(i));
            }
        }
        return auxiliar2;
    }

    @Override
    public Curso buscarCurso(Integer id) throws Exception {
        Curso cursoEncontrado = new Curso();
        cursoEncontrado = cursoRepository.findById(id).orElseThrow(()-> new Exception("Curso no encontrado"));
        return cursoEncontrado;
    }
    
}