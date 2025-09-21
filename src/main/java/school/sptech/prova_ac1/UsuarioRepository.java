package school.sptech.prova_ac1;



import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class UsuarioRepository {
    private static final Map<Integer, Usuario> usuarios = new HashMap<>();
    private static final AtomicInteger contadorId = new AtomicInteger(1);

    public static Usuario salvar(Usuario usuario) {
        usuario.setId(contadorId.getAndIncrement());
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    public static List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios.values());
    }

    public static Usuario buscarPorId(Integer id) {
        return usuarios.get(id);
    }

    public static boolean existeEmailOuCpf(String email, String cpf, Integer ignorarId) {
        return usuarios.values().stream().anyMatch(u ->
                (u.getEmail().equals(email) || u.getCpf().equals(cpf)) &&
                        (ignorarId == null || !u.getId().equals(ignorarId))
        );
    }

    public static boolean removerPorId(Integer id) {
        return usuarios.remove(id) != null;
    }

    public static List<Usuario> buscarPorDataNascimentoMaiorQue(LocalDate data) {
        return usuarios.values().stream()
                .filter(u -> u.getDataNascimento().isAfter(data))
                .collect(Collectors.toList());
    }

    public static Usuario atualizar(Integer id, Usuario novo) {
        novo.setId(id);
        usuarios.put(id, novo);
        return novo;
    }
}