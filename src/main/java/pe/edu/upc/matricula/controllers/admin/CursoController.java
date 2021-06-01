package pe.edu.upc.matricula.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.matricula.models.entities.Curso;
import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.services.CursoService;
import pe.edu.upc.matricula.services.UsuarioService;

@Controller
@RequestMapping("/admin")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("curso")
	public String curso(Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		
		try {
			List<Curso> cursos = cursoService.findAll();
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			model.addAttribute("cursos", cursos);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/curso/inicio";
	}
	
	@GetMapping("curso/crear")
	public String cursoCrear(Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		Curso curso = new Curso();
		try {
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			model.addAttribute("curso", curso);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/curso/crear";
	}
	
	@PostMapping("curso/save")
	public String saveCurso(@ModelAttribute("curso") Curso curso, SessionStatus status, RedirectAttributes attributes) {
		try {
			if (curso.getNombre().isEmpty()) {
					attributes.addFlashAttribute("mensaje", "Complete todos los campos, por favor");
					return "redirect:/admin/curso/crear";
			}else if (curso.getNombre().isBlank()) {
				attributes.addFlashAttribute("mensaje", "Complete todos los campos, por favor");
				return "redirect:/admin/curso/crear";
			}else if (curso.getNombre().toString().length() < 2) {
				attributes.addFlashAttribute("mensaje", "Complete el nombre del curso con mínimo 2 caracteres");
				System.out.println("Nombre del curso: " + curso.getNombre());
				return "redirect:/admin/curso/crear";
			}else if (! curso.getNombre().matches("[áéíóúÁÉÍÓÚa-zA-Zñ0-9\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores del nombre del curso deben de ser alfanuméricos");
				return "redirect:/admin/curso/crear";
			}else if (curso.getCodigo().isEmpty()) {
				attributes.addFlashAttribute("mensaje", "Complete todos los campos, por favor");
				return "redirect:/admin/curso/crear";
			}else if (curso.getCodigo().isBlank()) {
				attributes.addFlashAttribute("mensae", "Complete el código del curso, por favor");
				return "redirect:/admin/curso/crear";
			}else if (cursoService.findByCodigo(curso.getCodigo()).isPresent()) {
				System.out.println("Ya existe codigo");
				attributes.addFlashAttribute("mensaje", "El código de curso ya se encuentra registrado");
				return "redirect:/admin/curso/crear";
			}else if (! (curso.getCodigo().toString().length() == 4)) {
				attributes.addFlashAttribute("mensaje", "Complete el código del curso con 4 caracteres");
				return "redirect:/admin/curso/crear";
			}else if (! curso.getCodigo().matches("[a-zA-Zñ0-9]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores del Código de cursos deben ser alfanuméricos sin espacios");
				System.out.println("Codigo: " + curso.getCodigo());
				return "redirect:/admin/curso/crear";
			}else {
				System.out.println("Nombre del curso: " + curso.getCodigo()+ "   -   digitos: "+ curso.getCodigo().length());
				curso.setCodigo(curso.getCodigo().toUpperCase());
				curso.setEstado(true);
				cursoService.save(curso);
				status.setComplete();
				attributes.addFlashAttribute("mensaje", "Se registró exitosamente el Curso");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/curso";
	}
	
	@GetMapping("curso/{id}")
	public String cursoShow(@PathVariable("id") Integer id, Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			Optional<Curso> optional = cursoService.findById(id);
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			if (optional.isPresent()) {
				model.addAttribute("curso", optional.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/curso/editar";
	}
	@PostMapping("curso/update/{id}")
	public String cursoUpdate(@PathVariable("id") Integer id, @ModelAttribute("curso") Curso curso, Model model, SessionStatus status, RedirectAttributes attributes) {
		try {
			Optional<Curso>GetCurso = cursoService.findById(id);
			if (curso.getNombre().isEmpty()) {
				attributes.addFlashAttribute("mensaje", "Complete todos los campos, por favor");
				return "redirect:/admin/curso/"+id;
			}else if (curso.getNombre().isBlank()) {
				attributes.addFlashAttribute("mensaje", "Complete el nombre del curso, por favor");
				return "redirect:/admin/curso/"+id;
			}else if (curso.getNombre().toString().length() < 2) {
				attributes.addFlashAttribute("mensaje", "Complete el nombre del curso con mínimo 2 caracteres");
				System.out.println("Nombre del curso: " + curso.getNombre());
				return "redirect:/admin/curso/"+id;
			}else if (! curso.getNombre().matches("[áéíóúÁÉÍÓÚa-zA-Zñ0-9\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores del nombre del curso deben de ser alfanuméricos");
				return "redirect:/admin/curso/"+id;
			}else if (curso.getCodigo().isEmpty()) {
				attributes.addFlashAttribute("mensaje", "Complete todos los campos, por favor");
				return "redirect:/admin/curso/"+id;
			}else if (curso.getCodigo().isBlank()) {
				attributes.addFlashAttribute("mensaje", "Complete el código del curso, por favor");
				return "redirect:/admin/curso/"+id;
			}else if (! (curso.getCodigo().toString().length() == 4)) {
				attributes.addFlashAttribute("mensaje", "Complete el código del curso con 4 caracteres");
				return "redirect:/admin/curso/"+id;
			}else if (! curso.getCodigo().matches("[a-zA-Zñ0-9]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores del del código curso deben de ser alfanuméricos sin espacios");
				System.out.println("Codigo: " + curso.getCodigo());
				return "redirect:/admin/curso/"+id;
			}else if (cursoService.findByCodigo(curso.getCodigo()).isPresent()) {
				if (! GetCurso.get().getCodigo().contentEquals(curso.getCodigo())) {
					attributes.addFlashAttribute("mensaje", "El código de curso ya se encuentra registrado");
					return "redirect:/admin/curso/"+id;
				}else {
					curso.setCodigo(curso.getCodigo().toUpperCase());
					cursoService.save(curso);
					status.setComplete();
					attributes.addFlashAttribute("mensaje", "Se actualizó correctamente el curso");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/curso";
	}
	
	@GetMapping("curso/buscar/{codigo}")
	public String cursoBuscar(Model model, @RequestParam("codigo") String codigo, SessionStatus status, RedirectAttributes attributes) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			List<Curso> busquedaCursos = cursoService.findByCodigoContaining(codigo.toUpperCase());
			System.out.println(codigo);
			if (codigo.isEmpty()) {
				attributes.addFlashAttribute("mensajeError", "Complete el campo si desea buscar, por favor");
				return "redirect:/admin/curso";
			}else if (codigo.toString().isBlank()) {
				attributes.addFlashAttribute("mensajeError", "Complete el campo si desea buscar, por favor");
				return "redirect:/admin/curso";
			}else if (codigo.toString().length() <2) {
				attributes.addFlashAttribute("mensajeError", "Complete el campo de búsqueda con mínimo 2 caracteres");
				return "redirect:/admin/curso";
			}else if(busquedaCursos.isEmpty()){
				attributes.addFlashAttribute("mensajeError", "No se encontraron registros en la búsqueda");
				return "redirect:/admin/curso";
			}else {
				System.out.println("POR QUÉ?");
				model.addAttribute("usuarioLogin", usuarioLogin.get());
				model.addAttribute("busquedaCursos", busquedaCursos);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/curso/busqueda";
	}
}
