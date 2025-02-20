package com.vortexBird.moviePlatform.web.config;

import com.vortexBird.moviePlatform.persistence.crud.*;
import com.vortexBird.moviePlatform.persistence.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private RoleCrudRepository roleCrudRepository;

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Autowired
    private CategoryCrudRepository categoryCrudRepository;

    @Autowired
    private CityCrudRepository cityCrudRepository;

    @Autowired
    private LocationCrudRepository locationCrudRepository;

    @Autowired
    private CinemaRoomCrudRepository cinemaRoomCrudRepository;

    @Autowired
    private SeatCrudRepository seatCrudRepository;

    @Override
    public void run(String... args) throws Exception {
        //ROLES
        Role admin = new Role("ADMIN");
        admin = roleCrudRepository.save(admin);
        Role premium = new Role("PREMIUM_USER");
        premium = roleCrudRepository.save(premium);
        Role common = new Role("COMMON_USER");
        common = roleCrudRepository.save(common);
        //USUARIO INICIAL
        User user = new User();
        user.setNickname("adminCP");
        user.setEmail("nicolascfernandez95@gmail.com");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode("c1n3m4Plus4dm1n");
        user.setPassword(result);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(admin);
        userRoles.add(premium);
        userRoles.add(common);
        user.setRoles(userRoles);
        userCrudRepository.save(user);
        //CATEGORIAS
        Category horror = new Category("Horror");
        horror = categoryCrudRepository.save(horror);
        Category sci_fi = new Category("Science Fiction");
        sci_fi = categoryCrudRepository.save(sci_fi);
        Category comedy = new Category("Comedy");
        comedy = categoryCrudRepository.save(comedy);
        Category action = new Category("Action");
        action = categoryCrudRepository.save(action);
        Category drama = new Category("Drama");
        drama = categoryCrudRepository.save(drama);
        Category animation = new Category("Animation");
        animation = categoryCrudRepository.save(animation);
        Category history = new Category("History");
        history = categoryCrudRepository.save(history);
        Category documentary = new Category("Documentary");
        documentary = categoryCrudRepository.save(documentary);
        Category mystery = new Category("Mystery");
        mystery = categoryCrudRepository.save(mystery);
        Category adventure = new Category("Adventure");
        adventure = categoryCrudRepository.save(adventure);
        //PELICULAS

        //CIUDADES
        City popayan = new City("Popayán");
        popayan = cityCrudRepository.save(popayan);
        City cali = new City("Cali");
        cali = cityCrudRepository.save(cali);
        City bogota = new City("Bogotá");
        bogota = cityCrudRepository.save(bogota);
        //UBICACIONES
        Location terraplaza = new Location("Centro comercial Terraplaza", "Carrera 9 #73 AN -200 Norte", popayan);
        terraplaza = locationCrudRepository.save(terraplaza);
        Location campanario = new Location("Centro comercial Campanario", "Cra. 9 #24AN-21", popayan);
        campanario = locationCrudRepository.save(campanario);
        Location jardinPlaza = new Location("Centro comercial Jardin Plaza", "Cra. 98 #16-200", cali);
        jardinPlaza = locationCrudRepository.save(jardinPlaza);
        Location chipichape = new Location("Centro comercial Chipichape", "Cl. 38 Nte. #6N – 45", cali);
        chipichape = locationCrudRepository.save(chipichape);
        Location santaFe = new Location("Centro comercial SantaFe", "Cl. 185 #453", bogota);
        santaFe = locationCrudRepository.save(santaFe);
        Location galerias = new Location("Centro comercial Galerias", "Cl. 53b #25-21", bogota);
        galerias = locationCrudRepository.save(galerias);
        //SALAS DE CINE
        List<CinemaRoom> cinemaRooms = new ArrayList<>();
        //Terraplaza
        CinemaRoom sala1T = new CinemaRoom((short) 1, 100, terraplaza, 8500);
        CinemaRoom sala2T = new CinemaRoom((short) 2, 120, terraplaza, 9000);
        CinemaRoom sala3T = new CinemaRoom((short) 3, 100, terraplaza, 8500);
        CinemaRoom sala4T = new CinemaRoom((short) 4, 80, terraplaza, 7500);
        CinemaRoom sala5T = new CinemaRoom((short) 5, 80, terraplaza, 7500);
        sala1T = cinemaRoomCrudRepository.save(sala1T);
        sala2T = cinemaRoomCrudRepository.save(sala2T);
        sala3T = cinemaRoomCrudRepository.save(sala3T);
        sala4T = cinemaRoomCrudRepository.save(sala4T);
        sala5T = cinemaRoomCrudRepository.save(sala5T);
        cinemaRooms.add(sala1T);
        cinemaRooms.add(sala2T);
        cinemaRooms.add(sala3T);
        cinemaRooms.add(sala4T);
        cinemaRooms.add(sala5T);
        //Campanario
        CinemaRoom sala1C = new CinemaRoom((short) 1, 80, campanario, 7000);
        CinemaRoom sala2C = new CinemaRoom((short) 2, 100, campanario, 7500);
        CinemaRoom sala3C = new CinemaRoom((short) 3, 80, campanario, 7000);
        CinemaRoom sala4C = new CinemaRoom((short) 4, 100, campanario, 7500);
        sala1C = cinemaRoomCrudRepository.save(sala1C);
        sala2C = cinemaRoomCrudRepository.save(sala2C);
        sala3C = cinemaRoomCrudRepository.save(sala3C);
        sala4C = cinemaRoomCrudRepository.save(sala4C);
        cinemaRooms.add(sala1C);
        cinemaRooms.add(sala2C);
        cinemaRooms.add(sala3C);
        cinemaRooms.add(sala4C);
        //JardinPlaza
        CinemaRoom sala1J = new CinemaRoom((short) 1, 120, jardinPlaza, 12000);
        CinemaRoom sala2J = new CinemaRoom((short) 2, 120, jardinPlaza, 12000);
        CinemaRoom sala3J = new CinemaRoom((short) 3, 100, jardinPlaza, 10500);
        CinemaRoom sala4J = new CinemaRoom((short) 4, 80, jardinPlaza, 9000);
        CinemaRoom sala5J = new CinemaRoom((short) 5, 80, jardinPlaza, 9000);
        CinemaRoom sala6J = new CinemaRoom((short) 6, 80, jardinPlaza, 9000);
        CinemaRoom sala7J = new CinemaRoom((short) 7, 100, jardinPlaza, 10500);
        sala1J = cinemaRoomCrudRepository.save(sala1J);
        sala2J = cinemaRoomCrudRepository.save(sala2J);
        sala3J = cinemaRoomCrudRepository.save(sala3J);
        sala4J = cinemaRoomCrudRepository.save(sala4J);
        sala5J = cinemaRoomCrudRepository.save(sala5J);
        sala6J = cinemaRoomCrudRepository.save(sala6J);
        sala7J = cinemaRoomCrudRepository.save(sala7J);
        cinemaRooms.add(sala1J);
        cinemaRooms.add(sala2J);
        cinemaRooms.add(sala3J);
        cinemaRooms.add(sala4J);
        cinemaRooms.add(sala5J);
        cinemaRooms.add(sala6J);
        cinemaRooms.add(sala7J);
        //Chipichape
        CinemaRoom sala1CH = new CinemaRoom((short) 1, 120, chipichape, 12000);
        CinemaRoom sala2CH = new CinemaRoom((short) 2, 150, chipichape, 14000);
        CinemaRoom sala3CH = new CinemaRoom((short) 3, 120, chipichape, 12000);
        CinemaRoom sala4CH = new CinemaRoom((short) 4, 100, chipichape, 10500);
        CinemaRoom sala5CH = new CinemaRoom((short) 5, 80, chipichape, 9000);
        CinemaRoom sala6CH = new CinemaRoom((short) 6, 80, chipichape, 9000);
        CinemaRoom sala7CH = new CinemaRoom((short) 7, 100, chipichape, 10500);
        CinemaRoom sala8CH = new CinemaRoom((short) 8, 120, chipichape, 12000);
        sala1CH = cinemaRoomCrudRepository.save(sala1CH);
        sala2CH = cinemaRoomCrudRepository.save(sala2CH);
        sala3CH = cinemaRoomCrudRepository.save(sala3CH);
        sala4CH = cinemaRoomCrudRepository.save(sala4CH);
        sala5CH = cinemaRoomCrudRepository.save(sala5CH);
        sala6CH = cinemaRoomCrudRepository.save(sala6CH);
        sala7CH = cinemaRoomCrudRepository.save(sala7CH);
        sala8CH = cinemaRoomCrudRepository.save(sala8CH);
        cinemaRooms.add(sala1CH);
        cinemaRooms.add(sala2CH);
        cinemaRooms.add(sala3CH);
        cinemaRooms.add(sala4CH);
        cinemaRooms.add(sala5CH);
        cinemaRooms.add(sala6CH);
        cinemaRooms.add(sala7CH);
        cinemaRooms.add(sala8CH);
        //SantaFé
        CinemaRoom sala1S = new CinemaRoom((short) 1, 120, santaFe, 13000);
        CinemaRoom sala2S = new CinemaRoom((short) 2, 150, santaFe, 15000);
        CinemaRoom sala3S = new CinemaRoom((short) 3, 150, santaFe, 15000);
        CinemaRoom sala4S = new CinemaRoom((short) 4, 100, santaFe, 11500);
        CinemaRoom sala5S = new CinemaRoom((short) 5, 80, santaFe, 10000);
        CinemaRoom sala6S = new CinemaRoom((short) 6, 80, santaFe,10000);
        CinemaRoom sala7S = new CinemaRoom((short) 7, 100, santaFe, 11500);
        CinemaRoom sala8S = new CinemaRoom((short) 8, 120, santaFe, 13000);
        CinemaRoom sala9S = new CinemaRoom((short) 9, 120, santaFe, 13000);
        sala1S = cinemaRoomCrudRepository.save(sala1S);
        sala2S = cinemaRoomCrudRepository.save(sala2S);
        sala3S = cinemaRoomCrudRepository.save(sala3S);
        sala4S = cinemaRoomCrudRepository.save(sala4S);
        sala5S = cinemaRoomCrudRepository.save(sala5S);
        sala6S = cinemaRoomCrudRepository.save(sala6S);
        sala7S = cinemaRoomCrudRepository.save(sala7S);
        sala8S = cinemaRoomCrudRepository.save(sala8S);
        sala9S = cinemaRoomCrudRepository.save(sala9S);
        cinemaRooms.add(sala1S);
        cinemaRooms.add(sala2S);
        cinemaRooms.add(sala3S);
        cinemaRooms.add(sala4S);
        cinemaRooms.add(sala5S);
        cinemaRooms.add(sala6S);
        cinemaRooms.add(sala7S);
        cinemaRooms.add(sala8S);
        cinemaRooms.add(sala9S);
        //GALERIAS
        CinemaRoom sala1G = new CinemaRoom((short) 1, 100, galerias, 10000);
        CinemaRoom sala2G = new CinemaRoom((short) 2, 100, galerias, 10000);
        CinemaRoom sala3G = new CinemaRoom((short) 3, 90, galerias, 9000);
        CinemaRoom sala4G = new CinemaRoom((short) 4, 90, galerias, 9000);
        CinemaRoom sala5G = new CinemaRoom((short) 5, 70, galerias, 7500);
        sala1G = cinemaRoomCrudRepository.save(sala1G);
        sala2G = cinemaRoomCrudRepository.save(sala2G);
        sala3G = cinemaRoomCrudRepository.save(sala3G);
        sala4G = cinemaRoomCrudRepository.save(sala4G);
        sala5G = cinemaRoomCrudRepository.save(sala5G);
        cinemaRooms.add(sala1G);
        cinemaRooms.add(sala2G);
        cinemaRooms.add(sala3G);
        cinemaRooms.add(sala4G);
        cinemaRooms.add(sala5G);
        //SEATS

        for (CinemaRoom room : cinemaRooms) {
            LOGGER.info("Sala:" + room.getRoomNumber() + ", ubicada en: " + room.getLocation().getName());
            LOGGER.info("Capacidad: " + room.getCapacity() + " personas.");
            int capacity = room.getCapacity();
            List<Seat> seats = new ArrayList<>();
            for (int i = 1; i <= capacity; i++) {

                LOGGER.info("Silla - " + i);
                String seatCode = "Seat-" + i;

                Seat seat = new Seat(seatCode, room);
                seats.add(seat);
            }
            seatCrudRepository.saveAll(seats);
        }
    }
}
