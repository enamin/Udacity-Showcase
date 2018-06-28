package ena.min.android.udacityshowcase.business_logic_models;

import com.google.gson.annotations.SerializedName;

public class Instructor {
    @SerializedName("name")
    public String name;
    @SerializedName("image")
    public String image;
    @SerializedName("bio")
    public String bio;
}
