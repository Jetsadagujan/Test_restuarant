package th.co.priorsolution.traning.newhttp.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.co.priorsolution.traning.newhttp.model.AddressModel;
import th.co.priorsolution.traning.newhttp.model.PersonModel;
import th.co.priorsolution.traning.newhttp.model.ResponseModel;
import th.co.priorsolution.traning.newhttp.service.AppService;

@RestController
@RequestMapping("/api")
public class AppRestController {

    private AppService appService;

    public AppRestController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/person")
    public ResponseModel<PersonModel> getPersonData(){

        return this.appService.getPersonDataAndResponse();
    }

    @GetMapping("/address")
    public ResponseModel<AddressModel> getAddressData() {
        return this.appService.getAddressDataAndResponse();
    }
}
