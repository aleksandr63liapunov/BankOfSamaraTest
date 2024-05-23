package com.example.bankofsamaratest.controller;

import com.example.bankofsamaratest.dto.UserDto;
import com.example.bankofsamaratest.model.User;
import com.example.bankofsamaratest.service.Impl.SearchServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Service API")
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class SearchController {

    private final SearchServiceImpl searchServiceImpl;


    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAll() {
        return new ResponseEntity<>(searchServiceImpl.getAll(), HttpStatus.CREATED)  ;
    }

    @GetMapping("/search_phone")
    public Page<User> searchByPhone(@RequestParam(required = false) String phone,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return searchServiceImpl.searchByPhone(phone, pageable);
    }

    @GetMapping("/search_date")
    public Page<User> searchByDateOfBirth(@RequestParam(required = false) LocalDate date,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String sortBy,
                                             @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return searchServiceImpl.searchByDateOfBirth(date, pageable);
    }

    @GetMapping("/search_name")
    public Page<User> searchUserStartWith(@RequestParam(required = false) String str,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String sortBy,
                                             @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return searchServiceImpl.searchUserStartWith(str, pageable);
    }

    @GetMapping("/search_email")
    public Page<User> searchByEmail(@RequestParam(required = false) String email,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return searchServiceImpl.searchByEmail(email, pageable);
    }

}