package com.example.server.controllers;

import java.util.List;
import java.util.ArrayList;
//import java.io.BufferedOutputStream;
import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

//import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.server.models.Mascota;
import com.example.server.repository.MascotaRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MascotaController {

    private static String imageDirectory = System.getProperty("user.dir") + "./images/";


    @Autowired
    MascotaRepository mascotaRep;
    
    @GetMapping("/mascotas")
    public ResponseEntity<List<Mascota>> getAllMascota(){
        List<Mascota> mascotas = new ArrayList<Mascota>();
        try {
            mascotaRep.findAll().forEach(mascotas::add);
            return new ResponseEntity<>(mascotas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/crear_Mascota")
    public ResponseEntity<Mascota> createMascota(@RequestBody Mascota mascota){
        try{
            Mascota _mascota = mascotaRep.save(new Mascota(
            mascota.getNombre(),mascota.getSexo(), 
            mascota.getPeso(), mascota.getRaza(), 
            mascota.getEdad(), mascota.getEspecie(), mascota.getTamanio(), 
            mascota.getDireccion(),mascota.getDescripcion(),
            mascota.getEsterilizado(),mascota.getVacunado(),
            mascota.getFechaNacimiento()
            ));
            return new ResponseEntity<>(_mascota, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update_mascota/{id")
    public ResponseEntity<Mascota> updateMascota(@PathVariable("id") long id, @RequestBody Mascota mascota ){
        Optional<Mascota> mascotaData= mascotaRep.findById(id);

        if(mascotaData.isPresent()){
            Mascota _mascota = mascotaData.get();
            _mascota.setNombre(mascota.getNombre());
            _mascota.setSexo(mascota.getSexo());
            _mascota.setPeso(mascota.getPeso());
            _mascota.setRaza(mascota.getRaza());
            _mascota.setEdad(mascota.getEdad());
            _mascota.setEspecie(mascota.getEspecie());
            _mascota.setTamanio(mascota.getTamanio());
            _mascota.setDireccion(mascota.getDireccion());
            _mascota.setDescripcion(mascota.getDescripcion());
            _mascota.setEsterilizado(mascota.getEsterilizado());
            _mascota.setVacunado(mascota.getVacunado());
            _mascota.setFechaNacimiento(mascota.getFechaNacimiento());
            return new ResponseEntity<>(mascotaRep.save(_mascota), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAllMascota")
    public ResponseEntity<HttpStatus> deleteAllMascota() {
        try {
        mascotaRep.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /*@PostMapping("/image")
    public ResponseEntity<?> uploadImage(
        @RequestParam("imageFile")MultipartFile file,
        @RequestParam("imageName")String name
    ){


        makeDirectoryIfNotExist(imageDirectory);
        Path fileNamePath = Paths.get(
            imageDirectory,
            name.concat(".").concat(FilenameUtils.getExtension(file.getOriginalFilename()))
        );
        //long id = 2;


        try{
            Files.write(fileNamePath, file.getBytes());
            
            
            return new ResponseEntity<>(fileNamePath, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("No se pudo cargar la imagen", HttpStatus.BAD_REQUEST);
        }
            
    }

    private void makeDirectoryIfNotExist(String imageDirectory){
        File directory = new File(imageDirectory);

        if(!directory.exists()){
            directory.mkdir();

        }

    }*/
}
