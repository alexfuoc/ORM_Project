package com.fdflib.example;

import com.fdflib.example.model.*;
import com.fdflib.example.service.RoleService;
import com.fdflib.example.service.CarService;
import com.fdflib.example.service.DriverService;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.state.FdfTenant;
import com.fdflib.persistence.database.DatabaseUtil;
import com.fdflib.service.FdfServices;
import com.fdflib.service.FdfTenantServices;
import com.fdflib.util.FdfSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexfuoc, ORM Project
 * Main driver for the Mapcushion ORM
 */
public class MapcushionService {
    public static void main(String[] args) {
        System.out.println("Hello 4DF World!"); // Display the string.

        // use the  settings within this method to customize the 4DFLib.  Note, everything in this method is optional.
        setOptionalSettings();

        // Create a array that will hold the classes that make up our 4df data model
        List<Class> myModel = new ArrayList<>();

        // Add our 2 classes
        myModel.add(Driver.class);
        myModel.add(Car.class);
        myModel.add(Role.class);
        myModel.add(User.class);
        myModel.add(IdCredentials.class);
        myModel.add(Address.class);
        myModel.add(UserRole.class);
        myModel.add(TenantRole.class);
        myModel.add(TenantUser.class);
        myModel.add(Location.class);
        myModel.add(Floor.class);
        myModel.add(Beacon.class);


        // call the initialization of library!
        FdfServices.initializeFdfDataModel(myModel);

        // insert some demo data
        try {
            insertSomeData();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        // do a few queries and output the results

    }

     /**
     * Everything set in this method is optional, but useful
     */
    private static void setOptionalSettings() {

        // get the 4dflib settings singleton
        FdfSettings fdfSettings = FdfSettings.getInstance();

        // set the database type and name and connection information
        // PostgreSQL settings, Using PostgreSQL 12
        fdfSettings.PERSISTENCE = DatabaseUtil.DatabaseType.POSTGRES;
        fdfSettings.DB_PROTOCOL = DatabaseUtil.DatabaseProtocol.JDBC_POSTGRES;

        // postgres default root user
        // root user settings are only required for initial database creation.  Once the database is created you
        // should remove this information
        fdfSettings.DB_ROOT_USER = "postgres";
        // root user password
        fdfSettings.DB_ROOT_PASSWORD = "";

        // Database encoding
        fdfSettings.DB_ENCODING = DatabaseUtil.DatabaseEncoding.UTF8;

        // Application Database name
        fdfSettings.DB_NAME = "Mapcushion";

        // Database host
        fdfSettings.DB_HOST = "localhost";

        // Port is not required for DB defaults can be changed when needed
        // fdfSettings.DB_PORT = 3306;

        // Database user information
        fdfSettings.DB_USER = "mapcushionuser";
        fdfSettings.DB_PASSWORD = "mapcushionpass";


        // set the default system information
        fdfSettings.DEFAULT_SYSTEM_NAME = "Mapcushion API";
        fdfSettings.DEFAULT_SYSTEM_DESCRIPTION = "Central API service for the Mapcushion Application";

        // set the default tenant information
        fdfSettings.DEFAULT_TENANT_NAME = "Mapcushion";
        fdfSettings.DEFAULT_TENANT_DESRIPTION = "Main system Tenant";
        fdfSettings.DEFAULT_TENANT_IS_PRIMARY = true;
        fdfSettings.DEFAULT_TENANT_WEBSITE = "http://www.4dflib.com";

        // local dev, no ssl
        fdfSettings.USE_SSL = false;


    }

    private static void insertSomeData() throws InterruptedException {
        //Services
        DriverService ds = new DriverService();
        CarService cs = new CarService();
        RoleService rs = new RoleService();
        FdfTenantServices tenantService = new FdfTenantServices();

        // create a new tenant
        FdfTenant tenant2 = new FdfTenant();
        tenant2.name = "Mapcushion2";
        tenant2.description = "Second tenant system.";
        tenant2.isPrimary = false;
        tenantService.saveTenant(tenant2);

        // create default roles
        Role clientUser = new Role();
        clientUser.name = "Client-User";
        clientUser.description = "Basic client user account can report locations to the system but not\n" +
                "see maps or locations.";
        rs.saveRole(clientUser);

        Role clientMapViewer = new Role();
        clientMapViewer.name = "Client-Map-Viewer";
        clientMapViewer.description = "Client account with privileges to view users current location\n" +
                "information, can be used by school administrators and local authorities to view\n" +
                "the current location of personal without needing to modify information.";
        rs.saveRole(clientMapViewer);

        Role clientBeaconManager = new Role();
        clientBeaconManager.name = "Client-Beacon-Manager";
        clientBeaconManager.description = "Manager of client account can perform client operations\n" +
                "including managing beacons and viewing users current locations";
        rs.saveRole(clientBeaconManager);

        Role clientManager = new Role();
        clientManager.name = "Client-Manager";
        clientManager.description = "Manager of client account can perform client operations\n" +
                "including managing maps, beacons and viewing users current and past locations";
        rs.saveRole(clientManager);

        Role clientAdministrator = new Role();
        clientAdministrator.name = "Client-Administrator";
        clientAdministrator.description = "Can administer information within client account";
        rs.saveRole(clientAdministrator);

        Role omniAdministrator = new Role();
        omniAdministrator.name = "Omni-Administrator";
        omniAdministrator.description = "Can administer any client, can also create, edit or delete\n" +
                "clients";
        rs.saveRole(omniAdministrator);


        // create a few of drivers
        Driver sam = new Driver();
        sam.firstName = "Sam";
        sam.lastName = "Holden";
        sam.phoneNumber = "212-555-1212";
        ds.saveDriver(sam);

        Driver harry = new Driver();
        harry.firstName = "Harry";
        harry.lastName = "Smith";
        harry.phoneNumber = "212-555-1313";
        harry = ds.saveDriver(harry);

        Driver jack = new Driver();
        jack.firstName = "Jack";
        jack.lastName = "Johnson";
        jack.phoneNumber = "212-555-1414";
        jack = ds.saveDriver(jack);

        Driver brian = new Driver();
        brian.firstName = "Brian";
        brian.lastName = "G";
        brian.phoneNumber = "845-555-1114";
        brian = ds.saveDriver(brian);

        Car mufasa = new Car();
        mufasa.name = "Mufasa";
        mufasa.color = "Teal";
        mufasa.isInNeedOfRepair = false;
        mufasa.description = "Total awesomeness";
        mufasa.make = CarMake.PONTIAC;
        mufasa.model = "Trans Am";
        mufasa.year = 1983;
        mufasa.currentDriverId = brian.id;
        cs.saveCar(mufasa);

        Car ss = new Car();
        ss.name = "sliver ss";
        ss.color = "Sliver";
        ss.isInNeedOfRepair = false;
        ss.description = "Nice Sleeper";
        ss.make = CarMake.CHEVY;
        ss.model = "SS";
        ss.year = 2014;
        cs.saveCar(ss);

        Car pbox = new Car();
        pbox.name = "Brians project";
        pbox.color = "Gray";
        pbox.isInNeedOfRepair = false;
        pbox.description = "Roadster";
        pbox.make = CarMake.PORSCHE;
        pbox.model = "Boxster s";
        pbox.year = 2001;
        cs.saveCar(pbox);

        Car pilot = new Car();
        pilot.name = "Current Family Hauler";
        pilot.color = "Maroon";
        pilot.isInNeedOfRepair = false;
        pilot.description = "Family Fun!";
        pilot.make = CarMake.HONDA;
        pilot.model = "Pilot";
        pilot.year = 2019;
        pilot.currentDriverId = brian.id;
        cs.saveCar(pilot);

        Car van = new Car();
        van.name = "Minivan o death";
        van.color = "Red";
        van.isInNeedOfRepair = false;
        van.description = "POS";
        van.make = CarMake.CHRYSLER;
        van.model = "Town & Country";
        van.year = 2014;
        cs.saveCar(van);

        Car cv1 = new Car();
        cv1.name = "Medallion 1";
        cv1.color = "Yellow";
        cv1.isInNeedOfRepair = false;
        cv1.description = "NYC has the best taxis";
        cv1.make = CarMake.FORD;
        cv1.model = "LTD Crown Victoria";
        cv1.year = 2001;
        // assign jack to the this car
        cv1.currentDriverId = jack.id;
        cs.saveCar(cv1);

        /*
            Lets do some stuff with the data
         */

        Thread.sleep(3000);

        // since we did not assign anyone to drive Brians project, lets do that now
        pbox = cs.getCarsByName("Brians project");
        // output the current driver id
        if(pbox != null) {
            System.out.println("Car: " + pbox.model + " Driver Id (shoud be empty): " + pbox.currentDriverId);
        }
        

        // assign me to drive
        pbox.currentDriverId = brian.id;
        // Wait a few seconds
        Thread.sleep(3000);
        cs.saveCar(pbox);

        // now try again
        pbox = cs.getCarsByName("Brians project");;
        System.out.println("Car: " + pbox.model + " Driver Id: "
                + pbox.currentDriverId + " [assigned!]"); // should have an id now!


        // lets also find all cars from the year 2001
        List<Car> cars01 = cs.getCarsByYear(2001);
        for(Car car: cars01) {
            // see if there should be a driver and get the driver if so!
            if(car.currentDriverId >= 0) {
                car.currentDriver = ds.getDriverById(car.currentDriverId);
            }
            String driver = (car.currentDriver != null) ? car.currentDriver.firstName : "no driver";

            System.out.println("2001 car: " + car.name + " which is a " + car.make + " " + car.model + " driven by: "
                    + driver + " has a current repair status: " + car.isInNeedOfRepair);
        }

        // Wait a few seconds
        Thread.sleep(6000);

        // change all 2014 cars status to needing repair
        for(Car car: cars01) {
            car.isInNeedOfRepair = true;
            cs.saveCar(car);
        }

        // Wait a few seconds
        Thread.sleep(6000);

        // get one of the cars and change it back
        List<Car> cars01new = cs.getCarsByYear(2001);
        cars01new.get(0).isInNeedOfRepair = false;
        cs.saveCar(cars01new.get(0));

        System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        // re-run the query and output the results again, this time with history so we can see the change!
        List<FdfEntity<Car>> cars01withHistory = cs.getCarsByYearWithHistory(2001);
        for(FdfEntity<Car> carWithHistory: cars01withHistory) {
            // first output the cars current status
            System.out.println("2001 cars [after updates]: " + carWithHistory.current.name + " which is a "
                    + carWithHistory.current.make + " " + carWithHistory.current.model
                    + " has a [current] repair status: " + carWithHistory.current.isInNeedOfRepair);

            System.out.println("----- History -----");
            // Now show the historical records for the car
            for(Car carHistory : carWithHistory.history) {
                System.out.println("Start time: " + carHistory.arsd + " End time: " + carHistory.ared
                        + " repair status: " + carHistory.isInNeedOfRepair + " it was driven by userid "
                        + carHistory.currentDriverId);
            }
            System.out.println("___________________");

        }

        // for fun lets try a custom query
        Car myBoxster = cs.customCarQuery("Brians project").current;
        System.out.println("\n^^^^^^^^^^^^^^^^ Custom Query Results ^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("year make and model: " + myBoxster.year + " " + myBoxster.make + " " + myBoxster.model);
        System.out.println("name: " + myBoxster.name + " Desc: " + myBoxster.description);
        System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        List<FdfTenant> tenants = tenantService.getAllTenants();
        System.out.println("\n---------------- Tenant Query ----------------");
        for(FdfTenant tenant: tenants){
            System.out.println("Tenant Id: " + tenant.id + "\t Tenant Name: "
                    + tenant.name + "\nTenant Description: "
                    + tenant.description);
        }

    }
}