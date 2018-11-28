package sk.tuke.smartlab.jsonspracovanie;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class JsonTools {
private static final String ZOZNAM_TIMOV = "teams";
private static final String ID = "id";

    public static List<Team> convertJsonToTeams(JsonObject responseData){
        List<Team> zoznamTimov = new ArrayList<>();
        //RESPONSE DATA = JSONOBJECT obsahuje copyright a teams zoznam

        //Toto je zoznam timov = z responsu som vytiahol len zoznam s nazvom "TEAMS"
        JsonArray zoznamTImovZJsonu = responseData.get(ZOZNAM_TIMOV).getAsJsonArray();

        //teams je zoznam, kt. obsahuje objekty, preto prehladavam kazdy objekt tohto zoznamu
        for(int i=0;i<zoznamTImovZJsonu.size();i++){

            Team novyTim = new Team();

            //Toto je ity prvok zoznamu, preto je to JsonObject
            JsonObject tim = zoznamTImovZJsonu.get(i).getAsJsonObject();
            novyTim.id = tim.get(ID).getAsInt();
            novyTim.link = tim.get("link").getAsString();
            novyTim.name = tim.get("name").getAsString();
            novyTim.teamName = tim.get("teamName").getAsString();

            JsonObject divizia = tim.get("division").getAsJsonObject();
            Division novaDivizia = new Division();
            novaDivizia.id = divizia.get("id").getAsInt();
            novaDivizia.abbreviation = divizia.get("abbreviation").getAsString();
            novaDivizia.link = divizia.get("link").getAsString();
            novaDivizia.name = divizia.get("name").getAsString();
            novaDivizia.nameShort = divizia.get("nameShort").getAsString();

            novyTim.divizia = novaDivizia;
            zoznamTimov.add(novyTim);
        }
        return zoznamTimov;
    }
}
