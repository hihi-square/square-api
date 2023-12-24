package com.hihi.square.global.jwt.service;

import com.hihi.square.domain.user.entity.User;
import com.hihi.square.domain.user.entity.UserStatus;
import com.hihi.square.domain.user.repository.UserRepository;
import com.hihi.square.global.error.type.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
   private final UserRepository userRepository;

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String uid) {
      return userRepository.findByUID(uid)
         .map(user -> createUser(user))
         .orElseThrow(() -> new UsernameNotFoundException("User Name Not Found"));
   }

   //로그인 시, 유저 정보를 권한 정보와 함께 가져옴
   private org.springframework.security.core.userdetails.User createUser(User user) {
      if (!user.getStatus().equals(UserStatus.ACTIVE)) {
         throw new UserNotFoundException("User Not Found");
      }

//      List<GrantedAuthority> grantedAuthorities = user.getAuthority().stream()
//              .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//              .collect(Collectors.toList());
      //단일 권한
      GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getDecriminatorValue().toString());
      
      return new org.springframework.security.core.userdetails.User(
              user.getUid(),
              user.getPassword(),
              Collections.singletonList(grantedAuthority));
   }
}