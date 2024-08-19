package lv.polarisit.vacationtracker.service;


import lv.polarisit.vacationtracker.data.ContactRepository;
import lv.polarisit.vacationtracker.data.Vacation;
import lv.polarisit.vacationtracker.data.VacationRepository;
import lv.polarisit.vacationtracker.ui.view.ContactView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationService {

    private final VacationRepository vacationRepository;

    public VacationService(@Autowired VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }
    public List<Vacation> getAllVacations() {

        return vacationRepository.findAll();
    }

    public Vacation saveVacation(Vacation vacation) {
        return vacationRepository.save(vacation);
    }

    public void deleteVacation(Long id) {
        vacationRepository.deleteById(id);
    }
}
