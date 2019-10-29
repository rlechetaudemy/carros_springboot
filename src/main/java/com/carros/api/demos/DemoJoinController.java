package com.carros.api.demos;

import com.carros.api.users.User;
import com.carros.api.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/join")
public class DemoJoinController {
    @Autowired
    private JoinService service;

    @GetMapping()
    public ResponseEntity get() {
        List<UsuarioRoleDTO> list = service.findUsersAndRoles();
        return list.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(list);
    }

    @GetMapping("/{role}")
    //http://localhost:8080/api/v1/join/user
    public ResponseEntity findUsersByRole(@PathVariable("role") String role) {
        List<UserDTO> list = service.findUsersByRole(role);
        return list.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(list);
    }
}

@Service
class JoinService {

    @Autowired
    private JoinRepository rep;

    public List<UsuarioRoleDTO> findUsersAndRoles() {
        return rep.findUsersAndRoles();
    }

    public List<UserDTO> findUsersByRole(String role) {
        return rep.findUsersByRole(role).stream().map(UserDTO::create).collect(Collectors.toList());
    }
}

@Repository
interface JoinRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT new com.carros.api.demos.UsuarioRoleDTO(u.login,u.email,r.nome) FROM User u inner join u.roles r")
    List<UsuarioRoleDTO> findUsersAndRoles();

    @Query(value = "SELECT u FROM User u inner join u.roles r WHERE r.nome like %:role%")
    List<User> findUsersByRole(@Param("role") String role);
}

@Data
@AllArgsConstructor
class UsuarioRoleDTO {
    String login;
    String email;
    String role;

}