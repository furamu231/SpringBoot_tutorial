package com.example.sample1app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.sample1app.repositories.PersonRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Controller
public class HelloController {
    
    @Autowired
    PersonRepository repository;

    @GetMapping("/")
    public ModelAndView index(
        @ModelAttribute("formModel") Person person,
        ModelAndView mav) {
        mav.setViewName("index");
        mav.addObject("title", "Hello page");
        mav.addObject("msg", "this is JPA sample data");
        Iterable<Person> list = repository.findAll();
        mav.addObject("data", list);
        return mav;
    }


    @PostMapping("/")
    @Transactional
    public ModelAndView form(
        @ModelAttribute("formModel") @Validated Person person,
        BindingResult result,
        ModelAndView mav
    ) {

        ModelAndView res = null;
        
        // ログ確認
        System.out.println(result.getFieldErrors());

        if (!result.hasErrors()) {
            repository.saveAndFlush(person);
            res = new ModelAndView("redirect:/");
        } else {
            mav.setViewName("index");
            mav.addObject("title", "Hello Page");
            mav.addObject("msg", "sorry, error is occuring");
            Iterable<Person> list = repository.findAll();

            // listはthymeleaf側で展開する必要がありそう
            mav.addObject("datalist", list);
            res = mav;
        }

        // それぞれの結果に応じたmavを返す

        return res;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(
        @ModelAttribute Person person,
        @PathVariable int id,
        ModelAndView mav
    ) {
       
        mav.setViewName("edit");
        mav.addObject("title", "edit person");
        Optional<Person> data = repository.findById((long)id);

        // formModelにデータを送信
        mav.addObject("formModel", data.get());
        return mav;        

    }

    @GetMapping("/edit")
    @Transactional
    public ModelAndView update(
        @ModelAttribute Person person,
        ModelAndView mav
    ) {
        repository.saveAndFlush(person);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(
        @PathVariable int id,
        ModelAndView mav
    ) {
        mav.setViewName("delete");
        mav.addObject("title", "Delete Person.");
        mav.addObject("msg", "delete this record");

        // dataはOptionalのため、値を取り出す必要あり。
        
        Optional<Person> data = repository.findById((long)id);
        mav.addObject("formModel", data.get());
        return mav;
    }
    
    // なんでlongじゃないの？
    
    @PostMapping("/delete")
    @Transactional
    public ModelAndView remove(
        @RequestParam long id,
        ModelAndView mav
    ) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/");
    }




    // ダミーデータの追加
    
    @PostConstruct
    public void init() {
        Person p1 = new Person();
        p1.setName("まひろ");
        p1.setAge(13);
        p1.setMail("mahiro@com");
        repository.saveAndFlush(p1);

        Person p2 = new Person();
        p2.setName("みはり");
        p2.setAge(14);
        p2.setMail("mihari@com");
        repository.saveAndFlush(p2);

        Person p3 = new Person();
        p3.setName("なゆた");
        p3.setAge(13);
        p3.setMail("nayuta@com");
        repository.saveAndFlush(p3);
    }
}
