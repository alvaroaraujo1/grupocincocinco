package pe.edu.upc.matricula.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.upc.matricula.models.entities.Curso;
import pe.edu.upc.matricula.models.entities.DocenteCurso;
import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.models.repositories.AuthorityRepository;
import pe.edu.upc.matricula.models.repositories.CursoRepository;
import pe.edu.upc.matricula.models.repositories.DocenteCursoRepository;
import pe.edu.upc.matricula.models.repositories.UsuarioRepository;

@Service
public class AddUserDB implements CommandLineRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private DocenteCursoRepository docenteCursoRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Override
	public void run (String... args) throws Exception{
		//SOLO SE DEBE  DE ACTIVAR UNA SOLA VEZ AL INICIAR EL PROGRAMA.
		/*
		
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		String password = bcpe.encode("admin");
		String passwordAlumno = bcpe.encode("71933197");
		String passwordDocente = bcpe.encode("21985419");
		
		Usuario administrador = new Usuario();
		administrador.setNombres("Ruben");
		administrador.setApellidoPaterno("Cerda");
		administrador.setApellidoMaterno("García");
		administrador.setUsuario("admin");
		administrador.setContraseña(password);
		administrador.setNumeroDocumento("00000001");
		administrador.setRol("ADMINISTRADOR");
		administrador.setEstado(true);
		administrador.addAuthority("ROLE_ADMINISTRADOR");
		
		usuarioRepository.save(administrador); 
		
		Usuario alumno = new Usuario();
		alumno.setNombres("Paula");
		alumno.setApellidoPaterno("Gastelumendi");
		alumno.setApellidoMaterno("Prado");
		alumno.setUsuario("u201715288");
		alumno.setContraseña(passwordAlumno);
		alumno.setNumeroDocumento("71933197");
		alumno.setRol("ALUMNO");
		alumno.setEstado(true);
		alumno.addAuthority("ROLE_ALUMNO");
		
		usuarioRepository.save(alumno);
		
		Usuario docente = new Usuario();
		docente.setNombres("Edgar");
		docente.setApellidoPaterno("Diaz");
		docente.setApellidoMaterno("Amaya");
		docente.setUsuario("ediaza");
		docente.setContraseña(passwordDocente);
		docente.setNumeroDocumento("21985419");
		docente.setRol("DOCENTE");
		docente.setEstado(true);
		docente.addAuthority("ROLE_DOCENTE");
		
		usuarioRepository.save(docente); 
		
		Curso curso = new Curso();
		curso.setNombre("Gerencia de proyectos");
		curso.setCodigo("SI42");
		curso.setEstado(true);
		
		cursoRepository.save(curso);	
		
		*/
	}
}
