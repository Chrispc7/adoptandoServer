package com.example.server.controllers;

//import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.nio.file.Paths;
import java.util.Optional;
/*import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;
import javax.xml.transform.Source;
import java.util.List;
import java.util.stream.Collectors;

import java.net.MalformedURLException;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.activation.FileTypeMap;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;*/

import com.example.server.repository.MascotaRepository;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.parser.MediaType;
//import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.FileSystemUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
/*import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;*/


//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.server.models.Mascota;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/upload")


public class ImageUploadController {
    private static String imageDirectory = System.getProperty("user.dir") + "./images/";
    private Path rootPath = Paths.get("uploads");
    @Autowired
    MascotaRepository mascotaRep;

    @GetMapping("/obtener_image/{img}")
    @ResponseBody
    public ResponseEntity<?> getImage(
        @PathVariable("img")String img
    ){
        
        try {
            
       
            //Resource file =  this.load(img);
            Path files = rootPath.resolve(img);
            return new ResponseEntity<>(files,HttpStatus.OK);
            
            /*return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"",file.getFilename()+"\"").body(file);*/
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        
    }

    private Resource load(String filename){
        try{
            
            Path file = rootPath.resolve(filename);
            Resource resource = new  UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("Could not read the file!");
            }
            

        
        }catch(Exception e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    

    @PostMapping("/image/{id}")
    public ResponseEntity<?> uploadImage(
        @PathVariable("id") long id,
        @RequestParam("imageFile")MultipartFile file
    ){
        
        /*makeDirectoryIfNotExist(imageDirectory);
        Path fileNamePath = Paths.get(
            imageDirectory,
            name.concat(".").concat(FilenameUtils.getExtension(file.getOriginalFilename()))
        );*/
        Optional<Mascota> mascotaData= mascotaRep.findById(id);
        

        try{
            if(mascotaData.isPresent()){
                Mascota _mascota = mascotaData.get();
                _mascota.setImagen(file.getOriginalFilename());
                Files.copy(file.getInputStream(), this.rootPath.resolve(file.getOriginalFilename()));

                //Files.write(fileNamePath, file.getBytes());
                return new ResponseEntity<>(_mascota, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("no existe",HttpStatus.NOT_FOUND);
            }
            
            
        }catch(Exception e){
            return new ResponseEntity<>("No se pudo cargar la imagen", HttpStatus.BAD_REQUEST);
        }
            
    }

    public void init() {
        try {
            
            Files.createDirectory(rootPath);
        } catch (Exception e) {
          throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    private void makeDirectoryIfNotExist(String imageDirectory){
        File directory = new File(imageDirectory);

        if(!directory.exists()){
            directory.mkdir();

        }

    }

    @DeleteMapping("/image")
    public ResponseEntity<?> deleteAllFile(){
        try {
            FileSystemUtils.deleteRecursively(rootPath.toFile());
            return new ResponseEntity<>("Eliminado",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo cargar la imagen", HttpStatus.BAD_REQUEST);
        }
    }


    //prueba sin usar
    @RequestMapping(value="/uploads", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<String>  handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) throws Exception{
        if (name.contains("/")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Folder separators not allowed.");
        } else if (name.contains("/")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Relative pathnames not allowed.");
        } else if (!name.endsWith(".jar")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("File type not allowed.  Must be a Jar file type ending in '.jar'.");
        }

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                return ResponseEntity.ok("File " + name + " uploaded.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("You failed to upload " + name + " because the file was empty.");
        }
    }
    

    /*@PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }*/


   
}
