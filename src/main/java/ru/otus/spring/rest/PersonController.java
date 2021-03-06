package ru.otus.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Person;
import ru.otus.spring.repostory.PersonRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class PersonController {

    private final PersonRepository repository;

    @Autowired
    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        List<Person> persons = repository.findAll();
        model.addAttribute("myPersonList", persons);
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") int id, Model model) {
        Person person = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("person", person);
        return "edit";
    }

    @RequestMapping(value = "/saveEditedPerson", method = RequestMethod.POST)
    public String savePerson(@ModelAttribute Person person, Model model) {
        repository.save(person);
        List<Person> persons = repository.findAll();
        model.addAttribute("myPersonList", persons);
        return "list";
    }

    /**
     *ttp://localhost:8080/personId?id=1
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(
            value = "/personId",
            method = RequestMethod.GET
    )
    public String get(@RequestParam(value = "id") int id, Model model) {
        Optional<Person> person = repository.findById(id);
        model.addAttribute("person", person.get());
        return "myPerson";
    }

}
