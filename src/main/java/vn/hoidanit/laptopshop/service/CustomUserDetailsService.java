package vn.hoidanit.laptopshop.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    // để tìm ra user cần kết nối xuống database
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        vn.hoidanit.laptopshop.domain.User user = this.userService.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return new User(
                user.getEmail(),
                user.getPassword(),
                // tạo array chỉ có 1 phần tử
                // SimpleGrantedAuthority sẽ gán role
                // không nên set session ở đây vì chưa hề biết người dùng đã đăng nhập thành
                // công hay chưa
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName())));

    }

}
