package dika.spring.security.service;

import dika.spring.security.enums.Roles;
import dika.spring.security.model.User;
import dika.spring.security.service.impl.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CustomUserDetailsServiceTest extends Mocks {

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
        assertNotNull(userDetails);
        assertEquals("Anacondaz", userDetails.getUsername());
        assertEquals("123", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
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
        assertNotNull(userDetails);
        assertEquals("Anacondaz", userDetails.getUsername());
        assertEquals("123", userDetails.getPassword());
        assertEquals(2, userDetails.getAuthorities().size());

        List<String> authorities = userDetails.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .sorted()
                .toList();
        assertEquals(List.of("ADMIN", "USER"), authorities);
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
