package school.sptech.prova_ac1;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        if (UsuarioRepository.existeEmailOuCpf(usuario.getEmail(), usuario.getCpf(), null)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Usuario novo = UsuarioRepository.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(UsuarioRepository.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        Usuario usuario = UsuarioRepository.buscarPorId(id);
        if (usuario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        boolean removido = UsuarioRepository.removerPorId(id);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtro-data")
    public ResponseEntity<List<Usuario>> filtrarPorData(@RequestParam("nascimento") String nascimento) {
        LocalDate data = LocalDate.parse(nascimento);
        List<Usuario> resultado = UsuarioRepository.buscarPorDataNascimentoMaiorQue(data);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        if (UsuarioRepository.buscarPorId(id) == null) return ResponseEntity.notFound().build();
        if (UsuarioRepository.existeEmailOuCpf(usuario.getEmail(), usuario.getCpf(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Usuario atualizado = UsuarioRepository.atualizar(id, usuario);
        return ResponseEntity.ok(atualizado);
    }
}
