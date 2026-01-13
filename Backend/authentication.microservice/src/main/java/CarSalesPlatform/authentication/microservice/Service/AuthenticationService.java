package CarSalesPlatform.authentication.microservice.Service;

import CarSalesPlatform.authentication.microservice.Dto.UserLoginDto;
import CarSalesPlatform.authentication.microservice.Dto.UserRegistrationDto;
import CarSalesPlatform.authentication.microservice.Entity.User;
import CarSalesPlatform.authentication.microservice.Repository.RoleRepository;
import CarSalesPlatform.authentication.microservice.Repository.UserRepository;
import CarSalesPlatform.authentication.microservice.Security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public User register(UserRegistrationDto dto) {
		if (userRepository.existsByEmail(dto.getEmail()))
			throw new IllegalArgumentException("Email already registered");
		var role = roleRepository.findByName(dto.getRole()).orElseThrow(() -> new RuntimeException("Role not found"));
		var user = User.builder()
			.email(dto.getEmail())
			.password(passwordEncoder.encode(dto.getPassword()))
			.role(role)
			.status("ACTIVE")
			.createDate(LocalDateTime.now())
			.build();
		return userRepository.save(user);
	}

	public Map<String, String> login(UserLoginDto dto) {
		var user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
			throw new RuntimeException("Invalid credentials");
		var token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().getName());
		return Map.of("token", token);
	}

	public User getCurrentUser() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		var userId = Long.parseLong(authentication.getPrincipal().toString());
		return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	}
}
