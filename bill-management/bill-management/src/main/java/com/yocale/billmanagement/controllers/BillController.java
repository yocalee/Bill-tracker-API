package com.yocale.billmanagement.controllers;

import com.yocale.billmanagement.dtos.BillDto;
import com.yocale.billmanagement.dtos.BillRegisterDto;
import com.yocale.billmanagement.security.model.UserPrincipal;
import com.yocale.billmanagement.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {
    private final BillService service;

    @Autowired
    public BillController(BillService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BillDto> create(@RequestBody BillRegisterDto registerDto, @AuthenticationPrincipal UserPrincipal principal) throws URISyntaxException {
        BillDto registered = service.register(registerDto, principal);
        return ResponseEntity.created(new URI("/bills/" + registered.getId())).body(registered);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id, @AuthenticationPrincipal UserPrincipal principal) {
        service.delete(id, principal);
    }

    @GetMapping
    public ResponseEntity<List<BillDto>> getAll(@AuthenticationPrincipal UserPrincipal principal) {
        List<BillDto> all = service.getAll(principal);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDto> getById(@PathVariable long id, @AuthenticationPrincipal UserPrincipal principal) {
        BillDto bill = service.findById(id, principal);
        return ResponseEntity.ok(bill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDto> edit(@PathVariable long id, @RequestBody BillRegisterDto dto, @AuthenticationPrincipal UserPrincipal principal) {
        BillDto updated = service.update(dto, id, principal);
        return ResponseEntity.ok(updated);
    }


}
