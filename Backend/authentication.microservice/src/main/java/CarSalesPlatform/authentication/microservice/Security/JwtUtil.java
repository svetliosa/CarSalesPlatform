package CarSalesPlatform.authentication.microservice.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	// TODO: Move the key to env variable later
	private static final String SECRET = "VB7nK13*/*'r^(Dz&btJ8zU?c=OIf+L>QN}1:I`CcmX&LL84";
	private static final long EXPIRATION_MS = 1000 * 60 * 60; // 1 hour

	private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

	public String generateToken(Long userId, String email, String role) {
		return Jwts.builder()
			.setSubject(userId.toString())
			.claim("email", email)
			.claim("role", role)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Claims validateToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}
