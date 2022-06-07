package ar.edu.unju.edm.controller;

import javax.validation.Valid;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.edm.model.Usuario;
import ar.edu.unju.edm.service.IUsuarioService;


@Controller
public class UsuarioController {
	private static final Log SANTI=LogFactory.getLog(UsuarioController.class);
	@Autowired
	Usuario nuevoUsuario;
	

	@Autowired
	IUsuarioService serviceUsuario;
	@GetMapping("/otroUsuario")
	public ModelAndView addUser() {
		SANTI.info("ingrese al metodo: bbbbbbbbbbbbbbbbbbbbbb");
		ModelAndView vista=new ModelAndView("cargarUsuario");
		//vista.addObject("nuevoUsuario");
		vista.addObject("usuario", nuevoUsuario);
		vista.addObject("editMode", false);
		return vista;
		
	}
	@PostMapping("/guardarusuario")
	public String saveUser( @Valid @ModelAttribute ("usuario") Usuario usuarioparaguardar,BindingResult resultado,Model model) {
		SANTI.info("Ingresadp al metodo guardar usuario"+ usuarioparaguardar.getFechaNacimiento());
		if (resultado.hasErrors()) {
			SANTI.fatal("Error de Validacion");
			model.addAttribute("usuario",usuarioparaguardar);
			return "cargarusuario";
		}
		//lista.getListado().add(usuarioparaguardar);
		//flata algo
		try {//va contraolar si se ejecuta bien 
			serviceUsuario.guardarUsuario(usuarioparaguardar);	
		}
		catch (Exception e){//si no sale
			model.addAttribute("formUsuarioErrorMessage",e.getMessage());
			model.addAttribute("usuario",usuarioparaguardar);
		    SANTI.error("no se pudo guaradar usuario");
		    return "cargarusuario";
		}
		
		//SANTI.error("tamaño de listado" + lista.getListado().size());+
		model.addAttribute("formularioErrorMessage","Usuario guardado correctamente");
		model.addAttribute("usuario",nuevoUsuario);
		model.addAttribute("editMode", false);
		return "cargarusuario";
	}
	@GetMapping("/listadoUsuario")
	public ModelAndView showuser(){
		ModelAndView vista = new ModelAndView("listadoUsuario");
		vista.addObject("ListaUsuario",serviceUsuario.mostrarUsuario() );
	    //
		return vista;
	}
	@GetMapping("/editarUsuario/{dni}")
	public ModelAndView edituser(Model model,@PathVariable (name="dni") Integer dni)throws Exception {
		Usuario usuarioEncontrado =new Usuario();
		try {
			usuarioEncontrado=serviceUsuario.buscarUsuario(dni);
		}
		catch(Exception e) {
			model.addAttribute("formUsuarioErrorMessage",e.getMessage());
		}
		//usuarioEncontrado=serviceUsuario.buscarUsuario(dni);
		ModelAndView modelView = new ModelAndView("cargarusuario");
		modelView.addObject("usuario",usuarioEncontrado);
		SANTI.error("saliendo del metodo :AAAAAA"+ usuarioEncontrado.getDni());
		modelView.addObject("editMode", true);
		return modelView;
	}
	@PostMapping("/editarUsuario")
	public ModelAndView postEditarUsuario ( @ModelAttribute("usuarioF") Usuario usuarioModificado) {
		serviceUsuario.modificarUsuario(usuarioModificado);
		ModelAndView vista = new ModelAndView("listadoUsuario");
		vista.addObject("ListaUsuario", serviceUsuario.mostrarUsuario());
		vista.addObject("formUsuarioErrorMessage", "Usuario Guardado Correctamente");
		return vista;
		
	}
	@GetMapping("/eliminarUsuario/{dni}")
	public String deleteUser(@PathVariable Integer dni ,Model model) {
		try {
			serviceUsuario.eliminarUsuario(dni);
			
		}catch(Exception e) {
			SANTI.error("encontrado:Eeeeeeeeeeeeeee");
			model.addAttribute("formUsuarioErrorMessage", e.getMessage());
			return "redirect:/nuevoUsuario";
		}
		return "redirect:/listadoUsuario";
	}
	
	
}
