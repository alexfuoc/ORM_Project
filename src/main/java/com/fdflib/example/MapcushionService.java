package com.fdflib.example;

import com.fdflib.example.model.*;
import com.fdflib.example.service.*;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.state.FdfTenant;
import com.fdflib.persistence.database.DatabaseUtil;
import com.fdflib.service.FdfServices;
import com.fdflib.service.FdfTenantServices;
import com.fdflib.util.FdfSettings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
        myModel.add(Role.class);
        myModel.add(User.class);
        myModel.add(IdCredentials.class);
        myModel.add(Device.class);
        myModel.add(Address.class);
        myModel.add(UserRole.class);
        myModel.add(TenantRole.class);
        myModel.add(TenantUser.class);
        myModel.add(Location.class);
        myModel.add(Floor.class);
        myModel.add(Beacon.class);
        myModel.add(BeaconReport.class);
        myModel.add(GuestVisit.class);
        myModel.add(UserAddress.class);


        // call the initialization of library!
        FdfServices.initializeFdfDataModel(myModel);

        // insert the Data
        try {
            insertSomeData();
            queryData();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

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
        fdfSettings.DB_NAME = "MapcushionHP";

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
        fdfSettings.DEFAULT_TENANT_NAME = "Hogwarts";
        fdfSettings.DEFAULT_TENANT_DESRIPTION = "Hogwarts School of Witchcraft and Wizardry. Main system Tenant";
        fdfSettings.DEFAULT_TENANT_IS_PRIMARY = true;

        // local dev, no ssl
        fdfSettings.USE_SSL = false;


    }

    /**
     * Inserts the data into the DB.
     * @throws InterruptedException
     */
    private static void insertSomeData() throws InterruptedException {
        //Services
        RoleService rs = new RoleService();
        TenantRoleService trs = new TenantRoleService();
        FdfTenantServices tenantService = new FdfTenantServices();
        UserService us = new UserService();
        TenantUserService tus = new TenantUserService();
        FdfTenantServices ts = new FdfTenantServices();
        IdCredentialsService ids = new IdCredentialsService();
        UserRoleService urs = new UserRoleService();
        LocationService ls = new LocationService();
        FloorService fs = new FloorService();
        BeaconService bs = new BeaconService();
        DeviceService ds = new DeviceService();
        GuestVisitService gvs = new GuestVisitService();


        // create a new tenant
        FdfTenant tenant2 = new FdfTenant();
        tenant2.name = "Diagon Alley";
        tenant2.description = "A high street in London. Second tenant system.";
        tenant2.isPrimary = false;
        ts.saveTenant(tenant2);

        //Get both Tenants
        List<FdfTenant> tenants = tenantService.getAllTenants();

        // create default roles
        ArrayList<Role> defaultRoles = new ArrayList<Role>();
        Role clientUser = new Role();
        clientUser.name = "Client-User";
        clientUser.description = "Basic client user account can report locations to the system but not\n" +
                "see maps or locations.";
        rs.saveRole(clientUser);
        defaultRoles.add(clientUser);

        Role clientMapViewer = new Role();
        clientMapViewer.name = "Client-Map-Viewer";
        clientMapViewer.description = "Client account with privileges to view users current location\n" +
                "information, can be used by school administrators and local authorities to view\n" +
                "the current location of personal without needing to modify information.";
        rs.saveRole(clientMapViewer);
        defaultRoles.add(clientMapViewer);

        Role clientBeaconManager = new Role();
        clientBeaconManager.name = "Client-Beacon-Manager";
        clientBeaconManager.description = "Manager of client account can perform client operations\n" +
                "including managing beacons and viewing users current locations";
        rs.saveRole(clientBeaconManager);
        defaultRoles.add(clientBeaconManager);

        Role clientManager = new Role();
        clientManager.name = "Client-Manager";
        clientManager.description = "Manager of client account can perform client operations\n" +
                "including managing maps, beacons and viewing users current and past locations";
        rs.saveRole(clientManager);
        defaultRoles.add(clientManager);

        Role clientAdministrator = new Role();
        clientAdministrator.name = "Client-Administrator";
        clientAdministrator.description = "Can administer information within client account";
        rs.saveRole(clientAdministrator);
        defaultRoles.add(clientAdministrator);

        Role omniAdministrator = new Role();
        omniAdministrator.name = "Omni-Administrator";
        omniAdministrator.description = "Can administer any client, can also create, edit or delete\n" +
                "clients";
        rs.saveRole(omniAdministrator);
        defaultRoles.add(omniAdministrator);

        //Custom Roles for Mapcushion2
        Role deviceAdministrator = new Role();
        deviceAdministrator.name = "Device-Administrator";
        deviceAdministrator.description = "Can administer any device data, can also create, edit or delete devices";
        rs.saveRole(deviceAdministrator);

        TenantRole deviceTR = new TenantRole();
        deviceTR.roleId = deviceAdministrator.id;
        deviceTR.tenantId = tenants.get(1).id;
        trs.saveTenantRole(deviceTR);

        Role userAdministrator = new Role();
        userAdministrator.name = "User-Administrator";
        userAdministrator.description = "Can administer any user, can also create, edit or delete users";
        rs.saveRole(userAdministrator);

        TenantRole userTR = new TenantRole();
        userTR.roleId = userAdministrator.id;
        userTR.tenantId = tenants.get(1).id;
        trs.saveTenantRole(userTR);

        Role beaconAdministrator = new Role();
        beaconAdministrator.name = "Beacon-Administrator";
        beaconAdministrator.description = "Can administer any beacon, can also create, edit or delete beacons";
        rs.saveRole(beaconAdministrator);

        TenantRole beaconTR = new TenantRole();
        beaconTR.roleId = beaconAdministrator.id;
        beaconTR.tenantId = tenants.get(1).id;
        trs.saveTenantRole(beaconTR);

        //Add the roles to TenantRole
        for(Role role: defaultRoles) {
            TenantRole tr1 = new TenantRole();
            tr1.roleId = role.id;
            tr1.tenantId = tenants.get(0).id;
            trs.saveTenantRole(tr1);
            TenantRole tr2 = new TenantRole();
            tr2.roleId = role.id;
            tr2.tenantId = tenants.get(1).id;
            trs.saveTenantRole(tr2);
        }

        // Add the users
        String[] firstName = {"Harry", "Ron", "Hermione", "Albus", "Draco", "Tom", "Rubeus", "Minerva",
                "Ginny", "Luna", "Bellatrix", "Sirius", "Severus", "Cho", "Cedric", "Fleur", "Fred",
                "George", "Lee", "Neville" };

        String[] lastName = {"Potter", "Weasley", "Granger", "Dumbledore", "Malfoy", "Riddle", "Hagrid",
                "McGonagall", "Weasley", "Lovegood", "Lestrange", "Black", "Snape", "Chang", "Diggory",
                "Delacour", "Weasley", "Weasley", "Jordan", "Longbottom"};

        String email = "@hogwarts.edu";

        String[] idType = {"Driver's License", "Passport", "Enhanced Id", "School Id"};
        String[] eyeColor = {"Blue", "Black", "Brown", "Green", "Hazel"};
        String[] gender = {"M", "F"};


        for(int i = 0; i < firstName.length; i++) {
            User user = new User();
            user.firstName = firstName[i];
            user.lastName = lastName[i];
            user.email = firstName[i] + lastName[i] + email;
            user.password = getRandomPassword();
            user.userColor = getRandomColor();
            us.saveUser(user);

            if(i < 10){
                TenantUser tu1 = new TenantUser();
                tu1.userId = user.id;
                tu1.tenantId = tenants.get(0).id;
                tus.saveTenantUser(tu1);

                //Add 3 users to tenant 2 also
                if(user.firstName == "Harry" || user.firstName == "Ron" || user.firstName == "Hermione") {
                    TenantUser tu2 = new TenantUser();
                    tu2.userId = user.id;
                    tu2.tenantId = tenants.get(1).id;
                    tus.saveTenantUser(tu2);
                }
            } else {
                TenantUser tu2 = new TenantUser();
                tu2.userId = user.id;
                tu2.tenantId = tenants.get(1).id;
                tus.saveTenantUser(tu2);
            }
        }

        //Add Roles to the User
        List<User> hogwartsUsers = tus.getUsersByTenantId(tenants.get(0).id);
        List<User> daUsers = tus.getUsersByTenantId(tenants.get(1).id);
        List<Role> allRolesHogwarts = trs.getRolesByTenant(tenants.get(0).id);
        List<Role> allRoleDA = trs.getRolesByTenant(tenants.get(1).id);

        // Assigning Roles
        // Harry
        UserRole hr1 = new UserRole();
        hr1.roleId = allRoleDA.get(8).id; // omni admin
        hr1.userId = daUsers.get(0).id;
        urs.saveUserRole(hr1);

        UserRole hr2 = new UserRole();
        hr2.roleId = allRoleDA.get(0).id; // device user
        hr2.userId = daUsers.get(0).id;
        urs.saveUserRole(hr2);

        UserRole hr3 = new UserRole();
        hr3.roleId = allRoleDA.get(7).id; // client admin
        hr3.userId = daUsers.get(0).id;
        urs.saveUserRole(hr3);

        // ron
        UserRole rr1 = new UserRole();
        rr1.roleId = allRoleDA.get(2).id; // beacon admin
        rr1.userId = daUsers.get(1).id;
        urs.saveUserRole(rr1);

        UserRole rr2 = new UserRole();
        rr2.roleId = allRoleDA.get(4).id; // client map viewer
        rr2.userId = daUsers.get(1).id;
        urs.saveUserRole(rr2);

        UserRole rr3 = new UserRole();
        rr3.roleId = allRoleDA.get(6).id; // client manager
        rr3.userId = daUsers.get(1).id;
        urs.saveUserRole(rr3);

        //Hermione
        UserRole hgr1 = new UserRole();
        hgr1.roleId = allRoleDA.get(5).id; // client beacon manager
        hgr1.userId = daUsers.get(2).id;
        urs.saveUserRole(hgr1);

        //Bellatrix
        UserRole br1 = new UserRole();
        br1.roleId = allRoleDA.get(7).id; // client admin
        br1.userId = daUsers.get(3).id;
        urs.saveUserRole(br1);

        UserRole br2 = new UserRole();
        br2.roleId = allRoleDA.get(3).id; // client user
        br2.userId = daUsers.get(3).id;
        urs.saveUserRole(br2);

        //Sirius
        UserRole sr1 = new UserRole();
        sr1.roleId = allRoleDA.get(3).id; // client user
        sr1.userId = daUsers.get(4).id;
        urs.saveUserRole(sr1);

        UserRole sr2 = new UserRole();
        sr2.roleId = allRoleDA.get(1).id; // user admin
        sr2.userId = daUsers.get(4).id;
        urs.saveUserRole(sr2);

        // Severus
        UserRole ssr = new UserRole();
        ssr.roleId = allRoleDA.get(3).id; // client user
        ssr.userId = daUsers.get(5).id;
        urs.saveUserRole(ssr);

        UserRole ssr1 = new UserRole();
        ssr1.roleId = allRoleDA.get(4).id; // client map viewer
        ssr1.userId = daUsers.get(5).id;
        urs.saveUserRole(ssr1);

        // Cho
        UserRole cr = new UserRole();
        cr.roleId = allRoleDA.get(7).id; // client admin
        cr.userId = daUsers.get(6).id;
        urs.saveUserRole(cr);

        UserRole cr2 = new UserRole();
        cr2.roleId = allRoleDA.get(8).id; // omni admin
        cr2.userId = daUsers.get(6).id;
        urs.saveUserRole(cr2);

        //Cedric
        UserRole cdr = new UserRole();
        cdr.roleId = allRoleDA.get(0).id; // device admin
        cdr.userId = daUsers.get(7).id;
        urs.saveUserRole(cdr);

        UserRole cdr2 = new UserRole();
        cdr2.roleId = allRoleDA.get(4).id; // client map viewer
        cdr2.userId = daUsers.get(7).id;
        urs.saveUserRole(cdr2);

        //Fleur
        UserRole fr = new UserRole();
        fr.roleId = allRoleDA.get(6).id; // client manager
        fr.userId = daUsers.get(8).id;
        urs.saveUserRole(fr);

        UserRole fr2 = new UserRole();
        fr2.roleId = allRoleDA.get(2).id; // beacon admin
        fr2.userId = daUsers.get(8).id;
        urs.saveUserRole(fr2);

        //Fred
        UserRole fwr = new UserRole();
        fwr.roleId = allRoleDA.get(7).id; // client manager
        fwr.userId = daUsers.get(9).id;
        urs.saveUserRole(fwr);

        UserRole fwr2 = new UserRole();
        fwr2.roleId = allRoleDA.get(8).id; // omni admin
        fwr2.userId = daUsers.get(9).id;
        urs.saveUserRole(fwr2);

        //George
        UserRole gwr = new UserRole();
        gwr.roleId = allRoleDA.get(0).id; // device admin
        gwr.userId = daUsers.get(10).id;
        urs.saveUserRole(gwr);

        UserRole gwr2 = new UserRole();
        gwr2.roleId = allRoleDA.get(1).id; // beacon admin
        gwr2.userId = daUsers.get(10).id;
        urs.saveUserRole(gwr2);

        UserRole gwr3 = new UserRole();
        gwr3.roleId = allRoleDA.get(5).id; // client beacon admin
        gwr3.userId = daUsers.get(10).id;
        urs.saveUserRole(gwr3);

        //Lee
        UserRole lr = new UserRole();
        lr.roleId = allRoleDA.get(3).id; // client user
        lr.userId = daUsers.get(11).id;
        urs.saveUserRole(lr);

        UserRole lr2 = new UserRole();
        lr2.roleId = allRoleDA.get(7).id; // client admin
        lr2.userId = daUsers.get(11).id;
        urs.saveUserRole(lr2);

        //Neville
        UserRole nlr = new UserRole();
        nlr.roleId = allRoleDA.get(3).id; // client user
        nlr.userId = daUsers.get(12).id;
        urs.saveUserRole(nlr);

        UserRole nlr2 = new UserRole();
        nlr2.roleId = allRoleDA.get(0).id; // device admin
        nlr2.userId = daUsers.get(12).id;
        urs.saveUserRole(nlr2);

        //Albus
        UserRole ar = new UserRole();
        ar.roleId = allRolesHogwarts.get(4).id; // client admin
        ar.userId = hogwartsUsers.get(3).id;
        urs.saveUserRole(ar);

        //Draco
        UserRole dr = new UserRole();
        dr.roleId = allRolesHogwarts.get(1).id; // client map viewer
        dr.userId = hogwartsUsers.get(4).id;
        urs.saveUserRole(dr);

        //Tom
        UserRole tr = new UserRole();
        tr.roleId = allRolesHogwarts.get(2).id; // client beacon manager
        tr.userId = hogwartsUsers.get(5).id;
        urs.saveUserRole(tr);

        //Rubeus
        UserRole rhr = new UserRole();
        rhr.roleId = allRolesHogwarts.get(5).id; // omni admin
        rhr.userId = hogwartsUsers.get(6).id;
        urs.saveUserRole(rhr);

        UserRole rhr2 = new UserRole();
        rhr2.roleId = allRolesHogwarts.get(0).id; // client user
        rhr2.userId = hogwartsUsers.get(6).id;
        urs.saveUserRole(rhr2);

        //Minerva
        UserRole mr = new UserRole();
        mr.roleId = allRolesHogwarts.get(4).id; // client admin
        mr.userId = hogwartsUsers.get(7).id;
        urs.saveUserRole(mr);

        //Ginny
        UserRole gr = new UserRole();
        gr.roleId = allRolesHogwarts.get(2).id; // client beacon manager
        gr.userId = hogwartsUsers.get(8).id;
        urs.saveUserRole(gr);

        //Luna
        UserRole llr = new UserRole();
        llr.roleId = allRolesHogwarts.get(3).id; // client manager
        llr.userId = hogwartsUsers.get(9).id;
        urs.saveUserRole(llr);

        //Create the Locations, Floors and Beacons
        Location hogwarts = new Location();
        hogwarts.name = "Hogwarts";
        hogwarts.description = "The main location of Hogwarts.";
        hogwarts.tid = tenants.get(0).id;
        hogwarts.minAlt = 0;
        hogwarts.minLat = 50.0000;
        hogwarts.minLong = 5.0000;
        hogwarts.maxAlt = 20;
        hogwarts.maxLat = 55.0000;
        hogwarts.maxLong = 10.0000;
        hogwarts = ls.saveLocation(hogwarts);

        Floor greatHall = new Floor();
        greatHall.description = "The Great Hall";
        greatHall.locationId = hogwarts.id;
        greatHall.tid = tenants.get(0).id;
        greatHall.minAlt = 0;
        greatHall.minLat = 50.2350;
        greatHall.minLong = 7.2879;
        greatHall.maxAlt = 10;
        greatHall.maxLat = 54.2146;
        greatHall.maxLong = 9.0280;
        greatHall = fs.saveFloor(greatHall);

        Beacon ghb1 = new Beacon();
        ghb1.name = "Right wall";
        ghb1.Lat = 51.7803;
        ghb1.Long = 7.8964;
        ghb1.Alt = 5;
        ghb1.floorId = greatHall.id;
        ghb1.locationId = hogwarts.id;
        ghb1 = bs.saveBeacon(ghb1);

        Beacon ghb2 = new Beacon();
        ghb2.name = "Left wall";
        ghb2.Lat = 54.1264;
        ghb2.Long = 9.0111;
        ghb2.Alt = 4;
        ghb2.floorId = greatHall.id;
        ghb2.locationId = hogwarts.id;
        ghb2 = bs.saveBeacon(ghb2);

        Beacon ghb3 = new Beacon();
        ghb3.name = "Headmasters table";
        ghb3.Lat = 53.2222;
        ghb3.Long = 8.6810;
        ghb3.Alt = 9;
        ghb3.floorId = greatHall.id;
        ghb3.locationId = hogwarts.id;
        ghb3 = bs.saveBeacon(ghb3);

        Beacon ghb4 = new Beacon();
        ghb4.name = "Entrance";
        ghb4.Lat = 53.5952;
        ghb4.Long = 7.9840;
        ghb4.Alt = 8;
        ghb4.floorId = greatHall.id;
        ghb4.locationId = hogwarts.id;
        ghb4 = bs.saveBeacon(ghb4);

        Floor dorm = new Floor();
        dorm.description = "The Gryffindor Dorms";
        dorm.locationId = hogwarts.id;
        dorm.tid = tenants.get(0).id;
        dorm.minAlt = 10;
        dorm.minLat = 51.6495;
        dorm.minLong = 5.2879;
        dorm.maxAlt = 20;
        dorm.maxLat = 54.9999;
        dorm.maxLong = 9.6540;
        dorm = fs.saveFloor(dorm);

        Beacon db1 = new Beacon();
        db1.name = "Common Room";
        db1.Lat = 54.9998;
        db1.Long = 7.0000;
        db1.Alt = 20;
        db1.floorId = dorm.id;
        db1.locationId = hogwarts.id;
        db1 = bs.saveBeacon(db1);

        Beacon db2 = new Beacon();
        db2.name = "Harry's Room";
        db2.Lat = 52.9421;
        db2.Long = 8.5420;
        db2.Alt = 12;
        db2.floorId = dorm.id;
        db2.locationId = hogwarts.id;
        db2 = bs.saveBeacon(db2);

        Beacon db3 = new Beacon();
        db3.name = "Hermoine's Room";
        db3.Lat = 51.9981;
        db3.Long = 5.6987;
        db3.Alt = 17;
        db3.floorId = dorm.id;
        db3.locationId = hogwarts.id;
        db3 = bs.saveBeacon(db3);

        Beacon db4 = new Beacon();
        db4.name = "Gryffindor Tower";
        db4.Lat = 53.4021;
        db4.Long = 6.5580;
        db4.Alt = 20;
        db4.floorId = dorm.id;
        db4.locationId = hogwarts.id;
        db4 = bs.saveBeacon(db4);

        Location forest = new Location();
        forest.name = "Forbidden Forest";
        forest.tid = tenants.get(0).id;
        forest.description = "The Forest outside of the grounds of Hogwarts.";
        forest.minAlt = 0;
        forest.minLat = 55.0000;
        forest.minLong = 5.0000;
        forest.maxAlt = 10;
        forest.maxLat = 60.0000;
        forest.maxLong = 10.0000;
        forest = ls.saveLocation(forest);

        Floor forestFloor = new Floor();
        forestFloor.description = "The forest floor in the forrbidden forest";
        forestFloor.locationId = forest.id;
        forestFloor.tid = tenants.get(0).id;
        forestFloor.minAlt = 0;
        forestFloor.minLat = 55.5555;
        forestFloor.minLong = 5.0001;
        forestFloor.maxAlt = 10;
        forestFloor.maxLat = 59.9999;
        forestFloor.maxLong = 8.5000;
        forestFloor = fs.saveFloor(forestFloor);

        Beacon fb1 = new Beacon();
        fb1.name = "Hagrids Hut";
        fb1.Lat = 58.9221;
        fb1.Long = 5.0002;
        fb1.Alt = 2;
        fb1.floorId = forestFloor.id;
        fb1.locationId = forest.id;
        fb1 = bs.saveBeacon(fb1);

        Beacon fb2 = new Beacon();
        fb2.name = "Buckbeak's Pasture";
        fb2.Lat = 55.6666;
        fb2.Long = 8.4999;
        fb2.Alt = 8;
        fb2.floorId = forestFloor.id;
        fb2.locationId = forest.id;
        fb2 = bs.saveBeacon(fb2);

        Beacon fb3 = new Beacon();
        fb3.name = "Aragog's Den";
        fb3.Lat = 59.1245;
        fb3.Long = 7.4598;
        fb3.Alt = 6;
        fb3.floorId = forestFloor.id;
        fb3.locationId = forest.id;
        fb3 = bs.saveBeacon(fb3);

        Beacon fb4 = new Beacon();
        fb4.name = "Womping Willow";
        fb4.Lat = 57.8175;
        fb4.Long = 8.1594;
        fb4.Alt = 8;
        fb4.floorId = forestFloor.id;
        fb4.locationId = forest.id;
        fb4 = bs.saveBeacon(fb4);

        Location leakyCauldron = new Location();
        leakyCauldron.name = "The Leaky Cauldron";
        leakyCauldron.tid = tenants.get(1).id;
        leakyCauldron.description = "The pub and inn at the entrance of Diagon Alley";
        leakyCauldron.minAlt = 0;
        leakyCauldron.minLat = 40.0000;
        leakyCauldron.minLong = 0.0000;
        leakyCauldron.maxAlt = 10;
        leakyCauldron.maxLat = 45.0000;
        leakyCauldron.maxLong = 5.0000;
        leakyCauldron = ls.saveLocation(leakyCauldron);

        Floor pub = new Floor();
        pub.description = "The pub in the Leaky Cauldron";
        pub.locationId = leakyCauldron.id;
        pub.tid = tenants.get(1).id;
        pub.minAlt = 0;
        pub.minLat = 40.2223;
        pub.minLong = 0.0001;
        pub.maxAlt = 10;
        pub.maxLat = 44.4567;
        pub.maxLong = 4.8888;
        pub = fs.saveFloor(pub);

        Beacon pb1 = new Beacon();
        pb1.name = "Bar";
        pb1.tid = tenants.get(1).id;
        pb1.Lat = 40.3339;
        pb1.Long = 1.0001;
        pb1.Alt = 7;
        pb1.floorId = pub.id;
        pb1.locationId = leakyCauldron.id;
        pb1 = bs.saveBeacon(pb1);

        Beacon pb2 = new Beacon();
        pb2.name = "Tables";
        pb2.tid = tenants.get(1).id;
        pb2.Lat = 44.1114;
        pb2.Long = 2.9786;
        pb2.Alt = 1;
        pb2.floorId = pub.id;
        pb2.locationId = leakyCauldron.id;
        pb2 = bs.saveBeacon(pb2);

        Beacon pb3 = new Beacon();
        pb3.name = "Broom Closet";
        pb3.tid = tenants.get(1).id;
        pb3.Lat = 41.7752;
        pb3.Long = 3.9081;
        pb3.Alt = 4;
        pb3.floorId = pub.id;
        pb3.locationId = leakyCauldron.id;
        pb3 = bs.saveBeacon(pb3);

        Beacon pb4 = new Beacon();
        pb4.name = "Office";
        pb4.tid = tenants.get(1).id;
        pb4.Lat = 42.3343;
        pb4.Long = 2.6026;
        pb4.Alt = 7;
        pb4.floorId = pub.id;
        pb4.locationId = leakyCauldron.id;
        pb4 = bs.saveBeacon(pb4);

        Location gingotts = new Location();
        gingotts.name = "Gringotts";
        gingotts.tid = tenants.get(1).id;
        gingotts.description = "The banking institution in Diagon Alley";
        gingotts.minAlt = 0;
        gingotts.minLat = 45.0000;
        gingotts.minLong = 0.0000;
        gingotts.maxAlt = 100;
        gingotts.maxLat = 50.0000;
        gingotts.maxLong = 5.0000;
        gingotts = ls.saveLocation(gingotts);

        Floor vault = new Floor();
        vault.description = "The vault gaurded by the dragon in Diagon Alley";
        vault.locationId = gingotts.id;
        vault.tid = tenants.get(1).id;
        vault.minAlt = 0;
        vault.minLat = 45.0000;
        vault.minLong = 0.0000;
        vault.maxAlt = 50;
        vault.maxLat = 50.0000;
        vault.maxLong = 5.0000;
        vault = fs.saveFloor(vault);

        Beacon vb1 = new Beacon();
        vb1.name = "Vault #66723";
        vb1.tid = tenants.get(1).id;
        vb1.Lat = 45.9939;
        vb1.Long = 1.05667;
        vb1.Alt = 10;
        vb1.floorId = vault.id;
        vb1.locationId = gingotts.id;
        vb1 = bs.saveBeacon(vb1);

        Beacon vb2 = new Beacon();
        vb2.name = "Vault #97249";
        vb2.tid = tenants.get(1).id;
        vb2.Lat = 50.0000;
        vb2.Long = 4.1182;
        vb2.Alt = 32;
        vb2.floorId = vault.id;
        vb2.locationId = gingotts.id;
        vb2 = bs.saveBeacon(vb2);

        Beacon vb3 = new Beacon();
        vb3.name = "Vault #23583";
        vb3.tid = tenants.get(1).id;
        vb3.Lat = 47.7777;
        vb3.Long = 2.5597;
        vb3.Alt = 24;
        vb3.floorId = vault.id;
        vb3.locationId = gingotts.id;
        vb3 = bs.saveBeacon(vb3);

        Beacon vb4 = new Beacon();
        vb4.name = "Dragon's Area";
        vb4.tid = tenants.get(1).id;
        vb4.Lat = 47.1645;
        vb4.Long = 3.0029;
        vb4.Alt = 49;
        vb4.floorId = vault.id;
        vb4.locationId = gingotts.id;
        vb4 = bs.saveBeacon(vb4);

        //Add Guest Visits
        User luna = us.getUserByFirstName("Luna");
        Device lunaWand = new Device();
        lunaWand.deviceType = "Unicorn Hair Wand";
        lunaWand.userId = luna.id;
        lunaWand.tid = tenants.get(0).id;
        lunaWand = ds.saveDevice(lunaWand);
        GuestVisit lunaVisit = new GuestVisit();
        lunaVisit.deviceId = lunaWand.id;
        lunaVisit.userId = luna.id;
        lunaVisit.locationId = hogwarts.id;
        lunaVisit.destinationAlt = 8;
        lunaVisit.destinationLat = 53.1111;
        lunaVisit.destinationLong = 8.5914;
        lunaVisit = gvs.saveGuestVisit(lunaVisit);

        User albus = us.getUserByFirstName("Albus");
        Device albusWand = new Device();
        albusWand.deviceType = "Elder Wand";
        albusWand.userId = albus.id;
        albusWand.tid = tenants.get(0).id;
        albusWand = ds.saveDevice(albusWand);
        GuestVisit albusVisit = new GuestVisit();
        albusVisit.deviceId = albusWand.id;
        albusVisit.userId = albus.id;
        albusVisit.locationId = forest.id;
        albusVisit.destinationAlt = 6;
        albusVisit.destinationLat = 57.2222;
        albusVisit.destinationLong = 9.1124;
        albusVisit = gvs.saveGuestVisit(albusVisit);

        User hagrid = us.getUserByFirstName("Rubeus");
        Device hagridWand = new Device();
        hagridWand.deviceType = "Umbrella Wand";
        hagridWand.userId = hagrid.id;
        hagridWand.tid = tenants.get(0).id;
        hagridWand = ds.saveDevice(hagridWand);
        GuestVisit hagridVisit = new GuestVisit();
        hagridVisit.deviceId = hagridWand.id;
        hagridVisit.userId = hagrid.id;
        hagridVisit.locationId = hogwarts.id;
        hagridVisit.destinationAlt = 20;
        hagridVisit.destinationLat = 54.8731;
        hagridVisit.destinationLong = 6.7893;
        hagridVisit = gvs.saveGuestVisit(hagridVisit);

        User hermione = us.getUserByFirstName("Hermione");
        Device hermioneWand = new Device();
        hermioneWand.deviceType = "Dragon Heartstring Wand";
        hermioneWand.userId = hermione.id;
        hermioneWand.tid = tenants.get(0).id;
        hermioneWand = ds.saveDevice(hermioneWand);
        GuestVisit hermioneVisit = new GuestVisit();
        hermioneVisit.deviceId = hermioneWand.id;
        hermioneVisit.userId = hermione.id;
        hermioneVisit.locationId = hogwarts.id;
        hermioneVisit.destinationAlt = 10;
        hermioneVisit.destinationLat = 50.3462;
        hermioneVisit.destinationLong = 8.2009;
        hermioneVisit = gvs.saveGuestVisit(hermioneVisit);

        User bellatrix = us.getUserByFirstName("Bellatrix");
        Device bellatrixWand = new Device();
        bellatrixWand.deviceType = "Walnut Wand";
        bellatrixWand.userId = bellatrix.id;
        bellatrixWand.tid = tenants.get(1).id;
        bellatrixWand = ds.saveDevice(bellatrixWand);
        GuestVisit bellatrixVisit = new GuestVisit();
        bellatrixVisit.deviceId = bellatrixWand.id;
        bellatrixVisit.userId = bellatrix.id;
        bellatrixVisit.locationId = gingotts.id;
        bellatrixVisit.tid = tenants.get(1).id;
        bellatrixVisit.destinationAlt = 50;
        bellatrixVisit.destinationLat = 47.9897;
        bellatrixVisit.destinationLong = 3.0;
        bellatrixVisit = gvs.saveGuestVisit(bellatrixVisit);

        User george = us.getUserByFirstName("George");
        Device georgeWand = new Device();
        georgeWand.deviceType = "Walnut Wand";
        georgeWand.userId = george.id;
        georgeWand.tid = tenants.get(1).id;
        georgeWand = ds.saveDevice(georgeWand);
        GuestVisit georgeVisit = new GuestVisit();
        georgeVisit.deviceId = georgeWand.id;
        georgeVisit.userId = george.id;
        georgeVisit.locationId = gingotts.id;
        georgeVisit.tid = tenants.get(1).id;
        georgeVisit.destinationAlt = 10;
        georgeVisit.destinationLat = 49.0097;
        georgeVisit.destinationLong = 4.0980;
        georgeVisit = gvs.saveGuestVisit(georgeVisit);

        User sirius = us.getUserByFirstName("Sirius");
        Device siriusWand = new Device();
        siriusWand.deviceType = "Walnut Wand";
        siriusWand.userId = sirius.id;
        siriusWand.tid = tenants.get(1).id;
        siriusWand = ds.saveDevice(siriusWand);
        GuestVisit siriusVisit = new GuestVisit();
        siriusVisit.userId = sirius.id;
        siriusVisit.deviceId = siriusWand.id;
        siriusVisit.locationId = leakyCauldron.id;
        siriusVisit.tid = tenants.get(1).id;
        siriusVisit.destinationAlt = 7;
        siriusVisit.destinationLat = 41.7663;
        siriusVisit.destinationLong = 2.0894;
        siriusVisit = gvs.saveGuestVisit(siriusVisit);

        User lee = us.getUserByFirstName("Lee");
        Device leeWand = new Device();
        leeWand.deviceType = "Chestnut Wand";
        leeWand.userId = lee.id;
        leeWand.tid = tenants.get(1).id;
        leeWand = ds.saveDevice(leeWand);
        GuestVisit leeVisit = new GuestVisit();
        leeVisit.userId = lee.id;
        leeVisit.deviceId = leeWand.id;
        leeVisit.locationId = leakyCauldron.id;
        leeVisit.tid = tenants.get(1).id;
        leeVisit.destinationAlt = 7;
        leeVisit.destinationLat = 41.7663;
        leeVisit.destinationLong = 2.0894;
        leeVisit = gvs.saveGuestVisit(leeVisit);

        Thread.sleep(7000);

        lunaVisit.userState = "Checked In";
        lunaVisit.checkInTS = new Date();
        lunaVisit = gvs.saveGuestVisit(lunaVisit);

        hagridVisit.userState = "Checked In";
        hagridVisit.checkInTS = new Date();
        hagridVisit = gvs.saveGuestVisit(hagridVisit);

        siriusVisit.userState = "Checked In";
        siriusVisit.checkInTS = new Date();
        siriusVisit = gvs.saveGuestVisit(siriusVisit);

        georgeVisit.userState = "Checked In";
        georgeVisit.checkInTS = new Date();
        georgeVisit = gvs.saveGuestVisit(georgeVisit);

        Thread.sleep(5000);

        lunaVisit.userState = "Checked Out";
        lunaVisit.checkOutTS = new Date();
        lunaVisit = gvs.saveGuestVisit(lunaVisit);

        siriusVisit.userState = "Checked Out";
        siriusVisit.checkOutTS = new Date();
        siriusVisit = gvs.saveGuestVisit(siriusVisit);


        //Wait and update 5 users
        Thread.sleep(6000);


        User ginny = us.getUserByFirstName("Ginny");
        ginny.lastName = "Potter";
        ginny.email = ginny.firstName + ginny.lastName + email;
        ginny = us.saveUser(ginny);

        User tom = us.getUserByFirstName("Tom");
        tom.firstName = "Lord";
        tom.lastName = "Voldemort";
        tom.email = "LordVoldemortRules@deatheater.com";
        tom = us.saveUser(tom);

        User draco = us.getUserByFirstName("Draco");
        draco.email = "harrypottersux@deatheater.com";
        draco = us.saveUser(draco);

        // Wait to update Draco again
        Thread.sleep(4000);
        draco.email = "HarryPotterIsCool@goodguy.com";
        draco = us.saveUser(draco);

        User neville = us.getUserByFirstName("Neville");
        neville.password = getRandomPassword();
        neville = us.saveUser(neville);

        User minerva = us.getUserByFirstName("Minerva");
        minerva.firstName = "Headmaster";
        minerva = us.saveUser(minerva);

    }

    /**
     * Query the data previously inserted
     * @throws InterruptedException
     */
    private static void queryData() throws InterruptedException {
        //Services
        RoleService rs = new RoleService();
        TenantRoleService trs = new TenantRoleService();
        FdfTenantServices tenantService = new FdfTenantServices();
        UserService us = new UserService();
        TenantUserService tus = new TenantUserService();
        FdfTenantServices ts = new FdfTenantServices();
        IdCredentialsService ids = new IdCredentialsService();
        UserRoleService urs = new UserRoleService();
        LocationService ls = new LocationService();
        FloorService fs = new FloorService();
        BeaconService bs = new BeaconService();
        DeviceService ds = new DeviceService();
        GuestVisitService gvs = new GuestVisitService();

        //Get both Tenants
        List<FdfTenant> tenants = tenantService.getAllTenants();

        // QUERIES
        System.out.println("\n################# Tenant Query ########################");
        for(FdfTenant tenant: tenants){
            System.out.println("Tenant Id: " + tenant.id + "\t Tenant Name: "
                    + tenant.name + "\nTenant Description: "
                    + tenant.description);

            System.out.println("\nUsers in tenant, " + tenant.name + ": ");
            List<User> users = tus.getUsersByTenantId(tenant.id);
            for(User user: users){
                System.out.println("User Id: " + user.id + "\t Name: " + user.firstName + " " + user.lastName);
            }
            System.out.println("");
        }


        System.out.println("\n################# User Role Query ########################");
        User HP = us.getUserByFirstName("Harry"); //Harry Potter Roles
        System.out.println("User Id: " + HP.id + "\t Name: " + HP.firstName + " " + HP.lastName);
        System.out.println("Associated Roles:");
        List<Role> HProles = urs.getRolesByUser(HP.id);
        for(Role r: HProles){
            System.out.println("Role Id: " + r.id + ", Role Name: "
                    + r.name);
        }

        User CD = us.getUserByFirstName("Cedric"); //Cedric Diggory Roles
        System.out.println("\nUser Id: " + CD.id + "\t Name: " + CD.firstName + " " + CD.lastName);
        System.out.println("Associated Roles:");
        List<Role> CDroles = urs.getRolesByUser(CD.id);
        for(Role r: CDroles){
            System.out.println("Role Id: " + r.id + ", Role Name: "
                    + r.name);
        }

        System.out.println("\n##################### Users with History Query############################");

        // Users Changed Query
        List<FdfEntity<User>> usersWithHistory = new ArrayList<>();
        usersWithHistory.add(us.getUsersByFirstNameWithHistory("Ginny"));
        usersWithHistory.add(us.getUserWithHistoryById(6));
        usersWithHistory.add(us.getUsersByFirstNameWithHistory("Draco"));
        usersWithHistory.add(us.getUsersByFirstNameWithHistory("Neville"));
        usersWithHistory.add(us.getUserWithHistoryById(8));

        int i = 1;
        for(FdfEntity<User> userWithHistory: usersWithHistory) {
            System.out.println("User change " + i + ": ");
            System.out.println("User Id: " + userWithHistory.current.id + "\t Name: " +
                    userWithHistory.current.firstName + " " + userWithHistory.current.lastName);
            System.out.println("Email: " + userWithHistory.current.email + "\t Password: " +
                    userWithHistory.current.password);
            System.out.println("Start time: " + userWithHistory.current.arsd + " End time: " +
                    userWithHistory.current.ared);

            System.out.println("----- History -----");
            // Now show the historical records for the car
            for (User userHistory : userWithHistory.history) {
                System.out.println("User Id: " + userHistory.id +
                        "\t Name: " + userHistory.firstName + " " + userHistory.lastName);
                System.out.println("Email: " + userHistory.email + "\t Password: " +
                        userHistory.password);
                System.out.println("Start time: " + userHistory.arsd + " End time: " + userHistory.ared);
            }
            i++;
            System.out.println("");
        }

        System.out.println("\n#################### Guest Visits ######################");
        for(FdfTenant tenant: tenants) {
            System.out.println("Tenant Id: " + tenant.id + "\t Tenant Name: "
                    + tenant.name + "\n");

            System.out.println("All Current Guest Visits in  " + tenant.name + ": ");
            List<GuestVisit> guestVisits = gvs.getAllVisitsByTenant(tenant.id);
            for (GuestVisit gv : guestVisits) {
                gv.user = us.getUserById(gv.userId);
                System.out.println("Guest Visit Id: " + gv.id + "\t Name: " + gv.user.firstName + " " +
                        gv.user.lastName);
                System.out.println("Destination Alt, Lat, Long: " + gv.destinationAlt + ", " +
                        gv.destinationLat + ", " + gv.destinationLong);
                System.out.println("Check In Time: " + gv.checkInTS + ", Current Status: " + gv.userState);
                System.out.println("");
            }
            System.out.println("------------------------------------");
        }

        FdfEntity<GuestVisit> visitCycle = gvs.getVisitWithHistoryById(1); // Lunas Visit
        visitCycle.current.user = us.getUserById(visitCycle.current.userId);
        System.out.println("Guest Visit Cycle: ");
        System.out.println("Visit Id: " + visitCycle.current.id + "\t Name: " +
                visitCycle.current.user.firstName + " " + visitCycle.current.user.lastName);
        System.out.println("Current Status: " + visitCycle.current.userState + "\tCheck In Time: " +
                visitCycle.current.checkInTS + "\tCheck Out Time: " +
                visitCycle.current.checkOutTS);
        System.out.println("Start time: " + visitCycle.current.arsd + " End time: " +
                visitCycle.current.ared);

        System.out.println("----- History -----");
        // Now show the historical records for the car
        for (GuestVisit visitHistory : visitCycle.history) {
            System.out.println("Current Status: " + visitHistory.userState + "\tCheck In Time: " +
                    visitHistory.checkInTS + "\tCheck Out Time: " +
                    visitHistory.checkOutTS);
            System.out.println("Start time: " + visitHistory.arsd + " End time: " +
                    visitHistory.ared);
        }
        System.out.println("");


        System.out.println("\n#################### Locations, Floors, Beacons by Tenant ######################");
        for(FdfTenant tenant: tenants) {
            System.out.println("Tenant Id: " + tenant.id + "\t Tenant Name: "
                    + tenant.name);

            System.out.println("Locations in tenant, " + tenant.name + ": ");
            List<Location> locations = ls.getLocationByTenant(tenant.id);
            for (Location loc : locations) {
                System.out.println("------------------------------------");
                System.out.println("Locations Id: " + loc.id + "\t Name: " + loc.name + ", " + loc.description);
                System.out.println("Min and Max Alt: " + loc.minAlt + ", " + loc.maxAlt);
                System.out.println("Min and Max Lat: " + loc.minLat + ", " + loc.maxLat);
                System.out.println("Min and Max Long: " + loc.minLong + ", " + loc.maxLong);

                List<Floor> floors = fs.getFloorsByLocation(loc.id, tenant.id);
                System.out.println("\nFloors in location: " + loc.name);
                for (Floor floor : floors) {
                    System.out.println("Floor Id: " + floor.id + "\t Desc: " + floor.description);
                    System.out.println("Min and Max Alt: " + floor.minAlt + ", " + floor.maxAlt);
                    System.out.println("Min and Max Lat: " + floor.minLat + ", " + floor.maxLat);
                    System.out.println("Min and Max Long: " + floor.minLong + ", " + floor.maxLong);

                    List<Beacon> beacons = bs.getBeaconsByFloor(floor.id, tenant.id);
                    System.out.println("\nBeacons in this floor: ");

                    for (Beacon beacon : beacons) {
                        System.out.println("Beacon Id: " + beacon.id + "\t Name: " + beacon.name);
                        System.out.println("Alt, Lat and Long: " + beacon.Alt + ", " + beacon.Lat + ", " + beacon.Long);
                    }
                    System.out.println("");
                }
                System.out.println("");
            }
        }
    }

    /**
     * Gets random string to use as password
     * @return String
     */
    private static String getRandomPassword() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    /**
     * Gets random string to use as color
     * @return String
     */
    private static String getRandomColor() {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);

        return Integer.toString(r) + Integer.toString(g) + Integer.toString(b);
    }
}