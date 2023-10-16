
# Api Rest Foro Alura

Api desarrollada con SpringBoot, donde es posible relizar un Crud de topicos y respuesta de los mismo. Como a su vez, realizar el resgitro y logueo de usuario.


## Documentación
Para poder probar la api en su ordenador debe configurar los siguientes parametros en su IDE y a su vez debe estar corriendo MySql WorkBrench para que la base de datos tenga el servidor levantado.

Debera modificar el archivo application.propertie que se encuentra en la ruta(src/main/resources)

En "foroalura" debera crear en su base de datos un Schema con el mismo nombre o bien modifcar ese nombre al Schema que usted creo.

En username y password debera colocar sus respectivos datos (Los que use en MySql WorkBrench).

![Properties](https://i.imgur.com/0Q4iS4n.png)


## Deployment

Una vez realizado los cambios puede iniciar la aplicacion desde su IDE, se levantara el server el el puerto 8080 y podra probar los endpoint de la api de forma sencilla ingesando a la siguiente url:

```bash
http://localhost:8080/swagger-ui/index.html#/
```

Cuando ingrese a la URL, vera una pestaña como la que se muestra acontinuación(Debera registarse para poder usar los endpoint que estan marcados como "PRIVATE", una vez loggeado debera ingresar el token en Authorize)


![URL](https://i.imgur.com/mPPAfEL.png)


## 🌐 Socials:
[![Instagram](https://img.shields.io/badge/Instagram-%23E4405F.svg?logo=Instagram&logoColor=white)](https://www.instagram.com/_.juan.bravo._/) [![LinkedIn](https://img.shields.io/badge/LinkedIn-%230077B5.svg?logo=linkedin&logoColor=white)](https://linkedin.com/in/juan-bravo-7b0930244) 
