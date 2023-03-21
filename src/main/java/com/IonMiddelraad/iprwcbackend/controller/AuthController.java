package com.IonMiddelraad.iprwcbackend.controller;

import com.IonMiddelraad.iprwcbackend.dao.OrderDAO;
import com.IonMiddelraad.iprwcbackend.dao.ProductDAO;
import com.IonMiddelraad.iprwcbackend.dto.UserStoreDTO;
import com.IonMiddelraad.iprwcbackend.model.*;
import com.IonMiddelraad.iprwcbackend.repositories.UserRepository;
import com.IonMiddelraad.iprwcbackend.security.JWTUtilization;
import com.IonMiddelraad.iprwcbackend.service.EncryptionService;
import com.IonMiddelraad.iprwcbackend.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserRepository userRepo;
    private JWTUtilization jwtUtil;
    private AuthenticationManager authManager;
    private RoleService roleService;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    public AuthController(UserRepository employeeRepo, JWTUtilization jwtUtil, AuthenticationManager authManager, PasswordEncoder passEncoder, RoleService roleService, ProductDAO productDAO, OrderDAO orderDAO) {
        this.userRepo = employeeRepo;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.roleService = roleService;
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
    }

    @PostMapping("/register")
    public Map<String, Object> registrationHandler(@Valid @RequestBody UserStoreDTO employeeDTO) throws Exception {
        User employee = employeeDTO.toUser();
        employee.setPassword(EncryptionService.decrypt(employee.getPassword()));

        List<Role> roles = new ArrayList<>();
        int userRoleIndex = 1;
        roles.add(this.roleService.getRoles().get(userRoleIndex));
        employee.setRoles(roles);

        userRepo.save(employee);
        String token = jwtUtil.generateToken(employee.getEmail(), roleService.toJsonRoleString(employee.getRoles()));
        return Collections.singletonMap("jwt_token", token);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body) {
        try {
            String email = body.getEmail();
            String password = EncryptionService.decrypt(body.getPassword());

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
            authManager.authenticate(authToken);
            String token = jwtUtil.generateToken(body.getEmail(), body.getRoles());
            return Collections.singletonMap("jwt_token", token);
        } catch (AuthenticationException authExc) {
            throw new RuntimeException("Login credentials are invalid");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/product/all")
    @ResponseBody
    public ResponseEntity getAllProducts() {
        List<Product> productList = this.productDAO.getAll();
        return new ApiResponse<>(HttpStatus.OK, productList).getResponse();
    }

    @GetMapping(value = "order/all")
    @ResponseBody
    public ResponseEntity getAllOrders() {
        List<Order> orderList = this.orderDAO.getAll();

        List<Order> safeOrderList = new ArrayList<>();
        for (Order order : orderList) {
            safeOrderList.add(new Order(order.getId(),
                    new User(order.getUser().getId(), order.getUser().getName()),
                    order.getProductList()));
        }

        return new ApiResponse<>(HttpStatus.OK, safeOrderList).getResponse();
    }
}