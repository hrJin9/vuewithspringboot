package org.dallae.gallery.backend.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dallae.gallery.backend.entity.Member;
import org.dallae.gallery.backend.repository.MemberRepository;
import org.dallae.gallery.backend.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @PostMapping("/api/account/login")
    public ResponseEntity<Long> login(
            @RequestBody Map<String, String> params,
            HttpServletResponse res
    ) {
        Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));

        if(member != null) {
            long id = member.getId();
            String token = jwtService.getToken("id", id);
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true); // javascript로는 접근 불가
            cookie.setPath("/");

            res.addCookie(cookie);
            return ResponseEntity.ok(id);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/account/logout")
    public ResponseEntity<Void> logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        res.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/account/check")
    public ResponseEntity<Long> check(
            @CookieValue(name = "token", required = false) String token
    ) {
        Claims claims = jwtService.getClaims(token);

        if(claims != null) {
            Long id = Long.parseLong(
                    claims.get("id").toString()
            );
            return ResponseEntity.ok(id);
        }

        return ResponseEntity.notFound().build();
    }

}
