package ar.edu.unju.edm.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.edm.model.Curso;
import ar.edu.unju.edm.service.ICursoService;
import ar.edu.unju.edm.until.ListadoCursos;

@Controller

public class CursoController {

	private static final Log SANTI = LogFactory.getLog(CursoController.class);
	
	@Autowired
	Curso nuevoCurso;

	@Autowired
	ICursoService cursoService;
	
	@Autowired
	ListadoCursos listadoCursos;
	
	// cargar cursos
	@GetMapping({"/otroCurso"})	
	public ModelAndView addCourse() {
		
		SANTI.info("Ingresando al metodo agregar Curso");
		ModelAndView modelView = new ModelAndView("cargarCurso");
		modelView.addObject("unCurso", nuevoCurso);
		modelView.addObject("band", false);
		// modelView.addObject("idCurso", nuevoCurso.getId());
		return modelView;
	}
	
	@PostMapping("/guardarCurso")
	public String saveCourse(@Valid @ModelAttribute ("unCurso") Curso coursetosave, BindingResult result, ModelMap model) {
		SANTI.info("Ingresadp al metodo guardar usuario"+ coursetosave.getFechaInicio());
		if(result.hasErrors()) {
			SANTI.fatal("ERROR DE VALIDACION");
			model.addAttribute("unCurso", coursetosave);
			return "cargarCurso";
		}

		try {
			// model.addAttribute();
			cursoService.guardarCurso(coursetosave);
		} catch (Exception e) {
			model.addAttribute("formCourseErrorMessage", e.getMessage());
			model.addAttribute("unCurso", coursetosave);
			// model.addAttribute("idCurso", coursetosave.getId());
			SANTI.error("Saliendo del metodo: saveCourse");
			return "cargarCurso";
		}

		model.addAttribute("formCourseErrorMessage", "Curso guardado corecramente");
		model.addAttribute("unCurso", coursetosave);
        model.addAttribute("band",false);
		return "cargarCurso";
	}
		
	@GetMapping("/listarCursos")	
	public ModelAndView showCourse() {
		ModelAndView modelView = new ModelAndView("mostrarCursos");

		modelView.addObject("ListadoCursos", cursoService.listarCursos());
		//SANTI.info("Ingresando al metodo showCourse "+cursoService.listarCursos().get(0).getNombre());

		return modelView;
	}
	
		
	@GetMapping("/editarCurso/{id}")
	public ModelAndView getFormEditCourse(Model model, @PathVariable(name = "id") Integer id) throws Exception{
		Curso cursoEncontrado = new Curso();
		try {
			cursoEncontrado = cursoService.buscarCurso(id);
		} catch (Exception e) {
			model.addAttribute("formCourseErrorMessage", e.getMessage());
		}
		ModelAndView modelView = new ModelAndView("cargarCurso");
		modelView.addObject("unCurso", cursoEncontrado);
		//rescatarId=cursoEncontrado.getId();
		SANTI.error("Saliendo del metodo: getFormEditCourse "+cursoEncontrado.getId());
			
		modelView.addObject("band", true);

		return modelView;
	}

	@PostMapping("/editarCurso")
	public ModelAndView postEditCourse(@ModelAttribute("cursoF") Curso cursoModificado){
	//	SANTI.info("El id del curso ha modiicar es: "+rescatarId);
		//cursoModificado.setId(rescatarId);
		cursoService.modficarCurso(cursoModificado);
		ModelAndView modelView = new ModelAndView("listarCursos");
		modelView.addObject("ListadoCursos", cursoService.listarCursos());
		modelView.addObject("formCourseErrorMessage", "Curso guardado Correctamente");
//		SANTI.info("Curso modificado guardado correctamente");

		return modelView;
	}

	@GetMapping("/deletecourse/{id}")
	public String eliminar(@PathVariable Integer id, Model model){
		try {
			cursoService.eliminarCurso(id);
		} catch (Exception e) {
			SANTI.error("Encontrando Curso");
			model.addAttribute("formCourseErrorMessage", e.getMessage());
			return "redirect:/nuevoCurso";
		}
		return "redirect:/listarCursos";
	}
}
