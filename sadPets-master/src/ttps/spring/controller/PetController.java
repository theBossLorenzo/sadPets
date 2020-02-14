package ttps.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ttps.spring.clasesDAO.UserDAO;
import ttps.spring.clasesDAO.VetDAO;
import ttps.spring.interfacesDAO.VetDAOInterface;
import ttps.spring.model.Owner;
import ttps.spring.model.Pet;
import ttps.spring.model.Vet;
import ttps.spring.model.objectDTO;
import ttps.spring.service.PetService;
import ttps.spring.service.UserService;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class PetController {
    @Autowired
    PetService petService;
    @Autowired
    UserService userService;
    @Autowired
    VetDAOInterface vetDao;

    @GetMapping("/pets/{id}")
    public ResponseEntity<List<Pet>> listAllPets(@PathVariable("id")long id){
        List<Pet> pets = petService.getAllPets(id);
        if(pets.isEmpty()){
            return new  ResponseEntity<List<Pet>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Pet>>(pets, HttpStatus.OK);
    }
    
    @PostMapping(value = "/pets")
    public ResponseEntity<Pet> createPet(@RequestBody objectDTO objeto){
    	if(!petService.isPetExist(objeto.getPet())) {
    		return new ResponseEntity<Pet>(objeto.getPet(),HttpStatus.CONFLICT);
    	}
    	objeto.getPet().setOwner(objeto.getOwner());
    	objeto.getPet().setVet(objeto.getVet());
    	petService.savePet(objeto.getPet());
    	return new ResponseEntity<Pet>(objeto.getPet(),HttpStatus.OK);
    }
}
