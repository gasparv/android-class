package sk.tuke.smartlab.lab6_restapiclient_retrofit2;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class JsonTools {

    public static List<UserModel> parseToUsers(JsonArray userJsonArray){
        List<UserModel> usersList = new ArrayList<>();
        for(int i=0;i<userJsonArray.size();i++){
            UserModel newUser = new UserModel();
            newUser.setId(userJsonArray.get(i).getAsJsonObject().get("id").getAsInt());
            newUser.setName(userJsonArray.get(i).getAsJsonObject().get("name").getAsString());
            newUser.setUsername(userJsonArray.get(i).getAsJsonObject().get("username").getAsString());
            newUser.setEmail(userJsonArray.get(i).getAsJsonObject().get("email").getAsString());
            newUser.setWebsite(userJsonArray.get(i).getAsJsonObject().get("website").getAsString());
            usersList.add(newUser);
        }
        return usersList;
    }
}
