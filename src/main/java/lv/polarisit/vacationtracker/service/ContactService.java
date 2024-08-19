package lv.polarisit.vacationtracker.service;

import lv.polarisit.vacationtracker.data.Contact;
import lv.polarisit.vacationtracker.data.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
    public Contact getContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }
    public void saveContact(Contact contact) {
        contactRepository.save(contact);
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
