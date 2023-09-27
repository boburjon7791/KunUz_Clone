package com.example.demo.controllers;

import com.example.demo.enums.Role;
import com.example.demo.objects.AuthUser;
import com.example.demo.sevices.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.utils.Utils.number;

@RestController
@RequestMapping("/api.director")
@RequiredArgsConstructor
@PreAuthorize("hasRole('DIRECTOR')")
public class DirectorController {
    private final DirectorService directorService;
    @PostMapping("/save/employee")
    public ResponseEntity<AuthUser> save(@RequestBody AuthUser user){
        AuthUser authUser = directorService.save(user);
        return new ResponseEntity<>(authUser, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/employee/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        directorService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/employees")
    public ResponseEntity<Page<AuthUser>> employees(@RequestParam(required = false) String page,
                                                    @RequestParam(required = false) String size){

        int[] nums = number(page, size);
        Page<AuthUser> employees = directorService.employees(PageRequest.of(nums[0],nums[1]));
        return ResponseEntity.ok(employees);
    }
    @PutMapping("/update/employee/position/{position}/{id}")
    public ResponseEntity<Void> update(@PathVariable String position,
                                       @PathVariable Long id){
        directorService.updatePosition(Role.valueOf(position),id);
        return ResponseEntity.noContent().build();
    }
}
