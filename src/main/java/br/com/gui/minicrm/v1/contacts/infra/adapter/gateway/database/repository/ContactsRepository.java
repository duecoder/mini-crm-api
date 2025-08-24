package br.com.gui.minicrm.v1.contacts.infra.adapter.gateway.database.repository;

import br.com.gui.minicrm.v1.contacts.infra.adapter.gateway.database.entity.ContactsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactsRepository extends JpaRepository<ContactsEntity, UUID> {

}
