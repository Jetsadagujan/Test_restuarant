package th.co.priorsolution.traning.newhttp.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.co.priorsolution.traning.newhttp.model.PersonModel;

@Controller
@RequestMapping("/app")
public class AppViewController {

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

    @GetMapping("/test")
    public String index(Model model){
        PersonModel personModel = new PersonModel();
        personModel.setAge(25);
        personModel.setName("Tao");
        model.addAttribute("person",personModel);
        return "index";
    }
}
