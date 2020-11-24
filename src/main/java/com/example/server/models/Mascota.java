package com.example.server.models;

//import java.util.Calendar;
//import java.util.Objects;
import javax.persistence.*;

//import org.graalvm.compiler.nodes.ReturnNode;


@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "edad")
    private String edad;

    @Column(name = "especie")
    private String especie;

    @Column(name = "tamaño")
    private String tamaño;

    @Column(name = "raza")
    private String raza;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "fecha_nacimiento")
    private String fecha;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "peso")
    private double peso;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "esterilizado")
    private String esterelizado;

    @Column(name = "vacunado")
    private String vacunado;

    @Column(name = "imagen")
    private String imagen;
    public Mascota(){}
    public Mascota(
        String nombre, 
    String sexo, 
    double peso,
    String raza, 
    String edad, 
    String especie,
    String tamaño, 
    String direccion, 
    String descripcion,
    String esterelizado,
    String vacunado,
    String fecha){
        
        this.nombre = nombre;
        this.sexo = sexo;
        this.raza = raza;
        this.peso = peso;
        this.edad = edad;
        this.especie = especie;
        this.tamaño = tamaño;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.esterelizado = esterelizado;
        this.vacunado = vacunado;
        this.fecha = fecha;
    }

    public long getId(){
        return this.id;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getEdad(){
        return this.edad;
    }

    public void setEdad(String edad){
        this.edad = edad;
    }

    public String getEspecie(){
        return this.especie;
    }

    public void setEspecie(String especie){
        this.especie = especie;
    }

    public String getTamanio(){
        return this.tamaño;
    }

    public void setTamanio(String tam){
        this.tamaño = tam;
    }

    public String getRaza(){
        return this.raza;
    }

    public void setRaza(String raza){
        this.raza = raza;
    }

    public String getDireccion(){
        return this.direccion;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    public String getFechaNacimiento(){
        return fecha;
    }

    public void setFechaNacimiento(String fecha){
        this.fecha = fecha;
    }

    public String getSexo(){
        return this.sexo;
    }

    public void setSexo(String sexo){
        this.sexo = sexo;
    }

    public double getPeso(){
        return this.peso;
    }

    public void setPeso(double peso){
        this.peso = peso;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getEsterilizado(){
        return this.esterelizado;
    }

    public void setEsterilizado(String esterelizado){
        this.esterelizado = esterelizado;
    }

    public String getVacunado(){
        return this.vacunado;
    }

    public void setVacunado(String vacunado){
        this.vacunado = vacunado;
    }

    public String getImagen(){
        return this.imagen;
    }

    public void setImagen(String imagen){
        this.imagen = imagen;
    }
}
