package br.com.gui.minicrm.v1.contacts.infra.adapter.controller;

import br.com.gui.minicrm.v1.contacts.infra.adapter.gateway.database.entity.ContactsEntity;
import br.com.gui.minicrm.v1.contacts.infra.adapter.gateway.database.repository.ContactsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/contacts")
public class ContactsController {

    private final ContactsRepository repository;

    public ContactsController(ContactsRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<ContactsEntity>> listAll() {
        var all = repository.findAll();
        return ResponseEntity.ok(all);
    }
}
