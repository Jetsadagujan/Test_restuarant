package th.co.priorsolution.traning.newhttp.service;

import org.springframework.stereotype.Service;
import th.co.priorsolution.traning.newhttp.model.AddressModel;
import th.co.priorsolution.traning.newhttp.model.PersonModel;
import th.co.priorsolution.traning.newhttp.model.ResponseModel;

@Service
public class AppService {
    public ResponseModel<PersonModel> getPersonDataAndResponse() {
        ResponseModel<PersonModel> result = new ResponseModel<>();

        try {
            result.setStatus(200);
            result.setDescription("OK");
            PersonModel personModel = this.getPersonData();
            result.setData(personModel);
        }
        catch (Exception e){
            e.printStackTrace();
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }


        return  result;
    }

    public PersonModel getPersonData() {
        PersonModel personModel = new PersonModel();
        personModel.setAge(25);
        personModel.setName("Tao");

        return  personModel;
    }

    public ResponseModel<AddressModel> getAddressDataAndResponse(){
        ResponseModel<AddressModel> result = new ResponseModel<>();
        result.setStatus(200);
        result.setDescription("OK");
        try {
            AddressModel addressModel = this.getAddressData();
            result.setData(addressModel);

        }catch (Exception e) {
            e.printStackTrace();
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    public AddressModel getAddressData() {
        AddressModel addressModel = new AddressModel();
        addressModel.setDistrict("BANGKOK");
        addressModel.setSubDistrict("JATUJAK");
        addressModel.setProvince("Vipavadee");
        addressModel.setHouseNo("19/41");
        addressModel.setPostalCode("10900");

        return  addressModel;
    }
}
