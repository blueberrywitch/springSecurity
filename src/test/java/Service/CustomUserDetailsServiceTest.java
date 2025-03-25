package Service;

import dika.spring.security.enums.Roles;
import dika.spring.security.model.User;
import dika.spring.security.repository.UserRepository;
import dika.spring.security.service.impl.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testLoadUserByUsernameUser() {
        String userIdStr = "1";
        Long userId = Long.parseLong(userIdStr);

        User user = new User();
        user.setId(userId);
        user.setUsername("Anacondaz");
        user.setPassword("123");
        user.setRole(List.of(Roles.USER));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userIdStr);
        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals("Anacondaz", userDetails.getUsername(), "Username should be 'Anacondaz'");
        assertEquals("123", userDetails.getPassword(), "Password should be '123'");
        assertEquals(1, userDetails.getAuthorities().size(), "There should be one authority");
        assertEquals("USER", userDetails.getAuthorities().iterator().next().getAuthority(),
                "Authority should be 'USER'");
    }

    @Test
    public void testLoadUserByUsernameUserAndAdmin() {
        String userIdStr = "1";
        Long userId = Long.parseLong(userIdStr);

        User user = new User();
        user.setId(userId);
        user.setUsername("Anacondaz");
        user.setPassword("123");
        user.setRole(List.of(Roles.USER, Roles.ADMIN));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userIdStr);
        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals("Anacondaz", userDetails.getUsername(), "Username should be 'Anacondaz'");
        assertEquals("123", userDetails.getPassword(), "Password should be '123'");
        assertEquals(2, userDetails.getAuthorities().size(), "There should be two authorities");

        List<String> authorities = userDetails.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .sorted()
                .toList();
        assertEquals(List.of("ADMIN", "USER"), authorities, "Authorities should contain 'ADMIN' and 'USER'");
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        String userIdStr = "1";
        Long userId = Long.parseLong(userIdStr);

        User user = new User();
        user.setId(userId);
        user.setUsername("Anacondaz");
        user.setPassword("123");
        user.setRole(List.of(Roles.USER));

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(userIdStr));
    }

}
