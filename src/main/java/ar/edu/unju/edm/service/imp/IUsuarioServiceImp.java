package ar.edu.unju.edm.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.edm.controller.UsuarioController;
import ar.edu.unju.edm.model.Usuario;
import ar.edu.unju.edm.repository.UsuarioRepository;
import ar.edu.unju.edm.service.IUsuarioService;
import ar.edu.unju.edm.until.ListaUsuario;
@Service
public class IUsuarioServiceImp implements IUsuarioService{
	private static final Log SANTI = LogFactory.getLog(UsuarioController.class);
	@Autowired 
	ListaUsuario lista;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public void guardarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		usuario.setEstado(true);
	//	lista.getListado().add(usuario);
		usuarioRepository.save(usuario);
	}

	@Override
	public List<Usuario> mostrarUsuario() {
		// TODO Auto-generated method stub
		List<Usuario> auxiliar = new ArrayList<>();
		List<Usuario> auxiliar2 = new ArrayList<>();
		/*for(int i = 0 ;i<lista.getListado().size();i++) {
			SANTI.error("recorr;SSSS"+lista.getListado().get(i).getDni());
			if (lista.getListado().get(i).getEstado()==true) {
				auxiliar.add(lista.getListado().get(i));
			}
		}*/
		auxiliar=(List<Usuario>) usuarioRepository.findAll();
		for(int i = 0 ;i<auxiliar.size();i++) {
			//SANTI.error("recorr;SSSS"+lista.getListado().get(i).getDni());
			if (auxiliar.get(i).getEstado()==true) {
				auxiliar2.add(auxiliar.get(i));
			}
		}	
		return auxiliar2;
	}

	@Override
	public void eliminarUsuario(Integer dni) throws Exception {
		// TODO Auto-generated method stub
		/*for (int i=0;i<lista.getListado().size();i++) {
			if(lista.getListado().get(i).getDni().equals(dni)) {
				lista.getListado().get(i).setEstado(false);
			}
		}*/
		Usuario auxiliar =new Usuario();
		auxiliar=buscarUsuario(dni);
		auxiliar.setEstado(false);
		usuarioRepository.save(auxiliar);
 //		usuarioRepository.delete(dni);
	}

	@Override
	public void modificarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		/*for(int i=0;i<lista.getListado().size();i++) {
			if(lista.getListado().get(i).getDni().equals(usuario.getDni())) {
				SANTI.error("encontrado:AAAAAAAAAAA");
				lista.getListado().set(i, usuario);
			}
		}*/
		usuarioRepository.save(usuario);

	}      

	@Override
	public Usuario buscarUsuario(Integer dni) throws Exception {
		// TODO Auto-generated method stub
		Usuario usuarioEncontrado =new Usuario();
		/*for(int i =0 ; i<lista.getListado().size();i++) {
			SANTI.error("recorriendo:Aaaaaaaaa"+dni);
			if(lista.getListado().get(i).getDni().equals(dni)) {
				usuarioEncontrado= lista.getListado().get(i);
			}
		}*/
		usuarioEncontrado=usuarioRepository.findById(dni).orElseThrow(()->new Exception("usuario no encontrado"));
		return usuarioEncontrado;
	}

}
