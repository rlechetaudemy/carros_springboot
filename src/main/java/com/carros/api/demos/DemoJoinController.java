package com.Joins.api.demos;

import com.carros.api.users.User;
import com.carros.api.users.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
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
    public String get() {
        return "Teste Join.";
    }

    @GetMapping("/demo1")
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

    public List<UserDTO> findUsersByRole(String role) {
        List<UserDTO> list = rep.findUsersByRole(role).stream().map(UserDTO::create).collect(Collectors.toList());
        return list;
    }
}

interface JoinRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM Usuario u inner join u.roles r WHERE r.nome like %:role%")
    List<User> findUsersByRole(@Param("role") String role);
}
