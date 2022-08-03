package com.sparta.week01.sercurity;

import com.sparta.week01.domain.User;
import com.sparta.week01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("==============UserDetailsServiceImpl > loadUserByUsername================");
        User user = userRepository.findByUsername(username);
        return new UserDetailsImpl(user);
    }
}
