package CarSalesPlatform.authentication.microservice.Resource;

import CarSalesPlatform.authentication.microservice.Dto.UserLoginDto;
import CarSalesPlatform.authentication.microservice.Dto.UserRegistrationDto;
import CarSalesPlatform.authentication.microservice.Entity.User;
import CarSalesPlatform.authentication.microservice.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationResource {

	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public User register(@RequestBody UserRegistrationDto dto) {
		return authenticationService.register(dto);
	}

	@PostMapping("/login")
	public Map<String, String> login(@RequestBody UserLoginDto dto) {
		return authenticationService.login(dto);
	}

	@GetMapping("/me")
	public User getCurrentUser() {
		return authenticationService.getCurrentUser();
	}
}
