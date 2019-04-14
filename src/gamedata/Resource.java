package gamedata;

public class Resource {
    public enum ResourceType { IMAGE_RESOURCE, AUDIO_RESOURCE, COLOR_RESOURCE }
    public String resourceID, src; // For colour resources, src will just be a hexcode
    public ResourceType resourceType;
    public Resource () {
        resourceID = "";
        src = "";
    }
}
