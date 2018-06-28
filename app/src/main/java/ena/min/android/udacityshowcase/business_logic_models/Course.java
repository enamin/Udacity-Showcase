package ena.min.android.udacityshowcase.business_logic_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Course {
    @SerializedName("key")
    public String key;
    @SerializedName("image")
    public String image;
    @SerializedName("project_name")
    public String projectName;
    @SerializedName("title")
    public String title;
    @SerializedName("summary")
    public String summary;
    @SerializedName("instructors")
    public ArrayList<Instructor> instructors;
}
