package Engine.src.Components;

import java.util.Collections;
import java.util.List;

public class TagsComponent extends Component {
    private List<String> myTags;

    public TagsComponent(List<String> tags) {
        myTags = tags;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(myTags);
    }

    public boolean contains(String tag){
        return myTags.contains(tag);
    }
}
